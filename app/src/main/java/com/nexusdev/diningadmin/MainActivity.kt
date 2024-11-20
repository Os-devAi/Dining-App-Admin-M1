package com.nexusdev.diningadmin

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import com.nexusdev.diningadmin.databinding.ActivityMainBinding
import com.nexusdev.diningadmin.views.AddActivity
import com.nexusdev.diningadmin.views.ProductosActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        window.statusBarColor = ContextCompat.getColor(this, R.color.black)
        window.navigationBarColor = ContextCompat.getColor(this, R.color.black)

        clicks()
    }

    private fun clicks() {
        binding.let {
            it.btnInsert.setOnClickListener {
                val i = Intent(this, AddActivity::class.java)
                startActivity(i)
            }
            it.btnManagement.setOnClickListener {
                val i = Intent(this, ProductosActivity::class.java)
                startActivity(i)
            }
        }
    }

}