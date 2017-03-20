package com.durpoix.quentin.retrocollection;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;


public class TestBindingService  extends Service {
    int chrono;
    //Thread clock;

    @Override
    public IBinder onBind(Intent intent) {
        //if (clock == null)
            return new Liant();
    }

    public class Liant extends Binder {
        public int getChrono() {
            return chrono;
        }
    }
}
