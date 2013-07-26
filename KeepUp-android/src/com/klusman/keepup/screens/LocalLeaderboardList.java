package com.klusman.keepup.screens;

import java.util.List;

import android.app.Activity;
import android.app.ListActivity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.klusman.keepup.ListViewAdapter;
import com.klusman.keepup.MainActivity;
import com.klusman.keepup.MainKeepUp;
import com.klusman.keepup.R;
import com.klusman.keepup.database.ScoreObject;
import com.klusman.keepup.database.ScoreSource;

public class LocalLeaderboardList extends Activity {

	MainActivity _mainActivity;
	int SCORE;
	RadioGroup radioGrp;
	TextView myScore;
	int selectedRadio;
	ListView lv;
	List<ScoreObject> _scoreList;
	
	
	
	public LocalLeaderboardList(){
		
		
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(MainKeepUp.TAG, "LOC LEADER start");
		
		
		Log.i(MainKeepUp.TAG, "LOC LEADER onCreate");
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
      	setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
      	setContentView(com.klusman.keepup.R.layout.local_leaderboard); 
      	try {
			_scoreList =  ScoreSource.findAllNoFilter();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		listBuilder();
		
	}
		
	private void listBuilder(){
		Log.i(MainKeepUp.TAG, "LOC LEADER listBuilder");
		lv = (ListView)findViewById(R.id.list);
		lv.setAdapter(new ListViewAdapter(LocalLeaderboardList.this, _scoreList ));
	}
	
}
