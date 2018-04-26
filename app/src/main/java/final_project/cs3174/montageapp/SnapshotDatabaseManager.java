package final_project.cs3174.montageapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by Shawn on 4/26/2018.
 */

public class SnapshotDatabaseManager
{
    private SnapshotDBOpenHelper snapshotDBOpenHelper;
    private SQLiteDatabase sqLiteDatabase;

    public SnapshotDatabaseManager(Context context)
    {
        snapshotDBOpenHelper = new SnapshotDBOpenHelper(context);
    }

    public void open()
    {
        sqLiteDatabase = snapshotDBOpenHelper.getWritableDatabase();
    }

    public void close()
    {
        sqLiteDatabase.close();
    }

    public void insertSnapshot(Snapshot snapshot)
    {
        ContentValues cv = new ContentValues();
        cv.put(SnapshotDBOpenHelper.COLUMN_PHOTO_NAME, snapshot.getPhotoName());
        cv.put(SnapshotDBOpenHelper.COLUMN_LOCATION, snapshot.getLocation());
        cv.put(SnapshotDBOpenHelper.COLUMN_MOOD, snapshot.getMood());
        cv.put(SnapshotDBOpenHelper.COLUMN_WEATHER, snapshot.getWeather());
        sqLiteDatabase.insert(SnapshotDBOpenHelper.TABLE_NAME, null, cv);
    }

    public void deleteAll()
    {
        if (sqLiteDatabase.isOpen())
        {
            sqLiteDatabase.execSQL("DELETE FROM " + SnapshotDBOpenHelper.TABLE_NAME);
        }
    }

    public ArrayList<Snapshot> getAllRecords()
    {
        Cursor cursor = sqLiteDatabase.query(SnapshotDBOpenHelper.TABLE_NAME,
                new String[] {SnapshotDBOpenHelper.COLUMN_ID, SnapshotDBOpenHelper.COLUMN_PHOTO_NAME,
                        SnapshotDBOpenHelper.COLUMN_LOCATION, SnapshotDBOpenHelper.COLUMN_MOOD,
                        SnapshotDBOpenHelper.COLUMN_WEATHER},
                null, null, null, null, null);
        cursor.moveToFirst();
        ArrayList<Snapshot> result = new ArrayList<>();
        while(!cursor.isAfterLast())
        {
            Snapshot snapshot = new Snapshot();
            snapshot.setPhotoName(cursor.getString(1));
            snapshot.setLocation(cursor.getString(2));
            snapshot.setMood(cursor.getString(3));
            snapshot.setWeather(cursor.getString(4));
            result.add(snapshot);
            cursor.moveToNext();
        }
        return result;
    }
}