-- ============================================================
-- CREAR BASE DE DATOS
-- ============================================================

CREATE DATABASE matricula_db;


-- ============================================================
-- IMPORTANTE:
-- Después de crear la BD, conéctate a "matricula_db"
-- y ejecuta desde aquí el resto del script.
-- ============================================================


-- ============================================================
-- LIMPIAR DATOS ANTERIORES
-- ============================================================

DELETE FROM matriculas;
DELETE FROM cursos;
DELETE FROM aulas;
DELETE FROM alumnos;


-- ============================================================
-- AULAS
-- ============================================================

INSERT INTO aulas (nombre, capacidad) VALUES
('Aula 101', 2),
('Aula 102', 2),
('Aula 103', 2),
('Aula 104', 2),
('Aula 105', 2),
('Aula 201', 2),
('Aula 202', 2),
('Aula 203', 2),
('Aula 204', 2),
('Aula 205', 2);


-- ============================================================
-- ALUMNOS
-- ============================================================

INSERT INTO alumnos (nombre, ciclo) VALUES
('Oliver Ricalde', 1),
('Carlos Mendoza', 1),
('Andrea Torres', 1),
('Luis Ramirez', 2),
('Maria Flores', 2),
('Juan Perez', 2),
('Daniel Castro', 3),
('Lucia Vargas', 3),
('Pedro Sanchez', 3),
('Sofia Morales', 4),
('Diego Fernandez', 4),
('Valeria Rojas', 4),
('Miguel Quispe', 5),
('Camila Herrera', 5),
('Jose Castillo', 5),
('Ana Torres', 6),
('Fernando Diaz', 6),
('Gabriela Leon', 6),
('Ricardo Salazar', 7),
('Paola Medina', 7),
('Andres Chavez', 7),
('Carla Espinoza', 8),
('Marco Gutierrez', 8),
('Daniela Paredes', 8),
('Sebastian Ruiz', 9),
('Nicole Campos', 9),
('Jorge Navarro', 9),
('Patricia Mendoza', 10),
('Alonso Vargas', 10),
('Valentina Cruz', 10);


-- ============================================================
-- CICLO 1
-- ============================================================

INSERT INTO cursos (
    nombre,
    ciclo,
    hora_inicio_manana,
    hora_fin_manana,
    hora_inicio_tarde,
    hora_fin_tarde,
    hora_inicio_noche,
    hora_fin_noche,
    aula_id
) VALUES
(
    'Matematica I',
    1,
    '08:00', '10:00',
    '14:00', '16:00',
    '18:00', '20:00',
    1
),
(
    'Programacion I',
    1,
    '10:00', '12:00',
    '16:00', '18:00',
    '20:00', '22:00',
    2
),
(
    'Introduccion a la Ingenieria',
    1,
    '08:00', '10:00',
    '14:00', '16:00',
    '18:00', '20:00',
    3
),
(
    'Comunicacion I',
    1,
    '12:00', '14:00',
    '16:00', '18:00',
    '20:00', '22:00',
    4
);


-- ============================================================
-- CICLO 2
-- ============================================================

INSERT INTO cursos (
    nombre,
    ciclo,
    hora_inicio_manana,
    hora_fin_manana,
    hora_inicio_tarde,
    hora_fin_tarde,
    hora_inicio_noche,
    hora_fin_noche,
    aula_id
) VALUES
(
    'Matematica II',
    2,
    '08:00', '10:00',
    '14:00', '16:00',
    '18:00', '20:00',
    5
),
(
    'Programacion II',
    2,
    '10:00', '12:00',
    '16:00', '18:00',
    '20:00', '22:00',
    6
),
(
    'Base de Datos I',
    2,
    '08:00', '10:00',
    '14:00', '16:00',
    '18:00', '20:00',
    7
),
(
    'Estadistica I',
    2,
    '12:00', '14:00',
    '16:00', '18:00',
    '20:00', '22:00',
    8
);


-- ============================================================
-- CICLO 3
-- ============================================================

INSERT INTO cursos (
    nombre,
    ciclo,
    hora_inicio_manana,
    hora_fin_manana,
    hora_inicio_tarde,
    hora_fin_tarde,
    hora_inicio_noche,
    hora_fin_noche,
    aula_id
) VALUES
(
    'Estructuras de Datos',
    3,
    '08:00', '10:00',
    '14:00', '16:00',
    '18:00', '20:00',
    9
),
(
    'Base de Datos II',
    3,
    '10:00', '12:00',
    '16:00', '18:00',
    '20:00', '22:00',
    10
),
(
    'Sistemas Operativos',
    3,
    '12:00', '14:00',
    '14:00', '16:00',
    '18:00', '20:00',
    1
),
(
    'Redes I',
    3,
    '14:00', '16:00',
    '16:00', '18:00',
    '20:00', '22:00',
    2
);


-- ============================================================
-- CICLO 4
-- ============================================================

INSERT INTO cursos (
    nombre,
    ciclo,
    hora_inicio_manana,
    hora_fin_manana,
    hora_inicio_tarde,
    hora_fin_tarde,
    hora_inicio_noche,
    hora_fin_noche,
    aula_id
) VALUES
(
    'Ingenieria de Software I',
    4,
    '08:00', '10:00',
    '14:00', '16:00',
    '18:00', '20:00',
    3
),
(
    'Programacion Web I',
    4,
    '10:00', '12:00',
    '16:00', '18:00',
    '20:00', '22:00',
    4
),
(
    'Arquitectura de Computadores',
    4,
    '12:00', '14:00',
    '14:00', '16:00',
    '18:00', '20:00',
    5
),
(
    'Analisis de Sistemas',
    4,
    '14:00', '16:00',
    '16:00', '18:00',
    '20:00', '22:00',
    6
);


-- ============================================================
-- CICLO 5
-- ============================================================

INSERT INTO cursos (
    nombre,
    ciclo,
    hora_inicio_manana,
    hora_fin_manana,
    hora_inicio_tarde,
    hora_fin_tarde,
    hora_inicio_noche,
    hora_fin_noche,
    aula_id
) VALUES
(
    'Ingenieria de Software II',
    5,
    '08:00', '10:00',
    '14:00', '16:00',
    '18:00', '20:00',
    7
),
(
    'Programacion Web II',
    5,
    '10:00', '12:00',
    '16:00', '18:00',
    '20:00', '22:00',
    8
),
(
    'Seguridad Informatica',
    5,
    '12:00', '14:00',
    '14:00', '16:00',
    '18:00', '20:00',
    9
),
(
    'Gestion de Proyectos',
    5,
    '14:00', '16:00',
    '16:00', '18:00',
    '20:00', '22:00',
    10
);


-- ============================================================
-- CICLOS 6 - 10
-- ============================================================

INSERT INTO cursos (
    nombre,
    ciclo,
    hora_inicio_manana,
    hora_fin_manana,
    hora_inicio_tarde,
    hora_fin_tarde,
    hora_inicio_noche,
    hora_fin_noche,
    aula_id
) VALUES

-- CICLO 6
(
    'Cloud Computing',
    6,
    '08:00','10:00',
    '14:00','16:00',
    '18:00','20:00',
    1
),

(
    'DevOps',
    6,
    '10:00','12:00',
    '16:00','18:00',
    '20:00','22:00',
    2
),

(
    'Desarrollo Movil',
    6,
    '12:00','14:00',
    '14:00','16:00',
    '18:00','20:00',
    3
),

(
    'Calidad de Software',
    6,
    '14:00','16:00',
    '16:00','18:00',
    '20:00','22:00',
    4
),

-- CICLO 7
(
    'Arquitectura de Software',
    7,
    '08:00','10:00',
    '14:00','16:00',
    '18:00','20:00',
    5
),

(
    'Desarrollo Backend',
    7,
    '10:00','12:00',
    '16:00','18:00',
    '20:00','22:00',
    6
),

(
    'Desarrollo Frontend',
    7,
    '12:00','14:00',
    '14:00','16:00',
    '18:00','20:00',
    7
),

(
    'Bases de Datos Avanzadas',
    7,
    '14:00','16:00',
    '16:00','18:00',
    '20:00','22:00',
    8
),

-- CICLO 8
(
    'Inteligencia Artificial',
    8,
    '08:00','10:00',
    '14:00','16:00',
    '18:00','20:00',
    9
),

(
    'Machine Learning',
    8,
    '10:00','12:00',
    '16:00','18:00',
    '20:00','22:00',
    10
),

(
    'Ciberseguridad',
    8,
    '12:00','14:00',
    '14:00','16:00',
    '18:00','20:00',
    1
),

(
    'Auditoria de Sistemas',
    8,
    '14:00','16:00',
    '16:00','18:00',
    '20:00','22:00',
    2
),

-- CICLO 9
(
    'Proyecto de Software I',
    9,
    '08:00','10:00',
    '14:00','16:00',
    '18:00','20:00',
    3
),

(
    'Cloud Architecture',
    9,
    '10:00','12:00',
    '16:00','18:00',
    '20:00','22:00',
    4
),

(
    'DevSecOps',
    9,
    '12:00','14:00',
    '14:00','16:00',
    '18:00','20:00',
    5
),

(
    'Gestion de Servicios TI',
    9,
    '14:00','16:00',
    '16:00','18:00',
    '20:00','22:00',
    6
),

-- CICLO 10
(
    'Proyecto de Software II',
    10,
    '08:00','10:00',
    '14:00','16:00',
    '18:00','20:00',
    7
),

(
    'Arquitectura Empresarial',
    10,
    '10:00','12:00',
    '16:00','18:00',
    '20:00','22:00',
    8
),

(
    'Gestion de TI',
    10,
    '12:00','14:00',
    '14:00','16:00',
    '18:00','20:00',
    9
),

(
    'Seminario de Tesis',
    10,
    '14:00','16:00',
    '16:00','18:00',
    '20:00','22:00',
    10
);