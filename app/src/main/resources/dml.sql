INSERT INTO book_genre (book_genre_id, book_genre_name)
VALUES (1, 'Fiction'),
       (2, 'Non-Fiction'),
       (3, 'Science Fiction'),
       (4, 'Mystery'),
       (5, 'Biography'),
       (6, 'Horror'),
       (7, 'Thriller'),
       (8, 'Drama');

INSERT INTO book_author (book_author_id, book_author_name)
VALUES (1, 'George Orwell'),
       (2, 'J.K. Rowling'),
       (3, 'Isaac Asimov'),
       (4, 'Agatha Christie'),
       (5, 'Walter Isaacson'),
       (6, 'Stephen King'),
       (7, 'Peter Straub');

INSERT INTO book_publisher (book_publisher_id, book_publisher_name)
VALUES (1, 'Penguin Books'),
       (2, 'Bloomsbury Publishing'),
       (3, 'Doubleday'),
       (4, 'HarperCollins'),
       (5, 'Simon & Schuster'),
       (6, 'Scribner');

INSERT INTO book_location_floor (book_location_floor_id, book_location_floor_number)
VALUES (1, 1),
       (2, 2),
       (3, 3);

INSERT INTO book_location_shelf (book_location_shelf_id, book_location_shelf_number)
VALUES (1, 101),
       (2, 102),
       (3, 201),
       (4, 202),
       (5, 301);

INSERT INTO event_type (event_type_id, event_type_name)
VALUES (1, 'Book Reading'),
       (2, 'Author Meet & Greet'),
       (3, 'Workshop'),
       (4, 'Exhibition');

-- ------------------------------------------------------------
-- Address tables
-- ------------------------------------------------------------

INSERT INTO country (id, country_name)
VALUES (1, 'Austria'),
       (2, 'Germany'),
       (3, 'Switzerland'),
       (4, 'United Kingdom'),
       (5, 'United States');

INSERT INTO city (city_id, city_name)
VALUES (1, 'Wien'),
       (2, 'Graz'),
       (3, 'Eisenstadt'),
       (4, 'Pinkafeld'),
       (5, 'Oberwart'),
       (6, 'Jennersdorf'),
       (7, 'Jois');

INSERT INTO town (id, town_name)
VALUES (1, 'Pinkafeld'),
       (2, 'Graz'),
       (3, 'Oberwart'),
       (4, 'Jois'),
       (5, 'Wien');

INSERT INTO street (id, street)
VALUES (1, 'Kärntner Straße'),
       (2, 'Unter den Linden'),
       (3, 'Bahnhofstrasse'),
       (4, 'Waltendorfer Hauptstrasse'),
       (5, 'Ilz');

INSERT INTO zip (id, zip_code)
VALUES (1, '1010'),
       (2, '8010'),
       (3, '7000'),
       (4, '7423'),
       (5, '10001');

-- ------------------------------------------------------------
-- Customers
-- ------------------------------------------------------------

INSERT INTO customer (customer_id, first_name, last_name, country_id, city_id, zip_id, town_id, street_id, streetnumber)
VALUES (1, 'Anna', 'Müller', 1, 1, 1, 1, 1, '12A'),
       (2, 'Thomas', 'Schmidt', 2, 2, 2, 2, 2, '5'),
       (3, 'Sophie', 'Weber', 1, 3, 3, 3, 3, '78'),
       (4, 'Johann', 'Schweinzer', 1, 4, 4, 4, 4, '42'),
       (5, 'Lisa', 'Dietz', 1, 5, 5, 5, 5, '100'),
       (6, 'Markus', 'Bauer', 1, 1, 1, 1, 1, '3B'),
       (7, 'Laura', 'Fischer', 1, 1, 1, 1, 2, '17'),
       (8, 'David', 'Zollner', 1, 4, 4, 4, 4, '9C'),
       (9, 'Julia', 'Hoffmann', 1, 2, 2, 2, 2, '22'),
       (10, 'Michael', 'Braun', 1, 5, 5, 5, 5, '55');

-- ------------------------------------------------------------
-- Customer Cards  (one per customer)
-- ------------------------------------------------------------

INSERT INTO CustomerCard (customer_card_id, customer_id)
VALUES (10, 1),
       (9, 2),
       (8, 3),
       (7, 4),
       (6, 5),
       (5, 6),
       (4, 7),
       (3, 8),
       (2, 9),
       (1, 10);

-- ------------------------------------------------------------
-- Books
-- ------------------------------------------------------------

INSERT INTO book (isbn, book_title, book_genre_id, book_publisher_id)
VALUES ('9780451524935', '1984', 1, 1),
       ('9780747532699', 'Harry Potter and the Philosopher''s Stone', 1, 2),
       ('9780385333481', 'Foundation', 3, 3),
       ('9780062073488', 'Murder on the Orient Express', 4, 4),
       ('9781451648539', 'Steve Jobs', 5, 5),
       ('9780451524942', 'Animal Farm', 1, 1),
       ('9780747532705', 'Harry Potter and the Chamber of Secrets', 1, 2),
       ('9780385333498', 'Foundation and Empire', 3, 3),
       ('9780062073495', 'And Then There Were None', 4, 4),
       ('9781451648546', 'Leonardo da Vinci', 5, 5),
       ('9781668233092', 'Other Worlds Than These', 6, 6),
       ('9783641153434', 'The Stand', 6, 6),
       ('9783641229351', 'Der Outsider', 6, 6),
       ('9783641035693', 'Needful Things', 6, 6);


-- ------------------------------------------------------------
-- Author ↔ Book mapping
-- ------------------------------------------------------------

INSERT INTO author_book_map (book_author_id, isbn)
VALUES (1, '9780451524935'),
       (1, '9780451524942'),
       (2, '9780747532699'),
       (2, '9780747532705'),
       (3, '9780385333481'),
       (3, '9780385333498'),
       (4, '9780062073488'),
       (4, '9780062073495'),
       (5, '9781451648539'),
       (5, '9781451648546'),
       (6, '9781668233092'),
       (7, '9781668233092'),
       (6, '9783641153434'),
       (6, '9783641229351'),
       (6, '9783641035693');

------------------------------------------------------------
-- Book locations  (floor + shelf combos)
-- ------------------------------------------------------------

INSERT INTO book_location (book_location_id, book_location_floor_id, book_location_shelf_id)
VALUES (1, 1, 1),
       (2, 1, 2),
       (3, 2, 3),
       (4, 2, 4),
       (5, 3, 5),
       (6, 1, 1),
       (7, 1, 2),
       (8, 2, 3),
       (9, 2, 4),
       (10, 3, 5);

-- ------------------------------------------------------------
-- Book stock log  (one stock entry per book copy)
-- ------------------------------------------------------------

INSERT INTO book_stock_log (book_stock_log_id, book_is_in_stock, isbn, book_location_id)
VALUES (1, TRUE, '9780451524935', 1),
       (2, FALSE, '9780451524935', 2), -- copy 2 is on loan
       (3, TRUE, '9780747532699', 3),
       (4, TRUE, '9780385333481', 4),
       (5, FALSE, '9780062073488', 5), -- on loan
       (6, TRUE, '9781451648539', 6),
       (7, TRUE, '9780451524942', 7),
       (8, TRUE, '9780747532705', 8),
       (9, FALSE, '9780385333498', 9), -- on loan
       (10, TRUE, '9780062073495', 10),
       (11, TRUE, '9781668233092', 5),
       (12, TRUE, '9781668233092', 5),
       (13, TRUE, '9781668233092', 5),
       (14, TRUE, '9781668233092', 5),
       (15, TRUE, '9781668233092', 5),
       (16, TRUE, '9781668233092', 5),
       (17, TRUE, '9783641153434', 6),
       (18, TRUE, '9783641153434', 6),
       (19, TRUE, '9783641153434', 6),
       (20, TRUE, '9783641153434', 6),
       (21, TRUE, '9783641153434', 6),
       (22, TRUE, '9783641229351', 7),
       (23, TRUE, '9783641229351', 7),
       (24, TRUE, '9783641035693', 8),
       (25, TRUE, '9783641035693', 8),
       (26, TRUE, '9783641035693', 8),
       (27, TRUE, '9783641035693', 8),
       (28, TRUE, '9783641035693', 8),
       (29, TRUE, '9783641035693', 8);


-- ------------------------------------------------------------
-- Events
-- ------------------------------------------------------------

INSERT INTO event (event_id, event_name, event_starts_at_ts, event_type_id)
VALUES (1, 'Orwell Reading Night', '2026-07-10 19:00:00', 1),
       (2, 'Meet J.K. Rowling', '2026-07-15 14:00:00', 2),
       (3, 'Sci-Fi Writing Workshop', '2026-08-05 10:00:00', 3),
       (4, 'Classic Mystery Exhibition', '2026-08-20 09:00:00', 4),
       (5, 'Biography Book Club', '2026-09-01 18:00:00', 1);

-- ------------------------------------------------------------
-- Customer ↔ Event registrations
-- ------------------------------------------------------------

INSERT INTO customer_event_map (customer_id, event_id)
VALUES (1, 1),
       (2, 1),
       (3, 1),
       (1, 2),
       (4, 2),
       (5, 2),
       (6, 3),
       (7, 3),
       (8, 4),
       (9, 4),
       (10, 4),
       (2, 5),
       (5, 5),
       (6, 5);

-- ------------------------------------------------------------
-- Book ↔ Event mapping (books featured at events)
-- ------------------------------------------------------------

INSERT INTO book_event_map (book_event_id, isbn)
VALUES (1, '9780451524935'),
       (1, '9780451524942'),
       (2, '9780747532699'),
       (2, '9780747532705'),
       (3, '9780385333481'),
       (3, '9780385333498'),
       (4, '9780062073488'),
       (4, '9780062073495'),
       (5, '9781451648539'),
       (5, '9781451648546');

-- ------------------------------------------------------------
-- Book circulation log  (loans)
-- ------------------------------------------------------------

INSERT INTO book_circulation_log
(book_circulation_log_id, loan_starts_at_date, loan_ends_at_date, book_returned_at_date, customer_id, fk_stockid)
VALUES
    -- Active loans (not yet returned)
    (1, '2026-06-01', '2026-06-15', NULL, 2, 2),         -- Customer 2 has copy of 1984, overdue
    (2, '2026-06-10', '2026-06-24', NULL, 5, 5),         -- Customer 5 has Orient Express, due soon
    (3, '2026-06-12', '2026-06-26', NULL, 7, 9),         -- Customer 7 has Foundation+Empire

    -- Returned loans
    (4, '2026-05-01', '2026-05-15', '2026-05-14', 1, 1), -- Returned on time
    (5, '2026-05-10', '2026-05-24', '2026-05-24', 3, 3), -- Returned on due date
    (6, '2026-05-20', '2026-06-03', '2026-06-05', 4, 4), -- Returned 2 days late
    (7, '2026-04-01', '2026-04-15', '2026-04-13', 6, 6), -- Returned early
    (8, '2026-04-10', '2026-04-24', '2026-04-24', 8, 7),
    (9, '2026-03-15', '2026-03-29', '2026-03-28', 9, 8),
    (10, '2026-03-01', '2026-03-15', '2026-03-16', 10, 10); -- 1 day late

COMMIT;
