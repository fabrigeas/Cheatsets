package fab.app.mybalancesheet;

import java.util.LinkedList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {
	private static final int database_VERSION = 1;
	private static final String database_NAME = "fabrigeas";
	
	
	private static final String tabeleName = "entries";
	
	private static final String id		 = "id";
	private static final String date 	 = "date";
	private static final String amount 	 = "amount";
	private static final String detail 	 = "detail";
	private static final String other 	 = "other";
	private static final String priority = "priority";

	private static final String[] COLUMNS = { id,date,amount,detail,other,priority};
	
	Context context;
	
	public Database(Context context) {
		super(context, database_NAME, null, database_VERSION);
		this.context=context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("DROP TABLE IF EXISTS "+tabeleName);
		String create_tableOp = "CREATE TABLE "+tabeleName+
				" ("+ 
				id+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
				date+" 		TEXT, " +
				amount+" 	TEXT, " +
				detail+" 	TEXT, " +
				other+" 	TEXT, " +
				priority+" 	TEXT"
				
				+ ")";
		
		db.execSQL(create_tableOp);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS "+tabeleName);
		this.onCreate(db);
	}

	public void insertOperation(Entry operation) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		
		values.put(date, operation.getDate());
		values.put(amount, operation.getAmount());
		values.put(detail, operation.getDetail());
		values.put(other, operation.getOther());
		values.put(priority, operation.getPriority());

		db.insert(tabeleName, null, values);
	}

	public Entry getOperation(int id) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(tabeleName,COLUMNS, " id = ?", new String[] { String.valueOf(id) }, null, null, null, null);

			
		if (cursor != null)
			cursor.moveToFirst();

		Entry operation = new Entry();
		operation.setId(Integer.parseInt(cursor.getString(0)));
		operation.setDate(cursor.getString(1));
		operation.setAmount(cursor.getString(2));
		operation.setDetail(cursor.getString(3));
		operation.setOther(cursor.getString(4));
		operation.setPriority(cursor.getString(5));

		return operation;
	}

	public List<Entry> getAllLOperations(String param,String value) {
		
		
		List<Entry> list = new LinkedList<Entry>();
		SQLiteDatabase db = this.getWritableDatabase();

		
		Cursor cursor=null;
		//RawQuery
	if(param.equals("all")){
		String query = "SELECT * FROM "+tabeleName+" ORDER BY "+other;
		 cursor = db.rawQuery(query, null);
	}
		

		
		//where clause
//		Cursor cursor =db.query(
//				tabeleName,
//				COLUMNS,
//				date+"=?",
//				new String[] { "1.3.2016" },
//				null,
//				null,
//				null);
		
		
		
//GroupBy and OrderBY
	else{	
		param=priority+"=?";
		cursor =db.query(
				tabeleName,
				COLUMNS,
				param,
				new String[] { value },
				null,
				null,
				other+" ASC");
	}
		
	
		
		
		
		
		Entry operation = null;
		if (cursor.moveToFirst()) {
			do {
				operation = new Entry();
				
				operation.setId(Integer.parseInt(cursor.getString(0)));
				operation.setDate(cursor.getString(1));
				operation.setAmount(cursor.getString(2));
				operation.setDetail(cursor.getString(3));
				operation.setOther(cursor.getString(4));
				operation.setPriority(cursor.getString(5));
				
				list.add(operation);
			} while (cursor.moveToNext());
		}
		return list;
	}

	public int updateOperation(Entry entry) {
		SQLiteDatabase db = this.getWritableDatabase();
	
		ContentValues values = new ContentValues();
		values.put(date, entry.getDate());
		values.put(amount, entry.getAmount());
		values.put(detail, entry.getDetail());
		values.put(other, entry.getOther());
		values.put(priority, entry.getPriority());

		int i = db.update(tabeleName, values, id + " = ?", new String[] { String.valueOf(entry.getId()) });
		return i;
	}

	public void deleteOperation(Entry operation) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(tabeleName, id + " = ?", new String[] { String.valueOf(operation.getId()) });
	}
}
