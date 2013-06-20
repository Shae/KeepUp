package com.klusman.keepup;


import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.utils.Array;



public class MainKeepUp implements ApplicationListener, InputProcessor {
	
///////  WORLD VARIABLES ///////////
	
	private static String TAG = "KeepUp";

	public static final int GAME_READY = 0; 
	public static final int GAME_RUNNING = 1; 
	public static final int GAME_PAUSED = 2; 
	public static final int GAME_OVER = 4;

	public static int gameState;

	private OrthographicCamera camera;
	private SpriteBatch batch;
	
	float x;
	float y;
	float screenRatio;
	float deltaTime;
	float elapsedTime;
	
	public static int screenXRefactor;
	public static int screenYRefactor;
	
	int Level;
	
	double randNumXLoc;
	double randNumSize;
	double randNumSpeed;
	
	boolean pauseGame;
	boolean kidMovable;
	boolean kidMove;
	boolean ballCollision;
	boolean gameOver;
	
	private Texture bgTx;
	private Sprite bg;

	private Texture psTx;
	private Sprite ps;

	private Texture goTx;
	private Sprite go;
	
	private Texture restartBtnTx;
	private Sprite restartBtn;

	private Texture kidTx;
	public static Sprite kid;

	public TextureRegion pauseRegion;
	public Texture pauseTx;
	public static Sprite pause;

	public static Sound metalDing;
	public static Sound buzzer;
	public static Sound bounce;
	public static Music bgMusic;

	public Array<Ball> Balls;
	public static Array<LifeMarks> Marks;

/////////  CREATE  ///////////

	@Override
	public void create() {
		gameState = GAME_RUNNING;
		x = Gdx.graphics.getWidth();
		y = Gdx.graphics.getHeight();
		screenXRefactor = 1000;
		screenRatio = y/x;
		screenYRefactor = (int) (screenRatio * screenXRefactor);
		Gdx.input.setInputProcessor(this);
		pauseGame = false;
		gameOver = false;
		camera = new OrthographicCamera(screenXRefactor, screenYRefactor);

		//		Gdx.app.log(TAG, "SCREEN RATIO Y = " + screenYRefactor);
		//		Gdx.app.log(TAG, "DEVICE Width:" + x);  
		//		Gdx.app.log(TAG, "WORLD screenXRefactor:" + screenXRefactor);  
		//		Gdx.app.log(TAG, "DEVICE Height:" + y);  
		//		Gdx.app.log(TAG, "WORLD screenYRefactor:" + screenYRefactor);  

		bgMusic = Gdx.audio.newMusic(Gdx.files.internal("audio/Ttimes.mp3"));	
		bgMusic.setLooping(false);  
		bgMusic.setVolume(0.03f);
		bgMusic.play();

		Balls = new Array<Ball>();
		Marks = new Array<LifeMarks>();

		Level = 1;  // CURRENT GAME LEVEL

		levelBallSet(Level);
		kidMovable = true;
		kidMove = false;

		deltaTime = Gdx.graphics.getDeltaTime();


		batch = new SpriteBatch();

		/// AUDIO  ///
		metalDing = Gdx.audio.newSound(Gdx.files.internal("audio/Metalping.wav"));
		buzzer = Gdx.audio.newSound(Gdx.files.internal("audio/Buzzer.wav"));
		bounce = Gdx.audio.newSound(Gdx.files.internal("audio/Ball_Bounce.wav"));


		//// BACKGROUND
		bgTx = new Texture(Gdx.files.internal("data/bballcourtWhite.png"));
		bgTx.setFilter(TextureFilter.Linear, TextureFilter.Linear);	
		TextureRegion bgRegion = new TextureRegion(bgTx, 0, 0, bgTx.getWidth(), bgTx.getHeight() - 90);
		bg = new Sprite(bgRegion);
		bg.setSize(screenXRefactor,  screenYRefactor);  
		bg.setOrigin(bg.getWidth()/2, bg.getHeight()/2);
		bg.setPosition(0 - bg.getWidth()/2, 0f - bg.getHeight()/2);

		//// PAUSE SCREEN
		psTx = new Texture(Gdx.files.internal("data/pauseScreen.png"));
		psTx.setFilter(TextureFilter.Linear, TextureFilter.Linear);	
		TextureRegion psRegion = new TextureRegion(psTx, 0, 0, psTx.getWidth(), psTx.getHeight());
		ps = new Sprite(psRegion);
		ps.setSize(screenXRefactor,  screenYRefactor);  
		ps.setOrigin(ps.getWidth()/2, ps.getHeight()/2);
		ps.setPosition(0 - ps.getWidth()/2, 0f - ps.getHeight()/2);

		//// GAME OVER SCREEN
		goTx = new Texture(Gdx.files.internal("data/gameOver2.png"));
		goTx.setFilter(TextureFilter.Linear, TextureFilter.Linear);	
		TextureRegion goRegion = new TextureRegion(goTx, 0, 0, goTx.getWidth(), goTx.getHeight());
		go = new Sprite(goRegion);
		go.setSize(screenXRefactor,  screenYRefactor);  
		go.setOrigin(go.getWidth()/2, go.getHeight()/2);
		go.setPosition(0 - go.getWidth()/2, 0f - go.getHeight()/2);

		//// KID
		kidTx = new Texture(Gdx.files.internal("data/kid.png"));
		kidTx.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		TextureRegion kidRegion = new TextureRegion(kidTx, 0, 0, kidTx.getWidth(), kidTx.getHeight());
		kid = new Sprite(kidRegion);
		kid.setSize(100f , 130f );
		kid.setOrigin(kid.getWidth()/2, kid.getHeight()/2);
		kid.setPosition(0 - (kid.getWidth()/2), -screenYRefactor/2 );	


		//// Pause
		pauseTx = new Texture(Gdx.files.internal("data/PauseTabs.png"));
		pauseTx.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		pauseRegion = new TextureRegion(pauseTx, 0, 0, pauseTx.getWidth(), 64);
		pause = new Sprite(pauseRegion);
		pause.setSize(150f , 100f );
		pause.setOrigin(pause.getWidth()/2, pause.getHeight()/2);
		pause.setPosition(0 - pause.getWidth()/2, screenYRefactor/2 - pause.getHeight());	

		//// Restart Btn
		restartBtnTx = new Texture(Gdx.files.internal("data/restartBtn.png"));
		restartBtnTx.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		TextureRegion restartRegion = new TextureRegion(restartBtnTx, 0, 0, restartBtnTx.getWidth(), restartBtnTx.getHeight());
		restartBtn = new Sprite(restartRegion);
		restartBtn.setSize(300f , 150f );
		restartBtn.setOrigin(restartBtn.getWidth()/2, restartBtn.getHeight()/2);
		restartBtn.setPosition(0 - restartBtn.getWidth()/2, -600);	

	} // END CREATE
	
/////////  ALTER GAME  ////////
	
	public void addLifeMark(){
		LifeMarks mark = new LifeMarks();
		Marks.add(mark);
	}

	public void makeNewBall(){
		Ball ball = new Ball();
		float xFloat = 0;

		//// SIZE
		randNumSize = Math.random();
		double rand = ((randNumSize * 100) + 1);
		double rand2 = rand;
		if (rand <= 40){
			rand2 = rand + 41; // Makes a number greater than 41
		} if (rand >= 90){
			rand2 = rand - ((90 - rand ) * 2);  // makes a number less than 90
		}
		float r = (float) (rand2);
		Gdx.app.log(TAG, "Random Size: " + r);

		ball.setSizeXY(r, r);
		Gdx.app.log(TAG, "ball X:" + ball.getSizeX());  
		Gdx.app.log(TAG, "ball Y:" + ball.getSizeY()); 


		//// SPEED
		randNumSpeed = Math.random();
		double sp = ((randNumSize * 5) + 3);
		int spInt = (int)sp;
		Gdx.app.log(TAG, "Speed random " + spInt);

		ball.setXSpeed((float) -spInt);
		ball.setYSpeed((float) -spInt);



		//// STARTING LOCATION
		randNumXLoc = Math.random();
		double xLoc = (randNumXLoc * screenXRefactor) + 1;
		Gdx.app.log(TAG, "Location random " + xLoc);
		int xInt = (int)xLoc;
		if(xInt <= (screenXRefactor/2)){
			xFloat = (float) (xLoc * -1);
			//Gdx.app.log(TAG, "Location random y * -1 = " + yFloat );
			ball.setXPosition(xFloat);
			ball.setXSpeed(ball.getXSpeed() * -1);
			ball.setYSpeed(ball.getYSpeed() * -1);
		}else{
			xFloat = (float) (xLoc - (screenXRefactor/2));
			//Gdx.app.log(TAG, "Location random y - 30 / 100 = " + yFloat );
			ball.setXPosition((xFloat - ball.getBallSprite().getWidth()));
		}

		Balls.add(ball);
	}
	

	public void levelBallSet(int level){
		for(int i = 1; i <= level; i++){
			makeNewBall();
		}
	}

	@Override
	public void dispose() {
		batch.dispose();
		bgTx.dispose();
		kidTx.dispose();
		goTx.dispose();
		pauseTx.dispose();
		psTx.dispose();
		restartBtnTx.dispose();
		
		if(Balls.size > 0){
			for(Ball ball: Balls) {
				ball.ballTx.dispose();
			};
		}
		
		if(Marks.size > 0){
			for(LifeMarks marks: Marks) {
				marks.lifeTx.dispose();
			};
		}
	}

	@Override
	public void render() {	
		switch (gameState) {
		case GAME_READY:
			//Gdx.app.log(TAG, "Game Rdy");
			gameReady();
			break;
		case GAME_RUNNING:
			update(deltaTime);
			//Gdx.app.log(TAG, "Game Running");
			gameRunning();
			break;
		case GAME_PAUSED:
			//Gdx.app.log(TAG, "Game Paused");
			gamePaused();
			break;
		case GAME_OVER:
			//Gdx.app.log(TAG, "Game Over");
			gameOver = true;
			gameOver();
			break;
		}


	}// END RENDER

	public void update(float deltaTime){
		elapsedTime = deltaTime;
		if(elapsedTime / 5 > Balls.size){  // Make a new ball every 5 seconds
			makeNewBall();
		}
	}
	
	public void gameRestart(){
		dispose();
		create();
	}
	
/////////  GAME STATES  //////////////
	
	public void gameReady(){

	}

	private void gameRunning() {
		checkStrikeOut();
		deltaTime += Gdx.graphics.getDeltaTime();   
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		camera.update();

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		bg.draw(batch);
		kid.draw(batch);

		if(Balls.size > 0){
			for(Ball ball: Balls) {
				ball.draw(batch);
			};
		}

		if(Marks.size > 0){
			for(LifeMarks life: Marks) {
				life.draw(batch);
			};
		}

		pause.draw(batch);

		//font.draw(batch, "HELLO", 0, 0);  /// GRRRR  DOES NOT WORK
		batch.end();


		if(Balls.size > 0){
			for(Ball ball: Balls) {
				ballLoopCheckAndSet(ball);
			};
		}



	}

	private void gameOver() {
		kidMovable = false;
		//Gdx.app.log(TAG, "GAME OVER!");
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		camera.update();
		// TODO
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		bg.draw(batch);
		kid.draw(batch);
		
		if(Balls.size > 0){
			for(Ball ball: Balls) {
				ball.draw(batch);
			};
		}

		if(Marks.size > 0){
			for(LifeMarks life: Marks) {
				life.draw(batch);
			};
		}

		go.draw(batch);
		restartBtn.draw(batch);
		//font.draw(batch, "HELLO", 0, 0);  /// GRRRR  DOES NOT WORK
		batch.end();


		if(Balls.size > 0){
			for(Ball ball: Balls) {
				ballLoopCheckOnPause(ball);
			};
		}


	}

	private void gamePaused() {

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		camera.update();

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		bg.draw(batch);
		kid.draw(batch);

		if(Balls.size > 0){
			for(Ball ball: Balls) {
				ball.draw(batch);
			};
		}

		if(Marks.size > 0){
			for(LifeMarks life: Marks) {
				life.draw(batch);
			};
		}
		ps.draw(batch);
		pause.draw(batch);

		//font.draw(batch, "HELLO", 0, 0);  /// GRRRR  DOES NOT WORK
		batch.end();


		if(Balls.size > 0){
			for(Ball ball: Balls) {
				ballLoopCheckOnPause(ball);
			};
		}

	}


////////  SPRITE CHECKS  //////////////////
	
	public void checkStrikeOut(){
		if(Marks.size >= 3){
			gameState = GAME_OVER;
		}
	}

	public void ballLoopCheckAndSet(Ball ball){
		Sprite ballSprite = ball.getBallSprite();


		float xPosition = ball.getXPosition();
		float yPosition = ball.getYPosition();
		int rotationSpeed = ball.getRotationSpeed();
		boolean rotationDirection = ball.getRotation();
		float yPosSpriteHeight = yPosition + ballSprite.getWidth();


		if(xPosition + ballSprite.getHeight() >= (screenXRefactor/2)){
			//Gdx.app.log(TAG, "Out of bounds Down");  
			ball.setXSpeed(ball.getXSpeed() * -1);
			ball.setXPosition(ball.getXPosition() + ball.getXSpeed());
			if(rotationDirection != false){
				ball.setRotationSpeed(rotationSpeed * -1);
			}
			ball.setRotation(true);
			bounce.play();
		}

		if(yPosSpriteHeight >= screenYRefactor / 2){
			//Gdx.app.log(TAG, "Out of bounds Right");
			ball.setYSpeed(ball.getYSpeed() * -1);
			ball.setYPosition(ball.getYPosition() + ball.getYSpeed());
			if(rotationDirection != true){
				ball.setRotationSpeed(rotationSpeed * -1);
			}
			ball.setRotation(false);
			bounce.play();	
		}
		if(xPosition <= (screenXRefactor/2) * -1){
			//Gdx.app.log(TAG, "Out of bounds Up");
			ball.setXSpeed(ball.getXSpeed() * -1);
			ball.setXPosition(ball.getXPosition() + ball.getXSpeed());
			if(rotationDirection != false){
				ball.setRotationSpeed(rotationSpeed * -1);
			}
			ball.setRotation(true);
			bounce.play();
		} 
		if(yPosition <= (screenYRefactor / 2) * -1){
			//Gdx.app.log(TAG, "Out of bounds Left");
			ball.setYSpeed(ball.getYSpeed() * -1);
			ball.setYPosition(ball.getYPosition() + ball.getYSpeed());
			if(rotationDirection != true){
				ball.setRotationSpeed(rotationSpeed * -1);
			}
			ball.setRotation(false);
			bounce.play();	
		}

		ball.setXPosition(ball.getXPosition() + ball.getXSpeed());
		ball.setYPosition(ball.getYPosition() + ball.getYSpeed());
		float bRotate = ballSprite.getRotation() + rotationSpeed;

		float moveX = Interpolation.linear.apply(ball.getXPosition(), ball.getXPosition() + ball.getXSpeed(), 1);
		float moveY = Interpolation.linear.apply(ball.getYPosition(), ball.getYPosition() + ball.getYSpeed(), 1);
		ballSprite.setX(moveX);
		ballSprite.setY(moveY);
		ballSprite.setRotation(bRotate);

		boolean kidVSBallOverlap = ballSprite.getBoundingRectangle().overlaps(MainKeepUp.kid.getBoundingRectangle());
		if(kidVSBallOverlap == true){
			if(ball.collision == false){  //  check for current collision
				addLifeMark();
				buzzer.play(.05f);
				ball.collision = true;  // sets current collision
			}
		}else{
			ball.collision = false;  // resets to false after overlap ends
		}

	}

	public void ballLoopCheckOnPause(Ball ball){
		Sprite ballSprite = ball.getBallSprite();
		ballSprite.setX(ball.getXPosition());
		ballSprite.setY(ball.getYPosition());
	}

	public void checkUnPause(){
		if(pauseGame == true){
			pauseGame = false;
			gameState = GAME_RUNNING;
			kidMovable = true;
			Gdx.app.log(TAG, "RESUME GAME" );
			resume();
		}else{
			gameState = GAME_PAUSED;
			pauseGame = true;
			kidMovable = false;
			Gdx.app.log(TAG, "GAME PAUSED" );
			pause();
		}
	}


////////  APP FUNCTIONS  /////////	

	@Override
	public void resize(int width, int height) {
	}
	
	@Override
	public void pause() {
	}
	
	@Override
	public void resume() {
	}
	

////////  APP INTERACTIONS  ///////////
	
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		Vector2 touchPos = new Vector2();
		touchPos.set(Gdx.input.getX(), Gdx.input.getY());
		Ray cameraRay = camera.getPickRay(touchPos.x, touchPos.y);

		boolean kidDown = kid.getBoundingRectangle().contains(cameraRay.origin.x, cameraRay.origin.y);
		if(kidMovable == true){
			if(kidDown == true){
				kidMove = true;
			}
		}else{
			kidMove = false;
		}

		return true;

	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {

		Vector2 touchPos = new Vector2();
		touchPos.set(Gdx.input.getX(), Gdx.input.getY());
		Ray cameraRay = camera.getPickRay(touchPos.x, touchPos.y);
		Gdx.app.log(TAG, "Touch Ray Coords: X:" + cameraRay.origin.x + " Y:" + cameraRay.origin.y);

		boolean touchPause = pause.getBoundingRectangle().contains(cameraRay.origin.x, cameraRay.origin.y);

		if(touchPause == true){
			if(pauseGame == true){
				pauseRegion = new TextureRegion(pauseTx, 0, 0, pauseTx.getWidth(), 64);
				pause.setRegion(pauseRegion);
			}else{
				pauseRegion = new TextureRegion(pauseTx, 0,64, pauseTx.getWidth(), 64);
				pause.setRegion(pauseRegion);
			}
			checkUnPause();
		}
		if(gameOver == true){
			boolean touchRestart = restartBtn.getBoundingRectangle().contains(cameraRay.origin.x, cameraRay.origin.y);
			
			if(touchRestart == true){
				gameRestart();
			}
		}
		kidMove = false;
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		if(kidMove == true){
			Vector2 touchPos = new Vector2();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY());
			Ray cameraRay = camera.getPickRay(touchPos.x, touchPos.y);
			kid.setX(cameraRay.origin.x - kid.getHeight()/2);
			kid.setY(cameraRay.origin.y - kid.getWidth()/2);
			return true;
		}
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

//////////  DESKTOP ONLY FUNCTIONS  //////////////

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
	
}  // END MAIN




