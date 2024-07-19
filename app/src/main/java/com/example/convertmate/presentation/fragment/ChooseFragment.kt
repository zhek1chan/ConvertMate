package com.example.convertmate.presentation.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.convertmate.R
import com.example.convertmate.data.Constants
import com.example.convertmate.domain.Resource
import com.example.convertmate.domain.Utility
import com.example.convertmate.data.network.dto.Rates
import com.example.convertmate.databinding.FragmentChooseBinding
import com.example.convertmate.presentation.viewmodel.ChooseViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.delay
import okhttp3.internal.wait
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChooseFragment : Fragment() {

    private var _binding: FragmentChooseBinding? = null
    private val binding get() = _binding!!
    private var selectedItem1: String? = ""
    private var selectedItem2: String? = ""
    private var currencies = ArrayList<String>()
    private val viewModel by viewModel<ChooseViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChooseBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.firstCurrency.text = selectedItem1
        initSpinner()
        onButtonClick()
    }

    private fun initSpinner() {
        getCurrencies()

        val spinner1 = binding.spnFirst
        spinner1.setItems(currencies)
        spinner1.setOnClickListener {
            Utility.hideKeyboard(requireActivity())
        }

        spinner1.setOnItemSelectedListener { _, _, _, item ->
            selectedItem1 = item.toString()
            binding.firstCurrency.text = selectedItem1
        }
        val spinner2 = binding.spnSecond
        spinner2.setItems(currencies)
        spinner2.setOnClickListener {
            Utility.hideKeyboard(requireActivity())
        }
        spinner2.setOnItemSelectedListener { _, _, _, item ->
            selectedItem2 = item.toString()
        }

    }

    private fun getCurrencies() {
        viewModel.getCurrencies(Constants.API_KEY)
        observeCurrenciesData()
    }

    private fun onButtonClick() {
        binding.btnConvert.setOnClickListener {
            val numberToConvert = binding.amount.text.toString()
            if (numberToConvert.isEmpty() || numberToConvert == "0") {
                Snackbar.make(
                    binding.mainLayout,
                    getString(R.string.you_have_typed_the_wrong_amount),
                    Snackbar.LENGTH_LONG
                )
                    .setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                    .show()
            } else if (!Utility.isNetworkAvailable(requireContext())) {
                Snackbar.make(
                    binding.mainLayout,
                    getString(R.string.no_internet_connection),
                    Snackbar.LENGTH_LONG
                )
                    .setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                    .show()
            } else {
                convert()
            }
        }
    }

    private fun convert() {
        Utility.hideKeyboard(requireActivity())
        binding.progressbar.visibility = View.VISIBLE
        binding.btnConvert.visibility = View.GONE
        viewModel.getConvertedData(
            Constants.API_KEY,
            selectedItem1.toString(),
            selectedItem2.toString(),
            binding.amount.text.toString().toDouble()
        )
        observeConvertedData()
    }

    private fun observeCurrenciesData() {
        viewModel.currencyData.observe(viewLifecycleOwner) { result ->
            when (result.status) {
                Resource.Status.SUCCESS -> {
                    if (result.data?.status == "success") {
                        Log.d("ChooseFragment", "${result.data.currencies}")
                        viewModel.currencies.value = result.data.currencies.keys
                        currencies.clear()
                        currencies.addAll(viewModel.currencies.value!!)
                        currencies.sortBy { it }
                        viewModel.clearCurrencies()
                    } else if (result.data?.status == "fail") {
                        val layout = binding.mainLayout
                        Snackbar.make(
                            layout,
                            getString(R.string.something_went_wrong_try_again_later),
                            Snackbar.LENGTH_LONG
                        )
                            .setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                            .show()
                    }
                }

                Resource.Status.ERROR -> {
                    val layout = binding.mainLayout
                    Snackbar.make(
                        layout,
                        getString(R.string.something_went_wrong_try_again_later),
                        Snackbar.LENGTH_LONG
                    )
                        .setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                        .show()
                }

                Resource.Status.LOADING -> {
                }
            }
        }
    }

    private fun observeConvertedData() {
        viewModel.convertedData.observe(viewLifecycleOwner) { result ->
            when (result.status) {
                Resource.Status.SUCCESS -> {
                    if (result.data?.status == "success") {
                        val map: Map<String, Rates>
                        map = result.data.rates
                        Log.d("HUITA", "$map")
                        map.keys.forEach {
                            val rateForAmount = map[it]!!.rate_for_amount
                            Log.d("HUITA dva", "$rateForAmount")
                            viewModel.convertedRate.value = rateForAmount
                        }
                        val formattedString =
                            String.format("%.2f", viewModel.convertedRate.value)
                        binding.progressbar.visibility = View.GONE
                        binding.btnConvert.visibility = View.VISIBLE
                        val bundle = Bundle()
                        bundle.putString("from", selectedItem1.toString())
                        bundle.putString("to", selectedItem2.toString())
                        Log.d("HUITA1", "$selectedItem1 $selectedItem2")
                        Log.d("HUITA", "$formattedString")
                        bundle.putString("result", formattedString)
                        bundle.putString("num", binding.amount.text.toString())
                        findNavController().navigate(R.id.resultFragment, bundle)
                        viewModel.clearConvertedRate()
                    } else if (result.data?.status == "fail") {
                        val layout = binding.mainLayout
                        Snackbar.make(
                            layout,
                            getString(R.string.something_went_wrong_try_again_later),
                            Snackbar.LENGTH_LONG
                        )
                            .setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                            .show()

                        binding.progressbar.visibility = View.GONE
                        binding.btnConvert.visibility = View.VISIBLE
                    }
                }

                Resource.Status.ERROR -> {
                    val layout = binding.mainLayout
                    Snackbar.make(
                        layout,
                        getString(R.string.something_went_wrong_try_again_later),
                        Snackbar.LENGTH_LONG
                    )
                        .setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                        .show()
                    binding.progressbar.visibility = View.GONE
                    binding.btnConvert.visibility = View.VISIBLE
                }

                Resource.Status.LOADING -> {
                    binding.progressbar.visibility = View.VISIBLE
                    binding.btnConvert.visibility = View.GONE
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}