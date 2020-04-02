package com.pimuseum.indexpicker.tools

import com.pimuseum.indexpicker.data.Initial
import java.io.Serializable

class City(var cityName : String = "",
           var cityInitial : String = "") : Serializable , Initial, Comparable<City> {

    override fun getTextName(): String = cityName

    override fun getSpellInitial(): String = cityInitial

    override fun compareTo(other: City): Int {
        return getSpellInitial().compareTo(other.getSpellInitial())
    }
}