package com.example.takeaphoto;

import java.io.File;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 111;
	private File mImage;
	private Uri mUriSavedImage;

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

	public void takeAPhoto(View view) {
		// create Intent to take a picture and return control to the calling
		// application
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		File imagesFolder = new File(Environment.getExternalStorageDirectory(),
				"MyImages");
		imagesFolder.mkdirs(); // <---- Create MyImages directory
		if (imagesFolder.mkdir() || imagesFolder.isDirectory()) {
			Log.d("Alain", "MyImages exists");
		} else {
			Log.e("Alain", "MyImages not exists. Why?");
		}
		mImage = new File(imagesFolder, "image_002.jpg");
		if (!mImage.delete()) {
			Log.e("Alain", "image not deleted");
		}
		mUriSavedImage = Uri.fromFile(mImage);

		intent.putExtra(MediaStore.EXTRA_OUTPUT, mUriSavedImage); // set the
																	// image
																	// file name
		// start the image capture Intent
		startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (resultCode == RESULT_OK) {
			if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
				Log.d("Alain", "Result OK");
				if (mImage.exists()) {
					Log.d("Alain", "Exists");
					Log.d("Alain",
							"Image exists path=" + mImage.getAbsolutePath());
					mUriSavedImage = Uri.fromFile(mImage);

					try {
						Intent cropIntent = new Intent(
								"com.android.camera.action.CROP");
						// indicate image type and Uri
						cropIntent.setDataAndType(mUriSavedImage, "image/*");
						// set crop properties
						cropIntent.putExtra("crop", "true");
						// indicate aspect of desired crop
						cropIntent.putExtra("aspectX", 1);
						cropIntent.putExtra("aspectY", 1);
						// indicate output X and Y
						cropIntent.putExtra("outputX", 256);
						cropIntent.putExtra("outputY", 256);
						// retrieve data on return
						cropIntent.putExtra("return-data", true);
						// start the activity - we handle returning in
						// onActivityResult
						startActivityForResult(cropIntent, 222);
					} catch (ActivityNotFoundException anfe) {
						ImageView img = (ImageView) findViewById(R.id.act_main_photo);
						img.setImageURI(null);
						img.setImageURI(mUriSavedImage);
						Toast.makeText(this, "No activity found to crop image",
								Toast.LENGTH_SHORT);
					}
				} else {
					// Toast.makeText(this, "Bad, image doesn't exists",
					// Toast.LENGTH_LONG);
					Log.e("Alain", "Image doesn't exists");
				}
			} else if (requestCode == 222) {
				Log.d("Alain", "Return from Crop");
				ImageView img = (ImageView) findViewById(R.id.act_main_photo);

				final Bundle extras = data.getExtras();

				if (extras != null) {
					Bitmap photo = extras.getParcelable("data");
					img.setImageBitmap(photo);
					// Wysie_Soh: Delete the temporary file
					File f = new File(mImage.getPath());
					if (f.exists()) {
						f.delete();
					}
					// InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					// mgr.showSoftInput(img, InputMethodManager.SHOW_IMPLICIT);

					// Replacement for deprecated official Android crop image
					// function :
					// - https://github.com/edmodo/cropper ****
					// - https://github.com/biokys/cropimage
					// - https://github.com/dtitov/pickncrop
					// - https://github.com/lvillani/android-cropimage
					// - https://github.com/lorensiuswlt/AndroidImageCrop
				}
			} else {
				Log.e("Alain", "Invalid request code " + requestCode);
			}
		} else if (resultCode == RESULT_CANCELED) {
			Toast.makeText(this, "Photo canceled", Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(this, "Photo error", Toast.LENGTH_SHORT).show();
		}
	}

}
