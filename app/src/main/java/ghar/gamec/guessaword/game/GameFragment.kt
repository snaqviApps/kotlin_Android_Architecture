package ghar.gamec.guessaword.game


import android.os.Bundle
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

    /** moved to viewModel_Game */
//    // The current word
//    private var word = ""
//
//    // The current score
//    private var score = 0
//    private lateinit var wordList: MutableList<String>

    private lateinit var bindingGame: FragmentGameBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        bindingGame = DataBindingUtil.inflate(inflater, R.layout.fragment_game, container, false)

//        gameViewModel = ViewModelProviders.of(this).get(GameViewModel::class.java)        // Deprecated for line below
        Log.i("GameFragment", "ViewModelProvider called in GameFragment_OnCreateView() ")
        gameViewModel = ViewModelProvider(this).get(GameViewModel::class.java)
        /** Life-Cycle Library creating viewModle class */

        bindingGame.correctButton.setOnClickListener {
            gameViewModel.onCorrect()       // moved to viewModel-version of same method
        }
        bindingGame.skipButton.setOnClickListener {
            gameViewModel.onSkip()          // moved to viewModel-version of same method
        }

        gameViewModel.word.observe(this.viewLifecycleOwner, Observer { newWord ->
            bindingGame.wordText.text = newWord.toString()
        })

        gameViewModel.score.observe(this.viewLifecycleOwner, Observer {newScore ->                     /** LiveData Observer for 'word' (UIController i.e Fragment */
            bindingGame.scoreText.text = newScore.toString()
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
//        val action = GameFragmentDirections.actionGameFragmentToScoreFragment(gameViewModel.score.value ?: 0)  // concise
        val scoreStatus = gameViewModel.score.value ?: 0
        val action = GameFragmentDirections.actionGameFragmentToScoreFragment(scoreStatus)            /** Passing score to Scroe - Fragment using safe-Args
                                                                                                        * ?: is Elvis operator i.e: null reference checker
                                                                                                        * like "if a is not null, use it, otherwise use some non-null value x"
                                                                                                        */
        findNavController(this).navigate(action)
    }

}
