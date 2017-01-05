package masco.mis.software.mascoapproval;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import masco.mis.software.mascoapproval.service.CountTService;
import masco.mis.software.mascoapproval.service.LocationTService;

public class IntroActivity extends Activity {
    final private int per = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        try
        {

        }
        catch (Exception e)
        {
            Toast.makeText(this, "Error "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        try
        {
            startService(new Intent(this, CountTService.class));
        }
        catch (Exception e)
        {
            Toast.makeText(this, "failed to start the service "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        if (Build.VERSION.SDK_INT < 23) {
            Intent intent = new Intent(this, LocationTService.class);
            if (!isServiceRunning(LocationTService.class)) {
                startService(intent);
            }
        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, per);
            } else {
                if (!isServiceRunning(LocationTService.class))
                {
                    Intent intent = new Intent(this, LocationTService.class);
                    startService(intent);
                }

            }
        }
        Intent intentLogin = new Intent(IntroActivity.this,LoginActivity.class);
        startActivity(intentLogin);
        finish();


    }
    public  synchronized boolean isServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case per: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    Intent intent = new Intent(this, LocationTService.class);
                    startService(intent);

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}
