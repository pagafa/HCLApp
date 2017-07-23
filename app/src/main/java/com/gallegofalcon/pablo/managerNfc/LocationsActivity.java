package com.gallegofalcon.pablo.managerNfc;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.gallegofalcon.pablo.managerNfc.bean.Location;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class LocationsActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);
		((TextView) findViewById(R.id.textViewTitle)).setText("Locations");
	}

	@Override
	protected void onStart() {
		super.onStart();

		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl(MainActivity.SERVER)
				.addConverterFactory(GsonConverterFactory.create())
				.build();

		WebService service = retrofit.create(WebService.class);
		Call<Location[]> repos = service.locations();
		repos.enqueue(new Callback<Location[]>() {
			@Override
			public void onResponse(Call<Location[]> call, Response<Location[]> response) {
				Location[] locations = response.body();
				final ListView listview = (ListView) findViewById(R.id.listView);
				final ArrayList<Location> list = new ArrayList<Location>();
				for (Location location : locations) {
					list.add(location);
				}
				final LocationsActivity.StableArrayAdapter adapter = new LocationsActivity.StableArrayAdapter(LocationsActivity.this,
						android.R.layout.simple_list_item_1, list);
				listview.setAdapter(adapter);

				listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, final View view,
					                        int position, long id) {
						final Location item = (Location) parent.getItemAtPosition(position);

						Intent intent = new Intent(getBaseContext(), WriteActivity.class);
						intent.putExtra("NAME", item.getName());
						intent.putExtra("PATH", "l/"+item.getCode());
						startActivity(intent);
					}
				});
			}

			@Override
			public void onFailure(Call<Location[]> call, Throwable t) {

			}
		});
	}

	private class StableArrayAdapter extends ArrayAdapter<Location> {
		HashMap<Location, Integer> mIdMap = new HashMap<Location, Integer>();

		public StableArrayAdapter(Context context, int textViewResourceId,
		                          List<Location> objects) {
			super(context, textViewResourceId, objects);
			for (int i = 0; i < objects.size(); ++i) {
				mIdMap.put(objects.get(i), i);
			}
		}

		@Override
		public long getItemId(int position) {
			Location item = getItem(position);
			return mIdMap.get(item);
		}

		@Override
		public boolean hasStableIds() {
			return true;
		}

	}
}
