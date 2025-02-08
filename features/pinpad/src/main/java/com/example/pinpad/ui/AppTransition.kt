package com.example.pinpad.ui

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut

object AppTransition {
    val defaultEnter = fadeIn()
    val defaultExit = fadeOut()

    val fadeThroughEnter = fadeIn(
        initialAlpha = 0.35f,
        animationSpec = tween(durationMillis = 210, delayMillis = 90)
    ) + scaleIn(
        initialScale = 0.92f,
        animationSpec = tween(durationMillis = 210, delayMillis = 90)
    )

    val shared2AxisEnterForward = scaleIn(
        initialScale = 0.0f,
        animationSpec = tween(300)
    ) + fadeIn(animationSpec = tween(durationMillis = 60, delayMillis = 60, easing = LinearEasing))
    val shared2AxisEnterBackward = scaleIn(
        initialScale = 1.1f,
        animationSpec = tween(300)
    )
    val shared2AxisExitForward = scaleOut(
        targetScale = 1.1f,
        animationSpec = tween(durationMillis = 300),
    )
}