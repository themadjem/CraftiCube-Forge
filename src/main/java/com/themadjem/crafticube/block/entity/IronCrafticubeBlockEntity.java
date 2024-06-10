package com.themadjem.crafticube.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class IronCrafticubeBlockEntity extends CrafticubeBlockEntity{
    public IronCrafticubeBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(pPos, pBlockState, 9, 4_000);
    }
}
