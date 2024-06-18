package com.themadjem.crafticube.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class IronCrafticubeBlockEntity extends CrafticubeBlockEntity {
    public IronCrafticubeBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.IRON_CRAFTICUBE_BE.get(), pPos, pBlockState, 9, 4_000);
    }

    @Override
    public Component getDefaultName() {
        return Component.translatable("block.crafticube.iron_crafticube");
    }
}
