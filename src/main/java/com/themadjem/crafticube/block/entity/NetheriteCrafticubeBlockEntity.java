package com.themadjem.crafticube.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class NetheriteCrafticubeBlockEntity extends CrafticubeBlockEntity {
    public NetheriteCrafticubeBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.NETHERITE_CRAFTICUBE_BE.get(), pPos, pBlockState, 36, 32_000);
    }

    @Override
    public @NotNull Component getDefaultName() {
        return Component.translatable("block.crafticube.netherite_crafticube");
    }
}
