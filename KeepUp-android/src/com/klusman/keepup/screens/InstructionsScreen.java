package com.klusman.keepup.screens;

import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.utils.Array;
import com.klusman.keepup.MainActivity;
import com.klusman.keepup.MainKeepUp;

public class InstructionsScreen implements Screen, InputProcessor{

	MainActivity _mainActivity;
	MainKeepUp game;
	private OrthographicCamera camera;
	public static int screenXRefactor;
	public static int screenYRefactor;
	float x;
	float y;
	float screenRatio;
	private SpriteBatch batch;
	
	int touchCount = 0;
	Texture instructionsTx1;
	Texture instructionsTx2;
	Texture instructionsTx3;
	Texture instructionsTx4;
	Texture instructionsTx5;
	Texture instructionsTx6;
	Sprite instructionsSprite;
	int textureRegionLengthByRatio;
	
	TextureRegion instReg1;
	TextureRegion instReg2;
	TextureRegion instReg3;
	TextureRegion instReg4;
	TextureRegion instReg5;
	TextureRegion instReg6;
	Array<TextureRegion> InstRegionHolder;
	
	
	
	
	public InstructionsScreen(MainKeepUp game){
		this.game = game;
		_mainActivity = MainActivity.Instance;
		x = Gdx.graphics.getWidth();
		y = Gdx.graphics.getHeight();
		screenXRefactor = 1000;
		screenRatio = y/x;
		screenYRefactor = (int) (screenRatio * screenXRefactor);
		textureRegionLengthByRatio = (int) ( screenRatio * 500);
		camera = new OrthographicCamera(screenXRefactor, screenYRefactor);
		Gdx.input.setInputProcessor(this);
		Gdx.input.setCatchBackKey(true);
		batch = new SpriteBatch();
		touchCount = 0;
		InstRegionHolder = new Array<TextureRegion>();
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		
		instructionsSprite.draw(batch);
			
		batch.end();
		
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void show() {
	
		//instructionsTx = new Texture(Gdx.files.internal("data/InstBgPages.png"));
		instructionsTx1 = new Texture(Gdx.files.internal("data/instPg1.png"));
		instructionsTx1.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		Log.i(MainKeepUp.TAG, "500x by " + textureRegionLengthByRatio + "y");
		instReg1 = new TextureRegion(instructionsTx1,    0, 0, 500, textureRegionLengthByRatio);
		InstRegionHolder.add(instReg1);
		
		instructionsTx2 = new Texture(Gdx.files.internal("data/instPg2.png"));
		instructionsTx2.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		Log.i(MainKeepUp.TAG, "500x by " + textureRegionLengthByRatio + "y");
		instReg2 = new TextureRegion(instructionsTx2,    0, 0, 500, textureRegionLengthByRatio);
		InstRegionHolder.add(instReg2);
		
		instructionsTx3 = new Texture(Gdx.files.internal("data/instPg3.png"));
		instructionsTx3.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		Log.i(MainKeepUp.TAG, "500x by " + textureRegionLengthByRatio + "y");
		instReg3 = new TextureRegion(instructionsTx3,    0, 0, 500, textureRegionLengthByRatio);
		InstRegionHolder.add(instReg3);
		
		instructionsTx4 = new Texture(Gdx.files.internal("data/instPg4.png"));
		instructionsTx4.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		Log.i(MainKeepUp.TAG, "500x by " + textureRegionLengthByRatio + "y");
		instReg4 = new TextureRegion(instructionsTx4,    0, 0, 500, textureRegionLengthByRatio);
		InstRegionHolder.add(instReg4);
		
		instructionsTx5 = new Texture(Gdx.files.internal("data/instPg5.png"));
		instructionsTx5.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		Log.i(MainKeepUp.TAG, "500x by " + textureRegionLengthByRatio + "y");
		instReg5 = new TextureRegion(instructionsTx5,    0, 0, 500, textureRegionLengthByRatio);
		InstRegionHolder.add(instReg5);
		
		instructionsTx6 = new Texture(Gdx.files.internal("data/instPg6.png"));
		instructionsTx6.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		Log.i(MainKeepUp.TAG, "500x by " + textureRegionLengthByRatio + "y");
		instReg6 = new TextureRegion(instructionsTx6,    0, 0, 500, textureRegionLengthByRatio);
		InstRegionHolder.add(instReg6);

		instructionsSprite = new Sprite(InstRegionHolder.get(0));
		//instructionsSprite = new Sprite(instReg1);
		instructionsSprite.setSize(screenXRefactor,  screenYRefactor);  
		instructionsSprite.setOrigin(instructionsSprite.getWidth()/2, instructionsSprite.getHeight()/2);
		instructionsSprite.setPosition(0 - instructionsSprite.getWidth()/2, 0 - instructionsSprite.getHeight()/2);
		
	}

	@Override
	public void hide() {
		dispose();
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void dispose() {
		instructionsTx1.dispose();
		instructionsTx2.dispose();
		instructionsTx3.dispose();
		instructionsTx4.dispose();
		instructionsTx5.dispose();
		instructionsTx6.dispose();
	}

	@Override
	public boolean keyDown(int keycode) {
		if(keycode == Keys.BACK){
			   game.setScreen(new MainMenu(game));
	        }
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		Vector2 touchPos = new Vector2();
		touchPos.set(Gdx.input.getX(), Gdx.input.getY());
		Ray cameraRay = camera.getPickRay(touchPos.x, touchPos.y);

		boolean touchScreenDown = instructionsSprite.getBoundingRectangle().contains(cameraRay.origin.x, cameraRay.origin.y);
		if(touchScreenDown == true){
			touchCount = touchCount +1;
			if(touchCount == 1){
				instructionsSprite.setRegion(InstRegionHolder.get(1));
			}
			else if(touchCount == 2){
				instructionsSprite.setRegion(InstRegionHolder.get(2));	
			}
			else if(touchCount == 3){
				instructionsSprite.setRegion(InstRegionHolder.get(3));
				
			}
			else if(touchCount == 4){
				instructionsSprite.setRegion(InstRegionHolder.get(4));
				
			}
			else if(touchCount == 5){
				instructionsSprite.setRegion(InstRegionHolder.get(5));
				
			}
			else if(touchCount == 6){
				game.setScreen(new MainMenu(game));
				touchCount = 0;
			}
			else{
				instructionsSprite.setRegion(instReg1);
			}
		}
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

}
