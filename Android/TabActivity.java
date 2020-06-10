package fab.apps.personal;

public class MainActivity extends TabActivity {

	TabHost tabHost; 
	TabSpec tabSpec;
	
	Intent intent;
	
    @SuppressLint("NewApi") @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

		TabHost tabHost = getTabHost(); 
		
//Create and add more TabSpecs
		intent = new Intent().setClass(this, YourActivity.class);
		TabSpec tabSpec = tabHost
			.newTabSpec("Title")
			.setIndicator("", getResources().getDrawable(R.drawable.ic_))
			.setContent(intent);

	
		tabHost.addTab(tabSpec);
		tabHost.addTab(AnotherSpec);
//		tabHost.setCurrentTab(2);
    }

}

activity_main.xml

<?xml version="1.0" encoding="utf-8"?>
<TabHost xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@android:id/tabhost"
    android:layout_width="fill_parent"
    android:layout_height="fill_parent">
    <LinearLayout
        android:orientation="vertical"
        android:layout_width="fill_parent"
        android:layout_height="fill_parent">
        <TabWidget
            android:id="@android:id/tabs"
            android:layout_width="fill_parent"
            android:layout_height="wrap_content" />
        <FrameLayout
            android:id="@android:id/tabcontent"
            android:layout_width="fill_parent"
            android:layout_height="fill_parent"/>
    </LinearLayout>
</TabHost>


//Register Intents in the Manifest