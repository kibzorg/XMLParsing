package com.aaravmedia.xmlparsing;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SingleMenuItemActivity  extends Activity {
	
	// JSON node keys
	private static final String TAG_NAME = "name";
	private static final String TAG_COST = "cost";
	private static final String TAG_DESCRIPTION = "description";
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_list_item);
        
        // getting intent data
        Intent in = getIntent();
        
        // Get JSON values from previous intent
        String name = in.getStringExtra(TAG_NAME);
        String cost = in.getStringExtra(TAG_COST);
        String description = in.getStringExtra(TAG_DESCRIPTION);
        
        // Displaying all values on the screen
        TextView lblName = (TextView) findViewById(R.id.name_label);
        TextView lblCost = (TextView) findViewById(R.id.cost_label);
        TextView lblDesc = (TextView) findViewById(R.id.description_label);
        
        lblName.setText(name);
        lblCost.setText(cost);
        lblDesc.setText(description);
    }
}
