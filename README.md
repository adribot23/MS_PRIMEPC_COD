# PRIME PC - Gestor de Tienda de Componentes

Proyecto universitario de la UCM para la asignatura **MS (Modelado de Software)**.

La aplicacion implementa un gestor de tienda de componentes de PC con interfaz grafica en Java (Swing), arquitectura por capas y persistencia en base de datos H2/JPA.

## Descripcion

PRIME PC permite gestionar distintas entidades de negocio de una tienda de componentes, incluyendo:

- Cliente
- Empleado
- Producto
- Proveedor
- Almacen
- Venta
- Modulos JPA adicionales: Paquete, Remitente, Factura, Ruta, Trabajador y Transporte

Incluye operaciones de alta, baja, modificacion, busqueda, listados y varias consultas de negocio.

## Arquitectura del proyecto

El proyecto sigue una organizacion por capas:

- **presentacion**: interfaz grafica (Swing), vistas, eventos y controlador.
- **negocio**: servicios de aplicacion (SA), logica de negocio y objetos de transferencia.
- **integracion**: DAOs, transacciones y acceso a datos.
- **META-INF**: configuracion de persistencia JPA (`persistence.xml`).
- **tests**: pruebas JUnit 4 de integracion y negocio.

Tambien utiliza patrones como **Factory**, **DAO**, **Command** y **Singleton**.

## Tecnologias

- Java 17
- Eclipse (proyecto Java clasico, sin Maven/Gradle)
- Swing (GUI)
- H2 Database (archivo local)
- JPA (EclipseLink/Jakarta)
- JUnit 4

Dependencias en `lib/` (entre otras):

- `h2-2.4.240.jar`
- `eclipselink.jar`
- `jakarta.persistence-api.jar`
- `jakarta.annotation-api.jar`

## Estructura principal

```text
MS_PRIMEPC_COD/
|- src/
|  |- integracion/
|  |- negocio/
|  |- presentacion/
|  |- primePcMain/
|  \- META-INF/
|- tests/
|- resources/
|- bd/
|- lib/
|- .classpath
\- .project
```

## Requisitos

- Eclipse IDE (Java)
- JDK 17 instalado

## Puesta en marcha en Eclipse

1. Clona el repositorio:

	```bash
	git clone https://github.com/adribot23/MS_PRIMEPC_COD.git
	```

2. En Eclipse:
	- `File > Import... > Existing Projects into Workspace`
	- Selecciona la carpeta `MS_PRIMEPC_COD`
	- Finaliza el import

3. Verifica configuracion del proyecto:
	- JRE/JDK: **JavaSE-17**
	- Classpath con `src`, `tests` y librerias de `lib/`

4. Ejecuta la aplicacion:
	- Clase principal: `primePcMain.Main`
	- `Run As > Java Application`

## Base de datos

La aplicacion usa H2 en modo archivo local con la URL:

`jdbc:h2:./bd/IS2PrimePC`

Archivos de base de datos en la carpeta `bd/`.

Si quieres resetear el estado de datos:

1. Cierra la aplicacion.
2. Elimina los ficheros `IS2PrimePC*.db`/`IS2PrimePC*.trace.db` dentro de `bd/`.
3. Vuelve a ejecutar.

## Ejecutar pruebas

El proyecto incluye pruebas JUnit 4 en `tests/` (negocio e integracion).

Opciones en Eclipse:

- Clic derecho sobre el proyecto > `Run As > JUnit Test`
- O sobre un paquete/clase concreta dentro de `tests/`

Repositorio desarrollado con fines docentes para la asignatura MS (Modelado de Software) de la UCM.