package com.myschool.game.database.helper;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.myschool.game.main.MyApplication;
import com.myschool.game.model.Product;
import com.myschool.game.model.Shop;

public class DatabaseHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "shops.db";
	private static final int DATABASE_VERSION = 1;

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Table names
	private static final String TABLE_SHOP = "shops";
	private static final String TABLE_PRODUCT = "products";

	// Commun Columns name
	/**
	 * KEY_ID = _id
	 * 
	 * _id is useful when you are using the enhanced Adapters which make use of
	 * a Cursor (e.g. ResourceCursorAdapter). It's used by these adapters to
	 * provide an ID which can be used to refer to the specific row in the table
	 * which relates the the item in whatever the adapter is being used for
	 * (e.g. a row in a ListView). It's not necessary if you're not going to be
	 * using classes which need an _id column in a cursor, and you can also use
	 * "as _id" to make another column appear as though it's called _id in your
	 * cursor
	 * 
	 * http://stackoverflow.com/questions/3192064/about-id-field-in-android-
	 * sqlite
	 */
	public static final String KEY_ID = "_id";

	// Table shops - columns name
	private static final String KEY_SHOP_NAME = "name";

	// Table products - columns name
	private static final String KEY_PRODUCT_SHOP_ID = "shop_id";
	private static final String KEY_PRODUCT_NAME = "name";
	private static final String KEY_PRODUCT_CATEGORY = "category";
	private static final String KEY_PRODUCT_SUBCATEGORY = "subcategory";
	private static final String KEY_PRODUCT_PRICE = "price";
	private static final String KEY_PRODUCT_COUNT = "count";

	// Create table shops
	private static final String CREATE_TABLE_SHOP = "CREATE TABLE "
			+ TABLE_SHOP + "(" + KEY_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_SHOP_NAME + " TEXT"
			+ ")";

	// Create table products
	private static final String CREATE_TABLE_PRODUCT = "CREATE TABLE "
			+ TABLE_PRODUCT + "(" + KEY_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_PRODUCT_SHOP_ID
			+ " INTEGER," + KEY_PRODUCT_NAME + " TEXT," + KEY_PRODUCT_CATEGORY
			+ " TEXT," + KEY_PRODUCT_SUBCATEGORY + " TEXT," + KEY_PRODUCT_PRICE
			+ " INTEGER," + KEY_PRODUCT_COUNT + " INTEGER" + ")";

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_SHOP);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT);
		db.execSQL(CREATE_TABLE_SHOP);
		db.execSQL(CREATE_TABLE_PRODUCT);
		// this.initializeDatabase();
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// We use DROP here, but you can use ALTER to change Structure
		if (newVersion > oldVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_SHOP);
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT);
			this.onCreate(db);
		}
	}

	// SHOP CRUD
	public int createShop(Shop shop) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_SHOP_NAME, shop.getName());
		int id = (int) db.insert(TABLE_SHOP, null, values);
		shop.setId(id);
		return id;
	}

	public Shop getShop(int id) {
		SQLiteDatabase db = this.getReadableDatabase();
		String sql = "SELECT * FROM " + TABLE_SHOP + " WHERE " + KEY_ID + " = "
				+ id;
		Cursor cursor = db.rawQuery(sql, null);
		if (cursor != null) {
			cursor.moveToFirst();
		}
		return getShop(cursor, 0);
	}

	public Shop getShop(Cursor cursor, int position) {
		Shop shop = new Shop();
		shop.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
		shop.setName(cursor.getString(cursor.getColumnIndex(KEY_SHOP_NAME)));
		return shop;
	}

	public List<Shop> getShopList() {
		List<Shop> shops = new ArrayList<Shop>();
		SQLiteDatabase db = this.getReadableDatabase();
		String sql = "SELECT * FROM " + TABLE_SHOP;
		Cursor cursor = db.rawQuery(sql, null);
		Log.d("Alain", "count = " + cursor.getCount());
		if (cursor.moveToFirst()) {
			do {
				Shop shop = new Shop();
				shop.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
				shop.setName(cursor.getString(cursor
						.getColumnIndex(KEY_SHOP_NAME)));
				shops.add(shop);
			} while (cursor.moveToNext());
		}
		return shops;
	}

	public Cursor getAllShops() {
		SQLiteDatabase db = this.getReadableDatabase();
		String sql = "SELECT * FROM " + TABLE_SHOP;
		Cursor cursor = db.rawQuery(sql, null);
		Log.d("Alain", "count = " + cursor.getCount());
		cursor.moveToFirst();
		return cursor;
	}

	public int updateShop(Shop shop) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_SHOP_NAME, shop.getName());
		return db.update(TABLE_SHOP, values, KEY_ID + " = " + shop.getId(),
				null);
	}

	public void deleteShop(Shop shop) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_SHOP, KEY_ID + " = " + shop.getId(), null);
	}

	// PRODUCT CRUD
	public int createProduct(Product product) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_PRODUCT_SHOP_ID, product.getShopId());
		values.put(KEY_PRODUCT_NAME, product.getName());
		values.put(KEY_PRODUCT_CATEGORY, product.getCategory());
		values.put(KEY_PRODUCT_SUBCATEGORY, product.getSubCategory());
		values.put(KEY_PRODUCT_PRICE, product.getPrice());
		values.put(KEY_PRODUCT_COUNT, product.getCount());
		int id = (int) db.insert(TABLE_PRODUCT, null, values);
		product.setId(id);
		return id;
	}

	public Product getProduct(int id) {
		SQLiteDatabase db = this.getReadableDatabase();
		String sql = "SELECT * FROM " + TABLE_PRODUCT + " WHERE " + KEY_ID
				+ " = " + id;
		Cursor cursor = db.rawQuery(sql, null);
		if (cursor != null) {
			cursor.moveToFirst();
		}
		return getProduct(cursor);
	}

	public List<Product> getProductList(Shop shop) {
		List<Product> products = new ArrayList<Product>();
		SQLiteDatabase db = this.getReadableDatabase();
		String sql = "SELECT * FROM " + TABLE_PRODUCT + " WHERE "
				+ KEY_PRODUCT_SHOP_ID + " = " + shop.getId();
		Cursor cursor = db.rawQuery(sql, null);
		if (cursor.moveToFirst()) {
			do {
				products.add(getProduct(cursor));
			} while (cursor.moveToNext());
		}
		return products;
	}

	public Cursor getProductCursor(int shopId, String category, String subCategory) {
		SQLiteDatabase db = this.getReadableDatabase();
		String sql = "SELECT * FROM " + TABLE_PRODUCT + " WHERE "
				+ KEY_PRODUCT_SHOP_ID + " = " + shopId + " AND " 
				+ KEY_PRODUCT_CATEGORY + " = '" + category + "' AND "
				+ KEY_PRODUCT_SUBCATEGORY + " = '" + subCategory + "'";
 		Cursor cursor = db.rawQuery(sql, null);
		return cursor;
	}
	
	public List<String> getProductCategories(Shop shop) {
		List<String> categories = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();
		String sql = "SELECT category FROM " + TABLE_PRODUCT + " WHERE "
				+ KEY_PRODUCT_SHOP_ID + " = " + shop.getId() + " GROUP BY "
				+ KEY_PRODUCT_CATEGORY;
		Cursor cursor = db.rawQuery(sql, null);
		Log.d("Alain",
				"Shop " + shop.getName() + ": Categories nb:"
						+ cursor.getCount());
		if (cursor.moveToFirst()) {
			do {
				categories.add(cursor.getString(cursor
						.getColumnIndex(KEY_PRODUCT_CATEGORY)));
			} while (cursor.moveToNext());
		}
		return categories;
	}

	public List<String> getProductCategories(int shopId) {
		List<String> categories = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();
		String sql = "SELECT category FROM " + TABLE_PRODUCT + " WHERE "
				+ KEY_PRODUCT_SHOP_ID + " = " + shopId + " GROUP BY "
				+ KEY_PRODUCT_CATEGORY;
		Cursor cursor = db.rawQuery(sql, null);
		Log.d("Alain",
				"Shop " + shopId + ": Categories nb:" + cursor.getCount());
		if (cursor.moveToFirst()) {
			do {
				categories.add(cursor.getString(cursor
						.getColumnIndex(KEY_PRODUCT_CATEGORY)));
			} while (cursor.moveToNext());
		}
		return categories;
	}

	public List<String> getSubCategories(int shopId, String category) {
		List<String> subCategories = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();
		String sql = "SELECT " + KEY_PRODUCT_SUBCATEGORY + " FROM " + TABLE_PRODUCT + " WHERE "
				+ KEY_PRODUCT_SHOP_ID + " = " + shopId + " AND "
				+ KEY_PRODUCT_CATEGORY + " = '" + category + "' GROUP BY "
				+ KEY_PRODUCT_SUBCATEGORY;
		Cursor cursor = db.rawQuery(sql, null);
		Log.d("Alain",
				"Shop " + shopId + ": Categories nb:" + cursor.getCount());
		if (cursor.moveToFirst()) {
			do {
				subCategories.add(cursor.getString(cursor
						.getColumnIndex(KEY_PRODUCT_SUBCATEGORY)));
			} while (cursor.moveToNext());
		}
		return subCategories;
	}

	private Product getProduct(Cursor cursor) {
		Product product = new Product();
		product.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
		product.setShopId(cursor.getInt(cursor
				.getColumnIndex(KEY_PRODUCT_SHOP_ID)));
		product.setName(cursor.getString(cursor
				.getColumnIndex(KEY_PRODUCT_NAME)));
		product.setCategory(cursor.getString(cursor
				.getColumnIndex(KEY_PRODUCT_CATEGORY)));
		product.setSubCategory(cursor.getString(cursor
				.getColumnIndex(KEY_PRODUCT_SUBCATEGORY)));
		product.setPrice(cursor.getInt(cursor.getColumnIndex(KEY_PRODUCT_PRICE)));
		product.setCount(cursor.getInt(cursor.getColumnIndex(KEY_PRODUCT_COUNT)));
		return product;
	}
	

	public int updateProduct(Product product) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_PRODUCT_NAME, product.getName());
		values.put(KEY_PRODUCT_SHOP_ID, product.getShopId());
		values.put(KEY_PRODUCT_CATEGORY, product.getCategory());
		values.put(KEY_PRODUCT_SUBCATEGORY, product.getSubCategory());
		values.put(KEY_PRODUCT_PRICE, product.getPrice());
		values.put(KEY_PRODUCT_COUNT, product.getCount());

		return db.update(TABLE_PRODUCT, values,
				KEY_ID + " = " + product.getId(), null);
	}

	public void deleteProduct(Product product) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_PRODUCT, KEY_ID + " = " + product.getId(), null);
	}

	public void logShopList() {
		List<Shop> shopList = getShopList();
		for (Shop shop : shopList) {
			Log.d("Alain", "Shop " + shop.getName());
			List<Product> productList = getProductList(shop);
			for (Product p : productList) {
				Log.d("Alain",
						"  Product " + p.getName() + " category: "
								+ p.getCategory() + " subCategory: "
								+ p.getSubCategory() + " price: "
								+ p.getPrice() + " count: " + p.getCount());

			}
		}

	}

	public void initializeDatabase() {
		// Initialisation de la database

		Shop shop;
		Product product;
		
		shop = new Shop("Kevin Weapons");
		this.createShop(shop);

		product = new Product(shop, "Arc", "Armes", "Arme de projection", 100,
				10);
		this.createProduct(product);

		product = new Product(shop, "Arbalète", "Armes", "Arme de projection",
				100, 10);
		this.createProduct(product);

		product = new Product(shop, "Epée", "Armes", "Armes blanche", 80, 10);
		this.createProduct(product);

		product = new Product(shop, "Poignard", "Armes", "Arme blanche", 50, 10);
		this.createProduct(product);

		shop = new Shop("Jules Wear");
		this.createShop(shop);

		product = new Product(shop, "Armure", "Protection", "Armure", 120, 10);
		this.createProduct(product);

		product = new Product(shop, "Casque", "Protection", "Armure", 120, 10);
		this.createProduct(product);

		product = new Product(shop, "Jambière", "Protection", "Armure", 120, 10);
		this.createProduct(product);

		product = new Product(shop, "Cotte de mailles", "Protection", "Armure",
				120, 10);
		this.createProduct(product);

		product = new Product(shop, "Robe rouge", "Femme", "Robe", 50, 10);
		this.createProduct(product);

		product = new Product(shop, "Robe Verte", "Femme", "Robe", 50, 10);
		this.createProduct(product);

		product = new Product(shop, "Robe Jaune", "Femme", "Robe", 50, 10);
		this.createProduct(product);
		
		product = new Product(shop, "Chapeau jaune", "Femme", "Chapeau", 50, 10);
		this.createProduct(product);

		product = new Product(shop, "Pantalon noir", "Homme", "Pantalon", 50,
				10);
		this.createProduct(product);

		product = new Product(shop, "Pantalon blanc", "Homme", "Pantalon", 50,
				10);
		this.createProduct(product);

		this.logShopList();

	}

}
