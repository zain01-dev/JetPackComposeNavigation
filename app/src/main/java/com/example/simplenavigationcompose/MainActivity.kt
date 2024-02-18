package com.example.simplenavigationcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.simplenavigationcompose.ui.navigation.NavGraph
import com.example.simplenavigationcompose.ui.theme.SimpleNavComposeAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SendComplexData()
            //App()
            //Test()
            //MainScreen()
        }
    }
}

@Composable fun Test(){

    //step 1
    val navController = rememberNavController()

    //step 2
    NavHost(navController = navController, startDestination = "firstScreen"){

        //step3
        composable("firstScreen"){
            //step 3
            LoginScreen()
        }
        composable("secondScreen"){
            //step 3
            MainScreen2()
        }
    }
}

@Composable
private fun MainScreen() {
    SimpleNavComposeAppTheme {
        // Controller should be decalred at top level of the app
        // and it should be remembered because it will be used in multiple screens
        val navController = rememberNavController()
        NavGraph(navController)
    }
}

@Composable
private fun SendComplexData(){
    val navController = rememberNavController()
    NavHost(navController, startDestination = "firstScreen") {
        composable("firstScreen") { FirstScreen(navController) }
        composable("secondScreen/{data}",
            arguments = listOf(navArgument("data") {
                type = NavType.ParcelableType(ComplexData::class.java)
            })
        ) { backStackEntry ->
            secondScreen(backStackEntry.arguments?.getParcelable("data"))
        }
    }
}

@Composable fun App(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "registration" ){
        // in graph we have multiple nodes on which we can navigate to
        // each node is a composable function
        composable(route = "registration"){
            // we can also pass a lambda function to the composable function instead of navController
            // this lambda function will be called when we click on the text
            RegistrationScreen(navController)
        }
        composable(route = "login"){
            LoginScreen()
        }
        composable(route = "mainScreen2"){
            MainScreen2()
        }
    }
}
// we receive navController as a parameter because we need it to navigate to other screens
@Composable fun RegistrationScreen(controller: NavController){
    Text(text = "Registration Screen",
        style = MaterialTheme.typography.h1,
        modifier = Modifier.clickable {
            controller.navigate("login")
        }
    )
}

@Composable fun LoginScreen(){
    Text(text = "Login Screen", style = MaterialTheme.typography.h1)
}

@Composable fun MainScreen2(){
    Text(text = "Main Screen2", style = MaterialTheme.typography.h1)
}


@Composable
fun FirstScreen(navController: NavController) {
    val data = ComplexData("Zain", 29)
    Button(onClick = { navController.navigate("secondScreen/${data}") }) {
        Text("Navigate to Second Screen")
    }
}

@Composable
fun secondScreen(data: ComplexData?) {
    Text("Received data: ${data?.name}, ${data?.age}")
}

