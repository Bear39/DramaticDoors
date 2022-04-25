package com.fizzware.dramaticdoors.compat;

import net.minecraftforge.fml.ModList;

public class Compats
{
	public static final boolean CURIOS_INSTALLED;
    public static final boolean DOUBLE_DOORS_INSTALLED;
    public static final boolean QUARK_INSTALLED;
    public static final boolean SUPPLEMENTARIES_INSTALLED;
    
    static {
    	CURIOS_INSTALLED = ModList.get().isLoaded("curios");
    	DOUBLE_DOORS_INSTALLED = ModList.get().isLoaded("doubledoors");
    	QUARK_INSTALLED = ModList.get().isLoaded("quark");
    	SUPPLEMENTARIES_INSTALLED = ModList.get().isLoaded("supplementaries");
    }
    
    public static boolean hasQuarkDoubleDoorsModule() {
    	if (QUARK_INSTALLED) {
    		return true;
    	}
    	return false;
    }
}
