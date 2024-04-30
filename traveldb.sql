DROP TABLE Stay_At;
DROP TABLE Knows_About;
DROP TABLE Wants_to_Visit;
DROP TABLE Can_do_at;
DROP TABLE Interested_in;
DROP TABLE Activity;
DROP TABLE Stops_At_Accommodation;
DROP TABLE Accommodation_Offers;
DROP TABLE Stops_At_Attraction;
DROP TABLE Public_Transportation;
DROP TABLE Client;
DROP TABLE Client_2;
DROP TABLE Client_1;
DROP TABLE Pass_Through;
DROP TABLE Visits;
DROP TABLE Guides;
DROP TABLE Nature;
DROP TABLE Heritage;
DROP TABLE Shopping;
DROP TABLE Restaurant;
DROP TABLE Attraction_Located_In_Main;
DROP TABLE Destination;
DROP TABLE Tour_Guide_Main;
DROP TABLE Tour_Guide_1;
DROP TABLE Tour_Main;
DROP TABLE Tour_2;
DROP TABLE Tour_1;



CREATE TABLE Tour_1
(start_time TIMESTAMP,
end_time TIMESTAMP,
duration INTEGER,
PRIMARY KEY (start_time, end_time));


CREATE TABLE Tour_2
(start_time TIMESTAMP,
end_time TIMESTAMP,
refundable INTEGER,
PRIMARY KEY (start_time, end_time));

CREATE TABLE Tour_Main
(tourID INTEGER PRIMARY KEY,
tour_name VARCHAR(100),
price NUMERIC(8, 2),
space_available INTEGER,
start_time TIMESTAMP,
end_time TIMESTAMP,
UNIQUE (tour_name, start_time, end_time),
FOREIGN KEY (start_time, end_time) references Tour_1,
FOREIGN KEY (start_time, end_time) references Tour_2);

CREATE TABLE Tour_Guide_1
(experience INTEGER PRIMARY KEY,
salary NUMERIC(8, 2));

CREATE TABLE Tour_Guide_Main
(guideID INTEGER PRIMARY KEY,
guide_name VARCHAR(100),
age INTEGER,
experience INTEGER,
FOREIGN KEY (experience) references Tour_Guide_1);

CREATE TABLE Destination
(dest_name VARCHAR(100),
 parent_region VARCHAR(100),
 administrative_unit VARCHAR(100),
 PRIMARY KEY(dest_name, parent_region));

CREATE TABLE Attraction_Located_In_Main
(attr_name VARCHAR(100),
 latitude FLOAT,
 longitude FLOAT,
 cost NUMERIC(8, 2),
 open_time TIMESTAMP,
 close_time TIMESTAMP,
 website VARCHAR(100),
 type VARCHAR(100),
 minimum_age INTEGER,
 targeted_demographic VARCHAR(100),
 dest_name VARCHAR(100) NOT NULL,
 parent_region VARCHAR(100) NOT NULL,
 PRIMARY KEY(attr_name, latitude, longitude),
 FOREIGN KEY (dest_name, parent_region) references Destination);

CREATE TABLE Restaurant
(attr_name VARCHAR(100),
latitude FLOAT,
longitude FLOAT,
cuisine VARCHAR(100),
PRIMARY KEY(attr_name, latitude, longitude),
FOREIGN KEY (attr_name, latitude, longitude) references Attraction_Located_In_Main
);

CREATE TABLE Shopping
(attr_name VARCHAR(100),
latitude FLOAT,
longitude FLOAT,
merchandise VARCHAR(100),
shop_num INTEGER,
PRIMARY KEY(attr_name, latitude, longitude),
FOREIGN KEY (attr_name, latitude, longitude) references Attraction_Located_In_Main
);

CREATE TABLE Heritage
(attr_name VARCHAR(100),
latitude FLOAT,
longitude FLOAT,
completion_year INTEGER,
PRIMARY KEY(attr_name, latitude, longitude),
FOREIGN KEY (attr_name, latitude, longitude) references Attraction_Located_In_Main
);

CREATE TABLE Nature
(attr_name VARCHAR(100),
latitude FLOAT,
longitude FLOAT,
geographic_feature VARCHAR(100),
PRIMARY KEY(attr_name, latitude, longitude),
FOREIGN KEY (attr_name, latitude, longitude) references Attraction_Located_In_Main
);

CREATE TABLE Guides
(tourID INTEGER,
guideID INTEGER,
PRIMARY KEY(tourID, guideID),
FOREIGN KEY (tourID) references Tour_Main,
FOREIGN KEY (guideID) references Tour_Guide_Main);

CREATE TABLE Visits
(tourID INTEGER,
attr_name VARCHAR(100),
latitude FLOAT,
longitude FLOAT,
PRIMARY KEY (tourID, attr_name, latitude, longitude),
FOREIGN KEY (tourID) references Tour_Main,
FOREIGN KEY (attr_name, latitude, longitude) references Attraction_Located_In_Main);

CREATE TABLE Pass_Through
(tourID INTEGER,
dest_name VARCHAR(100),
parent_region VARCHAR(100),
PRIMARY KEY (tourID, dest_name, parent_region),
FOREIGN KEY (tourID) references Tour_Main,
FOREIGN KEY (dest_name, parent_region) references Destination);

CREATE TABLE Client_1(
points INTEGER PRIMARY KEY,
membership VARCHAR(100));

CREATE TABLE Client_2(
age INTEGER,
gender VARCHAR(100),
demographic_group VARCHAR(100),
PRIMARY KEY (age, gender));

CREATE TABLE Client (
age INTEGER,
gender VARCHAR(100),
points INTEGER,
clientID INTEGER,
cname VARCHAR(100),
PRIMARY KEY (clientID),
FOREIGN KEY (points) REFERENCES Client_1 (points),
FOREIGN KEY (age, gender) REFERENCES Client_2 (age, gender));

CREATE TABLE Public_Transportation
(dest_name VARCHAR(100),
 parent_region VARCHAR(100),
 num VARCHAR(100),
 type VARCHAR(100),
 fare NUMERIC(8,2),
 operator VARCHAR(100),
 PRIMARY KEY (dest_name, parent_region, num, type),
 FOREIGN KEY (dest_name, parent_region) REFERENCES Destination);

CREATE TABLE Stops_At_Attraction
(attr_name VARCHAR(100),
latitude FLOAT,
longitude FLOAT,
dest_name VARCHAR(100),
parent_region VARCHAR(100),
num VARCHAR(100),
type VARCHAR(100),
PRIMARY KEY (attr_name, latitude, longitude, dest_name, parent_region, type, num),
FOREIGN KEY (attr_name, latitude, longitude) REFERENCES Attraction_Located_In_Main,
FOREIGN KEY (dest_name, parent_region, num, type) REFERENCES Public_Transportation);

CREATE TABLE Accommodation_Offers (
contact VARCHAR(100),
accom_name VARCHAR(100),
address VARCHAR(100),
pet_friendly INTEGER,
estimated_price NUMERIC,
star_rating INTEGER,
type VARCHAR(100),
dest_name VARCHAR(100) NOT NULL,
parent_region VARCHAR(100) NOT NULL,
owner VARCHAR(100),
PRIMARY KEY (contact, accom_name, address),
FOREIGN KEY (dest_name, parent_region) REFERENCES Destination (dest_name,
parent_region));

CREATE TABLE Stops_At_Accommodation(
contact VARCHAR(100),
accom_name VARCHAR(100),
address VARCHAR(100),
dest_name VARCHAR(100),
parent_region VARCHAR(100),
num VARCHAR(100),
type VARCHAR(100),
PRIMARY KEY (contact, accom_name, address, dest_name, parent_region, num, type),
FOREIGN KEY (contact, accom_name, address) REFERENCES Accommodation_Offers ON DELETE CASCADE,
FOREIGN KEY (dest_name, parent_region, num, type) REFERENCES Public_Transportation);

CREATE TABLE Activity (
acti_name VARCHAR(100),
equipment_required VARCHAR(100),
PRIMARY KEY (acti_name));

CREATE TABLE Interested_in (
clientID INTEGER,
acti_name VARCHAR(100),
PRIMARY KEY (clientID, acti_name),
FOREIGN KEY (clientID) REFERENCES Client (clientID),
FOREIGN KEY (acti_name) REFERENCES Activity (acti_name));

CREATE TABLE Can_do_at (
acti_name VARCHAR(100),
attr_name VARCHAR(100),
latitude FLOAT,
longitude FLOAT,
PRIMARY KEY (acti_name, attr_name, latitude, longitude),
FOREIGN KEY (acti_name) REFERENCES Activity (acti_name),
FOREIGN KEY (attr_name, latitude, longitude) REFERENCES Attraction_Located_In_Main
(attr_name, latitude, longitude) );

CREATE TABLE Wants_to_Visit
(clientID INTEGER,
dest_name VARCHAR(100),
parent_region VARCHAR(100),
PRIMARY KEY (clientID, dest_name, parent_region),
FOREIGN KEY (clientID) REFERENCES Client,
FOREIGN KEY (dest_name, parent_region) REFERENCES Destination);

CREATE TABLE Knows_About
(dest_name VARCHAR(100),
parent_region VARCHAR(100),
 guideID INTEGER,
PRIMARY KEY (dest_name, parent_region, guideID),
FOREIGN KEY (dest_name, parent_region) REFERENCES Destination,
FOREIGN KEY (guideID) REFERENCES Tour_Guide_Main);

CREATE TABLE Stay_At
(tourID INTEGER,
contact VARCHAR(100),
accom_name VARCHAR(100),
address VARCHAR(100),
PRIMARY KEY (tourID, contact, accom_name, address),
FOREIGN KEY (tourID) REFERENCES Tour_Guide_Main,
FOREIGN KEY (contact, accom_name, address) REFERENCES Accommodation_Offers ON DELETE CASCADE);

INSERT INTO Tour_1 VALUES (TO_DATE('2024-07-03 7:00:00','YYYY-MM-DD HH24:MI:SS'),
                           TO_DATE('2024-07-03 19:00:00','YYYY-MM-DD HH24:MI:SS'), 12);
INSERT INTO Tour_1 VALUES (TO_DATE('2024/07/04 7:00:00','YYYY-MM-DD HH24:MI:SS'),
                          TO_DATE('2024/07/05 19:00:00','YYYY-MM-DD HH24:MI:SS'), 36);
INSERT INTO Tour_1 VALUES (TO_DATE('2024/05/03 14:00:00','YYYY-MM-DD HH24:MI:SS'),
                           TO_DATE('2024/05/03 19:00:00','YYYY-MM-DD HH24:MI:SS'), 5);
INSERT INTO Tour_1 VALUES (TO_DATE('2024/09/10 17:00:00','YYYY-MM-DD HH24:MI:SS'),
                           TO_DATE('2024/09/11 20:00:00','YYYY-MM-DD HH24:MI:SS'), 27);
INSERT INTO Tour_1 VALUES (TO_DATE('2024/09/10 7:00:00','YYYY-MM-DD HH24:MI:SS'),
                           TO_DATE('2024/09/19 21:00:00','YYYY-MM-DD HH24:MI:SS'), 217);
commit;

INSERT INTO Tour_2 VALUES (TO_DATE('2024/07/03 7:00:00','YYYY-MM-DD HH24:MI:SS'),
                           TO_DATE('2024/07/03 19:00:00','YYYY-MM-DD HH24:MI:SS'), 100);
INSERT INTO Tour_2 VALUES (TO_DATE('2024/07/04 7:00:00','YYYY-MM-DD HH24:MI:SS'),
                           TO_DATE('2024/07/05 19:00:00','YYYY-MM-DD HH24:MI:SS'), 100);
INSERT INTO Tour_2 VALUES (TO_DATE('2024/05/03 14:00:00','YYYY-MM-DD HH24:MI:SS'),
                           TO_DATE('2024/05/03 19:00:00','YYYY-MM-DD HH24:MI:SS'), 75);
INSERT INTO Tour_2 VALUES (TO_DATE('2024/09/10 17:00:00','YYYY-MM-DD HH24:MI:SS'),
                           TO_DATE('2024/09/11 20:00:00','YYYY-MM-DD HH24:MI:SS'), 100);
INSERT INTO Tour_2 VALUES (TO_DATE('2024/09/10 7:00:00','YYYY-MM-DD HH24:MI:SS'),
                           TO_DATE('2024/09/19 21:00:00','YYYY-MM-DD HH24:MI:SS'), 100);
commit;

INSERT INTO Tour_Main VALUES (1, 'All day Vancouver tour', 5, 70, TO_DATE('2024/07/03 7:00:00','YYYY-MM-DD HH24:MI:SS'),
                              TO_DATE('2024/07/03 19:00:00','YYYY-MM-DD HH24:MI:SS'));
INSERT INTO Tour_Main VALUES (2, '2-day Vancouver tour', 8, 180, TO_DATE('2024/07/04 7:00:00','YYYY-MM-DD HH24:MI:SS'),
                              TO_DATE('2024/07/05 19:00:00','YYYY-MM-DD HH24:MI:SS'));
INSERT INTO Tour_Main VALUES (3, 'Seattle Afternoon Tour', 2, 50, TO_DATE('2024/05/03 14:00:00','YYYY-MM-DD HH24:MI:SS'),
                              TO_DATE('2024/05/03 19:00:00','YYYY-MM-DD HH24:MI:SS'));
INSERT INTO Tour_Main VALUES (4, 'Long Singapore Tour', 12, 60, TO_DATE('2024/09/10 17:00:00','YYYY-MM-DD HH24:MI:SS'),
                              TO_DATE('2024/09/11 20:00:00','YYYY-MM-DD HH24:MI:SS'));
INSERT INTO Tour_Main VALUES (5, 'USA Washington Entire State Tour', 7, 5000,
                              TO_DATE('2024/09/10 7:00:00','YYYY-MM-DD HH24:MI:SS'),
                              TO_DATE('2024/09/19 21:00:00','YYYY-MM-DD HH24:MI:SS'));
commit;

INSERT INTO Tour_Guide_1 VALUES (20, 50130);
INSERT INTO Tour_Guide_1 VALUES (1, 25000);
INSERT INTO Tour_Guide_1 VALUES (30, 50130);
INSERT INTO Tour_Guide_1 VALUES (17, 42870);
INSERT INTO Tour_Guide_1 VALUES (5, 30000);
commit;

INSERT INTO Tour_Guide_Main VALUES (1, 'Daniel C. Warner', 54, 20);
INSERT INTO Tour_Guide_Main VALUES (2, 'Stephanie Larson', 20, 1);
INSERT INTO Tour_Guide_Main VALUES (3, 'Leona Miles', 53, 30);
INSERT INTO Tour_Guide_Main VALUES (4, 'Renée Rousseau',42, 17);
INSERT INTO Tour_Guide_Main VALUES (5, 'Lucia Yudina', 38, 17);
INSERT INTO Tour_Guide_Main VALUES (6, 'Joe Donner',25, 5);
commit;

INSERT INTO Destination VALUES ('Vancouver', 'British Columbia', 'City');
INSERT INTO Destination VALUES ('British Columbia', 'Canada', 'Province');
INSERT INTO Destination VALUES ('Seattle', 'Washington', 'City');
INSERT INTO Destination VALUES ('Washington', 'USA', 'State');
INSERT INTO Destination VALUES ('Singapore', 'Singapore', 'City');
INSERT INTO Destination VALUES ('Rome', 'Italy', 'City');
commit;

INSERT INTO Attraction_Located_In_Main VALUES ('Gastown', 49.28344, -123.10985, 0, NULL, NULL,
                                               'gastown.org', 'Neighborhood', 0,'middle-aged women','Vancouver', 'British Columbia');
INSERT INTO Attraction_Located_In_Main VALUES ('Metrotown', 49.1331, -123, 0, NULL, NULL,
                                               'https://www.metropolisatmetrotown.com/', 'Neighborhood', 0,'everyone','Vancouver', 'British Columbia');
INSERT INTO Attraction_Located_In_Main VALUES ('Granville Island', 49.27187, -123.13543, 0, NULL, NULL,
                                               'granvilleisland.com', 'Neighborhood', 0, 'everyone','Vancouver', 'British Columbia');
INSERT INTO Attraction_Located_In_Main VALUES ('Museum of Pop Culture', 47.62108, -122.34840, 25,
                                               TO_DATE('10:00:00','HH24:MI:SS'),
                                               TO_DATE('17:00:00','HH24:MI:SS'),
                                               'www.mopop.org','Museum', 0,'young women', 'Seattle', 'Washington');
INSERT INTO Attraction_Located_In_Main VALUES ('Singapore Botanic Gardens', 1.31384, 103.81600, 0,
                                               TO_DATE('5:00:00','HH24:MI:SS'),
                                               TO_DATE('0:00:00','HH24:MI:SS'),
                                               'www.nparks.gov.sg/sbg', 'Garden', 0,'young women', 'Singapore',
                                               'Singapore');
INSERT INTO Attraction_Located_In_Main VALUES ('Mount Rainier National Park', 46.73912, -121.91631, 30,
                                               TO_DATE('7:00:00','HH24:MI:SS'),
                                               TO_DATE('15:00:00','HH24:MI:SS'),'www.nps.gov/mora/index.htm',
                                               'National Park', 0,'old men','Washington', 'USA');
INSERT INTO Attraction_Located_In_Main VALUES ('Space Needle', 47.6204, -122.3491, 37,
                                               TO_DATE('10:00:00','HH24:MI:SS'), TO_DATE('21:00:00','HH24:MI:SS'),
                                               'www.spaceneedle.com','Observation Tower', 0, 'older men','Seattle',
                                               'Washington');
INSERT INTO Attraction_Located_In_Main VALUES ('Strathcona Provincial Park', 49.62533, -125.70249, 80,
                                               TO_DATE('9:00:00','HH24:MI:SS'),
                                               TO_DATE('18:00:00','HH24:MI:SS'), 'bcparks.ca/strathcona-park',
                                               'National Park', 0, 'old men',
                                               'British Columbia', 'Canada');
INSERT INTO Attraction_Located_In_Main VALUES ('Chinatown Street Market', 1.28370, 103.84422, 0,
                                               TO_DATE('9:00:00','HH24:MI:SS'),
                                               TO_DATE('22:00:00','HH24:MI:SS'), NULL, 'Market', 0,'everyone','Singapore',
                                               'Singapore');
INSERT INTO Attraction_Located_In_Main VALUES ('Vivocity', 1.26373, 103.82238, 0, TO_DATE('10:00:00','HH24:MI:SS'),
                                               TO_DATE('22:00:00','HH24:MI:SS'),
                                               'www.vivocity.com.sg', 'Shopping Mall',0, 'young women','Singapore', 'Singapore');
INSERT INTO Attraction_Located_In_Main VALUES ('The Shoppes', 1.28351, 103.85914, 0, TO_DATE('11:00:00','HH24:MI:SS'),
                                               TO_DATE('22:00:00','HH24:MI:SS'),
                                               'www.marinabaysands.com/shopping.html', 'Shopping Mall', 0, 'young women',
                                               'Singapore', 'Singapore');
INSERT INTO Attraction_Located_In_Main VALUES ('Pike Place Market', 47.6088, -122.3416, 0,
                                               TO_DATE('10:00:00','HH24:MI:SS'), TO_DATE('17:00:00','HH24:MI:SS'),
                                               'www.pikeplacemarket.org','Market', 0, 'everyone', 'Seattle', 'Washington');
INSERT INTO Attraction_Located_In_Main VALUES ('Siloso Beach', 1.2546, 103.8113, 0, NULL, NULL, NULL, 'Beach', 0, 'everyone',
                                               'Singapore','Singapore');
INSERT INTO Attraction_Located_In_Main VALUES ('Glacier National Park', 51.3353, -117.5298, 11,
                                               TO_DATE('8:00:00','HH24:MI:SS'),TO_DATE('16:30:00','HH24:MI:SS'),
                                               'parks.canada.ca/pnnp/bc/glacier','National Park', 0, 'old people',
                                               'British Columbia', 'Canada');
INSERT INTO Attraction_Located_In_Main VALUES ('Canlis', 47.6431, -122.3468, 180, TO_DATE('17:00:00','HH24:MI:SS'),
                                               TO_DATE('0:00:00','HH24:MI:SS'),
                                               'canlis.com', 'Restaurant', 0, 'everyone', 'Seattle','Washington');
INSERT INTO Attraction_Located_In_Main VALUES ('Sushi Kashiba', 47.60993, -122.34158, 200,
                                               TO_DATE('17:00:00','HH24:MI:SS'), TO_DATE('21:00:00','HH24:MI:SS'),
                                               'sushikashiba.com','Restaurant', 0,'everyone', 'Seattle', 'Washington');
INSERT INTO Attraction_Located_In_Main VALUES ('Rang Mahal', 1.29098, 103.85817, 58, TO_DATE('12:00:00','HH24:MI:SS'),
                                               TO_DATE('14:30:00','HH24:MI:SS'),
                                               'www.rangmahal.com.sg','Restaurant', 0,'everyone', 'Singapore', 'Singapore');
INSERT INTO Attraction_Located_In_Main VALUES ('Odette', 1.29045, 103.85154, 350, TO_DATE('17:00:00','HH24:MI:SS'),
                                               TO_DATE('21:30:00','HH24:MI:SS'),
                                               'odetterestaurant.com', 'Restaurant',0,'everyone', 'Singapore', 'Singapore');
INSERT INTO Attraction_Located_In_Main VALUES ('Tamarind Hill', 1.26788, 103.80041, 60, TO_DATE('12:00:00','HH24:MI:SS'),
                                               TO_DATE('19:45:00','HH24:MI:SS'),
                                               'www.tamarindrestaurants.com', 'Restaurant', 18,'young people', 'Singapore',
                                               'Singapore');
INSERT INTO Attraction_Located_In_Main VALUES ('Sultan Mosque', 1.3023, 103.8590, 0, TO_DATE('10:00:00','HH24:MI:SS'),
                                               TO_DATE('21:00:00','HH24:MI:SS'), NULL,
                                               'Mosque', 0, 'old people', 'Singapore','Singapore');
INSERT INTO Attraction_Located_In_Main VALUES ('Thian Hock Keng', 1.27529, 103.84184, 0, TO_DATE('7:30:00','HH24:MI:SS'),
                                               TO_DATE('17:00:00','HH24:MI:SS'),
                                               'thianhockkeng.com.sg','Temple', 0,'old people', 'Singapore', 'Singapore');
INSERT INTO Attraction_Located_In_Main VALUES ('Colosseum', 41.8902, 12.4922, 16, TO_DATE('8:30:00','HH24:MI:SS'),
                                               TO_DATE('17:00:00','HH24:MI:SS'),
                                               'www.il-colosseo.it', 'Roman Architecture', 0, 'everyone', 'Rome', 'Italy');
INSERT INTO Attraction_Located_In_Main VALUES ('Roman Forum', 41.8925, 12.4853, 16,TO_DATE('9:00:00','HH24:MI:SS'),
                                               TO_DATE('17:30:00','HH24:MI:SS'),
                                               'colosseo.it/en/area/the-romanforum/','Roman Architecture', 0, 'everyone',
                                               'Rome', 'Italy');
INSERT INTO Attraction_Located_In_Main VALUES ('Pantheon', 41.8986, 12.4769, 5, TO_DATE('9:00:00','HH24:MI:SS'),
                                               TO_DATE('18:30:00','HH24:MI:SS'),
                                               'www.pantheonroma.com/home-eng/','Roman Architecture', 0, 'everyone','Rome',
                                               'Italy');

commit;

INSERT INTO Restaurant VALUES ('Canlis', 47.6431, -122.3468, 'New American');
INSERT INTO Restaurant VALUES ('Sushi Kashiba', 47.60993, -122.34158, 'Japanese');
INSERT INTO Restaurant VALUES ('Rang Mahal', 1.29098, 103.85817, 'Indian');
INSERT INTO Restaurant VALUES ('Odette', 1.29045, 103.85154, 'French');
INSERT INTO Restaurant VALUES ('Tamarind Hill', 1.26788, 103.80041, 'Thai');
commit;

INSERT INTO Shopping VALUES ('Gastown', 49.28344, -123.10985, 'Art', 56);
INSERT INTO Shopping VALUES ('Chinatown Street Market', 1.28370, 103.84422, 'Various', 90);
INSERT INTO Shopping VALUES ('Vivocity', 1.26373, 103.82238, 'Various', 380);
INSERT INTO Shopping VALUES ('The Shoppes', 1.28351, 103.85914, 'Luxury', 200);
INSERT INTO Shopping VALUES ('Pike Place Market', 47.6088, -122.3416, 'Food', 220);

INSERT INTO Heritage VALUES ('Sultan Mosque', 1.3023, 103.8590, 1935);
INSERT INTO Heritage VALUES ('Thian Hock Keng', 1.27529, 103.84184, 1835);
INSERT INTO Heritage VALUES ('Colosseum', 41.8902, 12.4922, 80);
INSERT INTO Heritage VALUES ('Roman Forum', 41.8925, 12.4853, 608);
INSERT INTO Heritage VALUES ('Pantheon', 41.8986, 12.4769, 125);

INSERT INTO Nature  VALUES ('Singapore Botanic Gardens', 1.31384, 103.81600, 'Garden');
INSERT INTO Nature  VALUES ('Mount Rainier National Park', 46.73912, -121.91631, 'Volcano');
INSERT INTO Nature  VALUES ('Strathcona Provincial Park', 49.62533, -125.70249, 'Forest');
INSERT INTO Nature  VALUES ('Siloso Beach', 1.2546, 103.8113, 'Beach');
INSERT INTO Nature  VALUES ('Glacier National Park', 51.3353, -117.5298, 'Glacier');

INSERT INTO Guides VALUES (1, 1);
INSERT INTO Guides VALUES (1, 2);
INSERT INTO Guides VALUES (2, 3);
INSERT INTO Guides VALUES (3, 6);
INSERT INTO Guides VALUES (4, 4);
INSERT INTO Guides VALUES (5, 5);

INSERT INTO Visits VALUES (1, 'Gastown', 49.28344, -123.10985);
INSERT INTO Visits VALUES (2, 'Gastown', 49.28344, -123.10985);
INSERT INTO Visits VALUES (3, 'Museum of Pop Culture', 47.62108, -122.34840);
INSERT INTO Visits VALUES (4, 'Singapore Botanic Gardens', 1.31384, 103.81600);
INSERT INTO Visits VALUES (5, 'Mount Rainier National Park', 46.73912, -121.91631);
INSERT INTO Visits VALUES (5, 'Space Needle', 47.6204, -122.3491);
INSERT INTO Visits VALUES (5, 'Museum of Pop Culture', 47.62108, -122.34840);

INSERT INTO Pass_Through VALUES (1, 'Vancouver', 'British Columbia');
INSERT INTO Pass_Through VALUES (2, 'Vancouver', 'British Columbia');
INSERT INTO Pass_Through VALUES (3, 'Seattle', 'Washington');
INSERT INTO Pass_Through VALUES (4, 'Singapore', 'Singapore');
INSERT INTO Pass_Through VALUES (5, 'Washington', 'USA');
INSERT INTO Pass_Through VALUES (5, 'Seattle', 'Washington');

INSERT INTO Client_1 VALUES (2000, 'Silver');
INSERT INTO Client_1 VALUES (4000, 'Gold');
INSERT INTO Client_1 VALUES (1000, 'Silver');
INSERT INTO Client_1 VALUES (3000, 'Silver');
INSERT INTO Client_1 VALUES (0, 'Standard');

INSERT INTO Client_2 VALUES (70, 'Male', 'older men');
INSERT INTO Client_2 VALUES (24, 'Female', 'young women');
INSERT INTO Client_2 VALUES (40, 'Female', 'middle-aged women');
INSERT INTO Client_2 VALUES (18, 'Male', 'young men');
INSERT INTO Client_2 VALUES (3, 'Female', 'infant female');

INSERT INTO Client VALUES (70, 'Male', 2000, 1, 'Mehelle Stoneman');
INSERT INTO Client VALUES (24, 'Female', 4000, 2, 'Nicki Malchow');
INSERT INTO Client VALUES (40, 'Female', 1000, 3, 'Sidney Bodiford');
INSERT INTO Client VALUES (18, 'Male', 1000, 4, 'Elden Hanshaw');
INSERT INTO Client VALUES (3, 'Female', 0, 5, 'Pattie Markley');

INSERT INTO Public_Transportation VALUES ('Vancouver', 'British Columbia', '68', 'Bus', 2, 'TransLink');
INSERT INTO Public_Transportation VALUES ('British Columbia', 'Canada', 'Expo Line', 'Subway', 2, 'TransLink');
INSERT INTO Public_Transportation VALUES ('Seattle', 'Washington', '2', 'Bus', 1, 'King County');
INSERT INTO Public_Transportation VALUES ('Washington', 'USA', '2', 'Orange', 1, 'WMATA');
INSERT INTO Public_Transportation VALUES ('Singapore', 'Singapore', 'Metro', 'Circle Line', 2, 'SMRT Trains Ltd');

INSERT INTO Public_Transportation VALUES ('Vancouver', 'British Columbia', '49', 'Bus', 2, 'TransLink');
INSERT INTO Public_Transportation VALUES ('Vancouver', 'British Columbia', 'R4', 'Bus', 3, 'TransLink');
INSERT INTO Public_Transportation VALUES ('Vancouver', 'British Columbia', '33', 'Bus', 1, 'TransLink');
INSERT INTO Public_Transportation VALUES ('Vancouver', 'British Columbia', '25', 'Bus', 4, 'TransLink');

INSERT INTO Public_Transportation VALUES ('British Columbia', 'Canada', '33', 'Bus', 4, 'TransLink');
INSERT INTO Public_Transportation VALUES ('British Columbia', 'Canada', '11', 'Subway', 6, 'TransLink');
INSERT INTO Public_Transportation VALUES ('British Columbia', 'Canada', '7', 'Bus', 3, 'TransLink');

INSERT INTO Public_Transportation VALUES ('Seattle', 'Washington', '8', 'Bus', 4, 'King County');
INSERT INTO Public_Transportation VALUES ('Seattle', 'Washington', '11', 'Bus', 7, 'King County');
INSERT INTO Public_Transportation VALUES ('Seattle', 'Washington', 'AA', 'Subway', 3, 'King County');
INSERT INTO Public_Transportation VALUES ('Seattle', 'Washington', '79', 'Bus', 5, 'King County');

INSERT INTO Public_Transportation VALUES ('Washington', 'USA', '3', 'Orange', 3, 'WMATA');
INSERT INTO Public_Transportation VALUES ('Washington', 'USA', '92', 'Bus', 5, 'WMATA');
INSERT INTO Public_Transportation VALUES ('Washington', 'USA', '6', 'Subway', 1, 'WMATA');
INSERT INTO Public_Transportation VALUES ('Washington', 'USA', '10', 'Bus', 7, 'WMATA');

INSERT INTO Public_Transportation VALUES ('Singapore', 'Singapore', 'Richmond', 'Circle Line', 1, 'SMRT Trains Ltd');
INSERT INTO Public_Transportation VALUES ('Singapore', 'Singapore', '77', 'Bus', 3, 'SMRT Trains Ltd');
INSERT INTO Public_Transportation VALUES ('Singapore', 'Singapore', 'M', 'Subway', 2, 'SMRT Trains Ltd');
INSERT INTO Public_Transportation VALUES ('Singapore', 'Singapore', '6', 'Bus', 7, 'SMRT Trains Ltd');

commit;

INSERT INTO Accommodation_Offers VALUES ('605-471-8923', 'JHOTEL', '7887 Aojk RD', 1, 357, 4, 'hotel',
                                        'Vancouver', 'British Columbia','Judy');
INSERT INTO Accommodation_Offers VALUES ('ins: Olivia_family', 'O-HOUSE', '0907-3895 Aksl ST', 0, 268, 0,
                                        'airbnb', 'Seattle','Washington','Olivia');
INSERT INTO Accommodation_Offers VALUES ('mhrs.cunjw.concierge@marriott.com', 'JW Marriott',
                                        'Km 14.5, Blvd. Kuk Lote 40', 0,769,5, 'hotel', 'Washington','USA',
                                         'Michael');
INSERT INTO Accommodation_Offers VALUES ('779-586-3829', 'D-Campsite', '7864 LYTDB RD', 1, 241,0, 'campsite',
                                        'Singapore','Singapore','Daniel');
INSERT INTO Accommodation_Offers VALUES ('Matthew.1980@gmail.com', 'MIY', '753 UGFIK ST', 0, 411,3, 'hotel',
                                        'Vancouver', 'British Columbia','Matthew');
INSERT INTO Accommodation_Offers VALUES ('Matthew.1980@gmail.com',
                                        'Silver Cloud Hotel Tacoma at Point Ruston Waterfront',
                                        '5125 Grand Loop Ruston, WA 98407', 1, 300, 1, 'hotel', 'Washington', 'USA',
                                        'Matthew');

INSERT INTO Accommodation_Offers VALUES ('695-205-3017', 'GHOME', '102-1024 KAOF ST', 0, 290, 0, 'airbnb',
                                         'Seattle', 'Washington','Olivia');
INSERT INTO Accommodation_Offers VALUES ('jam_1047@gmail.com', 'MW', '1047-81 KDV ST', 1, 698, 5,
                                         'hotel', 'Seattle','Washington','Vencent');
INSERT INTO Accommodation_Offers VALUES ('910-0928-0479', 'LIPA',
                                         '108-1038 LJHGG RD', 1,284,0, 'motel', 'Washington','USA',
                                         'Mary');
INSERT INTO Accommodation_Offers VALUES ('104-0283-1928', 'D-C', '2847 JLHG RD', 1, 799,3, 'campsite',
                                         'Singapore','Singapore','Daniel');
INSERT INTO Accommodation_Offers VALUES ('Obaioh.10887@gmail.com', 'LAI', '108-8393 LJGA ST', 1,  971 ,3, 'hotel',
                                         'Vancouver', 'British Columbia','Matthew');
INSERT INTO Accommodation_Offers VALUES ('108-1928-1933',
                                         'OA-Campsite',
                                         '1028 LAOH RD', 1, 590, 3, 'campsite', 'Washington', 'USA',
                                         'Matthew');
commit;

INSERT INTO Stops_At_Attraction VALUES ('Gastown', 49.28344, -123.10985, 'Vancouver', 'British Columbia', '68', 'Bus');
INSERT INTO Stops_At_Attraction VALUES ('Metrotown', 49.1331, -123, 'Vancouver', 'British Columbia', '68', 'Bus');
INSERT INTO Stops_At_Attraction VALUES ('Granville Island', 49.27187, -123.13543, 'Vancouver', 'British Columbia', '68', 'Bus');
INSERT INTO Stops_At_Attraction VALUES ('Gastown', 49.28344, -123.10985, 'Vancouver', 'British Columbia', 'R4', 'Bus');
INSERT INTO Stops_At_Attraction VALUES ('Metrotown', 49.1331, -123, 'Vancouver', 'British Columbia', 'R4', 'Bus');
INSERT INTO Stops_At_Attraction VALUES ('Granville Island', 49.27187, -123.13543, 'Vancouver', 'British Columbia', 'R4', 'Bus');
INSERT INTO Stops_At_Attraction VALUES ('Thian Hock Keng', 1.27529, 103.84184,'Singapore', 'Singapore', 'Metro',
                                        'Circle Line');
INSERT INTO Stops_At_Attraction VALUES ('Museum of Pop Culture', 47.62108, -122.34840, 'Seattle', 'Washington',
                                        '2','Bus');
INSERT INTO Stops_At_Attraction VALUES ('Singapore Botanic Gardens', 1.31384, 103.81600, 'Singapore', 'Singapore',
                                        'Metro', 'Circle Line');
INSERT INTO Stops_At_Attraction VALUES ('Space Needle', 47.6204, -122.3491, 'Seattle', 'Washington', '2', 'Bus');
INSERT INTO Stops_At_Attraction VALUES ('Siloso Beach', 1.2546, 103.8113, 'Singapore', 'Singapore', 'Metro', 'Circle Line');
INSERT INTO Stops_At_Attraction VALUES ('Chinatown Street Market', 1.28370, 103.84422, 'Singapore', 'Singapore', 'Metro', 'Circle Line');
INSERT INTO Stops_At_Attraction VALUES ('Vivocity', 1.26373, 103.82238, 'Singapore', 'Singapore', 'Metro', 'Circle Line');
INSERT INTO Stops_At_Attraction VALUES ('Rang Mahal', 1.29098, 103.85817, 'Singapore', 'Singapore', 'Metro', 'Circle Line');
INSERT INTO Stops_At_Attraction VALUES ('Odette', 1.29045, 103.85154, 'Singapore', 'Singapore', 'Metro', 'Circle Line');
INSERT INTO Stops_At_Attraction VALUES ('Tamarind Hill', 1.26788, 103.80041, 'Singapore', 'Singapore', 'Metro', 'Circle Line');
INSERT INTO Stops_At_Attraction VALUES ('Sultan Mosque', 1.3023, 103.8590, 'Singapore', 'Singapore', 'Metro', 'Circle Line');
INSERT INTO Stops_At_Attraction VALUES ('The Shoppes', 1.28351, 103.85914, 'Singapore', 'Singapore', 'Metro', 'Circle Line');
    commit;

INSERT INTO Stops_At_Accommodation VALUES ('605-471-8923', 'JHOTEL', '7887 Aojk RD', 'Vancouver', 'British Columbia', '68', 'Bus');
INSERT INTO Stops_At_Accommodation VALUES  ('ins: Olivia_family', 'O-HOUSE', '0907-3895 Aksl ST', 'Seattle',
                                            'Washington', '2', 'Bus');
INSERT INTO Stops_At_Accommodation VALUES  ('mhrs.cunjw.concierge@marriott.com', 'JW Marriott',
                                            'Km 14.5, Blvd. Kuk Lote 40','Washington', 'USA', '2', 'Orange');
INSERT INTO Stops_At_Accommodation VALUES  ('779-586-3829', 'D-Campsite', '7864 LYTDB RD', 'Singapore', 'Singapore',
                                            'Metro','Circle Line');
INSERT INTO Stops_At_Accommodation VALUES ('Matthew.1980@gmail.com', 'MIY', '753 UGFIK ST', 'Vancouver',
                                           'British Columbia', '68','Bus');
INSERT INTO Stops_At_Accommodation VALUES  ('Matthew.1980@gmail.com',
                                            'Silver Cloud Hotel Tacoma at Point Ruston Waterfront',
                                            '5125 Grand Loop Ruston, WA 98407', 'Washington', 'USA', '2', 'Orange');
INSERT INTO Stops_At_Accommodation VALUES ('ins: Olivia_family', 'O-HOUSE', '0907-3895 Aksl ST', 'Seattle', 'Washington', '8', 'Bus');
INSERT INTO Stops_At_Accommodation VALUES ('ins: Olivia_family', 'O-HOUSE', '0907-3895 Aksl ST', 'Seattle', 'Washington', 'AA', 'Subway');
INSERT INTO Stops_At_Accommodation VALUES ('Matthew.1980@gmail.com', 'MIY', '753 UGFIK ST', 'Vancouver', 'British Columbia', 'R4', 'Bus');
INSERT INTO Stops_At_Accommodation VALUES ('Matthew.1980@gmail.com', 'MIY', '753 UGFIK ST', 'Vancouver',
                                           'British Columbia', '25', 'Bus');


INSERT INTO Activity VALUES ('skiing', 'snowboarding, skiing poles, skiing suit');
INSERT INTO Activity VALUES ('hiking', 'Hiking boots, trekking poles, gloves, safety equipment');
INSERT INTO Activity VALUES ('diving', 'diving mask, snorkel, Scuba Tank, Wetsuit, Dive Gloves and Boots');
INSERT INTO Activity VALUES ('shopping', NULL);
INSERT INTO Activity VALUES ('sightseeing', NULL);


INSERT INTO Interested_in VALUES (1, 'skiing');
INSERT INTO Interested_in VALUES (1, 'diving');
INSERT INTO Interested_in VALUES (2, 'skiing');
INSERT INTO Interested_in VALUES (4, 'shopping');
INSERT INTO Interested_in VALUES (3, 'hiking');

INSERT INTO Can_do_at VALUES ('shopping', 'Gastown', 49.28344, -123.10985);
INSERT INTO Can_do_at VALUES ('sightseeing', 'Museum of Pop Culture', 47.62108, -122.34840);
INSERT INTO Can_do_at VALUES ('sightseeing', 'Singapore Botanic Gardens', 1.31384, 103.81600);
INSERT INTO Can_do_at VALUES ('hiking', 'Mount Rainier National Park', 46.73912, -121.91631);
INSERT INTO Can_do_at VALUES ('sightseeing', 'Space Needle', 47.6204, -122.3491);

INSERT INTO Wants_to_visit VALUES (1, 'Vancouver', 'British Columbia');
INSERT INTO Wants_to_visit VALUES (2, 'British Columbia', 'Canada');
INSERT INTO Wants_to_visit VALUES (4, 'Seattle', 'Washington');
INSERT INTO Wants_to_visit VALUES (5, 'Singapore', 'Singapore');
INSERT INTO Wants_to_visit VALUES (3, 'Vancouver', 'British Columbia');

INSERT INTO Knows_About VALUES ('Singapore', 'Singapore', 1);
INSERT INTO Knows_About VALUES ('Vancouver', 'British Columbia', 2);
INSERT INTO Knows_About VALUES ('Vancouver', 'British Columbia', 3);
INSERT INTO Knows_About VALUES ('Seattle', 'Washington', 3);
INSERT INTO Knows_About VALUES ('Rome', 'Italy', 4);
INSERT INTO Knows_About VALUES ('Washington', 'USA', 5);
INSERT INTO Knows_About VALUES ('British Columbia', 'Canada', 6);

INSERT INTO Stay_At VALUES (2, '605-471-8923', 'JHOTEL', '7887 Aojk RD');
INSERT INTO Stay_At VALUES (3, 'mhrs.cunjw.concierge@marriott.com', 'JW Marriott', 'Km 14.5, Blvd. Kuk Lote 40');
INSERT INTO Stay_At VALUES (5, 'mhrs.cunjw.concierge@marriott.com', 'JW Marriott', 'Km 14.5, Blvd. Kuk Lote 40');
INSERT INTO Stay_At VALUES (4, '779-586-3829', 'D-Campsite', '7864 LYTDB RD');
INSERT INTO Stay_At VALUES (5, 'Matthew.1980@gmail.com',
                            'Silver Cloud Hotel Tacoma at Point Ruston Waterfront'
                               , '5125 Grand Loop Ruston, WA 98407');
commit;

-- SELECT PUBLIC_TRANSPORTATION.DEST_NAME, PUBLIC_TRANSPORTATION.parent_region, PUBLIC_TRANSPORTATION.type, PUBLIC_TRANSPORTATION.num, PUBLIC_TRANSPORTATION.fare, PUBLIC_TRANSPORTATION.operator
-- FROM PUBLIC_TRANSPORTATION
-- WHERE " + filter + " NOT EXISTS +
-- (SELECT Attraction_Located_In_Main.longitude, Attraction_Located_In_Main.latitude, Attraction_Located_In_Main.attr_name
-- FROM Attraction_Located_In_Main)
-- MINUS
-- (SELECT Stops_At_Attraction.longitude, Stops_At_Attraction.latitude, Stops_At_Attraction.arrt_name
-- FROM Stops_At_Attraction
-- WHERE Stops_At_Attraction.dest_name = Public_Transportation.dest_name AND Stops_At_Attraction.parent_region = Public_Transportation.parent_region AND Stops_At_Attraction.type = Public_Transportation.type AND Stops_At_Attraction.num= Public_Transportation.num))

-- See Milestone 4 document for assertions