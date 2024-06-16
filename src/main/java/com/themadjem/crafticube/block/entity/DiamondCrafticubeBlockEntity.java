package com.themadjem.crafticube.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class DiamondCrafticubeBlockEntity extends CrafticubeBlockEntity {
    public DiamondCrafticubeBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.DIAMOND_CRAFTICUBE_BE.get(), pPos, pBlockState, 27, 16_000);
    }

    @Override
    public @NotNull Component getDefaultName() {
        return Component.translatable("block.crafticube.diamond_crafticube");
    }
}
