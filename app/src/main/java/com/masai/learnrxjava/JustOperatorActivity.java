package com.masai.learnrxjava;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.masai.learnrxjava.databinding.ActivityJustOperatorBinding;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.functions.Consumer;

public class JustOperatorActivity extends AppCompatActivity {

    private ActivityJustOperatorBinding binding;
    private String[] names = {"Ram", "Sita", "Laxman"};
    private CompositeDisposable disposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_just_operator);
        binding.setActivity(this);
    }

    public void onSingleItemClick(View view) {
        disposable.add(
                Observable.just("Lloyd")
                        .subscribe(new Consumer<String>() {
                            @Override
                            public void accept(String s) throws Throwable {
                                binding.tvOneItem.setText(s);
                            }
                        })
        );

    }

    public void onMultipleItemClick(View view) {
        disposable.add(
                Observable.
                        //when a array is given to just operator it emits all the items at once. Better to use FromArray as it gives one item at a time
                                just(names)
                        .subscribe(new Consumer<String[]>() {
                            @Override
                            public void accept(String[] strings) throws Throwable {
                                for (String name : strings) {
                                    String prevText = (String) binding.tvMultipleItem.getText();
                                    binding.tvMultipleItem.setText(prevText + " \n" + name);
                                }
                            }
                        }));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }
}