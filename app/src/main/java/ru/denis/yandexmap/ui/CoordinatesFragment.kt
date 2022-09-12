package ru.denis.yandexmap.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import ru.denis.yandexmap.R
import ru.denis.yandexmap.databinding.CoordinatesFragmentBinding

class CoordinatesFragment : Fragment() {

    private lateinit var binding: CoordinatesFragmentBinding
    private val viewModel: CoordinatesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = CoordinatesFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.showOnTheMapButton.setOnClickListener {
            findNavController().navigate(CoordinatesFragmentDirections.actionCoordinatesToMap())
        }

        coordinatorObserver()
    }

    private fun coordinatorObserver() {
        viewModel.coordinates.observe(viewLifecycleOwner) {
            binding.coordinatesText.text = getString(
                R.string.addres_s_s,
                it.first.toString(),
                it.second.toString()
            )
        }
    }
}