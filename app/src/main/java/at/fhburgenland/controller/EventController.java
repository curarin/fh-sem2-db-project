package at.fhburgenland.controller;

import at.fhburgenland.model.Event;
import at.fhburgenland.model.EventType;
import at.fhburgenland.model.repository.interfaces.EventRepository;
import at.fhburgenland.view.EventView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Controller representation of Event - maps user input from view to repository methods.
 * Also consists of small business logic.
 */
public class EventController {
    private final EventRepository eventRepository;
    private final EventView eventView;

    public EventController(EventRepository eventRepository, EventView eventView) {
        this.eventRepository = eventRepository;
        this.eventView = eventView;
    }

    /**
     * Run the event controller layer inside a loop.
     */
    public void start() {

        boolean running = true;

        while (running) {
            int choice = eventView.showMainMenu();

            switch (choice) {
                // Show existing Event
                case 1 -> {
                    switch (eventView.showExistingEventMenu()) {
                        // Search by Title
                        case 1 -> {
                            String eventTitleInput = eventView.getEventNameByUser();
                            List<Event> foundEvent = eventRepository.findByName(eventTitleInput);
                            for (Event event : foundEvent) {
                                if (event != null) {
                                    eventView.printEvent(event);
                                }
                            }
                        }
                        // Search by ID
                        case 2 -> {
                            Integer eventIdInput = eventView.getEventIdByUser();
                            Event foundEvent = eventRepository.findById(eventIdInput);
                            if (foundEvent != null) {
                                eventView.printEvent(foundEvent);
                            }
                        }
                        // Search by Event Type
                        case 3 -> {
                            String eventTypeNameInput = eventView.getEventTypeNameByUser();
                            List<Event> foundEvent = eventRepository.findByType(eventTypeNameInput);
                            for (Event event : foundEvent) {
                                if (event != null) {
                                    eventView.printEvent(event);
                                }
                            }
                        }
                        // Show all
                        case 4 -> {
                            List<Event> foundEvents = eventRepository.findAll();
                            for (Event event : foundEvents) {
                                if (event != null) {
                                    eventView.printEvent(event);
                                }
                            }
                        }
                        case 0 -> running = false;
                    }
                }
                // Add new Event
                case 2 -> {
                    Event newEvent = new Event();
                    EventType newEventType = new EventType();
                    String eventTitleInput = eventView.getEventNameByUser();
                    String eventTypeNameInput = eventView.getEventTypeNameByUser();
                    newEventType.setEventTypeName(eventTypeNameInput);
                    String eventDateInput = eventView.getEventStartDateByUser();
                    String eventHourInput = eventView.getEventStartHourByUser();
                    String eventMinuteInput = eventView.getEventStartMinuteByUser();

                    String dateTimeString = eventDateInput + " " + eventHourInput + ":" + eventMinuteInput;
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd H:m");
                    LocalDateTime eventStart = LocalDateTime.parse(dateTimeString, formatter);
                    newEvent.setEventName(eventTitleInput);
                    newEvent.setEventType(newEventType);
                    newEvent.setEventStartsAtTs(eventStart);
                    eventRepository.save(newEvent);
                }
                // Edit existing event
                case 3 -> {
                    System.out.println("To be added");
                }
                // DElete Event
                case 4 -> {
                    List<Event> foundEvents = eventRepository.findAll();
                    if (foundEvents.isEmpty()) {
                        System.out.println("No events found");
                    } else {
                        eventRepository.findAll().forEach(eventView::printEvent);
                        Integer eventIdInput = eventView.getEventIdByUser();
                        eventRepository.remove(eventIdInput);
                    }

                }
                case 0 -> running = false;
                default -> {
                    System.out.println("Invalid choice");
                }
            }
        }

    }

}
