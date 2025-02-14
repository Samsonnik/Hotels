CREATE TABLE hotel (
                      id int primary key,
                      name varchar(100) not null,
                      brand varchar(100) not null,
                      description varchar(500)
);

CREATE TABLE address (
                        id int primary key,
                        hotel_id int not null,
                        foreign key (hotel_id) references hotel(id),
                        housenumber varchar(40) not null,
                        street varchar(100) not null,
                        city varchar(100) not null,
                        country varchar(100),
                        postcode varchar(40)
);

CREATE TABLE contacts (
                         id int generated by default as identity primary key,
                         hotel_id int not null,
                         foreign key (hotel_id) references hotel(id),
                         phone varchar(60) not null,
                         email varchar(60) not null
);

CREATE TABLE arrivaltime (
                          id int generated by default as identity primary key,
                          hotel_id int not null,
                          foreign key (hotel_id) references hotel(id),
                          checkin varchar(40) not null,
                          checkout varchar(40)
);

CREATE TABLE amenities (
                             id int generated by default as identity primary key,
                             hotel_id int not null,
                             foreign key (hotel_id) references hotel(id),
                             amenities varchar(10000) not null
);