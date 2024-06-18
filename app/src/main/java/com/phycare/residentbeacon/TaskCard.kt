package com.phycare.residentbeacon

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp

/*
@Composable
fun TaskCard(taskCardDto: TaskCardDto, onCheckedChange: (Int, Boolean) -> Unit) {

    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .height(70.dp)
            .fillMaxWidth(),
        // shape = Constants.cardShape
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = 12.dp, horizontal = 20.dp)
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = painterResource(id = R.drawable.baseline_delete_24),
                contentDescription = "default task icon",
                modifier = Modifier.weight(0.7f)
            )
            Text(
                text = taskCardDto.title,
                fontWeight = FontWeight.Medium,
                style = if (taskCardDto.done) {
                    LocalTextStyle.current.copy(textDecoration = TextDecoration.LineThrough)
                } else LocalTextStyle.current.copy(),
                modifier = Modifier
                    .weight(4f)
                    .padding(horizontal = 20.dp)
            )
            Box (
                modifier = Modifier
                    .background(Color.White)
                    .weight(0.5f)
                    .aspectRatio(1f)
            )
            {
                Checkbox(
                    checked = taskCardDto.done,
                    onCheckedChange = { isChecked -> onCheckedChange(taskCardDto.id, isChecked) },
                    modifier = Modifier.scale(1.5f),
                    colors = CheckboxDefaults.colors(
                        checkedColor = Color(0xFF2984BA),
                        uncheckedColor = Color(0xFF2984BA),
                        checkmarkColor = Color.White,
                    )
                )
            }
        }
    }
}*/
