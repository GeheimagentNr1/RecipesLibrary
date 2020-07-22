package de.geheimagentnr1.recipes_lib.elements.recipes.ingredients;

import de.geheimagentnr1.recipes_lib.RecipesLibrary;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.IIngredientSerializer;


@SuppressWarnings( "InterfaceWithOnlyOneDirectInheritor" )
public interface IngredientSerializer<I extends Ingredient> extends IIngredientSerializer<I> {
	
	//public
	default ResourceLocation getRegistryNameRL() {
		
		return new ResourceLocation( RecipesLibrary.MODID, getRegistryName() );
	}
	
	//public
	String getRegistryName();
}
