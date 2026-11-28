package com.example.practicatema2

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    val contexto = LocalContext.current
    val formatoFecha = SimpleDateFormat("d, MMMM 'de' yyyy", Locale("es", "ES"))
    val fechaActual = formatoFecha.format(Date())
    val moradoMockup = Color(0xFF8C52FF)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 24.dp, bottom = 12.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 32.dp, end = 24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
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

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(155.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .weight(0.36f)
                        .fillMaxHeight(),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.perfil),
                        contentDescription = "Foto de perfil",
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Spacer(modifier = Modifier.weight(0.14f))

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
                            .height(36.dp),
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
                            .height(36.dp),
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
                            .height(36.dp),
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

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Tareas pendientes",
                    color = Color.Black,
                    fontSize = 14.sp,
                    fontFamily = FontFamily.Monospace
                )
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(20.dp) //
            ) {
                listOf("Tarea 1", "Tarea 2", "Tarea 3").forEach { tarea ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(0.30f)
                                .height(48.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = tarea,
                                color = Color.Black,
                                fontSize = 13.sp,
                                fontFamily = FontFamily.Monospace
                            )
                        }

                        Spacer(modifier = Modifier.weight(0.20f))

                        Box(
                            modifier = Modifier
                                .weight(0.30f)
                                .height(48.dp),
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

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 14.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Button(
                onClick = {
                    contexto.startActivity(Intent(contexto, NuevaTareaActivity::class.java))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
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

            Button(
                onClick = {
                    contexto.startActivity(Intent(contexto, TitulosActivity::class.java))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(0.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = moradoMockup,
                    contentColor = Color.Black
                )
            ) {
                Text(
                    text = "Mis títulos",
                    fontSize = 14.sp,
                    fontFamily = FontFamily.Monospace
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 30.dp, end = 14.dp, top = 8.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = {
                            contexto.startActivity(Intent(contexto, PerfilActivity::class.java))
                        },
                        modifier = Modifier
                            .weight(0.40f)
                            .height(40.dp),
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
                    Spacer(modifier = Modifier.weight(0.20f))

                    Button(
                        onClick = {},
                        modifier = Modifier
                            .weight(0.40f)
                            .height(40.dp),
                        shape = RoundedCornerShape(0.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = moradoMockup,
                            contentColor = Color.Black
                        )
                    ) {
                        Text(
                            text = "Ajustes",
                            fontSize = 14.sp,
                            fontFamily = FontFamily.Monospace
                        )
                    }
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