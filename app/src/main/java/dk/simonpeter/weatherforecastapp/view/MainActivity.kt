package dk.simonpeter.weatherforecastapp.view

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import dk.simonpeter.weatherforecastapp.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container, DayList())
            .commit()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_search -> {searchCityClicked()
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }

    private fun searchCityClicked(){
        val currentFragment = SearchCity()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, currentFragment)
            .addToBackStack(null).commit()
    }

}

