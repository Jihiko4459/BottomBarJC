package com.example.bottombarjc

import android.annotation.SuppressLint
import android.icu.text.CaseMap.Title
import android.media.Image
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bottombarjc.ui.theme.BottomBarJCTheme
import com.example.bottombarjc.ui.theme.GreenJC

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BottomBarJCTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    //modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyBottomAppBar()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)//нужно для работы с конмпонентом BottomSheet
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")//для работы с компонентом Scaffold
@Composable
fun MyBottomAppBar(){
    val navigationController= rememberNavController()
    val context= LocalContext.current.applicationContext
    val selected=remember{
        mutableStateOf(Icons.Default.Home)
    }//выбранный по умолчанию элемент BottomNavigationBar
    val sheetState= rememberModalBottomSheetState()//сохраняет состояние листа
    var showBottomSheet by remember {
        mutableStateOf(false)
    }//состояние для показа нижнего листа

    Scaffold (//помогает нескольким компонентам работать вместе
        bottomBar = {
            BottomAppBar(
                containerColor = GreenJC
            ) {
                IconButton(onClick = {
                    selected.value=Icons.Default.Home
                    navigationController.navigate(Screens.Home.screen){
                        popUpTo(0)
                    }
                },
                    modifier = Modifier.weight(1f)) {
                    Icon(Icons.Default.Home, contentDescription = null,
                        modifier = Modifier.size(26.dp), tint=if(selected.value==Icons.Default.Home) Color.White else Color.DarkGray)
                }
                IconButton(onClick = {
                    selected.value=Icons.Default.Search
                    navigationController.navigate(Screens.Search.screen){
                        popUpTo(0)
                    }
                },
                    modifier = Modifier.weight(1f)) {
                    Icon(Icons.Default.Search, contentDescription = null,
                        modifier = Modifier.size(26.dp), tint=if(selected.value==Icons.Default.Search) Color.White else Color.DarkGray)
                }
                Box(modifier = Modifier
                    .weight(1f)
                    .padding(16.dp),
                    contentAlignment = Alignment.Center){
                    FloatingActionButton(onClick = {
                        showBottomSheet=true
                    }) {
                        Icon(Icons.Default.Add, contentDescription = null, tint = GreenJC)
                    }
                }
                IconButton(onClick = {
                    selected.value=Icons.Default.Notifications
                    navigationController.navigate(Screens.Notification.screen){
                        popUpTo(0)
                    }
                },
                    modifier = Modifier.weight(1f)) {
                    Icon(Icons.Default.Notifications, contentDescription = null,
                        modifier = Modifier.size(26.dp), tint=if(selected.value==Icons.Default.Notifications) Color.White else Color.DarkGray)
                }
                IconButton(onClick = {
                    selected.value=Icons.Default.Person
                    navigationController.navigate(Screens.Profile.screen){
                        popUpTo(0)
                    }
                },
                    modifier = Modifier.weight(1f)) {
                    Icon(Icons.Default.Person, contentDescription = null,
                        modifier = Modifier.size(26.dp), tint=if(selected.value==Icons.Default.Person) Color.White else Color.DarkGray)
                }
            }
        }
    )
    {paddingValues ->
        NavHost(navController = navigationController,
            startDestination = Screens.Home.screen,
            modifier = Modifier.padding(paddingValues)){
            composable(Screens.Home.screen){ Home()}
            composable(Screens.Search.screen){ Search() }
            composable(Screens.Notification.screen){ Notification() }
            composable(Screens.Profile.screen){ Profile() }
            composable(Screens.Post.screen){ Post()}
        }
    }
    if (showBottomSheet){
        //пишем ф-ю для создания BottomSheet
        ModalBottomSheet(onDismissRequest = {
            showBottomSheet=false
        }, sheetState=sheetState) {
            Column(modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)) {
                BottomSheetItem(icon = Icons.Default.ThumbUp, title = "Create a Post") {
                    showBottomSheet=false
                    navigationController.navigate(Screens.Post.screen){
                        popUpTo(0)
                    }
                }
                BottomSheetItem(icon = Icons.Default.Star, title ="Add a story" ) {
                    Toast.makeText(context, "Story", Toast.LENGTH_SHORT).show()
                }
                BottomSheetItem(icon = Icons.Default.PlayArrow, title ="Create a Reel" ) {
                    Toast.makeText(context, "Reel", Toast.LENGTH_SHORT).show()
                }
                BottomSheetItem(icon = Icons.Default.Favorite, title ="Go Live" ) {
                    Toast.makeText(context, "Live", Toast.LENGTH_SHORT).show()
                }
                
            }
        }
    }
}


@Composable
fun BottomSheetItem(icon:ImageVector, title: String, onClick: () -> Unit){

    Row(verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),//расстояние между двумя рядами
        modifier = Modifier.clickable { onClick() }) {
        Icon(imageVector = icon, contentDescription = null, tint = GreenJC)
        Text(text = title, color = GreenJC, fontSize = 22.sp)
    }

}