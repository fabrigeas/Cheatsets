// conditional operator
{
	max = (a > b) ? a : b;
	filter = (getIntent().getStringExtra(c.type)==null) ? c.home : getIntent().getStringExtra(c.type);
}
//AsyncTask<Params, Progress, Result>
{
AsyncTask<Params, Progress, Result>
{
	1 Params //parameter for doInBackground)
	2 Progress //parameter for onProgressUpdate
	3 Result //parameter for Result (output of doInBackground)

	new DownloadTast().execute("facebook.com", "gmail.com", "hotmail.com");

	   private class PostTask extends AsyncTask<String, Integer, String> {
	   @Override
	   protected void onPreExecute() {
		  super.onPreExecute();
		  displayProgressBar("Downloading...");
	   }
	   @Override
	   protected String doInBackground(String... params) {
		  String url=params[0];//facebook.com
		  for (int i = 0; i <= 100; i += 5) {
			try {Thread.sleep(50);}
			catch (InterruptedException e) {}
			 publishProgress(i);
		  }
		  return "All Done!";
	   }
	 
	   @Override
	   protected void onProgressUpdate(Integer... values) {
		  super.onProgressUpdate(values);
		  updateProgressBar(values[0]);
	   }
	 
	   @Override
	   protected void onPostExecute(String result) {
		  super.onPostExecute(result);
		  dismissProgressBar();
	   }
	}
}
}
//AsyncTask
{
	AsyncTask<String, Integer, String> {
	   @Override
	   protected void onPreExecute() {
		  super.onPreExecute();
		  displayProgressBar("Downloading...");
	   }
	 
	   @Override
	   protected String doInBackground(String... params) {
		  String url=params[0];
		  for (int i = 0; i <= 100; i += 5) {
			try {Thread.sleep(50);} catch (InterruptedException e) {e.printStackTrace();}
			publishProgress(i);
		  }
		  return "this return is the parameter to onPostExecute!";
	   }
	 
	   @Override
	   protected void onProgressUpdate(Integer... values) {
		  super.onProgressUpdate(values);
		  updateProgressBar(values[0]);
	   }
	 
	   @Override
	   protected void onPostExecute(String result) {
		  super.onPostExecute(result);
		  dismissProgressBar();
	   }
	}
	
	
	        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {


                } catch (final Exception e) { runOnUiThread(new Runnable(){ @Override public void run() {toast(e.getMessage());} });}
                return null;
            }

            @Override
            protected void onPostExecute(Void result)
            {}
	
}

//Date
{
new SimpleDateFormat("E dd MMM yyyy HH:mm", Locale.getDefault()).format(new Date())
new SimpleDateFormat("dd.MM.yyyy-hh:mm", Locale.getDefault()).format(new Date());
// HH 24hour format, hh 12hour
// HH:mm a pm/am
}

//DatePicker date into text. convert the integer values of datepicker into text
{
String due=new SimpleDateFormat("E dd MMM yyyy", Locale.getDefault())
.format(new GregorianCalendar(year, month, day).getTime());
}

//Start and stop thread
{
ExecutorService threadPoolExecutor;
Future longRunningTaskFuture;

threadPoolExecutor = Executors.newSingleThreadExecutor();
longRunningTaskFuture = threadPoolExecutor.submit(getMessagesList);
	
private Runnable getMessagesList = new Runnable() {
	@Override
	public void run() 
	{
		...
		stopSelf();
	}
}; 
// stop the thread
longRunningTaskFuture.cancel(true);
}

//Sharedpreferences

//copy files in android
{
	public boolean copyFile(File src, File dest) {
		try {
			InputStream is = new FileInputStream(src);
			OutputStream os = new FileOutputStream(dest);
			byte[] buf = new byte[1024];
			int len;
			while ((len = is.read(buf)) > 0)
				os.write(buf, 0, len);

		} catch (Exception e) {text(e.toString());return false;}
		return true;
	}
}
//Insert and remove items     
{

private String[] append(String tail){
String[]temp=new String[options.length+1];
	for (int i=0;i<options.length;i++)
		temp[i]=options[i];
	temp[options.length]=tail;
	return temp;
}
private String[] prepend(String tail){
	String[]n= options;
	final List<String> list =  new ArrayList<>();
	Collections.addAll(list, n);
	list.remove(c.paste_here);
	n = list.toArray(new String[list.size()]);
	return n;
}into array dynamically

}

//start Activity for results
{
startActivityForResult(new Intent(Activity.this,CustomDialogActivity.class) , REQUEST_CODE);
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	try {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == RESULT_OK)
			String requiredValue = data.getStringExtra("Key");
	} 
	catch (Exception e) {}

}

//CustomDialogActivity.java
Intent intent = getIntent();
intent.putExtra("key", value);
setResult(RESULT_OK, intent);
finish();
}

//Regular expressions. string matched 
{
breadCrump.matches("(.*)[Pp]erso(.*)")
https://docs.oracle.com/javase/7/docs/api/java/util/regex/Pattern.html
}

//Dialogs, Alert Dialog
{
//Dialog with input and layout 
promptView = LayoutInflater.from(this).inflate(R.layout.add_dialog_layout, null);
_value = (EditText) promptView.findViewById(R.id.add_value);
_value.setTransformationMethod(PasswordTransformationMethod.getInstance());//change input type to password

new AlertDialog.Builder(this)
.setMessage("title")//title
.setView(promptView)//optional normal view file
.setCancelable(false)
.setNegativeButton(c.cancel_text, null)
.setPositiveButton("ok", new OnClickListener() {@Overridepublic void onClick(DialogInterface dialog, int which) {}})
.create().show();

//Diakog with string arrays as option
new AlertDialog.Builder(MainActivity.this)
setNegativeButton("cancel", null)
setItems(new String[]={"","","",""}, 
	new DialogInterface.OnClickListener() {		public void onClick(DialogInterface dialog, int which) {
		//SWITCH STRING, multiple cases in switch
		switch (options[which]) {case "A" : case "B": case "C": setPosition(item);break;}}}).show();
}	
		
//BROADCAST RECEIVERS
{
	//A BROADCAST IN SEPARATE FILE
	{
		public class ExitAppReceiver extends BroadcastReceiver {
			@Override public void onReceive(Context context, Intent intent) {}
		}

		// REGISTER THE BR IN MANIFEST
		<receiver android:name=".ExitAppReceiver" >
			<intent-filter> 
				<action android:name="personal.apps.fabrigeas.tchat.exiting"></action>
			</intent-filter>
		</receiver>

		// SEND BROADCAST FROM ANOTHER APP
		Intent intent = new Intent();
		intent.setAction("personal.apps.fabrigeas.tchat.exiting");
		sendBroadcast(intent);
	}

	//B BROADCAST WITHIN ACTIVITY
	{
		public BroadcastReceiver messageReceiver=new BroadcastReceiver()
		{
			 @Override public void onReceive(Context context,Intent intent){}
		};

		// Register it
		registerReceiver(messageReceiver,new IntentFilter(".package..action"));
	}		
			
	//C BROADCAST THAT RECEIVES ANSWERS FROM RECEIVER 
	{
		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
		intent.setAction(c.setIntentAction(c.message_received_code));
		intent.putExtra(c.message_extra,content);
		sendOrderedBroadcast(intent, null, new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				//if broadcast received??
				if ((getResultCode() != Activity.RESULT_CANCELED)||(result==Activity.RESULT_OK)) 
					return;
				//was not received
				//handler here
				
			}}, null, Activity.RESULT_CANCELED, null, null);
			
		//Receiver notifies that it received
		public BroadcastReceiver messageReceiver = new BroadcastReceiver() {
				@Override
				public void onReceive(Context context, Intent intent) {
					//send Reply
					 this.setResultCode(Activity.RESULT_OK);
				}

		}
	}
}
			 
 //Auto complete
{
		_text = (MultiAutoCompleteTextView) findViewById(R.id.multiAutoCompleteTextView1);
		ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,new String[]{"Monday", "Tuesday", "Wednesday"});
		_text.setAdapter(adapter); _text.setTokenizer(new SpaceTokenizer());
	private class SpaceTokenizer implements MultiAutoCompleteTextView.Tokenizer {
		public int findTokenStart(CharSequence text, int cursor) {
			int i = cursor;
			while(i > 0 && text.charAt(i - 1) != ' ') i--;
			while(i < cursor && text.charAt(i) == ' ') i++;
			return i;
		}
		public int findTokenEnd(CharSequence text, int cursor) {
			int i = cursor; int len = text.length();
			while(i < len) {
				if(text.charAt(i) == ' ') return i;
				else i++;
			}
			return len;
		}
		public CharSequence terminateToken(CharSequence text) {
			int i = text.length();
			while(i > 0 && text.charAt(i - 1) == ' ') i--;
			return text;
		}
	}
}
	
// Determine if device is connected to internet
{
networkInfo = ((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE)) .getActiveNetworkInfo();
boolean isConnected = networkInfo != null &&activeNetwork.isConnectedOrConnecting();
boolean isWiFi = networkInfo.getType() == ConnectivityManager.TYPE_WIFI;
}

//vibrate
{
<uses-permission android:name="android.permission.VIBRATE"/>
import android.os.Vibrator;
((Vibrator) getSystemService(Context.VIBRATOR_SERVICE)) .vibrate(500);
}

//Progressbar progressDialog
{
void showProgress(String message) {
	final ProgressDialog progressDialog;
	progressDialog = new ProgressDialog(this);
	progressDialog.setMessage(message);
	progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	progressDialog.setProgress(0);
	progressDialog.setCancelable(false);
	progressDialog.show();
	thread = new Thread() {
		@Override
		public void run() {
			int count = 0;
			while (count < end) {
				try {
					sleep(200);
					count += 5;
					progressDialog.setProgress(count);
				} catch (InterruptedException e) { e.printStackTrace(); }
			}
			//progressDialog.dismiss();
		}
	}.start();
}
}

// Android Search
{
//add the Item to Menu Layout
<item android:id="@+id/action_search"
	  android:orderInCategory="5"
	  android:title="Search"
	  android:icon="@android:drawable/ic_menu_search"
	  app:showAsAction="ifRoom|collapseActionView"
	  app:actionViewClass="android.support.v7.widget.SearchView" />
	  

@Override
public boolean onCreateOptionsMenu(Menu menu){
	getMenuInflater().inflate(R.menu.list_contacts_menu,menu);

	MenuItem searchItem = menu.findItem(R.id.action_search);
	final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
	searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
		@Override
		public boolean onQueryTextSubmit(String key) {
			searchView.clearFocus();
			return true;
		}
		@Override
		public boolean onQueryTextChange(String key) {
			blink("changed");
			return false;
		}
	});
	return true;
}
}

