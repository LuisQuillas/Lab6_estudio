DELETE FROM tablero;
DELETE FROM tablero_inicial;
DELETE FROM estado_juego;
DELETE FROM estado_inicial;

INSERT INTO tablero (fila, columna, nivel, es_semilla) VALUES
(0,0,0,FALSE),(0,1,0,FALSE),(0,2,0,FALSE),(0,3,0,FALSE),(0,4,0,FALSE),
(1,0,3,FALSE),(1,1,2,FALSE),(1,2,3,FALSE),(1,3,1,FALSE),(1,4,3,FALSE),
(2,0,2,FALSE),(2,1,3,FALSE),(2,2,1,FALSE),(2,3,2,FALSE),(2,4,3,FALSE),
(3,0,1,FALSE),(3,1,2,FALSE),(3,2,2,FALSE),(3,3,1,FALSE),(3,4,2,FALSE),
(4,0,3,FALSE),(4,1,1,FALSE),(4,2,2,FALSE),(4,3,3,FALSE),(4,4,1,FALSE),
(5,0,2,FALSE),(5,1,1,FALSE),(5,2,2,FALSE),(5,3,1,FALSE),(5,4,0,TRUE);

INSERT INTO tablero_inicial (fila, columna, nivel, es_semilla)
SELECT fila, columna, nivel, es_semilla FROM tablero;

INSERT INTO estado_juego (id, fila_actual, columna_actual, picotazos_restantes, finalizado, gano)
VALUES (1, 0, 2, 45, FALSE, FALSE);

INSERT INTO estado_inicial (id, fila_inicial, columna_inicial, energia_maxima)
VALUES (1, 0, 2, 45);
