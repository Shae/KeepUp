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
import com.klusman.keepup.MainKeepUp;

public class CreditsScreen implements Screen, InputProcessor{

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
	
	Sprite backBtn;
	Texture backTx;
	
	public CreditsScreen(MainKeepUp game){

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
		
		backTx = new Texture(Gdx.files.internal("data/backBtn.png"));
		backTx.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		TextureRegion backReg = new TextureRegion(backTx, 0, 0, backTx.getWidth(), backTx.getHeight());
		float stretchRatioBack = (float) backTx.getHeight() / backTx.getWidth();
		backBtn = new Sprite(backReg);
		backBtn.setSize(500,  500 * stretchRatioBack);  
		backBtn.setOrigin(backBtn.getWidth()/2, backBtn.getHeight()/2);
		backBtn.setPosition(0 - backBtn.getWidth()/2, ((screenYRefactor / 2) * -1) + 100);
		
		creditsTx = new Texture(Gdx.files.internal("data/creditScreen.png"));
		creditsTx.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		TextureRegion creditReg = new TextureRegion(creditsTx, 0, 0, creditsTx.getWidth(), creditsTx.getHeight() - 200);
		float stretchRatioCredits = (float) (creditsTx.getHeight() - 200) / creditsTx.getWidth();
		creditsSprite = new Sprite(creditReg);
		creditsSprite.setSize(screenXRefactor,  screenXRefactor * stretchRatioCredits);  
		creditsSprite.setOrigin(creditsSprite.getWidth()/2, creditsSprite.getHeight()/2);
		creditsSprite.setPosition(0 - creditsSprite.getWidth()/2, 0 - creditsSprite.getHeight()/2);
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
			creditsSprite.draw(batch);
			backBtn.draw(batch);
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		backTx.dispose();
		creditsTx.dispose();
		
		
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
		//Gdx.app.log(MainKeepUp.TAG, "Touch Ray Coords: X:" + cameraRay.origin.x + " Y:" + cameraRay.origin.y);

		boolean backBool = backBtn.getBoundingRectangle().contains(cameraRay.origin.x, cameraRay.origin.y);
		
		if(backBool == true){			
			Gdx.app.log(MainKeepUp.TAG, "Back Button Clicked!");
			runGame(game);
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

}  // END GAME SCREEN
