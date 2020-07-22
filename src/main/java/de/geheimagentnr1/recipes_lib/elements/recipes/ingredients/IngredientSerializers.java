package de.geheimagentnr1.recipes_lib.elements.recipes.ingredients;

import de.geheimagentnr1.recipes_lib.elements.recipes.ingredients.nbt.NBTIngredient;
import de.geheimagentnr1.recipes_lib.elements.recipes.ingredients.nbt.NBTIngredientSerializer;
import net.minecraft.item.crafting.Ingredient;


@SuppressWarnings( { "PublicStaticArrayField", "PublicField", "unchecked" } )
public class IngredientSerializers {
	
	//NBT
	
	public static final IngredientSerializer<NBTIngredient> NBT_INGREDIENT = new NBTIngredientSerializer();
	
	public static final IngredientSerializer<? extends Ingredient>[] INGREDIENTS = new IngredientSerializer[] {
		//NBT
		NBT_INGREDIENT,
	};
}
