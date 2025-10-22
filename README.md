# 🏥 Sistema de Gestión de Citas Médicas

Sistema web para la gestión integral de citas médicas, desarrollado con Spring Boot y Thymeleaf.

## 👥 Grupo 12 - Integrantes

- **Ciro Jelsin Vasquez Malpartida** - Líder de Proyecto
- **[Nombre]** - Desarrollador Backend
- **[Nombre]** - Desarrollador Frontend
- **[Nombre]** - Analista de Base de Datos
- **[Nombre]** - Documentación y Testing

## 📚 Información Académica

- **Institución:** CIBERTEC
- **Carrera:** Computación e Informática
- **Curso:** Lenguaje de Programación II (4691)
- **Ciclo:** Cuarto
- **Período:** 2025

## 🎯 Descripción del Proyecto

Sistema web desarrollado en Java con Spring Boot que permite gestionar citas médicas, administrar información de médicos y pacientes, y generar reportes. La aplicación implementa arquitectura MVC con persistencia de datos en MySQL.

## ✨ Características Principales

- ✅ Gestión de Médicos (CRUD completo)
- ✅ Gestión de Pacientes (CRUD completo)
- ✅ Gestión de Citas Médicas (CRUD completo)
- ✅ Gestión de Especialidades
- ✅ Gestión de Usuarios con roles
- ✅ Dashboard con estadísticas
- ✅ Interfaz responsive con Bootstrap 5
- ✅ Reportes en PDF con Jasper Reports
- ✅ API REST documentada

## 🛠️ Tecnologías Utilizadas

### Backend
- **Java 17**
- **Spring Boot 3.5.6**
- **Spring Data JPA**
- **Spring Security**
- **Hibernate**
- **Maven**

### Frontend
- **Thymeleaf**
- **Bootstrap 5.3**
- **Font Awesome 6.4**
- **HTML5 / CSS3**
- **JavaScript**

### Base de Datos
- **MySQL 8.4.3**
- **HikariCP** (Connection Pool)

### Reportes
- **Jasper Reports**

### Deployment
- **Microsoft Azure App Service**
- **GitHub Actions** (CI/CD)

## 🚀 Instalación y Configuración

### Prerrequisitos

- Java JDK 17 o superior
- MySQL 8.0 o superior
- Maven 3.6 o superior
- IDE (Eclipse, IntelliJ IDEA)

### Pasos de Instalación

1. **Clonar el repositorio**

git clone https://github.com/[tu-usuario]/gestion-citas-medicas.git
cd gestion-citas-medicas


3. **Configurar application.properties**

Editar `src/main/resources/application.properties`:

spring.datasource.url=jdbc:mysql://localhost:3306/gestion_citas
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña


4. **Compilar el proyecto**

mvn clean install


5. **Ejecutar la aplicación**

mvn spring-boot:run


6. **Acceder a la aplicación**

http://localhost:8080


## 👤 Usuarios por Defecto

| Usuario 	| admin
| Contraseña 	| admin123
| Rol		| Administrador

| Usuario 	| recepcion
| Contraseña 	| recep123
| Rol		| Recepcionista

| Usuario 	| doctor
| Contraseña 	| doc123
| Rol		| Médico


## 📊 Modelo de Base de Datos

### Entidades Principales

- **Especialidad**: Especialidades médicas disponibles
- **Medico**: Información de médicos del sistema
- **Paciente**: Datos de pacientes registrados
- **Cita**: Gestión de citas médicas
- **Usuario**: Control de acceso al sistema

## 🔗 Endpoints API REST

### Especialidades
- `GET /api/especialidades` - Listar todas
- `POST /api/especialidades` - Crear nueva
- `GET /api/especialidades/{id}` - Obtener por ID
- `PUT /api/especialidades/{id}` - Actualizar
- `DELETE /api/especialidades/{id}` - Eliminar

### Médicos
- `GET /api/medicos` - Listar todos
- `POST /api/medicos` - Crear nuevo
- `GET /api/medicos/{id}` - Obtener por ID
- `PUT /api/medicos/{id}` - Actualizar
- `DELETE /api/medicos/{id}` - Eliminar

### Pacientes
- `GET /api/pacientes` - Listar todos
- `POST /api/pacientes` - Crear nuevo
- `GET /api/pacientes/{id}` - Obtener por ID
- `PUT /api/pacientes/{id}` - Actualizar
- `DELETE /api/pacientes/{id}` - Eliminar

### Citas
- `GET /api/citas` - Listar todas
- `POST /api/citas` - Crear nueva
- `GET /api/citas/{id}` - Obtener por ID
- `PUT /api/citas/{id}` - Actualizar
- `DELETE /api/citas/{id}` - Eliminar

## 📝 Reportes Disponibles

1. **Reporte de Citas por Médico**: Listado detallado de citas agrupadas por médico
2. **Reporte de Citas por Especialidad**: Estadísticas de citas por especialidad médica

## 🌐 Deployment en Azure

La aplicación está desplegada en Microsoft Azure App Service:

URL: https://gestion-citas-medicas.azurewebsites.net


## 🧪 Testing

Ejecutar tests:

mvn test


## 📄 Documentación Adicional

- [Manual de Usuario](docs/Manual_Usuario.pdf)
- [Manual Técnico](docs/Manual_Tecnico.pdf)
- [Informe Final del Proyecto](docs/Informe_Final.pdf)

## 🤝 Contribución

Este es un proyecto académico desarrollado por el Grupo 12 de Cibertec.

### Flujo de Trabajo Git

1. Crear branch desde `main`:

git checkout -b feature/nombre-funcionalidad


2. Realizar cambios y commit:

git add .
git commit -m "Descripción del cambio"


3. Subir cambios:

git push origin feature/nombre-funcionalidad


4. Crear Pull Request en GitHub

## 📜 Licencia

Este proyecto es de uso académico exclusivo para el curso de Lenguaje de Programación II de Cibertec.

## 📧 Contacto

Para consultas sobre el proyecto, contactar a:
- Email: Zirelement44@gmail.com

---

**Desarrollado con ❤️ por el Grupo 12 - Cibertec 2025**
