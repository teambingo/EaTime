package com.bingo.eatime.core;

import com.google.appengine.api.datastore.Key;

public class UserAccount extends Person {
	
	public static final String KIND_USERACCOUNT = "account";
	
	private Key key;

}
