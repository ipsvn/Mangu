package com.example.mangareader.Read;

import android.app.Activity;
import com.example.mangareader.Downloading.DownloadedChapter;
import com.example.mangareader.SourceHandlers.Sources;

import java.util.ArrayList;
import java.util.HashMap;

public interface Readmodes {

    class Data {
        Activity activity;
        ArrayList<String> images;
        Sources sources;
        HashMap<String, String> reqData;

        public Data(Activity activity, ArrayList<String> images, Sources sources, HashMap<String, String> reqData) {
            this.activity = activity;
            this.images = images;
            this.sources = sources;
            this.reqData = reqData;
        }
    }

    class DownloadData {
        Activity activity;
        ArrayList<DownloadedChapter> chapterDatas;
        Sources sources;
        HashMap<String, String> reqData;

        public DownloadData(Activity activity, ArrayList<DownloadedChapter> chapterDatas, Sources sources, HashMap<String, String> reqData) {
            this.activity = activity;
            this.chapterDatas = chapterDatas;
            this.sources = sources;
            this.reqData = reqData;

        }
    }

    void loadImageDownload();

    void inflate(Activity activity); // Inflates the necessary widgets from a xml file

    void Start(Activity activity, ArrayList<String> images, Sources sources, HashMap<String, String> reqData);

    void startDownloads(Activity activity, ArrayList<DownloadedChapter> downlaods, Sources sources, HashMap<String, String> reqData);

    void LoadImage(); // 1 = next -1 = previous

    void ChangePages(int direction); // 1 = next -1 = previous

    void ChangeChapter(int direction);

    void changeChapterDownloads(int direction);

    void ChangePagesDownload(int direction);

}
