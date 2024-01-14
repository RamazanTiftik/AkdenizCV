package com.ramazantiftik.akdenizcv.view.detailpages

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.ramazantiftik.akdenizcv.databinding.FragmentPostDetailScreenBinding


class PostDetailScreen : Fragment() {

    //viewBinding
    private var _binding: FragmentPostDetailScreenBinding?=null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //viewBinding
        _binding= FragmentPostDetailScreenBinding.inflate(inflater,container,false)
        val view=binding.root


        //*****CLICKS*****

        //newPostScreen to userProfileSc
        binding.photoImageViewPostDetailSc.setOnClickListener {
            val action= PostDetailScreenDirections.actionPostDetailScreenToUserProfileScreen()
            Navigation.findNavController(it).navigate(action)
        }

        //*****Nav Buttons*****

        //go to userProfile
        binding.profileBtnDetailSc.setOnClickListener {
            val action= PostDetailScreenDirections.actionPostDetailScreenToUserProfileScreen()
            Navigation.findNavController(it).navigate(action)
        }

        //go to homePage
        binding.homePageBtnDetailSc.setOnClickListener {
            val action= PostDetailScreenDirections.actionPostDetailScreenToFeedScreen()
            Navigation.findNavController(it).navigate(action)
        }


        // Inflate the layout for this fragment
        return view
    }


}