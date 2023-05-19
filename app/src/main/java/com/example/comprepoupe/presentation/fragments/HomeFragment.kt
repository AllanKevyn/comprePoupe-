package com.example.comprepoupe.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.comprepoupe.R
import com.example.comprepoupe.databinding.FragmentHomeBinding
import com.example.comprepoupe.data.model.AdapterProduct
import com.example.comprepoupe.data.model.Product

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapterProduct: AdapterProduct
    private lateinit var layoutManager: GridLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        setupProduct()
        return binding.root
    }

    private fun setupProduct() {
        val listProduct: MutableList<Product> = mutableListOf()
        adapterProduct = AdapterProduct(requireContext(), listProduct)
        layoutManager = GridLayoutManager(binding.idRecyclerViewProdutos.context, 2)
        binding.idRecyclerViewProdutos.layoutManager = layoutManager
        binding.idRecyclerViewProdutos.setHasFixedSize(true)
        binding.idRecyclerViewProdutos.adapter = adapterProduct



        val product1 = Product(
            R.drawable.notebook,
            "This product is very good for you, so you have buy",
            "de: R\$ 1,999.90",
            "por: R\$ 999,90"
        )
        listProduct.add(product1)

        val product2 = Product(
            R.drawable.notebook,
            "This product is very good for you, so you have buy",
            "de: R\$ 1,999.90",
            "por: R\$ 999,90"
        )
        listProduct.add(product2)

                val product3 = Product(
                    R.drawable.notebook,
                    "This product is very good for you, so you have buy",
                    "de: R\$ 1,999.90",
                    "por: R\$ 999,90"
                )
                listProduct.add(product3)

                val product4 = Product(
                    R.drawable.notebook,
                    "This product is very good for you, so you have buy",
                    "de: R\$ 1,999.90",
                    "por: R\$ 999,90"
                )
                listProduct.add(product4)

                val product5 = Product(
                    R.drawable.notebook,
                    "This product is very good for you, so you have buy",
                    "de: R\$ 1,999.90",
                    "por: R\$ 999,90"
                )
                listProduct.add(product5)

                val product6 = Product(
                    R.drawable.notebook,
                    "This product is very good for you, so you have buy",
                    "de: R\$ 1,999.90",
                    "por: R\$ 999,90"
                )
                listProduct.add(product6)

                val product7 = Product(
                    R.drawable.notebook,
                    "This product is very good for you, so you have buy",
                    "de: R\$ 1,999.90",
                    "por: R\$ 999,90"
                )
                listProduct.add(product7)

                val product8 = Product(
                    R.drawable.notebook,
                    "This product is very good for you, so you have buy",
                    "de: R\$ 1,999.90",
                    "por: R\$ 999,90"
                )
                listProduct.add(product8)

                val product9 = Product(
                    R.drawable.notebook,
                    "This product is very good for you, so you have buy",
                    "de: R\$ 1,999.90",
                    "por: R\$ 999,90"
                )
                listProduct.add(product9)

                val product10 = Product(
                    R.drawable.notebook,
                    "This product is very good for you, so you have buy",
                    "de: R\$ 1,999.90",
                    "por: R\$ 999,90"
                )
                listProduct.add(product10)

                val product11 = Product(
                    R.drawable.notebook,
                    "This product is very good for you, so you have buy",
                    "de: R\$ 1,999.90",
                    "por: R\$ 999,90"
                )
                listProduct.add(product11)
    }

}