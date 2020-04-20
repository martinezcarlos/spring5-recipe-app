insert into recipe (id, description, prep_time, cook_time, servings, difficulty)
values (1, 'Test Recipe', 15, 30, 4, 'EASY');

insert into ingredient (id, description, amount, recipe_id, unit_of_measure_id)
values (1, 'Salt', 50, 1, 1);
