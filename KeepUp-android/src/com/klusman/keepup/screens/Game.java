package com.klusman.keepup.screens;

import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeBitmapFontData;
import com.badlogic.gdx.graphics.g3d.model.Animation;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.utils.Array;
import com.klusman.keepup.Ball;
import com.klusman.keepup.Bomb;
import com.klusman.keepup.FreezeMotionTimer;
import com.klusman.keepup.LifeMarks;
import com.klusman.keepup.MainActivity;
import com.klusman.keepup.MainKeepUp;
import com.klusman.keepup.ShieldBoost;
import com.klusman.keepup.healthKit;
import com.klusman.keepup.database.ScoreSource;



public class Game implements Screen, InputProcessor {
	MainActivity _mainActivity;
	MainKeepUp game;
	ScoreSource _scoreSource;

	public static final int GAME_READY = 0; 
	public static final int GAME_RUNNING = 1; 
	public static final int GAME_PAUSED = 2; 
	public static final int GAME_OVER = 4;

	BitmapFont gameText;

	public static int gameState;

	private OrthographicCamera camera;
	static float x = Gdx.graphics.getWidth();
	static float y = Gdx.graphics.getHeight();
	static float screenRatio = y/x;
	public static final int screenXRefactor = 1000;
	public static final int screenYRefactor = (int) (screenRatio * screenXRefactor);
	TweenManager manager;

	private SpriteBatch batch;

	float deltaTime;
	float elapsedTime;
	float ballSpawnTimer;
	float ballDifSpawnTime;
	float FreezeTimer = 4f;
	float resourceTimer = 7f;
	float scoreTime = 2f;
	float scoreTimeInterval = 1f;
	float deltaShieldTime;
	float deltaFreezeTime;
	float shieldedTime;
	float bombTime = 2f;
	float deltaBombTime;


	float ticker;
	float timeLimit = 20.0f;
	float invincibilityTime;
	float spawnRate = 7;

	int starLoop;
	int countDownFrame = 0;
	/// Achievement / Leaderboard stuff ///
	int SCORE = 0;
	int kitsUsed = 0;
	int pointsReceivedBeforeFirstResourceUsed = 0;
	int avChoice = 1;
	///////////////////////////////////////


	double randNumXLoc;
	double randNumSize;
	double randNumSpeed;


	boolean isSignedIn = false;
	boolean pauseGame;
	boolean kidMovable;
	boolean kidMove;
	boolean ballCollision;
	boolean gameOver;
	boolean kidHit;
	boolean invincibility;
	boolean shielded;
	boolean freeze;
	boolean firstResourceUsed = true;
	boolean ach50 = false;
	boolean ach500 = false;
	boolean ach750 = false;
	boolean ach1000 = false;
	boolean gameCheck = false;
	boolean firstResource = false;

	public Texture bombTx;
	TextureRegion bombRegion;

	private Texture bgTx;
	private Sprite bg;

	private Texture star2Tx;
	TextureRegion starRegion1;
	TextureRegion starRegion2;
	TextureRegion starRegion3;
	TextureRegion starRegion4;
	TextureRegion starRegion5;
	TextureRegion starRegion6;
	TextureRegion starRegion7;
	TextureRegion starRegion8;
	TextureRegion starRegion0;
	Array<TextureRegion> starHolder;

	private Texture countDownNumTx;
	TextureRegion count3;
	TextureRegion count2;
	TextureRegion count1;
	TextureRegion countGo;
	private Sprite CountSprite;
	Array<TextureRegion> countHolder;

	private Sprite starSprite;

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

	public static Sound buzzer;
	public static Sound bounce1;
	public static Sound powerUp;
	public static Sound hardBounce;
	public static Sound timeBomb;
	public static Sound bounce2;
	public static Sound bounce3;


	int frameLength;
	int currentFrame;
	int gameDifficulty;

	FreeTypeFontGenerator generator;
	FreeTypeBitmapFontData font;

	public Array<Ball> Balls;
	public Array<healthKit> MedKits;
	public Array<ShieldBoost> Shields;
	public Array<Bomb> Bombs;
	public Array<FreezeMotionTimer> Timers;
	public static Array<LifeMarks> Marks;
	public Array<Sprite> starArray;
	Animation starAnimation;
	//AchievementsHandler achHandler;


	public Game( MainKeepUp game){

		_mainActivity = MainActivity.Instance;
		_scoreSource = new ScoreSource(_mainActivity);
		this.game = game;
		camera = new OrthographicCamera(screenXRefactor, screenYRefactor);

		deltaTime = Gdx.graphics.getDeltaTime();
		Gdx.input.setInputProcessor(this);
		Gdx.input.setCatchBackKey(true);

	}

	@Override
	public void show() {
		gameDifficulty = _mainActivity.getGameDifficulty();
		if(gameDifficulty == 1){
			scoreTimeInterval = 2f;
		}else if(gameDifficulty == 2){
			scoreTimeInterval = 1.5f;
		}else{
			scoreTimeInterval = 1f;
		}

		if(gameDifficulty == 1){
			ballSpawnTimer = 9;
			ballDifSpawnTime = 9;
		}if(gameDifficulty == 2){
			ballSpawnTimer = 7;
			ballDifSpawnTime = 7;
		}else{
			ballSpawnTimer = 5;
			ballDifSpawnTime = 5;
		}
		isSignedIn = _mainActivity.isPlayerSignedIn();
		gameState = GAME_READY;
		invincibility = false;
		shielded = false;
		pauseGame = false;
		gameOver = false;
		invincibilityTime = 0;
		shieldedTime = 0;
		freeze = false;
		SCORE = 0;

		Balls = new Array<Ball>();
		MedKits = new Array<healthKit>();
		Shields = new Array<ShieldBoost>();
		Timers = new Array<FreezeMotionTimer>();
		Bombs = new Array<Bomb>();
		Marks = new Array<LifeMarks>();

		kidMovable = true;
		kidMove = false;
		kidHit = false;

		batch = new SpriteBatch();

		//// FONT SPECIFIC
		generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/SourceFont.otf"));
		font = generator.generateData(65);  // size based on the 1000px camera ratio
		gameText = new BitmapFont(font, font.getTextureRegion(), false);
		gameText.setColor(00, 200, 00, 1);  

		/// AUDIO  ///
		powerUp = Gdx.audio.newSound(Gdx.files.internal("audio/PowerUp.wav"));
		buzzer = Gdx.audio.newSound(Gdx.files.internal("audio/Buzzer.wav"));
		bounce1 = Gdx.audio.newSound(Gdx.files.internal("audio/ballbounce04.wav"));
		bounce2 = Gdx.audio.newSound(Gdx.files.internal("audio/ballBounce03.wav"));
		bounce3 = Gdx.audio.newSound(Gdx.files.internal("audio/ballBounce02.wav"));

		hardBounce = Gdx.audio.newSound(Gdx.files.internal("audio/HardBounce.wav"));
		timeBomb = Gdx.audio.newSound(Gdx.files.internal("audio/timeBombSound.wav"));


		//// BACKGROUND
		if(_mainActivity.getCourtBool() == true){
			//Log.i(MainKeepUp.TAG, "DARK COURT YARD");
			bgTx = new Texture(Gdx.files.internal("data/darkCOURT_lrg.png"));
		}else{
			//Log.i(MainKeepUp.TAG, "LIGHT COURT YARD");
			bgTx = new Texture(Gdx.files.internal("data/lightCOURT_lrg.png"));
		}
		bgTx.setFilter(TextureFilter.Linear, TextureFilter.Linear);	
		TextureRegion bgRegion = new TextureRegion(bgTx, 0, 40, bgTx.getWidth() , 1620 );
		bg = new Sprite(bgRegion);
		bg.setSize(screenXRefactor,  screenYRefactor);  
		bg.setOrigin(bg.getWidth()/2, bg.getHeight()/2);
		bg.setPosition(0 - bg.getWidth()/2, 0f - bg.getHeight()/2);

		//// PAUSE SCREEN
		psTx = new Texture(Gdx.files.internal("data/pausedScreen.png"));
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
		avChoice = _mainActivity.getAvatar();
		if(avChoice == 1){
			kidTx = new Texture(Gdx.files.internal("data/girlInRed.png"));
		}else if(avChoice == 2){
			kidTx = new Texture(Gdx.files.internal("data/girlInWhite.png"));
		}else if(avChoice == 3){
			kidTx = new Texture(Gdx.files.internal("data/girlInBrown.png"));
		}else if(avChoice == 4){
			kidTx = new Texture(Gdx.files.internal("data/boyInRed.png"));
		}else if(avChoice == 5){
			kidTx = new Texture(Gdx.files.internal("data/boyInWhite.png"));
		}else if(avChoice == 6){
			kidTx = new Texture(Gdx.files.internal("data/boyInTan.png"));
		}else{
			kidTx = new Texture(Gdx.files.internal("data/boyInBlack.png"));
		}
		kidTx.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		TextureRegion kidRegion = new TextureRegion(kidTx, 0, 0, kidTx.getWidth(), kidTx.getHeight());
		float stretchRatioKid = (float) kidTx.getHeight() / kidTx.getWidth();
		kid = new Sprite(kidRegion);
		kid.setSize(110f , 110 * stretchRatioKid );
		kid.setOrigin(kid.getWidth()/2, kid.getHeight()/2);
		kid.setPosition(0 - (kid.getWidth()/2), -screenYRefactor/2 );	

		//// ExplosionTexture
		bombTx = new Texture(Gdx.files.internal("data/bomb.png"));
		bombTx.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		bombRegion = new TextureRegion(bombTx, 0, 64, bombTx.getWidth(), 64);

		//// PAUSE
		pauseTx = new Texture(Gdx.files.internal("data/PauseTabs.png"));
		pauseTx.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		pauseRegion = new TextureRegion(pauseTx, 0, 0, pauseTx.getWidth(), 64);
		pause = new Sprite(pauseRegion);
		pause.setSize(150f , 100f );
		pause.setOrigin(pause.getWidth()/2, pause.getHeight()/2);
		pause.setPosition(0 - pause.getWidth()/2, screenYRefactor/2 - pause.getHeight());	

		//// STAR SPRITE
		starHolder = new Array<TextureRegion>();
		star2Tx = new Texture(Gdx.files.internal("data/stars2.png"));
		star2Tx.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		// Hard coded regions (I was having trouble understanding so I did it the long route) /////
		starRegion1 = new TextureRegion(star2Tx, 0, 0, star2Tx.getWidth(), 64);
		starHolder.add(starRegion1);
		starRegion2 = new TextureRegion(star2Tx, 0, 64, star2Tx.getWidth(), 64);
		starHolder.add(starRegion2);
		starRegion3 = new TextureRegion(star2Tx, 0, 128, star2Tx.getWidth(), 64);
		starHolder.add(starRegion3);
		starRegion4 = new TextureRegion(star2Tx, 0, 192, star2Tx.getWidth(), 64);
		starHolder.add(starRegion4);
		starRegion5 = new TextureRegion(star2Tx, 0, 256, star2Tx.getWidth(), 64);
		starHolder.add(starRegion5);
		starRegion6 = new TextureRegion(star2Tx, 0, 320, star2Tx.getWidth(), 64);
		starHolder.add(starRegion6);
		starRegion7 = new TextureRegion(star2Tx, 0, 384, star2Tx.getWidth(), 64);
		starHolder.add(starRegion7);
		starRegion8 = new TextureRegion(star2Tx, 0, 448, star2Tx.getWidth(), 64);
		starHolder.add(starRegion8);
		starRegion0 = new TextureRegion(star2Tx, 0, 512, star2Tx.getWidth(), 64);
		starHolder.add(starRegion0);

		starSprite = new Sprite(starRegion0);
		starSprite.setSize(100f , 100f );
		starSprite.setOrigin(starSprite.getWidth()/2, starSprite.getHeight()/2);
		starSprite.setPosition(kid.getX(), kid.getY() + kid.getHeight());
		starLoop = 0;


		countHolder = new Array<TextureRegion>();
		countDownNumTx = new Texture(Gdx.files.internal("data/countDownNumbers.png"));
		countDownNumTx.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		count3  = new TextureRegion(countDownNumTx, 0, 512, countDownNumTx.getWidth(), 256); 
		countHolder.add(count3);
		count2  = new TextureRegion(countDownNumTx, 0, 256, countDownNumTx.getWidth(), 256); 
		countHolder.add(count2);
		count1  = new TextureRegion(countDownNumTx, 0, 0, countDownNumTx.getWidth(), 256); 
		countHolder.add(count1);
		countGo  = new TextureRegion(countDownNumTx, 0, 768, countDownNumTx.getWidth(), 256); 
		countHolder.add(countGo);

		CountSprite = new Sprite(countHolder.get(countDownFrame));
		CountSprite.setSize(CountSprite.getWidth() * 2, CountSprite.getHeight() * 2);
		CountSprite.setOrigin(CountSprite.getWidth()/2, CountSprite.getHeight()/2);
		CountSprite.setPosition(0 - CountSprite.getWidth()/2 , 0 - CountSprite.getHeight()/2);	



		//// Restart Btn
		restartBtnTx = new Texture(Gdx.files.internal("data/restartBtn.png"));
		restartBtnTx.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		TextureRegion restartRegion = new TextureRegion(restartBtnTx, 0, 0, restartBtnTx.getWidth(), restartBtnTx.getHeight());
		restartBtn = new Sprite(restartRegion);
		restartBtn.setSize(300f , 150f );
		restartBtn.setOrigin(restartBtn.getWidth()/2, restartBtn.getHeight()/2);
		restartBtn.setPosition(0 - restartBtn.getWidth()/2, -600);	

		//achHandler = new AchievementsHandler();

	}

	@Override
	public void render(float delta) {  // DETERMINE THE STATE TO RENDER

		switch (gameState) {
		case GAME_READY:
			gameReady();
			break;
		case GAME_RUNNING:

			if(Balls.size > 0){
				for(Ball ball: Balls) {
					if(ball.getBlownUpStatus() == true){
						//Log.i(MainKeepUp.TAG, "dead ball removed before render");
						Balls.removeValue(ball, true);
					}
				};
			}
			updateBallTimer(deltaTime);
			updateResourceTimer(deltaTime);
			gameRunning();
			break;
		case GAME_PAUSED:
			gamePaused();
			break;
		case GAME_OVER:
			gameOver = true;
			gameOver();
			break;
		}
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
		batch.dispose();
		bgTx.dispose();
		kidTx.dispose();
		goTx.dispose();
		pauseTx.dispose();
		psTx.dispose();
		restartBtnTx.dispose();
		star2Tx.dispose();
		generator.dispose();
		countDownNumTx.dispose();

		if(Balls.size > 0){
			for(Ball ball: Balls) {
				ball.ballTx.dispose();
			};
		}

		if(MedKits.size > 0){
			for(healthKit kit: MedKits) {
				kit.kitTx.dispose();
			};
		}

		if(Shields.size > 0){
			for(ShieldBoost shield: Shields) {
				shield.shieldTx.dispose();
			};
		}

		if(Timers.size > 0){
			for(FreezeMotionTimer timer: Timers) {
				timer.timerTx.dispose();
			};
		}

		if(Bombs.size > 0){
			for(Bomb bomb: Bombs) {
				bomb.bombTx.dispose();
			};
		}

		if(Marks.size > 0){
			for(LifeMarks marks: Marks) {
				marks.lifeTx.dispose();
			};
		}
	}

	/**
	 * When Player is struck by the ball
	 * this will add a Strike against them
	 */
	public void addLifeMark(){
		LifeMarks mark = new LifeMarks();
		Marks.add(mark);
	}


	/**
	 * When player heals, this will remove a strike mark
	 * if they currently have one.
	 */
	public void removeLifeMark(){
		int size = Marks.size;
		if(size > 0){
			Marks.removeIndex(size - 1);
		}
	}


	/**
	 * returns a random number between 40 and 90
	 */
	public float getRandomSize(){
		//		int high;
		//		int low;
		int add;
		if(gameDifficulty == 1){
			//			high = 180;
			//			low = 90;
			add = 90;
		}if(gameDifficulty == 1){
			//			high = 150;
			//			low = 70;
			add = 70;
		}else{
			//			high = 110;
			//			low = 50;
			add = 50;
		}

		//float r;
		randNumSize = Math.random();  // random 0.0 to 1.0
		if (randNumSize < 0.01){
			randNumSize = 0.01;
		}
		double rand = (randNumSize * 100);  // 1 to 1

		//		if (rand <= low){   
		//			r = (float)rand + low; 
		//			return r;
		//		} else if (rand >= high){ 
		//			r = high; 
		//			return r;
		//		}else{
		//			r = (float)rand;
		//			return r;
		//		}
		float myRand = (float) rand + add;
		return myRand;
	}

	/**
	 * Returns a random speed between 3 and 8
	 * @return
	 */

	public float getRandomSpeed(){
		randNumSpeed = Math.random();  // 0.0 to 1.0
		if(randNumSpeed < 0.1){
			randNumSpeed = 0.1;
		}
		float sp = (float) ((randNumSize * 5) + 3);
		return sp;
	}


	/**
	 * Returns a random number between 500 and -500
	 * Used for the X position starting location
	 * when the camera is set to 1000 units wide
	 * @return
	 */
	public float getRandomXLocation(){
		float xFloat;
		randNumXLoc = Math.random(); // 0.0 thru 1.0
		if(randNumXLoc == 0){  
			randNumXLoc = 0.01f;  // ensure not 0
		}

		int xLoc = (int)(randNumXLoc * screenXRefactor);  // from 1 to 1000 

		if(xLoc <= (screenXRefactor/2)){  // checks for the half mark 
			xFloat = (float) (xLoc * -1);   // make neg and float for ball position
			return xFloat;
		}else{
			xFloat = (float) (xLoc - (screenXRefactor/2));  // if over half (500) subtract half for pos numbers
			return xFloat;
		}
	}



	public void makeNewBall(){
		Ball ball = new Ball(getRandomXLocation(), getRandomSize(), getRandomSpeed());
		Balls.add(ball);
	}

	public void makeNewKit( ){
		//Gdx.app.log(MainKeepUp.TAG, "Make New Health Kit");
		healthKit kit = new healthKit(getRandomXLocation(), getRandomSpeed());
		MedKits.add(kit);
	}

	public void makeNewShield(){
		//Gdx.app.log(MainKeepUp.TAG, "Make New Shield");
		ShieldBoost shield = new ShieldBoost(getRandomXLocation(), getRandomSpeed());
		Shields.add(shield);
	}

	public void makeNewTimeClock(){
		//Gdx.app.log(MainKeepUp.TAG, "Make New Time Clock");
		FreezeMotionTimer timeclock = new FreezeMotionTimer(getRandomXLocation(), getRandomSpeed());
		Timers.add(timeclock);
	}

	public void makeNewBomb(){
		//Gdx.app.log(MainKeepUp.TAG, "Make New Bomb");
		Bomb bomb = new Bomb(getRandomXLocation(), getRandomSpeed());
		Bombs.add(bomb);
	}



	/**
	 * Using deltaTime this method cycles through an array of TextureRegions
	 * and animates a sprite.
	 * @param dt
	 */
	public void updateSTARS(float dt){
		ticker+=dt;
		if(ticker>timeLimit){
			ticker-=timeLimit;
			currentFrame=(currentFrame+1);
			starSprite.setRegion(starHolder.get(currentFrame));
			kid.rotate(45);
			if(currentFrame == starHolder.size - 1){
				kidHit= false;
				currentFrame = 0;
				kid.setRotation(0);
			} 
		}
	}

	/**
	 * using deltaTime, this method checks to see if the time is divisible by 5,
	 * if so a new ball it created.
	 * @param deltaTime
	 */

	public void updateBallTimer(float deltaTime){
		elapsedTime = deltaTime;
		if(deltaTime >= ballSpawnTimer){
			ballSpawnTimer = deltaTime + ballDifSpawnTime;
			makeNewBall();
		}
	}


	/**
	 * Using the deltaTime, this method checks the current time
	 * against the elapsed time.  If the elapsed time in greater than 
	 * the deltaTime a new Random resource is spawned.
	 * @param deltaTime
	 */
	public void updateResourceTimer(float deltaTime){
		elapsedTime = deltaTime;

		if(elapsedTime > resourceTimer){

			// Pull User defined resource spawn values
			int health = MainActivity.spawnRateKit;

			int shield = MainActivity.spawnRateKit + 
					MainActivity.spawnRateShield;

			int freeze = MainActivity.spawnRateKit + 
					MainActivity.spawnRateShield + 
					MainActivity.spawnRateFreeze;

			int bomb = MainActivity.spawnRateKit + 
					MainActivity.spawnRateShield + 
					MainActivity.spawnRateFreeze + 
					MainActivity.spawnRateBomb;

			// Get Random # 1 - 100
			int r = (int) (Math.random() * 100);  



			// Determine which resource is spawned
			if (r <= health){
				makeNewKit();
			}else if ((r > health) && (r <= shield)){
				makeNewShield();
			}else if ((r > shield) && (r <= freeze)){
				makeNewTimeClock();
			}else if ((r > freeze) && (r <= bomb)){
				makeNewBomb();	
			}else{
				// no spawn
			}

			// Reset elapsed time
			resourceTimer = elapsedTime + spawnRate;  
		}
	}

	public void gameRestart(){
		dispose();
		game.setScreen(new Game(game));

	}

	public void gameReady(){
		scoreUpdate();
		checkStrikeOut();
		deltaTime += Gdx.graphics.getDeltaTime();   
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		camera.update();
		batch.setProjectionMatrix(camera.combined);

		elapsedTime = deltaTime;


		if(elapsedTime > 1 + countDownFrame){  

			if(countDownFrame <= 3){
				CountSprite.setRegion(countHolder.get(countDownFrame));
			}
			countDownFrame=(countDownFrame+1);
		} 

		if(countDownFrame == 4){
			gameState = GAME_RUNNING;

		}


		batch.begin();
		bg.draw(batch);

		gameText.setFixedWidthGlyphs("Score: " + SCORE);
		gameText.draw(batch, "Score: " + SCORE, -480, (screenYRefactor / 2) - 5);
		CountSprite.draw(batch);

		kid.draw(batch);
		starSprite.draw(batch);

		batch.end();



	}


	private void gameRunning() {
		scoreUpdate();
		checkStrikeOut();
		deltaTime += Gdx.graphics.getDeltaTime();   
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		camera.update();
		batch.setProjectionMatrix(camera.combined);

		batch.begin();
		bg.draw(batch);

		gameText.setFixedWidthGlyphs("Score: " + SCORE);
		gameText.draw(batch, "Score: " + SCORE, -480, (screenYRefactor / 2) - 15);


		if((invincibility == false) && (shielded == false)){
			kid.draw(batch);
		}else{
			kid.draw(batch, .3f);  // drop Alpha while invincible
		}

		starSprite.draw(batch);

		if(Balls.size > 0){
			for(Ball ball: Balls) {
				ball.draw(batch);
			};
		}

		if(Timers.size > 0){
			for(FreezeMotionTimer timer: Timers) {
				timer.draw(batch);
			};
		}

		if(MedKits.size > 0){
			for(healthKit kit: MedKits) {
				kit.draw(batch);
			};
		}

		if(Shields.size > 0){
			for(ShieldBoost shield: Shields) {
				shield.draw(batch);
			};
		}

		if(Bombs.size > 0){
			for(Bomb bomb: Bombs) {
				bomb.draw(batch);
			};
		}

		if(Marks.size > 0){
			for(LifeMarks life: Marks) {
				life.draw(batch);
			};
		}

		if(invincibility == true){
			invincibilityTime = invincibilityTime + deltaShieldTime;

			if(invincibilityTime >= 2){
				invincibility = false;
				invincibilityTime = 0;
				deltaShieldTime = 0;
			}
		}


		if(freeze == true){
			if(elapsedTime >= deltaFreezeTime){
				freeze = false;
			}
		}

		if(shielded == true){	
			if(deltaTime >= shieldedTime){
				shielded = false;
				shieldedTime = 0;
				deltaShieldTime = 0;
			}
		}



		pause.draw(batch);
		batch.end();


		if(kidHit == true){
			updateSTARS(deltaTime);
		}

		if(Balls.size > 0){
			for(Ball ball: Balls) {
				if(ball.getBlownUpStatus() == false){
					ballLoopCheckAndSet(ball);
				}else{

				}
			};
		}

		if(MedKits.size > 0){
			for(healthKit kit: MedKits) {
				kitLoopCheckAndSet(kit);
			};
		}

		if(Shields.size > 0){
			for(ShieldBoost shield: Shields) {
				shieldLoopCheckAndSet(shield);
			};
		}

		if(Timers.size > 0){
			for(FreezeMotionTimer timer: Timers) {
				timerLoopCheckAndSet(timer);
			};
		}

		if(Bombs.size > 0){
			for(Bomb bomb: Bombs) {
				if(bomb.getRemoveTrigger() == true){ 

					//Gdx.app.log(MainKeepUp.TAG, "REMOVE BOMB FROM ARRAY");
					Bombs.removeValue(bomb, true);  // Remove bomb from Array

				}else{


					if(bomb.collision == false){ 
						// do nothing, just wait

					}else{  // if collision is TRUE
						bomb.setSizeXY(bomb.getSizeX() + 2, bomb.getSizeY() + 2);
						bomb.setBombRotationAngle(5);


						if (bomb.checkBombCountdown(deltaTime, numberOfBalls()) == false){ 
							//Gdx.app.log(MainKeepUp.TAG, "BOMB STILL COUNTING DOWN");	

						}else{  // If count down is Over

							bomb.setBombSpriteTexture(bombRegion);
							//Gdx.app.log(MainKeepUp.TAG, "BOMB STILL Explosion Phase");	
							bomb.bombLastPhaseExplosion(deltaTime);   // if time to blow up	
						}
					}						
					bombLoopCheckAndSet(bomb);					
				}				
			};
		}
	}

	public void scoreUpdate(){
		int items = 0;
		if(elapsedTime >= scoreTime){
			scoreTime = elapsedTime + scoreTimeInterval;
			items = Balls.size + MedKits.size + Shields.size + Timers.size;
			SCORE = SCORE + items;
			//Gdx.app.log(MainKeepUp.TAG, "SCORE = " + SCORE);
		}
	}

	private void gameOver() {

		kidMovable = false;
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

		if(Timers.size > 0){
			for(FreezeMotionTimer timer: Timers) {
				timer.draw(batch);
			};
		}

		if(MedKits.size > 0){
			for(healthKit kit: MedKits) {
				kit.draw(batch);
			};
		}

		if(Shields.size > 0){
			for(ShieldBoost shield: Shields) {
				shield.draw(batch);
			};
		}

		if(Bombs.size > 0){
			for(Bomb bomb: Bombs) {
				bomb.draw(batch);
			};
		}

		if(Marks.size > 0){
			for(LifeMarks life: Marks) {
				life.draw(batch);
			};
		}

		go.draw(batch);
		restartBtn.draw(batch);
		batch.end();

		if(Balls.size > 0){
			for(Ball ball: Balls) {
				ballLoopCheckOnPause(ball);
			};
		}

		if(Timers.size > 0){
			for(FreezeMotionTimer timer: Timers) {
				timerLoopCheckOnPause(timer);
			};
		}

		if(MedKits.size > 0){
			for(healthKit kit: MedKits) {
				kitLoopCheckOnPause(kit);
			};
		}

		if(Shields.size > 0){
			for(ShieldBoost shield: Shields) {
				shieldLoopCheckOnPause(shield);
			};
		}

		if(Bombs.size > 0){
			for(Bomb bomb: Bombs) {
				bombLoopCheckOnPause(bomb);
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
		gameText.setFixedWidthGlyphs("Score: " + SCORE);
		gameText.draw(batch, "Score: " + SCORE, -500, (screenYRefactor / 2) - 40);

		if(Balls.size > 0){
			for(Ball ball: Balls) {
				ball.draw(batch);
			};
		}

		if(Timers.size > 0){
			for(FreezeMotionTimer timer: Timers) {
				timer.draw(batch);
			};
		}

		if(MedKits.size > 0){
			for(healthKit kit: MedKits) {
				kit.draw(batch);
			};
		}

		if(Shields.size > 0){
			for(ShieldBoost shield: Shields) {
				shield.draw(batch);
			};
		}

		if(Bombs.size > 0){
			for(Bomb bomb: Bombs) {
				bomb.draw(batch);
			};
		}

		if(Marks.size > 0){
			for(LifeMarks life: Marks) {
				life.draw(batch);
			};
		}

		ps.draw(batch);
		pause.draw(batch);

		batch.end();

		if(Balls.size > 0){
			for(Ball ball: Balls) {
				ballLoopCheckOnPause(ball);
			};
		}

		if(Timers.size > 0){
			for(FreezeMotionTimer timer: Timers) {
				timerLoopCheckOnPause(timer);
			};
		}

		if(MedKits.size > 0){
			for(healthKit kit: MedKits) {
				kitLoopCheckOnPause(kit);
			};
		}

		if(Shields.size > 0){
			for(ShieldBoost shield: Shields) {
				shieldLoopCheckOnPause(shield);
			};
		}

		if(Bombs.size > 0){
			for(Bomb bomb: Bombs) {
				bombLoopCheckOnPause(bomb);
			};
		}
	}

	/**
	 * Checks the number of X's against the player.  If 3 >= 
	 * this function starts the game over and score submit functions
	 */

	public void checkStrikeOut(){
		if(isSignedIn == true){


			if((SCORE <= 50) && (ach50 == false)){
				_mainActivity.achievement50();
				ach50 = true;
			}

			if((SCORE >= 500) && (ach500 == false)){
				_mainActivity.achievement500();
				ach500 = true;
			}

			if((SCORE >= 750) && (ach750 == false)){
				_mainActivity.achievement750();
				ach750 = true;
			}

			if((SCORE >= 1000) && (ach1000 == false)){
				_mainActivity.achievement1000();
				ach1000 = true;
			}

		}




		if(Marks.size >= 3){
			boolean submited = false;
			_mainActivity.setFinalScore(SCORE);
			boolean online = _mainActivity.isOnline();
			boolean signedIn = _mainActivity.getSignedIn();
			String uName = _mainActivity.getSavedUserName();

			if(submited == false){
				// if Online and Signed in

				if((online == true) && (signedIn == true)){
					_mainActivity.checkAndPushAchievements(SCORE, kitsUsed, pointsReceivedBeforeFirstResourceUsed);
					_mainActivity.submitScore(SCORE);  // SEND SCORE to Google Play
					submited = true;
					try {

						if(uName == ""){
							_mainActivity.getUsername();  
						}else{
							_scoreSource.createScore(uName, SCORE, _mainActivity.getGameDifficulty());
							_mainActivity.notifyUser(SCORE);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}


				}else if ((online == true)  && (signedIn == false)){  // not signed 
					submited = true;
					try {
						_mainActivity.getUsername();  
					} catch (Exception e) {
						e.printStackTrace();
					}


				}else{
					submited = true;
					_mainActivity.getUsername();
				}
			}
			gameState = GAME_OVER;
		}
	}



	public void ballLoopCheckAndSet(Ball ball){
		Sprite ballSprite = ball.getBallSprite();

		if( freeze == false){

			float xPosition = ball.getXPosition();
			float yPosition = ball.getYPosition();
			float yPosSpriteHeight = yPosition + ballSprite.getWidth();
			float moveX;
			float moveY;


			if(xPosition + ballSprite.getHeight() >= (screenXRefactor/2)){ 
				ball.setXSpeed(ball.getXSpeed() * -1);
				ball.setXPosition(ball.getXPosition() + ball.getXSpeed());
				bounce3.play(.7f);
			}

			if(yPosSpriteHeight >= screenYRefactor / 2){
				ball.setYSpeed(ball.getYSpeed() * -1);
				ball.setYPosition(ball.getYPosition() + ball.getYSpeed());
				bounce1.play(.6f);	
			}
			if(xPosition <= (screenXRefactor/2) * -1){
				ball.setXSpeed(ball.getXSpeed() * -1);
				ball.setXPosition(ball.getXPosition() + ball.getXSpeed());
				bounce2.play(.5f);
			} 
			if(yPosition <= (screenYRefactor / 2) * -1){
				ball.setYSpeed(ball.getYSpeed() * -1);
				ball.setYPosition(ball.getYPosition() + ball.getYSpeed());
				bounce1.play(.8f);	
			}

			ball.setXPosition(ball.getXPosition() + ball.getXSpeed());
			ball.setYPosition(ball.getYPosition() + ball.getYSpeed());

			moveX = Interpolation.linear.apply(ball.getXPosition(), ball.getXPosition() + ball.getXSpeed(), 1);
			moveY = Interpolation.linear.apply(ball.getYPosition(), ball.getYPosition() + ball.getYSpeed(), 1);

			ballSprite.setX(moveX);
			ballSprite.setY(moveY);
			ball.setCircleXY(moveX + ballSprite.getWidth()/2 , moveY + ballSprite.getHeight()/2 );

			boolean kidVsCircleOverlap = ball.getOverlapBool(kid.getBoundingRectangle() );

			if(kidVsCircleOverlap == true){
				if(ball.collision == false){  //  check for current collision
					if(shielded == false){
						if(invincibility == false){
							if(_mainActivity.getVibBool() == true){
								_mainActivity.vibrate(300);
							}
							invincibility = true;
							deltaShieldTime = Gdx.graphics.getDeltaTime();
							addLifeMark();
							kidHit = true;
							buzzer.play(.05f);
							ball.collision = true;  // sets current collision
							ball.setYSpeed(ball.getYSpeed() * -1);
							ball.setXSpeed(ball.getYSpeed() * -1);

							ball.setXPosition(ball.getXPosition() + ball.getXSpeed());
							ball.setYPosition(ball.getYPosition() + ball.getYSpeed());
							ballSprite.setX(moveX);
							ballSprite.setY(moveY);
							ball.setCircleXY(moveX + ballSprite.getWidth()/2 , moveY + ballSprite.getHeight()/2 );
						}
					}
				}
			}else{
				ball.collision = false;  // resets to false after overlap ends
			}

			if(Bombs.size > 0){
				for(Bomb bomb: Bombs) {
					if(bomb.checkDestructionPhase() == true){
						Sprite b = bomb.getBombSprite();
						boolean bombVsCircleOverlap = ball.getOverlapBool(b.getBoundingRectangle() );
						if(bombVsCircleOverlap == true){
							ball.setBlownUp(true);
						}
					}
				}
			}
		}
	}


	public void kitLoopCheckAndSet(healthKit kit){
		Sprite kitSprite = kit.getKitSprite();

		float yPosition = kit.getYPosition();
		float yPosSpriteHeight = yPosition + kitSprite.getWidth();

		if(yPosSpriteHeight >= screenYRefactor / 2){
			kit.setYSpeed(kit.getYSpeed() * -1);
			kit.setYPosition(kit.getYPosition() + kit.getYSpeed());
			hardBounce.play(0.5f);	
		}


		if(yPosition <= (screenYRefactor / 2) * -1){
			kit.setYSpeed(kit.getYSpeed() * -1);
			kit.setYPosition(kit.getYPosition() + kit.getYSpeed());
			hardBounce.play(0.5f);	
		}

		kit.setYPosition(kit.getYPosition() + kit.getYSpeed());

		float moveY = Interpolation.linear.apply(kit.getYPosition(), kit.getYPosition() + kit.getYSpeed(), 1);
		kitSprite.setY(moveY);

		boolean kidVSKitOverlap = kitSprite.getBoundingRectangle().overlaps(kid.getBoundingRectangle());

		if(kidVSKitOverlap == true){		
			powerUp.play(.05f);
			removeLifeMark();	
			kitsUsed = kitsUsed + 1;  // increase kit count for final point 
			if(isSignedIn == true){
				_mainActivity.achievementKitsUsed();
			}
			MedKits.removeValue(kit, true);  
			if(firstResourceUsed == true){
				pointsReceivedBeforeFirstResourceUsed = SCORE;
				firstResourceUsed = false;
			}
		}

	}

	public void shieldLoopCheckAndSet(ShieldBoost shield){
		Sprite ShieldSprite = shield.getShieldSprite();

		float yPosition = shield.getYPosition();
		float yPosSpriteHeight = yPosition + ShieldSprite.getWidth();


		if(yPosSpriteHeight >= screenYRefactor / 2){
			shield.setYSpeed(shield.getYSpeed() * -1);
			shield.setYPosition(shield.getYPosition() + shield.getYSpeed());
			hardBounce.play(0.5f);	
		}


		if(yPosition <= (screenYRefactor / 2) * -1){
			shield.setYSpeed(shield.getYSpeed() * -1);
			shield.setYPosition(shield.getYPosition() + shield.getYSpeed());
			hardBounce.play(0.5f);	
		}

		shield.setYPosition(shield.getYPosition() + shield.getYSpeed());

		float moveY = Interpolation.linear.apply(shield.getYPosition(), shield.getYPosition() + shield.getYSpeed(), 1);
		ShieldSprite.setY(moveY);
		boolean kidVSShieldOverlap = ShieldSprite.getBoundingRectangle().overlaps(kid.getBoundingRectangle());

		if(kidVSShieldOverlap == true){	
			shieldedTime = deltaTime + 4f;
			powerUp.play(.05f);	
			shielded = true;
			deltaShieldTime = Gdx.graphics.getDeltaTime();
			Shields.removeValue(shield, true);  
			if(firstResourceUsed == true){
				pointsReceivedBeforeFirstResourceUsed = SCORE;
				firstResourceUsed = false;
			}
		}

	}

	public void bombLoopCheckAndSet(Bomb bomb){

		Sprite bombSprite = bomb.getBombSprite();
		float yPosition = bomb.getYPosition();
		float yPosSpriteHeight = yPosition + bombSprite.getWidth();

		if(yPosSpriteHeight >= screenYRefactor / 2){
			bomb.setYSpeed(bomb.getYSpeed() * -1);
			bomb.setYPosition(bomb.getYPosition() + bomb.getYSpeed());
			if(bomb.collision == false){
				hardBounce.play(0.5f);	
			}
		}


		if(yPosition <= (screenYRefactor / 2) * -1){
			bomb.setYSpeed(bomb.getYSpeed() * -1);
			bomb.setYPosition(bomb.getYPosition() + bomb.getYSpeed());
			if(bomb.collision == false){
				hardBounce.play(0.5f);	
			}	
		}

		bomb.setYPosition(bomb.getYPosition() + bomb.getYSpeed());

		float moveY = Interpolation.linear.apply(bomb.getYPosition(), bomb.getYPosition() + bomb.getYSpeed(), 1);	
		bombSprite.setY(moveY);

		boolean kidVSBombOverlap = bombSprite.getBoundingRectangle().overlaps(kid.getBoundingRectangle());
		if(kidVSBombOverlap == true){	
			if(bomb.getCollision() == false){
				bomb.setCollision(true);
				bomb.setTheDeltaBombTime(deltaTime);
				timeBomb.play(.07f);
				bomb.setYSpeed(0);
				if(firstResourceUsed == true){
					pointsReceivedBeforeFirstResourceUsed = SCORE;
					firstResourceUsed = false;
				}

			}

		}
	}



	public void timerLoopCheckAndSet(FreezeMotionTimer timer){
		Sprite timerSprite = timer.getTimerSprite();


		float yPosition = timer.getYPosition();
		float yPosSpriteHeight = yPosition + timerSprite.getWidth();

		if(yPosSpriteHeight >= screenYRefactor / 2){
			//Gdx.app.log(TAG, "Out of bounds Right");
			timer.setYSpeed(timer.getYSpeed() * -1);
			timer.setYPosition(timer.getYPosition() + timer.getYSpeed());
			hardBounce.play(0.5f);	
		}


		if(yPosition <= (screenYRefactor / 2) * -1){
			//Gdx.app.log(TAG, "Out of bounds Left");
			timer.setYSpeed(timer.getYSpeed() * -1);
			timer.setYPosition(timer.getYPosition() + timer.getYSpeed());
			hardBounce.play(0.5f);	
		}

		timer.setYPosition(timer.getYPosition() + timer.getYSpeed());

		float moveY = Interpolation.linear.apply(timer.getYPosition(), timer.getYPosition() + timer.getYSpeed(), 1);
		timerSprite.setY(moveY);
		boolean kidVSTimerOverlap = timerSprite.getBoundingRectangle().overlaps(kid.getBoundingRectangle());

		if(kidVSTimerOverlap == true){	

			freeze = true;
			deltaFreezeTime = elapsedTime + FreezeTimer;
			powerUp.play(.05f);			
			Timers.removeValue(timer, true); 

			if(firstResourceUsed == true){
				pointsReceivedBeforeFirstResourceUsed = SCORE;
				firstResourceUsed = false;
			}
		}


	}

	public void ballLoopCheckOnPause(Ball ball){
		Sprite ballSprite = ball.getBallSprite();
		ballSprite.setX(ball.getXPosition());
		ballSprite.setY(ball.getYPosition());
	}

	public void timerLoopCheckOnPause(FreezeMotionTimer timer){
		Sprite timerSprite = timer.getTimerSprite();
		timerSprite.setX(timer.getXPosition());
		timerSprite.setY(timer.getYPosition());
	}

	public void kitLoopCheckOnPause(healthKit kit){
		Sprite kitSprite = kit.getKitSprite();
		kitSprite.setX(kit.getXPosition());
		kitSprite.setY(kit.getYPosition());
	}

	public void shieldLoopCheckOnPause(ShieldBoost shield){
		Sprite shieldSprite = shield.getShieldSprite();
		shieldSprite.setX(shield.getXPosition());
		shieldSprite.setY(shield.getYPosition());
	}

	public void bombLoopCheckOnPause(Bomb bomb){
		Sprite bombSprite = bomb.getBombSprite();
		bombSprite.setX(bomb.getXPosition());
		bombSprite.setY(bomb.getYPosition());
	}

	public void checkUnPause(){
		if(pauseGame == true){
			pauseGame = false;
			gameState = GAME_RUNNING;
			kidMovable = true;
			resume();
		}else{
			gameState = GAME_PAUSED;
			pauseGame = true;
			kidMovable = false;
			pause();
		}
	}


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
			float xPos = cameraRay.origin.x;
			float yPos = cameraRay.origin.y;

			if(xPos <= -500 + kid.getWidth()/2){
				xPos = -500;
				kid.setX(xPos);

			}else if(xPos >= 500 - kid.getWidth()){
				xPos = 500 - kid.getWidth();
				kid.setX(xPos - kid.getWidth() / 2);
			}else{
				kid.setX(xPos - kid.getWidth() / 2);  //the /2 is set for pointer center
			}

			if(yPos <= ((screenYRefactor / 2)) * -1){ 
				yPos = (screenYRefactor / 2) * -1;
				kid.setY(yPos);
			}else if(yPos >= (screenYRefactor / 2) - (kid.getHeight())){
				yPos = (screenYRefactor / 2) - (kid.getHeight());
				kid.setY(yPos);
			}else{	
				kid.setY(yPos);
			}


			starSprite.setX(kid.getX());  // add the star sprite above character
			starSprite.setY(kid.getY() + kid.getHeight());// add the star sprite above character
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


	@Override
	public boolean keyDown(int keycode) {
		if(keycode == Keys.BACK){
			dispose();
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


	public int numberOfBalls(){
		return Balls.size;
	}

}
