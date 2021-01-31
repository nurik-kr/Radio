package kg.nurik.radio.ui.main

import android.content.ServiceConnection
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import kg.nurik.radio.databinding.ActivityMainBinding
import kg.nurik.radio.ui.main.favourite.FavouriteFragment
import kg.nurik.radio.ui.popular.PopularFragment
import kg.nurik.radio.utils.viewBinding
import kg.nurik.radio.utils.viewModel

class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)
    private val viewModel by viewModel(MainViewModel::class) //ext fun to helper
    private lateinit var serviceConnection: ServiceConnection

    private val adapterViewPager by lazy { PagerAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupTable()
    }

    private fun setupTable() {
        binding.viewPager.adapter = adapterViewPager
        adapterViewPager.update(getDataForPager() as ArrayList<ViewPagerModel>)
        TabLayoutMediator(binding.tableLayout, binding.viewPager) { tab, pos ->
            tab.text = adapterViewPager.getCurrentItem(pos).title
        }.attach()
    }

    private fun getDataForPager(): List<ViewPagerModel> {
        val list = arrayListOf<ViewPagerModel>()
        list.add(
            ViewPagerModel(
                PopularFragment(),
                "Популярные"
            )
        )
        list.add(
            ViewPagerModel(
                FavouriteFragment(),
                "Любимые"
            )
        )
        return list
    }

    override fun onDestroy() {
        super.onDestroy()
        //For destroy service when activity is destroyed
        unbindService(serviceConnection)
    }
}