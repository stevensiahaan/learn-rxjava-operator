package com.stevensiahaan.learnrxjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private String TAG = "MainActivity";
    private Disposable disposable;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Observable<String> animalsObservable = getAnimalsObservable();
        DisposableObserver<String> animalsObserver = getAnimalObserver();
        DisposableObserver<String> animalsObserverAllCaps = getAnimalAllCapsObserver();

//        animalsObservable
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .filter(new Predicate<String>() {
//                    @Override
//                    public boolean test(String s) throws Exception {
//                        return s.toLowerCase().startsWith("b");
//                    }
//                })
//                .subscribe(animalObserver);


//        compositeDisposable.add(animalsObservable
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .filter(new Predicate<String>() {
//                    @Override
//                    public boolean test(String s) throws Exception {
//                        return s.toLowerCase().startsWith("b");
//                    }
//                })
//                .subscribeWith(animalsObserver));
//
//        compositeDisposable.add(
//                animalsObservable
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .filter(new Predicate<String>() {
//                            @Override
//                            public boolean test(String s) throws Exception {
//                                return s.toLowerCase().startsWith("c");
//                            }
//                        })
//                        // .map() operator is used to turn the note into all uppercase letters
//                        .map(new Function<String, String>() {
//                            @Override
//                            public String apply(String s) throws Exception {
//                                return s.toUpperCase();
//                            }
//                        })
//                        .subscribeWith(animalsObserverAllCaps));

        compositeDisposable.add(getNotesObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<Note, Note>() {
                    @Override
                    public Note apply(Note note) throws Exception {
                        // Making the note to all uppercase
                        note.setNote(note.getNote().toUpperCase());
                        return note;
                    }
                })
                .subscribeWith(getNotesObserver()));

    }

    private DisposableObserver<String> getAnimalObserver() {
        return new DisposableObserver<String>() {
            @Override
            public void onNext(String s) {
                Log.e(TAG, "Name: " + s);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "All items are emitted!");
            }
        };
    }

    private DisposableObserver<String> getAnimalAllCapsObserver() {
        return new DisposableObserver<String>() {
            public void onNext(String s) {
                Log.e(TAG, "All Caps Name: " + s);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "All Caps onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "All items All Caps are emitted!");
            }
        };
    }

    private Observable<String> getAnimalsObservable() {
        // Observable.just("Ant", "Bee", "Cat", "Dog", "Fox");
        return Observable.fromArray(
                "Ant", "Ape",
                "Bat", "Bee", "Bear", "Butterfly",
                "Cat", "Crab", "Cod",
                "Dog", "Dove",
                "Fox", "Frog");
    }


    private List<Note> prepareNotes() {
        List<Note> notes = new ArrayList<>();
        notes.add(new Note(1, "buy tooth paste!"));
        notes.add(new Note(2, "call brother!"));
        notes.add(new Note(3, "watch narcos tonight!"));
        notes.add(new Note(4, "pay power bill!"));

        return notes;
    }

    private DisposableObserver<Note> getNotesObserver() {
        return new DisposableObserver<Note>() {

            @Override
            public void onNext(Note note) {
                Log.e(TAG, "Note: " + note.getNote());
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "All notes are emitted!");
            }
        };
    }


    private Observable<Note> getNotesObservable() {
        final List<Note> notes = prepareNotes();

        return Observable.create(new ObservableOnSubscribe<Note>() {
            @Override
            public void subscribe(ObservableEmitter<Note> emitter) throws Exception {
                for (Note note : notes) {
                    if (!emitter.isDisposed()) {
                        emitter.onNext(note);
                    }
                }

                if (!emitter.isDisposed()) {
                    emitter.onComplete();
                }
            }
        });
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();

        // don't send events once the activity is destroyed
        compositeDisposable.dispose();
    }
}
