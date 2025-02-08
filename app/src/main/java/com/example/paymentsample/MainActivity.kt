package com.example.paymentsample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.paymentsample.ui.theme.PaymentSampleTheme
import com.example.pinpad.pinpad.PinPadScreen
import com.example.pinpad.ui.Destination
import com.example.pinpad.ui.appDestination
import com.example.pinpad.ui.aaa
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PaymentSampleTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    Greeting(
//                        name = "Android 15",
//                        modifier = Modifier.padding(innerPadding)
//                    )
                    Screens(navController = rememberNavController(), innerPadding)
                }
            }
        }
    }
}

@Composable
private fun Screens(
    navController: NavHostController,
    padding: PaddingValues
) {
    val navigateToReceipt: (String) -> Unit = { transaction ->
        val route = Destination.ReceiptScreen.buildRoute(transaction)
        navController.navigate(route)
    }

    NavHost(
        navController = navController,
        startDestination = Destination.PinPadScreen.route,
        modifier = Modifier
            .padding(padding)
            .fillMaxSize()
    ) {
        appDestination(Destination.PinPadScreen) {
            PinPadScreen(
                paddingValues = padding,
                navigateToReceipt = navigateToReceipt,
                viewModel = hiltViewModel()
            )
        }

        appDestination(Destination.ReceiptScreen) { backStackEntry ->
            // TODO: Implement ReceiptScreen
        }
    }
}

//@Composable
//fun Greeting(name: String, modifier: Modifier = Modifier) {
//    Text(
//        text = "Hello $name!",
//        modifier = modifier
//    )
//}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PaymentSampleTheme {
        //Greeting("Android 15")
    }
}