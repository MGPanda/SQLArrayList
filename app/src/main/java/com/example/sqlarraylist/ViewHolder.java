package com.example.sqlarraylist;

import android.view.View;
import android.widget.ImageView;

import com.example.sqlarraylist.R;

class ViewHolder {
    ImageView icon = null;

    ViewHolder(View base) {
        this.icon = (ImageView) base.findViewById(R.id.icon);
    }


}
