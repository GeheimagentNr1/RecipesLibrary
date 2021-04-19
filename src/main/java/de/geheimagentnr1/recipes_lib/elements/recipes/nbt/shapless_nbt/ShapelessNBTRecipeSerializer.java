package de.geheimagentnr1.recipes_lib.elements.recipes.nbt.shapless_nbt;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import de.geheimagentnr1.recipes_lib.elements.recipes.nbt.NBTRecipeFactory;
import de.geheimagentnr1.recipes_lib.elements.recipes.nbt.NBTRecipeSerializer;
import de.geheimagentnr1.recipes_lib.util.Pair;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;

import javax.annotation.Nonnull;


public class ShapelessNBTRecipeSerializer extends NBTRecipeSerializer<ShapelessNBTRecipe> {
	
	
	private static final ShapelessNBTRecipeFactory SHAPELESS_NBT_RECIPE_FACTORY = new ShapelessNBTRecipeFactory();
	
	public ShapelessNBTRecipeSerializer() {
		
		setRegistryName( ShapelessNBTRecipe.registry_name );
	}
	
	@Nonnull
	@Override
	protected Pair<NonNullList<Ingredient>, NBTRecipeFactory<ShapelessNBTRecipe>> readRecipeData(
		@Nonnull JsonObject json ) {
		
		NonNullList<Ingredient> ingredients = readIngredients( JSONUtils.getAsJsonArray( json, "ingredients" ) );
		if( ingredients.isEmpty() ) {
			throw new JsonParseException( "No ingredients for shapeless recipe" );
		}
		if( ingredients.size() > MAX_WIDTH * MAX_HEIGHT ) {
			throw new JsonParseException(
				"Too many ingredients for shapeless recipe the max is " + MAX_WIDTH * MAX_HEIGHT );
		}
		return new Pair<>( ingredients, SHAPELESS_NBT_RECIPE_FACTORY );
	}
	
	private NonNullList<Ingredient> readIngredients( JsonArray ingredientsJson ) {
		
		NonNullList<Ingredient> ingredients = NonNullList.create();
		
		for( int i = 0; i < ingredientsJson.size(); i++ ) {
			Ingredient ingredient = Ingredient.fromJson( ingredientsJson.get( i ) );
			if( !ingredient.isEmpty() ) {
				ingredients.add( ingredient );
			}
		}
		return ingredients;
	}
	
	@Nonnull
	@Override
	protected Pair<Integer, NBTRecipeFactory<ShapelessNBTRecipe>> readRecipeData( @Nonnull PacketBuffer buffer ) {
		
		return new Pair<>( buffer.readVarInt(), SHAPELESS_NBT_RECIPE_FACTORY );
	}
	
	@Override
	protected void writeRecipeData( @Nonnull PacketBuffer buffer, @Nonnull ShapelessNBTRecipe recipe ) {
		
		buffer.writeVarInt( recipe.getIngredients().size() );
	}
}
