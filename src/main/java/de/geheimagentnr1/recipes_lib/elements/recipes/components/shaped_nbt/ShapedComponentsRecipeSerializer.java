package de.geheimagentnr1.recipes_lib.elements.recipes.components.shaped_nbt;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.geheimagentnr1.recipes_lib.elements.recipes.components.ComponentsRecipeSerializer;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.ShapedRecipePattern;
import org.jetbrains.annotations.NotNull;


public class ShapedComponentsRecipeSerializer extends ComponentsRecipeSerializer<ShapedComponentsRecipe> {
	
	
	private static final MapCodec<ShapedComponentsRecipe> CODEC =
		RecordCodecBuilder.mapCodec( builder -> builder.group(
			Codec.STRING.fieldOf( "group" ).orElse( "" ).forGetter( ShapedComponentsRecipe::getGroup ),
			ShapedRecipePattern.MAP_CODEC.forGetter( ShapedComponentsRecipe::getPattern ),
			RESULT_CODEC.fieldOf( "result" ).forGetter( ShapedComponentsRecipe::getNBTRecipeResult )
		).apply( builder, ShapedComponentsRecipe::new ) );
	
	@NotNull
	@Override
	public MapCodec<ShapedComponentsRecipe> codec() {
		
		return CODEC;
	}
	
	@NotNull
	@Override
	protected ShapedComponentsRecipe buildRecipe(
		@NotNull RegistryFriendlyByteBuf buffer,
		@NotNull String group,
		@NotNull ItemStack result,
		boolean merge_components ) {
		
		return new ShapedComponentsRecipe(
			group,
			ShapedRecipePattern.STREAM_CODEC.decode( buffer ),
			result,
			merge_components
		);
	}
	
	@Override
	protected void writeRecipeData( @NotNull RegistryFriendlyByteBuf buffer, @NotNull ShapedComponentsRecipe recipe ) {
		
		ShapedRecipePattern.STREAM_CODEC.encode( buffer, recipe.getPattern() );
	}
}
