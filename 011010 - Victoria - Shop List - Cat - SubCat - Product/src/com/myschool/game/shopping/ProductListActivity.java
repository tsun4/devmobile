package com.myschool.game.shopping;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.myschool.game.R;

/**
 * An activity representing a list of Products. This activity has different
 * presentations for handset and tablet-size devices. On handsets, the activity
 * presents a list of items, which when touched, lead to a
 * {@link ProductDetailActivity} representing item details. On tablets, the
 * activity presents the list of items and item details side-by-side using two
 * vertical panes.
 * <p>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link ProductListFragment} and the item details (if present) is a
 * {@link ProductDetailFragment}.
 * <p>
 * This activity also implements the required
 * {@link ProductListFragment.Callbacks} interface to listen for item
 * selections.
 */
public class ProductListActivity extends FragmentActivity implements
		ProductListFragment.Callbacks {

	/**
	 * Whether or not the activity is in two-pane mode, i.e. running on a tablet
	 * device.
	 */
	private boolean mTwoPane;
	private Bundle mBundle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mBundle = getIntent().getBundleExtra("shop_bundle");
		
		setContentView(R.layout.activity_product_list);
		
		if (findViewById(R.id.product_detail_container) != null) {
			// The detail container view will be present only in the
			// large-screen layouts (res/values-large and
			// res/values-sw600dp). If this view is present, then the
			// activity should be in two-pane mode.
			mTwoPane = true;

			// In two-pane mode, list items should be given the
			// 'activated' state when touched.
			((ProductListFragment) getSupportFragmentManager()
					.findFragmentById(R.id.product_list))
					.setActivateOnItemClick(true);
			((ProductListFragment) getSupportFragmentManager()
					.findFragmentById(R.id.product_list))
					.display(mBundle);
		}

		// TODO: If exposing deep links into your app, handle intents here.
	}

	/**
	 * Callback method from {@link ProductListFragment.Callbacks} indicating
	 * that the item with the given ID was selected.
	 */
	@Override
	public void onItemSelected(int productId) {
		mBundle.putInt("selected_product_id", productId);
		if (mTwoPane) {
			// In two-pane mode, show the detail view in this activity by
			// adding or replacing the detail fragment using a
			// fragment transaction.
			ProductDetailFragment fragment = new ProductDetailFragment();
			fragment.setArguments(mBundle);
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.product_detail_container, fragment).commit();

		} else {
			// In single-pane mode, simply start the detail activity
			// for the selected item ID.
			Intent detailIntent = new Intent(this, ProductDetailActivity.class);
			detailIntent.putExtra("shop_bundle", mBundle);
			startActivity(detailIntent);
		}
	}
}
