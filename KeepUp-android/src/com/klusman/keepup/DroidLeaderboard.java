package com.klusman.keepup;

import com.google.android.gms.games.GamesClient;

import android.content.Context;



public class DroidLeaderboard implements Leaderboard {
	
	Context _context;
	GamesClient _gc;
	
	public DroidLeaderboard (Context context, GamesClient gc){
		_context = context;
		_gc = gc;
	}
	
	
	public void submitMyScore(int score) {
		_gc.submitScore("CgkI27yiz5oWEAIQBQ", score);
		
	}

}
