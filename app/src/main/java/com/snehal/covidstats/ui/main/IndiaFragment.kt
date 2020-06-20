package com.snehal.covidstats.ui.main


import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import com.snehal.covidstats.Fetcher

import com.snehal.covidstats.R
import com.snehal.covidstats.States

class IndiaFragment : Fragment() {

    private lateinit var allTotalTv: TextView
    private lateinit var allActiveTv: TextView
    private lateinit var allRecoveredTv: TextView
    private lateinit var allDeathTv: TextView
    private lateinit var allDtotalTv: TextView
    private lateinit var allDactiveTv: TextView
    private lateinit var allDrecoveredTv: TextView
    private lateinit var allDdeathTv: TextView
    private lateinit var selTv: TextView
    private lateinit var stateTotalTv: TextView
    private lateinit var stateActiveTv: TextView
    private lateinit var stateRecoveredTv: TextView
    private lateinit var stateDeathTv: TextView
    private lateinit var stateDtotalTv: TextView
    private lateinit var stateDactiveTv: TextView
    private lateinit var stateDrecoveredTv: TextView
    private lateinit var stateDdeathTv: TextView
    private lateinit var fet: Fetcher
    private lateinit var names: ArrayList<String>
    private lateinit var states: HashMap<String, States>
    private lateinit var selCv: CardView
    private lateinit var lv: ListView
    private lateinit var dia: Dialog
    private lateinit var ll1: LinearLayout
    private lateinit var ll2: LinearLayout


    private fun setAttr() {
        fet.allTotalTv = allTotalTv
        fet.allActiveTv = allActiveTv
        fet.allRecoveredTv = allRecoveredTv
        fet.allDeathTv = allDeathTv
        fet.allDtotalTv = allDtotalTv
        fet.allDactiveTv = allDactiveTv
        fet.allDrecoveredTv = allDrecoveredTv
        fet.allDdeathTv = allDdeathTv

    }

    private fun commaFormat(i: Int, mark:Boolean = false): String {
        if (i==0)
            return "-"
        var sign="+"
        if (i<0)
            sign="-"
        var x = i
        var str = ""
        var gap = 3
        while (x%10!=0 || x/10!=0) {
            gap--
            str = (x%10).toString() + str
            if (gap==0) {
                str = ",$str"
                gap+=2
            }
            x/=10
        }
        if (str[0]==',')
            str = str.substring(1)
        if (mark)
            return sign+str
        return str
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_india, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        allTotalTv = view.findViewById(R.id.all_total_tv)
        allActiveTv = view.findViewById(R.id.all_active_tv)
        allRecoveredTv = view.findViewById(R.id.all_recovered_tv)
        allDeathTv = view.findViewById(R.id.all_death_tv)
        allDtotalTv = view.findViewById(R.id.all_dtotal_tv)
        allDactiveTv = view.findViewById(R.id.all_dactive_tv)
        allDrecoveredTv = view.findViewById(R.id.all_drecovered_tv)
        allDdeathTv = view.findViewById(R.id.all_ddeath_tv)
        selTv = view.findViewById(R.id.state_tv)
        stateTotalTv = view.findViewById(R.id.state_total_tv)
        stateActiveTv = view.findViewById(R.id.state_active_tv)
        stateRecoveredTv = view.findViewById(R.id.state_recovered_tv)
        stateDeathTv = view.findViewById(R.id.state_death_tv)
        stateDtotalTv = view.findViewById(R.id.state_dtotal_tv)
        stateDactiveTv = view.findViewById(R.id.state_dactive_tv)
        stateDrecoveredTv = view.findViewById(R.id.state_drecovered_tv)
        stateDdeathTv = view.findViewById(R.id.state_ddeath_tv)
        selCv = view.findViewById(R.id.state_cv)
        ll1 = view.findViewById(R.id.ll_1)
        ll2 = view.findViewById(R.id.ll_2)
        dia = Dialog(this.context!!)
        dia.setContentView(R.layout.dd_dialog)
        lv = dia.findViewById(R.id.list_view)
        selCv.setOnClickListener { showStates() }
        refreshList()
    }

    private fun refreshList() {
        fet = Fetcher(this.context!!)
        setAttr()
        fet.execute()
    }

    private fun showStates() {
        if (!fet.isInited()){
            Toast.makeText(this.context, "Fetching data...", Toast.LENGTH_SHORT).show()
            return
        }
        names = fet.names
        states = fet.states
        val adapter = ArrayAdapter(this.context!!, android.R.layout.simple_list_item_1, names)
        lv.adapter = adapter
        dia.show()
        lv.setOnItemClickListener { _, _, index, _ ->
            val st = names[index]
            selTv.text = st
            dia.dismiss()
            val selSt = states[st]
            stateTotalTv.text = commaFormat(selSt!!.total)
            stateActiveTv.text = commaFormat(selSt.active)
            stateRecoveredTv.text = commaFormat(selSt.discharged)
            stateDeathTv.text = commaFormat(selSt.deaths)
            stateDtotalTv.text = commaFormat(selSt.deltaTotal, true)
            stateDactiveTv.text = commaFormat(selSt.deltaActive, true)
            stateDrecoveredTv.text = commaFormat(selSt.deltaDischarged, true)
            stateDdeathTv.text = commaFormat(selSt.deltaDeaths, true)
            ll1.visibility = View.VISIBLE
            ll2.visibility = View.VISIBLE

        }
    }

}