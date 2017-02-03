package com.anax.preferencerhythm;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author Anas Alaa
 * @version 1.0.0
 * @since 21/01/2017
 */

interface PreferenceRepo<T> {

    List<T> getList();

    Observable<List<T>> getListObservable();

    void saveList(List<T> tList);

    T getObject();

    Observable<T> getObjectObservable();

    void saveObject(T t);

    void clear();

    boolean isEmpty();

}
