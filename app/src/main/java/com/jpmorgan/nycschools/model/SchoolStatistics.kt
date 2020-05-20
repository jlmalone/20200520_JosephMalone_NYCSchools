package com.jpmorgan.nycschools.model

import com.squareup.moshi.Json

class SchoolStatistics {

    @Json(name = "dbn")
    var dbn: String? = null

    @Json(name = "sat_writing_avg_score")
    var satWritingAvgScore: String? = null

    @Json(name = "sat_critical_reading_avg_score")
    var satCriticalReadingAvgScore: String? = null

    @Json(name = "sat_math_avg_score")
    var satMathAvgScore: String? = null

    @Json(name = "school_name")
    var schoolName: String? = null

    @Json(name = "num_of_sat_test_takers")
    var numOfSatTestTakers: String? = null


}