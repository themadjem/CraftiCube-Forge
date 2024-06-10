package com.themadjem.crafticube.block.custom;

import com.themadjem.crafticube.block.entity.CrafticubeBlockEntity;
import com.themadjem.crafticube.block.entity.IronCrafticubeBlockEntity;
import com.themadjem.crafticube.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.OutgoingChatMessage;
import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.Nullable;

public class CrafticubeBlock extends BaseEntityBlock {
    public CrafticubeBlock(Properties pProporites) {
        super(pProporites);
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pMovedByPiston) {
        /* For now, drop any contained Items and void any Fluids */

        if (pState.getBlock() != pNewState.getBlock()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof CrafticubeBlockEntity) {
                ((CrafticubeBlockEntity) blockEntity).drops();
            }
        }

        super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        /* Don't need this if not implementing menu */
        if (!pLevel.isClientSide) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof CrafticubeBlockEntity crafticubeBlockEntity) {

                SimpleContainer inventory = crafticubeBlockEntity.getInventory();
                if (!inventory.isEmpty()) {
                    StringBuilder inventoryContents = new StringBuilder("Inventory Contents:\n");

                    for (int i = 0; i < inventory.getContainerSize(); i++) {
                        ItemStack stack = inventory.getItem(i);
                        if (!stack.isEmpty()) {
                            inventoryContents.append("- ")
                                    .append(stack.getCount())
                                    .append("x ")
                                    .append(stack.getHoverName().getString())
                                    .append("\n");
                        }
                    }

                    if (pPlayer instanceof ServerPlayer serverPlayer) {
                        serverPlayer.displayClientMessage(Component.literal(inventoryContents.toString()), false);
                    }
                }

                return InteractionResult.SUCCESS;
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
