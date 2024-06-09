package com.themadjem.crafticube.datagen;

import com.themadjem.crafticube.CraftiCube;
import com.themadjem.crafticube.block.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, CraftiCube.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(ModBlocks.CRAFTICUBE);

        blockWithItem(ModBlocks.IRON_CRAFTICUBE);
        blockWithItem(ModBlocks.GOLD_CRAFTICUBE);
        blockWithItem(ModBlocks.DIAMOND_CRAFTICUBE);
        blockWithItem(ModBlocks.NETHERITE_CRAFTICUBE);
    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject){
        simpleBlockWithItem(blockRegistryObject.get(),cubeAll(blockRegistryObject.get()));
    }
}
