package net.homelinux.paubox;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class EditActivity extends Activity {

	Editable obj;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		obj = (Editable)getIntent().getSerializableExtra("net.homelinux.paubox.Editable");
		final View editView = obj.makeEditView(this);
		setContentView(editView);
	}
}

interface Editable {
	public View makeEditView(Activity a);
}