package com.klusman.keepup.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.collision.Ray;
import com.klusman.keepup.MainActivity;
import com.klusman.keepup.MainKeepUp;

public class CreditsScreen implements Screen, InputProcessor{
	MainActivity _mainActivity;
	MainKeepUp game;
	private OrthographicCamera camera;
	public static int screenXRefactor;
	public static int screenYRefactor;
	float x;
	float y;
	float screenRatio;
	private SpriteBatch batch;

	Sprite creditsSprite;
	Texture creditsTx;

	Sprite bugSprite;
	Texture bugTx;


	public CreditsScreen(MainKeepUp game){
		_mainActivity = MainActivity.Instance;
		this.game = game;
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
	public void show() {
		creditsTx = new Texture(Gdx.files.internal("data/creditScreen.png"));
		creditsTx.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		TextureRegion creditReg = new TextureRegion(creditsTx, 0, 0, creditsTx.getWidth(), creditsTx.getHeight() - 200);
		float stretchRatioCredits = (float) (creditsTx.getHeight() - 200) / creditsTx.getWidth();
		creditsSprite = new Sprite(creditReg);
		creditsSprite.setSize(screenXRefactor,  screenXRefactor * stretchRatioCredits);  
		creditsSprite.setOrigin(creditsSprite.getWidth()/2, creditsSprite.getHeight()/2);
		creditsSprite.setPosition(0 - creditsSprite.getWidth()/2, 0 - creditsSprite.getHeight()/2);

		bugTx = new Texture(Gdx.files.internal("data/reportBugBtn.png"));
		bugTx.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		TextureRegion bugReg = new TextureRegion(bugTx, 0, 0, bugTx.getWidth(), bugTx.getHeight());
		bugSprite = new Sprite(bugReg);
		bugSprite.setSize(bugSprite.getWidth()*4,  bugSprite.getHeight()*4);  
		bugSprite.setOrigin(bugSprite.getWidth()/2 , bugSprite.getHeight()/2);
		bugSprite.setPosition(0 - bugSprite.getWidth()/2, -550 - bugSprite.getHeight()/2);

		if(_mainActivity.getSoundBool() == true){
			if(_mainActivity.isMusicPlaying() == false){
				_mainActivity.playBgMusic(true);
			}
		}else{
			if(_mainActivity.isMusicPlaying() == true){
				_mainActivity.playBgMusic(false);
			}
		}
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		camera.update();

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		creditsSprite.draw(batch);
		bugSprite.draw(batch);
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
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
		creditsTx.dispose();
		bugTx.dispose();
	}

	public void runGame(MainKeepUp game){
		game.setScreen(new MainMenu(game));

	}


	///////////  INPUT CONTROLLS  /////////////

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

		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		Vector2 touchPos = new Vector2();
		touchPos.set(Gdx.input.getX(), Gdx.input.getY());
		Ray cameraRay = camera.getPickRay(touchPos.x, touchPos.y);

		boolean bugBool = bugSprite.getBoundingRectangle().contains(cameraRay.origin.x, cameraRay.origin.y);

		if(bugBool == true){
			_mainActivity.bugReport();
		}

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

}  // END GAME SCREEN
