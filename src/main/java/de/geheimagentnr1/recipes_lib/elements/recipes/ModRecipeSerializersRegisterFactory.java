package de.geheimagentnr1.recipes_lib.elements.recipes;

import de.geheimagentnr1.recipes_lib.RecipesLibrary;
import de.geheimagentnr1.recipes_lib.elements.recipes.components.shaped_nbt.ShapedComponentsRecipe;
import de.geheimagentnr1.recipes_lib.elements.recipes.components.shaped_nbt.ShapedComponentsRecipeSerializer;
import de.geheimagentnr1.recipes_lib.elements.recipes.components.shapless_nbt.ShapelessComponentsRecipe;
import de.geheimagentnr1.recipes_lib.elements.recipes.components.shapless_nbt.ShapelessComponentsRecipeSerializer;
import de.geheimagentnr1.recipes_lib.elements.recipes.renaming.RenamingRecipe;
import de.geheimagentnr1.recipes_lib.elements.recipes.renaming.RenamingRecipeSerializer;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.registries.RegisterEvent;


@SuppressWarnings( "StaticNonFinalField" )
public class ModRecipeSerializersRegisterFactory {
	
	//NBT
	
	public static RecipeSerializer<ShapedComponentsRecipe> SHAPED_NBT;
	
	public static RecipeSerializer<ShapelessComponentsRecipe> SHAPELESS_NBT;
	
	//Renaming
	
	public static RecipeSerializer<RenamingRecipe> RENAMING;
	
	@SubscribeEvent
	public void register( RegisterEvent event ) {
		
		event.register( Registries.RECIPE_SERIALIZER, helper -> {
			SHAPED_NBT = new ShapedComponentsRecipeSerializer();
			helper.register( ResourceLocation.fromNamespaceAndPath( RecipesLibrary.MODID, ShapedComponentsRecipe.registry_name ), SHAPED_NBT );
			
			SHAPELESS_NBT = new ShapelessComponentsRecipeSerializer();
			helper.register( ResourceLocation.fromNamespaceAndPath( RecipesLibrary.MODID, ShapelessComponentsRecipe.registry_name ), SHAPELESS_NBT );
			
			RENAMING = new RenamingRecipeSerializer();
			helper.register( ResourceLocation.fromNamespaceAndPath( RecipesLibrary.MODID, RenamingRecipe.registry_name ), RENAMING );
		} );
	}
}
