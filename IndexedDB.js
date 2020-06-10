//https://developer.mozilla.org/en-US/docs/Web/API/IndexedDB_API

let MyIndexedDB = class {

    version = 1;
    
    constructor(databaseName) {
      
      if (!window.indexedDB) {
        alert("Your browser doesn't support a stable version of IndexedDB. Such and such feature will not be available.");
      }
  
      this.databaseName = databaseName;
  
    };
  
    /** Create an object store (which is equivalent to creating a table|collection)
     * 
     * @param {String} objectStoreName The name of the KeyStore(collection, table...)
     * @param {Array} indices An array of searcheable indices containing the tuples name: String, unique: Boolean
     * @param {String} keyPath An optional primary key. If this value is absent, autoicrement will be used instead
     * 
     * This is the only place where you can alter the structure of the database,
     * create and delete object stores (Object store = table) and build and remove indices.
     * 
     * To create an object store that uses a 'key' (primary key), simply set the key flag in the createObjectStore()
     * To create an object store that uses 'generator' instead of primary key, simply  replace { keyPath: "x" } replace with { autoIncrement : true }
     */
    createObjectStore (objectStoreName, indices, keyPath) {
  
      const request = indexedDB.open(this.databaseName, this.version++);
  
      return new Promise( (resolve,reject) => {
  
        request.onupgradeneeded = event => {
          let db = event.target.result;
  
          let option      = keyPath ? {keyPath: keyPath} : { autoIncrement : true },
              objectStore = db.createObjectStore(objectStoreName, option);
  
          indices.forEach( index => objectStore.createIndex(index.name,  index.name, { unique: index.unique }) );
  
          db.onversionchange = () => db.close() ;
  
          resolve(db)
        }
  
        request.onerror = event => reject(event)
  
        request.onblocked  = () => {
          db.close()
        }
      })
    };
  
    /** Save an Object into the Object Store
     * 
     * @param {String} objectStoreName The name of the object store (collection) where the data will be inserted
     * @param {Object} data The object to be stored
     */
    insertOne (objectStoreName,data) {
      return this.getObjectStore(objectStoreName).then( objectStore => objectStore.add(data));
    }
  
    /** Store an array of objects into the indexedDB
     * 
     *@param {String} objectStoreName The name of the object store (collection) where the data will be inserted
     * @param {Array} data 
     */
    insertMany (objectStoreName, data) { 
      return this.getObjectStore(objectStoreName).then( objectStore => data.forEach(i => objectStore.add(i)));
    }
  
    /** Retrive the data from the object store with the given primary key
     * 
     * @param {String} objectStoreName 
     * @param {*} primaryKey 
     */
    findOne (objectStoreName, primaryKey) {
      return new Promise( (resolve,reject) => {
        this.getObjectStore(objectStoreName).then( objectStore => {
          let request = objectStore.get(primaryKey);
          request.onsuccess = event => resolve (event.target.result) 
          request.onerror = event => reject (event.target) 
        });
      })
    }
  
    /** Find and object from Object store given the index value.
     * 
     * @param {String} objectStoreName The name of the Object Store to be queried
     * @param {String} index The name of the index. This Index must have been initialized during the execution on MyIndexedDatabase.creatObjectStore
     * @param {String} value The value
     * Example: myIndexedBD.find("Users", "name", "John Doe")// find a user from the collection Users where name = "John Doe"
     */
    findbyIndex (objectStoreName,index, value) { 
      
      return new Promise( (resolve,reject) => {
        this.getObjectStore(objectStoreName).then( objectStore => {
          let request = objectStore.index(index).get(value);
          request.onsuccess = event => resolve (event.target.result) 
          request.onerror = event => reject (event.target) 
        });
      })
    }
  
    /** Get all the objects from an Object Store
     * 
     * @param {*} objectStoreName 
     */
    find (objectStoreName) { 
      return new Promise( (resolve,reject) => {
        this.getObjectStore(objectStoreName).then( objectStore => {
          let request = objectStore.getAll();
          request.onsuccess = event => resolve (event.target.result) 
          request.onerror = event => reject (event.target) 
        });
      })
    }
  
    /** Delete an Object from a given Object Store
     * 
     * @param {String} objectStoreName The name of the Object Store to be queried
     * @param {*} key 
     */
    remove (objectStoreName, key) { 
      return new Promise( (resolve,reject) => {
        this.getObjectStore(objectStoreName).then( objectStore => {
          let request = objectStore.delete(key)
          request.onsuccess = event => resolve (event.target) 
          request.onerror = event => reject (event.target) 
        });
      })
  
    }
    /** Update an Objec from a specific Object Store
     * 
     * @param {String} objectStoreName The name of the Object Store to be queried
     * @param {*} data 
     */
    update (objectStoreName, data) { 
      return new Promise( (resolve,reject) => {
        this.getObjectStore(objectStoreName).then( objectStore => {
          let request = objectStore.put(data);
          request.onsuccess = event => resolve (event.target.result) 
          request.onerror = event => reject (event.target) 
        });
      })
    }
  
    /** Get an object store. Object store is the equivalent ot a collection in MongoDb or a table in MySql
     * 
     * @param {String} objectStoreName The name of the Object store to be extracted
     */
    async getObjectStore (objectStoreName) { 
      let request = indexedDB.open(this.databaseName);
  
      return await new Promise( (resolve, reject) => {
        request.onsuccess = event =>  resolve(event.target.result.transaction([objectStoreName], "readwrite").objectStore(objectStoreName));
        request.onerror = event => reject(event);
      })
    }
  
  }
  
  test();
  
  function test() {
  
    let myIndexedDB = new MyIndexedDB("HelloIndexdDb");
  
    let objectStoreName   = "Users",
        objectStoreName2  = "users.duplicate"
        keyStoreIndices   = [
                              {name: "name", unique: false},
                              {name: "email", unique: false},
                              {name: "username", unique: false},
                              {name: "gender", unique: false},
                            ],
        keyStoreKeyPath   = "username",
        manyUsers         = [
                              {name: "Snow", email: "feugang@gmail.com", username: "snow", gender: "male"},
                              {name: "Sersey", email: "Sersey@gmail.com", username: "sersey", gender: "female"},
                              {name: "Jimmy", email: "jimmy@gmail.com", username: "jimmy", gender: "female"},
                            ];
    
    myIndexedDB.createObjectStore(objectStoreName, keyStoreIndices, keyStoreKeyPath).catch( e => console.log(e))
    myIndexedDB.createObjectStore(objectStoreName2, keyStoreIndices, keyStoreKeyPath).catch( e => console.log(e))
    myIndexedDB.createObjectStore("objectStoreName2", keyStoreIndices, keyStoreKeyPath).catch( e => console.log(e))
    
    
    myIndexedDB.insertOne(objectStoreName,{name: "Lanister", email: "John@gmnail.com", username: "lanister", gender: "male", other: "Code developper"})
    myIndexedDB.insertMany(objectStoreName, manyUsers)
    myIndexedDB.find(objectStoreName,"name","Snow").then (user => console.log( user))
    myIndexedDB.findOne(objectStoreName,"snow").then (user => console.log( user) )
    myIndexedDB.findbyIndex(objectStoreName,"name","Sersey").then (user => console.log( user) )
    myIndexedDB.remove(objectStoreName,"jimmy").then (user => console.log( user) )
    myIndexedDB.update(objectStoreName,{name: "King of the throne", email: "jimmy@gmail.com", username: "jimmy", gender: "female"}).then (result => console.log( result))
  }