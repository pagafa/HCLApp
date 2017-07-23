package com.gallegofalcon.pablo.managerNfc;

import com.gallegofalcon.pablo.managerNfc.bean.Location;
import com.gallegofalcon.pablo.managerNfc.bean.Url;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface WebService {

	@Headers("Authorization:Basic dXNlcjoxMjM0LTEyMzQ=")
	@GET("url/list")
	Call<Url[]> urls();

	@Headers("Authorization:Basic dXNlcjoxMjM0LTEyMzQ=")
	@GET("location/list")
	Call<Location[]> locations();

}
