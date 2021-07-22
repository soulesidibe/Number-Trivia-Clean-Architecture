package com.soulesidibe.numbertrivia.view.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.soulesidibe.numbertrivia.model.NumberTriviaUiModel
import com.soulesidibe.numbertrivia.viewmodel.NumberTriviaViewModel
import java.time.format.TextStyle


@Composable
fun MainScreen(viewModel: NumberTriviaViewModel) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Number Trivia")
                }
            )
        }
    ) {
        Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp)) {
            NumberTriviaContent(viewModel.numberTriviaLiveData)
            Spacer(modifier = Modifier.height(16.dp))
            val onRandomNumber = {
                viewModel.getRandomNumberTrivia()
            }

            val onConcreteNumber = { number: Long ->
                viewModel.getConcreteNumberTrivia(number)
            }
            NumberTriviaSearch(onRandomNumber, onConcreteNumber)
        }
    }
}

@Composable
fun NumberTriviaSearch(onRandomNumber: () -> Unit, onConcreteNumber: (Long) -> Unit) {
    var text by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = text,
            onValueChange = { text = it },
            maxLines = 1,
            label = {
                Text(text = "Number")
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedButton(modifier = Modifier.align(Alignment.End), onClick = {}) {
            Text(text = "Random")
        }
    }
}

@Preview
@Composable
fun PreviewNumberTriviaSearch() {
    NumberTriviaSearch({}, {})
}

@Composable
fun NumberTriviaContent(numberTriviaLiveData: LiveData<Result<NumberTriviaUiModel>>) {
    val state by numberTriviaLiveData.observeAsState()
    if (state?.isSuccess == true) {
        // Show Data
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val numberTrivia = (state as Result<NumberTriviaUiModel>).getOrNull()
            Text(text = "${numberTrivia?.number ?: ""}", style = MaterialTheme.typography.h1)
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = numberTrivia?.description ?: "",
                fontSize = 26.sp,
                maxLines = 3,
                textAlign = TextAlign.Center
            )
        }
    } else {
        // Show error
        Text(text = "Aucune information trouvee :(", fontSize = 26.sp, maxLines = 3)
    }
}

@Preview(name = "Number trivia", device = Devices.PIXEL_4)
@Composable
fun PreviewNumberTriviaContent() {
    NumberTriviaContent(
        MutableLiveData(
            Result.success(
                NumberTriviaUiModel(
                    1960, "C'est la date de l'independance du Senegal"
                )
            )
        )
    )
}