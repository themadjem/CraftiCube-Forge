package com.themadjem.crafticube.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class DiamondCrafticubeBlockEntity extends CrafticubeBlockEntity{
    public DiamondCrafticubeBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.IRON_CRAFTICUBE_BE.get(),pPos, pBlockState, 26, 16_000);
    }
}
