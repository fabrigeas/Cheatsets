#

[Tutorials Point](https://www.tutorialspoint.com/expressjs)
 
[Moz:lla](https://developer.mozilla.org/en-US/docs/Learn/Server-side/Express_Nodejs)
 
[html to pug](https://html-to-pug.com/)

[pug](https://pugjs.org/language/attributes.html)

Install Express generator

    npm install express-generator -g
 
 create a new project
 
     express projectname 
     express --view=pug projectname
     cd project
     npm install
     npm init
 
 nodemon to auto restart the server on filse save
 
     npm install --save-dev nodemon
 
 start the server
     
     npm ./bin/www
     nodemon ./bin/www
     http://localhost:3000/ # the app
 
 pug view engine
 
     https://pugjs.org/language/includes.html
     https://html-to-pug.com/
 
 serve, load static files like css and images
 
    app.use(express.static(path.join(__dirname, 'public')));
    app.use(express.static('public'));
    app.use(express.static('images'));
    
    link(href="stylesheets/carousel.css" rel="stylesheet")

 ## cookies
 
     npm install --save cookie-parser
     var cookieParser = require('cookie-parser');
     app.use(cookieParser());
     app.get('/', function(req, res){
         console.log('Cookies: ', req.cookies);// check if cookies
         res.cookie('name', 'express').send('cookie set'); //Sets name = express
         res.cookie(name, 'value', {expire: 360000 + Date.now()}); // send expiring cookie
         res.cookie(name, 'value', {expire: 360000 + Date.now()}); // send expiring cookie
         res.cookie(name, 'value', {maxAge: 360000});
         res.clearCookie('foo');
     });
     
 ## sessions
  npm install --save express-session
  
    app.get('/', function(req, res){
        if(req.session.page_views){
            req.session.page_views++;
            res.send("You visited this page " + req.session.page_views + " times");
        } else {
            req.session.page_views = 1;
            res.send("Welcome to this page for the first time!");
        }
    });
    
 ## i18n
 
 jade
 
    if(lang === "en")
        li#language-french.nav-item
          a.nav-link(href="/?lang=fr" title="Francais") Fr
    else
        li#language-english.nav-item
          a.nav-link(href="/?lang=en" title="English") En
  
  app.js
  
    function initI18n (app) {
		const i18n = require("i18n-express");
		app.use(
			i18n({
				translationsPath : path.join(__dirname, "i18n"),
				siteLangs :["en", "fr"],
				textsVarName :"i18n",
				defaultLang :"en",
				paramLangName : "lang",
				cookieLangName :"i18n"
			}));

		app.get("/lang", (req, res) => {
			req.cookies.i18n = req.query.i18n;
			// console.log(req.cookies.i18n);
			console.log(res.cookie("i18n").lang);
			res.redirect("/");
		});
	}
 
     
 /i18n/en.json
     
     {
          "title":"Express tutorial",
          "welcome":"Welcome",
     }
     
     
## Authentication

    var session = require('express-session');
    app.use(session({
        key: 'your_key',
        secret: 'somerandonstuffs',
        resave: false,
        saveUninitialized: false,
        cookie: {
            expires: 600000
        }
    }));
    
    app.get('/todo', checkLoginstatus("todo"), function(req,res) { res.render('todo/todo')  });
    
    function checkLoginstatus(destination) {
    return function(req, res, next) {
      if (req.session && req.session.User) {
        return next();
      } 
      else {
         res.cookie('destination', destination);
         res.redirect('/signin');
       }
     }
   }
      
    app.post('/signin/:destination', function(req,res) { 

      let user  = req.body;
      
      MongoClient.connect(url, function(err, db){
        if (err) throw err;

        db.collection("users").findOne( { Email : user.Email},(function(err, found) {
          if (err) throw err;

          if(found){
            if(user.Email === found.Email){
              if(user.Password === found.Password){ 
                req.session.User = found;
                res.status(200).redirect(`/${req.params.destination}`);
              }
              else{
                res.status(500).send(`The password doesn't correspond to the Username '${user.Email}'given`);
              }
            }
          }
          else{
            res.status(404).send("No User exist with the given Username");
          }

        }));
        db.close();

      });
    });
    
    app.get('/logout', (req, res) => {
        res.clearCookie('your_key');
        res.redirect('/');
    });

## REST API mongodb

		const mongodbUrl = "mongodb://localhost:27017/library";
		const databaseName = "library";
		const mongodb = require("mongodb");

		var dbClient = mongodb.MongoClient.connect(mongodbUrl, {
			useNewUrlParser: true
		}).then( client => client.db(databaseName) );

		//This module exports mongodb crud methods
		module.exports = function () {

			this.ObjectID  = mongodb.ObjectID;
			
		/** This funxtion inserts a document into the database
		 *
		 * @param {*} collection
		 * @param {*} document
		 */
			this.insertOne = function (collection, document) {
				return new Promise( (resolve, reject) => {
					dbClient
						.then( db => db.collection(collection).insertOne(document))
						.then( result => resolve(result))
						.catch( error =>reject(error));
				});
			};

			/** Makes and Update request to the MongoDb, then sends the result, resolve/reject
			*
			* @param {String} collection name of the collection
			* @param {Object} document object to be updated
			*/
			this.updateOne = function (collection, document) {
				return new Promise( (resolve, reject) =>{
					dbClient
						.then( db => db.collection(collection).updateOne({ _id: document._id}, { $set: document }, { upsert: true }) )
						.then( result => resolve(result))
						.catch( error =>reject(error));
				});
			};

			/** Makes and Updatemany request to the MongoDb, then sends the result, resolve/reject
			*
			* @param {String} collection name of the collection
			* @param {Object} document object to be updated
			*/
		
			/** mongoDb UpdateMany
			*
			* @param {*} collection
			* @param {*} query
			* @param {*} value
			*/
			this.updateMany = function (collection, query, value) {
				return new Promise( (resolve, reject) =>{
					dbClient
						.then( db => db.collection(collection).updateMany(query, value) )
						.then( result => resolve(result))
						.catch( error =>reject(error));
				});
			};
		
			/** This function deletes the given document from the given collection
			*
			* @param {string} collection
			* @param {Object} document
			*/
			this.deleteOne = function (collection, query) {
				return new Promise( (resolve, reject) =>{
					dbClient
						.then( db => db.collection(collection).deleteOne(query) )
						.then( result => resolve(result))
						.catch( error =>reject(error));
				});
			};

			/** This function deletes the given document from the given collection
			*
			* @param {string} collection
			* @param {Object} query
			*/
			this.findOne = function (collection, query) {
				return new Promise( (resolve, reject) =>{
					dbClient
						.then( db => db.collection(collection).findOne(query) )
						.then( result => resolve(result))
						.catch( error =>reject(error));
				});
		
			};

			/** Calls the MongoDB findquery
			*
			* @param {*} collection
			* @param {*} query
			*/
			this.find = function (collection, query) {


				return new Promise( (resolve, reject) =>{
					dbClient
						.then( db => db.collection(collection).find( query ? query :{}).toArray() )
						.then( result => resolve(result))
						.catch( error =>reject(error));
				});
			};
		};

in app.js

    require("./mongodb-helpers")();
    
    router.get("/", function (req, res) {
	    find("books", {"title" : {$regex : new RegExp(req.query.keyword, "i") }} ).then( result => res.json(result) );
    });


## Facebook login

	/** Initialize the sign in with facebook module
	 *
	 * @param {app} app
	 */
	function initFacebookPassport (app) {

		const passport		 		= require("passport"),
			FacebookStrategy	    = require("passport-facebook").Strategy;

		app.use(passport.initialize());
		app.use(passport.session());
	
		passport.use(
			new FacebookStrategy({
				clientID: facebookStrategyClientID, // imported from a config file
				clientSecret: facebookStrategyClientSecret, // imported from a config file
				callbackURL: "/auth/facebook/callback",
				profileFields: ["id", "displayName", "photos", "email", "address"]
			},
			async function (accessToken, refreshToken, profile, callback) {
	
				let user = UserModel(profile);
	
				user.email 			= profile.emails[0].value;
				user.userName   	= profile.displayName;
				user.firstName   	= profile.name.givenName || profile.displayName;
				user.lastName   	= profile.name.famillyName || profile.displayName;
				user.facebookId 	= profile.id;
				user.password    	= accessToken;
				user.token    		= accessToken;
	
				if(await UserModel.findOne({facebookId: user.id})) {
					return callback(null, user);
				} else {
					return callback(null, await UserModel.create(user));
				}
			}
		));
	
		passport.serializeUser( (user, callback) => callback(null, user) );
		passport.deserializeUser( (obj, callback) => callback(null, obj) );
	
		app.get("/auth/facebook", passport.authenticate("facebook"));
		
		app.get("/auth/facebook/callback", passport.authenticate("facebook", { failureRedirect: "/login" }), function (req, res) {
			res.redirect("/");
		});
	
	}

