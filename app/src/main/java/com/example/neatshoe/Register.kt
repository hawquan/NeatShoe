package com.example.neatshoe

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.neatshoe.databinding.FragmentRegisterBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.database.FirebaseDatabase

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Register.newInstance] factory method to
 * create an instance of this fragment.
 */
class Register : Fragment() {
    // TODO: Rename and change types of parameters
    class User(val name: String, val email: String, val address: String, val phone: String, val point: Int, val image: String)

    lateinit var editName: EditText
    lateinit var editEmail: EditText
    lateinit var editPassword: EditText
    lateinit var btnRegister: Button
    private lateinit var mAuth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editName = requireActivity().findViewById(R.id.txtName)
        editEmail = requireActivity().findViewById(R.id.txtEmail)
        editPassword = requireActivity().findViewById(R.id.txtPassword)
        btnRegister = requireActivity().findViewById(R.id.btLogin)




        btnRegister.setOnClickListener {
            register()
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mAuth = FirebaseAuth.getInstance();
        val binding = DataBindingUtil.inflate<FragmentRegisterBinding>(inflater,
            R.layout.fragment_register,container,false)
        binding.btnLogRegister1.setOnClickListener { view : View ->
            view.findNavController().navigate(R.id.action_register_to_login)
        }

        return binding.root
        //return inflater.inflate(R.layout.fragment_register, container, false)


    }
    override fun onStart() {
        super.onStart()

        if(mAuth.currentUser != null){
            //handle the already login user7

        }
    }
    private fun register(){
        val name = editName.text.toString().trim()
        val email =editEmail.text.toString().trim()
        val password = editPassword.text.toString().trim()
        val phone = ""
        val point = "0"
        val image = ""

        if(name.isEmpty() && email.isEmpty() && password.isEmpty() ) {
            editName.error = "Please enter a name"
            editEmail.error = "Please enter an email"
            editPassword.error = "Please enter a password"
            return
        }
        if(name.isEmpty()){
            editName.error = "Please enter a name"
            return
        }

        if(email.isEmpty()){
            editEmail.error = "Please enter an email"
            return
        }
        if(password.isEmpty()){
            editPassword.error = "Please enter a password"
            return
        }
        mAuth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(object: OnCompleteListener<AuthResult> {
                override fun onComplete(task: Task<AuthResult>) {

                    if(task.isSuccessful) {
                        //store additional fields in firebase database

                        var user : User = User(name,email,password,phone,point.toInt(),image)
                        FirebaseDatabase.getInstance().getReference("Users")
                            .child(FirebaseAuth.getInstance().currentUser!!.uid)
                            .setValue(user).addOnCompleteListener(object: OnCompleteListener<Void> {
                                override fun onComplete(task: Task<Void>){
                                    if(task.isSuccessful){
                                        Toast.makeText(activity,"Register Successfully", Toast.LENGTH_LONG).show()

                                        view!!.findNavController().navigate(R.id.action_register_to_login)
                                    }
                                    else{
                                        //display a failure message
                                        if(task.exception is FirebaseAuthUserCollisionException){
                                            Toast.makeText(activity, "You are already registered", Toast.LENGTH_SHORT).show()
                                        }else{
                                            Toast.makeText(activity, task.exception!!.message, Toast.LENGTH_SHORT).show()
                                        }


                                    }

                                }

                            })
                    }else{
                        Toast.makeText(activity, task.exception!!.message, Toast.LENGTH_LONG).show()
                    }
                }
            })
    }


}