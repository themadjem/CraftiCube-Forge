package com.themadjem.crafticube.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class NetheriteCrafticubeBlockEntity extends CrafticubeBlockEntity{
    public NetheriteCrafticubeBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.IRON_CRAFTICUBE_BE.get(),pPos, pBlockState, 36, 32_000);
    }
}
