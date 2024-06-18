package com.themadjem.crafticube.datagen;

import com.themadjem.crafticube.CraftiCube;
import com.themadjem.crafticube.block.ModBlocks;
import com.themadjem.crafticube.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pRegistries) {
        super(pOutput, pRegistries);
    }

    @Override
    protected void buildRecipes(RecipeOutput pRecipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.CRAFTING_CORE.get())
                .pattern("CTC")
                .pattern("TDT")
                .pattern("CTC")
                .define('C', Tags.Items.CHESTS)
                .define('T', Blocks.CAULDRON)
                .define('D', Tags.Items.GEMS_DIAMOND)
                .unlockedBy(getHasName(Items.DIAMOND), has(Tags.Items.GEMS_DIAMOND))
                .save(pRecipeOutput);

        chestLikeUpgrade(pRecipeOutput, ModItems.CRAFTING_CORE.get(), Items.IRON_INGOT, ModBlocks.IRON_CRAFTICUBE.get());
        chestLikeUpgrade(pRecipeOutput, ModBlocks.IRON_CRAFTICUBE.get(), Items.GOLD_INGOT, ModBlocks.GOLD_CRAFTICUBE.get());
        chestLikeUpgrade(pRecipeOutput, ModBlocks.GOLD_CRAFTICUBE.get(), Items.DIAMOND, ModBlocks.DIAMOND_CRAFTICUBE.get());
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.NETHERITE_CRAFTICUBE.get())
                .requires(ModBlocks.DIAMOND_CRAFTICUBE.get())
                .requires(Items.NETHERITE_INGOT)
                .unlockedBy(getHasName(Items.NETHERITE_INGOT), has(Items.NETHERITE_INGOT))
                .save(pRecipeOutput);
        //netheriteSmithing(pRecipeOutput, ModBlocks.IRON_CRAFTICUBE.get(), RecipeCategory.MISC, ModBlocks.NETHERITE_CRAFTICUBE.get());

    }

    protected static void chestLikeUpgrade(RecipeOutput pRecipeOutput, ItemLike pCenterItem, ItemLike pUpgradeItem, ItemLike pResult) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, pResult)
                .pattern("UUU")
                .pattern("UCU")
                .pattern("UUU")
                .define('U', pUpgradeItem)
                .define('C', pCenterItem)
                .unlockedBy(getHasName(pUpgradeItem), has(pUpgradeItem))
                .save(pRecipeOutput);
    }

    protected static void netheriteSmithing(RecipeOutput pRecipeOutput, ItemLike pIngredientItem,
                                            RecipeCategory pCategory, ItemLike pResultItem) {
        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE),
                        Ingredient.of(pIngredientItem),
                        Ingredient.of(Items.NETHERITE_INGOT),
                        pCategory, pResultItem.asItem())
                .unlocks("has_netherite_ingot", has(Items.NETHERITE_INGOT))
                .save(pRecipeOutput, CraftiCube.MOD_ID + ":" + getItemName(pResultItem) + "_smithing");
    }

}
