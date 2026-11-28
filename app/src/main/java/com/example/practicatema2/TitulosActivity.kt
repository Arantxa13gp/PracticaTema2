package com.example.practicatema2

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
import java.io.BufferedReader
import java.io.InputStreamReader

data class TituloItem(
    val titulo: String,
    val fechaLanzamiento: String,
    val descripcion: String,
    val puntuacion: String,
    val plataforma: String,
    val numJugadores: String
)

class TitulosActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    PantallaTitulos(
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

@Composable
fun PantallaTitulos(
    onVolverMenu: () -> Unit = {}
) {
    val contexto = LocalContext.current
    val moradoMockup = Color(0xFF8C52FF)

    val listaTitulos = remember { leerTitulosDesdeCsv(contexto) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 24.dp, bottom = 12.dp, start = 24.dp, end = 24.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Mis títulos",
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            textAlign = TextAlign.Center,
            color = Color.Black,
            fontSize = 18.sp,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold
        )

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            items(listaTitulos) { item ->
                TarjetaTitulo(item)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

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

@Composable
fun TarjetaTitulo(item: TituloItem) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.Black)
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = item.titulo,
                modifier = Modifier.weight(0.45f),
                textAlign = TextAlign.Center,
                color = Color.Black,
                fontSize = 13.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.weight(0.10f))

            Text(
                text = item.fechaLanzamiento,
                modifier = Modifier.weight(0.45f),
                textAlign = TextAlign.Center,
                color = Color.Black,
                fontSize = 12.sp,
                fontFamily = FontFamily.Monospace
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = item.descripcion,
                textAlign = TextAlign.Center,
                color = Color.Black,
                fontSize = 13.sp,
                fontFamily = FontFamily.Monospace
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = item.puntuacion,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                color = Color.Black,
                fontSize = 12.sp,
                fontFamily = FontFamily.Monospace
            )

            Text(
                text = item.plataforma,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                color = Color.Black,
                fontSize = 12.sp,
                fontFamily = FontFamily.Monospace
            )

            Text(
                text = item.numJugadores,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                color = Color.Black,
                fontSize = 12.sp,
                fontFamily = FontFamily.Monospace
            )
        }
    }
}

fun leerTitulosDesdeCsv(context: Context): List<TituloItem> {
    val lista = mutableListOf<TituloItem>()
    try {
        val inputStream = context.assets.open("titulos.csv")
        val reader = BufferedReader(InputStreamReader(inputStream))
        val lineas = reader.readLines()

        for (linea in lineas.drop(1)) {
            if (linea.isBlank()) continue
            // Soporta separador por punto y coma (;) o coma (,)
            val delimitador = if (linea.contains(";")) ";" else ","
            val columnas = linea.split(delimitador)

            if (columnas.size >= 6) {
                lista.add(
                    TituloItem(
                        titulo = columnas[0].trim(),
                        fechaLanzamiento = columnas[1].trim(),
                        descripcion = columnas[2].trim(),
                        puntuacion = columnas[3].trim(),
                        plataforma = columnas[4].trim(),
                        numJugadores = columnas[5].trim()
                    )
                )
            }
        }
        reader.close()
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return lista
}

@Preview(showBackground = true, widthDp = 360, heightDp = 740)
@Composable
fun PreviewPantallaTitulos() {
    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TarjetaTitulo(
                TituloItem(
                    titulo = "The Witcher 3",
                    fechaLanzamiento = "19/05/2015",
                    descripcion = "RPG de mundo abierto de fantasía oscura.",
                    puntuacion = "9.5",
                    plataforma = "PC / PS5",
                    numJugadores = "1 jugador"
                )
            )
        }
    }
}