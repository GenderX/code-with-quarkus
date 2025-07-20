-- Insert demo data with explicit ID sequence starting higher
INSERT INTO demo (id, name, description) VALUES (1, 'Sample Item 1', 'This is a sample description 1');
INSERT INTO demo (id, name, description) VALUES (2, 'Sample Item 2', 'This is a sample description 2');
INSERT INTO demo (id, name, description) VALUES (3, 'Sample Item 3', 'This is a sample description 3');
-- Set the sequence to start from 4 for new records
ALTER SEQUENCE demo_seq RESTART WITH 4;