
INSERT INTO heating_mode (id, name) VALUES (1, 'Top heating');
INSERT INTO heating_mode (id, name) VALUES (2, 'Bottom heating');
INSERT INTO heating_mode (id, name) VALUES (3, 'Convection');

INSERT INTO recipe (id, name, temperature, cooking_time_seconds, heating_mode_id) VALUES (1, 'Timur best chicken', 255, 600, 3);

INSERT INTO oven (id, name, door, light_bulb, cook, temperature, remain_time_seconds, heating_mode_id, last_applied_recipe_id) VALUES (1, 'Timur oven', false, false, false, 0, 0, 1, 1);
