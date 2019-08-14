package com.example.udelivery_courier.Services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class MyLocation extends Service {
    public MyLocation() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
