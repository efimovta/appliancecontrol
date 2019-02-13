
INSERT INTO heating_mode (id, name) VALUES (1, 'Top heating');
INSERT INTO heating_mode (id, name) VALUES (2, 'Bottom heating');
INSERT INTO heating_mode (id, name) VALUES (3, 'Convection');


INSERT INTO recipe (id, name, temperature, cooking_time_seconds, heating_mode_id,
  description) 
VALUES (1, 'Timur best chicken', 255, 600, 3,
  'Hello friends! My recipe steps too simple: 1. Get chicken 2. Add salt 3. Move chicken into oven 4. Accept recipe params 5. Well done!' 
);


INSERT INTO oven (id, name, door_is_open, light_bulb_is_on, cooking, temperature, cooking_time_seconds, heating_mode_id, recipe_id)
VALUES (1, 'Timur oven', false, false, false, 0, 0, null, 1);
