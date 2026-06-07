package at.fhburgenland.controller;

import at.fhburgenland.model.Book;
import at.fhburgenland.model.BookAuthor;
import at.fhburgenland.model.BookGenre;
import at.fhburgenland.model.BookPublisher;
import at.fhburgenland.model.repository.interfaces.BookRepository;
import at.fhburgenland.view.BookView;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BookController {

    private final BookRepository repository;
    private final BookView view;

    public BookController(BookRepository repository, BookView view) {
        this.repository = repository;
        this.view = view;
    }

    public void start() {

        boolean running = true;

        while (running) {

            int choice = view.showMainMenu();

            switch (choice) {
                case 1 -> {
                    // Show & Print books with various combinations of lookups
                    switch (view.showExistingBookMenu()) {
                        case 1 -> {
                            Book foundBook = repository.findByIsbn(view.getIsbnByUser());
                            if (foundBook != null) {
                                view.printBook(foundBook);
                            }
                        }
                        case 2 -> {
                            List<Book> booksByTitle = repository.findByBookName(view.getBookTitleByUser());
                            for (Book book : booksByTitle) {
                                if (book != null) {
                                    view.printBook(book);
                                }
                            }
                            view.printSearchStatistics(booksByTitle);
                        }
                        case 3 -> {
                            List<Book> booksByGenre = repository.findByGenre(view.getBookGenreByUser());
                            for (Book book : booksByGenre) {
                                if (book != null) {
                                    view.printBook(book);
                                }
                            }
                            view.printSearchStatistics(booksByGenre);
                        }
                        case 4 -> {
                            List<Book> booksByPublisher = repository.findByPublisher(view.getBookPublisherByUser());
                            for (Book book : booksByPublisher) {
                                if (book != null) {
                                    view.printBook(book);
                                }
                            }
                            view.printSearchStatistics(booksByPublisher);
                        }
                        case 5 -> {
                            List<Book> booksByAuthor = repository.findByAuthor(view.getBookAuthorByUser());
                            for (Book book : booksByAuthor) {
                                if (book != null) {
                                    view.printBook(book);
                                }
                            }
                            view.printSearchStatistics(booksByAuthor);
                        }
                        case 0 -> running = false;
                    }

                }
                case 2 -> {
                    // Add new book
                    String isbnInput = view.getIsbnByUser();
                    if (repository.findByIsbn(isbnInput) != null) {
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
                        Book book = new Book();
                        BookGenre bookGenre = new BookGenre();
                        BookPublisher bookPublisher = new BookPublisher();

                        bookGenre.setBookGenreName(bookGenreInput);
                        bookPublisher.setBookPublisherName(bookPublisherInput);

                        book.setBookTitle(bookTitleInput);
                        book.setIsbn(isbnInput);
                        book.setBookGenre(bookGenre);
                        book.setBookPublisher(bookPublisher);
                        book.setBookAuthors(bookAuthorsInput);

                        repository.save(book);
                        view.printBook(book);
                    }

                }
                case 3 -> {
                    // Edit existing book
                    // Enter ISBN
                    // What to change?
                }
                case 4 -> {
                    // Delete book
                    String isbnInput = view.getIsbnByUser();
                    Book bookToBeDeleted = repository.findByIsbn(isbnInput);
                    if (bookToBeDeleted == null) {
                        System.out.println("Book does not exist");
                        running = false;
                    } else {
                        repository.remove(bookToBeDeleted.getIsbn());
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