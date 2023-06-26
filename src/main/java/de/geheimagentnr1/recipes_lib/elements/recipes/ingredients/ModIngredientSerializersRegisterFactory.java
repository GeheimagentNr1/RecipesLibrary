package de.geheimagentnr1.recipes_lib.elements.recipes.ingredients;

import de.geheimagentnr1.minecraft_forge_api.AbstractMod;
import de.geheimagentnr1.minecraft_forge_api.elements.recipes.ingredients.IngredientSerializersRegisterFactory;
import de.geheimagentnr1.minecraft_forge_api.registry.RegistryEntry;
import de.geheimagentnr1.recipes_lib.elements.recipes.ingredients.nbt.NBTIngredient;
import de.geheimagentnr1.recipes_lib.elements.recipes.ingredients.nbt.NBTIngredientSerializer;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.IIngredientSerializer;
import org.jetbrains.annotations.NotNull;

import java.util.List;


public class ModIngredientSerializersRegisterFactory extends IngredientSerializersRegisterFactory {
	
	//NBT
	
	@NotNull
	public static final IIngredientSerializer<NBTIngredient> NBT_INGREDIENT = new NBTIngredientSerializer();
	
	public ModIngredientSerializersRegisterFactory( @NotNull AbstractMod abstractMod ) {
		
		super( abstractMod );
	}
	
	@NotNull
	@Override
	protected List<RegistryEntry<IIngredientSerializer<? extends Ingredient>>> elements() {
		
		return List.of(
			RegistryEntry.create( NBTIngredient.registry_name, NBT_INGREDIENT )
		);
	}
}
