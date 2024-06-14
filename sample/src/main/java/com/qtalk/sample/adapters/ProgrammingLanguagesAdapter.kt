package com.qtalk.sample.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.qtalk.recyclerviewfastscroller.RecyclerViewFastScroller
import com.qtalk.sample.R
import com.qtalk.sample.databinding.RecyclerViewListItemBinding

class ProgrammingLanguagesAdapter(private val list: List<String>) :
    RecyclerView.Adapter<ProgrammingLanguagesAdapter.ViewHolder>(),
    RecyclerViewFastScroller.OnPopupTextUpdate
{

    class ViewHolder(val view: RecyclerViewListItemBinding) : RecyclerView.ViewHolder(view.root)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.textView.text = list[position]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(RecyclerViewListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int =
        list.size

    override fun onChange(position: Int): CharSequence =
        list[position].first().uppercaseChar().toString()

}