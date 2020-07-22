package de.geheimagentnr1.recipes_lib.elements.recipes.ingredients.nbt;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import de.geheimagentnr1.recipes_lib.elements.recipes.ingredients.IngredientSerializers;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.*;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IIngredientSerializer;
import net.minecraftforge.common.crafting.IngredientNBT;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;
import java.util.stream.Stream;


public class NBTIngredient extends Ingredient {
	
	
	public static final String registry_name = "nbt";
	
	private final ItemStack stack;
	
	private final boolean equalsNBT;
	
	protected NBTIngredient( ItemStack _stack, boolean _equalsNBT ) {
		
		super( Stream.of( new Ingredient.SingleItemList( _stack ) ) );
		stack = _stack;
		equalsNBT = _equalsNBT;
	}
	
	@Override
	public boolean test( @Nullable ItemStack input ) {
		
		if( input == null ) {
			return false;
		}
		return stack.getItem() == input.getItem() && stack.getDamage() == input.getDamage() &&
			( equalsNBT && stack.areShareTagsEqual( input ) || areNBTEquals( stack.getTag(), input.getTag() ) );
	}
	
/*	public static void test() {
		
		System.out.println( "TestTestTest++++++++++++++++" );
		
		CompoundNBT compoundNBT1 = create1();
		CompoundNBT compoundNBT2 = create1();
		CompoundNBT compoundNBT3 = create2();
		CompoundNBT compoundNBT4 = create3();
		CompoundNBT compoundNBT5 = create4();
		System.out.println( areNBTEquals( null, null, true ) );
		System.out.println( areNBTEquals( compoundNBT1, compoundNBT1, true ) );
		System.out.println( areNBTEquals( null, compoundNBT1, true ) );
		System.out.println( areNBTEquals( compoundNBT1, null, true ) );
		System.out.println( areNBTEquals( compoundNBT1, compoundNBT2, true ) );
		System.out.println( areNBTEquals( compoundNBT1, compoundNBT3, true ) );
		System.out.println( areNBTEquals( compoundNBT3, compoundNBT1, true ) );
		System.out.println();
		System.out.println( areNBTEquals( compoundNBT1, compoundNBT4, true ) );
		System.out.println( areNBTEquals( compoundNBT4, compoundNBT1, true ) );
		System.out.println();
		System.out.println( areNBTEquals( compoundNBT1, compoundNBT5, true ) );
		System.out.println( areNBTEquals( compoundNBT5, compoundNBT1, true ) );
		
		System.out.println( "TestTestTest----------------" );
	}
	
	private static CompoundNBT create1() {
		
		CompoundNBT compoundNBT = new CompoundNBT();
		
		compoundNBT.putBoolean( "boolean", true );
		compoundNBT.putByte( "byte", (byte)0 );
		compoundNBT.putByteArray( "byteArray", new byte[] { 0 } );
		compoundNBT.putDouble( "double", 0.0D );
		compoundNBT.putFloat( "float", 0.0F );
		compoundNBT.putInt( "int", 0 );
		compoundNBT.putIntArray( "intArray1", new int[] { 0 } );
		compoundNBT.putIntArray( "intArray2", Collections.singletonList( 0 ) );
		compoundNBT.putLong( "long", 0 );
		compoundNBT.putLongArray( "longArray1", new long[] { 0 } );
		compoundNBT.putLongArray( "longArray2", Collections.singletonList( 0L ) );
		compoundNBT.putShort( "short", (short)0 );
		compoundNBT.putString( "string", "string" );
		compoundNBT.putUniqueId( "uniqueId", new UUID( 0, 0 ) );
		compoundNBT.put( "end", new EndNBT() );
		ListNBT listNBT = new ListNBT();
		listNBT.add( new StringNBT( "test" ) );
		compoundNBT.put( "list", listNBT );
		CompoundNBT compoundNBT1 = new CompoundNBT();
		compoundNBT1.putString( "string", "string" );
		compoundNBT.put( "compound", compoundNBT1 );
		return compoundNBT;
	}
	
	private static CompoundNBT create2() {
		
		CompoundNBT compoundNBT = new CompoundNBT();
		
		compoundNBT.putBoolean( "boolean", true );
		compoundNBT.putBoolean( "boolean2", true );
		compoundNBT.putByte( "byte", (byte)0 );
		compoundNBT.putByteArray( "byteArray", new byte[] { 0 } );
		compoundNBT.putDouble( "double", 0.0D );
		compoundNBT.putFloat( "float", 0.0F );
		compoundNBT.putInt( "int", 0 );
		compoundNBT.putIntArray( "intArray1", new int[] { 0 } );
		compoundNBT.putIntArray( "intArray2", Collections.singletonList( 0 ) );
		compoundNBT.putLong( "long", 0 );
		compoundNBT.putLongArray( "longArray1", new long[] { 0 } );
		compoundNBT.putLongArray( "longArray2", Collections.singletonList( 0L ) );
		compoundNBT.putShort( "short", (short)0 );
		compoundNBT.putString( "string", "string" );
		compoundNBT.putUniqueId( "uniqueId", new UUID( 0, 0 ) );
		compoundNBT.put( "end", new EndNBT() );
		ListNBT listNBT = new ListNBT();
		listNBT.add( new StringNBT( "test" ) );
		listNBT.add( new StringNBT( "test2" ) );
		compoundNBT.put( "list", listNBT );
		CompoundNBT compoundNBT1 = new CompoundNBT();
		compoundNBT1.putString( "string", "string" );
		compoundNBT1.putString( "string2", "string2" );
		compoundNBT.put( "compound", compoundNBT1 );
		return compoundNBT;
	}
	
	private static CompoundNBT create3() {
		
		CompoundNBT compoundNBT = new CompoundNBT();
		
		compoundNBT.putBoolean( "boolean", true );
		compoundNBT.putByte( "byte", (byte)0 );
		compoundNBT.putByteArray( "byteArray", new byte[] { 0 } );
		compoundNBT.putDouble( "double", 0.0D );
		compoundNBT.putFloat( "float", 0.0F );
		compoundNBT.putInt( "int", 0 );
		compoundNBT.putIntArray( "intArray1", new int[] { 0 } );
		compoundNBT.putIntArray( "intArray2", Collections.singletonList( 0 ) );
		compoundNBT.putLong( "long", 0 );
		compoundNBT.putLongArray( "longArray1", new long[] { 0 } );
		compoundNBT.putLongArray( "longArray2", Collections.singletonList( 0L ) );
		compoundNBT.putShort( "short", (short)0 );
		compoundNBT.putString( "string", "string" );
		compoundNBT.putUniqueId( "uniqueId", new UUID( 0, 0 ) );
		compoundNBT.put( "end", new EndNBT() );
		ListNBT listNBT = new ListNBT();
		listNBT.add( new StringNBT( "test" ) );
		compoundNBT.put( "list", listNBT );
		CompoundNBT compoundNBT1 = new CompoundNBT();
		compoundNBT1.putString( "string", "string2" );
		compoundNBT.put( "compound", compoundNBT1 );
		return compoundNBT;
	}
	
	private static CompoundNBT create4() {
		
		CompoundNBT compoundNBT = new CompoundNBT();
		
		compoundNBT.putBoolean( "boolean", true );
		compoundNBT.putByte( "byte", (byte)0 );
		compoundNBT.putByteArray( "byteArray", new byte[] { 0 } );
		compoundNBT.putDouble( "double", 0.0D );
		compoundNBT.putFloat( "float", 0.0F );
		compoundNBT.putInt( "int", 0 );
		compoundNBT.putIntArray( "intArray1", new int[] { 0 } );
		compoundNBT.putIntArray( "intArray2", Collections.singletonList( 0 ) );
		compoundNBT.putLong( "long", 0 );
		compoundNBT.putLongArray( "longArray1", new long[] { 0 } );
		compoundNBT.putLongArray( "longArray2", Collections.singletonList( 0L ) );
		compoundNBT.putShort( "short", (short)0 );
		compoundNBT.putString( "string", "string" );
		compoundNBT.putUniqueId( "uniqueId", new UUID( 0, 0 ) );
		compoundNBT.put( "end", new EndNBT() );
		ListNBT listNBT = new ListNBT();
		listNBT.add( new StringNBT( "test" ) );
		compoundNBT.put( "list", listNBT );
		CompoundNBT compoundNBT1 = new CompoundNBT();
		compoundNBT.put( "compound", compoundNBT1 );
		return compoundNBT;
	}*/
	
	public static boolean areNBTEquals( @Nullable INBT nbt1, @Nullable INBT nbt2 ) {
		
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
								if( !areNBTEquals( inbt1, compoundnbt1.get( s ) ) ) {
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
										@SuppressWarnings( "NegativelyNamedBooleanVariable" )
										boolean notEqual = true;
										
										for( INBT value : listnbt1 ) {
											if( areNBTEquals( inbt, value ) ) {
												notEqual = false;
												break;
											}
										}
										if( notEqual ) {
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
		json.addProperty( "type", Objects.requireNonNull( CraftingHelper.getID( IngredientNBT.Serializer.INSTANCE ) )
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
	
	public boolean isEqualsNBT() {
		
		return equalsNBT;
	}
}
