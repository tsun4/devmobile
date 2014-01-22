package com.myschool.game.shopping;

import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;

import com.myschool.game.R;
import com.myschool.game.database.helper.DatabaseHelper;
import com.myschool.game.main.MyApplication;

public class ProductSubCategoryChooserFragment extends DialogFragment {

	private MyApplication mMyApplication;
	private DatabaseHelper mDatabaseHelper;
	private List<String> mSubCategories;
	private Bundle mBundle;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		mBundle = getArguments();
		mMyApplication = (MyApplication) getActivity().getApplicationContext();

		mDatabaseHelper = new DatabaseHelper(mMyApplication);
		mSubCategories = mDatabaseHelper.getSubCategories(mBundle.getInt("selected_shop_id"), mBundle.getString("selected_product_category"));
		Log.d("Alain", "nb SubCategories = " + mSubCategories.size());
		/*
		 * You can use getActivity(), which returns the activity associated with
		 * a fragment. The activity is a context (since Activity extends
		 * Context)
		 */
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(R.string.frag_product_category_chooser_title).setItems(
				mSubCategories.toArray(new CharSequence[mSubCategories.size()]), new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent(getActivity(), ProductListActivity.class);
						mBundle.putString("selected_product_subcategory", mSubCategories.get(which));
						intent.putExtra("shop_bundle", mBundle);
						startActivity(intent);
						getDialog().dismiss();

					}
				});
		return builder.create();
	}
}
