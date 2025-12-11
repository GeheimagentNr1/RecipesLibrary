package de.geheimagentnr1.recipes_lib.elements.recipes.ingredients;

import de.geheimagentnr1.recipes_lib.RecipesLibrary;
import de.geheimagentnr1.recipes_lib.elements.recipes.ingredients.components.ComponentsIngredient;
import de.geheimagentnr1.recipes_lib.elements.recipes.ingredients.components.ComponentsIngredientCodec;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.crafting.IngredientType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;


public class ModIngredientSerializersRegisterFactory {
	
	@NotNull
	public static final DeferredRegister<IngredientType<?>> INGREDIENT_TYPES =
		DeferredRegister.create( NeoForgeRegistries.Keys.INGREDIENT_TYPES, RecipesLibrary.MODID );
	
	@NotNull
	public static final Supplier<IngredientType<ComponentsIngredient>> COMPONENTS_INGREDIENT =
		INGREDIENT_TYPES.register( ComponentsIngredient.registry_name,
			() -> new IngredientType<>( ComponentsIngredientCodec.CODEC, ComponentsIngredientCodec.STREAM_CODEC ) );
	
	public void register( @NotNull IEventBus modEventBus ) {
		
		INGREDIENT_TYPES.register( modEventBus );
	}
}
