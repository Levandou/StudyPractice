package com.velagissellint.presentation.ui.productList

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.velagissellint.domain.models.User
import com.velagissellint.presentation.R
import com.velagissellint.presentation.ViewModelFactory
import com.velagissellint.presentation.containersDi.ContainerAppContainer
import com.velagissellint.presentation.databinding.FragmentProductListBinding
import com.velagissellint.presentation.ui.home.CategoriesListFragmentDirections
import com.velagissellint.presentation.ui.home.DividerItemDecoration
import com.velagissellint.presentation.ui.home.adapters.CategoriesListAdapter
import com.velagissellint.presentation.ui.productList.adapters.ProductListAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProductListFragment : Fragment() {
    @Inject
    lateinit var factory: ViewModelFactory
    lateinit var productListViewModel: ProductListViewModel

    private lateinit var auth: FirebaseAuth
    private lateinit var fStore: FirebaseFirestore
    private var _binding: FragmentProductListBinding? = null
    private val binding: FragmentProductListBinding
        get() = _binding ?: throw RuntimeException("FragmentProductListBinding == null")
    private lateinit var navController: NavController
    private var id: String? = null
    lateinit var rv: RecyclerView
    private lateinit var adapter: ProductListAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity?.application as ContainerAppContainer).appContainer()?.plusProductListComponent()
            ?.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        productListViewModel = ViewModelProvider(this, factory)[ProductListViewModel::class.java]
        _binding = FragmentProductListBinding.inflate(inflater, container, false)
        navController = NavHostFragment.findNavController(this)
        auth = FirebaseAuth.getInstance()
        fStore=FirebaseFirestore.getInstance()
        setHasOptionsMenu(true)
        val args: ProductListFragmentArgs by navArgs()
        id = args.myArg
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView(view)
        loadPaging()
    }

    private fun setupRecyclerView(view: View) {
        rv = view.findViewById(R.id.rv_product)
        rv.addItemDecoration(DividerItemDecoration(activity?.applicationContext))
        adapter=ProductListAdapter()
        rv.adapter = adapter
        //    rv.adapter = adapter.withLoadStateFooter(ToDoListLoadStateAdapter { adapter.retry() })
//
    }

    private fun loadPaging() {
        lifecycleScope.launch {
            id?.let { idNotNull->
                productListViewModel.loadCategoryList(idNotNull).distinctUntilChanged().collectLatest {
                    adapter.submitData(it)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        if (auth.currentUser != null) {
            val uid = auth.currentUser!!.uid
            val df = fStore.collection("Users").document(uid)
            fStore.collection("Users")
            df.addSnapshotListener { querySnapshot, error ->
                val a = querySnapshot?.toObject(User::class.java)
                if (a?.adm == 1)
                    inflater.inflate(R.menu.admin_mode, menu)
                else
                    inflater.inflate(R.menu.main, menu)
            }
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add -> navController.navigate(R.id.addNewCategoryFragment)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}