package com.example.authenticationapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth


@Composable
fun LoginScreen(onLogin: (String) -> Unit, onRegisterClick: () -> Unit) {
    var user by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    val auth = remember { FirebaseAuth.getInstance() }

    // Define as cores do gradiente de fundo
    val gradientColors = listOf(Color(0xFF001F3F), Color(0xFF000080))

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(colors = gradientColors)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .padding(32.dp)
                // Adiciona um background para o formulário
                .background(Color.White.copy(alpha = 0.1f), RoundedCornerShape(16.dp))
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Título
            Text(
                "Bem-Vindo de Volta", // Texto mais convidativo
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(32.dp))

            // Campo de e-mail (usuário) com acentos amarelos
            OutlinedTextField(
                value = user,
                onValueChange = { user = it },
                label = { Text("E-mail", color = Color.White.copy(alpha = 0.7f)) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Email Icon",
                        tint = Color(0xFFFFD700) // Cor amarela para o ícone
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFFFD700), // Borda amarela
                    unfocusedBorderColor = Color.White.copy(alpha = 0.5f),
                    cursorColor = Color(0xFFFFD700),
                    focusedLabelColor = Color(0xFFFFD700),
                    unfocusedLabelColor = Color.White.copy(alpha = 0.7f),
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Campo de senha com acentos amarelos
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Senha", color = Color.White.copy(alpha = 0.7f)) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Password Icon",
                        tint = Color(0xFFFFD700) // Cor amarela para o ícone
                    )
                },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFFFD700), // Borda amarela
                    unfocusedBorderColor = Color.White.copy(alpha = 0.5f),
                    cursorColor = Color(0xFFFFD700),
                    focusedLabelColor = Color(0xFFFFD700),
                    unfocusedLabelColor = Color.White.copy(alpha = 0.7f),
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Botão de login com cor amarela e texto azul escuro
            Button(
                onClick = {
                    isLoading = true
                    errorMessage = null
                    auth.signInWithEmailAndPassword(user, password)
                        .addOnCompleteListener { task ->
                            isLoading = false
                            if (task.isSuccessful) {
                                onLogin(task.result?.user?.uid ?: user)
                            } else {
                                errorMessage = task.exception?.localizedMessage
                            }
                        }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp), // Aumento da altura
                shape = RoundedCornerShape(24.dp), // Cantos mais arredondados
                enabled = !isLoading,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFFD700), // Botão amarelo
                    contentColor = Color(0xFF001F3F) // Texto azul escuro
                )
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        color = Color(0xFF001F3F), // Spinner azul escuro
                        strokeWidth = 2.dp,
                        modifier = Modifier.size(28.dp)
                    )
                } else {
                    Text("Entrar", fontSize = 18.sp, fontWeight = FontWeight.Bold) // Texto em negrito
                }
            }

            // Mensagem de erro
            errorMessage?.let {
                Spacer(modifier = Modifier.height(12.dp))
                Text(it, color = Color.Red.copy(alpha = 0.8f), fontSize = 14.sp)
            }

            // Botão para a tela de registro
            TextButton(
                onClick = onRegisterClick,
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text(
                    "Não tem uma conta? Crie aqui.",
                    color = Color.White.copy(alpha = 0.7f)
                )
            }
        }
    }
}