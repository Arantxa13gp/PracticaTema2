package com.example.practicatema2

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.roundToInt

class NuevaTareaActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val tituloPrevio = intent.getStringExtra("EXTRA_TITULO") ?: ""
        val descripcionPrevia = intent.getStringExtra("EXTRA_DESCRIPCION") ?: ""
        val prioridadPrevia = intent.getIntExtra("EXTRA_PRIORIDAD", 5)
        val fechaPrevia = intent.getStringExtra("EXTRA_FECHA") ?: ""

        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    PantallaNuevaTarea(
                        tituloInicial = tituloPrevio,
                        descripcionInicial = descripcionPrevia,
                        prioridadInicial = prioridadPrevia,
                        fechaInicial = fechaPrevia,
                        onGuardarTarea = { titulo, descripcion, prioridad, fecha ->
                            val intent = Intent(this, TareaActivity::class.java).apply {
                                putExtra("EXTRA_TITULO", titulo.ifBlank { "Tarea sin título" })
                                putExtra("EXTRA_DESCRIPCION", descripcion.ifBlank { "Sin descripción detallada." })
                                putExtra("EXTRA_PRIORIDAD", prioridad)
                                putExtra("EXTRA_FECHA", fecha.ifBlank { "Sin fecha límite" })
                            }
                            startActivity(intent)
                        },
                        onVolverMenu = {
                            val intent = Intent(this, MainActivity::class.java).apply {
                                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                            }
                            startActivity(intent)
                            finish()
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaNuevaTarea(
    tituloInicial: String = "",
    descripcionInicial: String = "",
    prioridadInicial: Int = 5,
    fechaInicial: String = "",
    onGuardarTarea: (String, String, Int, String) -> Unit = { _, _, _, _ -> },
    onVolverMenu: () -> Unit = {}
) {
    var titulo by remember { mutableStateOf(tituloInicial) }
    var descripcion by remember { mutableStateOf(descripcionInicial) }
    var prioridad by remember { mutableFloatStateOf(prioridadInicial.toFloat()) }
    var fechaLimite by remember { mutableStateOf(fechaInicial) }

    // Estado para mostrar el diálogo de selección de fecha
    var mostrarDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()

    val moradoMockup = Color(0xFF8C52FF)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 24.dp, bottom = 12.dp, start = 24.dp, end = 24.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Añadir nueva tarea",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                textAlign = TextAlign.Center,
                color = Color.Black,
                fontSize = 18.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Título",
                color = Color.Black,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = titulo,
                onValueChange = { titulo = it },
                placeholder = {
                    Text(
                        text = "Introduce el título de la tarea",
                        fontFamily = FontFamily.Monospace,
                        fontSize = 13.sp
                    )
                },
                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 13.sp,
                    fontFamily = FontFamily.Monospace
                ),
                shape = RoundedCornerShape(0.dp),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Descripción",
                color = Color.Black,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = descripcion,
                onValueChange = { descripcion = it },
                placeholder = {
                    Text(
                        text = "Introduce una descripción",
                        fontFamily = FontFamily.Monospace,
                        fontSize = 13.sp
                    )
                },
                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 13.sp,
                    fontFamily = FontFamily.Monospace
                ),
                shape = RoundedCornerShape(0.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Prioridad",
                    color = Color.Black,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp
                )
                Text(
                    text = "${prioridad.roundToInt()} / 10",
                    color = Color.Black,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }
            Slider(
                value = prioridad,
                onValueChange = { prioridad = it },
                valueRange = 0f..10f,
                steps = 9,
                colors = SliderDefaults.colors(
                    thumbColor = moradoMockup,
                    activeTrackColor = moradoMockup
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Fecha límite",
                color = Color.Black,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(6.dp))
            Box(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = fechaLimite,
                    onValueChange = {},
                    readOnly = true,
                    placeholder = {
                        Text(
                            text = "Selecciona una fecha",
                            fontFamily = FontFamily.Monospace,
                            fontSize = 13.sp
                        )
                    },
                    textStyle = TextStyle(
                        color = Color.Black,
                        fontSize = 13.sp,
                        fontFamily = FontFamily.Monospace
                    ),
                    shape = RoundedCornerShape(0.dp),
                    modifier = Modifier.fillMaxWidth()
                )
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .clickable { mostrarDatePicker = true }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = {
                    onGuardarTarea(
                        titulo,
                        descripcion,
                        prioridad.roundToInt(),
                        fechaLimite
                    )
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
                    text = "Guardar tarea",
                    fontSize = 14.sp,
                    fontFamily = FontFamily.Monospace
                )
            }

            Button(
                onClick = onVolverMenu,
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
                    text = "Volver al menú principal",
                    fontSize = 14.sp,
                    fontFamily = FontFamily.Monospace
                )
            }
        }
    }

    if (mostrarDatePicker) {
        DatePickerDialog(
            onDismissRequest = { mostrarDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                        fechaLimite = formatter.format(Date(millis))
                    }
                    mostrarDatePicker = false
                }) {
                    Text(
                        text = "Aceptar",
                        color = moradoMockup,
                        fontFamily = FontFamily.Monospace
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = { mostrarDatePicker = false }) {
                    Text(
                        text = "Cancelar",
                        color = Color.Black,
                        fontFamily = FontFamily.Monospace
                    )
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 740)
@Composable
fun PreviewPantallaNuevaTarea() {
    MaterialTheme {
        PantallaNuevaTarea()
    }
}