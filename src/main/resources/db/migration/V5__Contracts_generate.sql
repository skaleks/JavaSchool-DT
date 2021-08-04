-- Contracts
insert into contracts(number, price, status, tariff_tariff_id, user_user_id) VALUES
('89992477803', 56.5, 0, 1, 1),
('89818280578', 107, 0, 2, 2),
('89811653711', 10, 0, 3, 1),
('88005553535', 57, 0, 1, 3);

-- Contract options
insert into contract_options(contract_id, option_id) VALUES
(1,1), (1,2), (1,5),
(2,3), (2,4), (2,5),
(4,1), (4,4), (4,5);