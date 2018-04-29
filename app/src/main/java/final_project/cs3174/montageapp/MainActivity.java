package final_project.cs3174.montageapp;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements SnapshotConfirmFragment.OnFragmentInteractionListener,
        MontageOptionsFragment.OnFragmentInteractionListener, MontageFragment.OnFragmentInteractionListener,
        SettingsFragment.OnFragmentInteractionListener, ConfirmDeleteFragment.OnFragmentInteractionListener
{
    Button takePicture;
    Button viewMontage;
    Button settings;
    File outputFile;
    Uri imgUri;
    ContextWrapper cw;
    File directory;
    GPSManager gpsManager;
    Snapshot currentSnapshot;
    FrameLayout mainFrame;
    SnapshotConfirmFragment scf;
    MontageOptionsFragment mof;
    MontageFragment mf;
    SnapshotDatabaseManager sdbman;
    SettingsFragment sf;
    ConfirmDeleteFragment cdf;
    SharedPreferences sharedPref;
    boolean recordLocation;

    public static final int REQUEST_IMAGE = 1;
    public static final String PATH = "/data/user/0/final_project.cs3174.montageapp/app_imageDir";
    public static final String RECORD_LOCATION = "record_location";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        takePicture = findViewById(R.id.takePicture);
        viewMontage = findViewById(R.id.viewMontage);
        settings = findViewById(R.id.settings);
        mainFrame = findViewById(R.id.mainFrame);
        cw = new ContextWrapper(getApplicationContext());
        directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        gpsManager = new GPSManager(this);
        currentSnapshot = new Snapshot();
        sdbman = new SnapshotDatabaseManager(this);
        sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        recordLocation = sharedPref.getBoolean(RECORD_LOCATION, true);
    }

    // Will get the date, location, weather, user's mood, and the photo
    // and store it into the currentSnapshot object.
    public void onTakePicture(View view)
    {
        currentSnapshot = new Snapshot();
        Location loc = gpsManager.getCurrentLocation();
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());
        try
        {
            addresses = geocoder.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);
            if (recordLocation)
            {
                currentSnapshot.setLocation(addresses.get(0).getAddressLine(0));
            }
            Log.d("streetAddress", currentSnapshot.getLocation());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        // get the weather using the provided location.
        WeatherAPI weatherAPI = new WeatherAPI(this);
        weatherAPI.getWeatherData(loc);

        try
        {
            File storageDir = this.getFilesDir();
            outputFile = File.createTempFile("" +
                    Calendar.getInstance().getTimeInMillis(), ".jpg", storageDir);
            imgUri = FileProvider.getUriForFile(this,
                    BuildConfig.APPLICATION_ID + ".provider", outputFile);
            Intent takePicIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            takePicIntent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
            if (takePicIntent.resolveActivity(getPackageManager()) != null)
            {
                startActivityForResult(takePicIntent, REQUEST_IMAGE);
            }
        }
        catch (IOException e)
        {
            Log.d("test", "Image Capture Error" + e.getMessage());
            e.printStackTrace();
        }
    }

    public void onViewMontage(View view)
    {
        mof = new MontageOptionsFragment();
        getSupportFragmentManager().beginTransaction().replace(mainFrame.getId(), mof).commit();
    }

    public void onClickSettings(View view)
    {
        sf = new SettingsFragment();
        getSupportFragmentManager().beginTransaction().replace(mainFrame.getId(), sf).addToBackStack(null).commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE && resultCode == RESULT_OK)
        {
            this.getContentResolver().notifyChange(imgUri, null);

            // name that will be used to get the picture from memory
            currentSnapshot.setPhotoName(new SimpleDateFormat("yyyyMMdd_HHmmss")
                    .format(Calendar.getInstance().getTime()));
            // We will need to store this name in a database in order to be able to access it later
            scf = new SnapshotConfirmFragment();
            try
            {
                scf.setSnapshotInfo(currentSnapshot, MediaStore.Images.Media.getBitmap(getContentResolver(), imgUri));
                getSupportFragmentManager().beginTransaction().replace(mainFrame.getId(), scf).addToBackStack(null).commit();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public void logWeather(String weather)
    {
        if (recordLocation)
        {
            this.currentSnapshot.setWeather(weather);
        }
    }

    @Override // from SnapshotConfirmFragment
    public void onSnapshotConfirmClick(int i, String m)
    {
        if (i == 0) // back was pressed
        {
            getSupportFragmentManager().beginTransaction().remove(scf).commit();
        }
        if (i == 1) // confirm was pressed
        {
            // Set up the AsyncTask to save image to internal storage and then remove the confirm fragment
            if (m != null)
            {
                this.currentSnapshot.setMood(m);
            }
            SaveImageAsync saveImage = new SaveImageAsync(this);
            saveImage.execute();
            sdbman.insertSnapshot(currentSnapshot);
            getSupportFragmentManager().beginTransaction().remove(scf).commit();
            getContentResolver().delete(imgUri, null, null);
        }
    }

    @Override // from MontageOptionsFragment
    public void onStartClicked(int time, int order, Uri musicUri)
    {
        // Will need to start the fragment that plays the montage and pass in the settings.
        mf = new MontageFragment();
        mf.setTimeAndOrder(time, order, sdbman.getAllRecords().size());
        if (musicUri != null)
        {
            mf.setMediaPlayer(getApplicationContext(), musicUri);
        }
        getSupportFragmentManager().beginTransaction().replace(mainFrame.getId(), mf).commit();
    }

    // from MontageFragment
    public void removeMontageFrag()
    {
        if (mf != null)
        {
            getSupportFragmentManager().beginTransaction().remove(mf).commit();
        }
    }

    @Override // from SettingsFragment
    public void setRecordLocation(boolean rec)
    {
        this.recordLocation = rec;
    }

    @Override
    public MainActivity setMainActivity()
    {
        return this;
    }

    @Override
    public void confirmDelete()
    {
        // Bring up another fragment that asks user to confirm decision to delete
        cdf = new ConfirmDeleteFragment();
        getSupportFragmentManager().beginTransaction().replace(mainFrame.getId(), cdf).addToBackStack(null).commit();
    }

    @Override
    public void onClickConfirm(boolean b)
    {
        if (b)
        {
            // delete all records in the database and clear the folder containing the images
            File file = new File(PATH);
            String[] files = file.list();
            for (int i = 0; i<files.length; i++)
            {
                File myFile = new File(file, files[i]);
                myFile.delete();
            }
            sdbman.deleteAll();
            Toast.makeText(getApplicationContext(), "All images and data removed", Toast.LENGTH_SHORT).show();
        }
        onBackPressed();
    }

    public void saveToInternalStorage(Bitmap bitmapImage, String name)
    {
        File filePath = new File(directory,name + ".jpg"); // <-- name of the file that we are saving
        FileOutputStream fos = null;
        try
        {
            fos = new FileOutputStream(filePath);
            bitmapImage.compress(Bitmap.CompressFormat.JPEG, 25, fos);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                fos.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        Log.d("directory path", directory.getAbsolutePath());
        // This should be the same as the static final String from above the onCreate method
    }

    public Bitmap loadImageFromStorage(String name)
    {
        try
        {
            File file = new File(MainActivity.PATH, name + ".jpg");
            return BitmapFactory.decodeStream(new FileInputStream(file));
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    // Start, Resume, Pause, Destroy, Permission Request methods *****************************
    @Override
    protected void onStart()
    {
        super.onStart();
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
        }, 0);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        sdbman.open();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                grantResults[1] == PackageManager.PERMISSION_GRANTED)
        {
            gpsManager.register();
        }
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        sdbman.close();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        gpsManager.unregister();
    }
    // ************************************************************************
}