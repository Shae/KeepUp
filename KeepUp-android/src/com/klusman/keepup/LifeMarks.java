package com.klusman.keepup;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.klusman.keepup.screens.Game;

public class LifeMarks {
	//private static String TAG = "KeepUp";
	public Texture lifeTx;
	private Sprite lifeSprite;
	float sizeX;
	float sizeY;
	float PositionX;  
	float PositionY;
	
	public LifeMarks(){
		sizeX = 60f; 
		sizeY = 60f; 
		lifeTx = new Texture(Gdx.files.internal("data/redX.png"));
		lifeTx.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		TextureRegion lifeRegion = new TextureRegion(lifeTx, 0, 0, lifeTx.getWidth(), lifeTx.getHeight());
		lifeSprite = new Sprite(lifeRegion);
		lifeSprite.setSize(sizeX , sizeY );
		lifeSprite.setOrigin(lifeSprite.getWidth()/2, lifeSprite.getHeight()/2);
		PositionX = (((Game.screenXRefactor/2) - 20 ) - ( sizeX  * (Game.Marks.size +1) ) );
		PositionY = (Game.screenYRefactor/2 - lifeSprite.getHeight()) - 5;
		lifeSprite.setPosition(PositionX, PositionY);	
	
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
	lifeSprite.setSize(sizeX, sizeY);
	lifeSprite.setOrigin(lifeSprite.getWidth()/2, lifeSprite.getHeight()/2);
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
	return lifeSprite;
}

public void draw(SpriteBatch batch) {
	lifeSprite.draw(batch);
}



	
}
