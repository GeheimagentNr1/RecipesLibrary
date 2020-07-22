package de.geheimagentnr1.recipes_lib.elements.recipes;

import de.geheimagentnr1.recipes_lib.RecipesLibrary;
import de.geheimagentnr1.recipes_lib.elements.recipes.nbt.shaped_nbt.ShapedNBTRecipe;
import de.geheimagentnr1.recipes_lib.elements.recipes.nbt.shaped_nbt.ShapedNBTRecipeSerializer;
import de.geheimagentnr1.recipes_lib.elements.recipes.nbt.shapless_nbt.ShapelessNBTRecipe;
import de.geheimagentnr1.recipes_lib.elements.recipes.nbt.shapless_nbt.ShapelessNBTRecipeSerializer;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraftforge.registries.ObjectHolder;


@SuppressWarnings( { "StaticNonFinalField", "PublicField", "PublicStaticArrayField" } )
public class RecipeSerializers {
	
	
	public static final IRecipeSerializer<?>[] RECIPE_SERIALIZERS = new IRecipeSerializer[] {
		//NBT
		new ShapedNBTRecipeSerializer(),
		new ShapelessNBTRecipeSerializer(),
	};
	
	//NBT
	
	@ObjectHolder( RecipesLibrary.MODID + ":" + ShapedNBTRecipe.registry_name )
	public static IRecipeSerializer<ShapedNBTRecipe> SHAPED_NBT;
	
	@ObjectHolder( RecipesLibrary.MODID + ":" + ShapelessNBTRecipe.registry_name )
	public static IRecipeSerializer<ShapelessNBTRecipe> SHAPELESS_NBT;
}
