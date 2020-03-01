package ghar.gamec.guessaword.score


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
//import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController

import ghar.gamec.guessaword.R
import ghar.gamec.guessaword.databinding.FragmentScoreBinding

/**
 * A simple [Fragment] subclass.
 */
class ScoreFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val bindingScore:FragmentScoreBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_score, container, false
        )
        bindingScore.scoreText.text = ScoreFragmentArgs.fromBundle(arguments!!).scoreSent.toString()
        bindingScore.playAgainButton.setOnClickListener {
            onPlayAgain()
        }
//        return inflater.inflate(R.layout.fragment_score, container, false)            // No dataBinding
        return bindingScore.root
    }

    private fun  onPlayAgain(){
        findNavController(this).navigate(ScoreFragmentDirections.actionScoreDestinationToGameDestination())
//        findNavController(this).navigate(ScoreFragmentDirections.actionRestart())                   // doesn't work
    }
}
