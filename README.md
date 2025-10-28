# Guau y Miau 🐾

**Link de Trello:** https://trello.com/b/o3ZEVOBO/guauymiau

## Descripción

**Guau y Miau** es una aplicación de Android que demuestra una implementación moderna de un flujo de autenticación y gestión de datos de ejemplo. La app, construida 100% en Kotlin, utiliza las últimas librerías de Jetpack, con una interfaz de usuario creada enteramente con Compose y una navegación gestionada por Navigation-Compose.

El proyecto es una base excelente para entender los principios de la gestión de estado en Compose, utilizando una arquitectura limpia y desacoplada.

## Tecnologías Utilizadas

*   **Lenguaje:** Kotlin
*   **UI Toolkit:** Jetpack Compose
*   **Navegación:** Jetpack Navigation Compose
*   **Diseño:** Material 3
*   **Herramienta de Construcción:** Gradle

## Funcionalidades Principales

#### Flujo de Autenticación
*   **Registro de Usuarios:** Permite a los nuevos usuarios crear una cuenta y registrar su primera mascota.
*   **Inicio de Sesión Seguro:** Ofrece un formulario para que los usuarios existentes accedan a la app.
*   **Navegación Protegida:** Una vez que el usuario inicia sesión, es redirigido a la pantalla principal, eliminando el historial de navegación de las pantallas de autenticación para evitar retornos no deseados.

#### Gestión de Mascotas
*   **Añadir y Eliminar:** Los usuarios pueden añadir nuevas mascotas a su perfil y eliminar las existentes.
*   **Edición en Tiempo Real:** Es posible cambiar el nombre y el tipo de cada mascota directamente desde la lista, viendo los cambios reflejados instantáneamente.

## Arquitectura

El proyecto se apoya en un patrón **Repositorio (Singleton)** para la gestión de datos. El objeto `UserRepository` actúa como la única fuente de verdad (Single Source of Truth), centralizando toda la lógica de negocio y los datos del usuario y sus mascotas en memoria.

Este enfoque, aunque simple, es altamente efectivo para ilustrar la gestión de estado en una aplicación de Compose, garantizando que la interfaz de usuario reaccione de manera predecible a los cambios en los datos.

## Puesta en Marcha

#### Requisitos
*   Una versión reciente de Android Studio.
*   Un emulador o dispositivo físico con Android.

#### Pasos
1.  **Clona el repositorio** en tu máquina local.
2.  **Abre el proyecto** con Android Studio.
3.  Espera a que la **sincronización de Gradle** finalice.
4.  Presiona **"Run" (▶️)** para compilar y ejecutar la aplicación.

#### Credenciales de Prueba
Para facilitar el acceso y las pruebas, puedes usar el siguiente usuario por defecto:

*   **Correo:** `usuario@duoc.cl`
*   **Contraseña:** `Password123@`

## Estructura del Proyecto

El código fuente está organizado de la siguiente manera para facilitar su comprensión:

```
app/
└── src/
    └── main/
        └── java/
            └── com/example/myapplication/
                ├── MainActivity.kt         # Actividad principal, aloja el NavHost y define el grafo de navegación.
                ├── data/
                │   └── UserRepository.kt   # Repositorio Singleton y modelos de datos (User, Pet).
                └── ui/
                    ├── login/
                    │   ├── LoginScreen.kt      # Composable para el inicio de sesión.
                    │   ├── RegisterScreen.kt   # Composable para el registro de usuarios.
                    │   └── WelcomeScreen.kt    # Pantalla de bienvenida y gestión de mascotas.
                    └── theme/              # Tema de la app (colores, tipografía, etc.).
```
