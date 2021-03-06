package kg.nurik.radio.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import kg.nurik.radio.databinding.ActivityMainBinding
import kg.nurik.radio.ui.favourite.FavouriteFragment
import kg.nurik.radio.ui.popular.PopularFragment
import kg.nurik.radio.utils.viewBinding

class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)
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
                "Проигрыватель"
            )
        )
        return list
    }
}