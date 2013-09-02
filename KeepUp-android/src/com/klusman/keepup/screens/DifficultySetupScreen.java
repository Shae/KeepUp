package com.klusman.keepup.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
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

public class DifficultySetupScreen implements Screen, InputProcessor {

	MainActivity _mainActivity;
	MainKeepUp game;
	private OrthographicCamera camera;
	public static int screenXRefactor;
	public static int screenYRefactor;
	float x;
	float y;
	float screenRatio;
	private SpriteBatch batch;

	Sprite easySprite;
	Sprite mildSprite;
	Sprite hardSprite;
	Texture easyTx;
	Texture mildTx;
	Texture hardTx;
	public Sound bounce1;

	public DifficultySetupScreen (MainKeepUp game){
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
		easyTx = new Texture(Gdx.files.internal("data/easyBtn.png"));
		easyTx.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		TextureRegion easyReg = new TextureRegion(easyTx, 0, 0, easyTx.getWidth(), easyTx.getHeight());
		easySprite = new Sprite(easyReg);
		easySprite.setSize(easySprite.getWidth() * 1.5f,  easySprite.getHeight() * 1.5f);  
		easySprite.setOrigin(easySprite.getWidth()/2, easySprite.getHeight()/2);
		easySprite.setPosition(0 - easySprite.getWidth()/2, 450 - easySprite.getHeight()/2);

		mildTx = new Texture(Gdx.files.internal("data/mildBtn.png"));
		mildTx.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		TextureRegion mildReg = new TextureRegion(mildTx, 0, 0, mildTx.getWidth(), mildTx.getHeight());
		mildSprite = new Sprite(mildReg);
		mildSprite.setSize(mildSprite.getWidth() * 1.5f,  mildSprite.getHeight() * 1.5f);    
		mildSprite.setOrigin(mildSprite.getWidth()/2, mildSprite.getHeight()/2);
		mildSprite.setPosition(0 - mildSprite.getWidth()/2, 0 - mildSprite.getHeight()/2);

		hardTx = new Texture(Gdx.files.internal("data/hardBtn.png"));
		hardTx.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		TextureRegion hardReg = new TextureRegion(hardTx, 0, 0, hardTx.getWidth(), hardTx.getHeight());
		hardSprite = new Sprite(hardReg);
		hardSprite.setSize(hardSprite.getWidth() * 1.5f,  hardSprite.getHeight() * 1.5f);   
		hardSprite.setOrigin(hardSprite.getWidth()/2, hardSprite.getHeight()/2);
		hardSprite.setPosition(0 - hardSprite.getWidth()/2, -450 - hardSprite.getHeight()/2);

		bounce1 = Gdx.audio.newSound(Gdx.files.internal("audio/ballbounce04.wav"));

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
		easySprite.draw(batch);
		mildSprite.draw(batch);
		hardSprite.draw(batch);
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
		easyTx.dispose();
		mildTx.dispose();
		hardTx.dispose();
	}

	public void runGame(MainKeepUp game){
		game.setScreen(new Game(game));

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

		boolean easyBool = easySprite.getBoundingRectangle().contains(cameraRay.origin.x, cameraRay.origin.y);
		boolean mildBool = mildSprite.getBoundingRectangle().contains(cameraRay.origin.x, cameraRay.origin.y);
		boolean hardBool = hardSprite.getBoundingRectangle().contains(cameraRay.origin.x, cameraRay.origin.y);

		if(easyBool == true){	
			bounce1.play(.7f);
			_mainActivity.setDifficulty(1);
			runGame(game);

		}

		if(mildBool == true){
			bounce1.play(.7f);
			_mainActivity.setDifficulty(2);
			runGame(game);

		}

		if(hardBool == true){	
			bounce1.play(.7f);
			_mainActivity.setDifficulty(3);
			runGame(game);

		}

		return true;
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