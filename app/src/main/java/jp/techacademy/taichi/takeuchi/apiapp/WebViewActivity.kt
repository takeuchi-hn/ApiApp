package jp.techacademy.taichi.takeuchi.apiapp

import android.app.Activity
import android.app.AlertDialog
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
        if(FavoriteShop.findBy(shop.id) != null) {
            mFavoriteState = true
        }else{
            mFavoriteState = false
        }
        // ★マークを再描画
        favoriteImageRedraw()

        // ★マークをクリックした時
        favoriteImageView.setOnClickListener{
            if(mFavoriteState) {
                // お気に入りに登録済みなので削除
                showConfirmDeleteFavoriteDialog(shop.id)
            }else{
                //お気に入り未登録なので登録
                onAddFavorite(shop)
                favoriteImageRedraw()//　★を再描画
            }
        }
    }

    // お気に入り登録用　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　
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

    // 「削除しますか？」のダイアログ表示　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　
    private fun showConfirmDeleteFavoriteDialog(id: String) {
        AlertDialog.Builder(this)
            .setTitle(R.string.delete_favorite_dialog_title)
            .setMessage(R.string.delete_favorite_dialog_message)
            .setPositiveButton(android.R.string.ok) { _, _ ->
                deleteFavorite(id) // 「OK」ならお気に入りから削除
                favoriteImageRedraw() // お気に入り状態を確認して、★マーク画像に反映する
            }
            .setNegativeButton(android.R.string.cancel) { _, _ -> }
            .create()
            .show()
    }

    // お気に入りから削除する　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　
    private fun deleteFavorite(id: String) {
        FavoriteShop.delete(id)
    }

    // ★の再描画用　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　
    private fun favoriteImageRedraw(){
        val shop = intent.getSerializableExtra(KEY_URL) as Shop
        mFavoriteState = FavoriteShop.findBy(shop.id) != null
        if(mFavoriteState){
            favoriteImageView.setImageResource(R.drawable.ic_star)
        } else {
            favoriteImageView.setImageResource(R.drawable.ic_star_border)
        }
    }

    // クリックした店の情報をShop型オブジェクトで受け渡す　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　
    companion object {
        private const val KEY_URL = "key_url"
        fun start(activity: Activity, shop: Shop) {
            // Shop型をSerializable型にしてput
            activity.startActivity(Intent(activity, WebViewActivity::class.java).putExtra(KEY_URL, shop))
        }
    }
}