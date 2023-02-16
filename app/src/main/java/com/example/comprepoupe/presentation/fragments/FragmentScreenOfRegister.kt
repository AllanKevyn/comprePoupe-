package com.example.comprepoupe.presentation.fragments

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import com.example.comprepoupe.databinding.FragmentScreenOfRegisterBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
import kotlin.collections.HashMap

class FragmentScreenOfRegister : Fragment() {

    private lateinit var edit_nome: EditText
    private lateinit var edit_senha: EditText
    private lateinit var edit_email: EditText
    private lateinit var bt_cadastrar: Button

    val messeges = arrayOf("Preencha todos os campos", "Cadastro realizado com sucesso")


    private lateinit var binding: FragmentScreenOfRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentScreenOfRegisterBinding.inflate(inflater, container, false)
        setupOnCreateView()
        return binding.root
    }

    private fun setupOnCreateView() {
        initializationComponents()
    }

    private fun initializationComponents() {
        edit_nome = binding.idEditTextNomeCadastro
        edit_email = binding.idEditTextEmailCadastro
        edit_senha = binding.idEditTextSenhaCadastro
        bt_cadastrar = binding.idButtonTelaCadastro
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        backPage()
    }

    private fun setupView() {
        setupClicks()
    }

    private fun setupClicks() {
        bt_cadastrar.setOnClickListener {
            val nome = edit_nome.text.toString()
            val email = edit_email.text.toString()
            val senha = edit_senha.text.toString()
            if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
                val snackbar =
                    view?.let { it1 -> Snackbar.make(it1, messeges[0], Snackbar.LENGTH_SHORT) }
                if (snackbar != null) {
                    snackbar.setBackgroundTint(Color.WHITE)
                }
                if (snackbar != null) {
                    snackbar.setTextColor(Color.BLACK)
                }
                if (snackbar != null) {
                    snackbar.show()
                }
            } else {
                view?.let {
                    CadastrarUsuario(email, senha)
                }
            }
        }
    }


    private fun CadastrarUsuario(email: String, senha: String) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, senha)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    salvarDadosDoUsuario()

                    val snackbar = Snackbar.make(view!!, messeges[1], Snackbar.LENGTH_SHORT)
                    snackbar.setBackgroundTint(Color.WHITE)
                    snackbar.setTextColor(Color.BLACK)
                    snackbar.show()
                } else {
                    val erro: String
                    try {
                        throw task.exception!!
                    } catch (e: FirebaseAuthWeakPasswordException) {
                        erro = "Digite uma senha de no mínimo 6 caracteres"
                    } catch (e: FirebaseAuthInvalidUserException) {
                        erro = "E-mail inválido"
                    } catch (e: FirebaseAuthInvalidCredentialsException) {
                        erro = "Esta conta ja foi cadastrada"
                    } catch (e: Exception) {
                        erro = "Erro desconhecido: ${e.message}"
                    }

                    val snackbar = Snackbar.make(view!!, erro, Snackbar.LENGTH_SHORT)
                    snackbar.setBackgroundTint(Color.WHITE)
                    snackbar.setTextColor(Color.BLACK)
                    snackbar.show()

                }
            }
    }

    private fun salvarDadosDoUsuario(){
        val nome = edit_nome.text.toString()
        val dataBase = FirebaseFirestore.getInstance()

        val usuarios: MutableMap<String, Any> = HashMap()
        usuarios.put("nome",nome)

    }

    private fun backPage() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().popBackStack()
        }
    }

}