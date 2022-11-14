package com.example.sqllite14_11_22.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.sqllite14_11_22.databinding.UsuarioLayoutBinding
import com.example.sqllite14_11_22.models.Usuarios

class UsuariosViewHolder(v: View): RecyclerView.ViewHolder(v) {
    private val binding = UsuarioLayoutBinding.bind(v)

    fun render(usuario: Usuarios) {
        binding.tvId.text = usuario.id.toString()
        binding.tvNombre.text = usuario.nombre
        binding.tvEmail.text = usuario.email
    }
}
