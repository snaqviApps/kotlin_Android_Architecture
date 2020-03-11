package ghar.gamec.guessaword.ui.game

import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.getSystemService
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment.findNavController

import ghar.gamec.guessaword.R
import ghar.gamec.guessaword.databinding.FragmentGameBinding


/**
 * A simple [Fragment] subclass.
 */

class GameFragment : Fragment() {

    private lateinit var gameViewModel: GameViewModel
    private lateinit var bindingGame: FragmentGameBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        bindingGame = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_game,
            container,
            false)

        gameViewModel = ViewModelProvider(this).get(GameViewModel::class.java)          /** ViewModel added to dataBinding */
        bindingGame.gameViewModelView = gameViewModel

        // TODO: Call binding.setLifecycleOwner to make the data binding lifecycle aware:
        bindingGame.lifecycleOwner = this.viewLifecycleOwner

        Log.i("GameFragment", "ViewModelProvider called in GameFragment_OnCreateView() ")

        /** Below code moved to viewModel so it could be sent to view using data-binding */
//        gameViewModel.currentTime.observe(this.viewLifecycleOwner, Observer { instant ->              /** update current Time */
//            bindingGame.timerText.text = DateUtils.formatElapsedTime(instant)
//        })

        gameViewModel.eventGameFinished.observe(this.viewLifecycleOwner, Observer {hasChanged ->
            if(hasChanged){
                gameFinished()
                gameViewModel.onGameFinishComplete()            // Stopping live-Data from further updating
            }
        })

        gameViewModel.eventBuzz.observe(this.viewLifecycleOwner, Observer { buzzing ->
            if(buzzing != BuzzType.NO_BUZZ){
                buzz(buzzing.pattern)
                gameViewModel.onCompleteBuzz()
            }
        })
        return bindingGame.root
    }

    private fun gameFinished() {
        val scoreStatus = gameViewModel.score.value ?: 0
        val action = GameFragmentDirections.actionGameFragmentToScoreFragment(scoreStatus)
        findNavController(this).navigate(action)
    }

    private fun buzz(pattern: LongArray) {
        val buzzer = activity?.getSystemService<Vibrator>()
        buzzer.let {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                it?.vibrate(VibrationEffect.createWaveform(pattern, -1))
            }else{
                it?.vibrate(pattern, -1)
            }
        }
    }

}
