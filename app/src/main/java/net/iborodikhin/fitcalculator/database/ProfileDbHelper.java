package net.iborodikhin.fitcalculator.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import net.iborodikhin.fitcalculator.model.Profile;

public class ProfileDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "profile.db";
    protected static final String SQL_CREATE = "CREATE TABLE " + ProfileContract.TABLE_NAME + " (" +
            ProfileContract._ID + " INTEGER PRIMATY KEY, " +
            ProfileContract.COLUMN_HEIGHT + " INTEGER, " +
            ProfileContract.COLUMN_AGE + " INTEGER, " +
            ProfileContract.COLUMN_WEIGHT + " INTEGER, " +
            ProfileContract.COLUMN_GENDER + " INTEGER, " +
            ProfileContract.COLUMN_ACTIVITY + " INTEGER"
            + ")";

    public ProfileDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean save(Profile profile) {
        ContentValues values = new ContentValues();
        values.put(ProfileContract.COLUMN_HEIGHT, profile.getHeight());
        values.put(ProfileContract.COLUMN_AGE, profile.getAge());
        values.put(ProfileContract.COLUMN_WEIGHT, profile.getWeight());
        values.put(ProfileContract.COLUMN_GENDER, profile.isMale() && !profile.isFemale() ? 0 : 1);
        values.put(ProfileContract.COLUMN_ACTIVITY, profile.getActivityId());

        SQLiteDatabase db = getWritableDatabase();
        db.delete(ProfileContract.TABLE_NAME, null, null);

        return db.insert(ProfileContract.TABLE_NAME, null, values) > -1;
    }

    public Profile read() throws Exception {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(
                ProfileContract.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        if (!cursor.moveToNext()) {
            throw new Exception("Profile is empty");
        }

        Profile profile = (new Profile())
            .setHeight(getIntValue(cursor, ProfileContract.COLUMN_HEIGHT))
            .setAge(getIntValue(cursor, ProfileContract.COLUMN_AGE))
            .setWeight(getIntValue(cursor, ProfileContract.COLUMN_WEIGHT))
            .setMale(getIntValue(cursor, ProfileContract.COLUMN_GENDER) == 0)
            .setFemale(getIntValue(cursor, ProfileContract.COLUMN_GENDER) == 1)
            .setActivityId(getIntValue(cursor, ProfileContract.COLUMN_ACTIVITY));

        cursor.close();

        return profile;
    }

    protected int getIntValue(Cursor cursor, String column) {
        return cursor.getInt(cursor.getColumnIndexOrThrow(column));
    }
}
