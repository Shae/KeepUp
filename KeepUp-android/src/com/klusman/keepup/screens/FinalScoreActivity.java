package com.klusman.keepup.screens;


import com.klusman.keepup.MainActivity;
import com.klusman.keepup.MainKeepUp;
import com.klusman.keepup.R;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class FinalScoreActivity extends Activity {
	MainActivity _mainActivity;
	int SCORE;
	Button local;
	Button google;
	TextView myScore;
	
	public FinalScoreActivity(MainActivity mainActivity, int score){
		SCORE = score;
		_mainActivity = mainActivity;
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
      	setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(com.klusman.keepup.R.layout.final_screen); 

		local = (Button)findViewById(R.id.btnSaveLocal);
		local.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//Intent intent = new Intent(MainActivity.this, ChildList.class);
		        //startActivity(intent);
				Log.i(MainKeepUp.TAG, "LOCAL BTN CLICKED");
			}
		});
		
		google = (Button)findViewById(R.id.btnSaveGoogle);
		google.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//Intent intent = new Intent(MainActivity.this, ChildList.class);
		        //startActivity(intent);
				Log.i(MainKeepUp.TAG, "GOOGLE BTN CLICKED");
			}
		});

		myScore = (TextView)findViewById(R.id.scoreText);
		myScore.setText(SCORE);

	} // END onCreate

}
