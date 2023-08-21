DROP table students;

CREATE TYPE gender_enum AS ENUM ('MALE', 'FEMALE');

CREATE TABLE IF NOT EXISTS students (
    student_id UUID PRIMARY KEY NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    gender gender_enum NOT NULL
    );

CREATE TABLE IF NOT EXISTS course(
    course_id UUID NOT NULL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    description TEXT NOT NULL,
    department VARCHAR(255),
    teacher_name VARCHAR(100)
    );

CREATE TABLE IF NOT EXISTS student_course(
    student_id UUID NOT NULL REFERENCES students (student_id),
    course_id UUID NOT NULL REFERENCES course (course_id),
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    grade INTEGER CHECK (grade >=0 AND grade <= 100),
    UNIQUE (student_id, course_id)
    );