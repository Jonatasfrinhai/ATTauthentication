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
fun RegistrationScreen(onRegistrationSuccess: (String) -> Unit, onLoginClick: () -> Unit) {
    var user by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    val auth = remember { FirebaseAuth.getInstance() }

    // Changed background colors to dark blue shades
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
                // Added a background for the form to make it stand out
                .background(Color.White.copy(alpha = 0.1f), RoundedCornerShape(16.dp))
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                "Criar Conta", // Changed text to be more direct
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White // Changed title color to white
            )

            Spacer(modifier = Modifier.height(32.dp))

            // E-mail field with yellow accents
            OutlinedTextField(
                value = user,
                onValueChange = { user = it },
                label = { Text("E-mail", color = Color.White.copy(alpha = 0.7f)) },
                leadingIcon = {
                    Icon(Icons.Default.Person, contentDescription = "Email Icon", tint = Color(0xFFFFD700)
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFFFD700), // Yellow border
                    unfocusedBorderColor = Color.White.copy(alpha = 0.5f), // White border
                    cursorColor = Color(0xFFFFD700),
                    focusedLabelColor = Color(0xFFFFD700),
                    unfocusedLabelColor = Color.White.copy(alpha = 0.7f),
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Password field with yellow accents
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Senha", color = Color.White.copy(alpha = 0.7f)) },
                leadingIcon = {
                    Icon(Icons.Default.Lock, contentDescription = "Password Icon", tint = Color(0xFFFFD700))
                },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFFFD700), // Yellow border
                    unfocusedBorderColor = Color.White.copy(alpha = 0.5f), // White border
                    cursorColor = Color(0xFFFFD700),
                    focusedLabelColor = Color(0xFFFFD700),
                    unfocusedLabelColor = Color.White.copy(alpha = 0.7f),
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Registration button with yellow color
            Button(
                onClick = {
                    isLoading = true
                    errorMessage = null
                    auth.createUserWithEmailAndPassword(user, password)
                        .addOnCompleteListener { task ->
                            isLoading = false
                            if (task.isSuccessful) {
                                onRegistrationSuccess(task.result?.user?.uid ?: user)
                            } else {
                                errorMessage = task.exception?.localizedMessage
                            }
                        }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp), // Increased button height
                shape = RoundedCornerShape(24.dp), // More rounded corners
                enabled = !isLoading,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFFD700), // Yellow button
                    contentColor = Color(0xFF001F3F) // Dark blue text
                )
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        color = Color(0xFF001F3F), // Dark blue spinner
                        strokeWidth = 2.dp,
                        modifier = Modifier.size(28.dp)
                    )
                } else {
                    Text("Cadastrar", fontSize = 18.sp, fontWeight = FontWeight.Bold) // Bolded text
                }
            }

            errorMessage?.let {
                Spacer(modifier = Modifier.height(12.dp))
                Text(it, color = Color.Red.copy(alpha = 0.8f), fontSize = 14.sp) // Adjusted error text color
            }

            TextButton(onClick = onLoginClick) {
                Text(
                    "Já tem uma conta? Faça login.",
                    color = Color.White.copy(alpha = 0.7f) // White text for button
                )
            }
        }
    }
}