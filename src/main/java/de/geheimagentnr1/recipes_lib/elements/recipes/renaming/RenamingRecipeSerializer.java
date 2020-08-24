package de.geheimagentnr1.recipes_lib.elements.recipes.renaming;

import com.google.gson.JsonObject;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;


public class RenamingRecipeSerializer extends ForgeRegistryEntry<IRecipeSerializer<?>>
	implements IRecipeSerializer<RenamingRecipe> {
	
	
	public RenamingRecipeSerializer() {
		
		setRegistryName( RenamingRecipe.registry_name );
	}
	
	@Nonnull
	@Override
	public RenamingRecipe read( @Nonnull ResourceLocation recipeId, @Nonnull JsonObject json ) {
		
		return new RenamingRecipe( recipeId, Ingredient.deserialize( JSONUtils.getJsonObject( json, "ingredient" ) ) );
	}
	
	@Nullable
	@Override
	public RenamingRecipe read( @Nonnull ResourceLocation recipeId, @Nonnull PacketBuffer buffer ) {
		
		return new RenamingRecipe( recipeId, Ingredient.read( buffer ) );
	}
	
	@Override
	public void write( @Nonnull PacketBuffer buffer, @Nonnull RenamingRecipe recipe ) {
		
		recipe.getIngredient().write( buffer );
	}
}
