package com.example.s1121138

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import com.example.s1121138.ui.theme.S1121138Theme
import androidx.compose.runtime.*
import androidx.compose.ui.input.pointer.pointerInput
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        enableEdgeToEdge()
        setContent {
            S1121138Theme {
                FullScreenContent()
            }
        }
    }
}

@Composable
fun FullScreenContent() {
    val context = LocalContext.current as? ComponentActivity

    // 定义背景颜色列表
    val colors = listOf(
        Color(0xff95fe95),
        Color(0xfffdca0f),
        Color(0xfffea4a4),
        Color(0xffa5dfed)
    )
    var currentColorIndex by remember { mutableStateOf(0) }
    var dragDirection by remember { mutableStateOf(0) }
    var elapsedTime by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(1000)
            elapsedTime += 1
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colors[currentColorIndex])
            .pointerInput(Unit) {
                detectHorizontalDragGestures(
                    onHorizontalDrag = { _, dragAmount ->
                        dragDirection = when {
                            dragAmount > 0 -> 1
                            dragAmount < 0 -> -1
                            else -> 0
                        }
                    },
                    onDragEnd = {
                        if (dragDirection == 1) {
                            currentColorIndex = (currentColorIndex + 1) % colors.size
                        } else if (dragDirection == -1) {
                            currentColorIndex = (currentColorIndex - 1 + colors.size) % colors.size
                        }
                        dragDirection = 0
                    }
                )
            },
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text(text = "2024期末上機考(資管二B陳恩儒)")
            Spacer(modifier = Modifier.height(16.dp))
            Image(
                painter = painterResource(id = R.drawable.class_b),
                contentDescription = "B班同學"
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "遊戲持續時間 0 秒")
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "您的成績 0 分")
            Spacer(modifier = Modifier.height(16.dp))
            
            Button(
                onClick = {
                    context?.apply {
                        finishAffinity()
                        System.exit(0)
                    }
                },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text(text = "關閉應用")
            }
        }
    }
}
