package com.durpoix.quentin.retrocollection;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Coco on 15/02/2017.
 */
public class Database extends SQLiteOpenHelper {

    public static final String CATEGORY_ID = "id_category";
    public static final String CATEGORY_NAME = "name";


    public static final String CATEGORY_TABLE_NAME = "CATEGORY";
    public static final String CATEGORY_TABLE_CREATE =
            "CREATE TABLE IF NOT EXISTS " + CATEGORY_TABLE_NAME + " (" +
                    CATEGORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    CATEGORY_NAME + " TEXT "+");";

    public static final String CONSOLE_ID = "id_console";
    public static final String CONSOLE_NAME = "name";
    public static final String CONSOLE_PRICE = "price";
    public static final String CONSOLE_NB_CONTRO = "nb_contro"; //nb manettes

    public static final String CONSOLE_TABLE_NAME = "CATEGORY";
    public static final String CONSOLE_TABLE_CREATE =
            "CREATE TABLE IF NOT EXISTS " + CONSOLE_TABLE_NAME + " (" +
                    CONSOLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    CONSOLE_NAME + " TEXT "+
                    CONSOLE_PRICE + " FLOAT, "+
                    CONSOLE_NB_CONTRO + " INTEGER"+");";


    public static final String GAME_ID = "id_game";
    public static final String GAME_NAME = "name";
    public static final String GAME_ID_CONSOLE = "id_console";
    public static final String GAME_ID_CATEGORY = "id_category";
    public static final String GAME_PRICE = "price";

    public static final String GAME_TABLE_NAME = "GAME";
    public static final String GAME_TABLE_CREATE =
            "CREATE TABLE IF NOT EXISTS " + GAME_TABLE_NAME + " (" +
                    GAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    GAME_NAME + " TEXT, " +
                    GAME_ID_CONSOLE + " INTEGER, "+
                    GAME_ID_CATEGORY + " INTEGER " +
                    GAME_PRICE + " FLOAT," +
                    "FOREIGN KEY("+GAME_ID_CATEGORY+") REFERENCES "+CATEGORY_TABLE_NAME+"("+CATEGORY_ID+"), "+
                    "FOREIGN KEY("+GAME_ID_CONSOLE+") REFERENCES "+CONSOLE_TABLE_NAME+"("+CONSOLE_ID+") "+");";


    public static final String GAME_TABLE_DROP = "DROP TABLE IF EXISTS " + GAME_TABLE_NAME + ";";
    public static final String CONSOLE_TABLE_DROP = "DROP TABLE IF EXISTS " + CONSOLE_TABLE_NAME + ";";
    public static final String CATEGORY_TABLE_DROP = "DROP TABLE IF EXISTS " + CATEGORY_TABLE_NAME + ";";


    private static final String DATABASE_NAME = "database_shelf.db";
    private static final int VERSION = 1;

    public Database(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CATEGORY_TABLE_CREATE);
        db.execSQL(CONSOLE_TABLE_CREATE);
        db.execSQL(GAME_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(CATEGORY_TABLE_DROP);
        db.execSQL(CONSOLE_TABLE_DROP);
        db.execSQL(GAME_TABLE_DROP);
        onCreate(db);

    }
}
