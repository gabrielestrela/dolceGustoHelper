package br.com.nespressohelper.daitan.nespressohelper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by gabrielestrela on 09/11/17.
 */

public class CoffeGrid extends BaseAdapter {
   private Context mContext;
   private final String[] web;
   private final ArrayList<Integer> imageId;

    public CoffeGrid(Context mContext, ArrayList<String> web, ArrayList<Integer> imageId) {
        this.mContext = mContext;
        this.web = web.toArray(new String[0]);
        this.imageId = imageId;
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

//        grid = new View(mContext);


        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            grid = inflater.inflate(R.layout.grid_item, null);

        }else {
            grid = (View) convertView;
        }

        TextView txt = (TextView) grid.findViewById(R.id.gridTxt);
        txt.setText(web[position]);
        ImageView img = (ImageView) grid.findViewById(R.id.gridImage);
//        img.setImageResource(imageId[position]);
        Picasso.with(mContext).load(imageId.get(position)).into(img);

        return grid;
    }
}
