package de.geheimagentnr1.recipes_lib.elements.recipes.nbt;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.geheimagentnr1.recipes_lib.util.Pair;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.TagParser;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public abstract class NBTRecipeSerializer<R extends NBTRecipe> implements RecipeSerializer<R> {
	
	
	private static final Codec<Item> ITEM_NONAIR_CODEC = ExtraCodecs.validate(
		BuiltInRegistries.ITEM.byNameCodec(),
		builder -> builder == Items.AIR ?
			DataResult.error( () -> "Crafting result must not be minecraft:air" ) :
			DataResult.success( builder )
	);
	
	protected static final Codec<NBTRecipeResult> RESULT_CODEC =
		RecordCodecBuilder.create( builder -> builder.group(
			ITEM_NONAIR_CODEC.fieldOf( "item" ).forGetter( NBTRecipeResult::item ),
			TagParser.AS_CODEC.fieldOf( "nbt" ).forGetter( NBTRecipeResult::nbt ),
			ExtraCodecs.strictOptionalField( ExtraCodecs.POSITIVE_INT, "count", 1 )
				.forGetter( NBTRecipeResult::count ),
			ExtraCodecs.strictOptionalField( Codec.BOOL, "merge_nbt", true )
				.forGetter( NBTRecipeResult::mergeNbt )
		).apply( builder, NBTRecipeResult::new ) );
	
	@Nullable
	@Override
	public R fromNetwork( @NotNull FriendlyByteBuf buffer ) {
		
		String group = buffer.readUtf();
		ItemStack result = buffer.readItem();
		boolean merge_nbt = buffer.readBoolean();
		return buildRecipe( buffer, group, result, merge_nbt );
	}
	
	@NotNull
	protected abstract R buildRecipe( @NotNull FriendlyByteBuf buffer,
	                                  @NotNull String group,
	                                  @NotNull ItemStack result,
	                                  boolean merge_nbt );
	
	@Override
	public void toNetwork( @NotNull FriendlyByteBuf buffer, @NotNull R recipe ) {
		
		buffer.writeUtf( recipe.getGroup() );
		buffer.writeItem( recipe.getResult() );
		buffer.writeBoolean( recipe.isMergeNbt() );
		writeRecipeData( buffer, recipe );
	}
	
	protected abstract void writeRecipeData( @NotNull FriendlyByteBuf buffer, @NotNull R recipe );
}
