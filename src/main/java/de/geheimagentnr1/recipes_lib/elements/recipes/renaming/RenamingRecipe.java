package de.geheimagentnr1.recipes_lib.elements.recipes.renaming;

import de.geheimagentnr1.recipes_lib.elements.recipes.RecipeSerializers;
import de.geheimagentnr1.recipes_lib.elements.recipes.ingredients.nbt.MatchType;
import de.geheimagentnr1.recipes_lib.elements.recipes.ingredients.nbt.NBTIngredient;
import de.geheimagentnr1.recipes_lib.helpers.ShaplessRecipesHelper;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import java.util.Arrays;


public class RenamingRecipe implements CraftingRecipe {
	
	
	public static final String registry_name = "renaming";
	
	private final ResourceLocation id;
	
	private final Ingredient ingredient;
	
	private final NonNullList<Ingredient> ingredients;
	
	private final boolean isSimple;
	
	//package-private
	RenamingRecipe( ResourceLocation _id, Ingredient _ingredient ) {
		
		id = _id;
		ingredient = _ingredient;
		ingredients = NonNullList.create();
		ingredients.addAll( Arrays.asList( buildNameTagIngredient(), ingredient ) );
		isSimple = ingredients.stream().allMatch( Ingredient::isSimple );
	}
	
	
	private Ingredient buildNameTagIngredient() {
		
		CompoundTag name_tag_nbt = new CompoundTag();
		name_tag_nbt.put( "display", new CompoundTag() );
		ItemStack stack = new ItemStack( Items.NAME_TAG );
		stack.setTag( name_tag_nbt );
		return NBTIngredient.fromStack( stack, MatchType.CONTAINS );
	}
	
	@Nonnull
	@Override
	public ResourceLocation getId() {
		
		return id;
	}
	
	@Nonnull
	@Override
	public RecipeSerializer<?> getSerializer() {
		
		return RecipeSerializers.RENAMING;
	}
	
	@Nonnull
	@Override
	public ItemStack getResultItem() {
		
		return ingredient.getItems()[0];
	}
	
	@Nonnull
	@Override
	public NonNullList<Ingredient> getIngredients() {
		
		return ingredients;
	}
	
	@Override
	public boolean matches( @Nonnull CraftingContainer container,@Nonnull  Level level ) {
		
		return ShaplessRecipesHelper.matches( this, container, ingredients, isSimple );
	}
	
	@Nonnull
	@Override
	public ItemStack assemble( @Nonnull CraftingContainer container ) {
		
		ItemStack result = ItemStack.EMPTY;
		Component resultDisplayName = null;
		for( int j = 0; j < container.getContainerSize(); j++ ) {
			ItemStack stack = container.getItem( j );
			if( !stack.isEmpty() && stack.getItem() != Items.NAME_TAG ) {
				result = stack.copy();
			}
		}
		for( int j = 0; j < container.getContainerSize(); j++ ) {
			ItemStack stack = container.getItem( j );
			if( stack.getItem() == Items.NAME_TAG ) {
				resultDisplayName = stack.getHoverName();
			}
		}
		if( result.getHoverName().equals( resultDisplayName ) ) {
			return ItemStack.EMPTY;
		}
		result.setHoverName( resultDisplayName );
		return result;
	}
	
	@Override
	public boolean canCraftInDimensions( int width, int height ) {
		
		return width * height >= ingredients.size();
	}
	
	public Ingredient getIngredient() {
		
		return ingredient;
	}
}
