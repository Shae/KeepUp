package com.klusman.keepup;


import java.awt.Point;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenAccessor;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.collision.Ray;



public class MainKeepUp implements ApplicationListener, InputProcessor {
	private static String TAG = "KeepUp";
	private OrthographicCamera camera;
	private SpriteBatch batch;


	private Texture bgTx;
	private Sprite bg;
	
	private Texture grnBallTx;
	private Sprite ball;
	
	private Texture grnBallTx2;
	private Sprite ball2;

	private Texture kidTx;
	private Sprite kid;
	
	float w;
	float h;
	float time;
	
	int rotateSpeed;
	float moveSpeed;
	float YmoveSpeed;
	Boolean SpinPos;
	
	int rotateSpeed2;
	float moveSpeed2;
	float YmoveSpeed2;
	Boolean SpinPos2;
	
	public static Sound metalDing;
	public static Sound buzzer;
	public static Sound bounce;

	private BitmapFont font; 
	
	boolean kidMove;


	
	@Override
	public void create() {
		// TWEEEN CRAP
		//Tween.registerAccessor(man.class, new manAccessor());
		//TweenManager manager = new TweenManager();
		
		w = Gdx.graphics.getWidth();
		h = Gdx.graphics.getHeight();
		SpinPos = true;
		SpinPos2 = true;
		kidMove = false;
		
		font = new BitmapFont(); 
		font.setColor(0.0f, 0.0f, 1.0f, 1.0f); // tint font blue

		time = Gdx.graphics.getDeltaTime();
		
		///  Ball Speeds  ////
		rotateSpeed = 5;
		moveSpeed = .005f;
		YmoveSpeed = .005f;
		
		rotateSpeed2 = 8;
		moveSpeed2 = .008f;
		YmoveSpeed2 = .005f;
		
		////  Camera, touch and batch  //////
		Gdx.input.setInputProcessor(this);
		camera = new OrthographicCamera(1, h/w);
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
		bg.setSize(h/w, 1);
		bg.rotate(90);
		bg.setOrigin(bg.getWidth()/2, bg.getHeight()/2);
		bg.setPosition(0 - bg.getWidth()/2, 0f - bg.getHeight()/2);
		
		//// BALL
		grnBallTx = new Texture(Gdx.files.internal("data/grnBall.png"));
		grnBallTx.setFilter(TextureFilter.Linear, TextureFilter.Linear);	
		TextureRegion ballRegion = new TextureRegion(grnBallTx, 0, 0, grnBallTx.getWidth(), grnBallTx.getHeight());
		ball = new Sprite(ballRegion);
		ball.setSize(.09f, .09f);
		ball.setOrigin(ball.getWidth()/2, ball.getHeight()/2);
		ball.setPosition(-.49f, -0.22f - ball.getHeight()/2);

		//// BALL2
		grnBallTx2 = new Texture(Gdx.files.internal("data/grnBall.png"));
		grnBallTx2.setFilter(TextureFilter.Linear, TextureFilter.Linear);	
		TextureRegion ballRegion2 = new TextureRegion(grnBallTx2, 0, 0, grnBallTx2.getWidth(), grnBallTx2.getHeight());
		ball2 = new Sprite(ballRegion2);
		ball2.setSize(.06f, .06f);
		ball2.setOrigin(ball2.getWidth()/2, ball2.getHeight()/2);
		ball2.setPosition(-.49f, -0.1f - ball2.getHeight()/2);
		
		//// KID
		
		kidTx = new Texture(Gdx.files.internal("data/kid.png"));
		kidTx.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		TextureRegion kidRegion = new TextureRegion(kidTx, 0, 0, kidTx.getWidth(), kidTx.getHeight());
		kid = new Sprite(kidRegion);
		kid.setSize(.07f, .09f);
		kid.rotate(90);
		kid.setOrigin(kid.getWidth()/2, kid.getHeight()/2);
		kid.setPosition(0 , 0);	
		

	} // END CREATE


	@Override
	public void dispose() {
		batch.dispose();
		bgTx.dispose();
		grnBallTx.dispose();
		grnBallTx2.dispose();
		kidTx.dispose();
	}

	@Override
	public void render() {	
		time += Gdx.graphics.getDeltaTime();   
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		camera.update();
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
			bg.draw(batch);
			kid.draw(batch);
			ball.draw(batch);
			ball2.draw(batch);
			//font.draw(batch, "HELLO", 0, 0);
		batch.end();
			
		ball1CheckAndSet();
		ball2CheckAndSet();
		Gdx.app.log(TAG, "TIME:" + time);
		//float moveX = Interpolation.linear.apply(percent, game.manager.getProgress(), 0.1f);
		
		
	}// END RENDER
	
	public void ball1CheckAndSet(){
		float by = ball.getY() + YmoveSpeed;
		float bx = ball.getX() + moveSpeed;
		float bRotate = ball.getRotation() + rotateSpeed;
		
		if(bx + ball.getHeight() >= 0.5f){
			moveSpeed = moveSpeed* -1;
			if(SpinPos != false){
				rotateSpeed = rotateSpeed * -1;
			}
			SpinPos = true;
			bx = ball.getX() + moveSpeed;
			bounce.play();
		}
		
		if(by + ball.getWidth() >= .3f){
			YmoveSpeed = YmoveSpeed* -1;
			if(SpinPos != true){
				rotateSpeed = rotateSpeed * -1;
			}
			SpinPos = false;
			by = ball.getY() + YmoveSpeed;
			bounce.play();	
		}
		
		if(bx <= -0.5f){
			moveSpeed = moveSpeed* -1;
			if(SpinPos != false){
				rotateSpeed = rotateSpeed * -1;
			}
			SpinPos = true;
			bx = ball.getX() + moveSpeed;
			bounce.play();
		}
		
		if(by <= -.3f){
			if(SpinPos != true){
				rotateSpeed = rotateSpeed * -1;
			}
			SpinPos = false;
			YmoveSpeed = YmoveSpeed* -1;
			by = ball.getY() + YmoveSpeed;
			bounce.play();	
		}
		
		float moveX = Interpolation.linear.apply(ball.getX(), ball.getX() + moveSpeed, 1);
		float moveY = Interpolation.linear.apply(ball.getY(), ball.getY() + YmoveSpeed, 1);
		ball.setY(moveY);
		ball.setX(moveX);
		ball.setRotation(bRotate);
		
		boolean kidVSBallOverlap = ball.getBoundingRectangle().overlaps(kid.getBoundingRectangle());
		if(kidVSBallOverlap == true){
			buzzer.play(.05f);
		}
			
	}
	
	public void ball2CheckAndSet(){
		float by = ball2.getY() + YmoveSpeed2;
		float bx = ball2.getX() + moveSpeed2;
		float bRotate = ball2.getRotation() + rotateSpeed2;
		
		if(bx + ball2.getHeight() >= 0.5f){
			moveSpeed2 = moveSpeed2* -1;
			if(SpinPos2 != false){
				rotateSpeed2 = rotateSpeed2 * -1;
			}
			SpinPos2 = true;
			bx = ball2.getX() + moveSpeed2;
			bounce.play();
			
		}
		
		if(by + ball2.getWidth() >= .3f){
			YmoveSpeed2 = YmoveSpeed2* -1;
			if(SpinPos2 != true){
				rotateSpeed2 = rotateSpeed2 * -1;
			}
			SpinPos2 = false;
			by = ball2.getY() + YmoveSpeed2;
			bounce.play();
			
		}
		
		if(bx <= -0.5f){
			moveSpeed2 = moveSpeed2* -1;
			if(SpinPos2 != false){
				rotateSpeed2 = rotateSpeed2 * -1;
			}
			SpinPos2 = true;
			bx = ball2.getX() + moveSpeed2;
			bounce.play();
		}
		
		if(by <= -.3f){
			if(SpinPos2 != true){
				rotateSpeed2 = rotateSpeed2 * -1;
			}
			SpinPos2 = false;
			
			YmoveSpeed2 = YmoveSpeed2* -1;
			by = ball2.getY() + YmoveSpeed2;
			bounce.play();
			
		}
		
		float moveX = Interpolation.linear.apply(ball2.getX(), ball2.getX() + moveSpeed2, 1);
		float moveY = Interpolation.linear.apply(ball2.getY(), ball2.getY() + YmoveSpeed2, 1);
		ball2.setY(moveY);
		ball2.setX(moveX);
		ball2.setRotation(bRotate);
		
		boolean kidVSBall2Overlap = ball2.getBoundingRectangle().overlaps(kid.getBoundingRectangle());
		if(kidVSBall2Overlap == true){
			buzzer.play(.05f);
			//kid.getScaleX();
			//kid.getScaleY();
			//Gdx.app.log(TAG, "SCALE X:" + kid.getHeight() + " Y:" + kid.getScaleY());
			
			//kid.setSize(kid.getHeight() + 0.01f, kid.getWidth() + 0.01f);  to much to long
			//kid.rotate(90);

			
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
		
		//qo46789y1v5pb98yt34oy8ces ;imcx aliuyw oefjyghbls.djvnaD
		//DFLQIUGHPQWE;KFJH;QASLIDHJFKQ'AD';C"
		//Tween.to(man, tweenType, duration)
		
	};

}

class man {
    private float x, y;
    
    public float getX() {
        return x;
    }
    
    public float getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }
}

class manAccessor implements TweenAccessor<man> {

    // The following lines define the different possible tween types.
    // It's up to you to define what you need :-)

    public static final int POSITION_X = 1;
    public static final int POSITION_Y = 2;
    public static final int POSITION_XY = 3;


	@Override
	public int getValues(man target, int tweenType, float[] returnValues) {
		 switch (tweenType) {
         case POSITION_X: returnValues[0] = target.getX(); return 1;
         case POSITION_Y: returnValues[0] = target.getY(); return 1;
         case POSITION_XY:
             returnValues[0] = target.getX();
             returnValues[1] = target.getY();
             return 2;
         default: assert false; return -1;
     }
	}

	@Override
	public void setValues(man target, int tweenType, float[] newValues) {
		 switch (tweenType) {
         case POSITION_X: target.setX(newValues[0]); break;
         case POSITION_Y: target.setY(newValues[0]); break;
         case POSITION_XY:
             target.setX(newValues[0]);
             target.setY(newValues[1]);
             break;
         default: assert false; break;
     }
	}
}

