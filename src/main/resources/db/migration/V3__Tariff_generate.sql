-- Tarrifs
insert into tariffs(NAME, PRICE, STATUS, DESCRIPTION) VALUES
('Super', 50, 'ACTIVE', 'Best tariff'),
('Not super', 100, 'ACTIVE', 'Bad tariff'),
('Simple', 10, 'ACTIVE', 'Simple tariff');

insert into tariff_option(tariff_id, option_id) VALUES
(1,1), (1,2), (1,3), (1,4), (1,5),
(2,1), (2,2), (2,4),
(3,5)