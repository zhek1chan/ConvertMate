package com.example.convertmate.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.convertmate.R
import com.example.convertmate.databinding.FragmentResultBinding

class ResultFragment : Fragment() {

    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!
    private lateinit var from: String
    private lateinit var to: String
    private lateinit var num: String
    private lateinit var result: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        from = arguments?.getString("from")!!
        to = arguments?.getString("to")!!
        num = arguments?.getString("num")!!
        result = arguments?.getString("result")!!
        binding.resultFrom.setText(buildString {
            append(num)
            append(" ")
            append(from)
        })
        binding.resultTo.setText(buildString {
            append(result)
            append(" ")
            append(to)
        })
        binding.btnNavBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}