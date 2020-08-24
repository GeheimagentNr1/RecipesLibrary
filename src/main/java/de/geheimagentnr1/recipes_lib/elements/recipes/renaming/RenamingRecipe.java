package de.geheimagentnr1.recipes_lib.elements.recipes.renaming;

import de.geheimagentnr1.recipes_lib.elements.recipes.RecipeSerializers;
import de.geheimagentnr1.recipes_lib.elements.recipes.ingredients.nbt.MatchType;
import de.geheimagentnr1.recipes_lib.elements.recipes.ingredients.nbt.NBTIngredient;
import de.geheimagentnr1.recipes_lib.helpers.ShaplessRecipesHelper;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.ICraftingRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.Arrays;


public class RenamingRecipe implements ICraftingRecipe {
	
	
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
		
		CompoundNBT name_tag_nbt = new CompoundNBT();
		name_tag_nbt.put( "display", new CompoundNBT() );
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
	public IRecipeSerializer<?> getSerializer() {
		
		return RecipeSerializers.RENAMING;
	}
	
	@Nonnull
	@Override
	public ItemStack getRecipeOutput() {
		
		return ingredient.getMatchingStacks()[0];
	}
	
	@Nonnull
	@Override
	public NonNullList<Ingredient> getIngredients() {
		
		return ingredients;
	}
	
	@Override
	public boolean matches( @Nonnull CraftingInventory inv, @Nonnull World worldIn ) {
		
		return ShaplessRecipesHelper.matches( this, inv, ingredients, isSimple );
	}
	
	@Nonnull
	@Override
	public ItemStack getCraftingResult( @Nonnull CraftingInventory inv ) {
		
		ItemStack result = ItemStack.EMPTY;
		ITextComponent resultDisplayName = null;
		for( int j = 0; j < inv.getSizeInventory(); j++ ) {
			ItemStack stack = inv.getStackInSlot( j );
			if( !stack.isEmpty() && stack.getItem() != Items.NAME_TAG ) {
				result = stack.copy();
			}
		}
		for( int j = 0; j < inv.getSizeInventory(); j++ ) {
			ItemStack stack = inv.getStackInSlot( j );
			if( stack.getItem() == Items.NAME_TAG ) {
				resultDisplayName = stack.getDisplayName();
			}
		}
		if( result.getDisplayName().equals( resultDisplayName ) ) {
			return ItemStack.EMPTY;
		}
		result.setDisplayName( resultDisplayName );
		return result;
	}
	
	@Override
	public boolean canFit( int width, int height ) {
		
		return width * height >= ingredients.size();
	}
	
	public Ingredient getIngredient() {
		
		return ingredient;
	}
}
