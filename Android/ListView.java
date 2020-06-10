

1 String[] values
2 Arraylist<String>
3 ListView
4 the Activitylayout must contain the Listview layout

setContentView(R.layout.activity_main);
String[] values;
ArrayList<String> arraylist = new ArrayList<String>();
for (int i = 0; i < values.length; ++i) 
  arraylist.add(values[i]);

ListView listview=(ListView)findViewById(R.id._list2);
ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, arraylist);
listview.setAdapter(adapter);


//activity_main.xml
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    <ListView
        android:id="@+id/_list2"
    	style="@style/wrap"
    	android:dividerHeight="5dp"
        />
</RelativeLayout>













setContentView(R.layout.activity_main);

ArrayList<String> arraylist=new ArrayList<String>();
arraylist.add("aad");

ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.activity_listview, arraylist);
ListView listView = (ListView) findViewById(R.id.mobile_list);
listView.setAdapter(adapter);


activity_listview.xml
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android" xmlns:tools="http://schemas.android.com/tools"tools:context="com.example.fitness.MainActivity" >
   <ListView android:id="@+id/mobile_list" </ListView>
</RelativeLayout>

activity_main.xml
<?xml version="1.0" encoding="utf-8"?> 
<TextView xmlns:android="http://schemas.android.com/apk/res/android"android:id="@+id/label"</TextView>






//2 From DBase
Dbase db;

ArrayAdapter<String> adapter;
ListView listView;
ArrayList<String> arraylist;
List <Course>listOfCourses;

setContentView(R.layout.course_list_layout);

arraylist=new ArrayList<String>();

db= new Dbase(this);
listOfCourses= db.getAllCourses();
for (int i = 0; i < listOfCourses.size(); i++)
	arraylist.add(listOfCourses.get(i).toString());

adapter = new ArrayAdapter<String>(this, R.layout.course_list_listview, arraylist);
listView = (ListView) findViewById(R.id.courseListListView);
listView.setAdapter(adapter);