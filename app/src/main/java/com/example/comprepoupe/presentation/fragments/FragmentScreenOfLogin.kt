package com.example.comprepoupe.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.comprepoupe.R
import com.example.comprepoupe.databinding.FragmentScreenOfLoginBinding
import com.example.comprepoupe.presentation.fragments.FragmentScreenOfLogin.Companion.REGISTER
import com.google.firebase.ktx.Firebase


class FragmentScreenOfLogin : Fragment() {



    private lateinit var binding: FragmentScreenOfLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentScreenOfLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
    }

    private fun setup(){
        setupClicks()
    }
    private fun setupClicks(){
        binding.idTextCadastro.setOnClickListener {
            BundleCreator()
        }
    }
    private fun BundleCreator(){
        val bundle = Bundle().apply {}
        findNavController().navigate(R.id.action_fragmentScreenOfLogin_to_fragmentScreenOfRegister, bundle)
    }

    companion object {
        const val REGISTER = "register"
    }


}