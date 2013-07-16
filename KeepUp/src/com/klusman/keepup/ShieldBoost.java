package com.klusman.keepup;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ShieldBoost {

	public Texture shieldTx;
	public String textureAddress;
	private Sprite shieldSprite;
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
	
	
	public ShieldBoost(float randX){
		
		
		textureAddress = "data/shield.png";
		sizeX = 75f; //.09f;
		sizeY = 75f; //.09f;
		xSpeed = 0; //0.005f;
		ySpeed = 5f; //0.005f;;
		//rotationSpeed = 5;
		PositionY = 600f;
		PositionX = randX; 
		rotationDirection = true;
		collision = false;
		
		
		
		
		shieldTx = new Texture(Gdx.files.internal(textureAddress));
		shieldTx.setFilter(TextureFilter.Linear, TextureFilter.Linear);
			
		TextureRegion kitRegion = new TextureRegion(shieldTx, 0, 0, shieldTx.getWidth(), shieldTx.getHeight());
		shieldSprite = new Sprite(kitRegion);
		shieldSprite.setSize(sizeX, sizeY);
		shieldSprite.setOrigin(shieldSprite.getWidth()/2, shieldSprite.getHeight()/2);
		shieldSprite.setPosition(PositionX, PositionY - shieldSprite.getHeight()/2);
		
		
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
	shieldSprite.setSize(sizeX, sizeY);
	shieldSprite.setOrigin(shieldSprite.getWidth()/2, shieldSprite.getHeight()/2);
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
//public int getRotationSpeed(){
//	return rotationSpeed;
//}
//
//public void setRotationSpeed(int rotSpeed){
//	rotationSpeed = rotSpeed;
//}

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
public Sprite getShieldSprite(){
	return shieldSprite;
}

public void draw(SpriteBatch batch) {
	shieldSprite.draw(batch);
}

	
	
	
	
}
