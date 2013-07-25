package com.klusman.keepup.screens;

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
	
	Sprite instructionsSprite;
	Texture instructionsTx;
	
	public InstructionsScreen(MainKeepUp game, MainActivity mainActivity){
		this.game = game;
		_mainActivity = mainActivity;
		x = Gdx.graphics.getWidth();
		y = Gdx.graphics.getHeight();
		screenXRefactor = 1000;
		screenRatio = y/x;
		screenYRefactor = (int) (screenRatio * screenXRefactor);
		camera = new OrthographicCamera(screenXRefactor, screenYRefactor);
		Gdx.input.setInputProcessor(this);
		Gdx.input.setCatchBackKey(true);
		batch = new SpriteBatch();
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
		instructionsTx = new Texture(Gdx.files.internal("data/rules.png"));
		instructionsTx.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		TextureRegion creditReg = new TextureRegion(instructionsTx, 0, 0, instructionsTx.getWidth(), instructionsTx.getHeight() - 200);
		float stretchRatioCredits = (float) (instructionsTx.getHeight() - 200) / instructionsTx.getWidth();
		instructionsSprite = new Sprite(creditReg);
		instructionsSprite.setSize(screenXRefactor,  screenXRefactor * stretchRatioCredits);  
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
		instructionsTx.dispose();
	}

	@Override
	public boolean keyDown(int keycode) {
		if(keycode == Keys.BACK){
			   game.setScreen(new MainMenu(game, _mainActivity));
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
		return false;
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
