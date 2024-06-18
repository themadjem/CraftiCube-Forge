package com.themadjem.crafticube.block.custom;

import com.mojang.serialization.MapCodec;
import com.themadjem.crafticube.block.ModBlocks;
import com.themadjem.crafticube.block.entity.CrafticubeBlockEntity;
import com.themadjem.crafticube.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentHolder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.fluids.FluidUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
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
//                    CompoundTag nbt = new CompoundTag();
//                    if (crafticube.hasCustomName()) {
//                        CompoundTag name = new CompoundTag();
//
//                        // I have no idea if this section will work
//                        ResourceKey<? extends Registry<Block>> blockRegistryKey = ModBlocks.BLOCKS.getRegistryKey();
//                        Registry<Block> blockRegistry = pLevel.registryAccess().registryOrThrow(blockRegistryKey);
//                        HolderLookup.RegistryLookup<Block> blockLookup = blockRegistry.asLookup();
//                        HolderLookup.Provider provider = HolderLookup.Provider.create(Stream.of(blockLookup));
//                        // ChatGPT came up with this, so blame them
//
//                        name.putString("Name", Component.Serializer.toJson(crafticube.getCustomName(),provider));
//                        nbt.put("display", name);
//                    }
//
//                    crafticube.saveAdditionalToTag(nbt);
//                    itemStack.setTag(nbt);
                    Containers.dropContents(pLevel, pPos, new SimpleContainer(itemStack));
                }
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, LivingEntity pPlacer, ItemStack pStack) {
        // Don't need custom name handling anymore?
        //        if (pStack.get(DataComponents.CUSTOM_NAME)) {
//            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
//            if (blockEntity instanceof CrafticubeBlockEntity crafticube) {
//                crafticube.load(pStack.getTag());
//            }
//        }
//
//        if (pStack.hasCustomName()) {
//            BlockEntity blockentity = pLevel.getBlockEntity(pPos);
//            if (blockentity instanceof CrafticubeBlockEntity crafticubeBlockEntity) {
//                crafticubeBlockEntity.setCustomName(pStack.getHoverName());
//            }
//        }
        Component data = pStack.get(DataComponents.CUSTOM_NAME);

    }

    @Override
    public ItemInteractionResult useItemOn(ItemStack pStack,BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof CrafticubeBlockEntity crafticubeBlockEntity) {
                if (FluidUtil.interactWithFluidHandler(pPlayer, pHand, pLevel, pPos, pHit.getDirection())) {
                    crafticubeBlockEntity.setChanged();
                    return ItemInteractionResult.SUCCESS;
                } else {
                    if (pPlayer.getItemInHand(pHand).is(ModItems.CRAFTING_CORE.get())) {
                        crafticubeBlockEntity.printContents(pLevel, pPlayer);
                    }
                }
            }
        }
        return ItemInteractionResult.CONSUME;
    }


    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return null;
    }
}
