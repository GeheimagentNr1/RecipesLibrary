package de.geheimagentnr1.recipes_lib.elements.recipes;

import de.geheimagentnr1.minecraft_forge_api.registry.ElementsRegisterFactory;
import de.geheimagentnr1.minecraft_forge_api.registry.RegistryEntry;
import de.geheimagentnr1.minecraft_forge_api.registry.RegistryKeys;
import de.geheimagentnr1.recipes_lib.RecipesLibrary;
import de.geheimagentnr1.recipes_lib.elements.recipes.components.shaped_nbt.ShapedComponentsRecipe;
import de.geheimagentnr1.recipes_lib.elements.recipes.components.shaped_nbt.ShapedComponentsRecipeSerializer;
import de.geheimagentnr1.recipes_lib.elements.recipes.components.shapless_nbt.ShapelessComponentsRecipe;
import de.geheimagentnr1.recipes_lib.elements.recipes.components.shapless_nbt.ShapelessComponentsRecipeSerializer;
import de.geheimagentnr1.recipes_lib.elements.recipes.renaming.RenamingRecipe;
import de.geheimagentnr1.recipes_lib.elements.recipes.renaming.RenamingRecipeSerializer;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;
import org.jetbrains.annotations.NotNull;

import java.util.List;


@SuppressWarnings( "StaticNonFinalField" )
public class ModRecipeSerializersRegisterFactory extends ElementsRegisterFactory<RecipeSerializer<?>> {
	
	//NBT
	
	@ObjectHolder( registryName = RegistryKeys.RECIPE_SERIALIZERS,
		value = RecipesLibrary.MODID + ":" + ShapedComponentsRecipe.registry_name )
	public static RecipeSerializer<ShapedComponentsRecipe> SHAPED_NBT;
	
	@ObjectHolder( registryName = RegistryKeys.RECIPE_SERIALIZERS,
		value = RecipesLibrary.MODID + ":" + ShapelessComponentsRecipe.registry_name )
	public static RecipeSerializer<ShapelessComponentsRecipe> SHAPELESS_NBT;
	
	//Renaming
	
	@ObjectHolder( registryName = RegistryKeys.RECIPE_SERIALIZERS,
		value = RecipesLibrary.MODID + ":" + RenamingRecipe.registry_name )
	public static RecipeSerializer<RenamingRecipe> RENAMING;
	
	@NotNull
	@Override
	protected ResourceKey<Registry<RecipeSerializer<?>>> registryKey() {
		
		return ForgeRegistries.Keys.RECIPE_SERIALIZERS;
	}
	
	@NotNull
	@Override
	protected List<RegistryEntry<RecipeSerializer<?>>> elements() {
		
		return List.of(
			//NBT
			RegistryEntry.create( ShapedComponentsRecipe.registry_name, new ShapedComponentsRecipeSerializer() ),
			RegistryEntry.create( ShapelessComponentsRecipe.registry_name, new ShapelessComponentsRecipeSerializer() ),
			//Renaming
			RegistryEntry.create( RenamingRecipe.registry_name, new RenamingRecipeSerializer() )
		);
	}
}
