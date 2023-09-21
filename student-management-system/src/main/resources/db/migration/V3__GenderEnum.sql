
ALTER TABLE students
ALTER COLUMN gender TYPE gender_enum
    USING (gender::gender_enum);