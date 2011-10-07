package eltoraz.pug.android;

import android.app.Activity;
import android.os.Bundle;
import com.google.android.maps.*;
import android.widget.*;

public class PugAndroidActivity extends MapActivity {
    /** Called when the activity is first created. */
	LinearLayout linearLayout;
	MapView mapView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mapView = (MapView) findViewById(R.id.mapview);
        mapView.setBuiltInZoomControls(true);
            }
    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }
  
    
    
 
}