package ghar.gamec.guessaword.ui.game

import android.os.CountDownTimer
import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

private val CORRECT_BUZZ_PATTERN = longArrayOf(100, 100, 100, 100, 100, 100)
private val PANIC_BUZZ_PATTERN = longArrayOf(0, 200)
private val GAME_OVER_BUZZ_PATTERN = longArrayOf(0, 2000)
private val NO_BUZZ_PATTERN = longArrayOf(0)

enum class BuzzType(val pattern: LongArray) {
    CORRECT(CORRECT_BUZZ_PATTERN),
    GAME_OVER(GAME_OVER_BUZZ_PATTERN),
    COUNTDOWN_PANIC(PANIC_BUZZ_PATTERN),
    NO_BUZZ(NO_BUZZ_PATTERN)
}

class GameViewModel:ViewModel() {

    val logGameViewModel = "GameViewModel"
    companion object {
        // These represent different important times

        // This is when the game is over
        const val DONE = 0L

        // This is the time when the phone will start buzzing each second
        private const val COUNTDOWN_PANIC_SECONDS = 10L

        // This is the number of milliseconds in a second
        const val ONE_SECOND = 1000L

        // This is the total time of the game
        const val COUNTDOWN_TIME = 15000L           // 10 Seconds
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
    val currentTime: LiveData<Long>
    get() = _currentTime

    val currentTimeString = Transformations.map(currentTime, {time_instant ->
        DateUtils.formatElapsedTime(time_instant)
    }
    )

    // Event that triggers the phone to buzz using different patterns, determined by BuzzType
    private val _eventBuzz = MutableLiveData<BuzzType>()
    val eventBuzz: LiveData<BuzzType>
        get() = _eventBuzz

    private val _eventGameFinish = MutableLiveData<Boolean>()
        val eventGameFinished:LiveData<Boolean>
        get()  = _eventGameFinish

    init {
        resetList()
        nextWord()
        _score.value = 0

        /** creates Timer so game-finishing decision can be made */
        timer = object : CountDownTimer(COUNTDOWN_TIME, ONE_SECOND){
            override fun onFinish() {
                _currentTime.value = DONE
                _eventGameFinish.value = true
                _eventBuzz.value = BuzzType.GAME_OVER
            }

            override fun onTick(millisUntilFinished: Long) {
                _currentTime.value = (millisUntilFinished / ONE_SECOND)
                if(millisUntilFinished / ONE_SECOND <= COUNTDOWN_PANIC_SECONDS){
                    _eventBuzz.value = BuzzType.COUNTDOWN_PANIC
                }
            }
        }
        timer.start()
        Log.i(logGameViewModel, "Inside GameViewModel Class")
    }

    private fun nextWord() {
        if(wordList.isEmpty()) {
            resetList()                                               /** taking 'timer' approach, instead of using 'gameFinished()' */
        }
        _word.value = wordList.removeAt(0)                    /** Internally using 'Mutable' version of field 'word' */

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

    /**  onSkip() and onCorrect() moved from GameFragment */
    fun onSkip() {
        _score.value = (score.value)?.minus(1)

        nextWord()
    }

    fun onCorrect() {   // made it Public, by removing 'private'
        _score.value = (score.value)?.plus(1)
        _eventBuzz.value = BuzzType.CORRECT
        nextWord()
    }

    fun onGameFinishComplete(){
        _eventGameFinish.value = false
    }

    override fun onCleared() {
        super.onCleared()
        timer.cancel()                      /** cancel the timer */
        Log.i(logGameViewModel, "GameViewModel object cleared / destroyed")
    }

    fun onCompleteBuzz() {
//        TODO("Not yet implemented")
        _eventBuzz.value = BuzzType.NO_BUZZ
    }
}