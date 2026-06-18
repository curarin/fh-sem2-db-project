drop table author_book_map;

drop table book_author;

drop table customercard;

drop table customer_event_map;

drop table book_event_map;

drop table event;

drop table event_type;

drop table book_circulation_log;

drop table customer;

drop table city;

drop table country;

drop table street;

drop table town;

drop table zip;

drop table book_stock_log;

drop table book;

drop table book_genre;

drop table book_publisher;

drop table book_location;

drop table book_location_floor;

drop table book_location_shelf;

drop sequence if exists city_city_id_seq cascade;

drop sequence if exists country_id_seq cascade;

drop sequence if exists street_id_seq cascade;

drop sequence if exists town_id_seq cascade;

drop sequence if exists zip_id_seq cascade;

drop sequence if exists customer_customer_id_seq cascade;

drop sequence if exists customercard_customer_card_id_seq cascade;

drop sequence if exists book_circulation_log_book_circulation_log_id_seq cascade;

