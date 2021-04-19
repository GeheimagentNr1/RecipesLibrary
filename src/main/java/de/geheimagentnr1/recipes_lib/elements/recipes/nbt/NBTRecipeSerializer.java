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
	public R fromJson( @Nonnull ResourceLocation recipeId, @Nonnull JsonObject json ) {
		
		String group = JSONUtils.getAsString( json, "group", "" );
		Pair<NonNullList<Ingredient>, NBTRecipeFactory<R>> recipeData = readRecipeData( json );
		JsonObject resultJson = JSONUtils.getAsJsonObject( json, "result" );
		ItemStack result = ShapedRecipe.itemFromJson( resultJson );
		try {
			result.getOrCreateTag().merge( JsonToNBT.parseTag( JSONUtils.getAsString( resultJson, "nbt" ) ) );
		} catch( CommandSyntaxException exception ) {
			throw new IllegalStateException( exception );
		}
		boolean merge_nbt = JSONUtils.getAsBoolean( resultJson, "merge_nbt" );
		return recipeData.getValue().buildRecipe( recipeId, group, recipeData.getKey(), result, merge_nbt );
	}
	
	@Nonnull
	protected abstract Pair<NonNullList<Ingredient>, NBTRecipeFactory<R>> readRecipeData( @Nonnull JsonObject json );
	
	@Nullable
	@Override
	public R fromNetwork( @Nonnull ResourceLocation recipeId, @Nonnull PacketBuffer buffer ) {
		
		Pair<Integer, NBTRecipeFactory<R>> recipeData = readRecipeData( buffer );
		String group = buffer.readUtf();
		NonNullList<Ingredient> ingredients = NonNullList.withSize( recipeData.getKey(), Ingredient.EMPTY );
		for( int i = 0; i < ingredients.size(); i++ ) {
			ingredients.set( i, Ingredient.fromNetwork( buffer ) );
		}
		ItemStack result = buffer.readItem();
		boolean merge_nbt = buffer.readBoolean();
		return recipeData.getValue().buildRecipe( recipeId, group, ingredients, result, merge_nbt );
	}
	
	@Nonnull
	protected abstract Pair<Integer, NBTRecipeFactory<R>> readRecipeData( @Nonnull PacketBuffer buffer );
	
	@Override
	public void toNetwork( @Nonnull PacketBuffer buffer, @Nonnull R recipe ) {
		
		writeRecipeData( buffer, recipe );
		buffer.writeUtf( recipe.getGroup() );
		for( Ingredient ingredient : recipe.getIngredients() ) {
			ingredient.toNetwork( buffer );
		}
		buffer.writeItem( recipe.getResultItem() );
		buffer.writeBoolean( recipe.isMergeNbt() );
	}
	
	protected abstract void writeRecipeData( @Nonnull PacketBuffer buffer, @Nonnull R recipe );
}
