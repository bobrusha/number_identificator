package com.bobrusha.android.yandex.call_notification;

/**
 * Created by Aleksandra on 01/08/16.
 */
public class SeacherManager {
    public static final Searcher instance = new Searcher();

    public static Searcher getInstance() {
        return instance;
    }
}
