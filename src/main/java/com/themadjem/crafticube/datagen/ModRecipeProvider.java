package com.themadjem.crafticube.datagen;

import com.themadjem.crafticube.CraftiCube;
import com.themadjem.crafticube.block.ModBlocks;
import com.themadjem.crafticube.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.CRAFTING_CORE.get())
                .pattern("CTC")
                .pattern("TDT")
                .pattern("CTC")
                .define('C', Tags.Items.CHESTS)
                .define('T', Blocks.CAULDRON)
                .define('D', Tags.Items.GEMS_DIAMOND)
                .unlockedBy(getHasName(Items.DIAMOND), has(Tags.Items.GEMS_DIAMOND))
                .save(consumer);

        chestLikeUpgrade(consumer, ModItems.CRAFTING_CORE.get(), Items.IRON_INGOT, ModBlocks.IRON_CRAFTICUBE.get());
        chestLikeUpgrade(consumer, ModBlocks.IRON_CRAFTICUBE.get(), Items.GOLD_INGOT, ModBlocks.GOLD_CRAFTICUBE.get());
        chestLikeUpgrade(consumer, ModBlocks.GOLD_CRAFTICUBE.get(), Items.DIAMOND, ModBlocks.DIAMOND_CRAFTICUBE.get());

        netheriteSmithing(consumer, ModBlocks.IRON_CRAFTICUBE.get(), RecipeCategory.MISC, ModBlocks.NETHERITE_CRAFTICUBE.get());

    }

    protected static void chestLikeUpgrade(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ItemLike pCenterItem, ItemLike pUpgradeItem, ItemLike pResult) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, pResult)
                .pattern("UUU")
                .pattern("UCU")
                .pattern("UUU")
                .define('U', pUpgradeItem)
                .define('C', pCenterItem)
                .unlockedBy(getHasName(pUpgradeItem), has(pUpgradeItem))
                .save(pFinishedRecipeConsumer);
    }

    protected static void chestLikeUpgrade(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ItemLike pCenterItem, TagKey<Item> pUpgradeItem, ItemLike pResult) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, pResult)
                .pattern("UUU")
                .pattern("UCU")
                .pattern("UUU")
                .define('U', pUpgradeItem)
                .define('C', pCenterItem)
                //.unlockedBy(getHasName(pUpgradeItem),has(pUpgradeItem))
                .save(pFinishedRecipeConsumer);
    }

    protected static void oreSmelting(Consumer<FinishedRecipe> pFinishedRecipeConsumer,
                                      List<ItemLike> pIngredients,
                                      RecipeCategory pCategory,
                                      ItemLike pResult,
                                      float pExperience,
                                      int pCookingTIme,
                                      String pGroup) {
        oreCooking(pFinishedRecipeConsumer,
                RecipeSerializer.SMELTING_RECIPE,
                pIngredients,
                pCategory,
                pResult,
                pExperience,
                pCookingTIme,
                pGroup,
                "_from_smelting");
    }

    protected static void oreBlasting(Consumer<FinishedRecipe> pFinishedRecipeConsumer,
                                      List<ItemLike> pIngredients,
                                      RecipeCategory pCategory,
                                      ItemLike pResult,
                                      float pExperience,
                                      int pCookingTime,
                                      String pGroup) {
        oreCooking(pFinishedRecipeConsumer,
                RecipeSerializer.BLASTING_RECIPE,
                pIngredients,
                pCategory,
                pResult,
                pExperience,
                pCookingTime,
                pGroup,
                "_from_blasting");
    }

    protected static void oreCooking(Consumer<FinishedRecipe> pFinishedRecipeConsumer,
                                     RecipeSerializer<? extends AbstractCookingRecipe> pCookingSerializer,
                                     List<ItemLike> pIngredients,
                                     RecipeCategory pCategory,
                                     ItemLike pResult,
                                     float pExperience,
                                     int pCookingTime,
                                     String pGroup,
                                     String pRecipeName) {
        Iterator ingredientIterator = pIngredients.iterator();

        while (ingredientIterator.hasNext()) {
            ItemLike itemlike = (ItemLike) ingredientIterator.next();
            SimpleCookingRecipeBuilder.generic(Ingredient.of(new ItemLike[]{itemlike}),
                            pCategory,
                            pResult,
                            pExperience,
                            pCookingTime,
                            pCookingSerializer
                    )
                    .group(pGroup)
                    .unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(pFinishedRecipeConsumer,
                            CraftiCube.MOD_ID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike)
                    );
        }

    }

    protected static void netheriteSmithing(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ItemLike pIngredientItem,
                                            RecipeCategory pCategory, ItemLike pResultItem) {
        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(new ItemLike[]{Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE}),
                        Ingredient.of(new ItemLike[]{pIngredientItem}),
                        Ingredient.of(new ItemLike[]{Items.NETHERITE_INGOT}),
                        pCategory, pResultItem.asItem())
                .unlocks("has_netherite_ingot", has((ItemLike) Items.NETHERITE_INGOT))
                .save(pFinishedRecipeConsumer, CraftiCube.MOD_ID + ":" + getItemName(pResultItem) + "_smithing");
    }
}
