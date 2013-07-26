package com.klusman.keepup;

import java.util.List;

import android.content.Context;

import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.klusman.keepup.database.ScoreObject;

public class ListViewAdapter extends ArrayAdapter<ScoreObject> {
	Context _context;
	private List<ScoreObject> _scoreList ;
	//private Array<ScoreObject> scoreArray;
	
	public ListViewAdapter(Context context, List<ScoreObject> scores) {
		super(context, R.layout.leaderboard_cell, scores);
		this._context = context;
		this._scoreList = scores;
		for( int i = 0; i <= _scoreList.size(); i++){
			
		}
		
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        LayoutInflater inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView==null)
            vi = inflater.inflate(R.layout.leaderboard_cell, null);
 
        TextView name = (TextView)vi.findViewById(R.id.textName); 
        TextView score = (TextView)vi.findViewById(R.id.textScore); 
        
        
        ScoreObject so = _scoreList.get(position);
 
        // Setting all values in listview
        name.setText(so.getName());
        score.setText(String.valueOf(so.getScore()));
        
        return vi;
    }
	
	
	
}
