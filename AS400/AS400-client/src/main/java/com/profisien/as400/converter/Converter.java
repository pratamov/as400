package com.profisien.as400.converter;

public interface Converter {
	
	public String unmarshal(byte[] input);
	public byte[] marshal(String input);
	public byte[] marshal();
	
}