package com.klusman.keepup;

import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.klusman.keepup.screens.Game;

public class Bomb {

	public Texture bombTx;
	public String textureAddress;
	private Sprite bombSprite;
	float sizeX;
	float sizeY;
	float xSpeed;
	float ySpeed;
	float PositionX;  
	float PositionY;
	int rotationSpeed;
	public boolean rotationDirection;
	public boolean collision;
	double randNumXLoc;
	float theBombTime = 1f;
	public float theDeltaBombTime;
	public boolean boomTimeToBlow = false;
	boolean removeMe = false;
	float startedlastphaseAt = 0;
	boolean explosionTexture = false;

	//public Array<TextureRegion> textureHolder;
	TextureRegion bombRegion2;
	
	
	public Bomb(float randX , float speed){
		
		
		textureAddress = "data/bomb.png";
		sizeX = 65f; 
		sizeY = 65f; 
		xSpeed = 0; 
		ySpeed = -speed; 
		//rotationSpeed = 5;
		PositionY = 600f;
		PositionX = randX; 
		rotationDirection = true;
		collision = false;
		
		
		
		bombTx = new Texture(Gdx.files.internal(textureAddress));
		bombTx.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		TextureRegion bombRegion1 = new TextureRegion(bombTx, 0, 0, bombTx.getWidth(), 64);
		bombRegion2 = new TextureRegion(bombTx, 0, 64, bombTx.getWidth(), 64);
		
		//textureHolder.add(bombRegion1);
		//TextureRegion bombRegion2 = new TextureRegion(bombTx, 0, 64, bombTx.getWidth(), 64);
		//textureHolder.add(bombRegion2);
		

		bombSprite = new Sprite(bombRegion1);
		bombSprite.setSize(sizeX, sizeY);
		bombSprite.setOrigin(bombSprite.getWidth()/2, bombSprite.getHeight()/2);
		PositionX = randX; // random position -500 to 500 
		PositionY = Game.screenYRefactor / 2 - 100;
		if(randX >= 0 ){   // set random X position plus a 10 point buffer
			PositionX = randX - (bombSprite.getWidth() + 10);
			bombSprite.setPosition(PositionX , PositionY );
		}else{
			PositionX = randX + (bombSprite.getWidth() + 10);
			bombSprite.setPosition(PositionX , PositionY );

		}
		
	}
	
////
public float getSizeX(){
	return sizeX;
}

public float getSizeY(){
	return sizeY;
}
public boolean getBooleanTextureWasSwapped(){
	return explosionTexture;
}

public void setTextureSwapBool(Boolean swapped){
	explosionTexture = swapped;
}

public void setRemoveTrigger(){
	removeMe = true;
}

public boolean getRemoveTrigger(){
	return removeMe;
}

public void setTheDeltaBombTime(float deltaBombTime){
	theDeltaBombTime = deltaBombTime + theBombTime;
}

public float getTheDeltaBombTime(){
	return theDeltaBombTime;
}


public void setSizeXY(float floatSizeX, float floatSizeY){
	sizeX = floatSizeX;
	sizeY = floatSizeY;
	bombSprite.setSize(sizeX, sizeY);
	bombSprite.setOrigin(bombSprite.getWidth()/2, bombSprite.getHeight()/2);
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
	

public int getRotationSpeed(){
	return rotationSpeed;
}

public void setRotationSpeed(int rotSpeed){
	rotationSpeed = rotSpeed;
}


public void setBombRotationAngle(float rotation){
	float r = bombSprite.getRotation();
	bombSprite.setRotation(r + rotation);
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
public Sprite getBombSprite(){
	return bombSprite;
}

public void setBombSpriteTexture(TextureRegion txRegion){
	bombSprite.setRegion(txRegion);
}

public void draw(SpriteBatch batch) {
	bombSprite.draw(batch);
}

public boolean checkBombCountdown(float deltaTime, int numBalls){

	if(theDeltaBombTime >= deltaTime){  // under time limit do nada
		boomTimeToBlow = false;
		//Log.i(MainKeepUp.TAG, "Number of balls : " + numBalls);
		return false;
	}else{
		if(boomTimeToBlow == false){  // check to set once and not repeat
			startedlastphaseAt = deltaTime + 1.0f;
			boomTimeToBlow = true;
		//	Log.i(MainKeepUp.TAG, "Number of balls : " + numBalls);
		}
		return true;
	}
}

public boolean checkDestructionPhase(){
	return boomTimeToBlow;
}

public void bombLastPhaseExplosion(float deltaTime){
	
	if(deltaTime >= startedlastphaseAt){
		removeMe = true;
	}
}
	
	
	
}
