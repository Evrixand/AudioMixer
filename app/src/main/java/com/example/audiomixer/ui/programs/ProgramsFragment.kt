package com.example.audiomixer.ui.programs

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.audiomixer.R
import com.example.audiomixer.databinding.FragmentCommandCenterBinding
import com.example.audiomixer.databinding.FragmentProgramsBinding
import com.example.audiomixer.ui.commandCenter.CommandCenterViewModel

class ProgramsFragment : Fragment() {

    private var _binding: FragmentProgramsBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val programsViewModel =
            ViewModelProvider(this).get(ProgramsViewModel::class.java)

        _binding = FragmentProgramsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        programsViewModel.text.observe(viewLifecycleOwner) {
            binding.textPrograms.text = it
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}