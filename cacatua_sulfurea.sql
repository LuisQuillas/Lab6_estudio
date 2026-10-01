-- =====================================================================
-- cacatua_sulfurea.sql
-- Script de creacion y carga inicial de la base de datos del juego
-- "La Cacatua Sulfurea y la Semilla de Girasol" (Lab 6 - GTICS)
--
-- Tablero de 6 filas x 5 columnas (fila 0 = arriba, columna 0 = izquierda).
-- El ave inicia en (0,2). La semilla esta en (5,4) y es nivel 0 (libre).
-- Energia inicial: 45 picotazos.
-- =====================================================================

CREATE DATABASE IF NOT EXISTS cacatua_sulfurea;
USE cacatua_sulfurea;

DROP TABLE IF EXISTS tablero;
DROP TABLE IF EXISTS tablero_inicial;
DROP TABLE IF EXISTS estado_juego;
DROP TABLE IF EXISTS estado_inicial;

-- Tablero EN JUEGO (se actualiza con cada picotazo)
CREATE TABLE tablero (
    id INT PRIMARY KEY AUTO_INCREMENT,
    fila INT NOT NULL,
    columna INT NOT NULL,
    nivel INT NOT NULL,          -- 0 libre, 1 blanda, 2 media, 3 dura
    es_semilla BOOLEAN NOT NULL DEFAULT FALSE,
    UNIQUE (fila, columna)
);

-- Respaldo INMUTABLE del tablero original (para el boton Reiniciar)
CREATE TABLE tablero_inicial (
    id INT PRIMARY KEY AUTO_INCREMENT,
    fila INT NOT NULL,
    columna INT NOT NULL,
    nivel INT NOT NULL,
    es_semilla BOOLEAN NOT NULL DEFAULT FALSE,
    UNIQUE (fila, columna)
);

-- Estado ACTUAL de la partida (una sola fila, id = 1)
CREATE TABLE estado_juego (
    id INT PRIMARY KEY,
    fila_actual INT NOT NULL,
    columna_actual INT NOT NULL,
    picotazos_restantes INT NOT NULL,
    finalizado BOOLEAN NOT NULL DEFAULT FALSE,
    gano BOOLEAN NOT NULL DEFAULT FALSE
);

-- Respaldo del estado inicial (posicion de partida y energia maxima)
CREATE TABLE estado_inicial (
    id INT PRIMARY KEY,
    fila_inicial INT NOT NULL,
    columna_inicial INT NOT NULL,
    energia_maxima INT NOT NULL
);

-- ---------------------------------------------------------------------
-- Carga del tablero (6 filas x 5 columnas)
-- ---------------------------------------------------------------------
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
