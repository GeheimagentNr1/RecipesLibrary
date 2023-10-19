package de.geheimagentnr1.recipes_lib.elements.recipes.nbt.shapless_nbt;

import de.geheimagentnr1.recipes_lib.elements.recipes.nbt.RawNBTRecipeResult;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;


public record RawShapelessNBTRecipe(
	@NotNull String group,
	@NotNull NonNullList<Ingredient> ingredients,
	@NotNull RawNBTRecipeResult result) {
	
	
}
