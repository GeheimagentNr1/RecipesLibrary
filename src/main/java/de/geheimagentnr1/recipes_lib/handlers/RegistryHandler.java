package de.geheimagentnr1.recipes_lib.handlers;

import de.geheimagentnr1.recipes_lib.elements.recipes.RecipeSerializers;
import de.geheimagentnr1.recipes_lib.elements.recipes.ingredients.IngredientSerializer;
import de.geheimagentnr1.recipes_lib.elements.recipes.ingredients.IngredientSerializers;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@SuppressWarnings( "unused" )
@Mod.EventBusSubscriber( bus = Mod.EventBusSubscriber.Bus.MOD )
public class RegistryHandler {
	
	
	@SubscribeEvent
	public static void registerRecipeSerialziers( RegistryEvent.Register<IRecipeSerializer<?>> event ) {
		
		for( IngredientSerializer<? extends Ingredient> ingredientSerializer : IngredientSerializers.INGREDIENTS ) {
			CraftingHelper.register( ingredientSerializer.getRegistryNameRL(), ingredientSerializer );
		}
		event.getRegistry().registerAll( RecipeSerializers.RECIPE_SERIALIZERS );
	}
}
