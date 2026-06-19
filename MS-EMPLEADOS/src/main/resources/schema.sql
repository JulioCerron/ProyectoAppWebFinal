CREATE TABLE IF NOT EXISTS tbl_departamentos (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS tbl_empleados (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    dni VARCHAR(8) UNIQUE NOT NULL,
    cargo VARCHAR(100),
    departamento_id INTEGER REFERENCES tbl_departamentos(id),
    activo BOOLEAN DEFAULT TRUE
);

INSERT INTO tbl_departamentos (nombre) VALUES ('OPERACIONES') ON CONFLICT DO NOTHING;
INSERT INTO tbl_departamentos (nombre) VALUES ('LOGISTICA') ON CONFLICT DO NOTHING;
INSERT INTO tbl_departamentos (nombre) VALUES ('SST') ON CONFLICT DO NOTHING;