package de.geheimagentnr1.recipes_lib.elements.recipes.renaming;

import de.geheimagentnr1.recipes_lib.elements.recipes.ModRecipeSerializersRegisterFactory;
import de.geheimagentnr1.recipes_lib.elements.recipes.ingredients.nbt.MatchType;
import de.geheimagentnr1.recipes_lib.elements.recipes.ingredients.nbt.NBTIngredient;
import de.geheimagentnr1.recipes_lib.helpers.ShaplessRecipesHelper;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;


public class RenamingRecipe implements CraftingRecipe {
	
	
	@NotNull
	public static final String registry_name = "renaming";
	
	@NotNull
	private final ResourceLocation id;
	
	@NotNull
	private final Ingredient ingredient;
	
	@NotNull
	private final NonNullList<Ingredient> ingredients;
	
	private final boolean isSimple;
	
	//package-private
	RenamingRecipe( @NotNull ResourceLocation _id, @NotNull Ingredient _ingredient ) {
		
		id = _id;
		ingredient = _ingredient;
		ingredients = NonNullList.create();
		ingredients.addAll( Arrays.asList( buildNameTagIngredient(), ingredient ) );
		isSimple = ingredients.stream().allMatch( Ingredient::isSimple );
	}
	
	
	@NotNull
	private Ingredient buildNameTagIngredient() {
		
		CompoundTag name_tag_nbt = new CompoundTag();
		name_tag_nbt.put( "display", new CompoundTag() );
		ItemStack stack = new ItemStack( Items.NAME_TAG );
		stack.setTag( name_tag_nbt );
		return NBTIngredient.fromStack( stack, MatchType.CONTAINS );
	}
	
	@NotNull
	@Override
	public ResourceLocation getId() {
		
		return id;
	}
	
	@NotNull
	@Override
	public RecipeSerializer<?> getSerializer() {
		
		return ModRecipeSerializersRegisterFactory.RENAMING;
	}
	
	@NotNull
	@Override
	public ItemStack getResultItem( @NotNull RegistryAccess registryAccess ) {
		
		return ingredient.getItems()[0];
	}
	
	@NotNull
	@Override
	public NonNullList<Ingredient> getIngredients() {
		
		return ingredients;
	}
	
	@Override
	public boolean matches( @NotNull CraftingContainer container, @NotNull Level level ) {
		
		return ShaplessRecipesHelper.matches( this, container, ingredients, isSimple );
	}
	
	@NotNull
	@Override
	public ItemStack assemble( @NotNull CraftingContainer container, @NotNull RegistryAccess registryAccess ) {
		
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
	
	@NotNull
	@Override
	public CraftingBookCategory category() {
		
		return CraftingBookCategory.MISC;
	}
	
	@NotNull
	public Ingredient getIngredient() {
		
		return ingredient;
	}
}
