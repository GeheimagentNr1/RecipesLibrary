package de.geheimagentnr1.recipes_lib.elements.recipes.renaming;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class RenamingRecipeSerializer implements RecipeSerializer<RenamingRecipe> {
	
	
	private static final Codec<RenamingRecipe> CODEC = RecordCodecBuilder.create( ( builder ) -> builder.group(
		Ingredient.CODEC_NONEMPTY.fieldOf( "ingredient" ).forGetter( RenamingRecipe::getIngredient )
	).apply( builder, RenamingRecipe::new ) );
	
	@Override
	public Codec<RenamingRecipe> codec() {
		
		return CODEC;
	}
	
	@Nullable
	@Override
	public RenamingRecipe fromNetwork( @NotNull FriendlyByteBuf buffer ) {
		
		return new RenamingRecipe( Ingredient.fromNetwork( buffer ) );
	}
	
	@Override
	public void toNetwork( @NotNull FriendlyByteBuf buffer, @NotNull RenamingRecipe recipe ) {
		
		recipe.getIngredient().toNetwork( buffer );
	}
}
