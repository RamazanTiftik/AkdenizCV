package com.ramazantiftik.akdenizcv.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.ramazantiftik.akdenizcv.databinding.HomescreenrowBinding
import com.ramazantiftik.akdenizcv.model.Post
import com.ramazantiftik.akdenizcv.view.mainpages.FeedScreenDirections

import com.squareup.picasso.Picasso

class FeedScRecyclerAdapter (val postList: List<Post>): RecyclerView.Adapter<FeedScRecyclerAdapter.FeedScViewHolder>(){

    class FeedScViewHolder(val binding: HomescreenrowBinding) : RecyclerView.ViewHolder(binding.root){
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedScViewHolder {
        val binding=HomescreenrowBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        postList.forEach{

            if (it.pictureUrl == null) {
                binding.postImageHomeScreenRow.visibility=View.GONE
            } else {
                binding.postImageHomeScreenRow.visibility=View.VISIBLE
            }


        }

        return FeedScViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    override fun onBindViewHolder(holder: FeedScViewHolder, position: Int) {

        holder.binding.userNameTxtHomeScreenRow.text=postList[position].userName.toString()
        holder.binding.postTextHomeScreenRow.text=postList[position].title.toString()
        holder.binding.userNameTxtHomeScreenRow.text=postList[position].userName.toString()
        Picasso.get().load(postList[position].pictureUrl.toString()).into(holder.binding.userImageHomeScreenRow)

        if (postList[position].postPicUrl != null && !postList[position].postPicUrl.toString().equals("")){
            //postPicUrl is not empty or not null
            Picasso.get().load(postList[position].postPicUrl.toString()).into(holder.binding.postImageHomeScreenRow)
        }

        holder.binding.linearLayoutHomeScRow.setOnClickListener {
            val action= FeedScreenDirections.actionFeedScreenToPostDetailScreen()
            Navigation.findNavController(it).navigate(action)
        }

    }

}