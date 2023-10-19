package de.geheimagentnr1.recipes_lib;

import de.geheimagentnr1.minecraft_forge_api.AbstractMod;
import de.geheimagentnr1.recipes_lib.elements.recipes.ModRecipeSerializersRegisterFactory;
import de.geheimagentnr1.recipes_lib.elements.recipes.ingredients.ModIngredientSerializersRegisterFactory;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;


@Mod( RecipesLibrary.MODID )
public class RecipesLibrary extends AbstractMod {
	
	
	@NotNull
	public static final String MODID = "recipes_lib";
	
	@NotNull
	@Override
	public String getModId() {
		
		return MODID;
	}
	
	@Override
	protected void initMod() {
		
		registerEventHandler( new ModIngredientSerializersRegisterFactory() );
		registerEventHandler( new ModRecipeSerializersRegisterFactory() );
	}
}
