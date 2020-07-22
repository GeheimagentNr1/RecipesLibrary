package de.geheimagentnr1.recipes_lib.handlers;

import de.geheimagentnr1.recipes_lib.elements.recipes.RecipeSerializers;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber( bus = Mod.EventBusSubscriber.Bus.MOD )
public class RegistryHandler {
	
	
	@SubscribeEvent
	public static void registerRecipeSerialziers( RegistryEvent.Register<IRecipeSerializer<?>> event ) {
		
		event.getRegistry().registerAll( RecipeSerializers.RECIPE_SERIALIZERS );
		
	}
}
