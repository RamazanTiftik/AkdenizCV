package com.ramazantiftik.akdenizcv.view.mainpages

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ramazantiftik.akdenizcv.adapter.FeedScRecyclerAdapter
import com.ramazantiftik.akdenizcv.databinding.FragmentFeedScreenBinding
import com.ramazantiftik.akdenizcv.model.Post
import com.squareup.picasso.Picasso


class FeedScreen : Fragment() {

    //viewBinding
    private var _binding: FragmentFeedScreenBinding?=null
    private val binding get() = _binding!!

    //firebase
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    //list of posts
    private lateinit var postArrayList: ArrayList<Post>

    //adapter
    private lateinit var feedScAdapter: FeedScRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //viewBinding
        _binding= FragmentFeedScreenBinding.inflate(inflater,container,false)
        val view=binding.root

        //firebase initialize
        auth= Firebase.auth
        firestore= Firebase.firestore
        val currentUser=Firebase.auth.currentUser


        //arrayList initialize
        postArrayList=ArrayList<Post>()

        //adapter
        feedScAdapter= FeedScRecyclerAdapter(postArrayList)
        binding.recyclerViewFeedSc.adapter=feedScAdapter
        binding.recyclerViewFeedSc.layoutManager=LinearLayoutManager(requireContext())

        //get posts from firebase for feed screen
        getPostData()

        //get userPhoto from firebase
        currentUser?.let {

            it?.let {
                firestore.collection("Student").document(it.uid).get().addOnSuccessListener {

                    //currentUser Photo
                    val userPhoto=it.get("pictureUrl")
                    if (userPhoto != null){
                        Picasso.get().load(userPhoto.toString()).into(binding.photoImageViewFeedSc)
                    }

                }
            }
        }

        //*****CLICKS*****

        //feedSc to userProfileSc
        binding.photoImageViewFeedSc.setOnClickListener {
            val action= FeedScreenDirections.actionFeedScreenToUserProfileScreen()
            Navigation.findNavController(it).navigate(action)
        }

        //feedSc to newAddPostSc
        binding.addPostFeedSc.setOnClickListener {
            val action= FeedScreenDirections.actionFeedScreenToNewPostScreen()
            Navigation.findNavController(it).navigate(action)
        }


        //*****Nav Buttons*****

        //go to userProfile
        binding.profileBtnFeedSc.setOnClickListener {
            val action= FeedScreenDirections.actionFeedScreenToUserProfileScreen()
            Navigation.findNavController(it).navigate(action)
        }

        //go to userCvScreen
        binding.cvBtnFeedSc.setOnClickListener {
            val action= com.ramazantiftik.akdenizcv.view.mainpages.FeedScreenDirections.actionFeedScreenToUserCvScreen()
            Navigation.findNavController(it).navigate(action)
        }


        // Inflate the layout for this fragment
        return view
    }

    private fun getPostData(){

        firestore.collection("Posts").orderBy("date",Query.Direction.DESCENDING).addSnapshotListener{ value, error ->

            if (error!=null){
                //error
                Toast.makeText(requireContext(),error.localizedMessage,Toast.LENGTH_LONG).show()

            } else {
                if (value!=null){

                    if (!value.isEmpty){
                        //value is not empty or not null

                        postArrayList.clear()

                        //posts of firebase post list
                        val documents=value.documents
                        documents.forEach{

                            val title=it.get("title")
                            val userName=it.get("userName")
                            val userUID=it.get("userUID")
                            val pictureUrl=it.get("pictureUrl")
                            val email=it.get("email")
                            val phone=it.get("phone")
                            val date=it.get("date")
                            val postPicUrl=it.get("postPicUrl")

                            val post=Post(pictureUrl,postPicUrl,title,userName,userUID,date,phone,email)
                            postArrayList.add(post)

                        }
                        feedScAdapter.notifyDataSetChanged()

                    }

                }
            }

        }

    }

}