package com.example.comprepoupe.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import com.example.comprepoupe.R
import com.example.comprepoupe.databinding.FragmentScreenOfRegisterBinding

class FragmentScreenOfRegister : Fragment() {

    private lateinit var binding: FragmentScreenOfRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentScreenOfRegisterBinding.inflate(inflater, container, false)
        backPage()
        return binding.root

    }

    private fun backPage(){
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().popBackStack()
        }
    }

}