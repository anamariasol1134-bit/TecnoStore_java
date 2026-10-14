

DELIMITER //

-- 1. Validar correo
CREATE FUNCTION fn_correo_valido(p_correo VARCHAR(100))
RETURNS TINYINT
DETERMINISTIC
BEGIN
    IF p_correo REGEXP '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$' THEN
        RETURN 1;
    ELSE
        RETURN 0;
    END IF;
END //

-- 2. Validar telefono
CREATE FUNCTION fn_telefono_valido(p_telefono VARCHAR(20))
RETURNS TINYINT
DETERMINISTIC
BEGIN
    IF p_telefono REGEXP '^[0-9]{7,15}$' THEN
        RETURN 1;
    ELSE
        RETURN 0;
    END IF;
END //

-- 3. Hay stock suficiente
CREATE FUNCTION fn_hay_stock(p_id_celular INT, p_cantidad INT)
RETURNS TINYINT
READS SQL DATA
BEGIN
    DECLARE v_stock INT;
    SELECT stock INTO v_stock FROM celulares WHERE id = p_id_celular;
    IF v_stock IS NULL THEN
        RETURN 0;
    END IF;
    IF v_stock >= p_cantidad THEN
        RETURN 1;
    ELSE
        RETURN 0;
    END IF;
END //

-- 4. Descuento por gama
CREATE FUNCTION fn_descuento_gama(p_gama VARCHAR(20))
RETURNS DECIMAL(4,2)
DETERMINISTIC
BEGIN
    IF p_gama = 'Alta' THEN
        RETURN 0.00;
    ELSEIF p_gama = 'Media' THEN
        RETURN 0.05;
    ELSEIF p_gama = 'Baja' THEN
        RETURN 0.10;
    ELSE
        RETURN 0.00;
    END IF;
END //

-- 5. Total con IVA
CREATE FUNCTION fn_total_venta(p_subtotal DECIMAL(10,2))
RETURNS DECIMAL(10,2)
DETERMINISTIC
BEGIN
    RETURN p_subtotal * 1.19;
END //

DELIMITER ;