# Sistema de Matrícula Académica

Sistema web desarrollado como solución para la evaluación técnica de **Practicante de Desarrollo**.

## Descripción

Los centros académicos de ADEX requieren un sistema que permita a los alumnos matricularse en los cursos correspondientes a su ciclo académico.

La aplicación permite al alumno consultar los cursos disponibles para su ciclo, seleccionar el turno de su preferencia y gestionar los cursos seleccionados mediante una interfaz tipo carrito de compra.

Antes de registrar la matrícula, el sistema valida que no existan cruces de horarios y que las aulas no hayan alcanzado su capacidad máxima de 2 alumnos.

## Requerimientos implementados

- Selección de alumno.
- Consulta de cursos según el ciclo del alumno.
- Visualización de los turnos disponibles:
  - Mañana
  - Tarde
  - Noche
- Visualización de horarios.
- Selección de cursos mediante una interfaz tipo carrito.
- Eliminación de cursos seleccionados antes de confirmar.
- Validación de que el curso pertenezca al ciclo del alumno.
- Validación de cruces de horarios.
- Validación de capacidad máxima de las aulas.
- Registro de la matrícula.

## Tecnologías utilizadas

### Backend

- Java
- Spring Boot
- Spring Data JPA
- Hibernate
- PostgreSQL
- Maven

### Frontend

- React
- Vite
- JavaScript
- CSS
- Axios

## Arquitectura

El backend utiliza una arquitectura por capas:

Controller
    ↓
Service
    ↓
Repository
    ↓
JPA / Hibernate
    ↓
PostgreSQL
