package dk.simonpeter.weatherforecastapp.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import dk.simonpeter.weatherforecastapp.R

class SearchCity : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.search_city_fragment, container, false)
        // finding the button
        val showButton = view.findViewById<Button>(R.id.search_button)

        // finding the edit text
        val editText = view.findViewById<EditText>(R.id.change_city)

        // Setting On Click Listener
        showButton.setOnClickListener {

            // Getting the user input
            val text = editText.text

            Log.i("menu", "search")

        }
        return view
    }


}