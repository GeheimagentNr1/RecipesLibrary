package de.geheimagentnr1.recipes_lib.elements.recipes.renaming;

import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;


public class RenamingRecipeSerializer implements RecipeSerializer<RenamingRecipe> {
	
	
	@Nonnull
	@Override
	public RenamingRecipe fromJson( @Nonnull ResourceLocation recipeId, @Nonnull JsonObject json ) {
		
		return new RenamingRecipe( recipeId, Ingredient.fromJson( GsonHelper.getAsJsonObject( json, "ingredient" ) ) );
	}
	
	@Nullable
	@Override
	public RenamingRecipe fromNetwork( @Nonnull ResourceLocation recipeId, @Nonnull FriendlyByteBuf buffer ) {
		
		return new RenamingRecipe( recipeId, Ingredient.fromNetwork( buffer ) );
	}
	
	@Override
	public void toNetwork( @Nonnull FriendlyByteBuf buffer, @Nonnull RenamingRecipe recipe ) {
		
		recipe.getIngredient().toNetwork( buffer );
	}
}
