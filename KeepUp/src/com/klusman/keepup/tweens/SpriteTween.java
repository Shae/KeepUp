package com.klusman.keepup.tweens;


import com.badlogic.gdx.graphics.g2d.Sprite;

import aurelienribon.tweenengine.TweenAccessor;

public class SpriteTween implements TweenAccessor<Sprite>{

	public static final int ALPHA = 1;
	public static final int POSITION = 2;
	public static final int POSITION_X = 3;
    public static final int POSITION_Y = 4;
    public static final int POSITION_XY = 5;
    public static final int SCALE_XY = 6;


	@Override
	public int getValues(Sprite target, int tweenType, float[] returnValues) {
		switch(tweenType){
		case ALPHA:
			returnValues[0] = target.getColor().a;
			return 1;
		case POSITION:
			return 1;
		case POSITION_X: returnValues[0] = target.getX(); 
			return 1;
        case POSITION_Y: returnValues[0] = target.getY(); 
        	return 1;
        case POSITION_XY:
            returnValues[0] = target.getX();
            returnValues[1] = target.getY();
            return 2;
        case SCALE_XY:
            returnValues[0] = target.getScaleX();
            returnValues[1] = target.getScaleY();
            return 2;
		default: 
			return 0;
		}
	}

	@Override
	public void setValues(Sprite target, int tweenType, float[] newValues) {
		switch(tweenType){
			case ALPHA:
				target.setColor(1,1,1,newValues[0]);
				break;
			case POSITION:
				target.setPosition(newValues[0], newValues[1]);
				break;
			case POSITION_X: target.setX(newValues[0]); 
				break;
            case POSITION_Y: target.setY(newValues[0]); 
            	break;
            case POSITION_XY:
                target.setX(newValues[0]);
                target.setY(newValues[1]);
                break;
            case SCALE_XY: 
            	target.setScale(newValues[0], newValues[1]); 
            	break;
		}
		
	}

}
