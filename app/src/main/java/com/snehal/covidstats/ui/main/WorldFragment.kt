package com.snehal.covidstats.ui.main


import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import com.snehal.covidstats.CountryFetcher

import com.snehal.covidstats.R
import com.snehal.covidstats.WorldFetcher


class WorldFragment : Fragment() {

    private lateinit var allTotalTv: TextView
    private lateinit var allActiveTv: TextView
    private lateinit var allRecoveredTv: TextView
    private lateinit var allDeathTv: TextView
    private lateinit var selTv: TextView
    private lateinit var selCv: CardView
    private lateinit var countryTotalTv: TextView
    private lateinit var countryActiveTv: TextView
    private lateinit var countryRecoveredTv: TextView
    private lateinit var countryDeathTv: TextView
    private lateinit var countryCode: HashMap<String, String>
    private lateinit var ll1: LinearLayout
    private lateinit var ll2: LinearLayout
    private lateinit var dia:Dialog
    private lateinit var lv: ListView
    private lateinit var names: ArrayList<String>
    private lateinit var fetcher: WorldFetcher


    private fun setAttr() {
        fetcher.totalTv = allTotalTv
        fetcher.activeTv = allActiveTv
        fetcher.recoveredTv = allRecoveredTv
        fetcher.deathTv = allDeathTv

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_world, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        allTotalTv = view.findViewById(R.id.all_total_tv)
        allActiveTv = view.findViewById(R.id.all_active_tv)
        allRecoveredTv = view.findViewById(R.id.all_recovered_tv)
        allDeathTv = view.findViewById(R.id.all_death_tv)
        selCv = view.findViewById(R.id.country_cv)
        selTv = view.findViewById(R.id.country_tv)
        countryTotalTv = view.findViewById(R.id.country_total_tv)
        countryActiveTv = view.findViewById((R.id.country_active_tv))
        countryRecoveredTv = view.findViewById(R.id.country_recovered_tv)
        countryDeathTv = view.findViewById(R.id.country_death_tv)
        ll1 = view.findViewById(R.id.ll_1)
        ll2 = view.findViewById(R.id.ll_2)
        dia = Dialog(this.context!!)
        dia.setContentView(R.layout.dd_dialog)
        lv = dia.findViewById(R.id.list_view)
        selCv.setOnClickListener { showCountries() }
        refreshList()

    }

    private fun refreshList() {
        fetcher = WorldFetcher(view!!.context)
        setAttr()
        fetcher.execute()
    }

    private fun showCountries() {
        if (!fetcher.inited) {
            Toast.makeText(view?.context, "Fetching Data...", Toast.LENGTH_SHORT).show()
            return
        }
        names = fetcher.names
        countryCode = fetcher.countryCode
        val adapter = ArrayAdapter(view!!.context, android.R.layout.simple_list_item_1, names)
        lv.adapter = adapter
        dia.show()
        lv.setOnItemClickListener { _, _, index, _ ->
            val name = names[index]
            val code = countryCode[name]
            selTv.text = name
            dia.dismiss()
            val cf = CountryFetcher(view!!.context)
            cf.code = code.toString()
            cf.totalTv = countryTotalTv
            cf.activeTv = countryActiveTv
            cf.recoveredTv = countryRecoveredTv
            cf.deathTv = countryDeathTv
            cf.execute()
            ll1.visibility = View.VISIBLE
            ll2.visibility = View.VISIBLE
        }
    }


}
