package com.example.sqllite14_11_22.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sqllite14_11_22.R
import com.example.sqllite14_11_22.models.Usuarios

class UsuariosAdapter(
    private val lista: MutableList<Usuarios>,
    //El nuevo campo que es una funcion, pide un int
    private val onItemDelete: (Int)-> Unit,
    //El tercer campo para actualizar usuarios
    private val onItemUpdate: (Usuarios)-> Unit): RecyclerView.Adapter<UsuariosViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsuariosViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.usuario_layout, parent ,false)
        return UsuariosViewHolder(v)
    }

    override fun onBindViewHolder(holder: UsuariosViewHolder, position: Int) {
        //Pasamos los parametros
        holder.render(lista[position], onItemDelete, onItemUpdate)
    }

    override fun getItemCount(): Int {
        return lista.size
    }
}