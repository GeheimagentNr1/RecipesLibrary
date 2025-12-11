package de.geheimagentnr1.recipes_lib;

import de.geheimagentnr1.recipes_lib.elements.recipes.ModRecipeSerializersRegisterFactory;
import de.geheimagentnr1.recipes_lib.elements.recipes.ingredients.ModIngredientSerializersRegisterFactory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import org.jetbrains.annotations.NotNull;


@Mod( RecipesLibrary.MODID )
public class RecipesLibrary {
	
	
	@NotNull
	public static final String MODID = "recipes_lib";
	
	public RecipesLibrary( @NotNull IEventBus modEventBus ) {
		
		new ModIngredientSerializersRegisterFactory().register( modEventBus );
		modEventBus.register( new ModRecipeSerializersRegisterFactory() );
	}
}
