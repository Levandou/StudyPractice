package com.velagissellint.presentation.diplom

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.velagissellint.presentation.R
import com.velagissellint.presentation.databinding.ActivityMain2Binding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity2 : AppCompatActivity() {
    private val navController by lazy {
        findNavController(R.id.nav_host_fragment_content_main)
    }
    private val appBarConfiguration: AppBarConfiguration by lazy {
        AppBarConfiguration(navController.graph)
    }

    private val viewModel by viewModel<Main2ViewModel>()
    var _binding: ActivityMain2Binding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMain2Binding = ActivityMain2Binding.inflate(layoutInflater)
        _binding = binding
        setContentView(binding.root)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)

        binding.toolbar.setNavigationOnClickListener {
            val listMainFragments =
                listOf(R.id.profileFragment, R.id.documentationFragment, R.id.taskListFragment)
            navController.popBackStack()
            val lastFragmentId = navController.backQueue.last().destination.id
            if (listMainFragments.contains(lastFragmentId))
                binding.toolbar.navigationIcon = null

        }

        val user = viewModel.getUser()

        if (user == null) {
            _binding?.clBottomNavigation?.isVisible = false
        } else {
            val resId = if (user.isAdmin)
                R.id.adminChooseActionFragment
                else R.id.profileFragment

            navController.popBackStack()
            navController.navigate(resId)
            binding.toolbar.navigationIcon = null

            if (user.isAdmin)
                _binding?.clBottomNavigation?.isVisible = false
        }

        binding.ivProfile.setOnClickListener {
            navigatePopUp(R.id.action_profile_fragment)
        }

        binding.ivDocuments.setOnClickListener {
            navigatePopUp(R.id.action_docs)
        }

        binding.ivTasks.setOnClickListener {
            navigatePopUp(R.id.action_tasks_fragment)
        }
    }

    private fun navigatePopUp(res: Int) {
        navController.navigate(res)
        _binding?.toolbar?.navigationIcon = null
    }

    fun navigate(res: Int, bundle: Bundle? = null, withPopUp: Boolean = false) {
        if (withPopUp) {
            val idCurrent = navController.currentDestination?.id ?: 0
            navController.popBackStack(idCurrent, true)
        }

        navController.navigate(res, bundle)
        val listMainFragments =
            listOf(R.id.profileFragment, R.id.documentationFragment, R.id.taskListFragment)
        if (listMainFragments.contains(res))
            _binding?.toolbar?.navigationIcon = null

        if (viewModel.getUser()?.isAdmin != true)
            _binding?.clBottomNavigation?.isVisible = true
    }
}