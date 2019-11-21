DROP TABLE Route;

CREATE TABLE Route (ID INT PRIMARY KEY
, flight_number VARCHAR(255)
, departure varchar(255)
, destination  varchar(255)
, disabled varchar(255)
, signature UUID
, booking_date date);

insert into route(id, flight_Number, departure, destination, disabled, signature, booking_date) VALUES
	(1, 'LH7902', 'MUC', 'IAH', 'TRUE', '3b241101-e2bb-4255-8caf-4136c566a964', '2019-11-21' );
insert into route(id, flight_Number, departure, destination, disabled, signature, booking_date) VALUES
	(2, 'LH1602', 'MUC', 'IBZ', 'FALSE', '3b241102-e2bc-4256-8caf-4136c566a965', '2019-11-22');
insert into route(id, flight_Number, departure, destination, disabled, signature, booking_date) VALUES
	(3, 'LH401', 'FRA', 'NYC', 'TRUE', '3b241103-e2bc-4257-8caf-4136c566a966', '2019-11-23');