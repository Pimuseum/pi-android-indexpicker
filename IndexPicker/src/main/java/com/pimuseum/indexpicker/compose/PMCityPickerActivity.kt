package com.pimuseum.indexpicker.compose

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pimuseum.indexpicker.R
import com.pimuseum.indexpicker.adapter.CityAdapter
import com.pimuseum.indexpicker.decoration.DivideDecoration
import com.pimuseum.indexpicker.decoration.SectionDecoration
import com.pimuseum.indexpicker.tools.DataGenerator
import com.pimuseum.indexpicker.tools.noFastClick
import kotlinx.android.synthetic.main.pm_activity_city_picker.*
import android.view.View
import androidx.annotation.RequiresApi
import com.pimuseum.indexpicker.data.Initial


class PMCityPickerActivity : AppCompatActivity() , TextWatcher {

    companion object {

        const val RequestCode_Select = 2000
        const val RequestCode_Select_Start = 2001
        const val RequestCode_Select_End = 2002
        const val ResultCode_Select = 2011

        const val SelectResult = "select_result"

        fun citySelect(activity: Activity) {
            activity.startActivityForResult(Intent(activity,PMCityPickerActivity::class.java),RequestCode_Select)
            activity.overridePendingTransition(R.anim.pm_anim_bottom_in, 0)
        }

        fun citySelectForStart(activity: Activity) {
            activity.startActivityForResult(Intent(activity,PMCityPickerActivity::class.java),RequestCode_Select_Start)
            activity.overridePendingTransition(R.anim.pm_anim_bottom_in, 0)
        }

        fun citySelectForEnd(activity: Activity) {
            activity.startActivityForResult(Intent(activity,PMCityPickerActivity::class.java),RequestCode_Select_End)
            activity.overridePendingTransition(R.anim.pm_anim_bottom_in, 0)
        }

        fun citySelect(fragment: Fragment) {
            fragment.startActivityForResult(Intent(fragment.requireContext(),PMCityPickerActivity::class.java),RequestCode_Select)
            fragment.activity?.overridePendingTransition(R.anim.pm_anim_bottom_in, 0)
        }

        fun citySelectForStart(fragment: Fragment) {
            fragment.startActivityForResult(Intent(fragment.requireContext(),PMCityPickerActivity::class.java),RequestCode_Select_Start)
            fragment.activity?.overridePendingTransition(R.anim.pm_anim_bottom_in, 0)
        }

        fun citySelectForEnd(fragment: Fragment) {
            fragment.startActivityForResult(Intent(fragment.requireContext(),PMCityPickerActivity::class.java),RequestCode_Select_End)
            fragment.activity?.overridePendingTransition(R.anim.pm_anim_bottom_in, 0)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = ContextCompat.getColor(this, R.color.pm_DefaultBgColor)
        @RequiresApi(Build.VERSION_CODES.M)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        setContentView(R.layout.pm_activity_city_picker)
        initCityPicker()
    }

    private fun initCityPicker() {

        val data = DataGenerator.getAllCity(this)
        genInitialArray(data)

        tvClose.setOnClickListener {
            it.noFastClick()
            finish()
        }

        etSearch.addTextChangedListener(this)

        rvCity.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        rvCity.adapter = CityAdapter(this,data)

        rvCity.addItemDecoration(DivideDecoration(
            context = this,
            divideColor = ContextCompat.getColor(this,R.color.pm_DivideColor)))

        rvCity.addItemDecoration(SectionDecoration(context = this,
                textColor = ContextCompat.getColor(this,R.color.pm_SectionDefaultTextColor),
                contrastColor = ContextCompat.getColor(this,R.color.pm_DefaultBgColor))
        )

        rvCity.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val manager = recyclerView.layoutManager as LinearLayoutManager
                if (rvCity.adapter is CityAdapter) {
                    val adapter = rvCity.adapter as CityAdapter
                    val currentInitial = adapter.list[manager.findFirstVisibleItemPosition()].getSpellInitial()
                    if ( currentInitial != sideBar.getCurrentInitial()) {
                        sideBar.setCurrentInitial(currentInitial)
                        sideBar.invalidate()
                    }
                }
            }
        })

        sideBar.setOnSelectInitial { initialText->
            run outside@{
                (rvCity.adapter as CityAdapter).list.forEachIndexed { index, initial ->
                    if (initial.getSpellInitial() == initialText) {
                        (rvCity.layoutManager as? LinearLayoutManager)?.scrollToPositionWithOffset(index,0)
                        return@outside
                    }
                }
            }
        }
    }

    private fun genInitialArray(data : ArrayList<out Initial>) {
        val arrayList = arrayListOf<String>()
        data.forEach {
            if (!arrayList.contains(it.getSpellInitial())) arrayList.add(it.getSpellInitial())
        }
        sideBar.setInitialTextArray(arrayList)
    }

    override fun afterTextChanged(searchKey: Editable?) {
        if (searchKey.toString().isNotEmpty()) {

        } else {

        }
    }

    override fun beforeTextChanged(searchKey: CharSequence?, p1: Int, p2: Int, p3: Int) {}

    override fun onTextChanged(searchKey: CharSequence?, p1: Int, p2: Int, p3: Int) {}
}