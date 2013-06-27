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
	
	
	public Ball (){
		
		
		textureAddress = "data/redBall.png";
		sizeX = 90f; //.09f;
		sizeY = 90f; //.09f;
		xSpeed = 5f; //0.005f;
		ySpeed = 5f; //0.005f;;
		//rotationSpeed = 5;
		PositionY = 600f;
		PositionX = 0; 
		rotationDirection = true;
		collision = false;
		
		
		
		
		ballTx = new Texture(Gdx.files.internal(textureAddress));
		ballTx.setFilter(TextureFilter.Linear, TextureFilter.Linear);
			
		TextureRegion ballRegion = new TextureRegion(ballTx, 0, 0, ballTx.getWidth(), ballTx.getHeight());
		ballSprite = new Sprite(ballRegion);
		//ballSprite.rotate(90);
		ballSprite.setSize(sizeX, sizeY);
		ballSprite.setOrigin(ballSprite.getWidth()/2, ballSprite.getHeight()/2);
		ballSprite.setPosition(PositionX, PositionY - ballSprite.getHeight()/2);
		
		
		
		
		
		
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

}
