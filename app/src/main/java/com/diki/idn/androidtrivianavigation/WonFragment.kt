package com.diki.idn.androidtrivianavigation

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.diki.idn.androidtrivianavigation.databinding.FragmentWonBinding

/**
 * A simple [Fragment] subclass.
 */
class WonFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentWonBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_won, container, false)
        binding.btnNextMatch.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_wonFragment_to_gameFragment)
        }
        val args = WonFragmentArgs.fromBundle(arguments!!)
        Toast.makeText(
            context,
            "NumCorrect: ${args.numCorrect}," +
                    "NumQuestions: ${args.numQuestions}",
            Toast.LENGTH_SHORT
        ).show()
        return binding.root
    }

    private fun getShareIntent(): Intent {
        val args = WonFragmentArgs.fromBundle((arguments!!))
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.setType("text")
            .putExtra(
                Intent.EXTRA_TEXT,
                getString(R.string.share_success_text, args.numCorrect, args.numQuestions)
            )
        return shareIntent
    }

    private fun shareSuccess() {
        startActivity(getShareIntent())
    }
}