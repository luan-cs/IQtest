package com.example.tracnghiem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class ManhinhchaoActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_manhinhchao);
		Thread BamGio = new Thread()
		{
			public void run()
			{
				try
				{
					sleep(2000);
				}
				catch(Exception e)
				{
					
				}
				finally
				{
					Intent newActivity = new Intent("com.example.tracnghiem.MainActivity");
					startActivity(newActivity);
				}
			}
		};
		BamGio.start();
	}
	
	//Khi chuyển qua MainActivity thì hàm này được gọi và kết thúc ManhinhchaoActivity
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}
	
	
}
