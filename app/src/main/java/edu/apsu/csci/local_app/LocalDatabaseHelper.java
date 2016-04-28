package edu.apsu.csci.local_app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lpbearden on 4/27/16.
 */
public class LocalDatabaseHelper extends SQLiteOpenHelper {
    private static LocalDatabaseHelper sInstance;

    // Database Info
    private static final String DATABASE_NAME = "localDatabase";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    private static final String TABLE_LOCATIONS = "locations";
    private static final String TABLE_USERS = "users";
    private static final String TABLE_REVIEWS = "reviews";
    private static final String TABLE_LOC_IMGS = "loc_imgs";
    private static final String TABLE_USER_LIST = "user_list";

    // Location Table Columns
    private static final String KEY_LOCATION_ID = "id";
    private static final String KEY_LOCATION_NAME = "name";
    private static final String KEY_LOCATION_DESC = "desc";
    private static final String KEY_LOCATION_TYPE = "type";
    private static final String KEY_LOCATION_STREET = "street";
    private static final String KEY_LOCATION_CITY = "city";
    private static final String KEY_LOCATION_STATE = "state";
    private static final String KEY_LOCATION_ZIP = "zip";

    // User Table Columns
    private static final String KEY_USER_USERNAME = "username";
    private static final String KEY_USER_FNAME = "first_name";
    private static final String KEY_USER_LNAME = "last_name";
    private static final String KEY_USER_PASSWORD = "password";
    private static final String KEY_USER_PROFILE_PICTURE_URL = "profile_picture_url";

    // Review Table Columns
    private static final String KEY_REVIEW_ID = "id";
    private static final String KEY_REVIEW_DATE = "date";
    private static final String KEY_REVIEW_RATING = "rating";
    private static final String KEY_REVIEW_TEXT = "review_text";
    private static final String KEY_REVIEW_FK_LOC_ID = "loc_id";
    private static final String KEY_REVIEW_FK_USERNAME = "username";


    // Location_Images Table Columns
    private static final String KEY_LOC_IMGS_LOC_ID = "loc_id";
    private static final String KEY_LOC_IMGS_IMG_PATH = "img_path";

    // User_List Table Columns
    private static final String KEY_USER_LIST_USERNAME = "username";
    private static final String KEY_USER_LIST_LOC_ID = "loc_id";


    public static synchronized LocalDatabaseHelper getsInstance(Context context) {
        if (sInstance == null) {
            sInstance = new LocalDatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    public LocalDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Called when the database connection is being configured.
    // Configure database settings for things like foreign key support, write-ahead logging, etc.
    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    // Called when the database is created for the FIRST time.
    // If a database already exists on disk with the same DATABASE_NAME, this method will NOT be called.
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOCATIONS_TABLE = "CREATE TABLE " + TABLE_LOCATIONS +
                "(" +
                KEY_LOCATION_ID     + " INTEGER PRIMARY KEY," + // Define a primary key
                KEY_LOCATION_NAME   + " TEXT," +
                KEY_LOCATION_DESC   + " TEXT," +
                KEY_LOCATION_TYPE   + " TEXT," +
                KEY_LOCATION_STREET   + " TEXT," +
                KEY_LOCATION_CITY   + " TEXT," +
                KEY_LOCATION_STATE  + " TEXT," +
                KEY_LOCATION_ZIP    + " INTEGER" +
                ")";

        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS +
                "(" +
                KEY_USER_USERNAME               + " TEXT PRIMARY KEY," +
                KEY_USER_FNAME                  + " TEXT," +
                KEY_USER_LNAME                  + " TEXT," +
                KEY_USER_PASSWORD               + " TEXT," +
                KEY_USER_PROFILE_PICTURE_URL    + " TEXT"  +
                ")";

        String CREATE_REVIEWS_TABLE = "CREATE TABLE " + TABLE_REVIEWS +
                "(" +
                KEY_REVIEW_ID           + " INTEGER PRIMARY KEY," +
                KEY_REVIEW_DATE         + " TEXT," +
                KEY_REVIEW_RATING       + " INTEGER," +
                KEY_REVIEW_TEXT         + " TEXT," +
                KEY_REVIEW_FK_LOC_ID    + " INTEGER REFERENCES " + TABLE_LOCATIONS + "," +
                KEY_REVIEW_FK_USERNAME  + " INTEGER REFERENCES " + TABLE_USERS +
                ")";

        String CREATE_LOC_IMGS_TABLE = "CREATE TABLE " + TABLE_LOC_IMGS +
                "(" +
                KEY_LOC_IMGS_LOC_ID     + " INTEGER REFERENCES " + TABLE_LOCATIONS + "," +
                KEY_LOC_IMGS_IMG_PATH   + " TEXT," +
                "primary key("+KEY_LOC_IMGS_LOC_ID+","+KEY_LOC_IMGS_IMG_PATH+")" +
                ")";

        String CREATE_USER_LIST_TABLE = "CREATE TABLE " + TABLE_USER_LIST +
                "(" +
                KEY_USER_LIST_USERNAME  + " TEXT REFERENCES "       + TABLE_USERS + "," +
                KEY_USER_LIST_LOC_ID    + " INTEGER REFERENCES "    + TABLE_LOCATIONS + "," +
                "primary key("+KEY_USER_LIST_USERNAME+","+KEY_USER_LIST_LOC_ID+")" +
                ")";

        db.execSQL(CREATE_LOCATIONS_TABLE);
        db.execSQL(CREATE_USERS_TABLE);
        db.execSQL(CREATE_REVIEWS_TABLE);
        db.execSQL(CREATE_LOC_IMGS_TABLE);
        db.execSQL(CREATE_USER_LIST_TABLE);
    }

    // Called when the database needs to be upgraded.
    // This method will only be called if a database already exists on disk with the same DATABASE_NAME,
    // but the DATABASE_VERSION is different than the version of the database that exists on disk.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            // Simplest implementation is to drop all old tables and recreate them
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCATIONS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_REVIEWS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOC_IMGS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_LIST);
            onCreate(db);
        }
    }
}
