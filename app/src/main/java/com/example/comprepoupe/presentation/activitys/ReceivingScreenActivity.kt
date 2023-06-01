package com.example.comprepoupe.presentation.activitys


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.example.comprepoupe.R
import com.example.comprepoupe.data.model.UserMeneger
import com.example.comprepoupe.databinding.ActivityReceivingScreenBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class ReceivingScreenActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityReceivingScreenBinding
    private lateinit var userManeger: UserMeneger

    private val dataBase: FirebaseFirestore = FirebaseFirestore.getInstance()
    private lateinit var usuarioID: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReceivingScreenBinding.inflate(layoutInflater)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        userManeger = UserMeneger(this)
        setup()
    }



    private fun setup() {
        retrieveUserData()
        navigationDrawer()
        setupFragment()
        clickItensNavigationDrawer()
    }



    private fun navigationDrawer() {
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)

        appBarConfiguration = AppBarConfiguration(setOf(R.id.homeFragment), drawerLayout)

        setSupportActionBar(binding.appBarMain.toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // Define a visibilidade do menu lateral com base no destino atual
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.homeFragment) {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            } else {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            }
        }
    }

    private fun clickItensNavigationDrawer(){

        val navigationView = binding.navView

        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.homeFragment -> {
                    // Lógica para o item "Home" clicado
                    true
                }
                R.id.nav_perfil -> {
                    // Lógica para o item "Perfil" clicado
                    true
                }
                R.id.nav_logoff -> {
                    FirebaseAuth.getInstance().signOut()
                    clearDataUser()
                    navigationToScreenLogin()
                    true
                }
                else -> false
            }
        }
    }

    private fun retrieveUserData() {
        val navigationView = binding.navView
        val headerView = navigationView.getHeaderView(0)
        val textViewName = headerView.findViewById<TextView>(R.id.idTextNameDrawer)
        val textViewEmail = headerView.findViewById<TextView>(R.id.idTextEmailDrawer)

        val email = FirebaseAuth.getInstance().currentUser?.email
        usuarioID = FirebaseAuth.getInstance().currentUser?.uid.toString()
        val documentReference: DocumentReference = dataBase.collection("Usuarios").document(usuarioID)
        documentReference.addSnapshotListener { snapshot, e ->
            if (snapshot != null) {
                val nomeUsuario = snapshot.getString("nome")
                Log.d("UserData", "Nome: $nomeUsuario") // Verificar se o valor está sendo corretamente obtido
                textViewName.text = nomeUsuario
                textViewEmail.text = email
            }
        }
    }

    private fun clearDataUser() {
        lifecycleScope.launch { userManeger.clearDataUser() }
    }

    private fun navigationToScreenLogin(){
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        navController.navigate(R.id.fragmentScreenOfLogin)
    }

    private fun setupFragment() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.id_Fragment_Container_View) as NavHostFragment
        val navController: NavController = navHostFragment.navController
        NavigationUI.setupActionBarWithNavController(this, navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}