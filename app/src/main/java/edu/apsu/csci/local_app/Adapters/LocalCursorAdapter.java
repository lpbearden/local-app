//package edu.apsu.csci.local_app.Adapters;
//
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.CursorAdapter;
//import android.widget.ListView;
//import android.widget.TextView;
//
//import edu.apsu.csci.local_app.Data.LocalDatabaseHelper;
//import edu.apsu.csci.local_app.R;
//
///**
// * Created by PC on 4/30/2016.
// */
//public class LocalCursorAdapter extends CursorAdapter {
//    public LocalCursorAdapter(Context context, Cursor cursor, int flags) {
//        super(context, cursor, 0);
//    }
//
//
//    //The newview method is used to inflate a new view and return it
//    //you don't bind any data to the view in this function
//    @Override
//    public View newView(Context context, Cursor cursor, ViewGroup parent) {
//        return LayoutInflater.from(context).inflate(R.layout.adapter_locality, parent, false);
//    }
//
//    //The bindview method is used  to bind all data to a given view
//    //such as setting the text on a textView
//    @Override
//    public void bindView(View view, Context context, Cursor cursor) {
//        //Find fields to populate in inflated template
//        TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
//        TextView tv_description = (TextView) view.findViewById(R.id.tv_description);
//
//        //extract stuff from cursor
//        String name = cursor.getString(cursor.getColumnIndex("name"));
//        String description = cursor.getString(cursor.getColumnIndex("desc"));
//
//        tv_name.setText(name);
//        tv_description.setText(description);
//    }
//}
