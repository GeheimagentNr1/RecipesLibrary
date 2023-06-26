package de.geheimagentnr1.recipes_lib.elements.recipes.nbt;

import com.google.gson.JsonObject;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import de.geheimagentnr1.recipes_lib.util.Pair;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.TagParser;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;


public abstract class NBTRecipeSerializer<R extends NBTRecipe> implements RecipeSerializer<R> {
	
	
	protected static final int MAX_WIDTH = 3;
	
	protected static final int MAX_HEIGHT = 3;
	
	@NotNull
	@Override
	public R fromJson( @NotNull ResourceLocation recipeId, @NotNull JsonObject json ) {
		
		String group = GsonHelper.getAsString( json, "group", "" );
		Pair<NonNullList<Ingredient>, NBTRecipeFactory<R>> recipeData = readRecipeData( json );
		JsonObject resultJson = GsonHelper.getAsJsonObject( json, "result" );
		ItemStack result = ShapedRecipe.itemStackFromJson( resultJson );
		try {
			result.getOrCreateTag().merge( TagParser.parseTag( GsonHelper.getAsString( resultJson, "nbt" ) ) );
		} catch( CommandSyntaxException exception ) {
			throw new IllegalStateException( exception );
		}
		boolean merge_nbt = GsonHelper.getAsBoolean( resultJson, "merge_nbt" );
		return recipeData.getValue().buildRecipe( recipeId, group, recipeData.getKey(), result, merge_nbt );
	}
	
	@NotNull
	protected abstract Pair<NonNullList<Ingredient>, NBTRecipeFactory<R>> readRecipeData( @NotNull JsonObject json );
	
	@Nullable
	@Override
	public R fromNetwork( @NotNull ResourceLocation recipeId, @NotNull FriendlyByteBuf buffer ) {
		
		Pair<Integer, NBTRecipeFactory<R>> recipeData = readRecipeData( buffer );
		String group = buffer.readUtf();
		NonNullList<Ingredient> ingredients = NonNullList.withSize( recipeData.getKey(), Ingredient.EMPTY );
		ingredients.replaceAll( ignored -> Ingredient.fromNetwork( buffer ) );
		ItemStack result = buffer.readItem();
		boolean merge_nbt = buffer.readBoolean();
		return recipeData.getValue().buildRecipe( recipeId, group, ingredients, result, merge_nbt );
	}
	
	@NotNull
	protected abstract Pair<Integer, NBTRecipeFactory<R>> readRecipeData( @NotNull FriendlyByteBuf buffer );
	
	@Override
	public void toNetwork( @NotNull FriendlyByteBuf buffer, @NotNull R recipe ) {
		
		writeRecipeData( buffer, recipe );
		buffer.writeUtf( recipe.getGroup() );
		for( Ingredient ingredient : recipe.getIngredients() ) {
			ingredient.toNetwork( buffer );
		}
		buffer.writeItem( recipe.getResult() );
		buffer.writeBoolean( recipe.isMergeNbt() );
	}
	
	protected abstract void writeRecipeData( @NotNull FriendlyByteBuf buffer, @NotNull R recipe );
}
