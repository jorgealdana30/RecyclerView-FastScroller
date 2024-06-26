package com.qtalk.sample.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.qtalk.recyclerviewfastscroller.RecyclerViewFastScroller
import com.qtalk.sample.R
import com.qtalk.sample.adapters.AdvancedAdapter
import com.qtalk.sample.data.Country
import com.qtalk.sample.databinding.FragmentAdvancedBinding
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class AdvancedFragment : Fragment() {

    private lateinit var binding: FragmentAdvancedBinding

    private val countriesList: List<Country> by lazy {
        Json.decodeFromString<List<Country>>(
            activity?.assets?.open("countries.json")?.use { it.reader().readText() }
                ?: error("Cannot read asset")
        ).sorted()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentAdvancedBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = AdvancedAdapter(
            activity,
            countriesList,
            with(binding.fastScroller) {
                handleDrawable
            })

        with(binding.advancedRecyclerView) {
            this.adapter = adapter
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }

        adapter.notifyDataSetChanged()
    }
}