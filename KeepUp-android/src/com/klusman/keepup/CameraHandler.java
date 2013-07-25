package com.klusman.keepup;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class CameraHandler extends OrthographicCamera{

	MainKeepUp game;
	
	public static OrthographicCamera camera;
	public static int screenXRefactor;
	public static int screenYRefactor;
	float x;
	float y;
	float screenRatio;
	
	public CameraHandler(){
		x = Gdx.graphics.getWidth();
		y = Gdx.graphics.getHeight();
		screenXRefactor = 1000;
		screenRatio = y/x;
		screenYRefactor = (int) (screenRatio * screenXRefactor);
		camera = new OrthographicCamera(screenXRefactor, screenYRefactor);
	}
	
}
