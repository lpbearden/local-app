package edu.apsu.csci.local_app.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import edu.apsu.csci.local_app.Models.Locality;
import edu.apsu.csci.local_app.Models.Review;
import edu.apsu.csci.local_app.Models.User;

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
    private static final String TABLE_LOC_TYPES = "loc_types";

    // Location Table Columns
    private static final String KEY_LOCATION_ID = "id";
    private static final String KEY_LOCATION_NAME = "name";
    private static final String KEY_LOCATION_DESC = "desc";
    private static final String KEY_LOCATION_STREET = "street";
    private static final String KEY_LOCATION_CITY = "city";
    private static final String KEY_LOCATION_STATE = "state";
    private static final String KEY_LOCATION_ZIP = "zip";

    // User Table Columns
    private static final String KEY_USER_ID = "id";
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
    private static final String KEY_REVIEW_FK_USER_ID = "user_id";


    // Location_Images Table Columns
    private static final String KEY_LOC_IMGS_LOC_ID = "loc_id";
    private static final String KEY_LOC_IMGS_IMG_PATH = "img_path";

    // User_List Table Columns
    private static final String KEY_USER_LIST_USERNAME = "username";
    private static final String KEY_USER_LIST_LOC_ID = "loc_id";

    // Location_Types Table Columns
    private static final String KEY_LOC_TYPE_LOC_ID = "loc_id";
    private static final String KEY_LOC_TYPE_TYPE = "type";


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
                KEY_LOCATION_ID         + " INTEGER PRIMARY KEY," + // Define a primary key
                KEY_LOCATION_NAME       + " TEXT," +
                KEY_LOCATION_DESC       + " TEXT," +
                KEY_LOCATION_STREET     + " TEXT," +
                KEY_LOCATION_CITY       + " TEXT," +
                KEY_LOCATION_STATE      + " TEXT," +
                KEY_LOCATION_ZIP        + " INTEGER" +
                ")";

        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS +
                "(" +
                KEY_USER_ID               + " INTEGER PRIMARY KEY," +
                KEY_USER_USERNAME               + " TEXT," +
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
                KEY_REVIEW_FK_USER_ID   + " INTEGER REFERENCES " + TABLE_USERS +
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

        String CREATE_LOC_TYPE_TABLE = "CREATE TABLE " + TABLE_LOC_TYPES +
                "(" +
                KEY_LOC_TYPE_LOC_ID     + " INTEGER REFERENCES "    + TABLE_LOCATIONS + "," +
                KEY_LOC_TYPE_TYPE       + " TEXT,"   +
                "primary key("+KEY_LOC_TYPE_LOC_ID+","+KEY_LOC_TYPE_TYPE+")" +
                ")";

        db.execSQL(CREATE_LOCATIONS_TABLE);
        db.execSQL(CREATE_USERS_TABLE);
        db.execSQL(CREATE_REVIEWS_TABLE);
        db.execSQL(CREATE_LOC_IMGS_TABLE);
        db.execSQL(CREATE_USER_LIST_TABLE);
        db.execSQL(CREATE_LOC_TYPE_TABLE);
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
            db.execSQL("DROP TABLE IF EXITS "  + TABLE_LOC_TYPES);
            onCreate(db);
        }
    }


    // Insert a post into the database
    public void addReview(Review review) {
        // Create and/or open the database for writing
        SQLiteDatabase db = getWritableDatabase();

        // It's a good idea to wrap our insert in a transaction. This helps with performance and ensures
        // consistency of the database.
        db.beginTransaction();
        try {
            // The user might already exist in the database (i.e. the same user created multiple posts).
            long userId = addOrUpdateUser(review.user);
            long locationId = addOrUpdateLocality(review.locality);

            ContentValues values = new ContentValues();
            values.put(KEY_REVIEW_DATE, review.date);
            values.put(KEY_REVIEW_RATING, review.rating);
            values.put(KEY_REVIEW_TEXT, review.review_text);
            values.put(KEY_REVIEW_FK_LOC_ID, locationId);
            values.put(KEY_REVIEW_FK_USER_ID, userId);

            // Notice how we haven't specified the primary key. SQLite auto increments the primary key column.
            db.insertOrThrow(TABLE_REVIEWS, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d("DATABASE PROBLEM", "Error while trying to add a review to database");
        } finally {
            db.endTransaction();
        }
    }

    // Insert or update a user in the database
    // Since SQLite doesn't support "upsert" we need to fall back on an attempt to UPDATE (in case the
    // user already exists) optionally followed by an INSERT (in case the user does not already exist).
    // Unfortunately, there is a bug with the insertOnConflict method
    // (https://code.google.com/p/android/issues/detail?id=13045) so we need to fall back to the more
    // verbose option of querying for the user's primary key if we did an update.
    public long addOrUpdateUser(User user) {
        // The database connection is cached so it's not expensive to call getWriteableDatabase() multiple times.
        SQLiteDatabase db = getWritableDatabase();
        long userId = -1;

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_USER_USERNAME, user.username);
            values.put(KEY_USER_FNAME, user.first_name);
            values.put(KEY_USER_LNAME, user.last_name);
            values.put(KEY_USER_PASSWORD, user.password);
            values.put(KEY_USER_PROFILE_PICTURE_URL, user.profilePic_path);

                //insert user list into user list table
                for (int i = 0; i < user.user_list.size(); i++) {
                    values.put(KEY_USER_LIST_LOC_ID, user.user_list.get(i).getId());
                    values.put(KEY_USER_LIST_USERNAME, user.id);
                }


            // First try to update the user in case the user already exists in the database
            // This assumes userNames are unique
            int rows = db.update(TABLE_USERS, values, KEY_USER_USERNAME + "= ?", new String[]{user.username});

            // Check if update succeeded
            if (rows == 1) {
                // Get the primary key of the user we just updated
                String usersSelectQuery = String.format("SELECT %s FROM %s WHERE %s = ?",
                        KEY_USER_ID, TABLE_USERS, KEY_USER_USERNAME);
                Cursor cursor = db.rawQuery(usersSelectQuery, new String[]{String.valueOf(user.username)});
                try {
                    if (cursor.moveToFirst()) {
                        userId = cursor.getInt(0);
                        db.setTransactionSuccessful();
                    }
                } finally {
                    if (cursor != null && !cursor.isClosed()) {
                        cursor.close();
                    }
                }
            } else {
                // user with this userName did not already exist, so insert new user
                userId = db.insertOrThrow(TABLE_USERS, null, values);
                db.setTransactionSuccessful();
            }
        } catch (Exception e) {
            Log.d("DATABASE PROBLEM", "Error while trying to add or update user");
        } finally {
            db.endTransaction();
        }
        return userId;
    }


    public long addOrUpdateLocality(Locality locality) {
        // The database connection is cached so it's not expensive to call getWriteableDatabase() multiple times.
        SQLiteDatabase db = getWritableDatabase();
        long userId = -1;

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_LOCATION_NAME, locality.name);
            values.put(KEY_LOCATION_DESC, locality.description);
            values.put(KEY_LOCATION_STREET, locality.street);
            values.put(KEY_LOCATION_CITY, locality.city);
            values.put(KEY_LOCATION_STATE, locality.state);
            values.put(KEY_LOCATION_ZIP, locality.zip);

            //insert images into img paths table
            for (int i = 0; i < locality.img_paths.size(); i++) {
                values.put(KEY_LOC_IMGS_IMG_PATH, locality.img_paths.get(i));
                values.put(KEY_LOC_IMGS_LOC_ID, locality.id);
            }

            //insert types into type table
            for (int i = 0; i < locality.types.size(); i++) {
                values.put(KEY_LOC_TYPE_TYPE, locality.types.get(i));
                values.put(KEY_LOC_TYPE_LOC_ID, locality.id);
            }



            // First try to update the user in case the user already exists in the database
            // This assumes userNames are unique
            int rows = db.update(TABLE_LOCATIONS, values, KEY_LOCATION_NAME + "= ?", new String[]{locality.name});

            // Check if update succeeded
            if (rows == 1) {
                // Get the primary key of the user we just updated
                String usersSelectQuery = String.format("SELECT %s FROM %s WHERE %s = ?",
                        KEY_LOCATION_ID, TABLE_LOCATIONS, KEY_LOCATION_NAME);
                Cursor cursor = db.rawQuery(usersSelectQuery, new String[]{String.valueOf(locality.name)});
                try {
                    if (cursor.moveToFirst()) {
                        userId = cursor.getInt(0);
                        db.setTransactionSuccessful();
                    }
                } finally {
                    if (cursor != null && !cursor.isClosed()) {
                        cursor.close();
                    }
                }
            } else {
                // user with this userName did not already exist, so insert new user
                userId = db.insertOrThrow(TABLE_USERS, null, values);
                db.setTransactionSuccessful();
            }
        } catch (Exception e) {
            Log.d("DATABASE PROBLEM", "Error while trying to add or update locality");
        } finally {
            db.endTransaction();
        }
        return userId;
    }

    public List<Locality> getAllLocalities() {
        List<Locality> localities = new ArrayList<>();

        // SELECT * FROM LOCATIONS
        String LOCALITIES_SELECT_QUERY =
                String.format("SELECT * FROM %s",
                        TABLE_LOCATIONS
                        );

        // "getReadableDatabase()" and "getWriteableDatabase()" return the same object (except under low
        // disk space scenarios)
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(LOCALITIES_SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    Locality locality = new Locality();
                    locality.name = cursor.getString(cursor.getColumnIndex(KEY_LOCATION_NAME));
                    locality.description = cursor.getString(cursor.getColumnIndex(KEY_LOCATION_DESC));
                    locality.street = cursor.getString(cursor.getColumnIndex(KEY_LOCATION_STREET));
                    locality.city = cursor.getString(cursor.getColumnIndex(KEY_LOCATION_CITY));
                    locality.state = cursor.getString(cursor.getColumnIndex(KEY_LOCATION_STATE));
                    locality.zip = cursor.getString(cursor.getColumnIndex(KEY_LOCATION_ZIP));
                    locality.img_paths = getLocalityImages(cursor.getInt(cursor.getColumnIndex(KEY_LOCATION_ID)));
                    locality.types = getLocalityTypes(cursor.getInt(cursor.getColumnIndex(KEY_LOCATION_ID)));
                    localities.add(locality);
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d("DATABASE QUERY", "Error while trying to get LOCATIONS from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return localities;
    }

    public ArrayList<String> getLocalityTypes(int id) {
        ArrayList<String> types = new ArrayList<>();

        // SELECT TYPE FROM TYPE_TABLE
        // WHERE TYPE_TABLE.LOC_ID = ID
        String TYPES_SELECT_QUERY =
                String.format("SELECT %s FROM %s WHERE %s.%s = '$s'",
                        KEY_LOC_TYPE_TYPE,
                        TABLE_LOC_TYPES,
                        TABLE_LOC_TYPES, KEY_LOC_TYPE_LOC_ID, id);

        // "getReadableDatabase()" and "getWriteableDatabase()" return the same object (except under low
        // disk space scenarios)
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(TYPES_SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    types.add(cursor.getString(cursor.getColumnIndex(KEY_LOC_TYPE_TYPE)));
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d("DATABASE QUERY", "Error while trying to get TYPES from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return types;
    }

    public ArrayList<String> getLocalityImages(int id) {
        ArrayList<String> img_paths = new ArrayList<>();

        // SELECT IMG_PATH FROM LOC_IMGS
        // WHERE LOC_IMGS.LOC_ID = ID
        String TYPES_SELECT_QUERY =
                String.format("SELECT %s FROM %s WHERE %s.%s = $s",
                        KEY_LOC_IMGS_IMG_PATH,
                        TABLE_LOC_IMGS,
                        TABLE_LOC_IMGS, KEY_LOC_TYPE_LOC_ID,
                        id
                );

        // "getReadableDatabase()" and "getWriteableDatabase()" return the same object (except under low
        // disk space scenarios)
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(TYPES_SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    img_paths.add(cursor.getString(cursor.getColumnIndex(KEY_LOC_IMGS_IMG_PATH)));
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d("DATABASE QUERY", "Error while trying to get TYPES from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return img_paths;
    }
}
