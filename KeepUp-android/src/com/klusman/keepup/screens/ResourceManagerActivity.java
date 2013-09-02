package com.klusman.keepup.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.klusman.keepup.MainActivity;
import com.klusman.keepup.R;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class ResourceManagerActivity extends Activity{
	MainActivity _mainActivity;
	int MaxPoints = 100;
	int allPointsSpent = 100;
	int PointsLeft = 100 - allPointsSpent;

	public static Sound bounceUp;
	public static Sound bounceDwn;
	public static Sound rockBottom;
	TextView textLeft;

	SharedPreferences prefs;
	SharedPreferences.Editor editor; // = prefs.edit();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		_mainActivity = MainActivity.Instance;

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(com.klusman.keepup.R.layout.resource_manager); 

		if(_mainActivity.getSoundBool() == true){
			if(_mainActivity.isMusicPlaying() == false){
				_mainActivity.playBgMusic(true);
			}
		}else{
			if(_mainActivity.isMusicPlaying() == true){
				_mainActivity.playBgMusic(false);
			}
		}

		prefs = getSharedPreferences("userPrefs", MODE_PRIVATE);
		editor = prefs.edit();
		textLeft = (TextView)findViewById(R.id.pointsText);
		textLeft.setText(getPointsString());
		Button resetBtn = (Button)findViewById(R.id.resetPoints);

		Button up1 = (Button)findViewById(R.id.plusBtn1);
		Button up2 = (Button)findViewById(R.id.plusBtn2);
		Button up3 = (Button)findViewById(R.id.plusBtn3);
		Button up4 = (Button)findViewById(R.id.plusBtn4);

		Button dwn1 = (Button)findViewById(R.id.minusBtn1);
		Button dwn2 = (Button)findViewById(R.id.minusBtn2);
		Button dwn3 = (Button)findViewById(R.id.minusBtn3);
		Button dwn4 = (Button)findViewById(R.id.minusBtn4);

		bounceUp = Gdx.audio.newSound(Gdx.files.internal("audio/ballbounce04.wav"));
		bounceDwn = Gdx.audio.newSound(Gdx.files.internal("audio/Ball_Bounce.wav"));
		rockBottom = Gdx.audio.newSound(Gdx.files.internal("audio/HardBounce.wav"));

		final TextView tv1 = (TextView)findViewById(R.id.value1);
		tv1.setText(String.valueOf(MainActivity.spawnRateKit)+ "%");

		final TextView tv2 = (TextView)findViewById(R.id.value2);
		tv2.setText(String.valueOf(MainActivity.spawnRateShield+ "%"));

		final TextView tv3 = (TextView)findViewById(R.id.value3);
		tv3.setText(String.valueOf(MainActivity.spawnRateFreeze)+ "%");

		final TextView tv4 = (TextView)findViewById(R.id.value4);
		tv4.setText(String.valueOf(MainActivity.spawnRateBomb)+ "%");


		resetBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				MainActivity.spawnRateKit = 1;
				tv1.setText(String.valueOf(MainActivity.spawnRateKit)+ "%");
				MainActivity.spawnRateShield = 1;
				tv2.setText(String.valueOf(MainActivity.spawnRateShield)+ "%");
				MainActivity.spawnRateFreeze = 1;
				tv3.setText(String.valueOf(MainActivity.spawnRateFreeze)+ "%");
				MainActivity.spawnRateBomb = 1;
				tv4.setText(String.valueOf(MainActivity.spawnRateBomb)+ "%");
				getPointsLeft();
				textLeft.setText(getPointsString());

			}
		});

		up1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(PointsLeft > 0){
					bounceUp.play(0.5f);
					MainActivity.spawnRateKit = MainActivity.spawnRateKit + 1;
					getPointsLeft();
					tv1.setText(String.valueOf(MainActivity.spawnRateKit)+ "%");
					textLeft.setText(getPointsString());

				}else{
					rockBottom.play(0.5f);
				}
			}
		});

		dwn1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(MainActivity.spawnRateKit > 1){
					bounceDwn.play(0.5f);
					MainActivity.spawnRateKit = MainActivity.spawnRateKit - 1;
					getPointsLeft();
					tv1.setText(String.valueOf(MainActivity.spawnRateKit)+ "%");
					textLeft.setText(getPointsString());
				}
				if(MainActivity.spawnRateKit == 1){
					rockBottom.play(0.5f);
				}
			}
		});

		///////////

		up2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(PointsLeft > 0){
					bounceUp.play(0.5f);
					MainActivity.spawnRateShield = MainActivity.spawnRateShield + 1;
					getPointsLeft();
					tv2.setText(String.valueOf(MainActivity.spawnRateShield)+ "%");
					textLeft.setText(getPointsString());
				}else{
					rockBottom.play(0.5f);
				}
			}
		});

		dwn2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(MainActivity.spawnRateShield > 1){
					bounceDwn.play(0.5f);
					MainActivity.spawnRateShield = MainActivity.spawnRateShield - 1;
					getPointsLeft();
					tv2.setText(String.valueOf(MainActivity.spawnRateShield)+ "%");
					textLeft.setText(getPointsString());
				}
				if(MainActivity.spawnRateShield == 1){
					rockBottom.play(0.5f);

				}
			}
		});

		/////////////

		up3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(PointsLeft > 0){
					bounceUp.play(0.5f);
					MainActivity.spawnRateFreeze = MainActivity.spawnRateFreeze + 1;
					getPointsLeft();
					tv3.setText(String.valueOf(MainActivity.spawnRateFreeze) + "%");
					textLeft.setText(getPointsString());
				}else{
					rockBottom.play(0.5f);
				}
			}
		});

		dwn3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(MainActivity.spawnRateFreeze > 1){
					bounceDwn.play(0.5f);
					MainActivity.spawnRateFreeze = MainActivity.spawnRateFreeze - 1;
					getPointsLeft();
					tv3.setText(String.valueOf(MainActivity.spawnRateFreeze)+ "%");
					textLeft.setText(getPointsString());
				}
				if(MainActivity.spawnRateFreeze == 1){
					rockBottom.play(0.5f);
				}
			}
		});

		//////////////

		up4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(PointsLeft > 0){
					bounceUp.play(0.5f);
					MainActivity.spawnRateBomb = MainActivity.spawnRateBomb + 1;
					getPointsLeft();
					tv4.setText(String.valueOf(MainActivity.spawnRateBomb)+ "%");
					textLeft.setText(getPointsString());
				}else{
					rockBottom.play(0.5f);
				}
			}
		});

		dwn4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(MainActivity.spawnRateBomb > 1){
					bounceDwn.play(0.5f);
					MainActivity.spawnRateBomb = MainActivity.spawnRateBomb - 1;
					getPointsLeft();
					tv4.setText(String.valueOf(MainActivity.spawnRateBomb)+ "%");
					textLeft.setText(getPointsString());

				}
				if(MainActivity.spawnRateBomb == 1){
					rockBottom.play(0.5f);
				}
			}
		});

		if(PointsLeft == 0){
			textLeft.setTextColor(getResources().getColor(R.color.accent_green));
		}else{
			textLeft.setTextColor(getResources().getColor(R.color.red));
		}


	}



	private void getPointsLeft(){
		int addAll = MainActivity.spawnRateKit + 
				MainActivity.spawnRateShield + 
				MainActivity.spawnRateFreeze + 
				MainActivity.spawnRateBomb;
		allPointsSpent = addAll;
		PointsLeft = 100 - allPointsSpent;
		if(PointsLeft == 0){
			textLeft.setTextColor(getResources().getColor(R.color.accent_green));
		}else{
			textLeft.setTextColor(getResources().getColor(R.color.red));
		}


	}

	//TODO fix plural
	public String getPointsString(){
		String s = "You have " + PointsLeft + " points left to spend";
		return s;
	}




	@Override
	public void onBackPressed() {
		super.onBackPressed();
		editor.putInt("kitValue", MainActivity.spawnRateKit);
		editor.putInt("shieldValue", MainActivity.spawnRateShield);
		editor.putInt("freezeValue", MainActivity.spawnRateFreeze);
		editor.putInt("bombValue", MainActivity.spawnRateBomb);
		editor.commit();


	}



}
