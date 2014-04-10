package com.aaravmedia.xmlparsing;

import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class MainActivity extends ListActivity {

	//url to make request
	private static String URL = "http://api.androidhive.info/pizza/?format=xml";
	
	// XML node keys
    static final String KEY_ITEM = "item"; // parent node
    static final String KEY_ID = "id";
    static final String KEY_NAME = "name";
    static final String KEY_COST = "cost";
    static final String KEY_DESCRIPTION = "description";
	Document doc2;
	
	// Hashmap for ListView
	ArrayList<HashMap<String, String>> menuItems;
	ListAdapter adapter;
	ProgressDialog pd;
	NodeList nl;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
 
        menuItems = new ArrayList<HashMap<String, String>>();
 
        XMLParser parser = new XMLParser(){
        	@Override
        	protected void onPreExecute() {
        		super.onPreExecute();
        		pd = ProgressDialog.show(MainActivity.this, "Contacts", "Loading");
        	}
        	Element e;
        	@Override
        	protected void onPostExecute(Document result) {
        		super.onPostExecute(result);
        		pd.dismiss();
        		doc2 = result; // getting DOM element
 
        nl = doc2.getElementsByTagName(KEY_ITEM);
        // looping through all item nodes <item>
        for (int i = 0; i < nl.getLength(); i++) {
            // creating new HashMap
            HashMap<String, String> map = new HashMap<String, String>();
            e = (Element) nl.item(i);
            // adding each child node to HashMap key => value
            map.put(KEY_ID, getValue(e, KEY_ID));
            map.put(KEY_NAME, getValue(e, KEY_NAME));
            map.put(KEY_COST, "Rs." + getValue(e, KEY_COST));
            map.put(KEY_DESCRIPTION, getValue(e, KEY_DESCRIPTION));
 
            // adding HashList to ArrayList
            menuItems.add(map);
        }
 
        // Adding menuItems to ListView
        ListAdapter adapter = new SimpleAdapter(MainActivity.this, menuItems,
                R.layout.list_item,
                new String[] { KEY_NAME, KEY_DESCRIPTION, KEY_COST }, new int[] {
                        R.id.name, R.id.description, R.id.cost });
 
        setListAdapter(adapter);
		
        		}
        	};
        
        	parser.execute(URL);
	        // selecting single ListView item
	        ListView lv = getListView();
	        
	        // Launching new screen on Selecting Single ListItem
	        lv.setOnItemClickListener(new OnItemClickListener() {
	 
	            @Override
	            public void onItemClick(AdapterView<?> parent, View view,
	                    int position, long id) {
	                // getting values from selected ListItem
	                String name = ((TextView) view.findViewById(R.id.name)).getText().toString();
	                String cost = ((TextView) view.findViewById(R.id.cost)).getText().toString();
	                String description = ((TextView) view.findViewById(R.id.description)).getText().toString();
	                 
	                // Starting new intent
	                Intent in = new Intent(getApplicationContext(), SingleMenuItemActivity.class);
	                in.putExtra(KEY_NAME, name);
	                in.putExtra(KEY_COST, cost);
	                in.putExtra(KEY_DESCRIPTION, description);
	                startActivity(in);
	            }
	        });
	    }

		
}
