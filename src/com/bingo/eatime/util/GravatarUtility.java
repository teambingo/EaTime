package com.bingo.eatime.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class GravatarUtility {

	private static final String URL_HEAD_HTTP = "http://www.gravatar.com/avatar/";
	private static final String URL_HEAD_HTTPS = "https://www.gravatar.com/avatar/";

	public static String hex(byte[] array) {
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < array.length; ++i) {
			sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(
					1, 3));
		}

		return sb.toString();
	}

	public static String md5Hex(String message) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");

			return hex(md.digest(message.getBytes("CP1252")));
		} catch (NoSuchAlgorithmException e) {

		} catch (UnsupportedEncodingException e) {

		}

		return null;
	}

	public static String getGravatarUrlString(String email) {
		return getGravatarUrlString(email, false);
	}

	public static String getGravatarUrlString(String email, boolean secure) {
		return getGravatarUrlString(email, false, 0, secure);
	}

	public static String getGravatarUrlString(String email,
			boolean appendExtension, int size) {
		return getGravatarUrlString(email, appendExtension, size, false);
	}

	public static String getGravatarUrlString(String email,
			boolean appendExtension, int size, boolean secure) {
		return (secure ? URL_HEAD_HTTPS : URL_HEAD_HTTP)
				+ md5Hex(email.trim().toLowerCase())
				+ (appendExtension ? ".jpg" : "")
				+ ((size > 0) ? "?s=" + size : "");
	}
}
