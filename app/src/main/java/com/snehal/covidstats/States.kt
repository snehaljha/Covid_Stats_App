package com.snehal.covidstats

import org.jsoup.nodes.Document
import org.jsoup.select.Elements

class States(name: String, doc: Document) {
    private var code: String
    var total = -1
    var active = -1
    var discharged = -1
    var deaths = -1
    var deltaTotal = -1
    var deltaActive = -1
    var deltaDischarged = -1
    var deltaDeaths = -1

    init {
        var temp = name
        temp = temp.toLowerCase()
        temp = temp.trim { it <= ' ' }
        temp = temp.replace(" ".toRegex(), "-")
        code = temp
        var ele: Elements = doc.select("tr.filter-$code")
        ele = ele.select(".table-col:not(strong.s-changes)")
        total = ele[0].html().split(" ")[0].toInt()
        active = ele[1].html().split(" ")[0].toInt()
        discharged = ele[2].html().split(" ")[0].toInt()
        deaths = ele[3].html().split(" ")[0].toInt()
        ele = ele.select(".s-changes")
        deltaTotal = ele[0].html().toInt()
        deltaActive = ele[1].html().toInt()
        deltaDischarged = ele[2].html().toInt()
        deltaDeaths = ele[3].html().toInt()
    }
}
