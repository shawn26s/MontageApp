package final_project.cs3174.montageapp;

import android.content.ContentResolver;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.widget.Toast;

import java.io.IOException;

/**
 * Created by Shawn on 4/22/2018.
 */

public class SaveImageAsync extends AsyncTask<Integer, Integer, Void>
{
    MainActivity mainActivity;

    public SaveImageAsync(MainActivity ma)
    {
        this.mainActivity = ma;
    }

    @Override
    protected Void doInBackground(Integer... integers)
    {
        try
        {
            mainActivity.saveToInternalStorage(MediaStore.Images.Media.getBitmap(mainActivity.getContentResolver(), mainActivity.imgUri),
                    mainActivity.currentSnapshot.getPhotoName());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPreExecute()
    {
        Toast.makeText(mainActivity.getApplicationContext(), "Saving your image...", Toast.LENGTH_SHORT).show();
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid)
    {
        Toast.makeText(mainActivity.getApplicationContext(), "Image Saved!", Toast.LENGTH_SHORT).show();
        super.onPostExecute(aVoid);
    }
}