package com.example.pokymon

import android.graphics.drawable.shapes.Shape
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pokymon.ui.theme.Constants
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            readCsv()
        }
    }
}

@Composable
fun pokemons(list: List<Pokemon>) {
    LazyColumn{
        items(items = list, itemContent ={item ->
            cardPokemon(name = item.name, type =item.type , spAttack = item.spAttack, spDefense =item.spDefense, icon = item.icon!!)
        })
    }

}

@Composable
fun readCsv() {
    var listOfPokemon: ArrayList<Pokemon> = arrayListOf()
    csvReader().open(LocalContext.current.assets.open("pokemon.csv")){
        readAllAsSequence().forEach {row: List<String> ->
            run {
                var pokemon: Pokemon = object : Pokemon(row[0], row[1], row[2], row[3],
                    Constants.pokemonIcon.getOrDefault(row[0],R.drawable.ic_launcher_background)
                ){}
                listOfPokemon.add(pokemon)
            }
        }
    }
    pokemons(list = listOfPokemon)
      }
@Composable
fun getModifierCard(type:String): Modifier{
    if(type == "grass") 
        return Modifier
            .background(colorResource(R.color.green))
            .fillMaxSize()
    else if(type == "fire") return Modifier
        .background(colorResource(R.color.red))
        .fillMaxSize()
    else
    return Modifier
        .background(colorResource(id = R.color.blue))
        .fillMaxSize()
}
@Composable
fun getBGColor(type:String): Color{
    if(type == "grass")
        return colorResource(R.color.grass_bg)
    else if(type == "fire") return colorResource(id = R.color.fire_bg)
    else
        return colorResource(id = R.color.water_bg)
}
@Composable
fun getColorButton(type:String): ButtonColors{
    if(type == "grass")
        return ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.button_grass))
    else if(type == "fire") return ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.button_fire))
    else
        return ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.button_water))
}
@Composable
    fun cardPokemon(name:String, type: String, spAttack: String, spDefense: String, icon: Int) { 
        Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(10.dp)
    )
    {
        Row (modifier = getModifierCard(type = type)) {
            Column {
                Text(
                    text = name,
                    fontSize = 30.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp),
                )
                Row(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(10.dp)
                ) {
                    Button(onClick = {}, colors = getColorButton(type = type)) {
                        Text(text = type)

                    }
                    Column(Modifier.fillMaxHeight()) {
                        Row(Modifier.padding(5.dp)) {
                            Text(
                                text = "Attack: ",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            )
                            Text(text = "$spAttack", color = Color.Black)
                        }
                        Row(Modifier.padding(5.dp)) {
                            Text(
                                text = "Defense: ",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            )
                            Text(text = "$spDefense", color = Color.Black)
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.width(50.dp))
                Image(painter = painterResource(id= icon),
                    contentDescription = "",
                    modifier = Modifier
                        .size(120.dp)
                        .align(alignment = Alignment.CenterVertically)
                        .background(color = getBGColor(type = type), shape = CircleShape)
                )


        }
    }
}
