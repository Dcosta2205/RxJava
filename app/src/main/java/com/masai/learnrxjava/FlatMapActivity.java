package com.masai.learnrxjava;

import android.os.Bundle;
import android.os.SystemClock;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.masai.learnrxjava.databinding.ActivityFlatMapBinding;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FlatMapActivity extends AppCompatActivity {

    private ActivityFlatMapBinding binding;
    private Observable<Student> studentObservable;
    private DisposableObserver<Student> disposableObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_flat_map);
        studentObservable = getStudentObservable();
        getDisposableObserver();
        studentObservable.subscribeWith(disposableObserver);
    }

    private Observable<Student> getStudentObservable() {
        return Observable.fromIterable(getStudentList())
                .flatMap(new Function<Student, Observable<Student>>() {
                    @Override
                    public Observable<Student> apply(Student student) throws Throwable {
                        SystemClock.sleep(1000);
                        Student student1 = new Student(1, "Added Student - new_1");
                        return Observable.just(student, student1);
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private void getDisposableObserver() {
        disposableObserver = new DisposableObserver<Student>() {
            @Override
            public void onNext(@NonNull Student student) {
                String text = (String) binding.tvData.getText();
                binding.tvData.setText(text + "\n\n" + student.getName());
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
    }

    private ArrayList<Student> getStudentList() {
        ArrayList<Student> list = new ArrayList<>();
        for (int i = 1; i <= 5000; i++) {
            list.add(new Student(i, "Lloyd - " + i));
        }
        return list;
    }
}