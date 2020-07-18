package com.tyressory.slapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class UserActivity extends AppCompatActivity {

    ToggleButton userLocation;
    TextView statusDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        userLocation = (ToggleButton) findViewById(R.id.userLocation);
        statusDisplay = (TextView) findViewById(R.id.statusDisplay);

        userLocation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    // count user location
                    Toast.makeText(UserActivity.this, "The driver can see your location", Toast.LENGTH_SHORT).show();
                    // COnfigure ways to count number of locations on and store in database to display to driver
                    // Also set location services to confirm student location turned on

                }
                else{
                    //Location off
                    Toast.makeText(UserActivity.this, "Your location has been turned off", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }



    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.user_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {

            case R.id.userSettings:
                Toast.makeText(UserActivity.this, "Settings Feature not available yet", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.userTerms:
                Intent userTerm = new Intent(this, userTerms.class);
                startActivity(userTerm);
                return true;

            case R.id.rate:
                // Link to playstore
                Intent playstore = new Intent(Intent.ACTION_VIEW);
                playstore.setData(Uri.parse("market://details?id=com.android.app"));
                try{
                    startActivity(playstore);
                }
                catch(Exception e){
                    playstore.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.android.app"));
                }
                return true;

        }
        return true;

    }

}
