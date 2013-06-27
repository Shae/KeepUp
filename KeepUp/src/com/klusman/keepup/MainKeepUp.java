package com.klusman.keepup;


import com.badlogic.gdx.Game;
import com.klusman.keepup.screens.SplashScreen;






public class MainKeepUp extends Game {

	public static final String TAG = "KeepUp";
	public static final String VERSION = "0.4 Beta (MGD)";
	
	@Override
	public void create() {
		setScreen( new SplashScreen(this));
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		
	}

	@Override
	public void render() {
		super.render();
		
	}

	@Override
	public void pause() {
		super.pause();
		
	}

	@Override
	public void resume() {
		super.resume();
		
	}

	@Override
	public void dispose() {
		super.dispose();
		
	}

	
}  // END MAIN




