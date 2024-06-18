package com.themadjem.crafticube.block.custom;

import com.mojang.serialization.*;
import com.themadjem.crafticube.block.entity.CrafticubeBlockEntity;
import com.themadjem.crafticube.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BarrelBlock;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.fluids.FluidUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class CrafticubeBlock extends BaseEntityBlock {
    public static final MapCodec<CrafticubeBlock> CODEC = simpleCodec(CrafticubeBlock::new);

    public CrafticubeBlock(Properties pProporites) {
        super(pProporites);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public @NotNull RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public List<ItemStack> getDrops(BlockState pState, LootParams.Builder pParams) {
        super.getDrops(pState, pParams);
        return new ArrayList<>();
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pMovedByPiston) {
        if (!pState.is(pNewState.getBlock())) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof CrafticubeBlockEntity crafticube) {
                Player player = pLevel.getNearestPlayer(
                        pPos.getX(), pPos.getY(), pPos.getZ(), 10, false
                );
                if (player != null && !player.isCreative()) {
                    ItemStack itemStack = new ItemStack(this);
                    CompoundTag nbt = new CompoundTag();
                    if (crafticube.hasCustomName()) {
                        CompoundTag name = new CompoundTag();
                        name.putString("Name", Component.Serializer.toJson(crafticube.getCustomName()));
                        nbt.put("display", name);
                    }
                    crafticube.saveAdditionalToTag(nbt);
                    itemStack.setTag(nbt);
                    Containers.dropContents(pLevel, pPos, new SimpleContainer(itemStack));
                }
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, LivingEntity pPlacer, ItemStack pStack) {
        if (pStack.hasTag()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof CrafticubeBlockEntity crafticube) {
                crafticube.load(pStack.getTag());
            }
        }

        if (pStack.hasCustomHoverName()) {
            BlockEntity blockentity = pLevel.getBlockEntity(pPos);
            if (blockentity instanceof CrafticubeBlockEntity crafticubeBlockEntity) {
                crafticubeBlockEntity.setCustomName(pStack.getHoverName());
            }
        }

    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof CrafticubeBlockEntity crafticubeBlockEntity) {
                if (FluidUtil.interactWithFluidHandler(pPlayer, pHand, pLevel, pPos, pHit.getDirection())) {
                    crafticubeBlockEntity.setChanged();
                    return InteractionResult.SUCCESS;
                } else {
                    if (pPlayer.getItemInHand(pHand).is(ModItems.CRAFTING_CORE.get())) {
                        crafticubeBlockEntity.printContents(pLevel, pPlayer);
                    }
                }
            }
        }
        return InteractionResult.CONSUME;
    }


    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return null;
    }
}
