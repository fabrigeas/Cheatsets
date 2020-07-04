# [Express](https://expressjs.com/en/4x/api.html)

## Express application generator (creates an express app skeleton)

Istall express generator

    npm install -g express-generator // install the express generator as global package
    npx express-generator

Create an app

    express --view=pug myapp // create an app
    cd myapp
    npm install
    npm init // to re init the package.json
    set DEBUG=myapp:* & npm start

nodemon (restart the app on each save)

    npm install --save nodemon

    // package.json
    "scripts": {
      "start": "nodemon ./bin/www"
    },

[pug view engine](https://pugjs.org/language/includes.html))
[html to pug](https://html-to-pug.com/)

### Hello world

app.js

    const express = require('express')
    const app = express()
    const port = 3000

    app.get('/', (req, res) => res.send('Hello World!'))

    app.listen(port, () => console.log(`Example app listening on port ${port}!`))

Start the app

    node app.js
    http://localhost:3000/

## [Routing](https://expressjs.com/en/guide/routing.html)

    app.get('/', function (req, res) {
      res.send('Hello World!')
    })

    app.post('/', function (req, res) {
      console.log(req.body)
      res.send('Got a POST request')
    })

    app.put('/user', function (req, res) {
      res.send('Got a PUT request at /user')
    })

    app.delete('/user', function (req, res) {
      res.send('Got a DELETE request at /user')
    })

### Route Handlers

    app.get('/',
      function (req, res, next) {
        console.log('middleware 1');
        res.send({success: false, message: "auth failed}) // you can either return here or 
        next(); // call next to forward the request to the next handler
      }, 
      function (req, res, next) {
        console.log("middleware 2")
        next();
      },
      function (req, res, next) {
        console.log("middleware 3")
        next();
      },
      function (req, res) {
        console.log('middleware 4')
        res.send('middleware 4')
      }
    )

  app.method(path, [middleware1, middlewareN,])

### app.route()

    app.route('/book')
      .get(function (req, res) {
        res.send('Get a random book')
      })
      .post(function (req, res) {
        res.send('Add a book')
      })
      .put(function (req, res) {
        res.send('Update the book')
      })

### express.Router (create modular, mountable route handlers)

routes.js

    var express = require('express')
    var router = express.Router()

    // middleware that is specific to this router
    router.use(function timeLog (req, res, next) {
      console.log('Time: ', Date.now())
      next()
    })

    router.get('/', function (req, res) {})
    router.get('/about', function (req, res) {})
    router.post('/about', function (req, res) {})

    module.exports = router;

app.js

    var routes = require('./routes')
    app.use('/routes', routes)

### Route parameters

## Serving static files in Express

    app.use(express.static('public')); // serve static files ound in /public
    app.use(express.static(path.join(__dirname, 'public')));
    app.use(express.static('public')) // you can use multiple static asset dirs
    app.use(express.static('files'))

To create a virtual path prefix (where the path does not actually exist in the file system)

    app.use('/static', express.static('public'))

    http://localhost:3000/static/images/kitten.jpg
    http://localhost:3000/static/css/style.css
    http://localhost:3000/static/js/app.js
    http://localhost:3000/static/images/bg.png
    http://localhost:3000/static/hello.html

## [Middleware](https://expressjs.com/en/guide/using-middleware.html) (function that has access to req, res, next)

    var express = require('express')
    var app = express()

    // This middleware runs for each request
    app.use(function (req, res, next) {
      console.log('LOGGED')
      next(); // fi next is not called, the route will not be executed
    });


    // This middleware updates the req by appending a new prop
    app.use(function (req, res, next) {
      req.requestTime = Date.now()
      next();
    });

    app.get('/', function (req, res) {
      res.send('Hello World!')
    })

    app.listen(3000);

### Application-level middleware (express().use(middleware))

    var app = express()

    app.use(function (req, res, next) {
      console.log('Time:', Date.now())
      next()
    });

### Router-level middleware (express.Router().use(middleware))

    var app = express()
    var router = express.Router()

    // a middleware function with no mount path. This code is executed for every request to the router
    router.use(function (req, res, next) {
      console.log('Time:', Date.now())
      next()
    })

    // a middleware sub-stack shows request info for any type of HTTP request to the /user/:id path
    router.use('/user/:id', function (req, res, next) {
      console.log('Request URL:', req.originalUrl)
      next()
    }, function (req, res, next) {
      console.log('Request Type:', req.method)
      next()
    })

    // a middleware sub-stack that handles GET requests to the /user/:id path
    router.get('/user/:id', function (req, res, next) {
      // if the user ID is 0, skip to the next router
      if (req.params.id === '0') next('route')
      // otherwise pass control to the next middleware function in this stack
      else next()
    }, function (req, res, next) {
      // render a regular page
      res.render('regular')
    })

    // handler for the /user/:id path, which renders a special page
    router.get('/user/:id', function (req, res, next) {
      console.log(req.params.id)
      res.render('special')
    })

    // mount the router on the app
    app.use('/', router)

### Error-handling middleware (has 4 parameters, )

    app.use(function (err, req, res, next) {
      console.error(err.stack)
      res.status(500).send('Something broke!')
    })

### Configurable middleware (Middleware that take paramentes)

    function myParameterizedMiddleware(options) {
      return function (req, res, next) {
        // Implement the middleware function based on the options object
        next()
      }
    }

    app.use( myParameterizedMiddleware(someParameters));

## Error Handling

    app.get('/', function (req, res) {
      throw new Error('BROKEN') // Express will catch this on its own.
    })

Errors returned from asynchronous functions invoked by route handlers and middleware, must be passed to next()

    app.get('/', function (req, res, next) {
      fs.readFile('/file-does-not-exist', function (err, data) {
        if (err) {
          next(err) // anything except 'route' will be considered ThrownError
        } else {
          res.send(data)
        }
      })
    })

Throwing Errors Asynchronously

    app.get('/', function (req, res, next) {
      setTimeout(function () {
        try {
          throw new Error('BROKEN') //Async errors can only be caught in try or Promise
        } catch (err) {
          next(err)
        }
      }, 100)
    })

    app.get('/', function (req, res, next) {
      Promise.resolve().then(function () {
        throw new Error('BROKEN')
      }).catch(next) // Errors will be passed to Express.
    })

    app.get('/', [
      function (req, res, next) {
        fs.readFile('/maybe-valid-file', 'utf-8', function (err, data) {
          res.locals.data = data
          next(err) // next(null) || next(actuall error)
        })
      },
      function (req, res) {
        res.locals.data = res.locals.data.split(',')[1]
        res.send(res.locals.data)
      }
    ])

### Error Handler Middleware

    app.use(bodyParser.json())

    //file write failed error handler
    app.use(function (err, req, res, next) {
      if (error.isFileWriteFailed) {
        res.status(500).send({ error: 'Something failed!' })
      } else {
        next(err)
      }
    });

    // another error handler
    app.use(function (err, req, res, next) {
      if (req.xhr) {
        res.status(500).send({ error: 'Something failed!' })
      } else {
        next(err)
      }
    });

    // still another error handler
    app.use(function (err, req, res, next) {
      if (someTypeOfError) {
        res.status(500).send({ error: 'someTypeOfError!' })
      } else {
        next(err)
      }
    });

    // catch-all has no next because it handles all
    app.use(function (err, req, res, next) {
      res.status(500)
      res.render('error', { error: err })
    })

### The default error handler

Error handling middleware are defined last after app.use

  function defaultErrorHandler (err, req, res, next) {
    if (res.headersSent) {
      return next(err)
    }
    res.status(500)
    res.render('error', { error: err })
  }

  app.use(defaultErrorHandler); // this handles all the errors encountered by express

## [Database integration](https://expressjs.com/en/guide/database-integration.html)

## Misc

Debugging: start the app as follows

  set DEBUG=express:* & node index.js

## Cookies

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
    const config = require('path/to/config)

    app.use(session({
        key: config.session.key',
        secret: config.session.ecret',
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

## Passport