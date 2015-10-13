package net.homelinux.paubox;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Example that shows finding a preference from the hierarchy and a custom preference type.
 */
public class PreferencesActivity extends PreferenceActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Load the XML preferences file
		addPreferencesFromResource(R.xml.preferences);
	}
}
