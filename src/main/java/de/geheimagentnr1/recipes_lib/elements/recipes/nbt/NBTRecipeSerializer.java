package de.geheimagentnr1.recipes_lib.elements.recipes.nbt;

import com.google.gson.JsonObject;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import de.geheimagentnr1.recipes_lib.util.Pair;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;


public abstract class NBTRecipeSerializer<R extends NBTRecipe> extends ForgeRegistryEntry<IRecipeSerializer<?>>
	implements IRecipeSerializer<R> {
	
	
	protected static final int MAX_WIDTH = 3;
	
	protected static final int MAX_HEIGHT = 3;
	
	@Nonnull
	@Override
	public R read( @Nonnull ResourceLocation recipeId, @Nonnull JsonObject json ) {
		
		String group = JSONUtils.getString( json, "group", "" );
		Pair<NonNullList<Ingredient>, NBTRecipeFactory<R>> recipeData = readRecipeData( recipeId, json );
		JsonObject resultJson = JSONUtils.getJsonObject( json, "result" );
		ItemStack result = ShapedRecipe.deserializeItem( resultJson );
		try {
			result.getOrCreateTag().merge(
				JsonToNBT.getTagFromJson( JSONUtils.getString( resultJson, "nbt" ) ) );
		} catch( CommandSyntaxException exception ) {
			throw new IllegalStateException( exception );
		}
		boolean merge_nbt = JSONUtils.getBoolean( resultJson, "merge_nbt" );
		return recipeData.getValue().buildRecipe( recipeId, group, recipeData.getKey(), result, merge_nbt );
	}
	
	@Nonnull
	protected abstract Pair<NonNullList<Ingredient>, NBTRecipeFactory<R>> readRecipeData(
		@Nonnull ResourceLocation recipeId, @Nonnull JsonObject json );
	
	@Nullable
	@Override
	public R read( @Nonnull ResourceLocation recipeId, @Nonnull PacketBuffer buffer ) {
		
		Pair<Integer, NBTRecipeFactory<R>> recipeData = readRecipeData( recipeId, buffer );
		String group = buffer.readString();
		NonNullList<Ingredient> ingredients = NonNullList.withSize( recipeData.getKey(),
			Ingredient.EMPTY );
		for( int i = 0; i < ingredients.size(); i++ ) {
			ingredients.set( i, Ingredient.read( buffer ) );
		}
		ItemStack result = buffer.readItemStack();
		boolean merge_nbt = buffer.readBoolean();
		return recipeData.getValue().buildRecipe( recipeId, group, ingredients, result, merge_nbt );
	}
	
	@Nonnull
	protected abstract Pair<Integer, NBTRecipeFactory<R>> readRecipeData(
		@Nonnull ResourceLocation recipeId, @Nonnull PacketBuffer buffer );
	
	@Override
	public void write( @Nonnull PacketBuffer buffer, @Nonnull R recipe ) {
		
		writeRecipeData( buffer, recipe );
		buffer.writeString( recipe.getGroup() );
		for( Ingredient ingredient : recipe.getIngredients() ) {
			ingredient.write( buffer );
		}
		buffer.writeItemStack( recipe.getRecipeOutput() );
		buffer.writeBoolean( recipe.isMergeNbt() );
	}
	
	protected abstract void writeRecipeData( @Nonnull PacketBuffer buffer, @Nonnull R recipe );
}
