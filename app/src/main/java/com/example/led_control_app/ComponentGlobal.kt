package com.example.led_control_app

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun CircularColorButton(
    label: String,
    textLabel: String,
    buttonColor: Color,
    onClick: () -> Unit
) {
    Column(){
        Button(
            onClick = { onClick() },
            shape = CircleShape,
            modifier = Modifier.size(60.dp).padding(horizontal = 1.dp),
            colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
            elevation = ButtonDefaults.buttonElevation(8.dp),

            ) {
            Text(
                text = label,
                //fontSize = 10.sp,
                color = Color.White,
            )
        }
        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = textLabel,
            fontSize = 20.sp,
            color = Color.Black,
            textAlign = TextAlign.Center

        )

    }


}
