package com.example.test_glow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.test_glow.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    lateinit var binding : ActivityMainBinding
    private val TAG = "MainActivity"



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        binding = ActivityMainBinding.inflate(layoutInflater)
        settingActionBar()
        setContentView(binding.root)
    }

    private fun settingActionBar(){
        val toolbar = binding.myToolbar
        setSupportActionBar(toolbar)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        toolbar.setupWithNavController(navController, AppBarConfiguration(navController.graph))

        //actionBar navigationIcon 버튼 클릭, back Key 이벤트.
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        navController.addOnDestinationChangedListener{_, destination, _ ->
            toolbar.title = destination.label

            when(destination.id){
                R.id.listFragment->{
                    toolbar.navigationIcon = null
                }
                else->{
                    toolbar.setNavigationIcon(R.drawable.ic_round_arrow_back_ios_24)
                    toolbar.setNavigationIconTint(resources.getColor(R.color.black))
                }
            }
        }


    }
}