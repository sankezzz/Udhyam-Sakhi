package com.example.udyam

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.udyam.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.firestore.FirebaseFirestore


class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()
    private lateinit var progressDialogLogin: ProgressDialog




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater , R.layout.fragment_login , container , false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        progressDialogLogin = ProgressDialog(activity)

        binding.signInTextToSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signupFragment)
        }

        binding.forgotPass.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_forgotFragment)
        }



        binding.loginButton.setOnClickListener {
            val email = binding.loginetemail.text.toString()
            val password = binding.loginetpassword.text.toString()

            if (email.isEmpty()) {
                Toast.makeText(activity, "Email cant be Empty", Toast.LENGTH_SHORT).show()
            }
            if (password.isEmpty()) {
                Toast.makeText(activity, "Password cant be Empty", Toast.LENGTH_SHORT).show()
            }

            if (email.isNotEmpty() && password.isNotEmpty()) {
                getTheUserSignedIn(email, password)
            }
        }
    }


            private fun getTheUserSignedIn(email:String?, password :String?) {

                progressDialogLogin.show()
                progressDialogLogin.setMessage("Signing in")

                if (email != null) {
                    if (password != null) {
                        auth.signInWithEmailAndPassword(email , password).addOnCompleteListener(){task->

                            if(task.isSuccessful){


                                progressDialogLogin.dismiss()
                                val isVerified = auth.currentUser?.isEmailVerified
                                if(isVerified  == true) {
                                    val i = Intent(activity, MainActivity::class.java)
                                    startActivity(i)

                                    activity?.finish()
                                }
                                else{
                                    Toast.makeText(activity , "Please verify your email"  ,Toast.LENGTH_SHORT).show()
                                }

                            }
                            else{
                                progressDialogLogin.dismiss()
                                Toast.makeText(activity , "Invalid Credentials" , Toast.LENGTH_SHORT).show()
                            }
                        }.addOnFailureListener{exception->
                            if(exception is FirebaseAuthInvalidCredentialsException){
                                Toast.makeText(activity , "Invalid Credentials" , Toast.LENGTH_SHORT).show()
                            }
                            else{
                                Toast.makeText(activity , "Authorisation Failed" , Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }

    }


}