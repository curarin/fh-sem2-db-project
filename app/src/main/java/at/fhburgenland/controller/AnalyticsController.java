package at.fhburgenland.controller;

import at.fhburgenland.model.repository.interfaces.AnalyticsRepository;
import at.fhburgenland.view.AnalyticsView;

/**
 * Controller representation of analytics domain - maps user input from view to repository methods
 */
public class AnalyticsController {
    private final AnalyticsRepository analyticsRepository;
    private final AnalyticsView analyticsView;

    public AnalyticsController(AnalyticsRepository analyticsRepository, AnalyticsView analyticsView) {
        this.analyticsRepository = analyticsRepository;
        this.analyticsView = analyticsView;
    }

    /**
     * Run the analytics controller layer inside a loop
     */
    public void start() {
        boolean running = true;

        while (running) {
            switch (analyticsView.showMainMenu()) {
                case 1 -> {
                    System.out.println("Welcome to Analytics Controller");
                }
                case 2 -> {
                    System.out.println("This is to be...");

                }
                case 3 -> {
                    System.out.println("...implemented...");

                }
                case 4 -> {
                    System.out.println("...soon!");

                }
                case 0 -> running = false;
            }
        }
    }
}
