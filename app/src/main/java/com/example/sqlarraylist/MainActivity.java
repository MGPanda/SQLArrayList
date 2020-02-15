package com.example.sqlarraylist;

import android.database.Cursor;
import android.os.Bundle;
import android.app.ListActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends ListActivity {


    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        MyDB mydb = new MyDB(this);
        MyDatabaseHelper mdh = new MyDatabaseHelper(this);
        mdh.onCreate(mydb.getDatabase());
        mydb.insertItems();
        ArrayList<RowModel> list = new ArrayList<RowModel>();
        Cursor c = mydb.selectRecords();
        for (int i = 0; i < c.getCount(); i++) {
            list.add(new RowModel(c.getString(1)));
            c.moveToNext();
        }
        setListAdapter(new RatingAdapter(list));
        /*ArrayList<RowModel> list=new ArrayList<RowModel>();
        for (String s : items) {
            list.add(new RowModel(s));
        }
        setListAdapter(new RatingAdapter(list));*/
    }

    private RowModel getModel(int position) {
        return (((RatingAdapter) getListAdapter()).getItem(position));
    }

    class RatingAdapter extends ArrayAdapter<RowModel> {
        RatingAdapter(ArrayList<RowModel> list) {
            super(MainActivity.this, R.layout.row, R.id.label, list);
        }

        public View getView(int position, View convertView,
                            ViewGroup parent) {
            View row = super.getView(position, convertView, parent);
            ViewHolder holder = (ViewHolder) row.getTag();
            if (holder == null) {
                holder = new ViewHolder(row);
                row.setTag(holder);
                RatingBar.OnRatingBarChangeListener l =
                        new RatingBar.OnRatingBarChangeListener() {
                            public void onRatingChanged(RatingBar ratingBar,
                                                        float rating,
                                                        boolean fromTouch) {
                                Integer myPosition = (Integer) ratingBar.getTag();
                                RowModel model = getModel(myPosition);
                                model.rating = rating;
                                LinearLayout parent = (LinearLayout) ratingBar.getParent();
                                TextView label = (TextView) parent.findViewById(R.id.label);
                                label.setText(model.toString());
                            }
                        };
                holder.rate.setOnRatingBarChangeListener(l);
            }
            RowModel model = getModel(position);
            holder.rate.setTag(new Integer(position));
            holder.rate.setRating(model.rating);
            return (row);
        }
    }

    class RowModel {
        String label;
        float rating = 2.0f;

        RowModel(String label) {
            this.label = label;
        }

        public String toString() {
            if (rating >= 3.0) {
                return (label.toUpperCase());
            }
            return (label);
        }
    }

    class ViewHolder {
        RatingBar rate = null;

        ViewHolder(View base) {
            this.rate = (RatingBar) base.findViewById(R.id.rate);
        }
    }
}