package com.klusman.keepup;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class healthKit {

	public Texture kitTx;
	public String textureAddress;
	private Sprite kitSprite;
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
	
	
	public healthKit(float randX){
		
		
		textureAddress = "data/medKit.png";
		sizeX = 75f; //.09f;
		sizeY = 75f; //.09f;
		xSpeed = 0; //0.005f;
		ySpeed = 5f; //0.005f;;
		//rotationSpeed = 5;
		PositionY = 600f;
		PositionX = randX; 
		rotationDirection = true;
		collision = false;
		
		
		
		
		kitTx = new Texture(Gdx.files.internal(textureAddress));
		kitTx.setFilter(TextureFilter.Linear, TextureFilter.Linear);
			
		TextureRegion kitRegion = new TextureRegion(kitTx, 0, 0, kitTx.getWidth(), kitTx.getHeight());
		kitSprite = new Sprite(kitRegion);
		kitSprite.setSize(sizeX, sizeY);
		kitSprite.setOrigin(kitSprite.getWidth()/2, kitSprite.getHeight()/2);
		kitSprite.setPosition(PositionX, PositionY - kitSprite.getHeight()/2);
		
		
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
	kitSprite.setSize(sizeX, sizeY);
	kitSprite.setOrigin(kitSprite.getWidth()/2, kitSprite.getHeight()/2);
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
public Sprite getKitSprite(){
	return kitSprite;
}

public void draw(SpriteBatch batch) {
	kitSprite.draw(batch);
}

	
	
	
	
}
