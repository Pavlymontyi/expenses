-- noinspection SqlDialectInspectionForFile

INSERT INTO public.persons (id, group_id, name) VALUES (1, 1, 'Паша');

INSERT INTO public.cards (id, active, balance, bank, open_date, person_id) VALUES (2, true, 60000, 'Совкомбанк Активная', '2020-10-17 00:00:00.000000', 1);
INSERT INTO public.cards (id, active, balance, bank, open_date, person_id) VALUES (3, true, 20000, 'Сбербанк Активная', '2020-10-17 00:00:00.000000', 1);
INSERT INTO public.cards (id, active, balance, bank, open_date, person_id) VALUES (4, false, 30000, 'Совкомбанк НЕактивная', '2020-10-17 00:00:00.000000', 1);

INSERT INTO public.deposits (id, active, duration, end_date, percent, refillable, revenue, start_date, card_id) VALUES (5, true, 365, '2021-01-09 00:00:00.000000', 7.6, true, 0, '2020-01-10 04:00:00.000000', 2);
INSERT INTO public.deposits (id, active, duration, end_date, percent, refillable, revenue, start_date, card_id) VALUES (6, true, 365, '2021-10-17 00:00:00.000000', 5.7, true, 0, '2020-10-17 04:00:00.000000', 2);

INSERT INTO public.incomes (id, date, amount, deposit_id, person_id) VALUES (7, '2020-01-10 04:00:00.000000', 50000, 5, 1);
INSERT INTO public.incomes (id, date, amount, deposit_id, person_id) VALUES (8, '2020-10-17 04:00:00.000000', 50000, 5, 1);
INSERT INTO public.incomes (id, date, amount, deposit_id, person_id) VALUES (9, '2020-10-17 04:00:00.000000', 100000, 6, 1);
INSERT INTO public.incomes (id, date, amount, deposit_id, person_id) VALUES (10, '2020-11-17 04:00:00.000000', 50000, 6, 1);