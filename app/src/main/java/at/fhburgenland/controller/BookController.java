package at.fhburgenland.controller;

import at.fhburgenland.model.*;
import at.fhburgenland.model.repository.interfaces.BookRepository;
import at.fhburgenland.view.BookView;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Controller representation of book - maps user input from view to repository methods.
 * Also consists of small business logic.
 */
public class BookController {

    private final BookRepository bookRepository;
    private final BookView view;

    public BookController(BookRepository repository, BookView view) {
        this.bookRepository = repository;
        this.view = view;
    }

    /**
     * Run the book controller layer inside a loop.
     */
    public void start() {

        boolean running = true;

        while (running) {

            int choice = view.showMainMenu();

            switch (choice) {
                case 1 -> {
                    // Show & Print books with various combinations of lookups
                    switch (view.showExistingBookMenu()) {
                        case 1 -> {
                            String isbn = view.getIsbnByUser();
                            Book foundBook = bookRepository.findByIsbn(isbn);
                            if (foundBook != null) {
                                view.printBook(foundBook);
                                view.printStockStatistics(bookRepository.findStockByIsbn(isbn));
                            }
                        }
                        case 2 -> {
                            List<Book> booksByTitle = bookRepository.findByBookName(view.getBookTitleByUser());
                            for (Book book : booksByTitle) {
                                if (book != null) {
                                    view.printBook(book);
                                }
                            }
                            view.printSearchStatistics(booksByTitle);
                        }
                        case 3 -> {
                            List<Book> booksByGenre = bookRepository.findByGenre(view.getBookGenreByUser());
                            for (Book book : booksByGenre) {
                                if (book != null) {
                                    view.printBook(book);
                                }
                            }
                            view.printSearchStatistics(booksByGenre);
                        }
                        case 4 -> {
                            List<Book> booksByPublisher = bookRepository.findByPublisher(view.getBookPublisherByUser());
                            for (Book book : booksByPublisher) {
                                if (book != null) {
                                    view.printBook(book);
                                }
                            }
                            view.printSearchStatistics(booksByPublisher);
                        }
                        case 5 -> {
                            List<Book> booksByAuthor = bookRepository.findByAuthor(view.getBookAuthorByUser());
                            for (Book book : booksByAuthor) {
                                if (book != null) {
                                    view.printBook(book);
                                }
                            }
                            view.printSearchStatistics(booksByAuthor);
                        }
                        case 6 -> {
                            // Filter by Stock State true / false
                            List<Book> booksByStockLockState = bookRepository.findByStockState(view.getBookStockStateByUser());
                            for (Book book : booksByStockLockState) {
                                if (book != null) {
                                    view.printBook(book);
                                }
                            }
                            view.printSearchStatistics(booksByStockLockState);
                        }
                        case 0 -> running = false;
                        default -> System.out.println("Invalid choice.");
                    }

                }
                case 2 -> {
                    // Add new book
                    String isbnInput = view.getIsbnByUser();
                    if (bookRepository.findByIsbn(isbnInput) != null) {
                        System.out.println("Book already exists");
                        running = false;
                    } else {
                        String bookTitleInput = view.getBookTitleByUser();
                        String bookGenreInput = view.getBookGenreByUser();
                        String bookPublisherInput = view.getBookPublisherByUser();
                        Set<BookAuthor> bookAuthorsInput = new HashSet<>();
                        boolean anotherAuthorWanted = true;
                        while (anotherAuthorWanted) {
                            BookAuthor currentBookAuthor = new BookAuthor();
                            currentBookAuthor.setBookAuthorName(view.getBookAuthorByUser());
                            bookAuthorsInput.add(currentBookAuthor);
                            anotherAuthorWanted = view.getBookAuthorChoiceByUser();
                        }
                        int bookLocationFloorInput = 0;
                        while (bookLocationFloorInput <= 0) {
                            bookLocationFloorInput = view.getBookLocationFloorByUser();
                        }
                        int bookLocationShelfInput = 0;
                        while (bookLocationShelfInput <= 0) {
                            bookLocationShelfInput = view.getBookLocationShelfByUser();
                        }
                        int bookCounter = 0;
                        while (bookCounter <= 0) {
                            bookCounter = view.getBookCountByUser(bookLocationFloorInput, bookLocationShelfInput);
                        }

                        Book book = new Book();
                        BookGenre bookGenre = new BookGenre();
                        BookPublisher bookPublisher = new BookPublisher();

                        BookLocation bookLocation = new BookLocation();
                        BookLocationFloor bookLocationFloor = new BookLocationFloor();
                        BookLocationShelf bookLocationShelf = new BookLocationShelf();

                        bookLocationFloor.setBookLocationFloorNumber(bookLocationFloorInput);
                        bookLocationShelf.setBookLocationShelfNumber(bookLocationShelfInput);
                        bookLocation.setBookLocationFloor(bookLocationFloor);
                        bookLocation.setBookLocationShelf(bookLocationShelf);

                        bookGenre.setBookGenreName(bookGenreInput);
                        bookPublisher.setBookPublisherName(bookPublisherInput);

                        book.setBookTitle(bookTitleInput);
                        book.setIsbn(isbnInput);
                        book.setBookGenre(bookGenre);
                        book.setBookPublisher(bookPublisher);
                        book.setBookAuthors(bookAuthorsInput);

                        bookRepository.save(book);
                        bookRepository.saveBookCopyCount(book, bookCounter, bookLocation);
                        view.printBook(book);
                    }
                }
                case 3 -> {
                    // Edit existing book
                    String isbnInput = view.getIsbnByUser();
                    Book bookToBeEdited = bookRepository.findByIsbn(isbnInput);
                    view.printBook(bookToBeEdited);
                    switch (view.showEditOptionsMenu()) {
                        case 1 -> {
                            String bookTitleInput = view.getBookTitleByUser();
                            bookToBeEdited.setBookTitle(bookTitleInput);
                            bookRepository.save(bookToBeEdited);
                        }
                        case 2 -> {
                            // Genre
                            BookGenre updatedBookGenre = new BookGenre();
                            updatedBookGenre.setBookGenreName(view.getBookGenreByUser());
                            bookToBeEdited.setBookGenre(updatedBookGenre);
                            bookRepository.save(bookToBeEdited);
                        }
                        case 3 -> {
                            // Author
                            Set<BookAuthor> bookAuthorsInput = new HashSet<>();
                            boolean anotherAuthorWanted = true;
                            while (anotherAuthorWanted) {
                                BookAuthor currentBookAuthor = new BookAuthor();
                                currentBookAuthor.setBookAuthorName(view.getBookAuthorByUser());
                                bookAuthorsInput.add(currentBookAuthor);
                                anotherAuthorWanted = view.getBookAuthorChoiceByUser();
                            }
                            bookToBeEdited.setBookAuthors(bookAuthorsInput);
                            bookRepository.save(bookToBeEdited);
                        }
                        case 4 -> {
                            // Publisher
                            BookPublisher updatedBookPublisher = new BookPublisher();
                            updatedBookPublisher.setBookPublisherName(view.getBookPublisherByUser());
                            bookToBeEdited.setBookPublisher(updatedBookPublisher);
                            bookRepository.save(bookToBeEdited);
                        }
                        default -> System.out.println("Invalid choice.");
                    }
                }
                case 4 -> {
                    // Delete book
                    String isbnInput = view.getIsbnByUser();
                    Book bookToBeDeleted = bookRepository.findByIsbn(isbnInput);
                    if (bookToBeDeleted == null) {
                        System.out.println("Book does not exist");
                    } else {
                        boolean inStock = bookRepository.findStockByIsbn(isbnInput).stream().anyMatch(BookStockLog::getBookIsInStock);
                        boolean inEvent = !bookToBeDeleted.getBookEvents().isEmpty();
                        boolean inCirculation = bookRepository.isBookInCirculation(isbnInput);

                        if (inStock) {
                            view.printRemovalError("Book is still in stock.");
                            if (view.getRemoveStockChoiceByUser()) {
                                bookRepository.removeStock(isbnInput);
                                inStock = false;
                            }
                        }

                        if (!inStock) {
                            if (inEvent) {
                                view.printRemovalError("Book is involved in an event.");
                            } else if (inCirculation) {
                                view.printRemovalError("Book is in circulation log.");
                            } else {
                                bookRepository.remove(bookToBeDeleted.getIsbn());
                                System.out.println("Book removed successfully.");
                            }
                        }
                    }
                }
                case 0 -> {
                    running = false;
                }
                default -> {
                    System.out.println("Invalid choice");
                }
            }
        }
    }
}