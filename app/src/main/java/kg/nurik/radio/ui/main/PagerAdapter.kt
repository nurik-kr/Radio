package kg.nurik.radio.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class PagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {

    private var dataForPager = arrayListOf<ViewPagerModel>()

    override fun getItemCount() = dataForPager.size

    override fun createFragment(position: Int) = dataForPager[position].fragment

    fun update(dataForPager: ArrayList<ViewPagerModel>) {
        this.dataForPager = dataForPager
        notifyDataSetChanged()
    }

    fun getCurrentItem(position: Int) = dataForPager[position]
}

data class ViewPagerModel(
    val fragment: Fragment,
    val title: String
)