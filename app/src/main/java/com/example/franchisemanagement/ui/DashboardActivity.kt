package com.example.franchisemanagement.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.example.franchisemanagement.R
import com.example.franchisemanagement.ui.fragments.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.franchise_fragment.*

class DashboardActivity : AppCompatActivity(),
    BottomNavigationView.OnNavigationItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        loadFragment(HomeFragment())
        bottomNavigationView.setOnNavigationItemSelectedListener(this)

    }

    private fun loadFragment(frag: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, frag)
            .commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_home-> loadFragment(HomeFragment())
            R.id.menu_franchise -> loadFragment( FranchiseFragment(
            ) )
            R.id.menu_stores-> loadFragment(StoreFragment ())
            R.id.menu_products -> loadFragment( ProductFragment() )
            R.id.menu_orders -> loadFragment( OrdersFragment() )
        }
        return true
    }
}