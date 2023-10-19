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
	
	
	protected static final int MAX_WIDTH = 3;
	
	protected static final int MAX_HEIGHT = 3;
	
	private static final Codec<Item> ITEM_NONAIR_CODEC = ExtraCodecs.validate(
		BuiltInRegistries.ITEM.byNameCodec(),
		builder -> builder == Items.AIR ?
			DataResult.error( () -> "Crafting result must not be minecraft:air" ) :
			DataResult.success( builder )
	);
	
	protected static final Codec<RawNBTRecipeResult> RESULT_CODEC =
		RecordCodecBuilder.create( builder -> builder.group(
			ITEM_NONAIR_CODEC.fieldOf( "item" ).forGetter( RawNBTRecipeResult::item ),
			TagParser.AS_CODEC.fieldOf( "nbt" ).forGetter( RawNBTRecipeResult::nbt ),
			ExtraCodecs.strictOptionalField( ExtraCodecs.POSITIVE_INT, "count", 1 )
				.forGetter( RawNBTRecipeResult::count ),
			ExtraCodecs.strictOptionalField( Codec.BOOL, "merge_nbt", true )
				.forGetter( RawNBTRecipeResult::mergeNbt )
		).apply( builder, RawNBTRecipeResult::new ) );
	
	@Nullable
	@Override
	public R fromNetwork( @NotNull FriendlyByteBuf buffer ) {
		
		Pair<Integer, NBTRecipeFactory<R>> recipeData = readRecipeData( buffer );
		String group = buffer.readUtf();
		NonNullList<Ingredient> ingredients = NonNullList.withSize( recipeData.getKey(), Ingredient.EMPTY );
		ingredients.replaceAll( ignored -> Ingredient.fromNetwork( buffer ) );
		ItemStack result = buffer.readItem();
		boolean merge_nbt = buffer.readBoolean();
		return recipeData.getValue().buildRecipe( group, ingredients, result, merge_nbt );
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
