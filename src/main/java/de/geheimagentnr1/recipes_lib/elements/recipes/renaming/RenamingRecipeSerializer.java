package de.geheimagentnr1.recipes_lib.elements.recipes.renaming;

import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;


public class RenamingRecipeSerializer implements RecipeSerializer<RenamingRecipe> {
	
	
	@NotNull
	@Override
	public RenamingRecipe fromJson( @NotNull ResourceLocation recipeId, @NotNull JsonObject json ) {
		
		return new RenamingRecipe( recipeId, Ingredient.fromJson( GsonHelper.getAsJsonObject( json, "ingredient" ) ) );
	}
	
	@Nullable
	@Override
	public RenamingRecipe fromNetwork( @NotNull ResourceLocation recipeId, @NotNull FriendlyByteBuf buffer ) {
		
		return new RenamingRecipe( recipeId, Ingredient.fromNetwork( buffer ) );
	}
	
	@Override
	public void toNetwork( @NotNull FriendlyByteBuf buffer, @NotNull RenamingRecipe recipe ) {
		
		recipe.getIngredient().toNetwork( buffer );
	}
}
