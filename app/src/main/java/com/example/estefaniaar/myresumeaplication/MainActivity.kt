package com.example.estefaniaar.myresumeaplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import java.net.URL
import org.json.JSONObject
import android.os.AsyncTask
import android.support.design.widget.BottomNavigationView
import java.io.BufferedReader
import java.io.InputStreamReader
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import java.io.BufferedInputStream
import java.net.HttpURLConnection


class MainActivity : AppCompatActivity() {

    val overviewFragment:OverviewFragment=OverviewFragment()
    val experienceFragment:ExperienceFragment= ExperienceFragment()
    val educationFragment:EducationFragment=EducationFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        println("CREATING ACTIVITY....")

        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container,overviewFragment)
            .addToBackStack(null)
            .commit()
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.prof->{

                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container,overviewFragment)
                    .addToBackStack(null)
                    .commit()
                bottomNavigationView.getMenu().getItem(0).setChecked(true);
                true
            }
            R.id.exp->{
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container,experienceFragment)
                    .addToBackStack(null)
                    .commit()
                bottomNavigationView.getMenu().getItem(2).setChecked(true);
                true
            }
            R.id.edu->{
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container,educationFragment)
                    .addToBackStack(null)
                    .commit()
                bottomNavigationView.getMenu().getItem(1).setChecked(true);
                true
            }

        }
        false
    }

}


