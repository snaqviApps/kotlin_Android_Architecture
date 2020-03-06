package ghar.gamec.guessaword.ui.game

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class GameViewModel:ViewModel() {

    val logGameViewModel = "GameViewModel"

    companion object {
        // These represent different important times
        // This is when the game is over
        const val DONE = 0L
        // This is the number of milliseconds in a second
        const val ONE_SECOND = 1000L
        // This is the total time of the game
        const val COUNTDOWN_TIME = 10000L           // 10 Seconds
    }
    private val timer:CountDownTimer

    private lateinit var wordList: MutableList<String>
    private val _word = MutableLiveData<String>()
    val word:LiveData<String>
    get() = _word

    private val _score = MutableLiveData<Int>()                  /** Internal consumption: moved to LiveData */
    val score:LiveData<Int>                                      /** External consumption: for getter()  */
    get() = _score

    /** handling Timer */
    private val _currentTime = MutableLiveData<Long>()
    val currentTime: MutableLiveData<Long>
    get() = _currentTime

    private val _eventGameFinish = MutableLiveData<Boolean>()
        val eventGameFinished:LiveData<Boolean>
        get()  = _eventGameFinish

    init {
        resetList()
        nextWord()
//        _eventGameFinish.value = false
        _score.value = 0

        /** creates Timer so game-finishing decision can be made */
        timer = object : CountDownTimer(COUNTDOWN_TIME, ONE_SECOND){
            override fun onFinish() {
                _eventGameFinish.value = true
            }

            override fun onTick(millisUntilFinished: Long) {
                _currentTime.value = (millisUntilFinished / ONE_SECOND)
            }
        }
        timer.start()
        Log.i(logGameViewModel, "Inside GameViewModel Class")
    }

    override fun onCleared() {
        super.onCleared()
        timer.cancel()                      /** cancel the timer */
        Log.i(logGameViewModel, "GameViewModel object cleared / destroyed")
    }

    private fun nextWord() {
        if(wordList.isEmpty()) {
            resetList()                                               /** taking 'timer' approach, instead of using 'gameFinished()' */
        }
        _word.value = wordList.removeAt(0)                    /** Internally using 'Mutable' version of field 'word' */

    }

    /**  onSkip() and onCorrect() moved from GameFragment */
    fun onSkip() {
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
            "bubble",
            "water bottle",
            "kick"
        )
        wordList.shuffle()          // instant
    }

    fun onGameFinishComplete(){
        _eventGameFinish.value = false
    }

}