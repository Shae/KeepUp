package com.klusman.keepup.screens;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.EditText;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
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
import com.klusman.keepup.tweens.SpriteTween;


public class MainMenu implements Screen, InputProcessor{
	MainActivity _mainActivity;
	MainKeepUp game;
	private OrthographicCamera camera;
	public static int screenXRefactor;
	public static int screenYRefactor;
	float x;
	float y;
	float screenRatio;
	TweenManager manager;
	public static Sound bounce;
	public String textureAddress;
	boolean SignedIn;
	boolean Online;
	
	SpriteBatch batch;
	Texture menuBtns;
	
	Texture titleTx;
	Sprite titleSprite;
	
	Sprite playBtn;
	Sprite creditsBtn;
	Sprite instructionsBtn;

	
	////  Group Btns
	
	Sprite creditsBtn2;
	TextureRegion CredBtnUp2;
	TextureRegion CredBtnDwn2;
	
	Sprite instructionsBtn2;
	TextureRegion instBtnTxUp2;
	TextureRegion instBtnTxDwn2;
	
	Sprite playBtn2;
	TextureRegion playBtnTxUp2;
	TextureRegion playBtnTxDwn2;
	
	Sprite googlePlay;
	Texture googTx;
	
	Sprite googleOut;
	Texture googOutTx;
	
	public MainMenu (MainKeepUp game, MainActivity mainActivity){
		_mainActivity = mainActivity;
		this.game = game;
		checkOnLIneStatus();
		checkLogin();
		x = Gdx.graphics.getWidth();
		y = Gdx.graphics.getHeight();
		screenXRefactor = 1000;
		screenRatio = y/x;
		screenYRefactor = (int) (screenRatio * screenXRefactor);
		camera = new OrthographicCamera(screenXRefactor, screenYRefactor);
		Gdx.input.setInputProcessor(this);
		Tween.registerAccessor(Sprite.class, new SpriteTween());
		manager = new TweenManager();
		textureAddress = "data/menusButtons.png";
		bounce = Gdx.audio.newSound(Gdx.files.internal("audio/ballbounce04.wav"));
		
	}

	public void checkLogin(){
		 _mainActivity.getUsername();  
		    
		if(Online == true){
			SignedIn = _mainActivity.getSignedIn();
		}else{
			SignedIn = false;
		}
	}
	
	public void checkOnLIneStatus(){
		Online = _mainActivity.isOnline();
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
		
		googTx = new Texture(Gdx.files.internal("data/googPlayBtn.png"));
		googTx.setFilter(TextureFilter.Linear, TextureFilter.Linear);	
		TextureRegion googRegion = new TextureRegion(googTx, 0, 0, googTx.getWidth(), googTx.getHeight());
		googlePlay = new Sprite(googRegion);
		googlePlay.setSize(googlePlay.getWidth() * 2f,  googlePlay.getHeight() * 2f);  
		googlePlay.setOrigin(googlePlay.getWidth()/2, googlePlay.getHeight()/2);
		googlePlay.setPosition(-480, (screenYRefactor/2 * -1) + 10);
		
		googOutTx = new Texture(Gdx.files.internal("data/googOut.png"));
		googOutTx.setFilter(TextureFilter.Linear, TextureFilter.Linear);	
		TextureRegion googOutRegion = new TextureRegion(googOutTx, 0, 0, googOutTx.getWidth(), googOutTx.getHeight());
		googleOut = new Sprite(googRegion);
		googleOut.setSize(googleOut.getWidth() * 2f,  googleOut.getHeight() * 2f);  
		googleOut.setOrigin(googleOut.getWidth()/2, googleOut.getHeight()/2);
		googleOut.setPosition(-480, (screenYRefactor/2 * -1) + 10);

		
		menuBtns = new Texture(Gdx.files.internal(textureAddress));
		menuBtns.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		CredBtnDwn2 = new TextureRegion(menuBtns, 0, 0, menuBtns.getWidth()/2, 64);
		CredBtnUp2 = new TextureRegion(menuBtns, 0, 65, menuBtns.getWidth()/2, 64);
		instBtnTxDwn2 = new TextureRegion(menuBtns, 0, 130, menuBtns.getWidth()/2, 64);
		instBtnTxUp2 = new TextureRegion(menuBtns, 0, 195, menuBtns.getWidth()/2, 64);
		playBtnTxDwn2 = new TextureRegion(menuBtns, 0, 260, menuBtns.getWidth()/2, 64);
		playBtnTxUp2 = new TextureRegion(menuBtns, 0, 325, menuBtns.getWidth()/2, 64);
	

		playBtn = new Sprite(playBtnTxUp2);
		playBtn.setSize(800,  200);  
		playBtn.setOrigin(playBtn.getWidth()/2, playBtn.getHeight()/2);
		playBtn.setPosition(0 - playBtn.getWidth()/2, 0f - playBtn.getHeight()/2);
		playBtn.setColor(1, 1, 1, 0);
		
		

		instructionsBtn = new Sprite(instBtnTxUp2);
		instructionsBtn.setSize(800,  200);  
		instructionsBtn.setOrigin(instructionsBtn.getWidth()/2, instructionsBtn.getHeight()/2);
		instructionsBtn.setPosition(0 - instructionsBtn.getWidth() / 2, - 250 - (instructionsBtn.getHeight() /2));
		instructionsBtn.setColor(1, 1, 1, 0);


		creditsBtn = new Sprite(CredBtnUp2);
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
			if(SignedIn == false){
				googlePlay.draw(batch);
			}else{
				googleOut.draw(batch);
			}
		batch.end();
		
	}
	public void runGame(MainKeepUp game){
		game.setScreen(new Game(game, _mainActivity ));
	}

	@Override
	public void hide() {
		
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
		menuBtns.dispose();
		googTx.dispose();
		googOutTx.dispose();
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
		Vector2 touchPos = new Vector2();
		touchPos.set(Gdx.input.getX(), Gdx.input.getY());
		Ray cameraRay = camera.getPickRay(touchPos.x, touchPos.y);
		Gdx.app.log(MainKeepUp.TAG, "Touch Ray Coords: X:" + cameraRay.origin.x + " Y:" + cameraRay.origin.y);

		boolean playBool = playBtn.getBoundingRectangle().contains(cameraRay.origin.x, cameraRay.origin.y);
		boolean CreditBool = creditsBtn.getBoundingRectangle().contains(cameraRay.origin.x, cameraRay.origin.y);
		boolean instructionBool = instructionsBtn.getBoundingRectangle().contains(cameraRay.origin.x, cameraRay.origin.y);
		boolean googBool = googlePlay.getBoundingRectangle().contains(cameraRay.origin.x, cameraRay.origin.y);
		boolean googOutBool = googleOut.getBoundingRectangle().contains(cameraRay.origin.x, cameraRay.origin.y);
		if(playBool == true){
			bounce.play();
			playBtn.setRegion(playBtnTxDwn2);
	
		}
		
		if(CreditBool == true){
			bounce.play();
			creditsBtn.setRegion(CredBtnDwn2);
		
		}

		if(instructionBool == true){
			bounce.play();
			instructionsBtn.setRegion(instBtnTxDwn2);
		}
		
		if(googOutBool == true){
			bounce.play();
			Gdx.app.log(MainKeepUp.TAG, "GOOGLE Log Out TOUCHED");
			try {
				_mainActivity.LogOut();
				Log.i(MainKeepUp.TAG, "LOGGED OUT");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		if(googBool == true){
			bounce.play();
			Gdx.app.log(MainKeepUp.TAG, "GOOGLE TOUCHED");
			if(_mainActivity.isOnline() == true){
				if(_mainActivity.getSignedIn() == true){
					try {
						_mainActivity.LogOut();
						Log.i(MainKeepUp.TAG, "LOGGED OUT");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else{
					try {
						_mainActivity.Login();
						SignedIn = true;
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}
	
		}else{
			try {
				_mainActivity.LogOut();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
		return false;
	}
	

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		Vector2 touchPos = new Vector2();
		touchPos.set(Gdx.input.getX(), Gdx.input.getY());
		Ray cameraRay = camera.getPickRay(touchPos.x, touchPos.y);
		//Gdx.app.log(MainKeepUp.TAG, "Touch Ray Coords: X:" + cameraRay.origin.x + " Y:" + cameraRay.origin.y);

		boolean playBool = playBtn.getBoundingRectangle().contains(cameraRay.origin.x, cameraRay.origin.y);
		boolean CreditBool = creditsBtn.getBoundingRectangle().contains(cameraRay.origin.x, cameraRay.origin.y);
		boolean instructionBool = instructionsBtn.getBoundingRectangle().contains(cameraRay.origin.x, cameraRay.origin.y);
		
		if(playBool == true){
			
			
			Gdx.app.log(MainKeepUp.TAG, "PLAY Btn Clicked!");
			runGame(game);
			
		}
		
		if(CreditBool == true){
			
			Gdx.app.log(MainKeepUp.TAG, "Credits Btn Clicked!");
			game.setScreen(new CreditsScreen(game, _mainActivity));
		}

		if(instructionBool == true){
			
			Gdx.app.log(MainKeepUp.TAG, "instructions Btn Clicked!");
			game.setScreen(new InstructionsScreen(game, _mainActivity));
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
