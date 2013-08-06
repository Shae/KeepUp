package com.klusman.keepup.screens;

import com.klusman.keepup.MainActivity;
import com.klusman.keepup.MainKeepUp;
import com.klusman.keepup.R;
import com.klusman.keepup.database.ScoreSource;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.ToggleButton;

public class GameSettings extends Activity {
	MainActivity _mainActivity;
	SharedPreferences prefs;
	ToggleButton soundTog;
	ToggleButton vibTog;
	RadioGroup RG;
	RadioGroup RGAvatar;
	Boolean DarkOrLight;
	RadioButton dark;
	RadioButton light;
	RadioButton kid1;
	RadioButton kid2;
	RadioButton kid3;
	RadioButton kid4;
	RadioButton kid5;
	RadioButton kid6;
	RadioButton kid7;
	Button email;
	Button resetLeaderboard;
	
	
	public GameSettings (){
		_mainActivity = MainActivity.Instance;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_settings);

		prefs = getSharedPreferences("userPrefs", MODE_PRIVATE);

		soundTog = (ToggleButton)findViewById(R.id.togBtnBgSound);
		soundTog.setChecked(getPrefDataSoundBool());
		soundTog.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if(soundTog.isChecked() == true){
					setPrefSound(true);
					soundTog.setChecked(true);
					_mainActivity.bgMusic.play();
				}else{
					setPrefSound(false);
					soundTog.setChecked(false);
					_mainActivity.bgMusic.stop();
				}

			}
		});

		vibTog = (ToggleButton)findViewById(R.id.togBtnVibrate);
		vibTog.setChecked(getPrefDataVibrateBool());
		vibTog.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if(vibTog.isChecked() == true){
					setPrefVibrate(true);
					vibTog.setChecked(true);
					MainActivity.vibrate = true;
				}else{
					setPrefVibrate(false);
					vibTog.setChecked(false);
					MainActivity.vibrate = false;
				}

			}
		});

		buildRadioGrp();
		buildRadioGrpAvatar();

		email = (Button)findViewById(R.id.bugReportBtn);
		email.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(Intent.ACTION_SEND);
				i.setType("message/rfc822");
				i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"shae.klusman@gmail.com"});
				i.putExtra(Intent.EXTRA_SUBJECT, "Bug Report - Dodgeball Extreme " + MainKeepUp.VERSION);
				i.putExtra(Intent.EXTRA_TEXT   , "Please enter a description of the error here: ");
				try {
				    startActivity(Intent.createChooser(i, "Send mail..."));
				} catch (android.content.ActivityNotFoundException ex) {
				    Toast.makeText(GameSettings.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
				}
				
			}
		});
		
		resetLeaderboard = (Button)findViewById(R.id.resetLocalBtn);
		resetLeaderboard.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				resetLeaderboard();
			}
		});
		
	}



	private boolean getPrefDataSoundBool(){
		boolean sound = prefs.getBoolean("soundBool", true);
		if(sound == true){
			soundTog.setChecked(true);
		}else{
			soundTog.setChecked(false);
		}
		return sound;
	}

	private boolean getPrefDataVibrateBool(){
		boolean viberate = prefs.getBoolean("vibrateBool", true);
		if(viberate == true){
			vibTog.setChecked(true);
		}else{
			vibTog.setChecked(false);
		}
		return viberate;
	}

	private void getRadioGrpInfo(){
		boolean darkOrLight = prefs.getBoolean("darkCourt", true);
		if(darkOrLight == true){
			dark.setChecked(true);
		}else{
			light.setChecked(true);
		}
	}

	private void setPrefSound( boolean bool){	
		SharedPreferences.Editor editor = prefs.edit();
		editor.putBoolean("soundBool", bool);
		editor.commit();
	}


	private void setPrefVibrate(boolean bool){
		SharedPreferences.Editor editor = prefs.edit();
		editor.putBoolean("vibrateBool", bool);
		editor.commit();
	}

	private void buildRadioGrp(){
		RG = (RadioGroup)findViewById(R.id.radioGrpSettings);
		dark = (RadioButton)findViewById(R.id.radioCourtColorDark);
		light = (RadioButton)findViewById(R.id.radioCourtColorLight);
		getRadioGrpInfo();
		RG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			SharedPreferences.Editor editor = prefs.edit();
			public void onCheckedChanged(RadioGroup arg0, int id) {
				switch (id) {

				case R.id.radioCourtColorDark:
					editor.putBoolean("darkCourt", true);
					//dark.setChecked(true);
					Log.i(MainKeepUp.TAG, "dark court TRUE");
					editor.commit();
					break;

				case R.id.radioCourtColorLight:	
					editor.putBoolean("darkCourt", false);
					//dark.setChecked(false);
					Log.i(MainKeepUp.TAG, "dark court FALSE");
					editor.commit();
					break;

				default:
					break;
				}
			}
		});
	}

	private void buildRadioGrpAvatar(){
		RGAvatar = (RadioGroup)findViewById(R.id.radioGrpAvatar);
		kid1 = (RadioButton)findViewById(R.id.radioKid1);
		kid2 = (RadioButton)findViewById(R.id.radioKid2);
		kid3 = (RadioButton)findViewById(R.id.radioKid3);
		kid4 = (RadioButton)findViewById(R.id.radioKid4);
		kid5 = (RadioButton)findViewById(R.id.radioKid5);
		kid6 = (RadioButton)findViewById(R.id.radioKid6);
		kid7 = (RadioButton)findViewById(R.id.radioKid7);
		getAvatarRadioInfoAndSet();
		RGAvatar.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			SharedPreferences.Editor editor = prefs.edit();
			public void onCheckedChanged(RadioGroup arg0, int id) {
				switch (id) {
				case R.id.radioKid1:
					//_mainActivity.setAvatar(1);
					editor.putInt("avatarChoice", 1);	
					editor.commit();
					break;
				case R.id.radioKid2:
					//_mainActivity.setAvatar(2);
					editor.putInt("avatarChoice", 2);		
					editor.commit();
					break;
				case R.id.radioKid3:
					//_mainActivity.setAvatar(3);
					editor.putInt("avatarChoice", 3);
					editor.commit();
					break;
				case R.id.radioKid4:
					editor.putInt("avatarChoice", 4);
					editor.commit();
					break;
				case R.id.radioKid5:
					//_mainActivity.setAvatar(5);
					editor.putInt("avatarChoice", 5);
					editor.commit();
					break;
				case R.id.radioKid6:
					//_mainActivity.setAvatar(6);
					editor.putInt("avatarChoice", 6);
					editor.commit();
					break;
				case R.id.radioKid7:
					//_mainActivity.setAvatar(7);
					editor.putInt("avatarChoice", 7);
					editor.commit();
					break;

				default:
					break;
				}
			}
		});
	}
	
	public void getAvatarRadioInfoAndSet(){
		int avatar = prefs.getInt("avatarChoice", 1);
		Log.i(MainKeepUp.TAG, " Avatar choice : " + avatar);
		if( avatar == 1){
			kid1.setChecked(true);
		}else if( avatar == 2){
			kid2.setChecked(true);
		}else if( avatar == 3){
			kid3.setChecked(true);
		}else if( avatar == 4){
			kid4.setChecked(true);
		}else if( avatar == 5){
			kid5.setChecked(true);
		}else if( avatar == 6){
			kid6.setChecked(true);
		}else{
			kid7.setChecked(true);
		}	
	}
	
	public void resetLeaderboard(){

		try {
			runOnUiThread(new Runnable(){

				//@Override
				public void run(){
					AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(GameSettings.this);
					String stringMsg;

					stringMsg = "Would you like to reset the Local Leaderboard?";

					alertDialogBuilder.setTitle("-- Reset Leaderboard --");
					alertDialogBuilder
					.setMessage(stringMsg)
					.setCancelable(false)

					.setPositiveButton("YES",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							ScoreSource ss = new ScoreSource(_mainActivity);
							ss.deleteTableAndRebuild();
							Toast.makeText(GameSettings.this, "Local Leaderboard RESET.", Toast.LENGTH_SHORT).show();
						}

					})
					.setNegativeButton("No",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							dialog.cancel();
							Toast.makeText(GameSettings.this, "Action Canceled.", Toast.LENGTH_SHORT).show();
						}
					});

					// create alert dialog
					AlertDialog alertDialog = alertDialogBuilder.create();

					// show it
					alertDialog.show();
				}
			});
		}catch (final Exception ex){

		}

	}

	
	
}
