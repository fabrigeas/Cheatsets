public class ImageAndTextAdapter extends ArrayAdapter<String> {

	Activity context;
	ArrayList<Integer> images;
	ArrayList<String>
	date,
	text;
	
	public ImageAndTextAdapter(Activity context,ArrayList<Integer> imgid,ArrayList<String> date, ArrayList<String> text) {
		super(context, R.layout.adapter_list,text);
		this.context=context;
		this.date=date;
		this.text=text;
		this.images=imgid;
	}
	
	public View getView(int position,View view,ViewGroup parent) {
		
		LayoutInflater inflater=context.getLayoutInflater();
		View rowView=inflater.inflate(R.layout.adapter_list, null,true);
		
		TextView  heading =  (TextView) rowView.findViewById(R.id._title);
		ImageView icon =     (ImageView) rowView.findViewById(R.id._icon);
		TextView  desc =     (TextView) rowView.findViewById(R.id.description);
		
		heading.setText(date.get(position));
		desc.setText(text.get(position));
		icon.setBackgroundResource(images.get(position));
		return rowView;
		
	};
}

adapter_list.xml
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android">
    <RelativeLayout style="@style/simpleWrap" android:padding="10dp" android:id="@+id/pri" >
	    <ImageView style="@style/round" android:contentDescription="@string/priority" android:id="@+id/_icon"/>
    </RelativeLayout>
    
    <LinearLayout style="@style/simpleWrap"  android:orientation="vertical" android:layout_toRightOf="@+id/pri">
		<TextView android:id="@+id/_title" style="@style/simpleWrap" />
	    <TextView style="@style/simpleWrap" android:id="@+id/description" /> 
    </LinearLayout>
    

</RelativeLayout>

adapter.text
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android" xmlns:tools="http://schemas.android.com/tools"    >
    <ListView android:id="@+id/_list" />
    <Button android:id="@+id/toLevel" style="@style/floatButton"android:onClick="close" />
</RelativeLayout>

