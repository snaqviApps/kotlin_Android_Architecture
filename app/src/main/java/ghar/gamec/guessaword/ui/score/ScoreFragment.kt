package ghar.gamec.guessaword.ui.score


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
//import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController

import ghar.gamec.guessaword.R
import ghar.gamec.guessaword.databinding.FragmentScoreBinding


/**
 * A simple [Fragment] subclass.
 */
class ScoreFragment : Fragment() {

    private lateinit var scoreViewModel: ScoreViewModel
    private lateinit var scoreViewModelFactory: ScoreViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val bindingScore: FragmentScoreBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_score,
            container,
            false
        )

        scoreViewModelFactory = ScoreViewModelFactory(ScoreFragmentArgs.fromBundle(arguments!!).scoreSent)              // Integer value got from GameFragment
//        scoreViewModel = ViewModelProvider(this).get(ScoreViewModel::class.java)                                      // normal ViewModel reference
        scoreViewModel = ViewModelProvider(this, scoreViewModelFactory)                                          // ViewModel reference via ViewModelFactory
            .get(ScoreViewModel::class.java)

        bindingScore.scoreViewModelView = scoreViewModel

        // TODO: Call binding.setLifecycleOwner to make the data binding lifecycle aware:
        bindingScore.lifecycleOwner = this.viewLifecycleOwner

        // Add observer for score
//        scoreViewModel.scoreScore.observe(this.viewLifecycleOwner, Observer {scoreReceived ->
//            bindingScore.scoreText.text = scoreReceived.toString()
//        })

        /** Code below
         * bindingScore.playAgainButton.setOnClickListener
         * {
         *      scoreViewModel.onPlayAgain()
         * }
         * got replaced with below viewModel_added_to_data-binding in fragment_score.xml
         *  <data> ... </data>
         *  block and associated @{() -> viewModel.<methods>}
         *  using "onClick" attribute
         */


        /** Navigates back to title when button is pressed */
        scoreViewModel.eventPlayAgain.observe(this.viewLifecycleOwner, Observer {playAgain ->
            if(playAgain){
                val action = ScoreFragmentDirections.actionScoreDestinationToGameDestination()
                findNavController(this).navigate(action)
                scoreViewModel.onPlayAgainComplete()
            }
        })

        return bindingScore.root
    }

}
