package com.example.aula14

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.aula14.ui.theme.Aula14Theme
import com.google.gson.Gson

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
    LayoutMain()
        }
    }
}

@Composable
fun LayoutMain(){
   val navController = rememberNavController()
    NavHost(navController = navController , startDestination = "lista" ){
        composable("lista") {ListaContato(navController) }

        composable("detalhes/{contatoJson}") {

            backStackEntry ->
            val contatoJson = backStackEntry.arguments?.getString("contatoJson")
            val contato = Gson().fromJson(contatoJson, Contato::class.java)
            DetalhesContato(navController, contato)
        }
    }

}

@Composable
fun ListaContato(navController: NavController){

     var listaContatos = listOf<Contato>(
         Contato("edson", "9997563","edson@gmail.com", "rua carlos gomes 111"),
         Contato("joao", "8876435", "joao@gmail.com", "rua klentz 423"),
         Contato("andre", "1235467", "andre@gmail.com", "rua joamarcos678")
     )

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(15.dp)) {
        Text(text = "Lista de Contatos", fontSize = 22.sp, modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(15.dp))

        LazyColumn{
            items(listaContatos){
                contato -> Text(text = "${contato.nome} (${contato.fone})",
                fontSize = 20.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .clickable {

                        val contatoJson = Gson().toJson(contato)
                        navController.navigate("detalhes/$contatoJson")
                    })

            }
        }
    }
}


@Composable
fun DetalhesContato(navController: NavController, contato: Contato){
      Column(
          Modifier
              .fillMaxSize()
              .padding(15.dp)) {

          Text(text = "Detalhes do Contato", fontSize = 22.sp, modifier = Modifier.fillMaxWidth())

          Spacer(modifier = Modifier.height(15.dp))

          Text(text = "Nome: ${contato.nome}\n" +
                 "Fone: ${contato.fone}\n" +
                 "E-mail: ${contato.email}\n" +
                 "Endereço: ${contato.endereco}",
                  fontSize = 18.sp,
                  modifier = Modifier.fillMaxWidth())

          Button(onClick = {

              navController.popBackStack()

          }) {

              Text(text = "voltar")
          }

      }
}

