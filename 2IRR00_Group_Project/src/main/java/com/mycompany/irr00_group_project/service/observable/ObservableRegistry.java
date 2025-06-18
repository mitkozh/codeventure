package com.mycompany.irr00_group_project.service.observable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * A central registry for managing and providing access
 * to observable objects throughout an application.
 */
public class ObservableRegistry {
    private final Map<Class<?>, Object> observables = new HashMap<>();

    /**
     * Registers an observable instance with the registry.
     *
     * @param <T>        the type of the observable
     * @param type       the class of the observable to use as a key.
     * @param observable the observable instance to register.
     */
    public <T> void register(Class<T> type, T observable) {
        observables.put(type, observable);
    }

    /**
     * Retrieves an observable instance of a specific type from the registry.
     *
     * @param <T>  the type of the observable
     * @param type the class of the observable to retrieve.
     * @return an {@link Optional} containing the observable instance if registered,
     * or an empty {@link Optional} otherwise.
     */
    public <T> Optional<T> get(Class<T> type) {
        return Optional.ofNullable((T) observables.get(type));
    }

    /**
     * Retrieves an observable instance of a specific type, or throws an exception
     * if it is not found.
     *
     * @param <T>  the type of the observable
     * @param type the class of the observable to retrieve.
     * @return the non-null observable instance.
     * @throws IllegalStateException if no observable is registered for the given type.
     */
    public <T> T getOrThrow(Class<T> type) {
        return get(type).orElseThrow(() ->
                new IllegalStateException("No observable registered for type: "
                        + type.getSimpleName()));
    }

    /**
     * Checks if an observable for the specified type is registered.
     *
     * @param type the class of the observable to check.
     * @return {@code true} if an observable of the given type is registered,
     * {@code false} otherwise.
     */
    public boolean has(Class<?> type) {
        return observables.containsKey(type);
    }

    /**
     * Removes all observable instances from the registry.
     */
    public void clear() {
        observables.clear();
    }
}