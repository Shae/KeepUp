package com.klusman.keepup;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class MainActivity extends AndroidApplication /*implements GameHelperListener, GoogleInterface*/ {
//	private GameHelper aHelper;
//
//	private OnLeaderboardScoresLoadedListener theLeaderboardListener;

//	public MainActivity(){
//		aHelper = new GameHelper(this);
//		aHelper.enableDebugLog(true, "MYTAG");
//
//		//create a listener for getting raw data back from leaderboard
//		theLeaderboardListener = new OnLeaderboardScoresLoadedListener() {
//
//			public void onLeaderboardScoresLoaded(int arg0, LeaderboardBuffer arg1, LeaderboardScoreBuffer arg2) {
//
//				for(int i = 0; i <= arg2.getCount(); i++){
//					System.out.println(arg2.get(i).getScoreHolderDisplayName() + " : " + arg2.get(i).getDisplayScore());
//				}
//			}
//		};
//	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
		cfg.useGL20 = false;
		//aHelper.setup(this);
		initialize(new MainKeepUp(), cfg);
	}

//	@Override
//	public void onStart(){
//		super.onStart();
//		aHelper.onStart(this);
//	}
//
//	@Override
//	public void onStop(){
//		super.onStop();
//		aHelper.onStop();
//	}
//
//	@Override
//	public void onActivityResult(int request, int response, Intent data) {
//		super.onActivityResult(request, response, data);
//		aHelper.onActivityResult(request, response, data);
//	}
//
//	public void onSignInFailed() {
//		System.out.println("sign in failed");
//	}
//
//	public void onSignInSucceeded() {
//		System.out.println("sign in succeeded");
//	}
//
//	public void Login() {
//		try {
//			runOnUiThread(new Runnable(){
//
//				//@Override
//				public void run(){
//					aHelper.beginUserInitiatedSignIn();
//				}
//			});
//		}catch (final Exception ex){
//
//		}
//	}
//
//	public void LogOut() {
//		try {
//			runOnUiThread(new Runnable(){
//
//				//@Override
//				public void run(){
//					aHelper.signOut();
//				}
//			});
//		}catch (final Exception ex){
//
//		}
//
//	}
//
//	public boolean getSignedIn() {
//		return aHelper.isSignedIn();
//	}
//
//	public void submitScore(int _score) {
//		System.out.println("in submit score");
//		aHelper.getGamesClient().submitScore(getString(R.string.leaderboard_ID), _score);
//	}
//
//	public void getScores() {
//		startActivityForResult(aHelper.getGamesClient().getLeaderboardIntent(getString(R.string.leaderboard_ID)), 105);
//	}
//
//	public void getScoresData() {
//		aHelper.getGamesClient().loadPlayerCenteredScores(theLeaderboardListener,
//				getString(R.string.leaderboard_ID),
//				1,
//				1,
//				25) ;
//	}
}