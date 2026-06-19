CREATE TABLE IF NOT EXISTS tbl_materiales (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    stock_actual INTEGER DEFAULT 0,
    stock_minimo INTEGER DEFAULT 5,
    unidad_medida VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS tbl_solicitudes_material (
    id SERIAL PRIMARY KEY,
    incidencia_id INTEGER,
    material_id INTEGER REFERENCES tbl_materiales(id),
    cantidad INTEGER NOT NULL,
    estado VARCHAR(20) DEFAULT 'PENDIENTE',
    fecha_solicitud TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);