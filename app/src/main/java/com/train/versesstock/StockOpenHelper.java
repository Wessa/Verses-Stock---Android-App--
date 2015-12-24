package com.train.versesstock;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class StockOpenHelper extends SQLiteOpenHelper {

    private final Context myContext;

    //Database Name & Version
    private static final String DATABASE_PATH = "/data/data/com.train.versesstock/databases/";
    private static final String DATABASE_NAME = "versesStock.db";
    private static final int DATABAS_VERSION = 1;

    // Table Names
    public static final String TABLE_TYPES = "types";
    public static final String TABLE_CHAPTERS = "chapters";
    public static final String TABLE_VERSES = "verses";

    // Common column names ( TYPES Table Columns and CHAPTERS Table Columns )
    public static final String KEY_ID = "_id";
    public static final String KEY_CREATED_AT = "created_at";
    public static final String TYPE = "type";

    // VERSES Table - column names
    public static final String VERSE = "verse";

    //CHAPTERS Table - column names
    public static final String CHAPTER = "chapter";

    //SQL to create table TYPES
    private static final String TYPES_TABLE_CREATE =
            "CREATE TABLE " + TABLE_TYPES + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    TYPE + " TEXT, " +
                    KEY_CREATED_AT + " TEXT default CURRENT_TIMESTAMP" +
                    ")";

    //SQL to create table CHAPTERS
    private static final String CHAPTERS_TABLE_CREATE =
            "CREATE TABLE " + TABLE_CHAPTERS + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    TYPE + " TEXT, " +
                    CHAPTER + " TEXT, " +
                    KEY_CREATED_AT + " TEXT default CURRENT_TIMESTAMP" +
                    ")";

    //SQL to create table VERSES
    private static final String VERSES_TABLE_CREATE =
            "CREATE TABLE " + TABLE_VERSES + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    CHAPTER + " TEXT, " +
                    VERSE + " TEXT, " +
                    KEY_CREATED_AT + " TEXT default CURRENT_TIMESTAMP" +
                    ")";

    public static final String[] TYPE_COLUMNS = { KEY_ID, TYPE, KEY_CREATED_AT };
    public static final String[] CHAPTER_COLUMNS = { KEY_ID, TYPE, CHAPTER, KEY_CREATED_AT };
    public static final String[] VERSE_COLUMNS = { KEY_ID, CHAPTER, VERSE, KEY_CREATED_AT };

    public StockOpenHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABAS_VERSION);
        myContext = context;
    }

    public boolean checkDataBase(){

            String myPath = DATABASE_PATH + DATABASE_NAME;
            SQLiteDatabase checkDB = null;

            try {
                checkDB = SQLiteDatabase.openDatabase(myPath, null,
                        SQLiteDatabase.OPEN_READONLY);
                checkDB.close();
            } catch (SQLiteException e) {
                // database doesn't exist yet.
            }
            return checkDB != null;
    }

    public void copyDataBase(){

        try {
            //Open your local db as the input stream
            InputStream myInput = myContext.getAssets().open(DATABASE_NAME);

            // Path to the just created empty db
            String outFileName = DATABASE_PATH + DATABASE_NAME;

            //Open the empty db as the output stream
            OutputStream myOutput = new FileOutputStream(outFileName);

            //transfer bytes from the inputfile to the outputfile
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }

            //Close the streams
            myOutput.flush();
            myOutput.close();
            myInput.close();

        } catch (IOException e) {
            e.printStackTrace();
            }
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        /*db.execSQL(TYPES_TABLE_CREATE);
        db.execSQL(CHAPTERS_TABLE_CREATE);
        db.execSQL(VERSES_TABLE_CREATE);*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        /*db.execSQL("DROP TABLE IF EXISTS " + TABLE_TYPES );
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHAPTERS );
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VERSES );
        onCreate(db);*/
    }
}
