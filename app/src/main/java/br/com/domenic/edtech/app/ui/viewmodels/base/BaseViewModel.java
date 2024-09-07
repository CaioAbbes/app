package br.com.domenic.edtech.app.ui.viewmodels.base;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public abstract class BaseViewModel<T> extends AndroidViewModel {

    public BaseViewModel(@NonNull Application application) {
        super(application);
    }

    public abstract LiveData<List<T>> getAll();

    public abstract void insert(T entity, Runnable onCompletion);

    public abstract void update(T entity);

    public abstract void delete(T entity);
}