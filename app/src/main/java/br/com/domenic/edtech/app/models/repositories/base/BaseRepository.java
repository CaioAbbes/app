package br.com.domenic.edtech.app.models.repositories.base;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class BaseRepository<T> {

    protected ExecutorService executorService;

    public BaseRepository(Application application) {
        executorService = Executors.newSingleThreadExecutor();
    }

    public abstract LiveData<List<T>> getAll();

    public void insert(T entity, Runnable onCompletion) {
        executorService.execute(() -> {
            insertInDatabase(entity);
            onCompletion.run();
        });
    }

    protected abstract void insertInDatabase(T entity);

    public void update(T entity) {
        executorService.execute(() -> updateInDatabase(entity));
    }

    protected abstract void updateInDatabase(T entity);

    public void delete(T entity) {
        executorService.execute(() -> deleteInDatabase(entity));
    }

    protected abstract void deleteInDatabase(T entity);

    // Novo método para exclusões baseadas em critérios
    public void deleteByCriteria(int criteria, Runnable onCompletion) {
        executorService.execute(() -> {
            deleteInDatabaseByCriteria(criteria);  // Método abstrato a ser implementado por subclasses
            if (onCompletion != null) {
                onCompletion.run();
            }
        });
    }

    // Método abstrato que as subclasses devem implementar para definir o critério de exclusão
    protected abstract void deleteInDatabaseByCriteria(int criteria);
}