package com.themadjem.crafticube.block.entity;

import com.mojang.datafixers.types.Type;
import com.themadjem.crafticube.CraftiCube;
import com.themadjem.crafticube.block.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, CraftiCube.MOD_ID);

    public static final RegistryObject<BlockEntityType<IronCrafticubeBlockEntity>> IRON_CRAFTICUBE_BE =
            BLOCK_ENTITIES.register("iron_crafticube_be", () ->
                    BlockEntityType.Builder.of(IronCrafticubeBlockEntity::new,
                            ModBlocks.IRON_CRAFTICUBE.get()).build(null)
            );
    public static final RegistryObject<BlockEntityType<IronCrafticubeBlockEntity>> GOLD_CRAFTICUBE_BE =
            BLOCK_ENTITIES.register("gold_crafticube_be", () ->
                    BlockEntityType.Builder.of(IronCrafticubeBlockEntity::new,
                            ModBlocks.GOLD_CRAFTICUBE.get()).build(null)
            );
    public static final RegistryObject<BlockEntityType<IronCrafticubeBlockEntity>> DIAMOND_CRAFTICUBE_BE =
            BLOCK_ENTITIES.register("diamond_crafticube_be", () ->
                    BlockEntityType.Builder.of(IronCrafticubeBlockEntity::new,
                            ModBlocks.DIAMOND_CRAFTICUBE.get()).build(null)
            );
    public static final RegistryObject<BlockEntityType<IronCrafticubeBlockEntity>> NETHERITE_CRAFTICUBE_BE =
            BLOCK_ENTITIES.register("netherite_crafticube_be", () ->
                    BlockEntityType.Builder.of(IronCrafticubeBlockEntity::new,
                            ModBlocks.NETHERITE_CRAFTICUBE.get()).build(null)
            );

    public static void register (IEventBus eventBus){
        BLOCK_ENTITIES.register(eventBus);
    }
}
