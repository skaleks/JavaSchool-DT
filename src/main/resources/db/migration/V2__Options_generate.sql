-- Options
insert into options(COST, NAME, DESCRIPTION, PRICE, STATUS) VALUES
( 5, 'Blue' , 'Blue option', 1, 0 ),
(7,'Red', 'Red option', 1.5, 0),
(6, 'Green', 'Green option', 1, 0),
(9, 'Yellow', 'Yellow option', 2, 0),
(15, 'Magenta', 'Magenta option', 4, 0);

-- Excluded
insert into excluding_options(option_id, excluding_option_id) VALUES
(2,3), (3,2)