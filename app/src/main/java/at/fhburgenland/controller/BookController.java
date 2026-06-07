package at.fhburgenland.controller;

import at.fhburgenland.model.Book;
import at.fhburgenland.model.BookAuthor;
import at.fhburgenland.model.BookGenre;
import at.fhburgenland.model.BookPublisher;
import at.fhburgenland.model.repository.interfaces.BookRepository;
import at.fhburgenland.view.BookView;

import java.util.HashSet;
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
                    String isbnInput = view.getIsbnByUser();
                    view.printBook(repository.findByIsbn(isbnInput));
                }
                case 2 -> {
                    Book book = new Book();
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
                        book.setBookTitle(bookTitleInput);
                        book.setIsbn(isbnInput);

                        BookGenre bookGenre = new BookGenre();
                        bookGenre.setBookGenreName(bookGenreInput);
                        book.setBookGenre(bookGenre);
                        BookPublisher bookPublisher = new BookPublisher();
                        bookPublisher.setBookPublisherName(bookPublisherInput);
                        book.setBookPublisher(bookPublisher);
                        book.setBookAuthors(bookAuthorsInput);
                        repository.save(book);
                    }

                }
                case 3 -> {
                    // Edit existing book
                    // Enter ISBN
                    // What to change?
                }
                case 4 -> {
                    // Delete book
                    // Enter ISBN
                    // DELETE
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