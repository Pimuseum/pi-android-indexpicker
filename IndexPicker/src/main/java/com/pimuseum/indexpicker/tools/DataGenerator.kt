package com.pimuseum.indexpicker.tools

import android.content.Context
import com.pimuseum.indexpicker.data.Initial
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

object DataGenerator {

    fun getAllCity(context: Context) : ArrayList<out Initial> {
        val allCity = arrayListOf<City>()
        val cityJson = genJsonByFile(context,"allCity.json")
        try {
            if (cityJson.isNotEmpty()) {
                val city = JSONObject(cityJson).getJSONArray("city") as JSONArray
                for (i in 0 until city.length()) {
                    val cityJsonObj = city[i] as JSONObject
                    val spellInitial = cityJsonObj.getString("title")
                    val cityNameList = cityJsonObj.getJSONArray("lists")
                    for (j in 0 until cityNameList.length()) {
                        val cityName = cityNameList[j] as String
                        allCity.add(City(cityName,spellInitial))
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return allCity
        }
        return allCity
    }

    private fun genJsonByFile(context: Context, fileName: String): String {
        val sb = StringBuilder()
        try {
            val assetManager = context.assets
            val bf = BufferedReader(InputStreamReader(assetManager.open(fileName)))
            do {
                val line = bf.readLine()
                if (line != null) sb.append(line)
            } while (line != null)
        } catch (e: IOException) {
            e.printStackTrace()
            return ""
        }
        return sb.toString()
    }

}