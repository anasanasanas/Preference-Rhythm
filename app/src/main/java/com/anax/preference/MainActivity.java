package com.anax.preference;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;


import com.anax.preference.Model.Country;
import com.anax.preference.Preference.CountryPreferenceRepo;
import com.anax.preference.Remote.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public final String LOG = this.getClass().getSimpleName();

    private RecyclerViewAdapter mAdapter;

    private List<Country> countries = new ArrayList<>();

    private CountryPreferenceRepo countryPreferenceRepo;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        progressBar = (ProgressBar) findViewById(R.id.toolbar_progress_bar);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new RecyclerViewAdapter(countries);
        mRecyclerView.setAdapter(mAdapter);

        countryPreferenceRepo = new CountryPreferenceRepo(getApplicationContext(), Country.class);

        getCountriesCodeWithRx();

//        getCountriesCode();
    }

    public void getCountriesCodeWithRx() {
        progressBar.setVisibility(View.VISIBLE);

        countryPreferenceRepo.getListObservable()
                .mergeWith(ApiInterface.Creator.newApiService().getCountryCodeObservable()
                        .map(new Function<List<Country>, List<Country>>() {
                            @Override
                            public List<Country> apply(List<Country> countries) throws Exception {
                                Log.d(LOG, "Save new data in preference !");
                                countryPreferenceRepo.saveList(countries);
                                return countries;
                            }
                        }))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<List<Country>>() {
                    @Override
                    public void onNext(List<Country> countriesList) {
                        Log.d(LOG, "onNext");
                        countries.clear();
                        countries.addAll(countriesList);
                        mAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }

    public void getCountriesCode() {
        progressBar.setVisibility(View.VISIBLE);

        List<Country> preferenceList = countryPreferenceRepo.getList();
        if (preferenceList != null) {
            countries.clear();
            countries.addAll(preferenceList);
            mAdapter.notifyDataSetChanged();
        }

        ApiInterface.Creator.newApiService().getCountryCode().enqueue(new Callback<List<Country>>() {
            @Override
            public void onResponse(Call<List<Country>> call, Response<List<Country>> response) {
                if (response.isSuccessful()) {
                    countries.clear();
                    countries.addAll(response.body());
                    mAdapter.notifyDataSetChanged();
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<Country>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}
