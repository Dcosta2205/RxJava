package com.masai.learnrxjava;

import android.os.Bundle;
import android.os.SystemClock;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.masai.learnrxjava.databinding.ActivityMapOperatorBinding;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MapOperatorActivity extends AppCompatActivity {

    private Observable<Integer> integerObservable;
    private DisposableObserver<Integer> disposableObserver;
    private Integer[] arr = {1, 2, 3, 4, 5};
    private ActivityMapOperatorBinding binding;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_map_operator);
        getIntegerObservable().subscribe(getDisposableObserver());
    }

    private Observable<Integer> getIntegerObservable() {
        integerObservable = Observable.fromArray(arr)
                /*
                Transforming the data received and returning the transformed data
                 */
                .map(new Function<Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer) throws Throwable {
                        SystemClock.sleep(1000);
                        return integer * 10;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        return integerObservable;
    }

    private DisposableObserver<Integer> getDisposableObserver() {
        disposableObserver = new DisposableObserver<Integer>() {
            @Override
            public void onNext(@NonNull Integer integer) {
                binding.tvData.setText(integer + "");
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        return disposableObserver;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposableObserver.dispose();
    }
}