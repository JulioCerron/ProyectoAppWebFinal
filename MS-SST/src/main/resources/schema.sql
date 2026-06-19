CREATE TABLE IF NOT EXISTS tbl_observaciones (
    id SERIAL PRIMARY KEY,
    incidencia_id INTEGER NOT NULL,
    descripcion TEXT NOT NULL,
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    observado_por VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS tbl_evaluaciones_riesgo (
    id SERIAL PRIMARY KEY,
    observacion_id INTEGER REFERENCES tbl_observaciones(id),
    nivel_riesgo VARCHAR(20),
    medidas_preventivas TEXT,
    aprobado BOOLEAN DEFAULT FALSE
);