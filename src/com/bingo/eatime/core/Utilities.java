package com.bingo.eatime.core;


public class Utilities {

	public static String getKeyFromName(String name) {
		String keyName = name.trim().replace(' ', '-').toLowerCase();

		return keyName;
	}

}
