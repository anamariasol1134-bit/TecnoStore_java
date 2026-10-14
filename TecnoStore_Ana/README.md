# 📱 TecnoStore

**TecnoStore** es una aplicación de consola desarrollada en Java para administrar una tienda virtual de celulares. Permite gestionar marcas, celulares, clientes, empleados y ventas, con persistencia de datos en MySQL mediante JDBC.

Este proyecto académico integra una arquitectura por capas inspirada en **MVC + DAO**, validaciones de datos, lógica de negocio y consultas SQL para apoyar la administración básica de una tienda.

![Java](https://img.shields.io/badge/Java-21-orange?logo=openjdk&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-8.0-4479A1?logo=mysql&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-Build-C71A36?logo=apachemaven&logoColor=white)
![Licencia](https://img.shields.io/badge/Licencia-MIT-green)

> **Nota académica:** este README documenta el alcance descrito para el proyecto. Los nombres de clases, scripts y funciones deben coincidir con los archivos presentes en el repositorio. La licencia MIT solo aplica si se incluye y mantiene el archivo `LICENSE` correspondiente.

## 📚 Tabla de contenidos

- [Descripción del proyecto](#-descripción-del-proyecto)
- [Características principales](#-características-principales)
- [Estructura del proyecto](#-estructura-del-proyecto)
- [Tecnologías utilizadas](#-tecnologías-utilizadas)
- [Instalación y configuración](#-instalación-y-configuración)
- [Ejemplos de ejecución](#-ejemplos-de-ejecución)
- [Conexión con MySQL](#-conexión-con-mysql)
- [Patrones de diseño y principios SOLID](#-patrones-de-diseño-y-principios-solid)
- [Funciones, procedimientos y triggers SQL](#-funciones-procedimientos-y-triggers-sql)
- [Reportes generados](#-reportes-generados)
- [Autor](#-autor)
- [Licencia](#-licencia)

## 🎯 Descripción del proyecto

### ¿Qué es TecnoStore?

TecnoStore es un sistema de consola que permite administrar operaciones esenciales de una tienda de celulares. La aplicación se conecta a una base de datos MySQL usando **JDBC**, sin utilizar frameworks ORM como Hibernate ni frameworks de aplicación como Spring.

### Objetivo

Aplicar conocimientos de programación orientada a objetos, organización por capas, acceso a datos, validación de entradas, consultas SQL y generación de reportes en un caso práctico.

### Público objetivo

- Docentes y evaluadores del proyecto académico.
- Estudiantes que estén aprendiendo Java, JDBC, Maven y MySQL.
- Personas interesadas en revisar un ejemplo educativo de arquitectura por capas.

## ✨ Características principales

| Módulo | Funcionalidades |
|---|---|
| **Marcas** | Registrar, listar, buscar, actualizar y eliminar marcas de celulares. |
| **Celulares** | Crear, consultar, actualizar y eliminar celulares; asociar marca, sistema operativo y gama; administrar precio y stock. |
| **Empleados** | Gestionar datos personales, identificación, correo, teléfono, cargo y salario. |
| **Clientes** | Registrar y consultar clientes, con validaciones de identificación, correo y teléfono. |
| **Ventas** | Registrar ventas asociadas a un cliente y un empleado, con varios celulares por operación y cálculo del IVA del 19 %. |
| **Reportes** | Consultar celulares con stock bajo, top 3 de celulares más vendidos y ventas agrupadas por mes. Puede generarse el archivo `reporte_ventas.txt`. |

### Validaciones relevantes

- **Celulares:** precio entre `$50.000` y `$20.000.000`; stock entre `0` y `10.000` unidades.
- **Empleados:** correo con formato válido, identificación única y salario mínimo de `$1.000.000`.
- **Clientes:** identificación única y validación del formato del correo y del teléfono.
- **Ventas:** comprobación de stock disponible y cálculo automático del IVA del 19 %.

Los valores monetarios se presentan en pesos colombianos como referencia funcional del proyecto. Las reglas efectivas dependen de las validaciones implementadas en Java y en los scripts SQL.

## 📁 Estructura del proyecto

```text
TecnoStore_Ana/
├── src/
│   └── main/
│       └── java/
│           ├── main/
│           │   └── MenuPrincipal.java
│           ├── control/
│           │   ├── MarcaControl.java
│           │   ├── CelularControl.java
│           │   ├── EmpleadoControl.java
│           │   ├── ClienteControl.java
│           │   ├── VentaControl.java
│           │   ├── ReporteControl.java
│           │   └── GeneralControl.java
│           ├── dao/
│           │   ├── MarcaDao.java
│           │   ├── SistemaOperativoDao.java
│           │   ├── GamaDao.java
│           │   ├── CelularDao.java
│           │   ├── ClienteDao.java
│           │   ├── EmpleadoDao.java
│           │   └── VentaDao.java
│           ├── modelo/
│           │   ├── Persona.java
│           │   ├── Cliente.java
│           │   ├── Empleado.java
│           │   ├── Marca.java
│           │   ├── SistemaOperativo.java
│           │   ├── CategoriaGama.java
│           │   ├── Celular.java
│           │   ├── ItemVenta.java
│           │   └── Venta.java
│           ├── patron/
│           │   ├── ConexionDB.java
│           │   └── FactoryCelular.java
│           ├── util/
│           │   ├── Validador.java
│           │   ├── DatoInvalido.java
│           │   ├── ReporteUtils.java
│           │   └── ArchivoUtils.java
│           ├── vista/
│           │   ├── MenuMarca.java
│           │   ├── MenuCelular.java
│           │   ├── MenuEmpleado.java
│           │   ├── MenuCliente.java
│           │   ├── MenuVenta.java
│           │   └── MenuReporte.java
│           └── vistaValidaciones/
│               ├── Entrada.java
│               ├── Entero.java
│               ├── Decimal.java
│               └── Texto.java
├── DB/
│   ├── base_datos.sql
│   ├── funciones.sql
│   ├── procedimiento.sql
│   └── triggers.sql
├── reporte_ventas.txt
└── pom.xml
```

> El árbol anterior resume la estructura esperada según la descripción del proyecto. Si algún nombre o archivo difiere en el repositorio real, actualiza el árbol para que refleje el código entregado.

### ¿Qué hace cada capa?

| Carpeta | Responsabilidad |
|---|---|
| `main` | Contiene el punto de entrada y el menú principal desde el que se inicia la aplicación. |
| `control` | Coordina las acciones del usuario, las reglas del negocio y las operaciones de los DAO. |
| `dao` | Encapsula el acceso a MySQL: consultas, inserciones, actualizaciones y eliminaciones. |
| `modelo` | Define las entidades del dominio, sus atributos y relaciones. |
| `patron` | Contiene componentes asociados a patrones de diseño, como la conexión a la base de datos y la fábrica de celulares. |
| `util` | Agrupa validadores, excepciones y utilidades para reportes y archivos. |
| `vista` | Presenta los menús de consola para cada módulo. |
| `vistaValidaciones` | Centraliza la lectura y validación de entradas de texto, enteros y decimales. |
| `DB` | Contiene los scripts SQL para crear la base de datos y configurar funciones, procedimientos y triggers. |

### Flujo general de una operación

1. El usuario selecciona una opción en la vista.
2. El controlador recibe la solicitud y coordina la operación.
3. El DAO ejecuta la consulta correspondiente mediante JDBC.
4. MySQL procesa la operación y devuelve el resultado.
5. La aplicación informa el resultado en la consola.

## 🛠️ Tecnologías utilizadas

| Tecnología | Versión | Uso |
|---|---|---|
| Java | 21 | Lenguaje principal y programación orientada a objetos. |
| Apache Maven | Según instalación del entorno | Gestión de dependencias y compilación del proyecto. |
| MySQL | 8.0 | Base de datos relacional. |
| JDBC | API incluida en Java | Conexión y ejecución de operaciones SQL desde Java. |
| MySQL Connector/J | 8.4.0 | Controlador JDBC para conectarse a MySQL. |
| NetBeans | 25 | Entorno de desarrollo recomendado para abrir y ejecutar el proyecto. |
| Stream API | Java 21 | Procesamiento y agrupación de datos para informes. |

## 🚀 Instalación y configuración

### 1. Requisitos previos

Antes de ejecutar el proyecto, instala y verifica:

- **JDK 21**.
- **MySQL Server 8.0** y un cliente como MySQL Workbench o la consola de MySQL.
- **Apache Maven**, si no vas a usar el Maven integrado de NetBeans.
- **NetBeans 25** (recomendado).
- Git, si vas a clonar el repositorio.

Comprueba las instalaciones desde una terminal:

```bash
java -version
mvn -version
```

La versión de Java debe corresponder al JDK 21. El comando de Maven debe mostrar una instalación funcional y la versión de Java que está utilizando.

### 2. Clonar el repositorio

Reemplaza la URL de ejemplo por la URL real del repositorio:

```bash
git clone <URL_DEL_REPOSITORIO>
cd TecnoStore_Ana
```

Si ya tienes el proyecto descargado, abre la carpeta que contiene `pom.xml`.

### 3. Crear la base de datos

1. Inicia el servicio de MySQL.
2. Abre MySQL Workbench o una terminal de MySQL.
3. Ejecuta los scripts de la carpeta `DB` **en el orden indicado**.

| Orden | Archivo | Propósito |
|---:|---|---|
| 1 | `base_datos.sql` | Crea la base de datos y las tablas necesarias. |
| 2 | `funciones.sql` | Define las funciones de validación y cálculo. |
| 3 | `procedimiento.sql` | Define los procedimientos almacenados. |
| 4 | `triggers.sql` | Define las reglas automáticas asociadas a las operaciones. |

Si usas la consola de MySQL, puedes ejecutar cada archivo desde la raíz del proyecto con una ruta ajustada a tu equipo:

```sql
SOURCE /ruta/al/proyecto/DB/base_datos.sql;
SOURCE /ruta/al/proyecto/DB/funciones.sql;
SOURCE /ruta/al/proyecto/DB/procedimiento.sql;
SOURCE /ruta/al/proyecto/DB/triggers.sql;
```

**Importante:** `SOURCE` es un comando del cliente de MySQL, no una instrucción SQL que se ejecuta dentro de cualquier editor. En MySQL Workbench, abre cada archivo y ejecútalo por separado. Asegúrate de que el usuario de MySQL tenga permisos para crear funciones, procedimientos y triggers.

El nombre de la base de datos definido para el proyecto es:

```sql
tecnostore_anasuarez
```

### 4. Configurar la conexión JDBC

Busca `ConexionDB.java` dentro de `src/main/java/patron/`. Ajusta la URL, el usuario y la contraseña según tu instalación local. La estructura puede variar según la implementación real; el ejemplo siguiente es ilustrativo:

```java
String url = "jdbc:mysql://localhost:3306/tecnostore_anasuarez";
String usuario = "root";
String contrasena = "TU_CONTRASENA";
```

No reemplaces la implementación completa de `ConexionDB` sin revisar primero cómo está construida. Verifica que use la URL y los datos de conexión correctos y que gestione adecuadamente los errores de conexión.

**Seguridad:** no publiques contraseñas reales en el repositorio. Para una práctica local puedes configurar las credenciales en tu entorno; en un proyecto compartido, utiliza variables de entorno o un archivo de configuración excluido del control de versiones.

### 5. Revisar la dependencia de MySQL Connector/J

Comprueba que `pom.xml` incluya MySQL Connector/J 8.4.0. Una dependencia Maven habitual para esa versión es:

```xml
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <version>8.4.0</version>
</dependency>
```

Si la dependencia ya está declarada, no es necesario añadirla de nuevo.

### 6. Compilar y ejecutar

Desde la carpeta raíz del proyecto, ejecuta:

```bash
mvn clean compile
```

Para ejecutar el proyecto, abre la carpeta desde NetBeans, espera a que Maven cargue las dependencias y ejecuta la clase que contiene el método `main`. Según la configuración del proyecto, también puede estar disponible:

```bash
mvn package
```

El comando `mvn package` genera el artefacto configurado en el `pom.xml`, pero **no garantiza por sí solo** que se cree un JAR ejecutable con todas las dependencias. La forma de lanzamiento depende de la configuración Maven del proyecto.

### 7. Verificación inicial

Antes de probar todos los módulos:

- Confirma que MySQL esté activo.
- Verifica que la base de datos y las tablas existan.
- Comprueba los datos de conexión.
- Inicia la aplicación y consulta una lista, por ejemplo, el catálogo de marcas.
- Prueba primero con datos académicos o ficticios.

## 💻 Ejemplos de ejecución

Los siguientes ejemplos son **flujos ilustrativos**. Los textos exactos del menú, las opciones numéricas y los mensajes pueden variar según la implementación real.

### Menú principal

```text
====================================
          TECNOSTORE
     Sistema de tienda de celulares
====================================
1. Gestionar marcas
2. Gestionar celulares
3. Gestionar empleados
4. Gestionar clientes
5. Gestionar ventas
6. Consultar reportes
0. Salir
====================================
Seleccione una opción:
```

### Flujo: registrar un celular

1. Seleccionar **Gestionar celulares**.
2. Elegir la opción para registrar.
3. Introducir la marca, el modelo, el sistema operativo, la gama, el precio y el stock.
4. Confirmar los datos cuando la aplicación lo solicite.
5. Revisar el mensaje de resultado.

Ejemplo ilustrativo:

```text
--- Registro de celular ---
Marca: Samsung
Modelo: Galaxy A55
Sistema operativo: Android
Versión: 14
Gama: Media
Precio: 1800000
Stock inicial: 12

Celular registrado correctamente.
```

El ejemplo supone que las referencias de marca, sistema operativo y gama existen en la base de datos. El precio y el stock deben respetar los rangos definidos por el proyecto.

### Flujo: registrar una venta

1. Seleccionar **Gestionar ventas**.
2. Elegir la opción de registrar una venta.
3. Seleccionar o ingresar el cliente y el empleado responsables.
4. Agregar uno o más celulares y sus cantidades.
5. Revisar subtotal, IVA y total.
6. Confirmar el registro.

Ejemplo ilustrativo:

```text
--- Registro de venta ---
Cliente: Ana Pérez
Empleado: Carlos Gómez

Producto: Galaxy A55
Cantidad: 1
Precio unitario: $1.800.000
Subtotal: $1.800.000
IVA (19 %): $342.000
TOTAL: $2.142.000

Venta registrada correctamente.
Stock actualizado.
```

Los importes son un ejemplo de cálculo para un producto y no representan una venta real. El descuento de inventario se describe en los triggers del proyecto; el resultado depende de que los scripts SQL estén instalados correctamente.

### Reportes disponibles

| Reporte | Descripción |
|---|---|
| **Stock bajo** | Lista los celulares con menos de 5 unidades disponibles. |
| **Top 3 de celulares más vendidos** | Presenta los tres celulares con mayor cantidad vendida, según los datos registrados. |
| **Ventas por mes** | Agrupa los valores de ventas por mes. |
| **Reporte en archivo** | Genera `reporte_ventas.txt` con la información definida por la implementación. |

Ejemplo ilustrativo de salida en consola:

```text
--- Reportes TecnoStore ---
1. Celulares con stock bajo
2. Top 3 de celulares más vendidos
3. Ventas totales por mes
4. Generar reporte de ventas en archivo
0. Volver

Seleccione una opción:
```

Los reportes que dependen de ventas requieren datos registrados previamente en la base de datos.

## 🔌 Conexión con MySQL

| Dato | Valor de referencia |
|---|---|
| Base de datos | `tecnostore_anasuarez` |
| Servidor local | `localhost` |
| Puerto habitual de MySQL | `3306` |
| Usuario de ejemplo | `root` |
| Contraseña | La configurada en tu instalación |
| Archivo de conexión | `src/main/java/patron/ConexionDB.java` |

### ¿Cómo cambiar la contraseña?

1. Abre `ConexionDB.java`.
2. Localiza la variable o propiedad que almacena la contraseña.
3. Reemplaza el valor de ejemplo por la contraseña configurada en MySQL.
4. Guarda el archivo y vuelve a ejecutar la aplicación.
5. Si aparece un error de autenticación, comprueba usuario, contraseña, puerto, nombre de la base de datos y permisos.

No se recomienda usar `root` en una instalación compartida o productiva. Para mejorar la seguridad, crea un usuario de base de datos exclusivo para la aplicación con los permisos mínimos necesarios.

## 🧩 Patrones de diseño y principios SOLID

### Patrones de diseño

| Patrón | Clase | Aplicación |
|---|---|---|
| **Singleton** | `ConexionDB` | Busca centralizar el acceso a la conexión para evitar múltiples instancias de la clase. Debe revisarse la implementación para confirmar cómo administra y reutiliza la conexión. |
| **Factory** | `FactoryCelular` | Centraliza la creación de objetos `Celular`, evitando dispersar la lógica de construcción por distintas partes del código. |
| **DAO** | Clases `*Dao` | Separa las operaciones de persistencia y las consultas SQL de la lógica de control y de las entidades del modelo. |

> **Nota técnica:** un Singleton que mantiene una única conexión JDBC compartida puede presentar problemas con operaciones concurrentes o conexiones cerradas. Para aplicaciones más robustas suele preferirse administrar conexiones por operación o usar un pool; para este proyecto se documenta el patrón declarado.

### Principios SOLID

| Principio | Aplicación en TecnoStore |
|---|---|
| **S — Responsabilidad única** | Las vistas, los controladores, los DAO y los modelos tienen responsabilidades diferenciadas. |
| **O — Abierto/cerrado** | `FactoryCelular` puede facilitar la extensión de la creación de celulares sin repartir cambios por todo el sistema. La aplicación efectiva del principio depende del diseño concreto de la fábrica. |
| **L — Sustitución de Liskov** | `Cliente` y `Empleado` heredan de `Persona` y deberían poder utilizarse donde el código espere una `Persona`, manteniendo el comportamiento esperado. |
| **I — Segregación de interfaces** | Se debe procurar que las clases dependan únicamente de los contratos que necesitan. No se afirma una implementación específica de este principio si no existen interfaces segregadas en el código. |
| **D — Inversión de dependencias** | La arquitectura busca separar las capas y reducir el acoplamiento. Para afirmar que los controladores dependen de abstracciones, el código debe utilizar interfaces o contratos, no únicamente clases DAO concretas. |

La tabla describe la intención arquitectónica del proyecto. La verificación completa de SOLID requiere revisar las clases y sus dependencias reales.

## 🗄️ Funciones, procedimientos y triggers SQL

Los siguientes objetos corresponden a los nombres declarados para los scripts del proyecto. Su disponibilidad depende de haber ejecutado los archivos SQL correctamente.

### Funciones

| Función | Propósito |
|---|---|
| `fn_correo_valido(correo)` | Comprueba si un correo cumple el formato esperado. |
| `fn_telefono_valido(telefono)` | Valida que el teléfono contenga entre 7 y 15 dígitos, según la regla descrita. |
| `fn_hay_stock(id_celular, cantidad)` | Verifica si existe stock suficiente para una cantidad solicitada. |
| `fn_descuento_gama(gama)` | Devuelve el porcentaje de descuento asociado a una gama. |
| `fn_total_venta(subtotal)` | Calcula el total a partir del subtotal, incluyendo el IVA conforme a la lógica SQL definida. |

### Procedimientos almacenados

| Procedimiento | Propósito |
|---|---|
| `sp_registrar_venta(...)` | Centraliza el registro de una venta según los parámetros definidos en SQL. |
| `sp_anular_venta(id)` | Anula una venta y devuelve las unidades al inventario, de acuerdo con su implementación. |
| `sp_reponer_stock(id, cantidad)` | Incrementa el stock de un celular. |
| `sp_reporte_ventas()` | Devuelve un listado de ventas para consulta o reporte. |

### Triggers

| Trigger | Evento y propósito |
|---|---|
| `trg_celular_precio_stock_bi` | Antes de insertar un celular, valida precio y stock. |
| `trg_celular_precio_stock_bu` | Antes de actualizar un celular, valida precio y stock. |
| `trg_persona_correo_bi` | Antes de insertar una persona, valida el correo. |
| `trg_detalle_stock_bi` | Antes de insertar un detalle de venta, comprueba el stock disponible. |
| `trg_detalle_descontar_ai` | Después de insertar un detalle de venta, descuenta del inventario las unidades vendidas. |
| `trg_celular_log_au` | Después de actualizar un celular, registra los cambios en `log_celulares`, según los campos contemplados en el trigger. |

**Recomendación:** evita duplicar una misma modificación de inventario en Java y en los triggers. Define claramente qué capa es responsable del cambio para prevenir descuentos o reposiciones dobles.

## 📄 Reportes generados

### Archivo `reporte_ventas.txt`

La aplicación puede generar el archivo `reporte_ventas.txt` con información de las ventas registradas. Según la implementación, puede incluir datos como:

- Identificador de la venta.
- Fecha.
- Cliente y empleado asociados.
- Subtotal.
- IVA.
- Total.

La estructura exacta del archivo depende de la clase que construye el reporte, como `ReporteUtils` o `ArchivoUtils`. Los campos anteriores son una referencia documental, no una garantía de que todos estén incluidos en la versión actual.

El archivo se genera en la ruta que establezca el código. Si utiliza una ruta relativa, normalmente se resolverá desde el directorio de trabajo de la aplicación; por ello, verifica la ubicación después de ejecutar la opción de generación del reporte.

## 👩‍💻 Autor

| Campo | Información |
|---|---|
| **Estudiante** | Ana Suárez |
| **Institución** | [Nombre de la institución educativa] |
| **Proyecto** | TecnoStore — tienda virtual de celulares |
| **Año** | 2026 |

> Antes de entregar el proyecto, reemplaza `[Nombre de la institución educativa]` por el nombre oficial de tu institución.

## 📜 Licencia

Este proyecto se propone bajo la **Licencia MIT**, una licencia permisiva que permite utilizar, copiar, modificar, distribuir y sublicenciar el software, siempre que se conserven el aviso de copyright y el texto de la licencia.

Para que esta declaración sea efectiva, agrega un archivo `LICENSE` en la raíz del repositorio con el texto oficial de la licencia MIT y los datos correctos del titular. No declares que el repositorio ya cuenta con esa licencia hasta que el archivo esté incluido.

---

**TecnoStore** es un proyecto académico orientado a practicar Java, programación orientada a objetos, JDBC, MySQL, separación por capas y generación de reportes.
