package com.jpmorgan.nycschools.api

import com.jpmorgan.nycschools.model.School
import com.jpmorgan.nycschools.model.SchoolStatistics
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface SchoolService{

    /**
     * https://data.cityofnewyork.us/resource/97mf-9njv.json
     *
     * @return
     */
    @GET("97mf-9njv.json")
    fun getSchoolList(): Single<List<School?>?>?

    /**
     * https://data.cityofnewyork.us/resource/734v-jeq5.json
     *
     * @param dbn
     * @return
     */
    @GET("734v-jeq5.json")
    fun getSchoolDetails(@Query("dbn") dbn: String?): Single<List<SchoolStatistics?>?>?
}