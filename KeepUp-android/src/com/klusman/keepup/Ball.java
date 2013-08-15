package com.klusman.keepup;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.klusman.keepup.screens.Game;



public class Ball {
	//private static String TAG = "KeepUp";
	public Texture ballTx;
	public String textureAddress;
	private Sprite ballSprite;
	float sizeX;
	float sizeY;
	float xSpeed;
	float ySpeed;
	float PositionX;  
	float PositionY;
	int rotationSpeed;
	public boolean rotationDirection;
	public boolean collision;
	public final Circle circle;
	double randNumXLoc;
	boolean blownUp = false;
	
	
	public Ball (float randX, float size, float speed){
		
		
		textureAddress = "data/redBall.png";
		sizeX = size;
		sizeY = size; 
		xSpeed = speed; 
		ySpeed = speed; 
		//rotationSpeed = 5;
		
		double rNum = Math.random(); // 0.0 thru 1.0
		if(rNum == 0){  
			rNum = 0.01f;  // ensure not 0
		}
		
		if (rNum <= .2){
			textureAddress = "data/redBall.png";
		}else if ((rNum > .2) && (rNum <= .4)){
			textureAddress = "data/blueBall.png";
		}else if ((rNum > .4) && (rNum <= .6)){
			textureAddress = "data/yellowBall.png";
		}else if ((rNum > .6) && (rNum <= .8)){
			textureAddress = "data/purpBall.png";
		}else if (rNum > .8){
			textureAddress = "data/greenBall.png";
		}
		
		
		
		
		ballTx = new Texture(Gdx.files.internal(textureAddress));
		ballTx.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		TextureRegion ballRegion = new TextureRegion(ballTx, 0, 0, ballTx.getWidth(), ballTx.getHeight());
		ballSprite = new Sprite(ballRegion);
		ballSprite.setSize(sizeX, sizeY);
		ballSprite.setOrigin(ballSprite.getWidth()/2, ballSprite.getHeight()/2);
		
		
		
		PositionX = randX; // random position -500 to 500 
		PositionY = Game.screenYRefactor / 2 - (sizeX + 10);
		rotationDirection = true;
		collision = false;
		
		
		if(randX >= 0 ){   // set random X position plus a 10 point buffer
			PositionX = randX - (ballSprite.getWidth() + 10);
			ballSprite.setPosition(PositionX , PositionY );
		}else{
			PositionX = randX + (ballSprite.getWidth() + 10);
			ballSprite.setPosition(PositionX , PositionY );
		}
		
			
		
		circle = new Circle();
		circle.setRadius(sizeX / 2 - 10);  // making the circle a little smaller to then the sprite for better collision
		circle.setPosition(ballSprite.getX() - ballSprite.getWidth() / 2, ballSprite.getY() - ballSprite.getHeight()/2 );
		
		
	}
	
	
////	
	public float getSizeX(){
		return sizeX;
	}
	
	public float getSizeY(){
		return sizeY;
	}
	
	public void setSizeXY(float floatSizeX, float floatSizeY){
		sizeX = floatSizeX;
		sizeY = floatSizeY;
		ballSprite.setSize(sizeX, sizeY);
		ballSprite.setOrigin(ballSprite.getWidth()/2, ballSprite.getHeight()/2);
	}
	
////	
	public float getXSpeed(){
		return xSpeed;
	}
	
	public float getYSpeed(){
		return ySpeed;
	}
	
	public void setXSpeed(float floatXSpeed){
		xSpeed = floatXSpeed;
	}
	
	public void setYSpeed(float floatYSpeed){
		ySpeed = floatYSpeed;
	}
		
	public Circle getCircle(){
		return circle;
	}
	
	public boolean getOverlapBool(Rectangle r){
		//Gdx.app.log(MainKeepUp.TAG,	 "getOverLap");
		boolean sect = Intersector.overlaps(circle, r); 
		return sect;
	}
	
////
//	public int getRotationSpeed(){
//		return rotationSpeed;
//	}
//	
//	public void setRotationSpeed(int rotSpeed){
//		rotationSpeed = rotSpeed;
//	}

////	
	public boolean getRotation(){
		return rotationDirection;
	}
	
	public void setRotation(boolean boolRotationDirection){
		rotationDirection = boolRotationDirection;
	}
	
////	
	public boolean getCollision(){
		return collision;
	}
	
	public void setCollision(boolean boolCollision){
		collision = boolCollision;
	}
	
////	
	public float getXPosition(){
		return PositionX;
	}
	
	public void setXPosition(float xPos){
		PositionX = xPos;
	}
	
////
	public float getYPosition(){
		return PositionY;
	}
	
	public void setYPosition(float yPos){
		PositionY = yPos;
	}
	
////
	public Sprite getBallSprite(){
		return ballSprite;
	}

	public void draw(SpriteBatch batch) {
		ballSprite.draw(batch);
	}
////
	public float getCircleX(){
		return circle.x;
	}
	
	public void setCircleXY( float xPos, float yPos){
		circle.setPosition(xPos, yPos);
	}

	public void setBlownUp(boolean hit){
		blownUp = hit;
	}
	
	public boolean getBlownUpStatus(){
		return blownUp;
	}
}
