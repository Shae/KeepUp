package com.klusman.keepup.screens;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
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
import com.klusman.keepup.MainKeepUp;
import com.klusman.keepup.tweens.SpriteTween;


public class MainMenu implements Screen, InputProcessor{
	
	MainKeepUp game;
	
	private OrthographicCamera camera;
	public static int screenXRefactor;
	public static int screenYRefactor;
	float x;
	float y;
	float screenRatio;
	TweenManager manager;
	
	SpriteBatch batch;
	Texture titleTx;
	Sprite titleSprite;
	
	Sprite playBtn;
	Texture playBtnTxUp;
	
	Sprite creditsBtn;
	Texture CredBtnUp;
	
	Sprite instructionsBtn;
	Texture instBtnTxUp;
	
	
	public MainMenu (MainKeepUp game){
		this.game = game;
		x = Gdx.graphics.getWidth();
		y = Gdx.graphics.getHeight();
		screenXRefactor = 1000;
		screenRatio = y/x;
		screenYRefactor = (int) (screenRatio * screenXRefactor);
		camera = new OrthographicCamera(screenXRefactor, screenYRefactor);
		Gdx.input.setInputProcessor(this);
		Tween.registerAccessor(Sprite.class, new SpriteTween());
		manager = new TweenManager();
		
	}

	

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void show() {
		
		batch = new SpriteBatch();		

		titleTx = new Texture(Gdx.files.internal("data/splashTitle.png"));
		titleTx.setFilter(TextureFilter.Linear, TextureFilter.Linear);	
		TextureRegion titleRegion = new TextureRegion(titleTx, 0, 0, titleTx.getWidth(), titleTx.getHeight());
		titleSprite = new Sprite(titleRegion);
		titleSprite.setSize(titleTx.getWidth() * 2f,  titleTx.getHeight() * 2f);  
		titleSprite.setOrigin(titleSprite.getWidth()/2, titleSprite.getHeight()/2);
		titleSprite.setPosition(0 - titleSprite.getWidth()/2, 200);
		titleSprite.setRotation(18);
		
		
		playBtnTxUp = new Texture(Gdx.files.internal("data/playBtnUp.png"));
		playBtnTxUp.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		TextureRegion playBtnReg = new TextureRegion(playBtnTxUp, 0, 0, playBtnTxUp.getWidth(), playBtnTxUp.getHeight());
		playBtn = new Sprite(playBtnReg);
		playBtn.setSize(800,  200);  
		playBtn.setOrigin(playBtn.getWidth()/2, playBtn.getHeight()/2);
		playBtn.setPosition(0 - playBtn.getWidth()/2, 0f - playBtn.getHeight()/2);
		playBtn.setColor(1, 1, 1, 0);
		
		
		instBtnTxUp = new Texture(Gdx.files.internal("data/InstrBtnUp.png"));
		instBtnTxUp.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		TextureRegion instBtnReg = new TextureRegion(instBtnTxUp, 0, 0, instBtnTxUp.getWidth(), instBtnTxUp.getHeight());
		instructionsBtn = new Sprite(instBtnReg);
		instructionsBtn.setSize(800,  200);  
		instructionsBtn.setOrigin(instructionsBtn.getWidth()/2, instructionsBtn.getHeight()/2);
		instructionsBtn.setPosition(0 - instructionsBtn.getWidth() / 2, - 250 - (instructionsBtn.getHeight() /2));
		instructionsBtn.setColor(1, 1, 1, 0);
		
		CredBtnUp = new Texture(Gdx.files.internal("data/CreditsBtnUp.png"));
		CredBtnUp.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		TextureRegion credBtnReg = new TextureRegion(CredBtnUp, 0, 0, CredBtnUp.getWidth(), CredBtnUp.getHeight());
		creditsBtn = new Sprite(credBtnReg);
		creditsBtn.setSize(800,  200); 
		creditsBtn.setOrigin(creditsBtn.getWidth()/2, creditsBtn.getHeight()/2);
		creditsBtn.setPosition(0 - creditsBtn.getWidth() /2, -500 - (creditsBtn.getHeight()/ 2));
		creditsBtn.setColor(1, 1, 1, 0);
		
		Tween.to(playBtn, SpriteTween.ALPHA, 1.5f)
		.target(1)
		.ease(TweenEquations.easeInQuad)
		.start(manager);  // start the tween using the passed in manager
		
		Tween.to(instructionsBtn, SpriteTween.ALPHA, 1.5f)
		.target(1)
		.ease(TweenEquations.easeInQuad)
		.start(manager);  // start the tween using the passed in manager
		
		Tween.to(creditsBtn, SpriteTween.ALPHA, 1.5f)
		.target(1)
		.ease(TweenEquations.easeInQuad)	
		.start(manager);  // start the tween using the passed in manager
		
		
	}
	

	
	@Override
	public void render(float delta) {
	
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		camera.update();  // update the camera
		manager.update(delta);  // update manager using the delta time
		batch.setProjectionMatrix(camera.combined);
		
		batch.begin();
			titleSprite.draw(batch);
			playBtn.draw(batch);
			instructionsBtn.draw(batch);
			creditsBtn.draw(batch);
		batch.end();
		
	}
	public void runGame(MainKeepUp game){
		//game.setScreen(new GameScreen(game));
		game.setScreen(new Game(game));
	}

	@Override
	public void hide() {
	 //dispose();
		
	}

	@Override
	public void pause() {

		
	}

	@Override
	public void resume() {
		
		
	}

	@Override
	public void dispose() {
		titleTx.dispose();
		playBtnTxUp.dispose();
		instBtnTxUp.dispose();
		CredBtnUp.dispose();
		
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		Vector2 touchPos = new Vector2();
		touchPos.set(Gdx.input.getX(), Gdx.input.getY());
		Ray cameraRay = camera.getPickRay(touchPos.x, touchPos.y);
		Gdx.app.log(MainKeepUp.TAG, "Touch Ray Coords: X:" + cameraRay.origin.x + " Y:" + cameraRay.origin.y);

		boolean playBool = playBtn.getBoundingRectangle().contains(cameraRay.origin.x, cameraRay.origin.y);
		boolean CreditBool = creditsBtn.getBoundingRectangle().contains(cameraRay.origin.x, cameraRay.origin.y);
		boolean instructionBool = instructionsBtn.getBoundingRectangle().contains(cameraRay.origin.x, cameraRay.origin.y);

		if(playBool == true){
			
			//game.setScreen(new Game(game));
			Gdx.app.log(MainKeepUp.TAG, "PLAY Btn Clicked!");
			runGame(game);
			
		}
		
		if(CreditBool == true){
			
			Gdx.app.log(MainKeepUp.TAG, "Credits Btn Clicked!");
			game.setScreen(new CreditsScreen(game));
		}

		if(instructionBool == true){
		
			Gdx.app.log(MainKeepUp.TAG, "instructions Btn Clicked!");
			game.setScreen(new InstructionsScreen(game));
		}
		
		
		
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
