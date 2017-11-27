package com.anax.preferencerhythm;

import java.util.List;


/**
 * @author Anas Alaa
 * @version 1.0.0
 * @since 21/01/2017
 */

interface PreferenceRepo<T> {

    List<T> getList();


    void saveList(List<T> tList);

    T getObject();


    void saveObject(T t);

    void clear();

    boolean isEmpty();

}
