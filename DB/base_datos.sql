SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Limpiar la base de datos si ya existía a medias
-- -----------------------------------------------------
DROP DATABASE IF EXISTS `tecnostore_anasuarez`;
CREATE SCHEMA `tecnostore_anasuarez` DEFAULT CHARACTER SET utf8mb4;
USE `tecnostore_anasuarez`;

-- -----------------------------------------------------
-- 1. Tabla persona (padre)
-- -----------------------------------------------------
CREATE TABLE `persona` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(100) NOT NULL,
  `identificacion` VARCHAR(20) NOT NULL,
  `correo` VARCHAR(100) NOT NULL,
  `telefono` VARCHAR(20) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `identificacion_UNIQUE` (`identificacion` ASC),
  UNIQUE INDEX `correo_UNIQUE` (`correo` ASC)
) ENGINE = InnoDB;

-- -----------------------------------------------------
-- 2. Tabla cliente (hereda de persona)
-- -----------------------------------------------------
CREATE TABLE `cliente` (
  `id` INT NOT NULL,
  `fecha_ingreso` DATE NULL DEFAULT (CURRENT_DATE),
  `puntos` INT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_cliente_persona`
    FOREIGN KEY (`id`) REFERENCES `persona` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
) ENGINE = InnoDB;

-- -----------------------------------------------------
-- 3. Tabla empleado (hereda de persona)
-- -----------------------------------------------------
CREATE TABLE `empleado` (
  `id` INT NOT NULL,
  `cargo` VARCHAR(50) NULL,
  `fecha_ingreso` DATE NULL,
  `salario` DECIMAL(10,2) NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_empleado_persona`
    FOREIGN KEY (`id`) REFERENCES `persona` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
) ENGINE = InnoDB;

-- -----------------------------------------------------
-- 4. Tabla marca
-- -----------------------------------------------------
CREATE TABLE `marca` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `marca_nombre_UNIQUE` (`nombre` ASC)
) ENGINE = InnoDB;

-- -----------------------------------------------------
-- 5. Tabla sistema_operativo
-- -----------------------------------------------------
CREATE TABLE `sistema_operativo` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(50) NOT NULL,
  `version` VARCHAR(20) NULL,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB;

-- -----------------------------------------------------
-- 6. Tabla gama
-- -----------------------------------------------------
CREATE TABLE `gama` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nombre` ENUM('Alta', 'Media', 'Baja') NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `gama_nombre_UNIQUE` (`nombre` ASC)
) ENGINE = InnoDB;

-- -----------------------------------------------------
-- 7. Tabla celulares
-- -----------------------------------------------------
CREATE TABLE `celulares` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `marca_fk` INT NOT NULL,
  `sistema_fk` INT NOT NULL,
  `gama_fk` INT NOT NULL,
  `modelo` VARCHAR(100) NOT NULL,
  `precio` DECIMAL(10,2) NOT NULL,
  `stock` INT NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  INDEX `marca_fk_idx` (`marca_fk` ASC),
  INDEX `sistema_fk_idx` (`sistema_fk` ASC),
  INDEX `gama_fk_idx` (`gama_fk` ASC),
  CONSTRAINT `fk_celular_marca`
    FOREIGN KEY (`marca_fk`) REFERENCES `marca` (`id`)
    ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_celular_sistema`
    FOREIGN KEY (`sistema_fk`) REFERENCES `sistema_operativo` (`id`)
    ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_celular_gama`
    FOREIGN KEY (`gama_fk`) REFERENCES `gama` (`id`)
    ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB;

-- -----------------------------------------------------
-- 8. Tabla ventas
-- -----------------------------------------------------
CREATE TABLE `ventas` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `cliente_fk` INT NOT NULL,
  `empleado_fk` INT NOT NULL,
  `fecha` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `subtotal` DECIMAL(10,2) NOT NULL,
  `iva` DECIMAL(10,2) NOT NULL,
  `total` DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `cliente_fk_idx` (`cliente_fk` ASC),
  INDEX `empleado_fk_idx` (`empleado_fk` ASC),
  CONSTRAINT `fk_venta_cliente`
    FOREIGN KEY (`cliente_fk`) REFERENCES `cliente` (`id`)
    ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_venta_empleado`
    FOREIGN KEY (`empleado_fk`) REFERENCES `empleado` (`id`)
    ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB;

-- -----------------------------------------------------
-- 9. Tabla detalle_venta
-- -----------------------------------------------------
CREATE TABLE `detalle_venta` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `venta_fk` INT NOT NULL,
  `celular_fk` INT NOT NULL,
  `cantidad` INT NOT NULL,
  `precio_unitario` DECIMAL(10,2) NOT NULL,
  `subtotal` DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `venta_fk_idx` (`venta_fk` ASC),
  INDEX `celular_fk_idx` (`celular_fk` ASC),
  CONSTRAINT `fk_detalle_venta_venta`
    FOREIGN KEY (`venta_fk`) REFERENCES `ventas` (`id`)
    ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_detalle_venta_celular`
    FOREIGN KEY (`celular_fk`) REFERENCES `celulares` (`id`)
    ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB;

-- -----------------------------------------------------
-- 10. Tabla log_celulares (auditoría)
-- -----------------------------------------------------
CREATE TABLE `log_celulares` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `celular_fk` INT NOT NULL,
  `empleado_fk` INT NULL,
  `accion` VARCHAR(50) NOT NULL,
  `detalle_cambio` VARCHAR(255) NULL,
  `fecha_hora` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `celular_fk_idx` (`celular_fk` ASC),
  INDEX `empleado_fk_idx` (`empleado_fk` ASC),
  CONSTRAINT `fk_log_celular`
    FOREIGN KEY (`celular_fk`) REFERENCES `celulares` (`id`)
    ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_log_empleado`
    FOREIGN KEY (`empleado_fk`) REFERENCES `empleado` (`id`)
    ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB;

-- -----------------------------------------------------
-- Restaurar configuraciones
-- -----------------------------------------------------
SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

INSERT INTO marca (nombre) VALUES 
('Samsung'),
('Apple'),
('Xiaomi'),
('Motorola'),
('Huawei');

INSERT INTO sistema_operativo (nombre, version) VALUES 
('Android', '14'),
('Android', '13'),
('iOS', '17'),
('iOS', '16'),
('HarmonyOS', '4');

INSERT INTO gama (nombre) VALUES 
('Alta'),
('Media'),
('Baja');