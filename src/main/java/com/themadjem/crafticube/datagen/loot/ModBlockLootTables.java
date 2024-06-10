package com.themadjem.crafticube.datagen.loot;

import com.themadjem.crafticube.block.ModBlocks;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class ModBlockLootTables extends BlockLootSubProvider {
    public ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
//        this.dropSelf(ModBlocks.CRAFTICUBE.get());
        this.dropSelf(ModBlocks.IRON_CRAFTICUBE.get());
        this.dropSelf(ModBlocks.GOLD_CRAFTICUBE.get());
        this.dropSelf(ModBlocks.DIAMOND_CRAFTICUBE.get());
        this.dropSelf(ModBlocks.NETHERITE_CRAFTICUBE.get());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
