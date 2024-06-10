package com.themadjem.crafticube.datagen;

import com.themadjem.crafticube.CraftiCube;
import com.themadjem.crafticube.block.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagGenerator extends BlockTagsProvider {
    public ModBlockTagGenerator(PackOutput output,
                                CompletableFuture<HolderLookup.Provider> lookupProvider,
                                @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, CraftiCube.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(
                ModBlocks.IRON_CRAFTICUBE.get(),
                ModBlocks.GOLD_CRAFTICUBE.get(),
                ModBlocks.DIAMOND_CRAFTICUBE.get(),
                ModBlocks.NETHERITE_CRAFTICUBE.get()
        );
        this.tag(BlockTags.NEEDS_DIAMOND_TOOL).add(
                ModBlocks.IRON_CRAFTICUBE.get(),
                ModBlocks.GOLD_CRAFTICUBE.get(),
                ModBlocks.DIAMOND_CRAFTICUBE.get(),
                ModBlocks.NETHERITE_CRAFTICUBE.get()
        );
    }
}
