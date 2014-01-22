package com.myschool.game.main;

import android.app.Application;

import com.myschool.game.R;
import com.myschool.game.character.Character;

// J'étends la classe Application pour stocker des variables "globales" à toutes mes activités
public class MyApplication  extends Application {
	private Character person;
	private int personIndex;

	public int getPersonIndex() {
		return personIndex;
	}

	public void setPersonIndex(int personIndex) {
		this.personIndex = personIndex;
	}

	public Character getPerson() {
		return person;
	}

	public void setPerson(Character person, int index) {
		this.personIndex = index;
		this.person = person;
	}
	
	public String getCharNameAndType() {
		CharSequence item = getResources().getStringArray(
				R.array.character_type_chooser_array)[this.personIndex];
		return this.person.getName() + " (" + item + ")"; 
	}
}
