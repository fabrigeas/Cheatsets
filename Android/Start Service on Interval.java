//THE BROADCASTER
public class AlarmReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Intent background = new Intent(context, BackgroundService.class);
		context.startService(background);
	}
}


// THE SERVICE
public class BackgroundService extends Service {
 
    private boolean isRunning;
    private Context context;
    Thread backgroundThread;
    
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
 
    @Override
    public void onCreate() {
        this.context = this;
        this.isRunning = false;
        this.backgroundThread = new Thread(myTask);
    }
 
    private Runnable myTask = new Runnable() {
        public void run() {
//        	Toast.makeText(context, "haha", Toast.LENGTH_SHORT).show();
        	
		       NotificationManager notificationManager =(NotificationManager) 
		       getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE); 
		        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext())
		        .setAutoCancel(true)
		        .setOngoing(false)
		        .setContentText("bg svs br")
		        .setContentTitle("bg svs br")
		        .setSmallIcon(R.drawable.tchat_icon)
		        .setAutoCancel(true)
		        .setTicker("bg svs br");
		        notificationManager.notify((int) System.currentTimeMillis(), notificationBuilder.build()); 
		        
            stopSelf();
        }
    };
 
    @Override
    public void onDestroy() { 
        this.isRunning = false;
    }
 
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(!this.isRunning) {
            this.isRunning = true;
            this.backgroundThread.start();
        }
        return START_STICKY;
    }
 
}


//THE MANIFEST
        <service android:name=".BackgroundService" />
        <receiver android:name=".AlarmReceiver" />
		
		
//THE CINITIATOR
Intent alarm = new Intent(getApplicationContext(), AlarmReceiver.class);
boolean alarmRunning = (PendingIntent.getBroadcast(getApplicationContext(), 0, alarm, PendingIntent.FLAG_NO_CREATE) != null);
if(alarmRunning == false) {
	PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, alarm, 0);
	AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
	alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(),
			1000 * 20 * 1, pendingIntent);
}

