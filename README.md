# 🏥 Sistema de Gestión de Citas Médicas

Sistema web integral para la gestión de citas médicas desarrollado con Spring Boot, que incluye autenticación por roles, dashboard interactivo, reportes en PDF y una API REST completa.

## 👥 Información del Desarrollador

- **Desarrollador:** Ciro Jelsin Vasquez Malpartida
- **Proyecto:** Sistema de Gestión de Citas Médicas
- **Versión:** 1.0.0

## 📚 Información Académica

- **Institución:** CIBERTEC
- **Carrera:** Computación e Informática
- **Curso:** Lenguaje de Programación II (4691)
- **Ciclo:** Quinto Ciclo
- **Período:** 2024-2025
**Última actualización:** Noviembre 2025

## 🎯 Descripción del Proyecto

Sistema web completo desarrollado en Java con Spring Boot que permite la gestión integral de citas médicas. Incluye administración de médicos, pacientes, especialidades, usuarios con diferentes roles (ADMIN, MEDICO, RECEPCIONISTA), dashboard con estadísticas en tiempo real y generación de reportes en PDF.

## ✨ Características Principales

### 🔐 Sistema de Autenticación y Autorización
- ✅ Login seguro con Spring Security
- ✅ Gestión de roles (ADMIN, MEDICO, PACIENTE)
- ✅ Autenticación basada en sesiones
- ✅ Control de acceso por roles y endpoints

### 👨‍⚕️ Gestión de Médicos
- ✅ CRUD completo de médicos
- ✅ Asignación de especialidades
- ✅ Portal específico para médicos
- ✅ Vista de citas por especialidad

### 👥 Gestión de Pacientes
- ✅ CRUD completo de pacientes
- ✅ Registro con datos personales completos
- ✅ Historial de citas por paciente
- ✅ Búsqueda y filtros avanzados

### 📅 Gestión de Citas Médicas
- ✅ CRUD completo de citas
- ✅ Estados: PROGRAMADA, COMPLETADA, CANCELADA, NO_ASISTIO
- ✅ Validación de disponibilidad horaria
- ✅ Gestión de duración de citas
- ✅ Control de conflictos de horarios

### 🏥 Gestión de Especialidades
- ✅ CRUD de especialidades médicas
- ✅ Asignación múltiple a médicos
- ✅ Reportes por especialidad

### 📊 Dashboard y Estadísticas
- ✅ Dashboard interactivo con métricas
- ✅ Estadísticas del día actual
- ✅ Gráficos de citas por estado
- ✅ Contadores en tiempo real

### 📄 Sistema de Reportes
- ✅ Reportes en PDF con JasperReports
- ✅ Reportes de citas por fecha
- ✅ Reportes por médico/especialidad
- ✅ Estadísticas detalladas

### 🌐 API REST
- ✅ Endpoints completos para todas las entidades
- ✅ Documentación de API
- ✅ Autenticación JWT
- ✅ Manejo de errores estandarizado

## 🛠️ Tecnologías Utilizadas

### Backend
- **Java 17** - Lenguaje de programación principal
- **Spring Boot 3.1.5** - Framework principal
- **Spring Data JPA** - Persistencia de datos
- **Spring Security** - Autenticación y autorización
- **Spring Web** - API REST y controladores web
- **Hibernate** - ORM
- **Maven** - Gestión de dependencias

### Base de Datos
- **MySQL 8.0** - Base de datos principal
- **HikariCP** - Pool de conexiones
- **JPA/Hibernate** - Mapeo objeto-relacional

### Seguridad
- **Spring Security 6** - Framework de seguridad
- **Session Based Auth** - Autenticación basada en sesiones
- **Role Based Access Control** - Control de acceso basado en roles
- **NoOpPasswordEncoder** - Codificación de contraseñas (Modo desarrollo)

### Frontend
- **Thymeleaf** - Motor de plantillas
- **Bootstrap 5.3** - Framework CSS
- **Font Awesome 6.4** - Iconografía
- **HTML5/CSS3** - Markup y estilos
- **JavaScript** - Interactividad del cliente

### Reportes
- **JasperReports 6.20.5** - Generación de reportes PDF
- **JasperStudio** - Diseño de plantillas

### Utilidades
- **Lombok** - Reducción de código boilerplate
- **Spring Boot DevTools** - Desarrollo en caliente
- **Validation API** - Validación de datos

### Testing
- **Spring Boot Test** - Testing integrado
- **JUnit 5** - Framework de pruebas
- **Mockito** - Mocking para pruebas

## 🏗️ Arquitectura del Sistema

### Patrón MVC (Modelo-Vista-Controlador)
- **Modelo**: Entidades JPA (Cita, Medico, Paciente, Usuario, Especialidad)
- **Vista**: Plantillas Thymeleaf con Bootstrap
- **Controlador**: Controllers REST y Web separados

### Capas de la Aplicación
```
┌─────────────────┐
│   Presentation  │ ← Controllers (Web + REST)
├─────────────────┤
│     Service     │ ← Lógica de negocio
├─────────────────┤
│   Repository    │ ← Acceso a datos (JPA)
├─────────────────┤
│     Entity      │ ← Modelos de dominio
└─────────────────┘
```

### Seguridad por Capas
- **Autenticación**: JWT + Session-based
- **Autorización**: Roles y permisos granulares
- **Validación**: Bean Validation en todas las capas

## 🚀 Instalación y Configuración

### Prerrequisitos
- **Java 17** o superior
- **MySQL 8.0** o superior
- **Maven 3.6** o superior
- **IDE** (IntelliJ IDEA, Eclipse, VSCode)

### Paso 1: Clonar el Repositorio
```bash
git clone https://github.com/tuusuario/gestion-citas.git
cd gestion-citas
```

### Paso 2: Configurar Base de Datos
1. Crear base de datos en MySQL:
```sql
CREATE DATABASE gestion_citas CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

2. Configurar credenciales en `application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/gestion_citas
spring.datasource.username=tu_usuario
spring.datasource.password=tu_password
```

### Paso 3: Ejecutar la Aplicación
```bash
# Usando Maven
mvn spring-boot:run

# O usando el wrapper
./mvnw spring-boot:run
```

### Paso 4: Acceder a la Aplicación
- **URL Principal**: http://localhost:8080
- **Usuario Admin**: admin / admin123
- **Usuario Médico**: medico / medico123
- **Usuario Paciente**: paciente / paciente123

## 📁 Estructura del Proyecto

```
src/
├── main/
│   ├── java/com/cibertec/gestioncitas/
│   │   ├── config/          # Configuraciones (Security, JWT, etc.)
├── web/
│   └── controller/      # Controladores Web
│       ├── AdminController.java
│       ├── AuthController.java
│       ├── CustomErrorController.java
│       ├── DevToolsController.java
│       ├── HomeController.java
│       ├── LoginController.java
│       ├── MedicoController.java
│       ├── PacienteController.java
│       └── ReporteController.java
│   │   ├── entity/          # Entidades JPA
│   │   │   ├── Cita.java
│   │   │   ├── Especialidad.java
│   │   │   ├── Medico.java
│   │   │   ├── Paciente.java
│   │   │   └── Usuario.java
│   │   ├── exception/       # Manejo de excepciones
│   │   ├── repository/      # Repositorios JPA
│   │   ├── service/         # Servicios de negocio
│   │   └── GestionCitasApplication.java
│   └── resources/
│       ├── static/          # Recursos estáticos (CSS, JS, imágenes)
│       │   ├── css/
│       │   ├── js/
│       │   └── img/
│       ├── templates/       # Plantillas Thymeleaf
│       │   ├── citas/
│       │   ├── dashboard/
│       │   ├── medicos/
│       │   ├── pacientes/
│       │   ├── especialidades/
│       │   ├── usuarios/
│       │   └── fragments/
│       ├── reportes/        # Plantillas JasperReports
│       └── application.properties
└── test/                    # Pruebas unitarias e integración
```

## 🔗 Endpoints de la API REST

### Autenticación
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | `/api/auth/login` | Iniciar sesión |
| POST | `/api/auth/logout` | Cerrar sesión |

### Médicos
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/medicos` | Listar todos los médicos |
| GET | `/api/medicos/{id}` | Obtener médico por ID |
| POST | `/api/medicos` | Crear nuevo médico |
| PUT | `/api/medicos/{id}` | Actualizar médico |
| DELETE | `/api/medicos/{id}` | Eliminar médico |

### Pacientes
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/pacientes` | Listar todos los pacientes |
| GET | `/api/pacientes/{id}` | Obtener paciente por ID |
| POST | `/api/pacientes` | Crear nuevo paciente |
| PUT | `/api/pacientes/{id}` | Actualizar paciente |
| DELETE | `/api/pacientes/{id}` | Eliminar paciente |

### Citas
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/citas` | Listar todas las citas |
| GET | `/api/citas/{id}` | Obtener cita por ID |
| POST | `/api/citas` | Crear nueva cita |
| PUT | `/api/citas/{id}` | Actualizar cita |
| DELETE | `/api/citas/{id}` | Eliminar cita |
| GET | `/api/citas/medico/{id}` | Citas por médico |
| GET | `/api/citas/paciente/{id}` | Citas por paciente |

### Especialidades
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/especialidades` | Listar especialidades |
| GET | `/api/especialidades/{id}` | Obtener especialidad por ID |
| POST | `/api/especialidades` | Crear especialidad |
| PUT | `/api/especialidades/{id}` | Actualizar especialidad |
| DELETE | `/api/especialidades/{id}` | Eliminar especialidad |

## 📚 Documentación de la API REST

### 🔑 Autenticación

#### Login
```http
POST /api/auth/login
```
| Parámetro   | Tipo       | Descripción                           |
| :---------- | :--------- | :---------------------------------- |
| `username`  | `string`   | **Requerido**. Nombre de usuario    |
| `password`  | `string`   | **Requerido**. Contraseña          |

Respuesta exitosa:
```json
{
    "success": true,
    "username": "usuario",
    "roles": ["ROLE_ADMIN", "ROLE_MEDICO", "ROLE_PACIENTE"],
    "redirect": "/dashboard"
}
```

### 👨‍⚕️ Endpoints de Médicos

#### Obtener todos los médicos
```http
GET /api/medicos
```
| Header          | Tipo     | Descripción                         |
| :-------------- | :------- | :--------------------------------- |
| `Authorization` | `string` | **Requerido**. Bearer {token}      |

#### Obtener médico por ID
```http
GET /api/medicos/{id}
```

#### Crear nuevo médico
```http
POST /api/medicos
```
```json
{
    "nombre": "string",
    "apellido": "string",
    "dni": "string",
    "especialidadId": "number",
    "email": "string",
    "telefono": "string"
}
```

#### Actualizar médico
```http
PUT /api/medicos/{id}
```

#### Eliminar médico
```http
DELETE /api/medicos/{id}
```

### 📅 Endpoints de Citas

#### Listar citas
```http
GET /api/citas
```
| Parámetro    | Tipo      | Descripción                          |
| :----------- | :-------- | :---------------------------------- |
| `fecha`      | `string`  | Filtrar por fecha (YYYY-MM-DD)      |
| `estado`     | `string`  | Filtrar por estado                  |
| `medicoId`   | `number`  | Filtrar por médico                  |

#### Crear cita
```http
POST /api/citas
```
```json
{
    "pacienteId": "number",
    "medicoId": "number",
    "fecha": "string (YYYY-MM-DD)",
    "hora": "string (HH:mm)",
    "motivo": "string"
}
```

#### Actualizar estado de cita
```http
PATCH /api/citas/{id}/estado
```
```json
{
    "estado": "COMPLETADA|CANCELADA|NO_ASISTIO"
}
```

### 👥 Endpoints de Pacientes

#### Listar pacientes
```http
GET /api/pacientes
```

#### Buscar paciente por DNI
```http
GET /api/pacientes/buscar
```
| Parámetro | Tipo     | Descripción                    |
| :-------- | :------- | :---------------------------- |
| `dni`     | `string` | DNI del paciente a buscar     |

### 📊 Endpoints de Reportes

#### Generar reporte de citas
```http
GET /api/reportes/citas
```
| Parámetro     | Tipo     | Descripción                          |
| :------------ | :------- | :---------------------------------- |
| `fechaInicio` | `string` | Fecha inicial (YYYY-MM-DD)          |
| `fechaFin`    | `string` | Fecha final (YYYY-MM-DD)            |
| `formato`     | `string` | Formato del reporte (PDF|EXCEL)     |

### ⚡ Códigos de Estado

La API utiliza los siguientes códigos de estado HTTP:

| Código | Descripción                                    |
| :----- | :--------------------------------------------|
| 200    | OK - La solicitud se ha completado con éxito |
| 201    | Created - Recurso creado correctamente       |
| 400    | Bad Request - Solicitud inválida             |
| 401    | Unauthorized - No autenticado                |
| 403    | Forbidden - No autorizado                    |
| 404    | Not Found - Recurso no encontrado            |
| 500    | Internal Server Error - Error del servidor   |

### 🔒 Manejo de Errores

La API devuelve errores en el siguiente formato:

```json
{
    "timestamp": "2025-11-01T10:00:00",
    "status": 400,
    "error": "Bad Request",
    "message": "Descripción detallada del error",
    "path": "/api/recurso"
}
```

### 📝 Notas Importantes

- Todos los endpoints requieren autenticación mediante token JWT
- El token debe enviarse en el header `Authorization: Bearer {token}`
- Las fechas deben enviarse en formato ISO (YYYY-MM-DD)
- Las horas deben enviarse en formato 24h (HH:mm)
- Los IDs son de tipo numérico (Long)
- La paginación está disponible en endpoints de listado usando `page` y `size`

## 🔑 Permisos por Rol

### 👑 ROLE_ADMIN
- Gestión completa de usuarios del sistema
- Gestión de médicos y sus especialidades
- Acceso al dashboard administrativo
- Generación de todos los reportes
- Configuración del sistema

### 👨‍⚕️ ROLE_MEDICO
- Ver y gestionar sus citas asignadas
- Actualizar estado de citas (COMPLETADA, NO_ASISTIO)
- Ver historial de pacientes atendidos
- Gestionar su disponibilidad horaria
- Ver sus estadísticas personales

### 👤 ROLE_PACIENTE
- Solicitar nuevas citas médicas
- Ver y cancelar sus citas programadas
- Ver su historial médico
- Actualizar sus datos personales
- Ver disponibilidad de médicos