﻿CREATE TABLE book (
                      id SERIAL PRIMARY KEY,
                      author TEXT,
                      launch_date TIMESTAMP(6) NOT NULL,
                      price NUMERIC(65,2) NOT NULL,
                      title TEXT
);