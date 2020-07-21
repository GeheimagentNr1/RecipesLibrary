package de.geheimagentnr1.recipes_lib;

import de.geheimagentnr1.recipes_lib.elements.recipes.RecipeSerializers;
import net.minecraftforge.fml.common.Mod;


@Mod( RecipesLibrary.MODID )
public class RecipesLibrary {
	
	
	public static final String MODID = "recipes_lib";
	
	static {
		
		RecipeSerializers.init();
	}
}
