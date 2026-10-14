DELIMITER //

-- 1. Registrar venta completa
CREATE PROCEDURE sp_registrar_venta(
    IN p_cliente_id INT,
    IN p_empleado_id INT,
    IN p_subtotal DECIMAL(10,2),
    IN p_iva DECIMAL(10,2),
    IN p_total DECIMAL(10,2),
    OUT p_id_venta INT
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        RESIGNAL;
    END;

    START TRANSACTION;

    INSERT INTO ventas (cliente_fk, empleado_fk, fecha, subtotal, iva, total)
    VALUES (p_cliente_id, p_empleado_id, NOW(), p_subtotal, p_iva, p_total);

    SET p_id_venta = LAST_INSERT_ID();

    COMMIT;
END //

-- 2. Anular venta (devuelve stock y borra)
CREATE PROCEDURE sp_anular_venta(IN p_id_venta INT)
BEGIN
    DECLARE v_celular_id INT;
    DECLARE v_cantidad INT;
    DECLARE done INT DEFAULT 0;

    DECLARE cur CURSOR FOR
        SELECT celular_fk, cantidad FROM detalle_venta WHERE venta_fk = p_id_venta;

    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;

    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        RESIGNAL;
    END;

    START TRANSACTION;

    OPEN cur;
    read_loop: LOOP
        FETCH cur INTO v_celular_id, v_cantidad;
        IF done = 1 THEN
            LEAVE read_loop;
        END IF;

        UPDATE celulares
        SET stock = stock + v_cantidad
        WHERE id = v_celular_id;
    END LOOP;
    CLOSE cur;

    DELETE FROM detalle_venta WHERE venta_fk = p_id_venta;
    DELETE FROM ventas WHERE id = p_id_venta;

    COMMIT;
END //

-- 3. Reponer stock
CREATE PROCEDURE sp_reponer_stock(IN p_id_celular INT, IN p_cantidad INT)
BEGIN
    UPDATE celulares
    SET stock = stock + p_cantidad
    WHERE id = p_id_celular;
END //

-- 4. Reporte de ventas
CREATE PROCEDURE sp_reporte_ventas()
BEGIN
    SELECT
        v.id AS venta_id,
        v.fecha,
        c.nombre AS cliente,
        e.nombre AS empleado,
        v.subtotal,
        v.iva,
        v.total
    FROM ventas v
    JOIN cliente cl ON v.cliente_fk = cl.id
    JOIN persona c ON cl.id = c.id
    JOIN empleado em ON v.empleado_fk = em.id
    JOIN persona e ON em.id = e.id
    ORDER BY v.fecha DESC;
END //

DELIMITER ;