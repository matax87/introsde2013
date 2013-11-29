DROP TABLE IF EXISTS person;

CREATE TABLE person ( 
	p_id		  INTEGER PRIMARY KEY AUTOINCREMENT,
	name      TEXT NOT NULL,
	lastname  TEXT NOT NULL,
	birthdate TEXT DEFAULT NULL
);

INSERT INTO person (p_id, name, lastname, birthdate) VALUES (1, 'Matteo', 'Matassoni', '1987-09-16 00:00:00.000');


DROP TABLE IF EXISTS measure_type;

CREATE TABLE measure_type ( 
	mt_id	INTEGER PRIMARY KEY AUTOINCREMENT,
	name		TEXT NOT NULL,
	type		TEXT NOT NULL
);

INSERT INTO measure_type (mt_id, name, type) VALUES (1, 'weight', 'double');
INSERT INTO measure_type (mt_id, name, type) VALUES (2, 'height', 'double');
INSERT INTO measure_type (mt_id, name, type) VALUES (3, 'steps', 'integer');


DROP TABLE IF EXISTS measure;

CREATE TABLE measure ( 
	m_id		INTEGER PRIMARY KEY AUTOINCREMENT,	
	value	REAL NOT NULL,
	created	TEXT NOT NULL,
	p_id		INTEGER NOT NULL,
	mt_id	INTEGER NOT NULL,
	CONSTRAINT 'fk_measure_person' FOREIGN KEY (p_id) REFERENCES person (p_id) ON DELETE CASCADE ON UPDATE RESTRICT,
	CONSTRAINT 'fk_measure_measure_type' FOREIGN KEY (mt_id) REFERENCES measure_type (mt_id) ON DELETE CASCADE ON UPDATE RESTRICT
);

INSERT INTO measure (m_id, value, created, p_id, mt_id) VALUES (1, 70, '2013-09-26 09:00:00.000', 1, 1);
INSERT INTO measure (m_id, value, created, p_id, mt_id) VALUES (2, 69, '2013-10-26 09:00:00.000', 1, 1);
INSERT INTO measure (m_id, value, created, p_id, mt_id) VALUES (3, 68.5, '2013-11-26 09:00:00.000', 1, 1);