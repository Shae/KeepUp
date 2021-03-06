package com.klusman.keepup.screens;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
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
	public static Sound bounce1;
	public static Sound bounce2;
	public static Sound bounce3;
	public String textureAddress;
	public boolean SignedIn = false;
	boolean Online = false;
	boolean redBallShowing = false;

	SpriteBatch batch;

	Texture titleTx;
	Sprite titleSprite;

	Texture infoTx;
	Sprite infoSprite;

	Texture ballGroupPng;

	Sprite RedBallButton;
	TextureRegion RedButton;

	Sprite BlueBallButton;
	TextureRegion BlueButton;

	Sprite GreenBallButton;
	TextureRegion GreenButton;

	Sprite OrngBallButton;
	TextureRegion OrngButton;

	Sprite PurpleBallButton;
	TextureRegion PurpleButton;

	Sprite YellowBallButton;
	TextureRegion YellowButton;

	Sprite googlePlay;
	Texture googTx;

	Sprite googleOut;
	Texture googOutTx;

	TweenCallback cbBlue;
	TweenCallback cbGrn;
	TweenCallback cbOrng;
	TweenCallback cbPurp;
	TweenCallback cbRed;
	TweenCallback cbYlw;

	TweenCallback cbBOUNCE1;
	TweenCallback cbBOUNCE2;
	TweenCallback cbBOUNCE3;

	public MainMenu (MainKeepUp game){
		_mainActivity = MainActivity.Instance;
		this.game = game;
		x = Gdx.graphics.getWidth();
		y = Gdx.graphics.getHeight();
		screenXRefactor = 1000;
		screenRatio = y/x;
		screenYRefactor = (int) (screenRatio * screenXRefactor);
		camera = new OrthographicCamera(screenXRefactor, screenYRefactor);
		Gdx.input.setInputProcessor(this);

		checkOnLineStatus();


		Tween.registerAccessor(Sprite.class, new SpriteTween());
		manager = new TweenManager();
		textureAddress = "data/menusButtons.png";
		bounce1 = Gdx.audio.newSound(Gdx.files.internal("audio/ballbounce04.wav"));
		bounce2 = Gdx.audio.newSound(Gdx.files.internal("audio/ballBounce03.wav"));
		bounce3 = Gdx.audio.newSound(Gdx.files.internal("audio/ballBounce02.wav"));

	}

	public void checkOnLineStatus(){
		Online = _mainActivity.isOnline();
		if(Online == true){	
			SignedIn = _mainActivity.isPlayerSignedIn();
		}else{
			SignedIn = false;
		}
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

		infoTx = new Texture(Gdx.files.internal("data/infoBtn.png"));
		infoTx.setFilter(TextureFilter.Linear, TextureFilter.Linear);	
		TextureRegion infoRegion = new TextureRegion(infoTx, 0, 0, infoTx.getWidth(), infoTx.getHeight());
		infoSprite = new Sprite(infoRegion);
		infoSprite.setSize(100,  100);  
		infoSprite.setOrigin(infoSprite.getWidth()/2, infoSprite.getHeight()/2);
		infoSprite.setPosition( (500 - (infoSprite.getWidth()/2)) * -1, (((screenYRefactor/2) - 75 ) - (infoSprite.getHeight()/2)) );

		googTx = new Texture(Gdx.files.internal("data/signInGoogle.png"));
		googTx.setFilter(TextureFilter.Linear, TextureFilter.Linear);	
		TextureRegion googRegion = new TextureRegion(googTx, 0, 0, googTx.getWidth(), 92);
		googlePlay = new Sprite(googRegion);
		googlePlay.setSize(googlePlay.getWidth() * 1.3f,  googlePlay.getHeight() * 1.3f);  
		googlePlay.setOrigin(googlePlay.getWidth()/2, googlePlay.getHeight()/2);
		googlePlay.setPosition(-480, (screenYRefactor/2 * -1) + 20);

		googOutTx = new Texture(Gdx.files.internal("data/signOutGoogle.png"));
		googOutTx.setFilter(TextureFilter.Linear, TextureFilter.Linear);	
		TextureRegion googOutRegion = new TextureRegion(googOutTx, 0, 0, googOutTx.getWidth(), 92);
		googleOut = new Sprite(googOutRegion);
		googleOut.setSize(googleOut.getWidth() * 1.3f,  googleOut.getHeight() * 1.3f);  
		googleOut.setOrigin(googleOut.getWidth()/2, googleOut.getHeight()/2);
		googleOut.setPosition(-480, (screenYRefactor/2 * -1) + 20);


		ballGroupPng = new Texture(Gdx.files.internal("data/ballGroupIcons2.png"));
		ballGroupPng.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		BlueButton = new TextureRegion(ballGroupPng, 0, 0, ballGroupPng.getWidth(), ballGroupPng.getWidth());
		GreenButton = new TextureRegion(ballGroupPng, 0, 256, ballGroupPng.getWidth(), ballGroupPng.getWidth());
		OrngButton  = new TextureRegion(ballGroupPng, 0, 512, ballGroupPng.getWidth(), ballGroupPng.getWidth());
		PurpleButton  = new TextureRegion(ballGroupPng, 0, 768, ballGroupPng.getWidth(), ballGroupPng.getWidth());
		RedButton = new TextureRegion(ballGroupPng, 0, 1024, ballGroupPng.getWidth(), ballGroupPng.getWidth());
		YellowButton = new TextureRegion(ballGroupPng, 0, 1280, ballGroupPng.getWidth(), ballGroupPng.getWidth());

		BlueBallButton = new Sprite(BlueButton);
		BlueBallButton.setSize(20,  20); 
		BlueBallButton.setPosition(-10, - 90);
		BlueBallButton.setColor(1, 1, 1, 0);

		GreenBallButton = new Sprite(GreenButton);
		GreenBallButton.setSize(20,  20); 
		GreenBallButton.setPosition(-10, - 90);
		GreenBallButton.setColor(1, 1, 1, 0);

		PurpleBallButton = new Sprite(PurpleButton);
		PurpleBallButton.setSize(20,  20); 
		PurpleBallButton.setPosition(-10, - 90);
		PurpleBallButton.setColor(1, 1, 1, 0);

		RedBallButton = new Sprite(RedButton);
		RedBallButton.setSize(20,  20); 
		RedBallButton.rotate(20);
		RedBallButton.setPosition(-10, - 90);
		RedBallButton.setColor(1, 1, 1, 0);

		OrngBallButton = new Sprite(OrngButton);
		OrngBallButton.setSize(20,  20); 
		OrngBallButton.setPosition(-10, - 90);
		OrngBallButton.setColor(1, 1, 1, 0);

		YellowBallButton = new Sprite(YellowButton);
		YellowBallButton.setSize(20,  20); 
		YellowBallButton.setRotation(10);
		YellowBallButton.setPosition(-10, - 90);
		YellowBallButton.setColor(1, 1, 1, 0);

		cbGrn = new TweenCallback() {
			@Override
			public void onEvent(int type, BaseTween<?> source) {
				tweenPurple();  
			}
		};

		cbPurp = new TweenCallback() {
			@Override
			public void onEvent(int type, BaseTween<?> source) {
				tweenBlue();  
			}
		};

		cbBlue = new TweenCallback() {
			@Override
			public void onEvent(int type, BaseTween<?> source) {
				tweenOrng();  
			}
		};

		cbOrng = new TweenCallback() {
			@Override
			public void onEvent(int type, BaseTween<?> source) {
				tweenYellow(); 
			}
		};

		cbYlw = new TweenCallback() {
			@Override
			public void onEvent(int type, BaseTween<?> source) {
				if(SignedIn == true){
					tweenRed();  
				}
			}
		};



		cbRed = new TweenCallback() {
			@Override
			public void onEvent(int type, BaseTween<?> source) {
				redBallShowing = true;
			}
		};

		cbBOUNCE1 = new TweenCallback() {
			@Override
			public void onEvent(int type, BaseTween<?> source) {
				bounce1.play(.5f);
			}
		}; 

		cbBOUNCE2 = new TweenCallback() {
			@Override
			public void onEvent(int type, BaseTween<?> source) {
				bounce2.play(.8f);
			}
		}; 

		cbBOUNCE3 = new TweenCallback() {
			@Override
			public void onEvent(int type, BaseTween<?> source) {
				bounce3.play();
			}
		}; 

		tweenGreen();
	}


	private void tweenGreen(){

		Tween.to(GreenBallButton, SpriteTween.ALPHA, .5f)
		.target(1)
		.ease(TweenEquations.easeInQuad)
		.setCallback(cbGrn)
		.setCallbackTriggers(TweenCallback.COMPLETE)
		.start(manager); 

		Tween.to(GreenBallButton, SpriteTween.SCALE_XY, .5f)
		.target(400, 400)
		.ease(TweenEquations.easeInQuad)
		.start(manager);  

		Tween.to(GreenBallButton, SpriteTween.POSITION_XY, .5f)
		.target(-490 , -200)
		.ease(TweenEquations.easeInQuad)
		.setCallback(cbBOUNCE1)
		.setCallbackTriggers(TweenCallback.COMPLETE)
		.start(manager); 			
	}


	private void tweenYellow(){
		Tween.to(YellowBallButton, SpriteTween.ALPHA, .2f)
		.target(1)
		.ease(TweenEquations.easeInQuad)
		.setCallback(cbYlw)
		.setCallbackTriggers(TweenCallback.COMPLETE)
		.start(manager); 

		Tween.to(YellowBallButton, SpriteTween.SCALE_XY, .2f)
		.target(230, 230)
		.ease(TweenEquations.easeInQuad)
		.start(manager);  

		Tween.to(YellowBallButton, SpriteTween.POSITION_XY, .2f)
		.target(230 , -425)
		.ease(TweenEquations.easeInQuad)
		.setCallback(cbBOUNCE1)
		.setCallbackTriggers(TweenCallback.COMPLETE)
		.start(manager);  
	}


	private void tweenPurple(){
		Tween.to(PurpleBallButton, SpriteTween.ALPHA, .3f)
		.target(1)
		.ease(TweenEquations.easeInQuad)
		.setCallback(cbPurp)
		.setCallbackTriggers(TweenCallback.COMPLETE)
		.start(manager);  

		Tween.to(PurpleBallButton, SpriteTween.SCALE_XY, .3f)
		.target(280, 280)
		.ease(TweenEquations.easeInQuad)
		.start(manager);  

		Tween.to(PurpleBallButton, SpriteTween.POSITION_XY, .3f)
		.target(-200 , -600)
		.ease(TweenEquations.easeInQuad)
		.setCallback(cbBOUNCE2)
		.setCallbackTriggers(TweenCallback.COMPLETE)
		.start(manager);  
	}


	private void tweenRed(){
		Tween.to(RedBallButton, SpriteTween.ALPHA, .25f)
		.target(1)
		.ease(TweenEquations.easeInQuad)
		.setCallback(cbRed)
		.setCallbackTriggers(TweenCallback.COMPLETE)
		.start(manager);  

		Tween.to(RedBallButton, SpriteTween.SCALE_XY, .25f)
		.target(260, 260)
		.ease(TweenEquations.easeInQuad)
		.start(manager);  

		Tween.to(RedBallButton, SpriteTween.POSITION_XY, .25f)
		.target(-80 , -250)
		.ease(TweenEquations.easeInQuad)
		.setCallback(cbBOUNCE2)
		.setCallbackTriggers(TweenCallback.COMPLETE)
		.start(manager);  
	}


	private void tweenBlue(){
		Tween.to(BlueBallButton, SpriteTween.ALPHA, .4f)
		.target(1)
		.ease(TweenEquations.easeInQuad)
		.setCallback(cbBlue)
		.setCallbackTriggers(TweenCallback.COMPLETE)
		.start(manager);  

		Tween.to(BlueBallButton, SpriteTween.SCALE_XY, .4f)
		.target(250, 250)
		.ease(TweenEquations.easeInQuad)
		.start(manager);  

		Tween.to(BlueBallButton, SpriteTween.POSITION_XY, .4f)
		.target(90, 0)
		.ease(TweenEquations.easeInQuad)
		.setCallback(cbBOUNCE3)
		.setCallbackTriggers(TweenCallback.COMPLETE)
		.start(manager);  	
	}


	private void tweenOrng(){
		Tween.to(OrngBallButton, SpriteTween.ALPHA, .15f)
		.target(1)
		.ease(TweenEquations.easeInQuad)
		.setCallback(cbOrng)
		.setCallbackTriggers(TweenCallback.COMPLETE)
		.start(manager);  

		Tween.to(OrngBallButton, SpriteTween.SCALE_XY, .15f)
		.target(200, 200)
		.ease(TweenEquations.easeInQuad)
		.start(manager);  

		Tween.to(OrngBallButton, SpriteTween.POSITION_XY, .15f)
		.target(-490 , -450)
		.ease(TweenEquations.easeInQuad)
		.setCallback(cbBOUNCE3)
		.setCallbackTriggers(TweenCallback.COMPLETE)
		.start(manager); 
	}


	private void showGoogleLogOutButton(Boolean bool){
		if(bool == true){
			googleOut.draw(batch);

		}else if(bool == false){
			googlePlay.draw(batch);
		}
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

		GreenBallButton.draw(batch);
		PurpleBallButton.draw(batch);	
		BlueBallButton.draw(batch);
		OrngBallButton.draw(batch);
		YellowBallButton.draw(batch);
		infoSprite.draw(batch, 0.4f);
		if(Online == true){
			if(SignedIn == true){
				showGoogleLogOutButton(true);
				RedBallButton.draw(batch);
			}else{
				showGoogleLogOutButton(false);
			}
		}
		batch.end();

	}
	public void runGame(MainKeepUp game){
		game.setScreen(new Game(game));
	}

	@Override
	public void hide() {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {
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
	public void dispose() {
		titleTx.dispose();
		ballGroupPng.dispose();
		googTx.dispose();
		googOutTx.dispose();
		infoTx.dispose();

	}

	@Override
	public boolean keyDown(int keycode) {
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

		boolean playBool = GreenBallButton.getBoundingRectangle().contains(cameraRay.origin.x, cameraRay.origin.y);
		boolean leaderboardBool = PurpleBallButton.getBoundingRectangle().contains(cameraRay.origin.x, cameraRay.origin.y);
		boolean instructionBool = BlueBallButton.getBoundingRectangle().contains(cameraRay.origin.x, cameraRay.origin.y);
		boolean settingsBool = OrngBallButton.getBoundingRectangle().contains(cameraRay.origin.x, cameraRay.origin.y);
		boolean optionsBool = YellowBallButton.getBoundingRectangle().contains(cameraRay.origin.x, cameraRay.origin.y);
		boolean achievementsBool = false;




		if(playBool == true){
			bounce1.play();
		}

		if(leaderboardBool == true){
			bounce2.play();
		}

		if(instructionBool == true){
			bounce3.play();

		}

		if(achievementsBool == true){
			bounce1.play();

		}

		if(settingsBool == true){
			bounce2.play();

		}

		if(optionsBool == true){
			bounce3.play();
		}


		return false;
	}


	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		Vector2 touchPos = new Vector2();
		touchPos.set(Gdx.input.getX(), Gdx.input.getY());
		Ray cameraRay = camera.getPickRay(touchPos.x, touchPos.y);

		boolean playBool = GreenBallButton.getBoundingRectangle().contains(cameraRay.origin.x, cameraRay.origin.y);
		boolean leaderboardBool = PurpleBallButton.getBoundingRectangle().contains(cameraRay.origin.x, cameraRay.origin.y);
		boolean instructionBool = BlueBallButton.getBoundingRectangle().contains(cameraRay.origin.x, cameraRay.origin.y);
		boolean settingsBool = OrngBallButton.getBoundingRectangle().contains(cameraRay.origin.x, cameraRay.origin.y);
		boolean optionsBool = YellowBallButton.getBoundingRectangle().contains(cameraRay.origin.x, cameraRay.origin.y);
		boolean infoBool = infoSprite.getBoundingRectangle().contains(cameraRay.origin.x, cameraRay.origin.y);

		boolean achievementsBool = false;
		boolean googInBool = false;
		boolean googOutBool = false;

		if(infoBool == true){
			game.setScreen(new CreditsScreen(game));	
		}

		if(playBool == true){
			game.setScreen(new DifficultySetupScreen(game));	
		}

		if(leaderboardBool == true){
			if(SignedIn == true){
				_mainActivity.whichLeaderboard();
			}else{
				_mainActivity.getLocalLeaderboard();
			}
		}

		if(redBallShowing == true){
			achievementsBool = RedBallButton.getBoundingRectangle().contains(cameraRay.origin.x, cameraRay.origin.y);
		}

		if(achievementsBool == true){		
			_mainActivity.getAchievements();  // Testing Achievements Page // WORKS
		}

		if(instructionBool == true){		
			game.setScreen(new InstructionsScreen(game));
		}

		if(settingsBool == true){		
			_mainActivity.startUserPrefsPage();
		}

		if(optionsBool == true){		
			_mainActivity.startResourcePage();  // Testing resource Page // WORKS
		}



		if(Online == true){

			if(SignedIn == true){
				googOutBool = googleOut.getBoundingRectangle().contains(cameraRay.origin.x, cameraRay.origin.y);
			}else if (SignedIn == false){
				googInBool = googlePlay.getBoundingRectangle().contains(cameraRay.origin.x, cameraRay.origin.y);	
			}
		}

		if(googOutBool == true){

			try {
				_mainActivity.LogOut();
				redBallShowing = false;
				SignedIn = false;
				_mainActivity.setUserName(""); // resets the name to nil upon log out for other users
			} catch (Exception e) {	
				e.printStackTrace();
			}
		}

		if(googInBool == true){

			try {
				_mainActivity.Login();
				SignedIn = true;
				tweenRed(); // to build the red button
			} catch (Exception e) {
				e.printStackTrace();
			}

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
}
