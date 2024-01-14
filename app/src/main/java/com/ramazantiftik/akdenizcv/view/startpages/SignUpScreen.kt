package com.ramazantiftik.akdenizcv.view.startpages

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ramazantiftik.akdenizcv.databinding.FragmentSignUpScreenBinding


class SignUpScreen : Fragment() {

    //viewBinding
    private var _binding: FragmentSignUpScreenBinding?=null
    private val binding get() = _binding!!

    //firebase
    private lateinit var auth: FirebaseAuth
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var confirmPassword: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //viewBinding
        _binding= FragmentSignUpScreenBinding.inflate(inflater,container,false)
        val view=binding.root

        //initialize
        auth= Firebase.auth
        email=""
        password=""
        confirmPassword=""



        //*****CLICKS*****

        binding.signUpBtnSignUpSc.setOnClickListener {

            email=binding.userEmailEditTxtSignUpSc.text.toString()
            password=binding.userPasswordEditTxtSignUpSc.text.toString()
            confirmPassword=binding.userPasswordEditTxtSignUpSc2.text.toString()

            if (!email.equals("") && !password.equals("") && !confirmPassword.equals("")){

                if (password.equals(confirmPassword)){
                    //passwords matching and not null

                    auth.createUserWithEmailAndPassword(email,password)
                        .addOnSuccessListener {
                            //success
                            Toast.makeText(requireContext(),"Kayıt Başarılı Bir Şekilde Oluşturuldu", Toast.LENGTH_LONG).show()
//                            SignUpScreenDirections.actionSignUpScreenToFeedScreen()

                        }.addOnFailureListener {
                            //failed
                            Toast.makeText(requireContext(),it.localizedMessage, Toast.LENGTH_LONG).show()

                        }

                } else {
                    //passwords not matching
                    Toast.makeText(requireContext(),"Girilen Parola Eşleşmiyor", Toast.LENGTH_LONG).show()

                }

            } else {
                //email || password null
                Toast.makeText(requireContext(),"Email ve Şifreyi Girin", Toast.LENGTH_LONG).show()
            }

        }




        // Inflate the layout for this fragment
        return view
    }


}