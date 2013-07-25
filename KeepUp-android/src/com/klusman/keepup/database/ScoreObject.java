package com.klusman.keepup.database;

public class ScoreObject {

	 	public long id;
	    public String name;
	    public int score;


	    
//////////////	    
	    public long getId(){
	    	return id;
	    }
	    
	    public void setId(long id){
	    	this.id = id;
	    }
	    
/////////////
	    public String getName(){
	    	
	    	return name;
	    }
	    
	    public void setName(String name){
	    	this.name = name;
	    }
	    
////////////
	    public int getScore(){
	    	return score;
	    }
	    
	    public void setScore(int type){
	    	this.score = type;
	    }
	    
	
}  

