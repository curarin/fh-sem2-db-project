-- DDL

create sequence streetnumber_id_seq
    as integer;

alter sequence streetnumber_id_seq owner to bswe;

create table zip
(
    id       integer generated always as identity
        primary key,
    zip_code varchar not null
);

alter table zip
    owner to bswe;

create table town
(
    id        integer generated always as identity
        primary key,
    town_name varchar not null
);

alter table town
    owner to bswe;

create table country
(
    id           integer generated always as identity
        primary key,
    country_name varchar not null
);

alter table country
    owner to bswe;

create table event_type
(
    id   integer generated always as identity
        primary key,
    name varchar not null
);

alter table event_type
    owner to bswe;

create table publisher
(
    id   integer generated always as identity
        primary key,
    name varchar not null
);

alter table publisher
    owner to bswe;

create table book_genre
(
    id   integer generated always as identity
        primary key,
    name varchar not null
);

alter table book_genre
    owner to bswe;

create table author
(
    id          integer generated always as identity
        primary key,
    author_name varchar not null
);

alter table author
    owner to bswe;

create table book_location_floor
(
    id     integer generated always as identity
        primary key,
    number integer not null
);

alter table book_location_floor
    owner to bswe;

create table book_location_shelf
(
    id     integer generated always as identity
        primary key,
    number integer not null
);

alter table book_location_shelf
    owner to bswe;

create table event
(
    id                integer generated always as identity
        primary key,
    event_type_id     integer   not null
        references event_type,
    event_start_at_dt timestamp not null,
    name              varchar   not null
);

alter table event
    owner to bswe;

create table book
(
    isbn          varchar(17) not null
        primary key,
    book_genre_id integer     not null
        references book_genre,
    publisher_id  integer     not null
        references publisher,
    title         varchar     not null,
    year          date
);

alter table book
    owner to bswe;

create table book_location
(
    id                     integer generated always as identity
        primary key,
    book_location_floor_id integer not null
        references book_location_floor,
    book_location_shelf_id integer not null
        references book_location_shelf
);

alter table book_location
    owner to bswe;

create table book_stock_log
(
    id               integer     not null
        primary key,
    book_location_id integer
        references book_location,
    is_in_stock      boolean     not null,
    book_isbn        varchar(17) not null
        constraint book_stock_log_book_isbn_fk
            references book
);

alter table book_stock_log
    owner to bswe;

create table author_book_map
(
    book_isbn varchar(17) not null
        references book,
    author_id integer     not null
        references author,
    primary key (book_isbn, author_id)
);

alter table author_book_map
    owner to bswe;

create table event_book_map
(
    event_id  integer     not null
        references event,
    book_isbn varchar(17) not null
        references book,
    primary key (event_id, book_isbn)
);

alter table event_book_map
    owner to bswe;

create table street
(
    id     integer generated always as identity
        primary key,
    street varchar not null
);

alter table street
    owner to bswe;

create table customer
(
    id           integer generated always as identity
        primary key,
    first_name   varchar not null,
    last_name    varchar not null,
    country_id   integer not null
        references country,
    zip_id       integer not null
        references zip,
    town_id      integer not null
        references town,
    street_id    integer
        constraint customer_street_id_fk
            references street,
    streetnumber varchar(15)
);

alter table customer
    owner to bswe;

create table book_circulation_log
(
    id                    integer generated always as identity
        primary key,
    loan_starts_at_date   date not null,
    loan_ends_at_date     date,
    book_returned_at_date date,
    customerid            integer
        constraint book_circulation_log_customer_id_fk
            references customer,
    fk_stockid            integer
        constraint book_circulation_log_book_stock_log_id_fk
            references book_stock_log
);

alter table book_circulation_log
    owner to bswe;

create table customer_event_map
(
    customer_id integer not null
        references customer,
    event_id    integer not null
        references event,
    primary key (customer_id, event_id)
);

alter table customer_event_map
    owner to bswe;

-- add data

INSERT INTO public.author (author_name) VALUES ('Stephen King');

INSERT INTO public.author_book_map (book_isbn, author_id) VALUES ('9780385086952', 1);
INSERT INTO public.author_book_map (book_isbn, author_id) VALUES ('9780307743657', 1);
INSERT INTO public.author_book_map (book_isbn, author_id) VALUES ('9780307743664', 1);
INSERT INTO public.author_book_map (book_isbn, author_id) VALUES ('9780670813025', 1);
INSERT INTO public.author_book_map (book_isbn, author_id) VALUES ('9780345418864', 1);
INSERT INTO public.author_book_map (book_isbn, author_id) VALUES ('9780670813032', 1);
INSERT INTO public.author_book_map (book_isbn, author_id) VALUES ('9780670813049', 1);
INSERT INTO public.author_book_map (book_isbn, author_id) VALUES ('9780670813056', 1);
INSERT INTO public.author_book_map (book_isbn, author_id) VALUES ('9780670813063', 1);
INSERT INTO public.author_book_map (book_isbn, author_id) VALUES ('9780670813070', 1);
INSERT INTO public.author_book_map (book_isbn, author_id) VALUES ('9780670813087', 1);
INSERT INTO public.author_book_map (book_isbn, author_id) VALUES ('9780670813094', 1);
INSERT INTO public.author_book_map (book_isbn, author_id) VALUES ('9780670813100', 1);
INSERT INTO public.author_book_map (book_isbn, author_id) VALUES ('9780670813117', 1);
INSERT INTO public.author_book_map (book_isbn, author_id) VALUES ('9780670813124', 1);
INSERT INTO public.author_book_map (book_isbn, author_id) VALUES ('9780670813131', 1);
INSERT INTO public.author_book_map (book_isbn, author_id) VALUES ('9780670813148', 1);
INSERT INTO public.author_book_map (book_isbn, author_id) VALUES ('9780670813155', 1);
INSERT INTO public.author_book_map (book_isbn, author_id) VALUES ('9780670813162', 1);
INSERT INTO public.author_book_map (book_isbn, author_id) VALUES ('9780670813179', 1);
INSERT INTO public.author_book_map (book_isbn, author_id) VALUES ('9780670813186', 1);
INSERT INTO public.author_book_map (book_isbn, author_id) VALUES ('9780670813193', 1);
INSERT INTO public.author_book_map (book_isbn, author_id) VALUES ('9780670813209', 1);
INSERT INTO public.author_book_map (book_isbn, author_id) VALUES ('9780670813216', 1);
INSERT INTO public.author_book_map (book_isbn, author_id) VALUES ('9780670813223', 1);
INSERT INTO public.author_book_map (book_isbn, author_id) VALUES ('9780670813230', 1);
INSERT INTO public.author_book_map (book_isbn, author_id) VALUES ('9780670813247', 1);
INSERT INTO public.author_book_map (book_isbn, author_id) VALUES ('9780670813254', 1);
INSERT INTO public.author_book_map (book_isbn, author_id) VALUES ('9780670813261', 1);
INSERT INTO public.author_book_map (book_isbn, author_id) VALUES ('9780670813278', 1);
INSERT INTO public.author_book_map (book_isbn, author_id) VALUES ('9780670813285', 1);
INSERT INTO public.author_book_map (book_isbn, author_id) VALUES ('9780670813292', 1);
INSERT INTO public.author_book_map (book_isbn, author_id) VALUES ('9780670813308', 1);
INSERT INTO public.author_book_map (book_isbn, author_id) VALUES ('9780670813315', 1);
INSERT INTO public.author_book_map (book_isbn, author_id) VALUES ('9780670813322', 1);
INSERT INTO public.author_book_map (book_isbn, author_id) VALUES ('9780670813339', 1);
INSERT INTO public.author_book_map (book_isbn, author_id) VALUES ('9780670813346', 1);
INSERT INTO public.author_book_map (book_isbn, author_id) VALUES ('9780670813353', 1);
INSERT INTO public.author_book_map (book_isbn, author_id) VALUES ('9780670813360', 1);
INSERT INTO public.author_book_map (book_isbn, author_id) VALUES ('9780670813377', 1);
INSERT INTO public.author_book_map (book_isbn, author_id) VALUES ('9780670813384', 1);

INSERT INTO public.book (isbn, book_genre_id, publisher_id, title, year) VALUES ('9780385086952', 1, 1, 'Carrie', null);
INSERT INTO public.book (isbn, book_genre_id, publisher_id, title, year) VALUES ('9780307743657', 1, 2, 'Salem''s Lot', null);
INSERT INTO public.book (isbn, book_genre_id, publisher_id, title, year) VALUES ('9780307743664', 1, 2, 'The Shining', null);
INSERT INTO public.book (isbn, book_genre_id, publisher_id, title, year) VALUES ('9780670813025', 1, 5, 'The Stand', null);
INSERT INTO public.book (isbn, book_genre_id, publisher_id, title, year) VALUES ('9780345418864', 1, 2, 'The Dead Zone', null);
INSERT INTO public.book (isbn, book_genre_id, publisher_id, title, year) VALUES ('9780670813032', 1, 2, 'Firestarter', null);
INSERT INTO public.book (isbn, book_genre_id, publisher_id, title, year) VALUES ('9780670813049', 1, 2, 'Cujo', null);
INSERT INTO public.book (isbn, book_genre_id, publisher_id, title, year) VALUES ('9780670813056', 1, 2, 'Pet Sematary', null);
INSERT INTO public.book (isbn, book_genre_id, publisher_id, title, year) VALUES ('9780670813063', 1, 2, 'Christine', null);
INSERT INTO public.book (isbn, book_genre_id, publisher_id, title, year) VALUES ('9780670813070', 1, 2, 'The Eyes of the Dragon', null);
INSERT INTO public.book (isbn, book_genre_id, publisher_id, title, year) VALUES ('9780670813087', 1, 3, 'The Talisman', null);
INSERT INTO public.book (isbn, book_genre_id, publisher_id, title, year) VALUES ('9780670813094', 1, 2, 'It', null);
INSERT INTO public.book (isbn, book_genre_id, publisher_id, title, year) VALUES ('9780670813100', 1, 2, 'Misery', null);
INSERT INTO public.book (isbn, book_genre_id, publisher_id, title, year) VALUES ('9780670813117', 1, 2, 'The Tommyknockers', null);
INSERT INTO public.book (isbn, book_genre_id, publisher_id, title, year) VALUES ('9780670813124', 1, 2, 'The Dark Half', null);
INSERT INTO public.book (isbn, book_genre_id, publisher_id, title, year) VALUES ('9780670813131', 1, 3, 'Needful Things', null);
INSERT INTO public.book (isbn, book_genre_id, publisher_id, title, year) VALUES ('9780670813148', 1, 2, 'Gerald''s Game', null);
INSERT INTO public.book (isbn, book_genre_id, publisher_id, title, year) VALUES ('9780670813155', 1, 2, 'Dolores Claiborne', null);
INSERT INTO public.book (isbn, book_genre_id, publisher_id, title, year) VALUES ('9780670813162', 1, 2, 'Insomnia', null);
INSERT INTO public.book (isbn, book_genre_id, publisher_id, title, year) VALUES ('9780670813179', 1, 2, 'Rose Madder', null);
INSERT INTO public.book (isbn, book_genre_id, publisher_id, title, year) VALUES ('9780670813186', 1, 1, 'The Green Mile', null);
INSERT INTO public.book (isbn, book_genre_id, publisher_id, title, year) VALUES ('9780670813193', 1, 2, 'Desperation', null);
INSERT INTO public.book (isbn, book_genre_id, publisher_id, title, year) VALUES ('9780670813209', 1, 2, 'Bag of Bones', null);
INSERT INTO public.book (isbn, book_genre_id, publisher_id, title, year) VALUES ('9780670813216', 1, 2, 'The Girl Who Loved Tom Gordon', null);
INSERT INTO public.book (isbn, book_genre_id, publisher_id, title, year) VALUES ('9780670813223', 1, 2, 'Dreamcatcher', null);
INSERT INTO public.book (isbn, book_genre_id, publisher_id, title, year) VALUES ('9780670813230', 1, 2, 'From a Buick 8', null);
INSERT INTO public.book (isbn, book_genre_id, publisher_id, title, year) VALUES ('9780670813247', 1, 2, 'Lisey''s Story', null);
INSERT INTO public.book (isbn, book_genre_id, publisher_id, title, year) VALUES ('9780670813254', 1, 2, 'Duma Key', null);
INSERT INTO public.book (isbn, book_genre_id, publisher_id, title, year) VALUES ('9780670813261', 1, 2, 'Under the Dome', null);
INSERT INTO public.book (isbn, book_genre_id, publisher_id, title, year) VALUES ('9780670813278', 1, 2, '11/22/63', null);
INSERT INTO public.book (isbn, book_genre_id, publisher_id, title, year) VALUES ('9780670813285', 1, 2, 'Mr. Mercedes', null);
INSERT INTO public.book (isbn, book_genre_id, publisher_id, title, year) VALUES ('9780670813292', 1, 2, 'Finders Keepers', null);
INSERT INTO public.book (isbn, book_genre_id, publisher_id, title, year) VALUES ('9780670813308', 1, 2, 'End of Watch', null);
INSERT INTO public.book (isbn, book_genre_id, publisher_id, title, year) VALUES ('9780670813315', 1, 2, 'Joyland', null);
INSERT INTO public.book (isbn, book_genre_id, publisher_id, title, year) VALUES ('9780670813322', 1, 2, 'The Outsider', null);
INSERT INTO public.book (isbn, book_genre_id, publisher_id, title, year) VALUES ('9780670813339', 1, 2, 'Elevation', null);
INSERT INTO public.book (isbn, book_genre_id, publisher_id, title, year) VALUES ('9780670813346', 1, 2, 'Later', null);
INSERT INTO public.book (isbn, book_genre_id, publisher_id, title, year) VALUES ('9780670813353', 1, 2, 'Billy Summers', null);
INSERT INTO public.book (isbn, book_genre_id, publisher_id, title, year) VALUES ('9780670813360', 1, 2, 'Holly', null);
INSERT INTO public.book (isbn, book_genre_id, publisher_id, title, year) VALUES ('9780670813377', 1, 2, 'Fairy Tale', null);
INSERT INTO public.book (isbn, book_genre_id, publisher_id, title, year) VALUES ('9780670813384', 1, 2, 'Never Flinch', null);

INSERT INTO public.book_circulation_log (loan_starts_at_date, loan_ends_at_date, book_returned_at_date, customerid, fk_stockid) VALUES ('2026-03-01', '2026-03-09', null, 1, 1);
INSERT INTO public.book_circulation_log (loan_starts_at_date, loan_ends_at_date, book_returned_at_date, customerid, fk_stockid) VALUES ('2026-03-01', '2026-03-09', null, 1, 2);

INSERT INTO public.book_genre (name) VALUES ('Horror');
INSERT INTO public.book_genre (name) VALUES ('Thriller');
INSERT INTO public.book_genre (name) VALUES ('Fantasy');
INSERT INTO public.book_genre (name) VALUES ('Science Fiction');
INSERT INTO public.book_genre (name) VALUES ('Suspense');
INSERT INTO public.book_genre (name) VALUES ('Nonfiction');

INSERT INTO public.book_location (book_location_floor_id, book_location_shelf_id) VALUES (1, 1);
INSERT INTO public.book_location (book_location_floor_id, book_location_shelf_id) VALUES (1, 2);
INSERT INTO public.book_location (book_location_floor_id, book_location_shelf_id) VALUES (1, 3);
INSERT INTO public.book_location (book_location_floor_id, book_location_shelf_id) VALUES (2, 1);
INSERT INTO public.book_location (book_location_floor_id, book_location_shelf_id) VALUES (2, 2);
INSERT INTO public.book_location (book_location_floor_id, book_location_shelf_id) VALUES (2, 3);

INSERT INTO public.book_location_floor (number) VALUES (1);
INSERT INTO public.book_location_floor (number) VALUES (0);
INSERT INTO public.book_location_floor (number) VALUES (2);
INSERT INTO public.book_location_floor (number) VALUES (3);

INSERT INTO public.book_location_shelf (number) VALUES (1);
INSERT INTO public.book_location_shelf (number) VALUES (2);
INSERT INTO public.book_location_shelf (number) VALUES (3);

INSERT INTO public.book_stock_log (id, book_location_id, is_in_stock, book_isbn) VALUES (1, 6, true, '9780385086952');
INSERT INTO public.book_stock_log (id, book_location_id, is_in_stock, book_isbn) VALUES (2, 6, true, '9780307743657');
INSERT INTO public.book_stock_log (id, book_location_id, is_in_stock, book_isbn) VALUES (3, 6, true, '9780307743664');
INSERT INTO public.book_stock_log (id, book_location_id, is_in_stock, book_isbn) VALUES (4, 7, true, '9780670813025');
INSERT INTO public.book_stock_log (id, book_location_id, is_in_stock, book_isbn) VALUES (5, 7, true, '9780345418864');
INSERT INTO public.book_stock_log (id, book_location_id, is_in_stock, book_isbn) VALUES (6, 6, true, '9780670813032');
INSERT INTO public.book_stock_log (id, book_location_id, is_in_stock, book_isbn) VALUES (7, 6, true, '9780670813049');
INSERT INTO public.book_stock_log (id, book_location_id, is_in_stock, book_isbn) VALUES (8, 8, true, '9780670813056');
INSERT INTO public.book_stock_log (id, book_location_id, is_in_stock, book_isbn) VALUES (9, 8, true, '9780670813063');
INSERT INTO public.book_stock_log (id, book_location_id, is_in_stock, book_isbn) VALUES (10, 8, true, '9780670813070');
INSERT INTO public.book_stock_log (id, book_location_id, is_in_stock, book_isbn) VALUES (11, 8, true, '9780670813087');
INSERT INTO public.book_stock_log (id, book_location_id, is_in_stock, book_isbn) VALUES (12, 6, true, '9780670813094');
INSERT INTO public.book_stock_log (id, book_location_id, is_in_stock, book_isbn) VALUES (13, 6, true, '9780670813100');
INSERT INTO public.book_stock_log (id, book_location_id, is_in_stock, book_isbn) VALUES (14, 6, true, '9780670813117');
INSERT INTO public.book_stock_log (id, book_location_id, is_in_stock, book_isbn) VALUES (15, 6, true, '9780670813124');
INSERT INTO public.book_stock_log (id, book_location_id, is_in_stock, book_isbn) VALUES (16, 6, true, '9780670813131');
INSERT INTO public.book_stock_log (id, book_location_id, is_in_stock, book_isbn) VALUES (17, 6, true, '9780670813148');
INSERT INTO public.book_stock_log (id, book_location_id, is_in_stock, book_isbn) VALUES (18, 6, true, '9780670813155');
INSERT INTO public.book_stock_log (id, book_location_id, is_in_stock, book_isbn) VALUES (19, 6, true, '9780670813162');
INSERT INTO public.book_stock_log (id, book_location_id, is_in_stock, book_isbn) VALUES (20, 6, true, '9780670813179');
INSERT INTO public.book_stock_log (id, book_location_id, is_in_stock, book_isbn) VALUES (21, 6, true, '9780670813186');
INSERT INTO public.book_stock_log (id, book_location_id, is_in_stock, book_isbn) VALUES (22, 6, true, '9780670813193');
INSERT INTO public.book_stock_log (id, book_location_id, is_in_stock, book_isbn) VALUES (23, 6, true, '9780670813209');
INSERT INTO public.book_stock_log (id, book_location_id, is_in_stock, book_isbn) VALUES (24, 6, true, '9780670813216');
INSERT INTO public.book_stock_log (id, book_location_id, is_in_stock, book_isbn) VALUES (25, 6, true, '9780670813223');
INSERT INTO public.book_stock_log (id, book_location_id, is_in_stock, book_isbn) VALUES (26, 6, true, '9780670813230');
INSERT INTO public.book_stock_log (id, book_location_id, is_in_stock, book_isbn) VALUES (27, 6, true, '9780670813247');
INSERT INTO public.book_stock_log (id, book_location_id, is_in_stock, book_isbn) VALUES (28, 6, true, '9780670813254');
INSERT INTO public.book_stock_log (id, book_location_id, is_in_stock, book_isbn) VALUES (29, 6, true, '9780670813261');
INSERT INTO public.book_stock_log (id, book_location_id, is_in_stock, book_isbn) VALUES (30, 6, true, '9780670813278');
INSERT INTO public.book_stock_log (id, book_location_id, is_in_stock, book_isbn) VALUES (31, 6, true, '9780670813285');
INSERT INTO public.book_stock_log (id, book_location_id, is_in_stock, book_isbn) VALUES (32, 6, true, '9780670813292');
INSERT INTO public.book_stock_log (id, book_location_id, is_in_stock, book_isbn) VALUES (33, 6, true, '9780670813308');
INSERT INTO public.book_stock_log (id, book_location_id, is_in_stock, book_isbn) VALUES (34, 6, true, '9780670813315');
INSERT INTO public.book_stock_log (id, book_location_id, is_in_stock, book_isbn) VALUES (35, 6, true, '9780670813322');
INSERT INTO public.book_stock_log (id, book_location_id, is_in_stock, book_isbn) VALUES (36, 6, true, '9780670813339');
INSERT INTO public.book_stock_log (id, book_location_id, is_in_stock, book_isbn) VALUES (37, 6, true, '9780670813346');
INSERT INTO public.book_stock_log (id, book_location_id, is_in_stock, book_isbn) VALUES (38, 6, true, '9780670813353');
INSERT INTO public.book_stock_log (id, book_location_id, is_in_stock, book_isbn) VALUES (39, 6, true, '9780670813360');
INSERT INTO public.book_stock_log (id, book_location_id, is_in_stock, book_isbn) VALUES (40, 6, true, '9780670813377');
INSERT INTO public.book_stock_log (id, book_location_id, is_in_stock, book_isbn) VALUES (41, 6, true, '9780670813384');

INSERT INTO public.country (country_name) VALUES ('Austria');

INSERT INTO public.customer (first_name, last_name, country_id, zip_id, town_id, street_id, streetnumber) VALUES ('Max', 'Mustermann', 1, 3, 3, 1, '188');
INSERT INTO public.customer (first_name, last_name, country_id, zip_id, town_id, street_id, streetnumber) VALUES ('Alexander', 'Maier', 1, 3, 3, 1, '65');

INSERT INTO public.customer_event_map (customer_id, event_id) VALUES (1, 1);

INSERT INTO public.event (event_type_id, event_start_at_dt, name) VALUES (1, '2026-04-30 08:54:23.000000', 'Stephen King signiert');

INSERT INTO public.event_book_map (event_id, book_isbn) VALUES (1, '9780385086952');
INSERT INTO public.event_book_map (event_id, book_isbn) VALUES (1, '9780307743657');
INSERT INTO public.event_book_map (event_id, book_isbn) VALUES (1, '9780307743664');
INSERT INTO public.event_book_map (event_id, book_isbn) VALUES (1, '9780670813025');
INSERT INTO public.event_book_map (event_id, book_isbn) VALUES (1, '9780345418864');
INSERT INTO public.event_book_map (event_id, book_isbn) VALUES (1, '9780670813032');
INSERT INTO public.event_book_map (event_id, book_isbn) VALUES (1, '9780670813049');
INSERT INTO public.event_book_map (event_id, book_isbn) VALUES (1, '9780670813056');
INSERT INTO public.event_book_map (event_id, book_isbn) VALUES (1, '9780670813063');
INSERT INTO public.event_book_map (event_id, book_isbn) VALUES (1, '9780670813070');
INSERT INTO public.event_book_map (event_id, book_isbn) VALUES (1, '9780670813087');
INSERT INTO public.event_book_map (event_id, book_isbn) VALUES (1, '9780670813094');
INSERT INTO public.event_book_map (event_id, book_isbn) VALUES (1, '9780670813100');
INSERT INTO public.event_book_map (event_id, book_isbn) VALUES (1, '9780670813117');
INSERT INTO public.event_book_map (event_id, book_isbn) VALUES (1, '9780670813124');
INSERT INTO public.event_book_map (event_id, book_isbn) VALUES (1, '9780670813131');
INSERT INTO public.event_book_map (event_id, book_isbn) VALUES (1, '9780670813148');
INSERT INTO public.event_book_map (event_id, book_isbn) VALUES (1, '9780670813155');
INSERT INTO public.event_book_map (event_id, book_isbn) VALUES (1, '9780670813162');
INSERT INTO public.event_book_map (event_id, book_isbn) VALUES (1, '9780670813179');
INSERT INTO public.event_book_map (event_id, book_isbn) VALUES (1, '9780670813186');
INSERT INTO public.event_book_map (event_id, book_isbn) VALUES (1, '9780670813193');
INSERT INTO public.event_book_map (event_id, book_isbn) VALUES (1, '9780670813209');
INSERT INTO public.event_book_map (event_id, book_isbn) VALUES (1, '9780670813216');
INSERT INTO public.event_book_map (event_id, book_isbn) VALUES (1, '9780670813223');
INSERT INTO public.event_book_map (event_id, book_isbn) VALUES (1, '9780670813230');
INSERT INTO public.event_book_map (event_id, book_isbn) VALUES (1, '9780670813247');
INSERT INTO public.event_book_map (event_id, book_isbn) VALUES (1, '9780670813254');
INSERT INTO public.event_book_map (event_id, book_isbn) VALUES (1, '9780670813261');
INSERT INTO public.event_book_map (event_id, book_isbn) VALUES (1, '9780670813278');
INSERT INTO public.event_book_map (event_id, book_isbn) VALUES (1, '9780670813285');
INSERT INTO public.event_book_map (event_id, book_isbn) VALUES (1, '9780670813292');
INSERT INTO public.event_book_map (event_id, book_isbn) VALUES (1, '9780670813308');
INSERT INTO public.event_book_map (event_id, book_isbn) VALUES (1, '9780670813315');
INSERT INTO public.event_book_map (event_id, book_isbn) VALUES (1, '9780670813322');
INSERT INTO public.event_book_map (event_id, book_isbn) VALUES (1, '9780670813339');
INSERT INTO public.event_book_map (event_id, book_isbn) VALUES (1, '9780670813346');
INSERT INTO public.event_book_map (event_id, book_isbn) VALUES (1, '9780670813353');
INSERT INTO public.event_book_map (event_id, book_isbn) VALUES (1, '9780670813360');
INSERT INTO public.event_book_map (event_id, book_isbn) VALUES (1, '9780670813377');
INSERT INTO public.event_book_map (event_id, book_isbn) VALUES (1, '9780670813384');

INSERT INTO public.event_type (name) VALUES ('Reading');
INSERT INTO public.event_type (name) VALUES ('Signing');

INSERT INTO public.publisher (name) VALUES ('Doubleday');
INSERT INTO public.publisher (name) VALUES ('Scribner');
INSERT INTO public.publisher (name) VALUES ('Viking Press');
INSERT INTO public.publisher (name) VALUES ('Signet');
INSERT INTO public.publisher (name) VALUES ('New American Library');

INSERT INTO public.street (street) VALUES ('Herrnberg');
INSERT INTO public.street (street) VALUES ('Grazer Straße');
INSERT INTO public.street (street) VALUES ('Fürstenfeld');

INSERT INTO public.town (town_name) VALUES ('Graz');
INSERT INTO public.town (town_name) VALUES ('Wien');
INSERT INTO public.town (town_name) VALUES ('Großwilfersdorf');

INSERT INTO public.zip (zip_code) VALUES ('1010');
INSERT INTO public.zip (zip_code) VALUES ('8010');
INSERT INTO public.zip (zip_code) VALUES ('8263');
