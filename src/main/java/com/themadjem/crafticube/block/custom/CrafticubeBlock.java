package com.themadjem.crafticube.block.custom;

import com.themadjem.crafticube.block.entity.CrafticubeBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
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
                ((CrafticubeBlockEntity) blockEntity).dropContents();
            }
        }

        super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        /* Don't need this if not implementing menu */
        if (pLevel.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof CrafticubeBlockEntity) {
                ((CrafticubeBlockEntity) blockEntity).printContents(pLevel, pPlayer);
//                pPlayer.openMenu((CrafticubeBlockEntity)blockentity);
//                pPlayer.awardStat(Stats.OPEN_BARREL);
//                PiglinAi.angerNearbyPiglins(pPlayer, true);
            }
            return InteractionResult.CONSUME;
        }
    }


    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return null;
    }
}
