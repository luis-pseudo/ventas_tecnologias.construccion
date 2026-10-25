CREATE TABLE IF NOT EXISTS productos (
    id VARCHAR(50) PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    precio NUMERIC(10, 2) NOT NULL,
    stock INT NOT NULL
);


CREATE TABLE IF NOT EXISTS ventas (
    id VARCHAR(50) PRIMARY KEY,
    fecha TIMESTAMP NOT NULL,
    total NUMERIC(10, 2) NOT NULL
);


CREATE TABLE IF NOT EXISTS detalle_venta (
    id SERIAL PRIMARY KEY,
    venta_id VARCHAR(50) REFERENCES ventas(id) ON DELETE CASCADE,
    producto_id VARCHAR(50) REFERENCES productos(id),
    cantidad INT NOT NULL,
    subtotal NUMERIC(10, 2) NOT NULL
);