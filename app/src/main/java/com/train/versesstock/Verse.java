package com.train.versesstock;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class Verse extends AppCompatActivity {

    public static String chapter;
    private CursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verse);

        Intent intent = getIntent();
        chapter = intent.getStringExtra("Chapter");

        Cursor cursor = getContentResolver().query(StockProvider.CONTENT_URI3, StockOpenHelper.VERSE_COLUMNS,
                "chapter = ?" , new String[]{chapter}, null );

        cursor.moveToFirst();
        String verse = cursor.getString(cursor.getColumnIndex(StockOpenHelper.VERSE));

        TextView verseText = (TextView) findViewById(R.id.verseText);
        verseText.setText(verse);

        setTitle(chapter);

        cursor.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_verse, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        finish();
    }
}
