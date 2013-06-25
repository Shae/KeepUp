package com.klusman.keepup;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


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
	
	
	public Ball (){
		
		
		textureAddress = "data/grnBall.png";
		sizeX = 90f; //.09f;
		sizeY = 90f; //.09f;
		xSpeed = 5f; //0.005f;
		ySpeed = 5f; //0.005f;;
		PositionY = 600f;
		rotationSpeed = 5;
		rotationDirection = true;
		collision = false;
		
		ballTx = new Texture(Gdx.files.internal(textureAddress));
		ballTx.setFilter(TextureFilter.Linear, TextureFilter.Linear);
			
		TextureRegion ballRegion = new TextureRegion(ballTx, 0, 0, ballTx.getWidth(), ballTx.getHeight());
		ballSprite = new Sprite(ballRegion);
		ballSprite.rotate(90);
		ballSprite.setSize(sizeX, sizeY);
		ballSprite.setOrigin(ballSprite.getWidth()/2, ballSprite.getHeight()/2);
		ballSprite.setPosition(PositionX, PositionY - ballSprite.getHeight()/2);
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
		
////
	public int getRotationSpeed(){
		return rotationSpeed;
	}
	
	public void setRotationSpeed(int rotSpeed){
		rotationSpeed = rotSpeed;
	}

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
	
	

}
