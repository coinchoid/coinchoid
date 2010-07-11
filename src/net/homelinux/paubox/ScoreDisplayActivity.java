package net.homelinux.paubox;

import android.app.ListActivity;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ScoreDisplayActivity extends ListActivity  {

	private Parcelable[] games;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);



		ListView lv = getListView();
		lv.setTextFilterEnabled(true);

		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// When clicked, show a toast with the TextView text
				Toast.makeText(getApplicationContext(), ((TextView) view).getText(),
						Toast.LENGTH_SHORT).show();
			}
		});

		Bundle b = getIntent().getExtras();
		games = b.getParcelableArray("net.homelinux.paubox.Games");
		String[] strings = new String[games.length];
		for (int j = 0; j<games.length;j++) {
			strings[j] = games[j].toString();
		}

		setListAdapter(new ArrayAdapter<String>(this, R.layout.score_display, strings));
	}
}


