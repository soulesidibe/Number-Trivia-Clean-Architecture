package com.soulesidibe.numbertrivia.view.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.soulesidibe.numbertrivia.model.NumberTriviaUiModel
import com.soulesidibe.numbertrivia.viewmodel.NumberTriviaViewModel


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
        Column {
            NumberTriviaContent(viewModel.numberTriviaLiveData)
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
            Text(text = numberTrivia?.description ?: "", fontSize = 26.sp, maxLines = 3)
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