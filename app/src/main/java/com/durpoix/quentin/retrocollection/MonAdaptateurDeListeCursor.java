package com.durpoix.quentin.retrocollection;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Coco on 04/02/2017.
 */
public class MonAdaptateurDeListeCursor extends CursorAdapter {

    public MonAdaptateurDeListeCursor(Context context, Cursor cursor) {

        super(context, cursor, 0);

    }

    // The newView method is used to inflate a new view and return it,
    // you don't bind any data to the view at this point.
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.game_show, parent, false);
    }

    // The bindView method is used to bind all data to a given view
    // such as setting the text on a TextView.
    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        // Find fields to populate in inflated template
        TextView nom = (TextView) view.findViewById(R.id.nom);
        TextView emplacement = (TextView) view.findViewById(R.id.emplacement);
        TextView description = (TextView) view.findViewById(R.id.description);
        ImageView photo= (ImageView) view.findViewById(R.id.photo);
        // Find fields to populate in inflated template
        // Extract properties from cursor
        String name= cursor.getString(cursor.getColumnIndex("nom_jeu"));
        String name_cons= cursor.getString(cursor.getColumnIndex("nom_console"));
        int price= cursor.getInt(cursor.getColumnIndex("prix_jeu"));
        String priceV  = price+" €";
        byte[] imageByte= cursor.getBlob(cursor.getColumnIndex("image"));
        Log.i("image",""+imageByte);

        // Populate fields with extracted properties
        nom.setText(name);
        emplacement.setText(priceV);
        description.setText(name_cons);

        if (imageByte == null) {
            photo.setImageResource(R.drawable.space_invader);
        }else{

            Bitmap image = getImage(imageByte);
            photo.setImageBitmap(image);
        }

    }

    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
}
