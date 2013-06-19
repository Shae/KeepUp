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
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.utils.Array;



public class MainKeepUp implements ApplicationListener, InputProcessor {
	private static String TAG = "KeepUp";
	private OrthographicCamera camera;
	private SpriteBatch batch;
	int Level;
	double randNumYLoc;
	double randNumSize;
	double randNumSpeed;

	private Texture bgTx;
	private Sprite bg;
	
	private Texture kidTx;
	public static Sprite kid;
	
	private Texture pauseTx;
	public static Sprite pause;
	
	float x;
	float y;
	float screenRatio;
	float time;
	
	public static Sound metalDing;
	public static Sound buzzer;
	public static Sound bounce;

	//private BitmapFont font; 
	
	boolean kidMove;
	public static Music bgMusic;
	boolean ballCollision;
	public Array<Ball> Balls;

	int screenXRefactor;
	int screenYRefactor;
	
	@Override
	public void create() {
		x = Gdx.graphics.getWidth();
		y = Gdx.graphics.getHeight();
		screenRatio = x/y;
		screenXRefactor = 1000;
		screenYRefactor = (int) (screenRatio * screenXRefactor);
		Gdx.input.setInputProcessor(this);
		//camera = new OrthographicCamera(1, screenRatio);
		camera = new OrthographicCamera(screenXRefactor, screenYRefactor);
		Gdx.app.log(TAG, "SCREEN RATIO Y = " + screenYRefactor);
		bgMusic = Gdx.audio.newMusic(Gdx.files.internal("audio/Ttimes.mp3"));	
		bgMusic.setLooping(false);  // Didn't want to kill u with this bad loop
		bgMusic.setVolume(0.03f);
		bgMusic.play();
		
		Balls = new Array<Ball>();
		Level = 4;  // CURRENT GAME LEVEL
		levelBallSet(Level);

		kidMove = false;
		
		
		//font = new BitmapFont(); 
		//font.setColor(0.0f, 0.0f, 1.0f, 1.0f); // tint font blue

		time = Gdx.graphics.getDeltaTime();
		

		////  Camera, touch and batch  //////
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
		//bg.setSize(screenXRefactor, screenYRefactor);
		bg.setSize(screenXRefactor, screenXRefactor);
		bg.rotate(90);
		bg.setOrigin(bg.getWidth()/2, bg.getHeight()/2);
		bg.setPosition(0 - bg.getWidth()/2, 0f - bg.getHeight()/2);
		
		
		//// KID
		kidTx = new Texture(Gdx.files.internal("data/kid.png"));
		kidTx.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		TextureRegion kidRegion = new TextureRegion(kidTx, 0, 0, kidTx.getWidth(), kidTx.getHeight());
		kid = new Sprite(kidRegion);
		kid.setSize(.12f * 1000, .12f * 1000);
		kid.rotate(90);
		kid.setOrigin(kid.getWidth()/2, kid.getHeight()/2);
		kid.setPosition(0.3f  * 1000, 0 - kid.getWidth()/2 );	
		
		
	//// Pause
			pauseTx = new Texture(Gdx.files.internal("data/redBar.png"));
			pauseTx.setFilter(TextureFilter.Linear, TextureFilter.Linear);
			TextureRegion pauseRegion = new TextureRegion(pauseTx, 0, 0, pauseTx.getWidth(), pauseTx.getHeight());
			pause = new Sprite(pauseRegion);
			pause.setSize(.09f * 1000, .05f * 1000);
			//pause.rotate(90);
			pause.setOrigin(pause.getWidth()/2, pause.getHeight()/2);
			
			//pause.setOrigin(pause.getHeight()/2, pause.getWidth()/2);
			pause.setPosition(-0.5f * 1000, 0);	


	} // END CREATE


	public void makeNewBall(){
		Ball ball = new Ball();
		float yFloat = 0;
		
		//// SIZE
		randNumSize = Math.random();
		double rand = ((randNumSize * 100) + 1);
		double rand2 = rand;
		if (rand <= 40){
			rand2 = rand + 41; // Makes a number greater than 41
		} if (rand >= 90){
			rand2 = rand - ((90 - rand ) * 2);  // makes a number less than 90
		}
		float r = (float) (rand2 / 1000);
		Gdx.app.log(TAG, "Random Size: " + r);
		
		ball.setSizeXY(r, r);
		Gdx.app.log(TAG, "ball X:" + ball.getSizeX());  
		Gdx.app.log(TAG, "ball Y:" + ball.getSizeY()); 
		
		
		//// SPEED
		randNumSpeed = Math.random();
		double sp = ((randNumSize * 5) + 5);
		int spInt = (int)sp;
		Gdx.app.log(TAG, "Speed random " + spInt);
		
		ball.setXSpeed((float) spInt / 1000);
		ball.setYSpeed((float) spInt / 1000);
		
		
		//// STARTING LOCATION
		randNumYLoc = Math.random();
		double y = (randNumYLoc * 60) + 1;
		Gdx.app.log(TAG, "Location random " + y);
		int yInt = (int)y;
		if(yInt <=30){
			yFloat = (float) (y * -1) / 100;
			Gdx.app.log(TAG, "Location random y * -1 = " + yFloat );
			ball.setYPosition(yFloat);
			ball.setXSpeed(ball.getXSpeed() * -1);
			ball.setYSpeed(ball.getYSpeed() * -1);
		}else{
			yFloat = (float) (y - 30) / 100;
			Gdx.app.log(TAG, "Location random y - 30 / 100 = " + yFloat );
			ball.setYPosition((yFloat - ball.getBallSprite().getWidth()));
		}
		
		
		Gdx.app.log(TAG, "yFloat " + yFloat );
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
		if(Balls.size > 0){
			for(Ball ball: Balls) {
				ball.ballTx.dispose();
			};
		}
	}

	@Override
	public void render() {	
			//Gdx.app.log(TAG, "#of balls" + Balls.size);
		time += Gdx.graphics.getDeltaTime();   
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
			pause.draw(batch);
		      
			//font.draw(batch, "HELLO", 0, 0);  /// GRRRR  DOES NOT WORK
		batch.end();
		
		
		if(Balls.size > 0){
			for(Ball ball: Balls) {
				ballLoopCheckAndSet(ball);
			};
		}
		
		//Gdx.app.log(TAG, "TIME:" + time);   //LOG LOG LOG LOG
	}// END RENDER
	
	public void ballLoopCheckAndSet(Ball ball){
		Sprite ballSprite = ball.getBallSprite();

		float xPosition = ball.getXPosition();
		float yPosition = ball.getYPosition();
		int rotationSpeed = ball.getRotationSpeed();
		boolean rotationDirection = ball.getRotation();
		boolean collision = ball.getCollision();
		float yPosSpriteHeight = yPosition + ballSprite.getWidth();
		
		
		if(xPosition + ballSprite.getHeight() >= 0.5f){
			//Gdx.app.log(TAG, "Out of bounds Down");
			ball.setXSpeed(ball.getXSpeed() * -1);
			ball.setXPosition(ball.getXPosition() + ball.getXSpeed());
			if(rotationDirection != false){
				ball.setRotationSpeed(rotationSpeed * -1);
			}
			ball.setRotation(true);
			bounce.play();
		}
		
		if(yPosSpriteHeight >= screenRatio / 2){
			//Gdx.app.log(TAG, "Out of bounds Right");
			ball.setYSpeed(ball.getYSpeed() * -1);
			ball.setYPosition(ball.getYPosition() + ball.getYSpeed());
			if(rotationDirection != true){
				ball.setRotationSpeed(rotationSpeed * -1);
			}
			ball.setRotation(false);
			bounce.play();	
		}
		if(xPosition <= -0.5f){
			//Gdx.app.log(TAG, "Out of bounds Up");
			ball.setXSpeed(ball.getXSpeed() * -1);
			ball.setXPosition(ball.getXPosition() + ball.getXSpeed());
			if(rotationDirection != false){
				ball.setRotationSpeed(rotationSpeed * -1);
			}
			ball.setRotation(true);
			bounce.play();
		} 
		if(yPosition <= (screenRatio / 2) * -1){
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
			
			//float moveX = Interpolation.linear.apply(xPosition, xPosition + xSpeed, 1);
			//float moveY = Interpolation.linear.apply(yPosition, yPosition + ySpeed, 1);
		ballSprite.setX(ball.getXPosition() );
		ballSprite.setY(ball.getYPosition());
		ballSprite.setRotation(bRotate);
		
		boolean kidVSBallOverlap = ballSprite.getBoundingRectangle().overlaps(MainKeepUp.kid.getBoundingRectangle());
		if(kidVSBallOverlap == true){
			if(collision == false){
				buzzer.play(.05f);
				collision = true;
			}
		}else{
			collision = false;
		}
			
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
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
		
		boolean kidDown = kid.getBoundingRectangle().contains(cameraRay.origin.x, cameraRay.origin.y);
		
		if(kidDown == true){
			kidMove = true;
		}
		
		return true;
		
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {

		Vector2 touchPos = new Vector2();
		touchPos.set(Gdx.input.getX(), Gdx.input.getY());
		Ray cameraRay = camera.getPickRay(touchPos.x, touchPos.y);
		Gdx.app.log(TAG, "Touch Ray Coords: X:" + cameraRay.origin.x + " Y:" + cameraRay.origin.y);
		
//		Math.linearTween = function (t, b, c, d) {
//			return c*t/d + b;
//		};
	
		//float currentKidX = kid.getX();
		//float currentKidY = kid.getY();
		//float moveToX = cameraRay.origin.x;
		//float moveToY = cameraRay.origin.y;
		//Vector2 kidAt = new Vector2(currentKidX, currentKidY);
		//Vector2 goingTo = new Vector2(moveToX, moveToY);
		//float clickedTime = time;
		//moveInterp(currentKidX, currentKidY, moveToX, moveToY, clickedTime);
		
		//kid.setX(Interpolation.);
		//kid.setY(Interpolation.bounce.apply(currentKidY, moveToY, 1f));
		
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
	
	public void moveInterp(float startX, float startY, float endX, float endY, float startTime ){
		// set how long U want it to take to get there
		//float moveSpeedTime = 1f;
		// get time of request
		//float requestedAtTime = startTime;
	
		//Tween.to(man, tweenType, duration)
		
	};

}



