package de.geheimagentnr1.recipes_lib.handlers;

import de.geheimagentnr1.recipes_lib.RecipesLibrary;
import de.geheimagentnr1.recipes_lib.elements.RegistryEntry;
import de.geheimagentnr1.recipes_lib.elements.recipes.RecipeSerializers;
import de.geheimagentnr1.recipes_lib.elements.recipes.ingredients.IngredientSerializer;
import de.geheimagentnr1.recipes_lib.elements.recipes.ingredients.IngredientSerializers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;


@Mod.EventBusSubscriber( modid = RecipesLibrary.MODID, bus = Mod.EventBusSubscriber.Bus.MOD )
public class ModEventHandler {
	
	
	@SubscribeEvent
	public static void handleRegisterRecipeSerialzierEvent( RegisterEvent event ) {
		
		if( event.getRegistryKey().equals( ForgeRegistries.Keys.RECIPE_SERIALIZERS ) ) {
			for( IngredientSerializer<? extends Ingredient> ingredientSerializer :
				IngredientSerializers.INGREDIENTS ) {
				CraftingHelper.register( ingredientSerializer.getRegistryNameRL(), ingredientSerializer );
			}
			
			event.register(
				ForgeRegistries.Keys.RECIPE_SERIALIZERS,
				registerHelper -> RecipeSerializers.RECIPE_SERIALIZERS.forEach( registryEntry -> registerHelper.register(
					registryEntry.getRegistryName(),
					registryEntry.getValue()
				) )
			);
		}
	}
}
