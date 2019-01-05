INSERT INTO author (name,lastname,email) VALUES
  ('Tomas','Werner', 'werner@cmp.felk.cvut.cz'),
  ('Petr','Olsak','petr@olsak.net'),
  ('Jan','Cech','cech.jan@fel.cvut.cz');

INSERT INTO book ( isbn, datepublished, genre, title) VALUES
  ('1-4028-9462-7', '22.9.2018', 'Mathematics','Optimization'),
  ('1-4028-9462-8', '4.2.2015', 'Mathematics','Introduction to Linear Algebra');

INSERT INTO library (name) VALUES
  ('NTK');

INSERT INTO publisher (address, name) VALUES
  ('Karlovo nam. 13', 'FEL Karlovo namesti'),
  ('Technicka 2', 'FEL Dejvice');
