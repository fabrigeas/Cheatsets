    <item
        android:id="@+id/action_search"
          android:orderInCategory="5"
          android:title="Search"
          android:icon="@android:drawable/ic_menu_search"
          app:showAsAction="ifRoom|collapseActionView"
          app:actionViewClass="android.support.v7.widget.SearchView" />

	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		getMenuInflater().inflate(R.menu.mycontacts_menu,menu);

		final MenuItem searchItem = menu.findItem(R.id.action_search);
		final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
		searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
			@Override
			public boolean onQueryTextSubmit(String key) {
				search(key);
				searchView.clearFocus();
				return true;
			}

			@Override
			public boolean onQueryTextChange(String key) {

				search(key);
				return false;
			}
		});
		return true;
	}
	
	
	private void search(String key){
		contacts= new ArrayList<>();
		List<User>users=db.searchUser(owner_id,key);
		for(int i=0;i < users.size();i++)
			contacts.add(users.get(i));

		ListView listview=(ListView)findViewById(R.id._mycontactsList);
		ContactsAdapter mAdapter=new ContactsAdapter(MyContacts.this,contacts);
		listview.setAdapter(mAdapter);
		listview.setOnItemClickListener(itemClicked);
		
		if(key.length()>1&&contacts.size()<1)
			text("'"+key+ "' not found!");
	}
