package com.myschool.game.main;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.myschool.game.R;

public class CharTypeChooserFragment extends DialogFragment {
	public interface CharTypeChooserListener {
		void onFinishCharTypeChooser(int which);
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		/*
		 * You can use getActivity(), which returns the activity associated with
		 * a fragment. The activity is a context (since Activity extends
		 * Context).
		 */
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(R.string.character_type_chooser_title).setItems(
				R.array.character_type_chooser_array,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {

						// Get call calling activity (which implement interface
						// method CharTypeChooserListener
						CharTypeChooserListener activity = (CharTypeChooserListener) getActivity();

						// Call interface method of calling onFinsih
						activity.onFinishCharTypeChooser(which);

						// close dialog
						getDialog().dismiss();

					}
				});
		return builder.create();
	}
}
