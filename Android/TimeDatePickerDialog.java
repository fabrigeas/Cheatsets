		TimePickerDialog tp = new TimePickerDialog(this, new OnTimeSetListener() {
			@Override
			public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
				// TODO Auto-generated method stub
				
			}
		}, 0, 0, true);
		tp.show();






public void setDate(View v){
	Calendar c = Calendar.getInstance();
	int y = c.get(Calendar.YEAR);
	int m = c.get(Calendar.MONTH);
	int d = c.get(Calendar.DAY_OF_MONTH);

	DatePickerDialog dp = new DatePickerDialog(this, new OnDateSetListener() {
		public void onDateSet(DatePicker datepicker, int yy, int mm, int dd) {
//				mm +=1;
//				day=dd;
//				month=mm;
//				year=yy;
//				Toast.makeText(getApplicationContext(),""+day+"."+month+"."+year, Toast.LENGTH_SHORT).show();

		}
	}, y, m, d);
	dp.setTitle("Select Date");
	dp.setButton(DialogInterface.BUTTON_NEGATIVE,"Cancel", new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int which) {
			if (which == DialogInterface.BUTTON_NEGATIVE) {
				
			}
		};
	});
	dp.show();
}













//HardCode
public static class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the current time as the default values for the picker
		final Calendar c = Calendar.getInstance();
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);

		// Create a new instance of TimePickerDialog and return it
		return new TimePickerDialog(getActivity(), this, hour, minute,DateFormat.is24HourFormat(getActivity()));
	}

	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		// Do something with the time chosen by the user
		classHour=hourOfDay;
		classMin=minute;
	}
}

in The Wrapper Class

DialogFragment tp = new TimePickerFragment();
tp.show(getFragmentManager(), "timePicker");

// 2 DatePickerDialog
public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the current date as the default date in the picker
		final Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);

		// Create a new instance of DatePickerDialog and return it
		return new DatePickerDialog(getActivity(), this, year, month, day);
	}

	public void onDateSet(DatePicker view, int year, int month, int day) {
		// Do something with the date chosen by the user
//			Toast.makeText(this,"h:"+hour+"m"+minut, Toast.LENGTH_SHORT).show();
		classDay=day;
		classMonth=month;
		classYear=year;
	}
}



DialogFragment dp = new DatePickerFragment();
dp.show(getFragmentManager(), "datePicker");