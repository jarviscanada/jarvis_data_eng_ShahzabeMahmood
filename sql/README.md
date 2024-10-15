# Introduction

# SQL Quries

###### Table Setup (DDL)

-- Table for cd.members

CREATE TABLE cd.members
(
	memid INTEGER NOT NULL,
	surname VARCHAR(200) NOT NULL, 
	firstname VARCHAR(200) NOT NULL,
	address VARCHAR(200) NOT NULL,
	zipcode INTEGER,
	telephone VARCHAR(20),
	recommendedby INTEGER,
	joindate TIMESTAMP, 
	CONSTRAINT pk_members PRIMARY KEY (memid),
	CONSTRAINT fk_members FOREIGN KEY (recommendedby) REFERENCES cd.members(memid) 

);


-- Table for cd.bookings

CREATE TABLE cd.bookings 
(
	bookid INTEGER,
	facid INTEGER,
	memid INTEGER,
	starttime timestamp, 
	slots integer,
	CONSTRAINT pk_bookings PRIMARY KEY (bookid),
	CONSTRAINT fk_facid FOREIGN KEY (facid) REFERENCES cd.facilities(facid),
	CONSTRAINT fk_memid FOREIGN KEY (memid) REFERENCES cd.members(memid)
);


-- Table for cd.facilities

CREATE TABLE cd.facilities (
    facid INTEGER,
    name VARCHAR(100),
    membercost NUMERIC,
    guestcost NUMERIC,
    initialoutlay NUMERIC,
    monthlymaintenance NUMERIC,
    CONSTRAINT pk_facilities PRIMARY KEY (facid)
);




###### Question 1: Insert new facility into database 

insert into cd.facilities
(facid, name, membercost, guestcost, initialoutlay, monthlymaintenance)
values (9, 'Spa', 20, 30, 100000, 800)


###### Questions 2: Lorem ipsum...



