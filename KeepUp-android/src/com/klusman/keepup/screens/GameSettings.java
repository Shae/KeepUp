package com.klusman.keepup.screens;

import com.klusman.keepup.MainActivity;
import com.klusman.keepup.MainKeepUp;
import com.klusman.keepup.R;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ToggleButton;

public class GameSettings extends Activity {

	SharedPreferences prefs;
	ToggleButton soundTog;
	ToggleButton vibTog;
	RadioGroup RG;
	Boolean DarkOrLight;
	RadioButton dark;
	RadioButton light;

	public GameSettings (){
		
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
					SplashScreen.bgMusic.play();
				}else{
					setPrefSound(false);
					soundTog.setChecked(false);
					SplashScreen.bgMusic.stop();
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
			//light.setChecked(false);
		}else{
			light.setChecked(true);
			//dark.setChecked(false);
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
}
