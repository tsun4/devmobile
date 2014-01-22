package com.myschool.game.main;

import io.socket.IOAcknowledge;
import io.socket.IOCallback;
import io.socket.SocketIO;
import io.socket.SocketIOException;

import java.net.MalformedURLException;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.myschool.game.R;

public class ChatActivity extends Activity  {


	private SocketIO mSocket;
	private EditText mEditText;
	private TextView mChatTextView;
	private ChatActivity mActivity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		mActivity = this;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);
	
		mChatTextView = (TextView) findViewById(R.id.act_chat_text);
		Button button = (Button) findViewById(R.id.act_chat_btn_send);
		mEditText = (EditText) findViewById(R.id.act_chat_input);

		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String text = mEditText.getText().toString();
				JSONObject json = new JSONObject();
				try {
					Log.d("Alain", "Emit " + text);
					json.put("message", text);
					mSocket.emit("send", json);
				} catch (JSONException e) {
					Toast.makeText(mActivity, "Error on emit " + e.getMessage(), Toast.LENGTH_SHORT).show();
					Log.e("Alain", "Error on emit " + e.getMessage());
					e.printStackTrace();
				}
			}
		});
		initSocketIO();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.chat, menu);
		return true;
	}

	public void initSocketIO() {
		Log.d("Alain", "initSocketIO");
		try {
			mSocket = new SocketIO("http://172.16.4.117:3700");
			//mSocket.connect("http://192.168.1.99:3700", null);
		} catch (MalformedURLException e) {
			Log.e("Alain", "Error on connect : " + e.getMessage());
			Toast.makeText(mActivity, "Error on connect : " + e.getMessage(), Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
        mSocket.connect(new IOCallback() {

			@Override
			public void on(String event, IOAcknowledge ack, Object... arg) {
                Log.d("Alain", "Server triggered event. Event=" + event + ", " + "arg=" + arg[0]);
				final JSONObject json =  (JSONObject) arg[0];
				Log.d("Alain", "json init ok ");
				
				mActivity.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						String message;
						try {
							message = json.getString("message");
					        String text = (String) mChatTextView.getText();
					        mChatTextView.setText(text + "\n" +  message);
					        Log.d("Alain", "json setText ok");
						} catch (JSONException e) {
							Log.e("Alain", "JSONException : " + e.getMessage());
							Toast.makeText(mActivity, "JSONException : " + e.getMessage(), Toast.LENGTH_SHORT).show();
							e.printStackTrace();
						}
					}
					
				});

			}
			@Override
			public void onConnect() {
				Log.d("Alain", "Connexion SOCKETIO OK");	
			}
			@Override
			public void onDisconnect() {
				mActivity.runOnUiThread(new Runnable() {
					@Override
					public void run() {
							Toast.makeText(mActivity, "Connexion ended", Toast.LENGTH_SHORT).show();
					}
				});				
			}
			@Override
			public void onError(SocketIOException exception) {
				Log.e("Alain", "erreur socketIO : " + exception.getMessage());
				exception.printStackTrace();
				
			}
			@Override
			public void onMessage(String arg0, IOAcknowledge arg1) {
				Log.d("Alain", "onMessage string");				
			}
			@Override
			public void onMessage(JSONObject json, IOAcknowledge arg1) {
				Log.d("Alain", "onMessage JSON");
			}
        });
	}
}
