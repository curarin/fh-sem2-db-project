package at.fhburgenland.controller;

import at.fhburgenland.model.Customer;
import at.fhburgenland.model.Book;
import at.fhburgenland.model.Event;
import at.fhburgenland.model.EventType;
import at.fhburgenland.model.repository.interfaces.BookRepository;
import at.fhburgenland.model.repository.interfaces.CustomerRepository;
import at.fhburgenland.model.repository.interfaces.EventCustomerRepository;
import at.fhburgenland.model.repository.interfaces.EventRepository;
import at.fhburgenland.view.BookView;
import at.fhburgenland.view.CustomerEventView;
import at.fhburgenland.view.CustomerView;
import at.fhburgenland.view.EventView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Controller representation of Event - maps user input from view to repository methods.
 * Also consists of small business logic.
 */
public class EventController {
    private final EventRepository eventRepository;
    private final EventView eventView;
    private final BookRepository bookRepository;
    private final BookView bookView;
    private final EventCustomerRepository eventCustomerRepository;
    private final CustomerRepository customerRepository;
    private final CustomerView customerView;

    public EventController(EventRepository eventRepository, EventView eventView, BookRepository bookRepository, BookView bookView, EventCustomerRepository eventCustomerRepository, CustomerRepository customerRepository, CustomerView customerView) {
        this.eventRepository = eventRepository;
        this.eventView = eventView;
        this.bookRepository = bookRepository;
        this.bookView = bookView;
        this.eventCustomerRepository = eventCustomerRepository;
        this.customerRepository = customerRepository;
        this.customerView = customerView;
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
                            int eventIdInput = 0;
                            while (eventIdInput <= 0) {
                                eventIdInput = eventView.getEventIdByUser();
                            }
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
                        default -> System.out.println("Wrong choice. Try again.");
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

                    Set<Book> booksToBeAdded = new HashSet<>();
                    while (eventView.getUserChoiceForBookAddition()) {
                        String isbnInput = bookView.getIsbnByUser();
                        booksToBeAdded.add(bookRepository.findByIsbn(isbnInput));
                    }
                    newEvent.setBooks(booksToBeAdded);
                    eventRepository.save(newEvent);
                }
                // Edit existing event
                case 3 -> {
                    List<Event> foundEvents = eventRepository.findAll();
                    if (foundEvents.isEmpty()) {
                        System.out.println("No events found");
                    } else {
                        eventRepository.findAll().forEach(eventView::printEvent);
                        Integer eventIdInput = eventView.getEventIdByUser();
                        Event eventToBeEdited = eventRepository.findById(eventIdInput);
                        eventView.printEvent(eventToBeEdited);
                        switch (eventView.showEditOptionsMenu()) {
                            // Event Name
                            case 1 -> {
                                String newName = eventView.getEventNameByUser();
                                eventToBeEdited.setEventName(newName);
                                eventRepository.save(eventToBeEdited);
                            }
                            // Event Type Name
                            case 2 -> {
                                String newEventTypeName = eventView.getEventTypeNameByUser();
                                EventType newEventType = new EventType();
                                newEventType.setEventTypeName(newEventTypeName);
                                eventToBeEdited.setEventType(newEventType);
                                eventRepository.save(eventToBeEdited);
                            }
                            // Event Start Date
                            case 3 -> {
                                String eventDateInput = eventView.getEventStartDateByUser();
                                String eventHourInput = eventView.getEventStartHourByUser();
                                String eventMinuteInput = eventView.getEventStartMinuteByUser();

                                String dateTimeString = eventDateInput + " " + eventHourInput + ":" + eventMinuteInput;
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd H:m");
                                LocalDateTime newEventStart = LocalDateTime.parse(dateTimeString, formatter);
                                eventToBeEdited.setEventStartsAtTs(newEventStart);
                                eventRepository.save(eventToBeEdited);
                            }
                            case 0 -> running = false;
                            default -> System.out.println("Wrong choice. Try again.");
                        }
                    }
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
                // Add Customers to Event
                case 5 -> {
                    List<Event> foundEvents = eventRepository.findAll();

                    if (!foundEvents.isEmpty()) {
                        // print all events
                        eventView.printEventGrid(eventRepository.findAll().stream().toList());
                        // choose event to add customer to
                        Event selectedEvent = eventRepository.findById(eventView.chooseEventToAddUser());
                        if (selectedEvent != null) {
                            CustomerEventView.printPrologCustomerAddition();
                                do {
                                    Customer customer = CustomerController.selectCustomer(customerView, customerRepository);

                                    eventCustomerRepository.


                                    if (customer != null) {
                                        eventCustomerRepository.addCustomerToEvent(customer, selectedEvent);
                                        System.out.println("Customer added to event.");
                                    } else {
                                        System.out.println("Customer not found.");
                                    }
                                } while (eventView.getUserChoiceForCustomerAddition());



                        } else {
                            System.out.println("Event not found.");
                        }
                    } else {
                        System.out.println("No events found");
                    }
                }
                // Show Customers visiting Event
                case 6 -> {
                    List<Event> foundEvents = eventRepository.findAll();
                    if (foundEvents.isEmpty()) {
                        System.out.println("No events found");
                    } else {
                        eventView.printEventGrid(eventRepository.findAll().stream().toList());
                        Integer eventIdInput = eventView.getEventIdByUser();
                        List<Customer> customers = eventCustomerRepository.getCustomersByEvent(eventIdInput);
                        if (customers.isEmpty()) {
                            System.out.println("No customers found for this event.");
                        } else {
                            System.out.println("Customers visiting this event:");
                            eventView.printCustomerVisitingEventGrid(customers,
                                    eventRepository.findById(eventIdInput).getEventName());
                        }
                    }
                }
                // remove Customers visiting Event

                case 7 -> {
                    List<Event> foundEvents = eventRepository.findAll();

                    if (!foundEvents.isEmpty()) {
                        // print all events
                        eventView.printEventGrid(eventRepository.findAll().stream().toList());
                        // choose event to remove customer
                        Event selectedEvent = eventRepository.findById(eventView.chooseEventToRemoveUser());
                        if (selectedEvent != null) {
                            CustomerEventView.printPrologCustomerRemoval();
                            do {
                                Customer customer = CustomerController.selectCustomer(customerView, customerRepository);
                                if (customer != null) {
                                    eventCustomerRepository.removeCustomerFromEvent(customer, selectedEvent);
                                    System.out.println("Customer removed from event.");
                                } else {
                                    System.out.println("Customer not found.");
                                }
                            } while (eventView.getUserChoiceForCustomerRemoval());
                        } else {
                            System.out.println("Event not found.");
                        }
                    } else {
                        System.out.println("No events found");
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
