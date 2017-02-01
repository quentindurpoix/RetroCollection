package com.durpoix.quentin.retrocollection;

/**
 * Created by Coco on 01/02/2017.
 */
public class Game {
    private String Name;
    private String Console;
    private String Type;
    private String Price;

    public Game(String Name, String Console, String Price){
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

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }
}
