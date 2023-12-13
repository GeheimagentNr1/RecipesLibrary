package de.geheimagentnr1.recipes_lib.elements.recipes.nbt.shaped_nbt;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.geheimagentnr1.recipes_lib.elements.recipes.nbt.NBTRecipeSerializer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.ShapedRecipePattern;
import org.jetbrains.annotations.NotNull;


public class ShapedNBTRecipeSerializer extends NBTRecipeSerializer<ShapedNBTRecipe> {
	
	
	private static final Codec<ShapedNBTRecipe> CODEC =
		RecordCodecBuilder.create( builder -> builder.group(
			ExtraCodecs.strictOptionalField( Codec.STRING, "group", "" ).forGetter( ShapedNBTRecipe::getGroup ),
			ShapedRecipePattern.MAP_CODEC.forGetter( ShapedNBTRecipe::getPattern ),
			RESULT_CODEC.fieldOf( "result" ).forGetter( ShapedNBTRecipe::getNBTRecipeResult )
		).apply( builder, ShapedNBTRecipe::new ) );
	
	@NotNull
	@Override
	public Codec<ShapedNBTRecipe> codec() {
		
		return CODEC;
	}
	
	@NotNull
	@Override
	protected ShapedNBTRecipe buildRecipe(
		@NotNull FriendlyByteBuf buffer,
		@NotNull String group,
		@NotNull ItemStack result,
		boolean merge_nbt ) {
		
		return new ShapedNBTRecipe(
			group,
			ShapedRecipePattern.fromNetwork( buffer ),
			result,
			merge_nbt
		);
	}
	
	@Override
	protected void writeRecipeData( @NotNull FriendlyByteBuf buffer, @NotNull ShapedNBTRecipe recipe ) {
		
		recipe.getPattern().toNetwork( buffer );
	}
}
