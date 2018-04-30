package final_project.cs3174.montageapp;

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
    protected void onPostExecute(Void aVoid)
    {
        Toast.makeText(mainActivity.getApplicationContext(), "Image Saved!", Toast.LENGTH_SHORT).show();
        mainActivity.setLastPhotoText();
        super.onPostExecute(aVoid);
    }
}