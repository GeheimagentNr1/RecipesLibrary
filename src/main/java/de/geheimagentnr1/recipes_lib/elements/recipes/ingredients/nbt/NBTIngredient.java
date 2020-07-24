package de.geheimagentnr1.recipes_lib.elements.recipes.ingredients.nbt;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import de.geheimagentnr1.recipes_lib.elements.recipes.ingredients.IngredientSerializers;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IIngredientSerializer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;
import java.util.stream.Stream;


public class NBTIngredient extends Ingredient {
	
	
	public static final String registry_name = "nbt";
	
	private final ItemStack stack;
	
	private final MatchType matchType;
	
	protected NBTIngredient( ItemStack _stack, MatchType _matchType ) {
		
		super( Stream.of( new Ingredient.SingleItemList( _stack ) ) );
		stack = _stack;
		matchType = _matchType;
	}
	
	@Override
	public boolean test( @Nullable ItemStack input ) {
		
		if( input == null || stack.getItem() != input.getItem() || stack.getDamage() != input.getDamage() ) {
			return false;
		}
		switch( matchType ) {
			case EQUAL:
				return stack.areShareTagsEqual( input );
			case CONTAINS:
				return containsNBT( stack.getTag(), input.getTag() );
			case CONTAINS_NONE:
				return containsNoneNBT( stack.getTag(), input.getTag() );
			case NOT_EQUAL:
				return !stack.areShareTagsEqual( input );
		}
		return false;
	}
	
	private boolean containsNBT( @Nullable INBT nbt1, @Nullable INBT nbt2 ) {
		
		if( nbt1 == nbt2 ) {
			return true;
		} else {
			if( nbt1 == null ) {
				return true;
			} else {
				if( nbt2 == null ) {
					return false;
				} else {
					if( nbt1.getClass().equals( nbt2.getClass() ) ) {
						if( nbt1 instanceof CompoundNBT ) {
							CompoundNBT compoundnbt = (CompoundNBT)nbt1;
							CompoundNBT compoundnbt1 = (CompoundNBT)nbt2;
							
							for( String s : compoundnbt.keySet() ) {
								INBT inbt1 = compoundnbt.get( s );
								if( !containsNBT( inbt1, compoundnbt1.get( s ) ) ) {
									return false;
								}
							}
							
							return true;
						} else {
							if( nbt1 instanceof ListNBT ) {
								ListNBT listnbt = (ListNBT)nbt1;
								ListNBT listnbt1 = (ListNBT)nbt2;
								if( listnbt.isEmpty() ) {
									return listnbt1.isEmpty();
								} else {
									for( INBT inbt : listnbt ) {
										boolean containsNone = true;
										
										for( INBT value : listnbt1 ) {
											if( containsNBT( inbt, value ) ) {
												containsNone = false;
												break;
											}
										}
										if( containsNone ) {
											return false;
										}
									}
									return true;
								}
							} else {
								return nbt1.equals( nbt2 );
							}
						}
					} else {
						return false;
					}
				}
			}
		}
	}
	
	private boolean containsNoneNBT( @Nullable INBT nbt1, @Nullable INBT nbt2 ) {
		
		if( nbt1 == nbt2 ) {
			return false;
		} else {
			if( nbt1 == null ) {
				return false;
			} else {
				if( nbt2 == null ) {
					return true;
				} else {
					if( nbt1.getClass().equals( nbt2.getClass() ) ) {
						if( nbt1 instanceof CompoundNBT ) {
							CompoundNBT compoundnbt = (CompoundNBT)nbt1;
							CompoundNBT compoundnbt1 = (CompoundNBT)nbt2;
							
							for( String s : compoundnbt.keySet() ) {
								INBT inbt1 = compoundnbt.get( s );
								if( !containsNoneNBT( inbt1, compoundnbt1.get( s ) ) ) {
									return false;
								}
							}
							return true;
						} else {
							if( nbt1 instanceof ListNBT ) {
								ListNBT listnbt = (ListNBT)nbt1;
								ListNBT listnbt1 = (ListNBT)nbt2;
								if( listnbt.isEmpty() ) {
									return false;
								} else {
									for( INBT inbt : listnbt ) {
										boolean contains = false;
										
										for( INBT value : listnbt1 ) {
											if( !containsNoneNBT( inbt, value ) ) {
												contains = true;
												break;
											}
										}
										if( contains ) {
											return false;
										}
									}
									return true;
								}
							} else {
								return !nbt1.equals( nbt2 );
							}
						}
					} else {
						return false;
					}
				}
			}
		}
	}
	
	@Override
	public boolean isSimple() {
		
		return false;
	}
	
	@Nonnull
	@Override
	public IIngredientSerializer<? extends Ingredient> getSerializer() {
		
		return IngredientSerializers.NBT_INGREDIENT;
	}
	
	@Nonnull
	@Override
	public JsonElement serialize() {
		
		JsonObject json = new JsonObject();
		json.addProperty( "type", Objects.requireNonNull( CraftingHelper.getID( IngredientSerializers.NBT_INGREDIENT ) )
			.toString() );
		json.addProperty( "item", Objects.requireNonNull( stack.getItem().getRegistryName() ).toString() );
		json.addProperty( "count", stack.getCount() );
		if( stack.hasTag() ) {
			json.addProperty( "nbt", stack.getOrCreateTag().toString() );
		}
		return json;
	}
	
	public ItemStack getStack() {
		
		return stack;
	}
	
	public MatchType getMatchType() {
		
		return matchType;
	}
}
