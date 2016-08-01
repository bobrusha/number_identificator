package com.bobrusha.android.yandex.call_notification;

import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Aleksandra on 01/08/16.
 */
public class Searcher {

    public static final String PREFIX_URL = "https://yandex.ru/search/?text=";

    public void loadSearchResultAsync(String phone, final TextView tv) {
        Observable.just(phone)
                .subscribeOn(Schedulers.newThread()).map(new Func1<String, String>() {
            @Override
            public String call(String s) {
                return getSearchResultFor(s);
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        tv.setText(s);
                        tv.invalidate();
                    }
                });
    }

    public String getSearchResultFor(String query) {
        String searchResult = null;

        try {
            Document document = Jsoup.connect(PREFIX_URL + query).get();
            searchResult = document.getElementsByClass("serp-item__title")
                    .first()
                    .getAllElements()
                    .first()
                    .text();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return searchResult;
    }
}
