DELIMITER //

-- 1. Validar precio y stock antes de INSERT en celulares
CREATE TRIGGER trg_celular_precio_stock_bi
BEFORE INSERT ON celulares
FOR EACH ROW
BEGIN
    IF NEW.precio <= 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'El precio debe ser mayor a 0';
    END IF;
    IF NEW.stock < 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'El stock no puede ser negativo';
    END IF;
END //

-- 2. Validar precio y stock antes de UPDATE en celulares
CREATE TRIGGER trg_celular_precio_stock_bu
BEFORE UPDATE ON celulares
FOR EACH ROW
BEGIN
    IF NEW.precio <= 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'El precio debe ser mayor a 0';
    END IF;
    IF NEW.stock < 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'El stock no puede ser negativo';
    END IF;
END //

-- 3. Validar correo antes de INSERT en persona
CREATE TRIGGER trg_persona_correo_bi
BEFORE INSERT ON persona
FOR EACH ROW
BEGIN
    IF fn_correo_valido(NEW.correo) = 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Formato de correo invalido';
    END IF;
END //

-- 4. Validar stock antes de INSERT en detalle_venta
CREATE TRIGGER trg_detalle_stock_bi
BEFORE INSERT ON detalle_venta
FOR EACH ROW
BEGIN
    DECLARE v_stock INT;

    IF NEW.cantidad <= 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'La cantidad debe ser mayor a 0';
    END IF;

    SELECT stock INTO v_stock FROM celulares WHERE id = NEW.celular_fk;

    IF v_stock < NEW.cantidad THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Stock insuficiente';
    END IF;
END //

-- 5. Descontar stock despues de INSERT en detalle_venta
CREATE TRIGGER trg_detalle_descontar_ai
AFTER INSERT ON detalle_venta
FOR EACH ROW
BEGIN
    UPDATE celulares
    SET stock = stock - NEW.cantidad
    WHERE id = NEW.celular_fk;
END //

-- 6. Log de cambios en celulares (precio o stock)
CREATE TRIGGER trg_celular_log_au
AFTER UPDATE ON celulares
FOR EACH ROW
BEGIN
    IF OLD.precio <> NEW.precio THEN
        INSERT INTO log_celulares (celular_fk, empleado_fk, accion, detalle_cambio)
        VALUES (NEW.id, NULL, 'CAMBIO_PRECIO',
                CONCAT('Precio: ', OLD.precio, ' -> ', NEW.precio));
    END IF;

    IF OLD.stock <> NEW.stock THEN
        INSERT INTO log_celulares (celular_fk, empleado_fk, accion, detalle_cambio)
        VALUES (NEW.id, NULL, 'CAMBIO_STOCK',
                CONCAT('Stock: ', OLD.stock, ' -> ', NEW.stock));
    END IF;
END //

DELIMITER ;