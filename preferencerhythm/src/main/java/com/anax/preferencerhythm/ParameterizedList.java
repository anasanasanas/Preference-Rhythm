package com.anax.preferencerhythm;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;


/**
 * based on this answer.
 * http://stackoverflow.com/a/14139700/3564805
 *
 * @version 1.0.0
 * @since 27/01/2017
 */
class ParameterizedList<T> implements ParameterizedType {
    private Class<?> clazz;

    ParameterizedList(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public Type[] getActualTypeArguments() {
        return new Type[]{clazz};
    }

    @Override
    public Type getRawType() {
        return List.class;
    }

    @Override
    public Type getOwnerType() {
        return null;
    }
}
