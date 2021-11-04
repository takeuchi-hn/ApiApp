package jp.techacademy.taichi.takeuchi.apiapp

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class ApiApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)

        //TODO レルムの追記が必要かも
       /* val configration = RealmConfiguration.Builder()
            .allowWritesOnUiThread(true)
            .allowQueriesOnUiThread(true)
            .build

        Realm.setDefaultConfiguration(configration)*/
    }
}