package com.klusman.keepup.screens;

import com.klusman.keepup.MainKeepUp;
import com.klusman.keepup.R;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class ResourceManagerActivity extends Activity{

	int MaxPoints = 100;
	int res1 = 25;
	int res2 = 25;
	int res3 = 25;
	int res4 = 25;
	int allPointsSpent = 100;
	int PointsLeft = 100 - allPointsSpent;
	
	
	
	
	


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		Log.i(MainKeepUp.TAG, "step 1");
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
      	setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
      	setContentView(com.klusman.keepup.R.layout.resource_manager); 
      	
      	
		final TextView textLeft = (TextView)findViewById(R.id.pointsText);
		textLeft.setText(getPointsString());

		Button up1 = (Button)findViewById(R.id.plusBtn1);
		Button up2 = (Button)findViewById(R.id.plusBtn2);
		Button up3 = (Button)findViewById(R.id.plusBtn3);
		Button up4 = (Button)findViewById(R.id.plusBtn4);

		Button dwn1 = (Button)findViewById(R.id.minusBtn1);
		Button dwn2 = (Button)findViewById(R.id.minusBtn2);
		Button dwn3 = (Button)findViewById(R.id.minusBtn3);
		Button dwn4 = (Button)findViewById(R.id.minusBtn4);

		

		final TextView tv1 = (TextView)findViewById(R.id.value1);
		tv1.setText(String.valueOf(res1));

		final TextView tv2 = (TextView)findViewById(R.id.value2);
		tv2.setText(String.valueOf(res2));
	
		final TextView tv3 = (TextView)findViewById(R.id.value3);
		tv3.setText(String.valueOf(res3));

		final TextView tv4 = (TextView)findViewById(R.id.value4);
		tv4.setText(String.valueOf(res4));

		
	
		
		up1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(PointsLeft > 0){
					res1 = res1 + 1;
					getPointsLeft();
					tv1.setText(String.valueOf(res1));
					textLeft.setText(getPointsString());
				}
			}
		});
		
		dwn1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(res1 > 1){
					res1 = res1 - 1;
					getPointsLeft();
					tv1.setText(String.valueOf(res1));
					textLeft.setText(getPointsString());
				}
			}
		});
	
		///////////
		
		up2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(PointsLeft > 0){
					res2 = res2 + 1;
					getPointsLeft();
					tv2.setText(String.valueOf(res2));
					textLeft.setText(getPointsString());
				}
			}
		});
		
		dwn2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(res2 + PointsLeft > 1){
					res2 = res2 - 1;
					getPointsLeft();
					tv2.setText(String.valueOf(res2));
					textLeft.setText(getPointsString());
				}
			}
		});
	
		/////////////
		
		up3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(PointsLeft > 0){
					res3 = res3 + 1;
					getPointsLeft();
					tv3.setText(String.valueOf(res3));
					textLeft.setText(getPointsString());
				}
			}
		});
		
		dwn3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(res3 > 1){
					res3 = res3 - 1;
					getPointsLeft();
					tv3.setText(String.valueOf(res3));
					textLeft.setText(getPointsString());
				}
			}
		});
		
		//////////////
		
		up4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(PointsLeft > 0){
					res4 = res4 + 1;
					getPointsLeft();
					tv4.setText(String.valueOf(res4));
					textLeft.setText(getPointsString());
				}
			}
		});
		
		dwn4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(res4 > 1){
					res4 = res4 - 1;
					getPointsLeft();
					tv4.setText(String.valueOf(res4));
					textLeft.setText(getPointsString());
					
				}
			}
		});
		
	
		
		
	}
	
	
	
	private void getPointsLeft(){
		int addAll = res1 + res2 + res3 + res4;
		allPointsSpent = addAll;
		PointsLeft = 100 - allPointsSpent;
		
		
	}
	
	
	public String getPointsString(){
		String s = "You have " + PointsLeft + " points left to spend";
		return s;
	}
	

}
