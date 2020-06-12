package com.herlavroc.proyectofinal.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.FirebaseFirestore
import com.herlavroc.proyectofinal.R
import com.herlavroc.proyectofinal.encrypt.Encriptador
import com.herlavroc.proyectofinal.session.Session
import kotlinx.android.synthetic.main.fragment_inicio_login.*

/**
 * A simple [Fragment] subclass.
 * Use the [login.newInstance] factory method to
 * create an instance of this fragment.
 */
class login : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnLogin_Entrar.setOnClickListener{
            btnLogin_Entrar.isEnabled = false
            btnLogin_Registrar.isEnabled = false
            var encontrado = false
            val firebaseFirestore = FirebaseFirestore.getInstance()
            firebaseFirestore.collection("usuarios").get()
                .addOnSuccessListener { documents ->
                    for (document in documents)
                    {
                        if(txtLogin_Correo.text.toString() == document.getString("correo"))
                        {
                            encontrado = true
                            if(txtLogin_Contra.text.toString() == document.getString("contrasenia"))
                            {
                                Session.usuario.correo = document.getString("correo")!!
                                Session.usuario.contrasenia = document.getString("contrasenia")!!
                                Session.usuario.nombre = document.getString("nombre")!!
                                Session.usuario.apellido = document.getString("apellido")!!
                                Session.usuario.telefono = document.getString("telefono")!!
                                Session.rutina.correousuario = Session.usuario.correo
                                Session.actividad.correousuario = Session.usuario.correo
                                Toast.makeText(activity, "¡Hola ${Session.usuario.nombre.trim()}!" , Toast.LENGTH_LONG).show()
                                btnLogin_Entrar.isEnabled = true
                                btnLogin_Registrar.isEnabled = true
                                findNavController().navigate(R.id.action_login_to_usuario_rutinas)
                            }
                            else
                            {
                                btnLogin_Entrar.isEnabled = true
                                btnLogin_Registrar.isEnabled = true
                                Toast.makeText(activity, "Contraseña incorrecta" , Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                    if(!encontrado)
                    {
                        btnLogin_Entrar.isEnabled = true
                        btnLogin_Registrar.isEnabled = true
                        Toast.makeText(activity, "Usuario inexistente" , Toast.LENGTH_LONG).show()
                    }
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(activity, exception.toString(), Toast.LENGTH_LONG).show()
                    btnLogin_Entrar.isEnabled = true
                    btnLogin_Registrar.isEnabled = true
                }
            btnLogin_Entrar.isEnabled = true
            btnLogin_Registrar.isEnabled = true
        }
        btnLogin_Registrar.setOnClickListener{

            findNavController().navigate(R.id.action_login_to_registro)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_inicio_login, container, false)
    }


}
