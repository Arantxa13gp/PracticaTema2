package com.example.practicatema2

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ejemplo.practica2.MainActivity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.jvm.java
import kotlin.math.roundToInt


class NuevaTareaActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Recuperamos datos previos si venimos de pulsar "Editar tarea"
        val tituloPrevio = intent.getStringExtra("EXTRA_TITULO") ?: ""
        val descripcionPrevia = intent.getStringExtra("EXTRA_DESCRIPCION") ?: ""
        val prioridadPrevia = intent.getIntExtra("EXTRA_PRIORIDAD", 5)
        val fechaPrevia = intent.getStringExtra("EXTRA_FECHA") ?: ""

        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PantallaNuevaTarea(
                        tituloInicial = tituloPrevio,
                        descripcionInicial = descripcionPrevia,
                        prioridadInicial = prioridadPrevia,
                        fechaInicial = fechaPrevia,
                        onGuardarTarea = { titulo, descripcion, prioridad, fecha ->
                            val intent = Intent(this, TareaActivity::class.java).apply {
                                // Si están vacíos, enviamos valores por defecto
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
    onGuardarTarea: (String, String, Int, String) -> Unit,
    onVolverMenu: () -> Unit
) {
    var titulo by remember { mutableStateOf(tituloInicial) }
    var descripcion by remember { mutableStateOf(descripcionInicial) }
    var prioridad by remember { mutableFloatStateOf(prioridadInicial.toFloat()) }
    var fechaLimite by remember { mutableStateOf(fechaInicial) }

    // Estado para mostrar el diálogo de selección de fecha
    var mostrarDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Cabecera
            Text(
                text = "Añadir nueva tarea",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            // Campo Título
            Text(text = "Título", fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = titulo,
                onValueChange = { titulo = it },
                placeholder = { Text("Introduce el título de la tarea") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp)) // Spacer entre 15-30 dp

            // Campo Descripción
            Text(text = "Descripción", fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = descripcion,
                onValueChange = { descripcion = it },
                placeholder = { Text("Introduce una descripción") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            )

            Spacer(modifier = Modifier.height(20.dp)) // Spacer entre 15-30 dp

            // Control de Prioridad (Slider 0 a 10)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Prioridad", fontWeight = FontWeight.SemiBold)
                Text(text = "${prioridad.roundToInt()} / 10", fontWeight = FontWeight.Bold)
            }
            Slider(
                value = prioridad,
                onValueChange = { prioridad = it },
                valueRange = 0f..10f,
                steps = 9, // 9 pasos intermedios generan los valores enteros del 0 al 10
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp)) // Spacer entre 15-30 dp

            // Selector de Fecha Límite
            Text(text = "Fecha límite", fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(6.dp))
            Box(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = fechaLimite,
                    onValueChange = {},
                    readOnly = true,
                    placeholder = { Text("Selecciona una fecha") },
                    /**trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "Seleccionar fecha"
                        )
                    },*/
                    modifier = Modifier.fillMaxWidth()
                )
                // Capa transparente clicable sobre el TextField de solo lectura
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .clickable { mostrarDatePicker = true }
                )
            }

            Spacer(modifier = Modifier.height(24.dp)) // Spacer entre 15-30 dp
        }

        // Botones inferiores
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
                shape = RoundedCornerShape(6.dp)
            ) {
                Text(text = "Guardar tarea")
            }

            Button(
                onClick = onVolverMenu,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(6.dp)
            ) {
                Text(text = "Volver al menú principal")
            }
        }
    }

    // Diálogo nativo de Material 3 para elegir fecha
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
                    Text("Aceptar")
                }
            },
            dismissButton = {
                TextButton(onClick = { mostrarDatePicker = false }) {
                    Text("Cancelar")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}