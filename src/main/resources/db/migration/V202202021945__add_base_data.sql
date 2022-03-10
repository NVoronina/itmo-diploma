INSERT INTO users(id, email, name, surname, second_name, phone)
VALUES (1, 'test@medonline.ru', 'Василий', 'Иванов', 'Иванович', '+79999929939'),
       (2, 'pete@my.te', 'Василий', 'Петров', 'Петрович', '89349299349'),
       (3, 'john@my.te', 'John', 'Doe', null, null),
       (4, 'test@medonline.ru', 'Nataly', 'Voronina', null, null),
       (5, 'tets@medonline.ru', 'Test', 'Surname', null, '+7 (812) 837-88-77'),
       (6, 'ivan@my.te', 'Иван', 'Сидоров', 'Дмитриевич', '+7 (812) 837-88-77');

INSERT INTO specializations(id, specialization)
VALUES (1, 'Физиотерапевт'),
       (2, 'Психолог'),
       (3, 'Терапевт'),
       (4, 'Гастроинтеролог'),
       (5, 'Кардиолог')
;

INSERT INTO services(id, service, time)
VALUES (1, 'Первичный прием', null),
       (2, 'Вторичный прием', null),
       (3, 'Кардиограмма', 15)
;
