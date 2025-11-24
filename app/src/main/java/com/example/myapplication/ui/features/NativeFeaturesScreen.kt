package com.example.myapplication.ui.features

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat

@Composable
fun NativeFeaturesScreen() {
    val context = LocalContext.current
    var imageBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var locationText by remember { mutableStateOf("Ubicación no obtenida") }
    
    // --- DEFINICIÓN DE PERMISOS ---
    val galleryPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        Manifest.permission.READ_MEDIA_IMAGES
    } else {
        Manifest.permission.READ_EXTERNAL_STORAGE
    }

    // --- LAUNCHERS ---
    
    // 1. Launcher para pedir MÚLTIPLES permisos al inicio
    val multiplePermissionsLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val cameraGranted = permissions[Manifest.permission.CAMERA] ?: false
        val galleryGranted = permissions[galleryPermission] ?: false
        
        if (!cameraGranted || !galleryGranted) {
            Toast.makeText(context, "Se recomiendan todos los permisos para usar las funciones", Toast.LENGTH_SHORT).show()
        }
    }

    // 2. Launcher Cámara
    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) {
        imageBitmap = it
    }

    // 3. Launcher Galería
    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            imageBitmap = if (Build.VERSION.SDK_INT < 28) {
                @Suppress("DEPRECATION")
                MediaStore.Images.Media.getBitmap(context.contentResolver, it)
            } else {
                val source = ImageDecoder.createSource(context.contentResolver, it)
                ImageDecoder.decodeBitmap(source)
            }
        }
    }

    // --- SOLICITUD AUTOMÁTICA AL INICIO ---
    LaunchedEffect(Unit) {
        val permissionsToRequest = mutableListOf<String>()

        // Verificamos Cámara
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            permissionsToRequest.add(Manifest.permission.CAMERA)
        }

        // Verificamos Galería
        if (ContextCompat.checkSelfPermission(context, galleryPermission) != PackageManager.PERMISSION_GRANTED) {
            permissionsToRequest.add(galleryPermission)
        }

        // Si falta alguno, los pedimos todos juntos
        if (permissionsToRequest.isNotEmpty()) {
            multiplePermissionsLauncher.launch(permissionsToRequest.toTypedArray())
        }
    }

    // Vibration logic
    fun vibratePhone() {
        val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibratorManager.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(500)
        }
    }

    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Recursos Nativos", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(20.dp))

            // Botón Cámara
            Button(onClick = {
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    cameraLauncher.launch()
                } else {
                    multiplePermissionsLauncher.launch(arrayOf(Manifest.permission.CAMERA))
                }
            }) {
                Text("Tomar Foto (Cámara)")
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Botón Galería
            Button(onClick = {
                if (ContextCompat.checkSelfPermission(context, galleryPermission) == PackageManager.PERMISSION_GRANTED) {
                    galleryLauncher.launch("image/*")
                } else {
                    multiplePermissionsLauncher.launch(arrayOf(galleryPermission))
                }
            }) {
                Text("Seleccionar de Galería")
            }

            // Imagen
            imageBitmap?.let {
                Spacer(modifier = Modifier.height(20.dp))
                Image(
                    bitmap = it.asImageBitmap(),
                    contentDescription = "Imagen seleccionada",
                    modifier = Modifier.size(200.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Vibración
            Button(onClick = { vibratePhone() }) {
                Text("Vibrar Dispositivo")
            }

            Spacer(modifier = Modifier.height(20.dp))
            
            // GPS
            Text(text = locationText)
            Button(onClick = {
                locationText = "Lat: -33.4489, Lon: -70.6693 (Simulado)"
            }) {
                Text("Obtener GPS (Simulado)")
            }
        }
    }
}
