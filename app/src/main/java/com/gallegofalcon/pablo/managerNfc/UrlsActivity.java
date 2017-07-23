package com.gallegofalcon.pablo.managerNfc;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.gallegofalcon.pablo.managerNfc.bean.Url;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UrlsActivity extends AppCompatActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);
		((TextView) findViewById(R.id.textViewTitle)).setText("URLs");
	}

	@Override
	protected void onStart() {
		super.onStart();

		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl(MainActivity.SERVER)
				.addConverterFactory(GsonConverterFactory.create())
				.build();

		WebService service = retrofit.create(WebService.class);
		Call<Url[]> repos = service.urls();
		repos.enqueue(new Callback<Url[]>() {
			@Override
			public void onResponse(Call<Url[]> call, Response<Url[]> response) {
				Url[] urls = response.body();
				final ListView listview = (ListView) findViewById(R.id.listView);
				final ArrayList<Url> list = new ArrayList<Url>();
				for (Url url : urls) {
					Log.d("NFC", url.getName());
					list.add(url);
				}
				final StableArrayAdapter adapter = new StableArrayAdapter(UrlsActivity.this,
						android.R.layout.simple_list_item_1, list);
				listview.setAdapter(adapter);

				listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, final View view,
					                        int position, long id) {
						final Url item = (Url) parent.getItemAtPosition(position);

						Intent intent = new Intent(getBaseContext(), WriteActivity.class);
						intent.putExtra("NAME", item.getName());
						intent.putExtra("PATH", "u/"+item.getCode());
						startActivity(intent);
					}
				});
			}

			@Override
			public void onFailure(Call<Url[]> call, Throwable t) {

			}
		});
	}

	private class StableArrayAdapter extends ArrayAdapter<Url> {
		HashMap<Url, Integer> mIdMap = new HashMap<Url, Integer>();

		public StableArrayAdapter(Context context, int textViewResourceId,
		                          List<Url> objects) {
			super(context, textViewResourceId, objects);
			for (int i = 0; i < objects.size(); ++i) {
				mIdMap.put(objects.get(i), i);
			}
		}

		@Override
		public long getItemId(int position) {
			Url item = getItem(position);
			return mIdMap.get(item);
		}

		@Override
		public boolean hasStableIds() {
			return true;
		}

	}

}
