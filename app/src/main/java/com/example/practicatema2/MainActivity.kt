package com.ejemplo.practica2

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.practicatema2.NuevaTareaActivity
import com.example.practicatema2.PerfilActivity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    PantallaPrincipal()
                }
            }
        }
    }
}

@Composable
fun PantallaPrincipal(
    nombreUsuario: String = "[nombre de usuario]",
    rolUsuario: String = "[rol de usuario]"
) {
    val context = LocalContext.current

    // Fecha actual real con el formato exacto del mock-up: [día], [mes] de [año]
    val formatoFecha = SimpleDateFormat("d, MMMM 'de' yyyy", Locale("es", "ES"))
    val fechaActual = formatoFecha.format(Date())

    // Colores exactos extraídos del mock-up
    val verdeMockup = Color(0xFF7ED957)
    val rojoMockup = Color(0xFFFF5757)
    val moradoMockup = Color(0xFF8C52FF)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 24.dp, bottom = 12.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // BLOQUE SUPERIOR Y CENTRAL (mismo margen izquierdo y derecho en todos los elementos verdes/rojos)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 32.dp, end = 24.dp),
            verticalArrangement = Arrangement.spacedBy(26.dp)
        ) {
            // 1. Caja verde superior con la fecha actual
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .background(verdeMockup),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = fechaActual,
                    color = Color.Black,
                    fontSize = 14.sp,
                    fontFamily = FontFamily.Monospace,
                    textAlign = TextAlign.Center
                )
            }

            // 2. Fila de perfil: Caja roja a la izquierda + 3 cajas verdes a la derecha
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(155.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Caja roja: Foto de perfil con placeholder por defecto
                Box(
                    modifier = Modifier
                        .weight(0.36f)
                        .fillMaxHeight()
                        .background(rojoMockup),
                    contentAlignment = Alignment.Center
                ) {
                    /*Image(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = "Foto de perfil por defecto",
                        modifier = Modifier.size(80.dp)
                    )*/
                }

                // Hueco blanco central exacto del mock-up
                Spacer(modifier = Modifier.weight(0.14f))

                // 3 cajas verdes apiladas a la derecha (ligeramente más bajas que la caja roja)
                Column(
                    modifier = Modifier
                        .weight(0.50f)
                        .fillMaxHeight()
                        .padding(vertical = 8.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(36.dp)
                            .background(verdeMockup),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "¡Bienvenido!",
                            color = Color.Black,
                            fontSize = 13.sp,
                            fontFamily = FontFamily.Monospace
                        )
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(36.dp)
                            .background(verdeMockup),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = nombreUsuario,
                            color = Color.Black,
                            fontSize = 13.sp,
                            fontFamily = FontFamily.Monospace
                        )
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(36.dp)
                            .background(verdeMockup),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = rolUsuario,
                            color = Color.Black,
                            fontSize = 13.sp,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }
            }

            // 3. Caja verde "Tareas pendientes"
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .background(verdeMockup),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Tareas pendientes",
                    color = Color.Black,
                    fontSize = 14.sp,
                    fontFamily = FontFamily.Monospace
                )
            }

            // 4. Tres filas estáticas de Tareas (Caja izquierda + hueco blanco + Caja derecha)
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(22.dp)
            ) {
                listOf("Tarea 1", "Tarea 2", "Tarea 3").forEach { tarea ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(0.50f)
                                .height(48.dp)
                                .background(verdeMockup),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = tarea,
                                color = Color.Black,
                                fontSize = 13.sp,
                                fontFamily = FontFamily.Monospace
                            )
                        }

                        // Hueco blanco amplio entre Tarea X y Fecha límite
                        Spacer(modifier = Modifier.weight(0.25f))

                        Box(
                            modifier = Modifier
                                .weight(0.25f)
                                .height(48.dp)
                                .background(verdeMockup),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Fecha límite",
                                color = Color.Black,
                                fontSize = 12.sp,
                                fontFamily = FontFamily.Monospace,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }

        // 5. BLOQUE INFERIOR DE BOTONES MORADOS (un poco más ancho hacia la derecha, igual que en la imagen)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 32.dp, end = 14.dp, top = 16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Button(
                onClick = {
                    context.startActivity(Intent(context, NuevaTareaActivity::class.java))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                shape = RoundedCornerShape(0.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = moradoMockup,
                    contentColor = Color.Black
                )
            ) {
                Text(
                    text = "Añadir tareas",
                    fontSize = 14.sp,
                    fontFamily = FontFamily.Monospace
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {
                        context.startActivity(Intent(context, PerfilActivity::class.java))
                    },
                    modifier = Modifier
                        .weight(0.43f)
                        .height(60.dp),
                    shape = RoundedCornerShape(0.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = moradoMockup,
                        contentColor = Color.Black
                    )
                ) {
                    Text(
                        text = "Perfil",
                        fontSize = 14.sp,
                        fontFamily = FontFamily.Monospace
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 740)
@Composable
fun PreviewMockup() {
    MaterialTheme {
        PantallaPrincipal()
    }
}