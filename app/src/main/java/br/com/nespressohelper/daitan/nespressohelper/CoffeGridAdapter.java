package br.com.nespressohelper.daitan.nespressohelper;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;


/**
 * Created by gabrielestrela on 09/11/17.
 */


/**
 * This class is necessary to create the GridView
 * It is the custom adapter.
 */
public class CoffeGridAdapter extends BaseAdapter {
   private Context mContext;
   private ArrayList<String> web;
   private ArrayList<Bitmap> imageBitmaps;

    public CoffeGridAdapter(Context mContext, ArrayList<String> web, ArrayList<Bitmap> imageBitmaps) {
        this.mContext = mContext;
        this.web = web;
        this.imageBitmaps = imageBitmaps;
    }

    public void setGridValues(ArrayList<String> names, ArrayList<Bitmap> imageBitmaps) {
        this.web = names;
        this.imageBitmaps = imageBitmaps;

        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return web.size();
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
            grid = inflater.inflate(R.layout.grid_item, parent, false);

        }

        TextView txt = (TextView) grid.findViewById(R.id.gridTxt);
        txt.setText(web.get(position));
        ImageView img = (ImageView) grid.findViewById(R.id.gridImage);

        img.setImageBitmap(imageBitmaps.get(position));

        return grid;
    }
}
