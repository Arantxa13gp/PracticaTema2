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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class PerfilActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
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
    var nombreUsuario by remember { mutableStateOf(nombreUsuarioInicial) }
    var contrasena by remember { mutableStateOf(contrasenaInicial) }
    val opcionesRol = listOf("Administrador", "Cliente")
    var rolSeleccionado by remember { mutableStateOf(opcionesRol[0]) }
    var recibirCorreo by remember { mutableStateOf(true) }

    val moradoMockup = Color(0xFF8C52FF)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 24.dp, bottom = 12.dp, start = 24.dp, end = 24.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Perfil de usuario",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                textAlign = TextAlign.Center,
                color = Color.Black,
                fontSize = 18.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold
            )

            Box(
                modifier = Modifier
                    .width(140.dp)
                    .height(155.dp),
                contentAlignment = Alignment.Center
            ) {
                    Image(
                        painter = painterResource(id = R.drawable.perfil),
                        contentDescription = "Foto de perfil",
                        modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = nombreUsuario,
                    onValueChange = { nombreUsuario = it },
                    readOnly = true,
                    singleLine = true,
                    textStyle = TextStyle(
                        color = Color.Black,
                        fontSize = 13.sp,
                        fontFamily = FontFamily.Monospace
                    ),
                    shape = RoundedCornerShape(0.dp),
                    modifier = Modifier.weight(0.55f)
                )

                Text(
                    text = rolSeleccionado,
                    modifier = Modifier.weight(0.45f),
                    textAlign = TextAlign.Center,
                    color = Color.Black,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 13.sp
                )
            }

            Spacer(modifier = Modifier.height(22.dp))

            OutlinedTextField(
                value = contrasena,
                onValueChange = { contrasena = it },
                readOnly = true,
                singleLine = true,
                label = {
                    Text(
                        text = "Contraseña",
                        fontFamily = FontFamily.Monospace,
                        fontSize = 12.sp
                    )
                },
                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 13.sp,
                    fontFamily = FontFamily.Monospace
                ),
                shape = RoundedCornerShape(0.dp),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(26.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Rol actual",
                        color = Color.Black,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
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
                                onClick = { rolSeleccionado = opcion },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = moradoMockup
                                )
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = opcion,
                                color = Color.Black,
                                fontFamily = FontFamily.Monospace,
                                fontSize = 13.sp
                            )
                        }
                    }
                }

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Ajustes de correo",
                        color = Color.Black,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
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
                            onCheckedChange = { recibirCorreo = it },
                            colors = CheckboxDefaults.colors(
                                checkedColor = moradoMockup
                            )
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Recibir correo",
                            color = Color.Black,
                            fontFamily = FontFamily.Monospace,
                            fontSize = 13.sp
                        )
                    }
                }
            }
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

@Preview(showBackground = true, widthDp = 360, heightDp = 740)
@Composable
fun PreviewPantallaPerfil() {
    MaterialTheme {
        PantallaPerfil()
    }
}