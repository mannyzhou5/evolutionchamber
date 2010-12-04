package com.fray.evo;

public final class Optimization {
	private static Integer[] integers = new Integer[10000];
	
	public static Integer inte(int i) {
		if(i < 0 || i > integers.length)
			return new Integer(i);
		if(integers[i] == null)
			integers[i] = new Integer(i);
		return integers[i];
	}
}
