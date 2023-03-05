package com.example.comprepoupe.presentation.fragments

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import com.example.comprepoupe.databinding.FragmentScreenOfRegisterBinding
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.collections.HashMap
import com.google.firebase.auth.FirebaseAuthWeakPasswordException as FirebaseAuthWeakPasswordException1
import kotlin.Exception as Exception1

class FragmentScreenOfRegister : Fragment() {

    private lateinit var edit_nome: EditText
    private lateinit var edit_senha: EditText
    private lateinit var edit_confirmarSenha: EditText
    private lateinit var edit_email: EditText
    private lateinit var bt_cadastrar: Button

    lateinit var usuarioID: String

    val messeges = arrayOf(
        "Preencha todos os campos",
        "Cadastro realizado com sucesso",
        "As senhas não correspondem, digite novamente"
    )


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
        startComponents()
    }

    private fun startComponents() {
        edit_nome = binding.idEditTextNomeCadastro
        edit_email = binding.idEditTextEmailCadastro
        edit_senha = binding.idEditTextSenhaCadastro
        bt_cadastrar = binding.idButtonTelaCadastro
        edit_confirmarSenha = binding.idEditTextConfirmarSenha
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
                    confirmPassword()

                }
            }

        }
    }

    private fun confirmPassword() {
        val email = edit_email.text.toString()
        val senha = edit_senha.text.toString()
        val password = edit_senha.text.toString()
        val confirmPassword = edit_confirmarSenha.text.toString()

        if (confirmPassword != password) {

            val snackbar = Snackbar.make(view!!, messeges[2], Snackbar.LENGTH_SHORT)
            snackbar.setBackgroundTint(Color.WHITE)
            snackbar.setTextColor(Color.BLACK)
            snackbar.show()
            return
        }else{
            registerUser(email, senha)
        }
    }

    private fun registerUser(email: String, senha: String) {

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, senha)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    saveUserData()

                    val snackbar = Snackbar.make(view!!, messeges[1], Snackbar.LENGTH_SHORT)
                    snackbar.setBackgroundTint(Color.WHITE)
                    snackbar.setTextColor(Color.BLACK)
                    snackbar.show()
                } else {
                    val erro: String
                    try {
                        throw task.exception!!
                    } catch (e: FirebaseAuthWeakPasswordException1) {
                        erro = "Digite uma senha de no mínimo 6 caracteres"
                    } catch (e: FirebaseAuthInvalidUserException) {
                        erro = "E-mail inválido"
                    } catch (e: FirebaseAuthInvalidCredentialsException) {
                        erro = "Esta conta ja foi cadastrada"
                    } catch (e: Exception1) {
                        erro = "Erro desconhecido: ${e.message}"
                    }

                    val snackbar = Snackbar.make(view!!, erro, Snackbar.LENGTH_SHORT)
                    snackbar.setBackgroundTint(Color.WHITE)
                    snackbar.setTextColor(Color.BLACK)
                    snackbar.show()

                }
            }
    }





    private fun saveUserData() {
        val nome = edit_nome.text.toString()
        val dataBase = FirebaseFirestore.getInstance()

        val usuarios: MutableMap<String, Any> = HashMap()
        usuarios.put("nome", nome)

        usuarioID = FirebaseAuth.getInstance().currentUser?.uid.toString()
        val documentReference: DocumentReference =
            dataBase.collection("Usuarios").document(usuarioID)
        documentReference.set(usuarios)

            .addOnSuccessListener(OnSuccessListener {
                Log.d("dataBase", "Sucesso ao salvar os dados")
            })
            .addOnFailureListener(OnFailureListener { e ->
                Log.d("dataBase_erro", "Erro ao salvar os dados", e)
            })
    }


    private fun backPage() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().popBackStack()
        }
    }


}