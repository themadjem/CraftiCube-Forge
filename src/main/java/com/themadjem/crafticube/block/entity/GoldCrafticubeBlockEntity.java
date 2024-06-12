package com.themadjem.crafticube.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class GoldCrafticubeBlockEntity extends CrafticubeBlockEntity{
    public GoldCrafticubeBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.IRON_CRAFTICUBE_BE.get(),pPos, pBlockState, 18, 8_000);
    }
}
