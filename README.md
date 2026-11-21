# Sistema de Gestión de Torneos de Videojuegos 🎮

Proyecto académico de **Programación Orientada a Objetos en Java** que permite simular la organización y el desarrollo de torneos de videojuegos, desde la inscripción de participantes hasta el registro de partidas y la consulta de resultados.

> **Estado:** prototipo funcional por consola, desarrollado a partir de un diagrama de clases UML. Avances correspondientes a los Sprints 1 y 2.

## Información del proyecto

- **Universidad:** Universidad Nacional de San Agustín de Arequipa (UNSA)
- **Curso:** Desarrollo de Software Orientado a Objetos
- **Estudiante:** Tony Sergio Palomino Frisancho
- **Rol:** líder del proyecto y único integrante
- **Lenguaje:** Java 17
- **Tipo de aplicación:** consola (sin interfaz gráfica)

## Objetivo

Aplicar los principios de la programación orientada a objetos para representar las principales entidades de un torneo de videojuegos y sus relaciones. El proyecto utiliza clases, encapsulamiento, herencia, abstracción, enumeraciones y asociaciones entre objetos.

## Funcionalidades implementadas

- Crear torneos asociados a un videojuego.
- Registrar y confirmar inscripciones de jugadores.
- Validar inscripciones repetidas.
- Generar rondas y enfrentamientos de **eliminación directa**.
- Iniciar y finalizar partidas, y registrar sus marcadores.
- Impedir empates en partidas de eliminación directa.
- Avanzar ganadores a la siguiente ronda y obtener al campeón.
- Actualizar y consultar una clasificación con estadísticas de los encuentros.
- Ejecutar demostraciones y pruebas por consola sin dependencias de terceros.

## Tecnologías

| Tecnología | Uso |
| --- | --- |
| Java 17 | Implementación del sistema |
| Programación orientada a objetos | Diseño de clases y relaciones |
| UML | Modelo de referencia |
| Maven | Organización y configuración del proyecto (`pom.xml`) |

## Estructura del proyecto

```text
SistemaTorneos/
├── pom.xml
├── README.md
└── src/
    └── main/
        └── java/
            └── com/
                └── torneos/
                    ├── Main.java
                    ├── PruebasSprint2.java
                    ├── Administrador.java
                    ├── Videojuego.java
                    ├── Torneo.java
                    ├── Participante.java
                    ├── Jugador.java
                    ├── Equipo.java
                    ├── Inscripcion.java
                    ├── Ronda.java
                    ├── Partido.java
                    ├── Resultado.java
                    ├── Clasificacion.java
                    ├── EntradaClasificacion.java
                    └── ... (enumeraciones y validaciones)
```

## Requisitos

- **JDK 17** o superior, instalado y disponible en la terminal.
- Un editor o IDE como **NetBeans, IntelliJ IDEA o Visual Studio Code** (opcional).
- Maven es **opcional**: también se puede compilar directamente con `javac`.

Para comprobar que Java está instalado:

```bash
java -version
javac -version
```

## Cómo ejecutar el proyecto

1. Descarga o clona el repositorio y abre una terminal en la carpeta donde está `pom.xml`.
2. Compila las clases:

   ```bash
   mkdir out
   javac -encoding UTF-8 -d out src/main/java/com/torneos/*.java
   ```

3. Ejecuta la demostración principal:

   ```bash
   java -cp out com.torneos.Main
   ```

4. Ejecuta las pruebas del segundo Sprint:

   ```bash
   java -cp out com.torneos.PruebasSprint2
   ```

> Si trabajas desde un IDE, abre la carpeta como proyecto Maven y ejecuta `Main.java` o `PruebasSprint2.java` por separado. Si `mkdir out` indica que la carpeta ya existe, puedes continuar con la compilación.

### ¿Qué hace la demostración?

`Main` crea la **Copa Universitaria** para el videojuego Valorant, registra dos jugadores, organiza una partida, guarda su marcador e imprime al campeón y la clasificación.

`PruebasSprint2` crea un torneo con **cuatro jugadores**, comprueba semifinales y final, y verifica casos como una inscripción duplicada y un empate no permitido. Al completarse correctamente, muestra:

```text
TOTAL: 7 verificaciones correctas
```

## Diseño orientado a objetos

La clase abstracta `Participante` sirve como base para `Jugador` y `Equipo`. `Torneo` relaciona el videojuego, las inscripciones, las rondas y la clasificación. Cada `Ronda` agrupa uno o más `Partido`, y cada partida puede registrar un `Resultado`.

Las enumeraciones describen los estados y opciones del sistema, por ejemplo `Modalidad`, `FormatoTorneo`, `EstadoTorneo`, `EstadoInscripcion` y `FaseRonda`. También se incorporó `EstadoPartido` como apoyo a la implementación.

## Avance por Sprints

| Sprint | Trabajo principal |
| --- | --- |
| **Sprint 1** | Estructura de clases basada en UML, relaciones, inscripciones, partidas y demostración inicial |
| **Sprint 2** | Clase `PruebasSprint2`, escenario de cuatro participantes y siete verificaciones de comportamiento |

## Limitaciones actuales

- **Solo está implementada la generación de rondas para eliminación directa.** La opción de doble eliminación aparece en el modelo, pero no cuenta con un calendario funcional.
- No hay interfaz gráfica: la interacción se realiza mediante ejemplos escritos en Java y salida por consola.
- No se utiliza base de datos: la información se conserva en memoria mientras el programa está en ejecución.
- No existe un sistema de inicio de sesión seguro; las credenciales de demostración no deben usarse en un entorno real.
- La clasificación por estadísticas no sustituye al campeón obtenido mediante las rondas del torneo.

## Posibles mejoras

- Añadir una interfaz gráfica para administrar torneos.
- Guardar participantes y resultados en una base de datos.
- Implementar completamente el formato de doble eliminación.
- Incorporar autenticación y gestión segura de administradores.
- Agregar más pruebas automatizadas.

## Autor

**Tony Sergio Palomino Frisancho**  
Estudiante de primer año, segundo semestre  
Universidad Nacional de San Agustín de Arequipa (UNSA)

---

*Proyecto académico con fines de aprendizaje.*
