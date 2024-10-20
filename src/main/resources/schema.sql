CREATE TABLE IF NOT EXISTS cars (
    id INTEGER PRIMARY KEY,
    fname TEXT,
    mname TEXT,
    lname TEXT,
    email TEXT UNIQUE,
    phone TEXT
);