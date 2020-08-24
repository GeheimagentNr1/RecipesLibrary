package de.geheimagentnr1.recipes_lib.elements.recipes;

import de.geheimagentnr1.recipes_lib.RecipesLibrary;
import de.geheimagentnr1.recipes_lib.elements.recipes.nbt.shaped_nbt.ShapedNBTRecipe;
import de.geheimagentnr1.recipes_lib.elements.recipes.nbt.shaped_nbt.ShapedNBTRecipeSerializer;
import de.geheimagentnr1.recipes_lib.elements.recipes.nbt.shapless_nbt.ShapelessNBTRecipe;
import de.geheimagentnr1.recipes_lib.elements.recipes.nbt.shapless_nbt.ShapelessNBTRecipeSerializer;
import de.geheimagentnr1.recipes_lib.elements.recipes.renaming.RenamingRecipe;
import de.geheimagentnr1.recipes_lib.elements.recipes.renaming.RenamingRecipeSerializer;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraftforge.registries.ObjectHolder;


@SuppressWarnings( { "StaticNonFinalField", "PublicField", "PublicStaticArrayField", "unused" } )
public class RecipeSerializers {
	
	
	public static final IRecipeSerializer<?>[] RECIPE_SERIALIZERS = new IRecipeSerializer[] {
		//NBT
		new ShapedNBTRecipeSerializer(),
		new ShapelessNBTRecipeSerializer(),
		//Renaming
		new RenamingRecipeSerializer(),
	};
	
	//NBT
	
	@ObjectHolder( RecipesLibrary.MODID + ":" + ShapedNBTRecipe.registry_name )
	public static IRecipeSerializer<ShapedNBTRecipe> SHAPED_NBT;
	
	@ObjectHolder( RecipesLibrary.MODID + ":" + ShapelessNBTRecipe.registry_name )
	public static IRecipeSerializer<ShapelessNBTRecipe> SHAPELESS_NBT;
	
	//Renaming
	
	@ObjectHolder( RecipesLibrary.MODID + ":" + RenamingRecipe.registry_name )
	public static IRecipeSerializer<RenamingRecipe> RENAMING;
}
