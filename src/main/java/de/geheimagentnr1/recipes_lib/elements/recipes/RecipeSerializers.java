package de.geheimagentnr1.recipes_lib.elements.recipes;

import de.geheimagentnr1.recipes_lib.RecipesLibrary;
import de.geheimagentnr1.recipes_lib.elements.recipes.nbt.shaped_nbt.ShapedNBTRecipe;
import de.geheimagentnr1.recipes_lib.elements.recipes.nbt.shaped_nbt.ShapedNBTRecipeSerializer;
import de.geheimagentnr1.recipes_lib.elements.recipes.nbt.shapless_nbt.ShapelessNBTRecipe;
import de.geheimagentnr1.recipes_lib.elements.recipes.nbt.shapless_nbt.ShapelessNBTRecipeSerializer;
import de.geheimagentnr1.recipes_lib.elements.recipes.renaming.RenamingRecipe;
import de.geheimagentnr1.recipes_lib.elements.recipes.renaming.RenamingRecipeSerializer;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.ObjectHolder;


@SuppressWarnings( { "PublicStaticArrayField", "StaticNonFinalField" } )
public class RecipeSerializers {
	
	
	public static final RecipeSerializer<?>[] RECIPE_SERIALIZERS = new RecipeSerializer[] {
		//NBT
		new ShapedNBTRecipeSerializer(),
		new ShapelessNBTRecipeSerializer(),
		//Renaming
		new RenamingRecipeSerializer(),
	};
	
	//NBT
	
	@ObjectHolder( RecipesLibrary.MODID + ":" + ShapedNBTRecipe.registry_name )
	public static RecipeSerializer<ShapedNBTRecipe> SHAPED_NBT;
	
	@ObjectHolder( RecipesLibrary.MODID + ":" + ShapelessNBTRecipe.registry_name )
	public static RecipeSerializer<ShapelessNBTRecipe> SHAPELESS_NBT;
	
	//Renaming
	
	@ObjectHolder( RecipesLibrary.MODID + ":" + RenamingRecipe.registry_name )
	public static RecipeSerializer<RenamingRecipe> RENAMING;
}
