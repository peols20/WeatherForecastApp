package dk.simonpeter.weatherforecastapp.openweathermap.geo

data class GeoResponseItem(
    val country: String,
    val lat: Double,
    val local_names: LocalNames,
    val lon: Double,
    val name: String,
    val state: String
)