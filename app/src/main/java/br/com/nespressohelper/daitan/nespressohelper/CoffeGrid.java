package br.com.nespressohelper.daitan.nespressohelper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.TreeSet;

/**
 * Created by gabrielestrela on 09/11/17.
 */


/**
 * This class is necessary to create the GridView
 * It is the custom adapter.
 */
public class CoffeGrid extends BaseAdapter {
   private Context mContext;
   private String[] web;
   private ArrayList<Bitmap> imageBitmaps;
   private Target mTarget;

    public CoffeGrid(Context mContext, ArrayList<String> web, ArrayList<Bitmap> imageBitmaps) {
        this.mContext = mContext;
        this.web = web.toArray(new String[0]);
        this.imageBitmaps = imageBitmaps;
    }

    public void setGridValues(ArrayList<String> names, ArrayList<Bitmap> imageBitmaps) {
        this.web = names.toArray(new String[0]);
        this.imageBitmaps = imageBitmaps;

        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return web.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View grid = convertView;

        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            grid = inflater.inflate(R.layout.grid_item, null);

        }else {
            grid = (View) convertView;
        }

        TextView txt = (TextView) grid.findViewById(R.id.gridTxt);
        txt.setText(web[position]);
        ImageView img = (ImageView) grid.findViewById(R.id.gridImage);

        img.setImageBitmap(imageBitmaps.get(position));

        return grid;
    }
}
