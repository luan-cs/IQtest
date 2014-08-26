package com.example.tracnghiem;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class Manhinhchao extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_manhinhchao);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.manhinhchao, menu);
		return true;
	}

}
