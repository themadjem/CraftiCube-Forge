package com.themadjem.crafticube.block.custom;

import com.themadjem.crafticube.block.entity.DiamondCrafticubeBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;


public class DiamondCrafticubeBlock extends CrafticubeBlock {
    public DiamondCrafticubeBlock(Properties pProporites) {
        super(pProporites);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new DiamondCrafticubeBlockEntity(blockPos, blockState);
    }

}
