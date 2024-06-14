package com.qtalk.sample.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.qtalk.sample.R
import com.qtalk.sample.adapters.BasicAdapter
import com.qtalk.sample.databinding.FragmentBasicBinding
import kotlinx.coroutines.*

class BasicFragment : Fragment() {

    private var swipeRefreshLayout: SwipeRefreshLayout? = null

    private var swipeJob: Job? = null
    private lateinit var binding: FragmentBasicBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentBasicBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

            with(binding.basicRecyclerView) {
                adapter = BasicAdapter(activity)
                addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            }

            with(binding.swipeRefreshLayout) {
                swipeRefreshLayout = this
                setOnRefreshListener {
                    swipeJob = CoroutineScope(Dispatchers.Main).launch {
                        delay(3000)

                        if (swipeRefreshLayout?.isRefreshing == true)
                            swipeRefreshLayout?.isRefreshing = false
                    }
                }

        }
    }

    override fun onDestroyView() {
        swipeJob?.cancel()
        super.onDestroyView()
    }
}