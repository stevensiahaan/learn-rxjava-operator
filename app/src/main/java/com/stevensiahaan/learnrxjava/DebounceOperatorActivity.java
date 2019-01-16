package com.stevensiahaan.learnrxjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding3.widget.RxTextView;
import com.jakewharton.rxbinding3.widget.TextViewTextChangeEvent;

import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class DebounceOperatorActivity extends AppCompatActivity {

    private TextView txtSearchString;
    private EditText editTextInputSearch;
    private static final String TAG = DebounceOperatorActivity.class.getSimpleName();
    private CompositeDisposable disposable = new CompositeDisposable();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debounce_operator);

        txtSearchString = (TextView) findViewById(R.id.txt_search_string);
        editTextInputSearch = (EditText) findViewById(R.id.input_search);

        disposable.add(
                RxTextView.textChangeEvents(editTextInputSearch)
                        .skipInitialValue()
                        .debounce(300, TimeUnit.MILLISECONDS)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(searchQuery()));

        txtSearchString.setText("Search query will be accumulated every 300 milli sec");
    }

    private DisposableObserver<TextViewTextChangeEvent> searchQuery() {
        return new DisposableObserver<TextViewTextChangeEvent>() {
            @Override
            public void onNext(TextViewTextChangeEvent textViewTextChangeEvent) {
                Log.e(TAG, "search string: " + textViewTextChangeEvent.getText().toString());

                txtSearchString.setText("Query: " + textViewTextChangeEvent.getText().toString());
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }
}
