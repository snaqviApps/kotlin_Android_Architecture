package ghar.gamec.guessaword.ui.score

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class ScoreViewModelFactory(private val factory_finalScore:Int):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        if (modelClass.isAssignableFrom(ScoreViewModel::class.java)){
            return ScoreViewModel(factory_finalScore) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}