package com.qtalk.sample.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.qtalk.recyclerviewfastscroller.RecyclerViewFastScroller
import com.qtalk.sample.R
import com.qtalk.sample.adapters.ProgrammingLanguagesAdapter
import com.qtalk.sample.databinding.FragmentProgrammingLanguagesBinding
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.util.Locale

class ProgrammingLanguagesFragment : Fragment() {

    private lateinit var binding: FragmentProgrammingLanguagesBinding

    private val programmingLanguages: List<String> by lazy {
        Json.decodeFromString<List<String>>(
            requireActivity().assets.open("programming_languages.json").use {
                it.reader().readText()
            }
        ).sortedBy { it.toLowerCase(Locale.ROOT) }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentProgrammingLanguagesBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_programming_languages, container, false).apply {

            binding.recyclerView.layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false
            )

            binding.fab.setOnClickListener {
                with(binding.fastScroller) {
                    val tmp = handleWidth
                    handleWidth = handleHeight
                    handleHeight = tmp

                    fastScrollDirection =
                        if (fastScrollDirection == RecyclerViewFastScroller.FastScrollDirection.HORIZONTAL) {
                            binding.recyclerView.layoutManager =
                                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                            RecyclerViewFastScroller.FastScrollDirection.VERTICAL
                        } else {
                            binding.recyclerView.layoutManager =
                                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                            RecyclerViewFastScroller.FastScrollDirection.HORIZONTAL
                        }
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.recyclerView.adapter = ProgrammingLanguagesAdapter(programmingLanguages)
    }

}
