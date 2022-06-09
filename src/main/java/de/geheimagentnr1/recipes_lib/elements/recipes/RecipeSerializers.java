package de.geheimagentnr1.recipes_lib.elements.recipes;

import de.geheimagentnr1.recipes_lib.RecipesLibrary;
import de.geheimagentnr1.recipes_lib.elements.RegistryEntry;
import de.geheimagentnr1.recipes_lib.elements.recipes.nbt.shaped_nbt.ShapedNBTRecipe;
import de.geheimagentnr1.recipes_lib.elements.recipes.nbt.shaped_nbt.ShapedNBTRecipeSerializer;
import de.geheimagentnr1.recipes_lib.elements.recipes.nbt.shapless_nbt.ShapelessNBTRecipe;
import de.geheimagentnr1.recipes_lib.elements.recipes.nbt.shapless_nbt.ShapelessNBTRecipeSerializer;
import de.geheimagentnr1.recipes_lib.elements.recipes.renaming.RenamingRecipe;
import de.geheimagentnr1.recipes_lib.elements.recipes.renaming.RenamingRecipeSerializer;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.ObjectHolder;

import java.util.List;


@SuppressWarnings( "StaticNonFinalField" )
public class RecipeSerializers {
	
	
	public static final List<RegistryEntry<RecipeSerializer<?>>> RECIPE_SERIALIZERS = List.of(
		//NBT
		RegistryEntry.create( ShapedNBTRecipe.registry_name, new ShapedNBTRecipeSerializer() ),
		RegistryEntry.create( ShapelessNBTRecipe.registry_name, new ShapelessNBTRecipeSerializer() ),
		//Renaming
		RegistryEntry.create( RenamingRecipe.registry_name, new RenamingRecipeSerializer() )
	);
	
	//NBT
	
	@ObjectHolder( registryName = "minecraft:recipe_serializer",
		value = RecipesLibrary.MODID + ":" + ShapedNBTRecipe.registry_name )
	public static RecipeSerializer<ShapedNBTRecipe> SHAPED_NBT;
	
	@ObjectHolder( registryName = "minecraft:recipe_serializer",
		value = RecipesLibrary.MODID + ":" + ShapelessNBTRecipe.registry_name )
	public static RecipeSerializer<ShapelessNBTRecipe> SHAPELESS_NBT;
	
	//Renaming
	
	@ObjectHolder( registryName = "minecraft:recipe_serializer",
		value = RecipesLibrary.MODID + ":" + RenamingRecipe.registry_name )
	public static RecipeSerializer<RenamingRecipe> RENAMING;
}
