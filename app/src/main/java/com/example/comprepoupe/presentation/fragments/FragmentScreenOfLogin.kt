package com.example.comprepoupe.presentation.fragments


import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ProgressBar
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.comprepoupe.R
import com.example.comprepoupe.databinding.FragmentScreenOfLoginBinding
import com.example.comprepoupe.model.UserMeneger
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import androidx.appcompat.app.AppCompatActivity





class FragmentScreenOfLogin : Fragment() {

    private lateinit var binding: FragmentScreenOfLoginBinding

    private lateinit var edit_email: EditText
    private lateinit var edit_senha: EditText
    private lateinit var button_entrar: Button
    private lateinit var progress_bar: ProgressBar
    private lateinit var checkBox: CheckBox
    private lateinit var userMeneger: UserMeneger


    val messeges = arrayOf("Preencha todos os campos")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userMeneger = UserMeneger(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentScreenOfLoginBinding.inflate(inflater, container, false)
        startComponents()
        readDataUser()
        return binding.root
    }

    private fun keepConected() {
        lifecycleScope.launch {
            val user = userMeneger.readDataUser()
            if (user.checkBox) {

                val bundle = Bundle().apply { }
                findNavController().navigate(
                    R.id.action_fragmentScreenOfLogin_to_homeFragment,
                    bundle
                )
            }
        }
    }


    private fun startComponents() {
        edit_email = binding.idEditEmail
        edit_senha = binding.idEditSenha
        button_entrar = binding.idButtonEntrar
        progress_bar = binding.idProgressBar
        checkBox = binding.idCheckBox
    }

    private fun saveDataUser() {
        val email = binding.idEditEmail.text.toString()
        val senha = binding.idEditSenha.text.toString()
        val checkBox = binding.idCheckBox.isChecked

        lifecycleScope.launch {
            userMeneger.saveDataUser(email, senha, checkBox)
        }
    }

    private fun readDataUser() {
        lifecycleScope.launch {
            val user = userMeneger.readDataUser()
            binding.idEditEmail.setText(user.email)
            binding.idEditSenha.setText(user.senha)
            binding.idCheckBox.isChecked = user.checkBox
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
        keepConected()
    }

    private fun observerCheckBox() {

        if (binding.idCheckBox.isChecked) {
            lifecycleScope.launch {
                saveDataUser()
            }
        }
    }


    private fun setup() {
        setupClicks()
    }


    private fun setupClicks() {
        binding.idTextCadastro.setOnClickListener {
            NavigationLoginToRegister()
        }
        binding.idButtonEntrar.setOnClickListener {
            setupButtonEntrar()
        }
    }


    private fun setupButtonEntrar() {
        val email = edit_email.text
        val senha = edit_senha.text

        if (email.isEmpty() || senha.isEmpty()) {
            val snackbar = Snackbar.make(view!!, messeges[0], Snackbar.LENGTH_SHORT)
            snackbar.setBackgroundTint(Color.WHITE)
            snackbar.setTextColor(Color.BLACK)
            snackbar.show()
        } else {
            authenticateUser(edit_email.text.toString(), edit_senha.text.toString())
            observerCheckBox()
        }
    }


    private fun authenticateUser(email: String, senha: String) {

        val delayInMillis = 3000L

        FirebaseAuth.getInstance().signInWithEmailAndPassword(
            email.toString(),
            senha.toString()
        ).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                progress_bar.visibility = View.VISIBLE

                val handler = Handler(Looper.getMainLooper())
                handler.postDelayed({

                    val bundle = Bundle().apply { }
                    findNavController().navigate(
                        R.id.action_fragmentScreenOfLogin_to_homeFragment,
                        bundle
                    )

                }, delayInMillis)

            } else {
                val erro: String

                try {
                    throw task.exception!!
                } catch (e: Exception) {
                    erro = "Usuário ou senha inválidos"
                }
                val snackbar = Snackbar.make(view!!, erro, Snackbar.LENGTH_SHORT)
                snackbar.setBackgroundTint(Color.WHITE)
                snackbar.setTextColor(Color.BLACK)
                snackbar.show()
            }


        }

    }


    private fun NavigationLoginToRegister() {
        val bundle = Bundle().apply {}
        findNavController().navigate(
            R.id.action_fragmentScreenOfLogin_to_fragmentScreenOfRegister,
            bundle
        )
    }


    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
    }
}

