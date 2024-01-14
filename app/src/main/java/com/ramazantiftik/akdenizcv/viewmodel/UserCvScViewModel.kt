package com.ramazantiftik.akdenizcv.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class UserCvScViewModel : ViewModel() {

    //firebase
    private var firestore= Firebase.firestore

    var dataList= ArrayList<String>()

    /*
    //firebase initialize
    auth= Firebase.auth
    firestore= Firebase.firestore
     */

    private val currentUser= Firebase.auth.currentUser!!.uid


    fun getData() : ArrayList<String>{

        firestore.collection("Student").document(currentUser).get().addOnSuccessListener {

            val showProfile= it.data?.get("showProfile").toString()
            val openWork=it.data?.get("openWork").toString()
            val email=it.data?.get("email").toString()
            val userName=it.data?.get("userName").toString()
            val userPictureUrl=it.data?.get("pictureUrl").toString()
            val dateOfBirth=it.data?.get("date").toString()
            val phone=it.data?.get("phone").toString()
            val place=it.data?.get("place").toString()
            val linkedin=it.data?.get("linkedin").toString()
            val github=it.data?.get("github").toString()
            val career=it.data?.get("career").toString()
            val university=it.data?.get("university").toString()
            val position=it.data?.get("position").toString()
            val office=it.data?.get("office").toString()
            val programming=it.data?.get("programming").toString()
            val other=it.data?.get("other").toString()
            val social=it.data?.get("social").toString()
            val language=it.data?.get("language").toString()
            val course=it.data?.get("course").toString()
            val professional=it.data?.get("professional").toString()
            val hobby=it.data?.get("hobby").toString()
            val reference=it.data?.get("reference").toString()

            dataList.add(showProfile)
            dataList.add(openWork)
            dataList.add(email)
            dataList.add(userName)
            dataList.add(userPictureUrl)
            dataList.add(dateOfBirth)
            dataList.add(phone)
            dataList.add(place)
            dataList.add(linkedin)
            dataList.add(github)
            dataList.add(career)
            dataList.add(university)
            dataList.add(position)
            dataList.add(office)
            dataList.add(programming)
            dataList.add(other)
            dataList.add(social)
            dataList.add(language)
            dataList.add(course)
            dataList.add(professional)
            dataList.add(hobby)
            dataList.add(reference)



        }

        return dataList
    }

}