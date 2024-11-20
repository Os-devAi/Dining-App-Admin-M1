package com.nexusdev.diningadmin.views

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.nexusdev.diningadmin.R
import com.nexusdev.diningadmin.adapter.ProductAdapter
import com.nexusdev.diningadmin.databinding.ActivityProductosBinding
import com.nexusdev.diningadmin.entities.Constants
import com.nexusdev.diningadmin.model.Producto

class ProductosActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductosBinding

    private lateinit var adapter: ProductAdapter
    private lateinit var firestoreListener: ListenerRegistration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductosBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        window.statusBarColor = ContextCompat.getColor(this, R.color.black)
        window.navigationBarColor = ContextCompat.getColor(this, R.color.black)

        getData()
    }

    override fun onResume() {
        super.onResume()
        getData()
    }

    private fun getData() {
        val db = FirebaseFirestore.getInstance()

        val prodRef = db.collection(Constants.COLL_MENU)
        firestoreListener = prodRef.addSnapshotListener { value, error ->
            if (error != null) {
                Toast.makeText(this, "Error al consultar datos", Toast.LENGTH_SHORT).show()
                return@addSnapshotListener
            }

            val productosList = mutableListOf<Producto>()

            for (value in value!!.documentChanges) {
                val product = value.document.toObject(Producto::class.java)
                product.id = value.document.id

                when (value.type) {
                    DocumentChange.Type.ADDED -> productosList.add(product)
                    else -> {}
                }
            }

            configRecyclerView(productosList)
        }
    }

    private fun configRecyclerView(producList: List<Producto>) {
        adapter = ProductAdapter(producList.toMutableList())
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(
                this@ProductosActivity, 2, GridLayoutManager.VERTICAL, false
            )
            adapter = this@ProductosActivity.adapter
        }
        adapter.onItemClick = {
            val i = Intent(this@ProductosActivity, AddActivity::class.java)
            i.putExtra("producto", it)
            startActivity(i)
        }
    }
}