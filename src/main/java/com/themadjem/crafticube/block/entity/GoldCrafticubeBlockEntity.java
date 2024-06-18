package com.themadjem.crafticube.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class GoldCrafticubeBlockEntity extends CrafticubeBlockEntity {
    public GoldCrafticubeBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.GOLD_CRAFTICUBE_BE.get(), pPos, pBlockState, 18, 8_000);
    }

//    @Override
//    public @NotNull Component getDefaultName() {
//        return Component.translatable("block.crafticube.gold_crafticube");
//    }
}
