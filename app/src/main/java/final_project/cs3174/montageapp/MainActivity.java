package final_project.cs3174.montageapp;

import android.content.ContentResolver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity
{
    Button takePicture;
    Button viewMontage;
    Button settings;
    private Uri imgUri;
    public static final int REQUEST_IMAGE = 1;
    public static final String path = "/data/user/0/final_project.cs3174.montageapp/app_imageDir";
    ContextWrapper cw;
    File directory;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        takePicture = findViewById(R.id.takePicture);
        viewMontage = findViewById(R.id.viewMontage);
        settings = findViewById(R.id.settings);
        cw = new ContextWrapper(getApplicationContext());
        directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
    }

    public void onTakePicture(View view)
    {
        try
        {
            File storageDir = this.getFilesDir();
            File outputFile = File.createTempFile("" +
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

    }


    public void onClickSettings(View view)
    {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE && resultCode == RESULT_OK)
        {
            this.getContentResolver().notifyChange(imgUri, null);
            ContentResolver cr = this.getContentResolver();
            Log.d("test", "result ok");
            try
            {
                    // name that will be used to get the picture from memory
                    String name = Calendar.getInstance().getTimeInMillis() + "";
                    saveToInternalStorage(MediaStore.Images.Media.getBitmap(cr, imgUri),
                            name);
            }
            catch (IOException e)
            {
                Log.d("test", "activity result error");
                e.printStackTrace();
            }
        }
    }

    private void saveToInternalStorage(Bitmap bitmapImage, String name)
    {
        File filePath = new File(directory,name + ".jpg"); // <-- name of the file that we are saving
        FileOutputStream fos = null;
        try
        {
            fos = new FileOutputStream(filePath);
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
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

    private Bitmap loadImageFromStorage(String path, String name)
    {
        try
        {
            File file = new File(path, name + ".jpg");
            return BitmapFactory.decodeStream(new FileInputStream(file));
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
