package com.example.comprepoupe.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.comprepoupe.R
import com.example.comprepoupe.databinding.FragmentSlideShowBinding

class FragmentSlideShow : Fragment() {

    private lateinit var binding: FragmentSlideShowBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSlideShowBinding.inflate(inflater, container, false)
        return binding.root
    }


}