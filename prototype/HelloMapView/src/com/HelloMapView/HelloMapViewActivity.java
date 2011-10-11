package com.HelloMapView;

import android.app.Activity;
import android.os.Bundle;
import com.google.android.maps.*;
import android.widget.*;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

public class HelloMapViewActivity extends MapActivity {
    /** Called when the activity is first created. */
	LinearLayout linearLayout;
	MapView mapView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mapView = (MapView) findViewById(R.id.mapview);
        mapView.setBuiltInZoomControls(true);
       
        Button next = (Button) findViewById(R.id.button3);
        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), Profile.class);
                startActivityForResult(myIntent, 0);
                startActivity(myIntent);
            }

        });
            }
    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }
  
    
    
 
}