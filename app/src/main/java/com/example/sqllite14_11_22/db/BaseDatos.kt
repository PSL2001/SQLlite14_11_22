package com.example.sqllite14_11_22.db

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.sqllite14_11_22.models.Usuarios


class BaseDatos(c: Context): SQLiteOpenHelper(c, DATABASE, null, VERSION) {
    companion object {
        const val VERSION = 1
        const val DATABASE = "users_db"
        const val TABLA = "users_tb"
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        val q = "CREATE TABLE $TABLA(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre TEXT NOT NULL , " +
                "email TEXT NOT NULL UNIQUE)"

        p0?.execSQL(q)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        val q = "DROP TABLE IF EXISTS $TABLA"
        p0?.execSQL(q)
        onCreate(p0)
    }

    //CRUD (Create, Read, Update, Delete)

    //Crear un registro
    fun crear(usuario: Usuarios) : Long {
        //WritableDatabase permite escribir en ella
        val conexion = this.writableDatabase
        val valores = ContentValues().apply {
            put("NOMBRE", usuario.nombre)
            put("EMAIL", usuario.email)
        }

        val cod = conexion.insert(TABLA, null, valores)
        conexion.close()

        //Si devuelve -1 es que hay un error, cualquier otro si ha ido bien
        return cod
    }
    //Ver todos los registros
    @SuppressLint("Range")
    fun readAll(): MutableList<Usuarios> {
        //Creamos una mutable list
        val lista = mutableListOf<Usuarios>()
        val conexion = this.readableDatabase
        //Creamos la consulta
        val q = "SELECT * FROM $TABLA ORDER BY nombre"
        try {
            //Creamos un cursor, con el query anterior
            val cursor = conexion.rawQuery(q, null)
            //Hacemos este if para comprobar que la consulta no ha fallado
            if (cursor.moveToFirst()) {
                do {
                    //Y mientras que el cursor puede ir al siguiente creamos usuarios
                    val usuario = Usuarios(
                        cursor.getInt(cursor.getColumnIndex("id")),
                        cursor.getString(cursor.getColumnIndex("nombre")),
                        cursor.getString(cursor.getColumnIndex("email"))
                    )
                    //Y añadimos la lista
                    lista.add(usuario)
                }while (cursor.moveToNext())
            }
            //Cerramos el cursor
            cursor.close()
        }catch (e: Exception) {
            e.printStackTrace()
        }
        //Cerramos la conexion
        conexion.close()
        //Y devolvemos lista
        return lista
    }

    //Metodo para comprobar que email es unico
    fun existeEmail(email: String, id: Int?): Boolean {
        //Creamos el query, que cambiara dependiendo si el id es nulo o no
        val q = if ( id == null) "SELECT id from $TABLA WHERE email ='$email'" else
            "SELECT id from $TABLA WHERE email ='$email' AND id!=$id"
        // Creamos la conexion
        val conexion = this.readableDatabase
        //Creamos una variable para contar las filas
        var filas = 0
        try {
            //Abrimos el cursor
            val cursor = conexion.rawQuery(q, null)
            //Nos traemos las filas
            filas = cursor.count
            //Cerramos cursor
            cursor.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        //Cerramos conexion
        conexion.close()
        //Devolvemos un boolean dependiendo si filas es distinto de 0
        return filas != 0
    }

    //Metodo para borrar un dato de la base de datos
    fun borrar(id: Int?) {
        //Creamos el query
        val q = "DELETE FROM $TABLA WHERE id = '$id'"
        //Activamos la escritura
        val conexion = this.writableDatabase
        //Ejecutamos sql
        conexion.execSQL(q)
        //Y cerramos la conexion
        conexion.close()
    }

    //Metodo para actualizar un resgistro
    fun update(usuario: Usuarios): Int {
        //Se puede hacer como en el delete, pero habria problemas de seguridad de inyeccion SQL
        //val q = "UPDATE $TABLA SET nombre = '${usuario.nombre}', email = '${usuario.email}' WHERE id = $'{usuario.id}' "
        val conexion = this.writableDatabase
        val valores = ContentValues().apply {
            put("NOMBRE", usuario.nombre)
            put("EMAIL", usuario.email)
        }
        val update = conexion.update(TABLA, valores, "id = ?", arrayOf(usuario.id.toString()))
        conexion.close()
        return update
    }
}