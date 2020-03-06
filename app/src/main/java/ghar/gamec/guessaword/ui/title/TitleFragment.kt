package ghar.gamec.guessaword.ui.title

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController

import ghar.gamec.guessaword.R
import ghar.gamec.guessaword.databinding.FragmentTitleBinding

/**
 * A simple [Fragment] subclass.
 */
class TitleFragment : Fragment() {

    private lateinit var bindingTitle: FragmentTitleBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        bindingTitle = DataBindingUtil.inflate(
            inflater, R.layout.fragment_title, container, false)


//        return inflater.inflate(R.layout.fragment_title, container, false)

        bindingTitle.playGameButton.setOnClickListener {
            findNavController().navigate(TitleFragmentDirections.actionTitleDestinationToGameDestination())       // doesn't work
//            findNavController().navigate(R.id.action_title_destination_to_game_destination)                     // using action_id with 'Directions' when using safe-args
        }
        return bindingTitle.root

    }

}
