    
    // icons
    https://romannurik.github.io/AndroidAssetStudio/icons-launcher.html
    
    NotificationCompat.Builder notificationBuilder;
    NotificationManagerCompat notificationManager;
    int notificationId = 1234567;
    private static final String CHANNEL_ID = "DAILYTASKNOTIFICATIONCHANNELID";

    Intent thisIntent;
    PendingIntent pendingIntent;
    
    private void initNotification() {
        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ) {

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, getString(R.string.channel_name), NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription( getString(R.string.channel_description) );
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

            thisIntent = new Intent(this, MainActivity.class);
            thisIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            pendingIntent = PendingIntent.getActivity(this, 0, thisIntent, 0);

            notificationBuilder = new NotificationCompat.Builder( getApplicationContext(), CHANNEL_ID)
                    .setSmallIcon(R.drawable.fk_plan)
                    .setContentTitle( getText(R.string.time_to_plan) )
                    .setContentText( getText(R.string.time_to_plan_detail) )
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);

            initBroadcastReceivers();
            initAlarmManager();
        }

    }
    
    private void initBroadcastReceivers() {
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                notificationManager = NotificationManagerCompat.from( getApplicationContext() );
                notificationManager.notify(notificationId, notificationBuilder.build());
                Log.i("tasks", new Date().toString() );
            }
        },new IntentFilter("TIME_TO-PLAN"));
    }
    
    private void initAlarmManager(){

        Intent intentBroadcastReceiver = new Intent();
        intentBroadcastReceiver.setAction("TIME_TO-PLAN");
        
        // Today at 13:29
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 13);
        calendar.set(Calendar.MINUTE, 29);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intentBroadcastReceiver, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                1000 * 60 * 5, pendingIntent);
    }