package com.qtalk.sample.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.qtalk.recyclerviewfastscroller.RecyclerViewFastScroller
import com.qtalk.sample.R
import com.qtalk.sample.databinding.RecyclerViewListItemBinding
import com.qtalk.sample.databinding.RecyclerViewListItemCenterBinding

class BasicAdapter(private val context: Context?) :
    RecyclerView.Adapter<BasicAdapter.ViewHolder>(), RecyclerViewFastScroller.OnPopupTextUpdate {

    override fun onChange(position: Int): CharSequence {
        return (position + 1).toString()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(RecyclerViewListItemCenterBinding.inflate(
            LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = (position + 1).toString()
    }

    override fun getItemCount() = 100

    class ViewHolder(view: RecyclerViewListItemCenterBinding) : RecyclerView.ViewHolder(view.root) {
        val textView: TextView = view.textView
    }
}