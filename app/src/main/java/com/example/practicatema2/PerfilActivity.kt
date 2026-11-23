package com.example.practicatema2

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ejemplo.practica2.MainActivity

class PerfilActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PantallaPerfil(
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
fun PantallaPerfil(
    nombreUsuarioInicial: String = "[Nombre de usuario]",
    contrasenaInicial: String = "12345678",
    onVolverMenu: () -> Unit = {}
) {
    // Estados reactivos para los controles de la pantalla
    var nombreUsuario by remember { mutableStateOf(nombreUsuarioInicial) }
    var contrasena by remember { mutableStateOf(contrasenaInicial) }
    val opcionesRol = listOf("Administrador", "Cliente")
    var rolSeleccionado by remember { mutableStateOf(opcionesRol[0]) }
    var recibirCorreo by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 1. CABECERA
            Text(
                text = "Perfil de usuario",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            // 2. FOTO DE PERFIL CENTRADA (Con placeholder por defecto)
            Image(
                painter = painterResource(id = android.R.drawable.ic_menu_gallery),
                contentDescription = "Foto de perfil del usuario",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(140.dp)
                    .height(160.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.LightGray)
            )

            Spacer(modifier = Modifier.height(20.dp))

            // 3. FILA CON [Nombre de usuario] (solo lectura) Y [rol de usuario] (dinámico)
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Campo de entrada bloqueado a solo lectura
                OutlinedTextField(
                    value = nombreUsuario,
                    onValueChange = { nombreUsuario = it },
                    readOnly = true,
                    singleLine = true,
                    modifier = Modifier.weight(0.55f)
                )

                // Cuadro de texto que refleja el RadioButton seleccionado
                Text(
                    text = rolSeleccionado,
                    modifier = Modifier.weight(0.45f),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(20.dp)) // Spacer de 15-30 dp

            // 4. CAMPO CONTRASEÑA (Solo lectura + ocultación de caracteres tipo password)
            OutlinedTextField(
                value = contrasena,
                onValueChange = { contrasena = it },
                readOnly = true,
                singleLine = true,
                label = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp)) // Spacer de 15-30 dp

            // 5. SECCIÓN INFERIOR DIVIDIDA EN DOS COLUMNAS: Rol actual y Ajustes de correo
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                // Columna izquierda: Rol actual + RadioButtons excluyentes
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Rol actual",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    opcionesRol.forEach { opcion ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { rolSeleccionado = opcion }
                                .padding(vertical = 4.dp)
                        ) {
                            RadioButton(
                                selected = (rolSeleccionado == opcion),
                                onClick = { rolSeleccionado = opcion }
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = opcion, fontSize = 15.sp)
                        }
                    }
                }

                // Columna derecha: Ajustes de correo + Checkbox
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Ajustes de correo",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { recibirCorreo = !recibirCorreo }
                            .padding(vertical = 4.dp)
                    ) {
                        Checkbox(
                            checked = recibirCorreo,
                            onCheckedChange = { recibirCorreo = it }
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "Recibir correo", fontSize = 15.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }

        // 6. BOTÓN INFERIOR
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewPantallaPerfil() {
    MaterialTheme {
        PantallaPerfil()
    }
}