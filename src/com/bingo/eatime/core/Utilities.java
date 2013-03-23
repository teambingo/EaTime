package com.bingo.eatime.core;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utilities {

	public static String getKeyFromName(String name) {
		String keyName = name.trim().replace(' ', '-').toLowerCase();

		return keyName;
	}

}
