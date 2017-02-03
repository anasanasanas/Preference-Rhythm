# Preference Rhythm [![](https://jitpack.io/v/anasanasanas/Preference-Rhythm.svg)](https://jitpack.io/#anasanasanas/Preference-Rhythm)


Android library makes using Shared Preference easier.

## Features
* Support RxJava 2
* Encrypt Data
* Support Iterable
* Build on top of repository design pattern

## Installation

Add this to your module `build.gradle` file:

```
dependencies {
    ...
     compile "com.github.anasanasanas:Preference-Rhythm:1.0.0"
}
```

Add this to your **root** `build.gradle` file:
```
allprojects {
    repositories {
        ...
        maven { url "https://jitpack.io" }
    }
}
```

How to use Preference Rhythm ?

just extend `PreferenceRepoImpl` with the Object you want to save.
```
public class CountryPreferenceRepo extends PreferenceRepoImpl<Country> {
    public CountryPreferenceRepo(Context context, Class<Country> clazz) {
        super(context, clazz);
    }

    @Override
    public boolean encryptData() {
        return false;
    }

    @Override
    public String encryptionKey() {
        return null;
    }
}
```
OR
```
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
```

Then
```
        //check if the shared preference is empty and return boolean value
        userTokenPreferenceRepo.isEmpty();

        //clear the shared preference data
        userTokenPreferenceRepo.clear();

        //save the object in the shared preference
        userTokenPreferenceRepo.saveObject("Token");

        //get the object in the shared preference, if nothing saved return null
        userTokenPreferenceRepo.getObject();

        //save list of objects in the shared preference
        userTokenPreferenceRepo.saveList(new ArrayList<String>());

        //get the list saved in the shared preference, if nothing saved return null
        userTokenPreferenceRepo.getList();

        //return Observable that emits the Object
        userTokenPreferenceRepo.getObjectObservable();

        //return Observable that emits List of the objects
        userTokenPreferenceRepo.getListObservable();
```


## Notes
* Every class extend `PreferenceRepoImpl` has his own shared preference file.
* If the Encryption key changed all the data in the shared preference will be cleared.
