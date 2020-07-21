package de.geheimagentnr1.recipes_lib.elements.recipes;

import de.geheimagentnr1.recipes_lib.RecipesLibrary;
import de.geheimagentnr1.recipes_lib.elements.recipes.nbt.shaped_nbt.ShapedNBTRecipe;
import de.geheimagentnr1.recipes_lib.elements.recipes.nbt.shaped_nbt.ShapedNBTRecipeSerializer;
import de.geheimagentnr1.recipes_lib.elements.recipes.nbt.shapless_nbt.ShapelessNBTRecipe;
import de.geheimagentnr1.recipes_lib.elements.recipes.nbt.shapless_nbt.ShapelessNBTRecipeSerializer;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;


@SuppressWarnings( { "StaticNonFinalField", "PublicField" } )
public class RecipeSerializers {
	
	
	//NBT
	public static IRecipeSerializer<ShapedNBTRecipe> SHAPED_NBT;
	
	public static IRecipeSerializer<ShapelessNBTRecipe> SHAPELESS_NBT;
	
	@SuppressWarnings( "deprecation" )
	private static <S extends IRecipeSerializer<T>, T extends IRecipe<?>> S register( String key,
		S recipeSerializer ) {
		
		return Registry.register( Registry.RECIPE_SERIALIZER, new ResourceLocation( RecipesLibrary.MODID, key ),
			recipeSerializer );
	}
	
	public static void init() {
		
		//NBT
		SHAPED_NBT = register( ShapedNBTRecipe.registry_name, new ShapedNBTRecipeSerializer() );
		SHAPELESS_NBT = register( ShapelessNBTRecipe.registry_name, new ShapelessNBTRecipeSerializer() );
	}
}
