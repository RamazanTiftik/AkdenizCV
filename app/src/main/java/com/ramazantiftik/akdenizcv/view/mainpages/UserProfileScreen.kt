package com.ramazantiftik.akdenizcv.view.mainpages

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.ramazantiftik.akdenizcv.databinding.FragmentUserProfileScreenBinding

class UserProfileScreen : Fragment() {

    //viewBinding
    private var _binding: FragmentUserProfileScreenBinding?=null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //viewBinding
        _binding= FragmentUserProfileScreenBinding.inflate(inflater,container,false)
        val view=binding.root



        //*****Nav Buttons*****


        //go to homePage
        binding.homePageBtnUserProfileSc.setOnClickListener {
            val action= UserProfileScreenDirections.actionUserProfileScreenToFeedScreen()
            Navigation.findNavController(it).navigate(action)
        }

        //go to userCvScreen
        binding.cvBtnUserProfileSc.setOnClickListener {
            val action= UserProfileScreenDirections.actionUserProfileScreenToUserCvScreen()
            Navigation.findNavController(it).navigate(action)
        }


        // Inflate the layout for this fragment
        return view
    }


}