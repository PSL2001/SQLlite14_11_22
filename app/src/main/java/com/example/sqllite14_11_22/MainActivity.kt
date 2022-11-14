package com.example.sqllite14_11_22

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sqllite14_11_22.adapter.UsuariosAdapter
import com.example.sqllite14_11_22.databinding.ActivityMainBinding
import com.example.sqllite14_11_22.db.BaseDatos
import com.example.sqllite14_11_22.models.Usuarios

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var conexion: BaseDatos
    lateinit var adapter: UsuariosAdapter
    var lista = mutableListOf<Usuarios>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        conexion = BaseDatos(this)
        setRecycler()
        setListeners()
    }

    private fun setListeners() {
        binding.fabAdd.setOnClickListener {
            startActivity(Intent(this, AddUpdateActivity::class.java))
        }
    }

    private fun setRecycler() {
        lista = conexion.readAll()
        if (lista.size == 0) {
            binding.tvNoReg.visibility = View.VISIBLE
            return
        }
        val layoutmanager = LinearLayoutManager(this)
        binding.rcUsuarios.layoutManager = layoutmanager
        adapter = UsuariosAdapter(lista)
        binding.rcUsuarios.adapter = adapter
    }
}