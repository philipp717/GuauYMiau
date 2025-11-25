# Guau y Miau 🐾

[![Kotlin](https://img.shields.io/badge/Kotlin-1.9.0-blue.svg?logo=kotlin)](http://kotlinlang.org)
[![API](https://img.shields.io/badge/API-26%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=26)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

**Link de Trello:** https://trello.com/b/o3ZEVOBO/guauymiau

## 📖 Descripción

**Guau y Miau** es una aplicación Android nativa, construida 100% en Kotlin, que sirve como un proyecto de demostración de las mejores prácticas en el desarrollo de software moderno para Android. La app sigue una arquitectura limpia MVVM, con una UI declarativa creada con Jetpack Compose y Material 3.

El objetivo principal de este proyecto es mostrar un enfoque escalable y mantenible para construir aplicaciones, integrando funcionalidades clave como persistencia de datos, consumo de APIs REST, y uso de hardware nativo del dispositivo.

## ✨ Funcionalidades Clave

*   **Autenticación de Usuarios:** Flujo completo de registro y login con validación de datos en tiempo real. La información del usuario se almacena de forma segura en una base de datos local.
*   **Gestión de Mascotas (CRUD):**
    *   Crea, lee, actualiza y elimina registros de mascotas.
    *   La UI se actualiza de forma reactiva gracias al uso de `Flows` de Kotlin Coroutines.
    *   Los datos persisten en el dispositivo, incluso después de cerrar la app.
*   **Consumo de API Externa:**
    *   Se conecta a la [Dog CEO API](https://dog.ceo/dog-api/) para obtener imágenes de perros.
    *   Demuestra el manejo de llamadas de red asíncronas con Retrofit y Coroutines.
*   **Integración con Hardware:**
    *   **Cámara:** Captura de imágenes usando `ActivityResultContracts`.
    *   **Vibración:** Feedback háptico para mejorar la experiencia de usuario.
    *   **Permisos:** Gestión moderna de permisos en tiempo de ejecución.

## 🛠️ Tecnologías y Arquitectura

Este proyecto aprovecha un conjunto de herramientas y librerías modernas para ofrecer una base sólida y eficiente.

*   **Lenguaje:** [Kotlin](https://kotlinlang.org/)
*   **UI Declarativa:** [Jetpack Compose](https://developer.android.com/jetpack/compose) con tema [Material 3](https://m3.material.io/).
*   **Arquitectura:**
    *   **MVVM (Model-View-ViewModel):** Separa la lógica de la UI del negocio.
    *   **Guía de Arquitectura de Google:** Sigue los principios de capas de UI, Dominio y Datos.
    *   **Flujo de Datos Unidireccional (UDF):** El estado fluye hacia abajo (ViewModel a UI) y los eventos fluyen hacia arriba (UI a ViewModel).
*   **Componentes de Jetpack:**
    *   **Navigation Compose:** Para la navegación entre pantallas.
    *   **Room:** Para la persistencia de datos local (base de datos SQLite).
    *   **ViewModel:** Para gestionar el estado y la lógica de la UI.
*   **Coroutines y Flow:** Para el manejo de operaciones asíncronas y flujos de datos reactivos.
*   **Networking:** [Retrofit](https://square.github.io/retrofit/) para el consumo de API REST y [Gson](https://github.com/google/gson) para la serialización.
*   **Carga de Imágenes:** [Coil](https://coil-kt.github.io/coil/) para cargar imágenes de manera eficiente.
*   **Inyección de Dependencias:** Manual a través de un `AppContainer` para mantener el código desacoplado y testeable.

## 📂 Estructura del Proyecto

La estructura del código está organizada siguiendo los principios de separación de responsabilidades, facilitando la navegación y el mantenimiento.

```
app/src/main/java/com/example/myapplication/
├── data/                  # Capa de datos (fuente de verdad)
│   ├── local/             # Room (DAO, Entidades, Base de Datos)
│   ├── network/           # Retrofit (ApiService para la API externa)
│   ├── model/             # Modelos de datos (DTOs) y entidades
│   ├── repository/        # Repositorios que abstraen el origen de datos
│   └── AppContainer.kt    # Contenedor para inyección de dependencias manual
├── ui/                    # Capa de UI (todo lo relacionado con la pantalla)
│   ├── screens/           # Composables de cada pantalla (Login, Home, etc.)
│   ├── navigation/        # Grafo de navegación y rutas
│   ├── theme/             # Tema de la app (colores, tipografía)
│   └── viewmodel/         # ViewModels de cada pantalla
└── MainActivity.kt        # Actividad principal y host de navegación
```

## 🚀 Cómo Empezar

Sigue estos pasos para clonar y ejecutar el proyecto localmente.

#### Requisitos
*   Android Studio Iguana | 2023.2.1 o superior.
*   JDK 17.
*   Dispositivo o emulador con Android API 26 o superior.

#### Pasos de Instalación
1.  **Clonar el repositorio:**
    ```bash
    git clone https://github.com/tu-usuario/guau-y-miau.git
    ```
2.  **Abrir en Android Studio:**
    *   Abre Android Studio.
    *   Selecciona `Open` y navega hasta la carpeta del proyecto clonado.
3.  **Sincronizar Gradle:**
    *   Espera a que Android Studio descargue todas las dependencias de Gradle.
4.  **Ejecutar la aplicación:**
    *   Selecciona un dispositivo o emulador.
    *   Haz clic en el botón "Run" (▶️).

> **Nota:** Si tienes problemas con el emulador, prueba a hacer un "Wipe Data" desde el `Device Manager` en Android Studio.

## 📸 Capturas de Pantalla

*(Aquí puedes añadir capturas de pantalla de la aplicación para mostrar las funcionalidades clave)*

---
*Este README fue mejorado por Gemini.*
