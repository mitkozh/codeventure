package com.mycompany.irr00_group_project.service.navigator;

import com.mycompany.irr00_group_project.service.observable.NavigationObservables;
import com.mycompany.irr00_group_project.service.observable.ObservableProvider;
import com.mycompany.irr00_group_project.service.observable.ObservableRegistry;

/**
 * Service for managing navigation events and observables.
 */
public class NavigationService implements ObservableProvider {
    private static NavigationService instance;
    private final ObservableRegistry observableRegistry = new ObservableRegistry();

    private NavigationService() {
        observableRegistry.register(NavigationObservables.class, new NavigationObservables());
    }

    /**
     * Returns the singleton instance of NavigationService.
     *
     * @return The singleton instance of NavigationService.
     */
    public static synchronized NavigationService getInstance() {
        if (instance == null) {
            instance = new NavigationService();
        }
        return instance;
    }

    @Override
    public ObservableRegistry getObservableRegistry() {
        return observableRegistry;
    }

    /**
     * Notifies that the user has returned to the game after navigating away.
     */
    public void notifyReturnedToGame() {
        NavigationObservables navObs = getObservableOrThrow(NavigationObservables.class);
        navObs.setReturnedToGame(true);
    }

    /**
     * Notifies that the user has navigated to a specific destination.
     *
     * @param destination The destination to which the user has navigated.
     */
    public void notifyNavigatedTo(String destination) {
        NavigationObservables navObs = getObservableOrThrow(NavigationObservables.class);
        navObs.setLastNavigatedTo(destination);
    }
}