package com.pimuseum.indexpicker.builder

import android.content.Context
import androidx.core.content.ContextCompat
import com.pimuseum.indexpicker.R
import com.pimuseum.indexpicker.data.Initial
import java.io.Serializable

class CityPickerBuilder(var context: Context) : Serializable {

    /**
     * 所有城市数据
     */
    private var allCities : ArrayList<Initial> = arrayListOf()

    /**
     * 热门城市数据
     */
    private var popularCities : ArrayList<Initial> = arrayListOf()

    /**
     * 主题色
     */
    private var primaryColor : Int = ContextCompat.getColor(context, R.color.pm_DefaultPrimaryColor)

    /**
     * 中间过渡色
     */
    private var gradientColor : Int = ContextCompat.getColor(context, R.color.pm_DefaultGradientColor)

    /**
     * 背景色
     */
    private var bgColor : Int = ContextCompat.getColor(context, R.color.pm_DefaultBgColor)

    /**
     * Sidebar Text Color
     */
    private var sidebarTextColor : Int = ContextCompat.getColor(context, R.color.pm_SideBarDefaultTextColor)

    /**
     * Sidebar Text Selected Color
     */
    private var sidebarSelectedTextColor : Int = ContextCompat.getColor(context, R.color.pm_SideBarDefaultSelectedTextColor)

    /**
     * Section Text Color
     */
    private var sectionTextColor : Int = ContextCompat.getColor(context, R.color.pm_SectionDefaultTextColor)

    /**
     * Item Text Color
     */
    private var itemTextColor : Int = ContextCompat.getColor(context, R.color.pm_ItemDefaultSelectedTextColor)


    fun initAllCities(allCities : ArrayList<Initial>) : CityPickerBuilder {
        this.allCities = allCities
        return  this
    }

    fun initPopularCities(popularCities : ArrayList<Initial>) : CityPickerBuilder {
        this.popularCities = popularCities
        return  this
    }

    fun primaryColor(color : Int) : CityPickerBuilder {
        primaryColor = color
        return this
    }

    fun gradientColor(color : Int) : CityPickerBuilder {
        gradientColor = color
        return this
    }

    fun bgColor(color : Int) : CityPickerBuilder {
        bgColor = color
        return this
    }

    fun sideBarTextColor(color : Int) : CityPickerBuilder {
        sidebarTextColor = color
        return this
    }

    fun sidebarSelectedTextColor(color : Int) : CityPickerBuilder {
        sidebarSelectedTextColor = color
        return this
    }

    fun sectionTextColor(color : Int) : CityPickerBuilder {
        sectionTextColor = color
        return this
    }

    fun itemTextColor(color : Int) : CityPickerBuilder {
        itemTextColor = color
        return this
    }
}