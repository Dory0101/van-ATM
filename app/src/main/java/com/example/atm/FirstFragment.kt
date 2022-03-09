package com.example.atm

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.example.atm.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView( //剛產生fragment
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) { //fragment產生後
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            //login code
            if (binding.edUsername.text.toString() == "jack" &&
                binding.edPassword.text.toString() == "1234") {
                    findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
            } else {
                //error
                AlertDialog.Builder(requireContext())
                    .setTitle("error")
                    .setMessage("username or password is wrong!")
                    .setNeutralButton("ok", null)
                    .show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}