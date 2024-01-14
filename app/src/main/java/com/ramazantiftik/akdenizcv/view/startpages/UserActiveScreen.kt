package com.ramazantiftik.akdenizcv.view.startpages

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ramazantiftik.akdenizcv.R
import com.ramazantiftik.akdenizcv.databinding.FragmentPostDetailScreenBinding
import com.ramazantiftik.akdenizcv.databinding.FragmentUserActiveScreenBinding


class UserActiveScreen : Fragment() {

    //viewBinding
    private var _binding: FragmentUserActiveScreenBinding?=null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //viewBinding
        _binding= FragmentUserActiveScreenBinding.inflate(inflater,container,false)
        val view=binding.root


        // Inflate the layout for this fragment
        return view
    }


}