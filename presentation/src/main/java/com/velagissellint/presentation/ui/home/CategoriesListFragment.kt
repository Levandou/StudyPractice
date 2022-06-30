package com.velagissellint.presentation.ui.home

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
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.velagissellint.domain.models.User
import com.velagissellint.presentation.R
import com.velagissellint.presentation.ViewModelFactory
import com.velagissellint.presentation.containersDi.ContainerAppContainer
import com.velagissellint.presentation.databinding.FragmentCategoriesListBinding
import com.velagissellint.presentation.ui.home.adapters.CategoriesListAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

class CategoriesListFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var fStore: FirebaseFirestore
    private lateinit var navController: NavController
    lateinit var rv: RecyclerView
    private lateinit var adapter: CategoriesListAdapter

    @Inject
    lateinit var factory: ViewModelFactory
    lateinit var categoriesListViewModel: CategoriesListViewModel

    private var _binding: FragmentCategoriesListBinding? = null
    private val binding get() = _binding ?: throw RuntimeException("FragmentAddItemBinding == null")

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity?.application as ContainerAppContainer).appContainer()
            ?.plusCategoriesListComponent()?.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        auth = FirebaseAuth.getInstance()
        fStore = FirebaseFirestore.getInstance()
        setHasOptionsMenu(true)
        categoriesListViewModel =
            ViewModelProvider(this, factory).get(CategoriesListViewModel::class.java)

        _binding = FragmentCategoriesListBinding.inflate(inflater, container, false)
        navController = NavHostFragment.findNavController(this)

        // val textView: TextView = binding.textHome
        /*categoriesListViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }*/
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        if (auth.currentUser != null) {
            val uid = auth.currentUser!!.uid
            val df = fStore.collection("Users").document(uid)
            fStore.collection("Users")
            //это без слушателя
//            df.get().addOnSuccessListener {
//                var a = it.toObject(User::class.java)
//                if (a?.adm == 1)
//                    inflater.inflate(R.menu.admin_mode, menu)
//            }
            df.addSnapshotListener { querySnapshot, error ->
                val a = querySnapshot?.toObject(User::class.java)
                if (a?.adm == 1)
                    inflater.inflate(R.menu.admin_mode, menu)
                else
                    inflater.inflate(R.menu.main, menu)
            }
            //взять весь список
//            fStore.collection("Users").get().addOnSuccessListener { querySnapshot ->
//                // Успешно получили данные. Список в querySnapshot.documents
//                val taskList: List<User> = querySnapshot.toObjects(User::class.java)
//                Log.d("adssalist", taskList.toString())
//            }
//                .addOnFailureListener { exception ->
//                    Log.d("adssalist", exception.toString())
//                }
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView(view)
        loadPaging()
    }

    private fun setupRecyclerView(view: View) {
        rv = view.findViewById(R.id.rv_category)
        rv.addItemDecoration(DividerItemDecoration(activity?.applicationContext))
        adapter = CategoriesListAdapter { id ->
            val action=CategoriesListFragmentDirections.actionNavHomeToProductListFragment(id)
            navController.navigate(action)
        }
        rv.adapter = adapter
        //    rv.adapter = adapter.withLoadStateFooter(ToDoListLoadStateAdapter { adapter.retry() })
//
    }
    private fun loadPaging() {
        lifecycleScope.launch {
            categoriesListViewModel.loadCategoryList().distinctUntilChanged().collectLatest {
                adapter.submitData(it)
            }
        }
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