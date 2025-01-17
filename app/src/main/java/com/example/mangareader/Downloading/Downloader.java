package com.example.mangareader.Downloading;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.widget.Toast;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.example.mangareader.Recyclerviews.chapterlist.ButtonValuesChapterScreen;

import java.util.concurrent.CopyOnWriteArrayList;

public class Downloader {
    public void remove(CopyOnWriteArrayList<ButtonValuesChapterScreen> values) {
        // This should literally be impossible, but you can never be safe enough
        if (values.size() == 0) {
            return;
        }

        Context context = values.get(0).getSelectedButton().getContext();

        // We need to check the permissions before we can actually start doing anything
        // This function can only be triggered if the user has already downloaded something obviously,
        // so it wouldn't make sense to check for this permission again however there is still a possibility the user
        // manually turned off the permission afterwards, so it is necessary to keep checking
        int permissionsMet = checkPermissions(context);
        if (permissionsMet == -1) {
            return;
        }

        DownloadTracker downloadTracker = new DownloadTracker();


        // Awesome now that all requirements are met we can actually start removing the downloads
        RemoveService.setValues(values);
        RemoveService.setDownloads(downloadTracker.getFromDownloads(context));

        Intent serviceIntent = new Intent(context, RemoveService.class);
        context.startService(serviceIntent);

    }

    public void download(CopyOnWriteArrayList<ButtonValuesChapterScreen> values) {
        // This should literally be impossible, but you can never be safe enough
        if (values.size() == 0) {
            return;
        }

        Context context = values.get(0).getSelectedButton().getContext();

        // We need to check the permissions before we can actually start doing anything
        int permissionsMet = checkPermissions(context);
        if (permissionsMet == -1) {
            return;
        }

        // We want to send an arraylist with objects.
        // You can't just send an arraylist with objects to another activity instead, so I just assign it statically
        // As much as I hate using statics, I do think this is the most practical for this certain scenario.
        DownloadService.setValues(values);
        Intent serviceIntent = new Intent(context, DownloadService.class);
        context.startService(serviceIntent);
    }

    private int checkPermissions(Context context) {
        int permissions_code = 123;
        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, permissions, permissions_code);
        }

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context, "It appears the app does not have permissions to the storage. Please grant permissions in the settings", Toast.LENGTH_SHORT).show();
            return -1; // You may not continue
        }

        return 0; // You may continue
    }

}


