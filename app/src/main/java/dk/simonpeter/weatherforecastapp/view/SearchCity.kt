package dk.simonpeter.weatherforecastapp.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import dk.simonpeter.weatherforecastapp.R
import dk.simonpeter.weatherforecastapp.openweathermap.geo.GeoResponse
import dk.simonpeter.weatherforecastapp.viewmodel.DayListViewModel
import dk.simonpeter.weatherforecastapp.viewmodel.SearchCityViewModel


class SearchCity : Fragment() {

    private val searchViewModel: SearchCityViewModel = SearchCityViewModel()
    // Det er med vilje, at vi ikke benytter "by activityViewModels()" men opretter en ny viewmodel
    // hver gang. Ellers kaldes "observe" med de senest hentede data, hvis søgningen gentages
    // og hvis data ellers er valide, "pop'es" fragmentet fra stack'en med det samme inden fragmentet bliver vist.
    // Det er måske ikke den rigtige måde at gøre det på, men ...

    private val dayListViewModel: DayListViewModel by activityViewModels()


    private lateinit var editText: EditText
    private lateinit var appContext: Context

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.search_city_fragment, container, false)
        val searchButton = view.findViewById<Button>(R.id.search_button)
        editText = view.findViewById(R.id.change_city)
        editText.requestFocus()
        appContext = container!!.context

        searchButton.setOnClickListener {
            searchButtonClicked()
        }

        editText.setOnEditorActionListener { v, actionId, event ->
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    searchButtonClicked()
                    true
                }
                else -> false
            }
        }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        searchViewModel.searchData.observe(viewLifecycleOwner) {
            setSearchData(it)
        }

    }

    private fun searchButtonClicked() {
        val searchText = editText.text.toString()
        if (searchText.isEmpty()) {
            editText.error = getString(R.string.no_city_name_error_msg)
            return
        }

        searchViewModel.searchCity(searchText)
    }

    private fun setSearchData(resp: GeoResponse) {
        if (resp.size > 0) {

            dayListViewModel.writeCoordinates(resp.get(0).lat, resp.get(0).lon, resp.get(0).name)

            parentFragmentManager.popBackStack()

        } else {
            editText.setError(getString(R.string.city_not_found_error_msg))
        }
    }

}