package ghar.gamec.guessaword.game

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class GameViewModel:ViewModel() {

    val LOG_GameViewModel = "GameViewModel"

    // The current word
//    var word = ""
    private val _word = MutableLiveData<String>()               /** 'current-word', moved to LiveData */
    val word:LiveData<String>
    get() = _word                                               /** Applied 'Backing property': Mutable-LiveData (_word) being exposed through 'LiveData'
                                                                  * so _word will be exposed to external world as 'Live-Data' only and not 'Mutable'
                                                                  * hence External - Class (like GameFragment) will not be able to change it
                                                                  */
//    The current score
//    var score = 0
    private val _score = MutableLiveData<Int>()                  /** Internal consumption: moved to LiveData */
    val score:LiveData<Int>                                      /** External consumption: for getter()  */
    get() = _score

    private val _eventGameFinish = MutableLiveData<Boolean>()
        val eventGameFinished:LiveData<Boolean>
        get()  = _eventGameFinish

    private lateinit var wordList: MutableList<String>
    init {
        Log.i(LOG_GameViewModel, "Inside GameViewModel Class")

        /** below two methods moved from associated class, GameFragment */
        resetList()
        nextWord()
        _eventGameFinish.value = false
        _score.value = 0                                        /** moved to data-encapsulation */
    }

    override fun onCleared() {
        super.onCleared()
        Log.i(LOG_GameViewModel, "GameViewModel object cleared / destroyed")
    }

    private fun nextWord() {
        if(wordList.isEmpty()){

//            gameFinished()
            _eventGameFinish.value = true                                     // Game_finished_event
        }else{
            _word.value = wordList.removeAt(0)                          /** Internally using 'Mutable' version of field 'word' */
        }
    }

    /**  onSkip() and onCorrect() moved from GameFragment */
    fun onSkip() {
        //    score--
        _score.value = (score.value)?.minus(1)
        nextWord()
    }

    fun onCorrect() {   // made it Public, by removing 'private'
        _score.value = (score.value)?.plus(1)
        nextWord()
    }

    private fun resetList() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        wordList = mutableListOf(
            "queen",
            "apple",
            "sizzer",
            "river",
            "school",
            "snail",
            "soup",
            "calendar",
            "sad",
            "desk",
            "guitar",
            "home",
            "railway",
            "zebra",
            "jelly",
            "car",
            "crow",
            "trade",
            "bag",
            "roll",
            "computer",
            "tv",
            "speaker",
            "lamp",
            "bubble"
        )
        wordList.shuffle()          // instant
    }

    fun onGameFinishComplete(){
        _eventGameFinish.value = false
    }

}