package com.myschool.game.shopping;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;

import com.myschool.game.R;
import com.myschool.game.database.helper.DatabaseHelper;
import com.myschool.game.main.MyApplication;
import com.myschool.game.model.Shop;

public class ShopChooserFragment extends DialogFragment {

	private MyApplication mMyApplication;
	private Cursor mCursor;
	private DatabaseHelper mDatabaseHelper;
	private Shop mShop;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		mMyApplication = (MyApplication) getActivity().getApplicationContext();
		mDatabaseHelper = new DatabaseHelper(mMyApplication);
		mCursor = mDatabaseHelper.getAllShops();
		Log.d("Alain", "shop count = " + mCursor.getCount());

		/*
		 * You can use getActivity(), which returns the activity
		 * associated with a fragment. The activity is a context
		 * (since Activity extends Context).
		 */											
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		//builder.setView(view)
		builder.setTitle(R.string.character_type_chooser_title).setCursor(
				mCursor,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						mShop = mDatabaseHelper.getShop(mCursor, which);
						Log.d("Alain", "Selected Shop: id=" + mShop.getId() + "; name=" + mShop.getName());
						
						Intent intent = new Intent(mMyApplication, ShoppingActivity.class);
						intent.putExtra("selected_shop_id", mShop.getId());
						intent.putExtra("selected_shop_name", mShop.getName());
						startActivity(intent);
						//getDialog().dismiss();
						
					}
				}, "name");
		return builder.create();
	}
}
