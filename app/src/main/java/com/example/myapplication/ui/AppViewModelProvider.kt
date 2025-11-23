package com.example.myapplication.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.myapplication.GuauYMiauApplication
import com.example.myapplication.ui.features.NativeViewModel
import com.example.myapplication.ui.login.LoginViewModel
import com.example.myapplication.ui.login.RegisterViewModel
import com.example.myapplication.ui.login.WelcomeViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            LoginViewModel(guauYMiauApplication().container.userRepository)
        }
        initializer {
            RegisterViewModel(guauYMiauApplication().container.userRepository)
        }
        initializer {
            WelcomeViewModel(
                guauYMiauApplication().container.userRepository,
                guauYMiauApplication().container.dogApiService
            )
        }
        initializer {
             NativeViewModel()
        }
    }
}

fun CreationExtras.guauYMiauApplication(): GuauYMiauApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as GuauYMiauApplication)
