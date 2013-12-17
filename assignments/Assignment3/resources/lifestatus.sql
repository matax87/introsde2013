DROP TABLE IF EXISTS person;

CREATE TABLE person ( 
	p_id		  INTEGER PRIMARY KEY AUTOINCREMENT,
	name      TEXT NOT NULL,
	lastname  TEXT NOT NULL,
	birthdate TEXT NOT NULL
);

INSERT INTO person (p_id, name, lastname, birthdate) VALUES (1, 'Chuck', 'Norris', '1945-01-01 00:00:00.000');


DROP TABLE IF EXISTS healthprofile;

CREATE TABLE healthprofile ( 
	hp_id		INTEGER PRIMARY KEY AUTOINCREMENT,	
	created		TEXT NOT NULL,
	weight		REAL DEFAULT NULL,
	height		REAL DEFAULT NULL,
	steps		INTEGER DEFAULT NULL,
	calories	INTEGER DEFAULT NULL,
	person		INTEGER NOT NULL,
	CONSTRAINT 'fk_healthprofile_person' FOREIGN KEY (person) REFERENCES person (p_id) ON DELETE CASCADE ON UPDATE RESTRICT
);

INSERT INTO healthprofile (hp_id, created, weight, height, steps, calories, person) VALUES (999, '2013-12-05 09:00:00.000', 78.9, 172, 5000, 2120, 1);
INSERT INTO healthprofile (hp_id, created, steps, person) VALUES (998, '2013-11-29 09:00:00.000', 6430, 1);
INSERT INTO healthprofile (hp_id, created, steps, person) VALUES (1000, '2013-11-05 09:00:00.000', 12083, 1);