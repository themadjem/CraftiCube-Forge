package com.themadjem.crafticube.block;

import com.themadjem.crafticube.CraftiCube;
import com.themadjem.crafticube.block.custom.DiamondCrafticubeBlock;
import com.themadjem.crafticube.block.custom.GoldCrafticubeBlock;
import com.themadjem.crafticube.block.custom.IronCrafticubeBlock;
import com.themadjem.crafticube.block.custom.NetheriteCrafticubeBlock;
import com.themadjem.crafticube.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, CraftiCube.MOD_ID);

    public static final RegistryObject<Block> IRON_CRAFTICUBE = registerBlock("iron_crafticube",
            ()->new IronCrafticubeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> GOLD_CRAFTICUBE = registerBlock("gold_crafticube",
            ()->new GoldCrafticubeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GOLD_BLOCK)));
    public static final RegistryObject<Block> DIAMOND_CRAFTICUBE = registerBlock("diamond_crafticube",
            ()->new DiamondCrafticubeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DIAMOND_BLOCK)));
    public static final RegistryObject<Block> NETHERITE_CRAFTICUBE = registerBlock("netherite_crafticube",
            ()->new NetheriteCrafticubeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DIAMOND_BLOCK)));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block){
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
