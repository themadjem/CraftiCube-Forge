package com.themadjem.crafticube.block.custom;

import com.themadjem.crafticube.block.entity.GoldCrafticubeBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;


public class GoldCrafticubeBlock extends CrafticubeBlock {
    public GoldCrafticubeBlock(Properties pProporites) {
        super(pProporites);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new GoldCrafticubeBlockEntity(blockPos, blockState);
    }
}
