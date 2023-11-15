package com.example.shoeson.Activities;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class EpochTime {
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static long getEpochTime(){
        return LocalDateTime.now().toEpochSecond(java.time.ZoneOffset.ofHours(7));
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public LocalDateTime getTimeFromEpoch(long epoch){
        return LocalDateTime.ofEpochSecond(epoch,0, ZoneOffset.UTC);
    }
}
