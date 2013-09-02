package com.klusman.keepup.screens;

import java.util.List;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.klusman.keepup.ListViewAdapter;
import com.klusman.keepup.MainActivity;
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
	int spPos = 0;

	public LocalLeaderboardList(){


	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(com.klusman.keepup.R.layout.local_leaderboard); 

		try {
			_scoreList =  ScoreSource.findAllNoFilter();
		} catch (Exception e) {
			e.printStackTrace();
		}
		buildRadioGrp();
		listBuilder();

	}

	private void listBuilder(){
		lv = (ListView)findViewById(R.id.list);
		lv.setAdapter(new ListViewAdapter(LocalLeaderboardList.this, _scoreList ));
	}

	private void buildRadioGrp(){
		radioGrp = (RadioGroup)findViewById(R.id.radioGrp);
		radioGrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			public void onCheckedChanged(RadioGroup arg0, int id) {
				switch (id) {

				case R.id.radioScore:
					_scoreList =  ScoreSource.findAllHighToLow();
					listBuilder();			
					break;

				case R.id.radioByAlpha:
					_scoreList =  ScoreSource.findAllbyName();
					listBuilder();
					break;

				default:			
					break;
				}
			}
		});
	}

}
