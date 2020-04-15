package com.diki.idn.androidtrivianavigation

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
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

    //starting an activity with new intent
    private fun shareSuccess() {
        startActivity(getShareIntent())
    }

    //showing the share menu
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.share_menu, menu)

        //check the activity resolves
        if (null == getShareIntent().resolveActivity(activity!!.packageManager)) {
            //hide the menu
            menu.findItem(R.id.action_share)?.isVisible = false //setVisible
        }
    }

    //sharing from the menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_share -> shareSuccess()
        }
        return super.onOptionsItemSelected(item)
    }
}