package com.train.versesstock;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class TypeCursorAdapter extends CursorAdapter {

    public TypeCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        return LayoutInflater.from(context).inflate( R.layout.type_list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        String type = cursor.getString(cursor.getColumnIndex(StockOpenHelper.TYPE));

        TextView typeName = (TextView) view.findViewById(R.id.typeName);
        typeName.setText(type);
    }
}
