package com.velagissellint.presentation

import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.collection.ArrayMap
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.velagissellint.presentation.databinding.ActivityMainBinding
import com.velagissellint.presentation.ui.logIn.LogInFragment
import com.velagissellint.presentation.ui.register.RegisterFragment
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)
        binding.appBarMain.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        //     navView.menu.add("sad")

//        val a=navView.menu.size()
//        navView.menu.removeItem(-1)   начинается с -1
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home//сюда положить нужные для меню
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        val headerView = binding.navView.getHeaderView(0)
        setLogicMenu(headerView)
        headerView.findViewById<Button>(R.id.bt_log_in).setOnClickListener {
            navController.navigate(R.id.mainFragment)
            drawerLayout.closeDrawers()
            setLogicMenu(headerView)
        }

        headerView.findViewById<Button>(R.id.bt_log_out).setOnClickListener {
            navController.currentDestination?.id?.let { navController.navigate(it) }
            auth.signOut()
            setLogicMenu(headerView)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun setLogicMenu(headerView: View) {
        if (auth.currentUser == null) {
            headerView.findViewById<LinearLayout>(R.id.ll_data_user).isVisible = false
            headerView.findViewById<Button>(R.id.bt_log_in).isVisible = true
            headerView.findViewById<Button>(R.id.bt_log_out).isVisible = false
        } else {
            headerView.findViewById<LinearLayout>(R.id.ll_data_user).isVisible = true
            headerView.findViewById<Button>(R.id.bt_log_in).isVisible = false
            headerView.findViewById<Button>(R.id.bt_log_out).isVisible = true
        }
    }
}

abstract class BaseFragment : Fragment() {
    val pageId = Random.nextLong(2021, 2021 * 3)
    var pagePos = -1
    private lateinit var fragmentReplacer: FragmentReplacer

    fun setPageInfo(pagePos: Int, fragmentReplacer: FragmentReplacer) {
        this.pagePos = pagePos
        this.fragmentReplacer = fragmentReplacer
    }
}

interface FragmentReplacer {
    fun replace(position: Int, newFragment: BaseFragment, isNotify: Boolean = true)
    fun replaceDef(position: Int, isNotify: Boolean = true): BaseFragment
}

class MyViewPager2Adapter(fragment: Fragment) : FragmentStateAdapter(fragment),
    FragmentReplacer {

    companion object {
        private const val PAGE_COUNT = 2
    }

    private val mapOfFragment = ArrayMap<Int, BaseFragment>()


    override fun replace(position: Int, newFragment: BaseFragment, isNotify: Boolean) {
        mapOfFragment[position] = newFragment
        if (isNotify)
            notifyItemChanged(position)
    }

    override fun replaceDef(position: Int, isNotify: Boolean): BaseFragment {
        val fragment = when (position) {
            0 -> LogInFragment()
            1 -> RegisterFragment()
            else -> throw IllegalStateException()
        }
        fragment.setPageInfo(
            pagePos = position,
            fragmentReplacer = this
        )

        replace(position, fragment, isNotify)

        return fragment
    }


    override fun createFragment(position: Int): Fragment {
        return mapOfFragment[position] ?: replaceDef(position, false)
    }

    override fun containsItem(itemId: Long): Boolean {
        var isContains = false
        mapOfFragment.values.forEach {
            if (it.pageId == itemId) {
                isContains = true
                return@forEach
            }
        }
        return isContains
    }

    override fun getItemId(position: Int) =
        mapOfFragment[position]?.pageId ?: super.getItemId(position)

    override fun getItemCount() = PAGE_COUNT
}