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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ejemplo.practica2.MainActivity

class TareaActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val titulo = intent.getStringExtra("TITULO") ?: "Tarea sin título"
        val descripcion = intent.getStringExtra("DESCRIPCION") ?: "Sin descripción"
        val prioridad = intent.getIntExtra("PRIORIDAD", 5)
        val fecha = intent.getStringExtra("FECHA") ?: "Sin fecha límite"

        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PantallaResumenTarea(
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
fun PantallaResumenTarea(
    titulo: String,
    descripcion: String,
    prioridad: Int,
    fechaLimite: String
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = titulo,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(4.dp))
                .background(Color(0xFF76D747))
                .padding(vertical = 16.dp, horizontal = 12.dp),
            textAlign = TextAlign.Center,
            color = Color.Black,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(20.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 140.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(Color(0xFF76D747))
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = descripcion,
                textAlign = TextAlign.Center,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Prioridad",
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color(0xFF76D747))
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                color = Color.Black,
                fontWeight = FontWeight.Medium
            )

            Text(
                text = "Fecha límite",
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color(0xFF76D747))
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                color = Color.Black,
                fontWeight = FontWeight.Medium
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
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color(0xFF76D747))
                    .padding(horizontal = 24.dp, vertical = 10.dp),
                textAlign = TextAlign.Center,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = fechaLimite,
                modifier = Modifier
                    .widthIn(min = 120.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color(0xFF76D747))
                    .padding(horizontal = 24.dp, vertical = 10.dp),
                textAlign = TextAlign.Center,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
        Spacer(modifier = Modifier.weight(1f))

        // Botón Editar tarea
        Button(
            onClick = {
                val intent = Intent(context, NuevaTareaActivity::class.java).apply {
                    putExtra("TITULO", if (titulo == "Tarea sin título") "" else titulo)
                    putExtra("DESCRIPCION", if (descripcion == "Sin descripción") "" else descripcion)
                    putExtra("PRIORIDAD", prioridad)
                    putExtra("FECHA", if (fechaLimite == "Sin fecha límite") "" else fechaLimite)
                }
                context.startActivity(intent)
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7F47FF)),
            shape = RoundedCornerShape(4.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text(text = "Editar tarea", color = Color.White)
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Botón Volver al menú principal (MainActivity)
        Button(
            onClick = {
                val intent = Intent(context, MainActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                }
                context.startActivity(intent)
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7F47FF)),
            shape = RoundedCornerShape(4.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text(text = "Volver al menú principal", color = Color.White)
        }
    }
}