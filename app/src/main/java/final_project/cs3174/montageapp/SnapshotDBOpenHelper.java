package final_project.cs3174.montageapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Shawn on 4/26/2018.
 */

public class SnapshotDBOpenHelper extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME = "SnapshotDatabase";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "snapshots";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_PHOTO_NAME = "photo_name";
    public static final String COLUMN_LOCATION = "location";
    public static final String COLUMN_MOOD = "mood";
    public static final String COLUMN_WEATHER = "weather";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_PHOTO_NAME + " TEXT," +
                    COLUMN_LOCATION + " TEXT," +
                    COLUMN_MOOD + " TEXT," +
                    COLUMN_WEATHER + " TEXT)";

    public SnapshotDBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, name, factory, version);
    }

    public SnapshotDBOpenHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        // delete all records from our database and recreate the table.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}