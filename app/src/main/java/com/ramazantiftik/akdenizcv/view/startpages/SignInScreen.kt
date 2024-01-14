package com.ramazantiftik.akdenizcv.view.startpages

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ramazantiftik.akdenizcv.databinding.FragmentSignInScreenBinding
import com.ramazantiftik.akdenizcv.model.Student
import com.ramazantiftik.akdenizcv.model.UserData
import java.util.*

class SignInScreen : Fragment() {

    //viewBinding
    private var _binding: FragmentSignInScreenBinding?=null
    private val binding get() = _binding!!

    //firebase
    private lateinit var auth: FirebaseAuth
    private lateinit var firebaseDb: FirebaseDatabase
    private lateinit var databaseRef: DatabaseReference
    private lateinit var firestore: FirebaseFirestore

    private lateinit var email: String
    private lateinit var password: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //viewBinding
        _binding= FragmentSignInScreenBinding.inflate(inflater,container,false)
        val view=binding.root


        //firebase initialize
        auth= Firebase.auth
        email=""
        password=""
        firebaseDb=Firebase.database
        firestore=Firebase.firestore
        databaseRef= firebaseDb.getReference("users")


        //*****insert auth fun*****
        //insertAuth()






        //******CLICKS******

        binding.userActiveTxtSignInSc.setOnClickListener {
            val action=SignInScreenDirections.actionSignInScreenToUserActiveScreen()
            Navigation.findNavController(it).navigate(action)
        }

        //signIn Func
        binding.signInBtnSignInSc.setOnClickListener {

            email=binding.userEmailEditTxtSignInSc.text.toString()
            password=binding.userPasswordEditTxtSignInSc.text.toString()

            if (!email.equals("") && !password.equals("")){
                //email or password not empty

                auth.signInWithEmailAndPassword(email,password)
                    .addOnSuccessListener {
                        //successfully signIn

                        val action= SignInScreenDirections.actionSignInScreenToFeedScreen()
                        Navigation.findNavController(requireView()).navigate(action)

                    }.addOnFailureListener {
                        //failed signIn

                        Toast.makeText(context,it.localizedMessage,Toast.LENGTH_LONG).show()
                        val action= SignInScreenDirections.actionSignInScreenToFeedScreen()
                        Navigation.findNavController(requireView()).navigate(action)
                    }

            } else {
                //email or password empty

                Toast.makeText(context,"Email Ve Şifreyi Girin ",Toast.LENGTH_LONG).show()
            }

        }

        // Inflate the layout for this fragment
        return view
    }



    private fun insertAuth(){

        //email=binding.userEmailEditTxtSignInSc.text.toString()
        val userEmail = "ramazantiftik@gmail.com"
        val passwordUUID = UUID.randomUUID()
        //UUID.randomUUID()
        //println("password = $passwordUUID")
        val userType= "Student"

        auth.createUserWithEmailAndPassword(userEmail,passwordUUID.toString())
            .addOnSuccessListener {
                //success
                println("Kullanıcı oluştu")
                val uid=it.user?.uid ?: ""

                databaseRef.child(uid).setValue(UserData(userEmail,userType))
                    .addOnSuccessListener {
                        println("Kullanıcı başarılı bir şekilde veritabanına eklendi")

                    }.addOnFailureListener {
                        println("Veritabanına kullanıcı ekleme hatası")
                    }
                    //.add(Student(email = userEmail))
                firestore.collection(userType).document(uid).set(Student(email=userEmail))
                    .addOnSuccessListener {
                        println("Kullanıcı firestore'a başarılı bir şekilde eklendi")


                    }.addOnFailureListener {
                        println("Firestore'a kullanıcı eklenirken hata oluştu")
                    }

            }.addOnFailureListener {
                //failed
                println("Kullanıcı oluşurken hata oluştu")
                println(it.localizedMessage)

            }

    }


}