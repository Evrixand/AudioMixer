package com.example.audiomixer.ui.commandCenter

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.audiomixer.R
import com.example.audiomixer.databinding.FragmentCommandCenterBinding

class CommandCenterFragment : Fragment() {

    private var _binding: FragmentCommandCenterBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val commandCenterViewModel =
            ViewModelProvider(this).get(CommandCenterViewModel::class.java)

        _binding = FragmentCommandCenterBinding.inflate(inflater, container, false)
        val root: View = binding.root

        commandCenterViewModel.text.observe(viewLifecycleOwner) {
            binding.textCommandCenter.text = it
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}