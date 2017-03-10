package com.anax.preferencerhythm;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.List;


/**
 * @author Anas Alaa
 * @version 1.0.7
 * @since 27/01/2017
 */
class Preferences {
    private final String KEY_CHECKER = "TEST_ENCRYPTION_KEY";

    private SharedPreferences mPref;
    private Serializer serializer;
    private byte[] encryptionKey;
    private boolean cryptData;

    Preferences(Context context, String sharedPrefName, boolean cryptData, String encryptionKey) {
        mPref = context.getSharedPreferences(sharedPrefName, Context.MODE_PRIVATE);
        this.serializer = Serializer.getInstance();
        if (encryptionKey != null)
            this.encryptionKey = encryptionKey.getBytes();

        if (cryptData) {
            if (this.encryptionKey == null) {
                throw new NullPointerException("encryptionKey can NOT be null while cryptData is true");
            }
            if (this.encryptionKey.length != 16 &&
                    this.encryptionKey.length != 24 &&
                    this.encryptionKey.length != 32) {
                throw new IllegalArgumentException("Encryption key length must be 128, 192 or 256 bit i.e. 16, 24 or 32 byte");
            }

            //handle when Repo start using Encryption
            //all older data must be removed.
            if (getEncryptionKeyTester() == null) {
                clear();
                saveEncryptionKeyTester();
            }
            //handle when Encryption key changed in the Repo.
            else if (isEncryptionKeyChanged()) {
                clear();
                saveEncryptionKeyTester();
            }
        } else {
            //handle when Repo stop using Encryption
            //all old data must be removed
            if (getEncryptionKeyTester() != null)
                clear();
        }


        this.cryptData = cryptData;
    }

    void clear() {
        mPref.edit().clear().apply();
    }

    <T> void saveSingleObject(T t, String name) {
        String dataString = serializer.serialize(t, t.getClass());
        if (cryptData) {
            byte[] data = dataString.getBytes();
            byte[] encryptData = Utils.encryptAES2Base64(data, encryptionKey);
            String encryptDataString = Utils.bytes2Strings(encryptData);
            mPref.edit().putString(name, encryptDataString).apply();
        } else {
            mPref.edit().putString(name, dataString).apply();
        }
    }

    <T> T getSingleObject(String name, Class<T> clazz) {
        String prefDataString = mPref.getString(name, null);

        if (prefDataString == null)
            return null;

        if (cryptData) {
            byte[] encryptData = prefDataString.getBytes();
            byte[] data = Utils.decryptBase64AES(encryptData, encryptionKey);
            String dataString = Utils.bytes2Strings(data);
            return serializer.deserialize(dataString, clazz);
        } else {
            return serializer.deserialize(prefDataString, clazz);
        }

    }

    <T> void saveList(List<T> tList, String name, Class<T> clazz) {
        String dataString = serializer.serializeList(tList, clazz);
        if (cryptData) {
            byte[] data = dataString.getBytes();
            byte[] encryptData = Utils.encryptAES2Base64(data, encryptionKey);
            String encryptDataString = Utils.bytes2Strings(encryptData);
            mPref.edit().putString(name, encryptDataString).apply();
        } else {
            mPref.edit().putString(name, dataString).apply();
        }

    }

    <T> List<T> getList(String name, Class<T> clazz) {
        String prefDataString = mPref.getString(name, null);

        if (prefDataString == null)
            return null;

        if (cryptData) {
            byte[] encryptData = prefDataString.getBytes();
            byte[] data = Utils.decryptBase64AES(encryptData, encryptionKey);
            String dataString = Utils.bytes2Strings(data);
            return serializer.deserializeList(dataString, clazz);
        } else {
            return serializer.deserializeList(prefDataString, clazz);
        }
    }

    boolean isEmpty() {
        return mPref.getAll().size() == 0;
    }

    private void saveEncryptionKeyTester() {
        saveSingleObject(KEY_CHECKER, KEY_CHECKER);
    }

    private String getEncryptionKeyTester() {
        return getSingleObject(KEY_CHECKER, String.class);
    }

    private boolean isEncryptionKeyChanged() {
        return !getEncryptionKeyTester().equals(KEY_CHECKER);
    }
}
