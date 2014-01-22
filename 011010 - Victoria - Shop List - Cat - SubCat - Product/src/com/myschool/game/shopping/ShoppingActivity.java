package com.myschool.game.shopping;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.myschool.game.R;
import com.myschool.game.main.MyApplication;

public class ShoppingActivity extends FragmentActivity {

	private MyApplication mMyApplication;
	private int mShopId;
	private String mShopName;
	private Bundle mBundle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		mShopId = getIntent().getIntExtra("selected_shop_id", 0);
		mShopName = getIntent().getStringExtra("selected_shop_name");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shopping);

		mMyApplication = (MyApplication) getApplicationContext();

		((TextView) findViewById(R.id.act_game_text_charname))
				.setText(mMyApplication.getCharNameAndType());

		((TextView) findViewById(R.id.act_shopping_text_shopname))
				.setText(mShopName);

		// DatabaseHelper databaseHelper = new DatabaseHelper(mMyApplication);

	}

	@Override
	protected void onResume() {
		Log.d("Alain", "onResume");
		if (mBundle != null) {
		Log.d("Alain", "onResume: shop_id=" + mBundle.getInt("selected_shop_id"));
		Log.d("Alain", "; selected_category=" + mBundle.getString("selected_product_category"));
		Log.d("Alain", "; selected subcategory=" + mBundle.getString("selected_product_subcategory"));
		}
		super.onResume();
	}

	public void onProductCategoryChooserBtnClick(View v) {
		DialogFragment dialog = new ProductCategoryChooserFragment();
		mBundle = new Bundle();
		mBundle.putInt("selected_shop_id", mShopId);
		mBundle.putString("selected_shop_name", mShopName);
		dialog.setArguments(mBundle);
		dialog.show(this.getSupportFragmentManager(),
				"ProductCategoryChooserFragment");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game, menu);
		return true;
	}
}
