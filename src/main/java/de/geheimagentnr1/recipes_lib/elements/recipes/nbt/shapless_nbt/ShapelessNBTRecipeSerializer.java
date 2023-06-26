package de.geheimagentnr1.recipes_lib.elements.recipes.nbt.shapless_nbt;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import de.geheimagentnr1.recipes_lib.elements.recipes.nbt.NBTRecipeFactory;
import de.geheimagentnr1.recipes_lib.elements.recipes.nbt.NBTRecipeSerializer;
import de.geheimagentnr1.recipes_lib.util.Pair;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;


public class ShapelessNBTRecipeSerializer extends NBTRecipeSerializer<ShapelessNBTRecipe> {
	
	
	@NotNull
	private static final ShapelessNBTRecipeFactory SHAPELESS_NBT_RECIPE_FACTORY = new ShapelessNBTRecipeFactory();
	
	@NotNull
	@Override
	protected Pair<NonNullList<Ingredient>, NBTRecipeFactory<ShapelessNBTRecipe>> readRecipeData(
		@NotNull JsonObject json ) {
		
		NonNullList<Ingredient> ingredients = readIngredients( GsonHelper.getAsJsonArray( json, "ingredients" ) );
		if( ingredients.isEmpty() ) {
			throw new JsonParseException( "No ingredients for shapeless recipe" );
		}
		if( ingredients.size() > MAX_WIDTH * MAX_HEIGHT ) {
			throw new JsonParseException(
				"Too many ingredients for shapeless recipe the max is " + MAX_WIDTH * MAX_HEIGHT );
		}
		return new Pair<>( ingredients, SHAPELESS_NBT_RECIPE_FACTORY );
	}
	
	@NotNull
	private NonNullList<Ingredient> readIngredients( @NotNull JsonArray ingredientsJson ) {
		
		NonNullList<Ingredient> ingredients = NonNullList.create();
		
		for( int i = 0; i < ingredientsJson.size(); i++ ) {
			Ingredient ingredient = Ingredient.fromJson( ingredientsJson.get( i ) );
			if( !ingredient.isEmpty() ) {
				ingredients.add( ingredient );
			}
		}
		return ingredients;
	}
	
	@NotNull
	@Override
	protected Pair<Integer, NBTRecipeFactory<ShapelessNBTRecipe>> readRecipeData( @NotNull FriendlyByteBuf buffer ) {
		
		return new Pair<>( buffer.readVarInt(), SHAPELESS_NBT_RECIPE_FACTORY );
	}
	
	@Override
	protected void writeRecipeData( @NotNull FriendlyByteBuf buffer, @NotNull ShapelessNBTRecipe recipe ) {
		
		buffer.writeVarInt( recipe.getIngredients().size() );
	}
}
