package com.durpoix.quentin.retrocollection;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Coco on 04/02/2017.
 */
public class MonAdaptateurDeListe extends ArrayAdapter<Game> {

    private Integer[] tab_images_pour_la_liste = {
            R.drawable.jeu1, R.drawable.jeu2, R.drawable.jeu3, R.drawable.jeu4, R.drawable.jeu5 };

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        
        LayoutInflater inflater = (LayoutInflater)
                getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.game_show, parent, false);

        TextView textView = (TextView) rowView.findViewById(R.id.label);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);

        textView.setText(getItem(position).getName());

        if(convertView == null )
            imageView.setImageResource(tab_images_pour_la_liste[position]);
        else
            rowView = (View)convertView;

        return rowView;
    }

    public MonAdaptateurDeListe(Context context, Game[] jeux) {
        super(context, R.layout.game_show, jeux);
    }
}
