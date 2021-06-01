package com.billyindrai.subjetpack3.ui.favorite

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import com.billyindrai.subjetpack3.R
import com.billyindrai.subjetpack3.databinding.ActivityFavoriteBinding

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Favorite"

        val adapter = FavoriteAdapter(this, supportFragmentManager)

        binding.viewPagerFav.adapter = adapter
        binding.tabFav.setupWithViewPager(binding.viewPagerFav)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.sort_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
}