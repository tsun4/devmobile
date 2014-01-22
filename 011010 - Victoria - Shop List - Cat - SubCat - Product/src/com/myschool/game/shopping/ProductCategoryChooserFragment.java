package com.myschool.game.shopping;

import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.myschool.game.R;
import com.myschool.game.database.helper.DatabaseHelper;
import com.myschool.game.main.MyApplication;

public class ProductCategoryChooserFragment extends DialogFragment {

	private MyApplication mMyApplication;
	private DatabaseHelper mDatabaseHelper;
	private List<String> mCategories;
	private Bundle mBundle;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		mBundle = getArguments();
		mMyApplication = (MyApplication) getActivity().getApplicationContext();

		mDatabaseHelper = new DatabaseHelper(mMyApplication);
		mCategories = mDatabaseHelper.getProductCategories(mBundle.getInt("selected_shop_id"));
		
		/*
		 * You can use getActivity(), which returns the activity associated with
		 * a fragment. The activity is a context (since Activity extends
		 * Context)
		 */
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(R.string.frag_product_category_chooser_title).setItems(
				mCategories.toArray(new CharSequence[mCategories.size()]), new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						DialogFragment dialog2 = new ProductSubCategoryChooserFragment();
						String selectedCategory = mCategories.get(which);
						mBundle.putString("selected_product_category", selectedCategory);
						dialog2.setArguments(mBundle);
						dialog2.show(getActivity().getSupportFragmentManager(), "ProductCategoryChooserFragment");

						// close dialog
						getDialog().dismiss();

					}
				});
		return builder.create();
	}
}
