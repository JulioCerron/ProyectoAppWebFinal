CREATE TABLE IF NOT EXISTS tbl_notificaciones_historial (
    id SERIAL PRIMARY KEY,
    incidencia_id INTEGER,
    mensaje TEXT NOT NULL,
    destinatario VARCHAR(100),
    fecha_envio TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    tipo VARCHAR(50)
);