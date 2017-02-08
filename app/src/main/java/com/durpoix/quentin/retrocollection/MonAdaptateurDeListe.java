package com.durpoix.quentin.retrocollection;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Coco on 04/02/2017.
 */
public class MonAdaptateurDeListe extends ArrayAdapter<Game> {


    public MonAdaptateurDeListe(Context context, List<Game> games) {
        super(context, R.layout.game_show, games);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.game_show,parent, false);
        }

        GameViewHolder viewHolder = (GameViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new GameViewHolder();
            viewHolder.nom = (TextView) convertView.findViewById(R.id.nom);
            viewHolder.emplacement = (TextView) convertView.findViewById(R.id.emplacement);
            viewHolder.description = (TextView) convertView.findViewById(R.id.description);
            viewHolder.photo= (ImageView) convertView.findViewById(R.id.photo);
            convertView.setTag(viewHolder);
        }

        //getItem(position) va récupérer l'item [position] de la List<Tweet> tweets
        Game game = getItem(position);

        //il ne reste plus qu'à remplir notre vue
        viewHolder.nom.setText(game.getName());
        String prix = game.getPrice()+"€";
        viewHolder.emplacement.setText(prix);
        viewHolder.description.setText(game.getConsole());
        viewHolder.photo.setImageResource(game.getImg());



        return convertView;
    }

    private class GameViewHolder{
        public TextView emplacement;
        public TextView description;
        public ImageView photo;
        public TextView nom;
    }

}
