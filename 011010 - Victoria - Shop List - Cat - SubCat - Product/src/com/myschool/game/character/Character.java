package com.myschool.game.character;

import java.util.Locale;
import java.util.ResourceBundle;

import android.util.Log;

public class Character {
	private String name;
	private static String language = Locale.getDefault().getLanguage();
	private static String country = Locale.getDefault().getCountry();


	public Character(String name) {
		this.name = name;
		Log.d("XXXXXX", "Language=" + language + "; Country=" + country);

	}
	
	public String getCharacterType() {
		Locale currentLocale;
		ResourceBundle messages;
		currentLocale = new Locale(language, country);
		messages = ResourceBundle.getBundle("MessageBundle", currentLocale);
		return messages.getString(this.getName()); 
	}

	/**
	 * Le nom du personnage
	 * @return String name
	 */
	public String getName() {
		return name;
	}

}
