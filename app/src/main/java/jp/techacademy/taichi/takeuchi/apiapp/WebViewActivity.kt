package jp.techacademy.taichi.takeuchi.apiapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_web_view.*
// FragmentCallback
class WebViewActivity: AppCompatActivity() {
    var mFavoriteState:Boolean = false //お気に入りの状態　初期は未登録
    //var shop:Shop = intent.getSerializableExtra(KEY_URL) as Shop

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("TIMER","WebViewActivity_onCreate")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        val shop = intent.getSerializableExtra(KEY_URL) as Shop
        val url = if(shop.couponUrls.sp.isNotEmpty()){
                      shop.couponUrls.sp}// スマホ
                  else{
                      shop.couponUrls.pc }
        // クーポン画面が立ち上がる
        webView.loadUrl(url)
        // お気に入りの状態を更新
        if(FavoriteShop.findBy(shop.id) == null) {
            mFavoriteState = true
        }else{
            mFavoriteState = false
        }
        //　★を再描画
        favoriteImageRedraw()

        // ★マークをクリックした時
        favoriteImageView.setOnClickListener{
            if(mFavoriteState) {
                // お気に入りに登録済みなので削除
            }else{
                //お気に入り未登録なので登録
                //onAddFavorite()

            }
        }
    }

    // o
    private fun onAddFavorite(shop: Shop) {
        //Log.d("TIMER","onAddFavorite")
        FavoriteShop.insert(FavoriteShop().apply {
            id = shop.id
            name = shop.name
            address = shop.address
            imageUrl = shop.logoImage
            url = if (shop.couponUrls.sp.isNotEmpty()) shop.couponUrls.sp else shop.couponUrls.pc
        })
    }

    //override fun onAddFavorite(shop: Shop) {
    //    //}
    // ★の再描画用
    private fun favoriteImageRedraw(){
        if(mFavoriteState){
            favoriteImageView.setImageResource(R.drawable.ic_star)
        } else {
            favoriteImageView.setImageResource(R.drawable.ic_star_border)
        }
    }
    companion object {
        private const val KEY_URL = "key_url"
        fun start(activity: Activity, shop: Shop) {
            // Shop型をSerializable型にしてput
            activity.startActivity(Intent(activity, WebViewActivity::class.java).putExtra(KEY_URL, shop))
        }
    }
}