package com.durpoix.quentin.retrocollection;

import android.app.ListActivity;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

/**
 * Created by Coco on 01/02/2017.
 */
public class Game extends ListActivity{
    private String Name;
    private String Console;
    private String Type;
    private float Price;
    public int img;

    public Game(String Name, String Console, float Price){
        this.Name=Name;
        this.Console=Console;
        this.Price=Price;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getConsole() {
        return Console;
    }

    public void setConsole(String console) {
        Console = console;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public float getPrice() {
        return Price;
    }

    public void setPrice(float price) {
        Price = price;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
