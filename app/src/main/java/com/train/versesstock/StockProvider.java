package com.train.versesstock;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import java.io.IOException;

public class StockProvider extends ContentProvider {

    private static final String AUTHORITY = "com.train.versesstock.stockprovider";
    private static final String BASE_PATH1 = "types";
    private static final String BASE_PATH2 = "chapters";
    private static final String BASE_PATH3 = "verses";

    public static final Uri CONTENT_URI1 = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH1 );
    public static final Uri CONTENT_URI2 = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH2 );
    public static final Uri CONTENT_URI3 = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH3 );

    // Constant to identify the requested operation
    private static final int TYPE = 1;
    private static final int CHAPTER = 2;
    private static final int VERSE = 3;

    private static UriMatcher uriMathcer = new UriMatcher(UriMatcher.NO_MATCH);

    static {

        uriMathcer.addURI(AUTHORITY, BASE_PATH1, TYPE);
        uriMathcer.addURI(AUTHORITY, BASE_PATH2, CHAPTER);
        uriMathcer.addURI(AUTHORITY, BASE_PATH3, VERSE);
    }

    private SQLiteDatabase database;
    private StockOpenHelper helper;

    private String type ;

    @Override
    public boolean onCreate() {

        helper = new StockOpenHelper(getContext());

        database = helper.getReadableDatabase();
        helper.copyDataBase();

        return true;
    }

    @Override
    public Cursor query ( Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder ) {

        if ( uriMathcer.match(uri) == TYPE ){

            return database.query(StockOpenHelper.TABLE_TYPES, StockOpenHelper.TYPE_COLUMNS,
                    selection, null, null, null, StockOpenHelper.KEY_CREATED_AT + " DESC");
        }

        else if ( uriMathcer.match(uri) == CHAPTER ) {

            if ( VersesChapter.verseType != null ){

                type = VersesChapter.verseType ;
            }

            return database.query(StockOpenHelper.TABLE_CHAPTERS, StockOpenHelper.CHAPTER_COLUMNS,
                    "type = ?", new String[]{type}, null, null, StockOpenHelper.KEY_CREATED_AT + " DESC");
        }
        else {

            return database.query(StockOpenHelper.TABLE_VERSES, StockOpenHelper.VERSE_COLUMNS,
                    "chapter = ?", new String[]{Verse.chapter}, null, null, StockOpenHelper.KEY_CREATED_AT + " DESC");
        }
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        long id = database.insert(StockOpenHelper.TABLE_TYPES, null, values);
        return Uri.parse(BASE_PATH1 + "/" + id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        return database.delete(StockOpenHelper.TABLE_TYPES, selection, selectionArgs);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        return database.update(StockOpenHelper.TABLE_TYPES, values, selection, selectionArgs);
    }
}
