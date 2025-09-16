package com.example.authenticationapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth

@Composable
fun HomeScreen(onSignOut: () -> Unit) {
    val auth = FirebaseAuth.getInstance()
    val userEmail = auth.currentUser?.email

    val gradientColors = listOf(Color(0xFF001F3F), Color(0xFF000080)) // Fundo azul escuro

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(colors = gradientColors)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                "Bem-vindo!",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White // Texto branco
            )

            Spacer(modifier = Modifier.height(16.dp))

            userEmail?.let {
                Text(it, fontSize = 20.sp, color = Color.White) // Cor do e-mail também em branco
            } ?: Text(
                "Usuário não encontrado.",
                fontSize = 18.sp,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = onSignOut,
                modifier = Modifier
                    .fillMaxWidth(0.6f) // Deixa o botão mais estreito
                    .height(56.dp),
                shape = RoundedCornerShape(24.dp), // Cantos mais arredondados
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFFD700), // Cor do botão amarela
                    contentColor = Color(0xFF001F3F) // Cor do texto azul escuro
                )
            ) {
                Text("Sair", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}