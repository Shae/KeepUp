package come.klusman.keepup.screens;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquation;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.klusman.keepup.MainKeepUp;
import com.klusman.keepup.tweens.SpriteTween;

public class SplashScreen implements Screen{

	MainKeepUp game;
	Texture titleTx;
	Sprite titleSprite;
	
	private OrthographicCamera camera;
	public static int screenXRefactor;
	public static int screenYRefactor;
	float x;
	float y;
	float screenRatio;
	TweenManager manager;
	private SpriteBatch batch;
	
	public SplashScreen( MainKeepUp game){
		this.game = game;
		x = Gdx.graphics.getWidth();
		y = Gdx.graphics.getHeight();
		screenXRefactor = 1000;
		screenRatio = y/x;
		screenYRefactor = (int) (screenRatio * screenXRefactor);
		camera = new OrthographicCamera(screenXRefactor, screenYRefactor);
		
	}
	
	



	@Override
	public void show() {
	
		titleTx = new Texture(Gdx.files.internal("data/splashTitle.png"));
		titleTx.setFilter(TextureFilter.Linear, TextureFilter.Linear);	
		TextureRegion titleRegion = new TextureRegion(titleTx, 0, 0, titleTx.getWidth(), titleTx.getHeight());
		titleSprite = new Sprite(titleRegion);
		titleSprite.setSize(titleTx.getWidth() * 2f,  titleTx.getHeight() * 2f);  
		titleSprite.setOrigin(titleSprite.getWidth()/2, titleSprite.getHeight()/2);
		titleSprite.setPosition(0 - titleSprite.getWidth()/2, 0 - titleSprite.getHeight()/2);
		titleSprite.setRotation(18);
		titleSprite.setColor(1, 1, 1, 0);
		batch = new SpriteBatch();
		
		Tween.registerAccessor(Sprite.class, new SpriteTween());
		manager = new TweenManager();
		
		TweenCallback cb = new TweenCallback() {
			@Override
			public void onEvent(int type, BaseTween<?> source) {
				tweenCompleted();  // what method to call when Event is triggered
			}
		};
		
		Tween.to(titleSprite, SpriteTween.ALPHA, 2f)
			.target(1)
			.ease(TweenEquations.easeInQuad)
			.repeatYoyo(1, 2f)  // repeat once after X 
			.setCallback(cb)  // set a callback listener
			.setCallbackTriggers(TweenCallback.COMPLETE)  // set the trigger for the listener
			.start(manager);  // start the tween using the passed in manager
		
	}  // END SHOW

	protected void tweenCompleted() {
		
		Gdx.app.log(MainKeepUp.TAG, "Splash Tween COMPLETE");
		game.setScreen(new MainMenu(game));  // Send to MainMenu after tween complete
		
	}





	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);  // set the clear color
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);  // clear the screen before the rebuild using the clear color
		
		manager.update(delta);  // update manager using the delta time
		camera.update();  // update the camera
		batch.setProjectionMatrix(camera.combined);
		
		batch.begin();
			titleSprite.draw(batch);
		batch.end();
		
		
	}
	
	@Override
	public void resize(int width, int height) {
		
		
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
		
	}
	
}