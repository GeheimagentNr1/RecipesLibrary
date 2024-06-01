package de.geheimagentnr1.recipes_lib.elements.recipes.ingredients;

import de.geheimagentnr1.minecraft_forge_api.registry.ElementsRegisterFactory;
import de.geheimagentnr1.minecraft_forge_api.registry.RegistryEntry;
import de.geheimagentnr1.recipes_lib.elements.recipes.ingredients.components.ComponentsIngredient;
import de.geheimagentnr1.recipes_lib.elements.recipes.ingredients.components.ComponentsIngredientSerializer;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraftforge.common.crafting.ingredients.IIngredientSerializer;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.List;


public class ModIngredientSerializersRegisterFactory extends ElementsRegisterFactory<IIngredientSerializer<?>> {
	
	//NBT
	
	@NotNull
	public static final IIngredientSerializer<ComponentsIngredient> COMPONENTS_INGREDIENT = new ComponentsIngredientSerializer();
	
	@Override
	protected @NotNull ResourceKey<Registry<IIngredientSerializer<?>>> registryKey() {
		
		return ForgeRegistries.Keys.INGREDIENT_SERIALIZERS;
	}
	
	@NotNull
	@Override
	protected List<RegistryEntry<IIngredientSerializer<?>>> elements() {
		
		return List.of(
			RegistryEntry.create( ComponentsIngredient.registry_name, COMPONENTS_INGREDIENT )
		);
	}
}
