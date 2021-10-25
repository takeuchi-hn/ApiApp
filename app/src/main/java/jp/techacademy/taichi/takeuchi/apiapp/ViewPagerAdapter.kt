package jp.techacademy.taichi.takeuchi.apiapp

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {

    //タブの名前を格納したList getItemCount()で返す数字より小さいサイズだとクラッシュする
    val titleIds = listOf(R.string.tab_title_api, R.string.tab_title_favorite)

    //ページの中身            0ページ目　　　　　　1ページ目
    val fragments = listOf(ApiFragment(), FavoriteFragment())

    //ViewPager2が何ページあるのかという数字を返す
    override fun getItemCount(): Int {
        return fragments.size
    }

    //引数で受け取ったpositionのページのFragmentを返す
    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}