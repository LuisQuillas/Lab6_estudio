-- Version H2 (solo para demo local sin MySQL) del mismo esquema de cacatua_sulfurea.sql
CREATE TABLE IF NOT EXISTS tablero (
    id INT AUTO_INCREMENT PRIMARY KEY,
    fila INT NOT NULL,
    columna INT NOT NULL,
    nivel INT NOT NULL,
    es_semilla BOOLEAN NOT NULL DEFAULT FALSE,
    UNIQUE (fila, columna)
);

CREATE TABLE IF NOT EXISTS tablero_inicial (
    id INT AUTO_INCREMENT PRIMARY KEY,
    fila INT NOT NULL,
    columna INT NOT NULL,
    nivel INT NOT NULL,
    es_semilla BOOLEAN NOT NULL DEFAULT FALSE,
    UNIQUE (fila, columna)
);

CREATE TABLE IF NOT EXISTS estado_juego (
    id INT PRIMARY KEY,
    fila_actual INT NOT NULL,
    columna_actual INT NOT NULL,
    picotazos_restantes INT NOT NULL,
    finalizado BOOLEAN NOT NULL DEFAULT FALSE,
    gano BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS estado_inicial (
    id INT PRIMARY KEY,
    fila_inicial INT NOT NULL,
    columna_inicial INT NOT NULL,
    energia_maxima INT NOT NULL
);
