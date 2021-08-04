-- Users
insert into users(ADDRESS, BALANCE, BIRTH_DATE, EMAIL, FIRST_NAME, LAST_NAME, LOGIN, PASSPORT, PASSWORD, ROLE, STATUS) VALUES
(
    'Saint-Petersburg',
    0,
    '1992-01-14',
    'Skaleksandrovich@inbox.ru',
    'Kirill',
    'Streltsov',
    'skalek',
    '1111 222333',
    '$2a$10$SEXMxjHEIO2/7OdtWPCZVO.KvlGhezEV1tqyR83WmaOBkQIG7fZRa',
    'ADMIN',
    'ACTIVE'
),
(
    'Saint-Petersburg',
    0,
    '1993-03-31',
    'katkopikat@gmail.com',
    'Katerina',
    'Rogozinskaya',
    'katkopikat',
    '3333 222111',
    '$2a$10$mm69bc9nQbb1NXbIaYFWoOzSTXxarnMfa6AV334NB4BvcOR6CoRJ6',
    'USER',
    'ACTIVE'
),
(
    'London',
    100000,
    '1970-01-01',
    'johnsmith@gmail.com',
    'John',
    'Smith',
    'johnsmith',
    '0000 000000',
    '$2a$10$ltlIVNXrdkvMCU/z/rbzHek05QOt9qlIv2W/60kKJRFaPy.o/zLQe',
    'USER',
    'INACTIVE'
)