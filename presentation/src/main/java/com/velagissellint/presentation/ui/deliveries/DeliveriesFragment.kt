package com.velagissellint.presentation.ui.deliveries

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.velagissellint.presentation.ViewModelFactory
import com.velagissellint.presentation.containersDi.ContainerAppContainer
import com.velagissellint.presentation.databinding.FragmentDeliveriesBinding
import com.velagissellint.presentation.ui.deliveries.adapters.DeliveriesAdapter
import com.velagissellint.presentation.ui.home.DividerItemDecoration
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

class DeliveriesFragment : Fragment() {
    @Inject
    lateinit var factory: ViewModelFactory
    lateinit var deliveriesViewModel: DeliveriesViewModel

    private var _binding: FragmentDeliveriesBinding? = null
    private val binding: FragmentDeliveriesBinding
        get() = _binding ?: throw RuntimeException("FragmentDeliveriesBinding == null")
    private lateinit var navController: NavController
    private lateinit var adapter: DeliveriesAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity?.application as ContainerAppContainer).appContainer()?.plusDeliveriesComponent()
            ?.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        deliveriesViewModel = ViewModelProvider(this, factory)[DeliveriesViewModel::class.java]
        _binding = FragmentDeliveriesBinding.inflate(inflater, container, false)
        navController = NavHostFragment.findNavController(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        loadPaging()
    }

    private fun setupRecyclerView() {
        binding.rvDeliveries.addItemDecoration(DividerItemDecoration(activity?.applicationContext))
        adapter = DeliveriesAdapter()
        binding.rvDeliveries.adapter = adapter
    }

    private fun loadPaging() {
        lifecycleScope.launch {
            deliveriesViewModel.loadCategoryList().distinctUntilChanged().collectLatest {
                adapter.submitData(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}