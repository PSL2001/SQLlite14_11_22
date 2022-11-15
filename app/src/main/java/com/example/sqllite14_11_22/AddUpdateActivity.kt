package com.example.sqllite14_11_22

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.sqllite14_11_22.databinding.ActivityAddUpdateBinding
import com.example.sqllite14_11_22.db.BaseDatos
import com.example.sqllite14_11_22.models.Usuarios

class AddUpdateActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddUpdateBinding
    var nombre = ""
    var email = ""
    lateinit var conexion: BaseDatos
    var editar = false
    var id: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        conexion = BaseDatos(this)
        cogerDatos()
        setListeners()

    }

    private fun cogerDatos() {
        val datos = intent.extras
        if (datos != null) {
            editar = true
            binding.btnEnviar.text = "EDITAR"
            val usuario = datos.getSerializable("USUARIO") as Usuarios
            id = usuario.id
            binding.etNombre.setText(usuario.nombre)
            binding.etEmail.setText(usuario.email)
        }
    }

    private fun setListeners() {
        binding.btnVolver.setOnClickListener {
            finish()
        }
        binding.btnEnviar.setOnClickListener {
            crearRegistro()
        }
    }

    private fun crearRegistro() {
        nombre = binding.etNombre.text.toString().trim()
        email = binding.etEmail.text.toString().trim()

        if (nombre.length < 3) {
            binding.etNombre.setError("El campo nombre debe de tener al menos 3 caracteres")
            binding.etNombre.requestFocus()
            return
        }

        if (email.length < 6) {
            binding.etEmail.setError("El campo email debe tener al menos 6 caracteres")
            binding.etEmail.requestFocus()
            return
        }
        //Comprobamos que el email no esta duplicado
        if (conexion.existeEmail(email, id)) {
            binding.etEmail.setError("Este correo ya existe")
            binding.etEmail.requestFocus()
            return
        }
        if (!editar) {
            val usuario = Usuarios(1, nombre, email)
            if (conexion.crear(usuario) > -1) {
                finish()
            } else {
                Toast.makeText(this, "No se ha podido guardar el registro", Toast.LENGTH_SHORT).show()
            }
        } else {
            val usuario = Usuarios(id, nombre, email)
            if (conexion.update(usuario) > -1) {
                finish()
            } else {
                Toast.makeText(this, "No se ha podido actualizar el usuario", Toast.LENGTH_SHORT).show()
            }
        }

    }
}