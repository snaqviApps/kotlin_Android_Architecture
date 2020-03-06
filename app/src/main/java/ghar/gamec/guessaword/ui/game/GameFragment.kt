package ghar.gamec.guessaword.ui.game


import android.os.Bundle
import android.text.format.DateUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
            inflater, R.layout.fragment_game,
            container,
            false)

        /** ViewModel added to dataBinding */

        Log.i("GameFragment", "ViewModelProvider called in GameFragment_OnCreateView() ")
        gameViewModel = ViewModelProvider(this).get(GameViewModel::class.java)
        bindingGame.gameViewModelXml = gameViewModel
        /** Life-Cycle Library creating viewModel class */

        /** Code lines# 44 - 50 replaced as data-binding in associated xml-file fragment_game.xml
         * is now using
         *  <data>  </data>
         *  block and associated @{() -> viewModel.<methods>}
         *  using "onClick" attribute
         */
//        bindingGame.correctButton.setOnClickListener {
//            gameViewModel.onCorrect()       // moved to viewModel-version of same method
//        }
//
//        bindingGame.skipButton.setOnClickListener {
//            gameViewModel.onSkip()          // moved to viewModel-version of same method
//        }

        gameViewModel.word.observe(this.viewLifecycleOwner, Observer { newWord ->
            bindingGame.wordText.text = newWord.toString()
        })

        gameViewModel.score.observe(this.viewLifecycleOwner, Observer { newScore ->                     /** LiveData Observer for 'word' (UIController i.e Fragment */
            bindingGame.scoreText.text = newScore.toString()
        })

        gameViewModel.currentTime.observe(this.viewLifecycleOwner, Observer { instant ->              /** update current Time */
            bindingGame.timerText.text = DateUtils.formatElapsedTime(instant)
        })

        gameViewModel.eventGameFinished.observe(this.viewLifecycleOwner, Observer {hasChanged ->
            if(hasChanged){
                gameFinished()
                gameViewModel.onGameFinishComplete()            // Stopping live-Data from further updating
            }
        })
        return bindingGame.root
    }

    private fun gameFinished() {
        val scoreStatus = gameViewModel.score.value ?: 0
        val action = GameFragmentDirections.actionGameFragmentToScoreFragment(scoreStatus)
        findNavController(this).navigate(action)
    }

}
