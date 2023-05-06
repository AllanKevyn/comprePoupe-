package com.example.comprepoupe.presentation.fragments


import android.os.Bundle
import android.os.UserManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.activity.addCallback
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.comprepoupe.R
import com.example.comprepoupe.databinding.FragmentScreenOfUserBinding
import com.example.comprepoupe.model.UserMenager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class FragmentScreenOfUser : Fragment() {

    private lateinit var binding: FragmentScreenOfUserBinding

    private lateinit var nomeUsuario: TextView
    private lateinit var emailUsuario: TextView
    private lateinit var bt_deslogar: TextView
    private lateinit var userManager: UserMenager

    private val dataBase: FirebaseFirestore = FirebaseFirestore.getInstance()
    private lateinit var usuarioID: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentScreenOfUserBinding.inflate(inflater, container, false)
        initializeComponents()
        return binding.root
    }


    private fun initializeComponents() {
        nomeUsuario = binding.idTextViewUser
        emailUsuario = binding.idTextViewEmail
        bt_deslogar = binding.idButtonLogout
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userManager = UserMenager(requireContext())
        setupClicks()
        backPage()
    }

    private fun setupClicks() {
        bt_deslogar.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            clearDataUser()
            navigationToScreenLogin()
        }
    }

    private fun clearDataUser() {
        lifecycleScope.launch { userManager.clearDataUser() }
    }

    private fun navigationToScreenLogin() {
        val bundle = Bundle().apply { }
        findNavController().navigate(
            R.id.action_fragmentScreenOfUser_to_fragmentScreenOfLogin,
            bundle
        )
    }

    private fun backPage() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            null
        }
    }

    override fun onStart() {
        super.onStart()
        retrieveUserData()
    }

    private fun retrieveUserData() {
        val email = FirebaseAuth.getInstance().currentUser?.email
        usuarioID = FirebaseAuth.getInstance().currentUser?.uid.toString()
        val documentReference: DocumentReference =
            dataBase.collection("Usuarios").document(usuarioID)
        documentReference.addSnapshotListener { snapshot, e ->
            if (snapshot != null) {
                snapshot.getString("nome").also { nomeUsuario.text = it }
                emailUsuario.text = email
            }
        }
    }
}