package com.anax.preferencerhythm;

import android.content.Context;
import android.database.Observable;

import com.anax.preferencerhythm.exception.SharedPrefDataNotFoundException;

import java.util.List;


/**
 * @author Anas Alaa
 * @version 1.0.0
 * @since 21/01/2017
 */

public abstract class PreferenceRepoImpl<T> implements PreferenceRepo<T> {

    private Preferences preferences;
    private String objectKey;
    private String listKey;
    private Class<T> clazz;

    public abstract boolean encryptData();

    public abstract String encryptionKey();


    public PreferenceRepoImpl(Context context, Class<T> clazz) {
        if (clazz == null)
            throw new IllegalArgumentException("Class can NOT be null");
        if (context == null)
            throw new IllegalArgumentException("Context can NOT be null");

        String sharedPreferenceName = this.getClass().getSimpleName() + "SharedPreference";
        objectKey = this.getClass().getSimpleName() + "ObjectKey";
        listKey = this.getClass().getSimpleName() + "ListKey";

        this.getClass().getGenericSuperclass();

        preferences = new Preferences(context, sharedPreferenceName, encryptData(), encryptionKey());

        this.clazz = clazz;
    }

    @Override
    public final List<T> getList() {
        return preferences.getList(listKey, clazz);
    }


    @Override
    public final void saveList(List<T> tList) {
        preferences.saveList(tList, listKey, clazz);
    }

    @Override
    public final T getObject() {
        return preferences.getSingleObject(objectKey, clazz);
    }



    @Override
    public final void saveObject(T t) {
        preferences.saveSingleObject(t, objectKey);
    }

    @Override
    public final void clear() {
        preferences.clear();
    }

    @Override
    public final boolean isEmpty() {
        return preferences.isEmpty();
    }
}
