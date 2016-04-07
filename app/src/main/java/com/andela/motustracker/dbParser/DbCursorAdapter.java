package com.andela.motustracker.dbParser;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.andela.motustracker.R;
import com.andela.motustracker.helper.AppContext;

/**
 * Created by Spykins on 06/04/16.
 */
public class DbCursorAdapter extends CursorAdapter {
    public DbCursorAdapter(Cursor c, int flags) {
        super(AppContext.get(), c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_view_custom_layout, parent, false);

    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView addressTextView = (TextView) view.findViewById(R.id.id_frag_address);
        TextView latitudeTextView = (TextView)view.findViewById(R.id.id_frag_latitude);
        TextView longitudeTextView = (TextView)view.findViewById(R.id.id_frag_longitude);
        TextView timeSpentTextView = (TextView)view.findViewById(R.id.id_frag_timeSpent);
        TextView dateTextView = (TextView)view.findViewById(R.id.id_frag_date);


        String address = cursor.getString(cursor.getColumnIndex(
                DbConfig.COLUMN_ADDRESS.getRealName()));
        double latitude = cursor.getDouble(cursor.getColumnIndex(
                DbConfig.COLUMN_LATITUDE.getRealName()));
        double longitude = cursor.getDouble(cursor.getColumnIndex(
                DbConfig.COLUMN_LONGITUDE.getRealName()));
        Double timeSpent = cursor.getDouble(cursor.getColumnIndex(
                DbConfig.COLUMN_TIMESPENT.getRealName()));
        String date = cursor.getString(cursor.getColumnIndex(
                DbConfig.COLUMN_DATE.getRealName()));
        Log.d("waleola", "Location is " + address);

        addressTextView.setText(address);
        latitudeTextView.setText(String.valueOf(latitude));
        longitudeTextView.setText(String.valueOf(longitude));
        timeSpentTextView.setText(String.valueOf(timeSpent));
        dateTextView.setText(date);
    }


}
