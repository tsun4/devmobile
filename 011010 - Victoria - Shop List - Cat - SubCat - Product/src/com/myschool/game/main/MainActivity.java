	package com.myschool.game.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.myschool.game.R;
import com.myschool.game.character.Character;
import com.myschool.game.character.Warrior;
import com.myschool.game.main.CharTypeChooserFragment.CharTypeChooserListener;

/**
 * 
 * 
 * Extends FragmentActivity to allow use of fragment (see
 * http://stackoverflow.com
 * /questions/15318518/android-activity-vs-fragmentactivity
 * 
 * Implements CharTypeChooserListener
 * 
 * @author alain
 * 
 */
public class MainActivity extends FragmentActivity implements
		CharTypeChooserListener {

	private int mCharIndex;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void onCharTypeChooserButtonClick(View v) {
		DialogFragment dialog = new CharTypeChooserFragment();
		dialog.show(this.getSupportFragmentManager(), "NoticeDialogFragment");
	}

	@Override
	public void onFinishCharTypeChooser(int which) {
		//Toast.makeText(this, "" + which, Toast.LENGTH_SHORT).show();
		this.mCharIndex = which;
		// Change text in button
		Button view = (Button) findViewById(R.id.act_main_btn_char_chooser);
		CharSequence item = getResources().getStringArray(
				R.array.character_type_chooser_array)[which];
		view.setText(item);
	}

	public void onCreateCharacterClick(View v) {
		EditText viewCharname = (EditText) findViewById(R.id.charname);
		String charname = viewCharname.getText().toString();
		if (charname.matches("")) {
			Toast.makeText(getApplicationContext(),
					"Le nom du personnage est a renseigner", Toast.LENGTH_SHORT)
					.show();
		} else {
			// TODO améliorer ce code
			// see :
			// http://stackoverflow.com/questions/6094575/creating-an-instance-using-the-class-name-and-calling-constructor
			// http://docs.oracle.com/javase/6/docs/api/java/lang/reflect/Constructor.html#newInstance%28java.lang.Object...%29
			// BETTER?:
			// http://stackoverflow.com/questions/5329685/how-can-i-load-java-class-from-database
			switch (mCharIndex) {
			case 0:
				((MyApplication) getApplicationContext())
						.setPerson(new Character(charname), mCharIndex);
				break;
			case 1:
				((MyApplication) getApplicationContext())
						.setPerson(new Warrior(charname), mCharIndex);
				break;
			default:
				Toast.makeText(getApplicationContext(),
						"Le nom du personnage est a renseigner",
						Toast.LENGTH_SHORT).show();
				return;
			}
			Intent intent = new Intent(this, GameActivity.class);
			startActivity(intent);
		}
	}

}
