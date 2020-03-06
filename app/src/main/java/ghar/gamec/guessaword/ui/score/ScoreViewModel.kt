package ghar.gamec.guessaword.ui.score

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ScoreViewModel(finalScore:Int):ViewModel() {

    private val _scoreScore = MutableLiveData<Int>()
    val scoreScore: LiveData<Int>
        get() = _scoreScore                                     // Encapsulation

    private val _eventPlayAgain = MutableLiveData<Boolean>()
    val eventPlayAgain: LiveData<Boolean>
        get() = _eventPlayAgain


    init {
        _scoreScore.value = finalScore
        Log.i("log_ScoreViewModel", "ScoreView Model got score: $finalScore")
    }

    fun onPlayAgainComplete() {
        _eventPlayAgain.value = false
    }

    fun  onPlayAgain(){             // moved from ScoreFragment so that viewModel could replace the navigation_via_navigation-Graph
        _eventPlayAgain.value = true
    }

}