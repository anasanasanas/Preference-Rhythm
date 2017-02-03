package com.anax.preference.Preference;

import android.content.Context;

import com.anax.preferencerhythm.PreferenceRepoImpl;


/**
 * @author Anas Alaa
 * @version 1.0.0
 * @since 03/02/2017
 */
public class UserTokenPreferenceRepo extends PreferenceRepoImpl<String> {
    public UserTokenPreferenceRepo(Context context, Class<String> clazz) {
        super(context, clazz);
    }

    @Override
    public boolean encryptData() {
        return true;
    }

    @Override
    public String encryptionKey() {
        return "8N5zx1CUFyoE7Z3WuUq5SX14ziWKBJti";
    }
}
