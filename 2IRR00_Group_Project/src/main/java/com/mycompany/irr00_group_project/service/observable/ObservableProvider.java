package com.mycompany.irr00_group_project.service.observable;

import java.util.Optional;

/**
 * Interface for providing access to an observable registry.
 */
public interface ObservableProvider {
    ObservableRegistry getObservableRegistry();

    default <T> Optional<T> getObservable(Class<T> type) {
        return getObservableRegistry().get(type);
    }

    default <T> T getObservableOrThrow(Class<T> type) {
        return getObservableRegistry().getOrThrow(type);
    }

    default boolean hasObservable(Class<?> type) {
        return getObservableRegistry().has(type);
    }
}