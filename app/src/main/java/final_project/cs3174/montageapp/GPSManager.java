package final_project.cs3174.montageapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

/**
 * Created by Shawn on 4/19/2018.
 */

public class GPSManager implements LocationListener
{
    MainActivity mainActivity;
    LocationManager locationManager;
    Location currentLocation;

    public GPSManager(MainActivity ma)
    {
        this.mainActivity = ma;
        locationManager = (LocationManager) mainActivity.getSystemService(Context.LOCATION_SERVICE);
        currentLocation = null;
    }

    public void register()
    {
        if (ActivityCompat.checkSelfPermission(mainActivity, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(mainActivity, Manifest.permission.ACCESS_COARSE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED)
        {
            locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER, 1000, 5, this);
            currentLocation = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);
        }
    }

    public void unregister()
    {
        if (ActivityCompat.checkSelfPermission(mainActivity, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(mainActivity, Manifest.permission.ACCESS_COARSE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED)
        {
            locationManager.removeUpdates(this);
        }
    }

    public Location getCurrentLocation()
    {
        return this.currentLocation;
    }

    @Override
    public void onLocationChanged(Location location)
    {
        this.currentLocation = location;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras)
    {

    }

    @Override
    public void onProviderEnabled(String provider)
    {

    }

    @Override
    public void onProviderDisabled(String provider)
    {

    }
}