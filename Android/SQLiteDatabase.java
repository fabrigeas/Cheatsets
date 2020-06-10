
//LIKE CLAUSE
Cursor cursor = db.query(
	items_table,
	COLUMNS,// NULL= selec +
	type+" LIKE ?",//
	new String[]{"%"+key+"%"},
	null,
	null,
	null);


//multiple clauses
String[] tableColumns = new String[] {"column1", "(SELECT max(column1) FROM table2) AS max"};
String whereClause = "column1 = ? OR column1 = ?";
String[] whereArgs = new String[] {"value1", "value2"};
String orderBy = "column1";

Cursor c = sqLiteDatabase.query(
	"table1", 
	tableColumns,
	whereClause,
	whereArgs,
    null,
	null, 
	orderBy);

// since we have a named column we can do
int idx = c.getColumnIndex("max");
is equivalent to the following raw query

String queryString =
    "SELECT column1, (SELECT max(column1) FROM table1) AS max FROM table1 " +
    "WHERE column1 = ? OR column1 = ? ORDER BY column1";
sqLiteDatabase.rawQuery(queryString, whereArgs);

//OR
Cursor cursor = db.query(
	items_table,
	COLUMNS,
	type+" LIKE ? OR "+title+" LIKE ? ",//
	new String[]{"%"+key+"%","%"+key+"%"},
	null,
	null,
	null);