package com.example.practicatema2

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class TareaActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val titulo = intent.getStringExtra("EXTRA_TITULO") ?: "Tarea sin título"
        val descripcion = intent.getStringExtra("EXTRA_DESCRIPCION") ?: "Sin descripción"
        val prioridad = intent.getIntExtra("EXTRA_PRIORIDAD", 5)
        val fecha = intent.getStringExtra("EXTRA_FECHA") ?: "Sin fecha límite"

        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    PantallaTarea(
                        titulo = titulo,
                        descripcion = descripcion,
                        prioridad = prioridad,
                        fechaLimite = fecha
                    )
                }
            }
        }
    }
}

@Composable
fun PantallaTarea(
    titulo: String = "Tarea de ejemplo",
    descripcion: String = "Descripción detallada de la tarea.",
    prioridad: Int = 5,
    fechaLimite: String = "30/11/2026"
) {
    val contexto = LocalContext.current
    val moradoMockup = Color(0xFF8C52FF)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 24.dp, bottom = 12.dp, start = 24.dp, end = 24.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = titulo,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                textAlign = TextAlign.Center,
                color = Color.Black,
                fontSize = 18.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(20.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 140.dp)
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = descripcion,
                    textAlign = TextAlign.Center,
                    color = Color.Black,
                    fontSize = 14.sp,
                    fontFamily = FontFamily.Monospace
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Prioridad",
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                    color = Color.Black,
                    fontSize = 14.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.SemiBold
                )

                Text(
                    text = "Fecha límite",
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                    color = Color.Black,
                    fontSize = 14.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = prioridad.toString(),
                    modifier = Modifier
                        .widthIn(min = 120.dp)
                        .padding(horizontal = 24.dp, vertical = 10.dp),
                    textAlign = TextAlign.Center,
                    color = Color.Black,
                    fontSize = 14.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = fechaLimite,
                    modifier = Modifier
                        .widthIn(min = 120.dp)
                        .padding(horizontal = 24.dp, vertical = 10.dp),
                    textAlign = TextAlign.Center,
                    color = Color.Black,
                    fontSize = 14.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = {
                    val intent = Intent(contexto, NuevaTareaActivity::class.java).apply {
                        putExtra("EXTRA_TITULO", if (titulo == "Tarea sin título") "" else titulo)
                        putExtra("EXTRA_DESCRIPCION", if (descripcion == "Sin descripción") "" else descripcion)
                        putExtra("EXTRA_PRIORIDAD", prioridad)
                        putExtra("EXTRA_FECHA", if (fechaLimite == "Sin fecha límite") "" else fechaLimite)
                    }
                    contexto.startActivity(intent)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = moradoMockup,
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(0.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(
                    text = "Editar tarea",
                    fontSize = 14.sp,
                    fontFamily = FontFamily.Monospace
                )
            }

            Button(
                onClick = {
                    val intent = Intent(contexto, MainActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                    }
                    contexto.startActivity(intent)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = moradoMockup,
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(0.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(
                    text = "Volver al menú principal",
                    fontSize = 14.sp,
                    fontFamily = FontFamily.Monospace
                )
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 740)
@Composable
fun PreviewPantallaResumenTarea() {
    MaterialTheme {
        PantallaTarea()
    }
}