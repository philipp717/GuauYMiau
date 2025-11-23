# Guau y Miau 🐾

**Link de Trello:** https://trello.com/b/o3ZEVOBO/guauymiau

## Descripción

**Guau y Miau** es una aplicación de Android moderna desarrollada en Kotlin que implementa una arquitectura **MVVM (Model-View-ViewModel)** robusta. Utiliza **Jetpack Compose** para la interfaz de usuario y sigue las mejores prácticas recomendadas por Google para el desarrollo de apps escalables y mantenibles.

El proyecto demuestra la integración de persistencia local, consumo de APIs REST, manejo de recursos nativos y navegación compleja.

## Tecnologías Utilizadas

*   **Lenguaje:** Kotlin
*   **Arquitectura:** MVVM (Model-View-ViewModel)
*   **UI Toolkit:** Jetpack Compose (Material Design 3)
*   **Navegación:** Jetpack Navigation Compose
*   **Persistencia de Datos (Local):** Room Database (SQLite)
*   **Red (API):** Retrofit & Gson
*   **Carga de Imágenes:** Coil
*   **Inyección de Dependencias:** Manual (AppContainer)

## Funcionalidades Principales

#### A. Flujo de Autenticación y Usuarios
*   **Registro:** Creación de cuentas con validación de campos en tiempo real. Los datos se persisten en una base de datos local segura.
*   **Login:** Autenticación contra la base de datos local (Room).
*   **Sesión:** Gestión de estado de sesión a través de ViewModels.

#### B. Gestión de Mascotas (CRUD)
*   **Persistencia:** Las mascotas se guardan en el dispositivo, sobreviviendo al cierre de la app.
*   **Listado Dinámico:** Uso de `Flow` para actualizaciones reactivas de la UI cuando cambian los datos.
*   **Edición y Eliminado:** Modificación de nombre/tipo y eliminación de registros con actualización instantánea.

#### C. Integración de API Externa
*   Conexión con la API pública **Dog CEO** para obtener imágenes aleatorias de perros.
*   Demostración de manejo de llamadas asíncronas con Coroutines y Retrofit.

#### D. Recursos Nativos
*   **Cámara:** Captura de fotos utilizando `ActivityResultContracts`.
*   **Vibración:** Uso del servicio del sistema para feedback háptico.
*   **Permisos:** Manejo de permisos en tiempo de ejecución (Runtime Permissions).

## Arquitectura y Estructura

El proyecto sigue el principio de **Separation of Concerns** (Separación de preocupaciones):

*   **Capa de Datos (`data/`):**
    *   `local/`: Base de datos Room, Entidades y DAOs.
    *   `network/`: Interfaces de Retrofit para APIs externas.
    *   `UserRepository`: Repositorio que unifica las fuentes de datos y expone `Flows` a la UI.
*   **Capa de UI (`ui/`):**
    *   `ViewModels`: Gestionan el estado de la UI (`UiState`) y la lógica de negocio.
    *   `Screens`: Composables que solo dibujan la interfaz basada en el estado recibido.

## Puesta en Marcha

#### Requisitos
*   Android Studio (versión actual).
*   JDK 17 o superior.
*   Dispositivo/Emulador con API 26+ (Recomendado API 35).

#### Pasos
1.  **Sincronizar Gradle:** Asegúrate de que todas las dependencias se descarguen.
2.  **Ejecutar:** Usa el botón "Run" en Android Studio.
3.  **Emulador:** Si encuentras errores de instalación, realiza un "Wipe Data" en el emulador desde el Device Manager.

## Estructura del Proyecto

```
app/src/main/java/com/example/myapplication/
├── data/
│   ├── local/          # Room (Dao, Entity, Database)
│   ├── network/        # Retrofit (ApiService)
│   ├── AppContainer.kt # Inyección de dependencias
│   └── UserRepository.kt
├── model/              # Modelos de dominio (User, Pet)
├── ui/
│   ├── features/       # Pantallas de funciones nativas (Cámara, GPS)
│   ├── login/          # Pantallas de Auth (Login, Register, Welcome)
│   ├── theme/          # Tema Material 3
│   └── AppViewModelProvider.kt # Factory de ViewModels
└── MainActivity.kt     # Punto de entrada y NavHost
```
