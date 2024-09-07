package br.com.domenic.edtech.app.ui.viewmodels.factories.base;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.lang.reflect.Constructor;

public class BaseFactory<T extends ViewModel> implements ViewModelProvider.Factory {

    private final Application application;
    private final Class<T> viewModelClass;

    public BaseFactory(Application application, Class<T> viewModelClass) {
        this.application = application;
        this.viewModelClass = viewModelClass;
    }

    @NonNull
    @Override
    public <V extends ViewModel> V create(@NonNull Class<V> modelClass) {
        if (modelClass.isAssignableFrom(viewModelClass)) {
            try {
                Constructor<T> constructor = viewModelClass.getConstructor(Application.class);
                return (V) constructor.newInstance(application);
            } catch (Exception e) {
                throw new RuntimeException("Error creating ViewModel instance", e);
            }
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}