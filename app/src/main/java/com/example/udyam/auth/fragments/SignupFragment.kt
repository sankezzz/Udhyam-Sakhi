package com.example.udyam.auth.fragments

import android.app.ProgressDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.udyam.R
import com.example.udyam.databinding.FragmentSignupBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class SignupFragment : Fragment() {

    private lateinit var binding : FragmentSignupBinding
    val signupAuth: FirebaseAuth = FirebaseAuth.getInstance()
    val firestore = FirebaseFirestore.getInstance()
    private lateinit var SignupPD : ProgressDialog



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater , R.layout.fragment_signup, container ,false, )
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        SignupPD = ProgressDialog(activity)

        binding.signUpTextToSignIn.setOnClickListener {
            findNavController().navigate(R.id.action_signupFragment_to_loginFragment)
        }

        binding.signUpBtn.setOnClickListener {
            val email = binding.signUpEmail.text.toString()
            val name = binding.signUpEtName.text.toString()
            val password = binding.signUpPassword.text.toString()

            when {
                name.isEmpty() -> Toast.makeText(activity, "Name can't be empty", Toast.LENGTH_SHORT).show()
                email.isEmpty() -> Toast.makeText(activity, "Email can't be empty", Toast.LENGTH_SHORT).show()
                password.isEmpty() -> Toast.makeText(activity, "Password can't be empty", Toast.LENGTH_SHORT).show()
                else -> {
                    val action = SignupFragmentDirections.actionSignupFragmentToProfileFragment(
                        userName = name,
                        userEmail = email,
                        userPassword = password
                    )
                    findNavController().navigate(action)
                }
            }
        }


    }


}