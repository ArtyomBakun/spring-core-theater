create table auditorium (
   name varchar(50) PRIMARY KEY,
   numberOfSeats bigint,
   vips varchar(250)
);

create table event (
   id bigint IDENTITY PRIMARY KEY,
   name varchar(50),
   basePrice double,
   eventRating varchar(50)
);

create table eventAirDate (
   eventId bigint,
   airDate timestamp
);

create table eventAuditorium (
   eventId bigint,
   airDate timestamp,
   auditoriumName varchar(50)
);

create table ticket (
   id bigint IDENTITY PRIMARY KEY,
   userId bigint,
   eventId bigint,
   dateTime timestamp,
   auditoriumName varchar(50),
   seat bigint,
   booked boolean default false
);

create table user (
   id bigint IDENTITY PRIMARY KEY,
   firstName varchar(50),
   lastName varchar(50),
   email varchar(50),
   birthday timestamp
);

create table eventStatistic (
   name varchar(250),
   getByNameCalls bigint default 0,
   bookTimes bigint default 0,
   queryPriceCalls bigint default 0
);

create table totalCalls (
   name varchar(250),
   userId varchar(50),
   calls bigint default 0
);
