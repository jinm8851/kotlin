package org.study2.navigationdemo

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.study2.navigationdemo.ui.main.MainFragment
import org.study2.navigationdemo.ui.main.SecondFragment

class MainActivity : AppCompatActivity(), SecondFragment.OnFragmentInteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        //앱이실행할때 메인프레그먼트를 실행하지않도록 삭제
//        if (savedInstanceState == null) {
//            supportFragmentManager.beginTransaction()
//                .replace(R.id.container, MainFragment.newInstance())
//                .commitNow()
//        }

    }

    override fun onFragmentInteraction(uri:Uri) {

    }
}