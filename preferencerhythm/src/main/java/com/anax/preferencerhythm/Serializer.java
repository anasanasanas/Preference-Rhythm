/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.anax.preferencerhythm;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Json Serializer/Deserializer.
 *
 * @author Fernando Cejas
 * @author Anas Alaa
 * @version 2.0.0
 * @since 21/01/2017
 */
public final class Serializer {

    private final Gson gson = new Gson();

    private static Serializer serializer;

    public static Serializer getInstance() {
        if (serializer != null)
            return serializer;

        serializer = new Serializer();
        return serializer;
    }

    private Serializer() {

    }

    /**
     * Serialize an object to Json.
     *
     * @param object object to serialize.
     * @param clazz  object class
     * @return json string
     */
    public String serialize(Object object, Class clazz) {
        return gson.toJson(object, clazz);
    }

    /**
     * Serialize an object to Json.
     *
     * @param object List of objects
     * @param clazz  Object class
     * @return
     */
    public <T> String serializeList(Object object, Class<T> clazz) {
        return gson.toJson(object, new ParameterizedList<T>(clazz));
    }

    public String serialize(Object object, Type type) {
        return gson.toJson(object, type);
    }


    /**
     * Deserialize a json representation of an object.
     *
     * @param string A json string to deserialize.
     */
    public <T> T deserialize(String string, Class<T> clazz) {
        return gson.fromJson(string, clazz);
    }

    /**
     * Deserialize a json representation of an List of objects.
     *
     * @param string A json string to deserialize.
     * @param clazz  Object class
     * @return List of the T object
     */
    public <T> List<T> deserializeList(String string, Class<T> clazz) {
        return gson.fromJson(string, new ParameterizedList<T>(clazz));
    }


    public <T> T deserialize(String string, Type type) {
        return gson.fromJson(string, type);
    }
}
