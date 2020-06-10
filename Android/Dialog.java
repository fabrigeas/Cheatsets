public void dialog(final int position){
	
	  AlertDialog.Builder myDialog = new AlertDialog.Builder(this);
	  myDialog.setMessage("Dou you want to Delete or Edit");
	  
	  myDialog.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
		 @Override
		 public void onClick(DialogInterface arg0, int arg1) {
			Toast.makeText(getApplicationContext(),"Editing:"+position,Toast.LENGTH_LONG).show();
		 }
	  });
	  
	  myDialog.setNegativeButton("Delete",new DialogInterface.OnClickListener() {
		 @Override
		 public void onClick(DialogInterface dialog, int which) {
			 Toast.makeText(getApplicationContext(),"The Event has been Succesfully deleted !!",Toast.LENGTH_LONG).show();
			 db.deleteEvent(position);
			finish();
		 }
	  });
	  
	  AlertDialog alertDialog = myDialog.create();
	  alertDialog.show();
}





//Dialog with Inputs
//1
//2 create a Layout file for the Dilog
public void add(){
	LayoutInflater layoutInflater = LayoutInflater.from(this);
	View promptView = layoutInflater.inflate(R.layout.input_dialog, null);

	detailInput = (EditText) promptView.findViewById(R.id.inputDetail);
	amountInput = (EditText) promptView.findViewById(R.id.inputAmount);
	
	AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
	alertDialogBuilder.setView(promptView);
	alertDialogBuilder.setCancelable(false)
	.setPositiveButton("OK", new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int id) {
			text(""+detailInput.getText()+"|"+amountInput.getText());
		}
	})
	.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int id) {
			dialog.cancel();
		}
	});
	AlertDialog alert = alertDialogBuilder.create();
	alert.show();
}





	public void setIpAddress(){
		LayoutInflater layoutInflater = LayoutInflater.from(this);
		View promptView = layoutInflater.inflate(R.layout.ipaddress_input_layout, null);

		final EditText ip_inputEditTex = (EditText) promptView.findViewById(R.id.ipAddress_input);
		
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder.setView(promptView);
		alertDialogBuilder.setCancelable(false)
		.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				blink(ip_inputEditTex.getText().toString());
			}
		})
		.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
			}
		});
		AlertDialog alert = alertDialogBuilder.create();
		alert.show();
		
	}
ipAddress_input.xml

	<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="vertical"
    android:padding="10dp">
    
        <EditText
        android:id="@+id/ipAddress_input"
        style="@style/fill"
        android:hint="@string/ipAddress_input"
        android:text="@string/ipAddress_input"
        android:inputType="textCapWords"
         />
    
</RelativeLayout>



