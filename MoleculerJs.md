  <!-- markdownlint-disable MD024 -->

# MoleculerJs: A Microservice for NodeJs (A framework consisting of services managed by a Broker)

    npm i moleculer --save

    npm i moleculer-cli -g
    moleculer init project project-name
    cd project-name
    npm run dev

## [Broker: Manage services](https://moleculer.services/docs/0.13/broker.html)

- [BrokerOptions](https://moleculer.services/docs/0.13/broker.html#Broker-options)
- [ServiceSchema](https://moleculer.services/docs/0.13/services.html)
- [RunnerOptions](https://moleculer.services/docs/0.13/runner.html#Options)

### Using a broker

    // app.js

    const { ServiceBroker } = require("moleculer");

    const brokerOptions = {}; // See broker options
    const broker = new ServiceBroker(brokerOptions);
    const serviceSchema = {name, actions}; // See Service Schema
    
    broker.createService(serviceSchema);// create a single service
    broker.loadService("path/to/service.js"); // load a single service
    broker.loadServices();        // Load every *.service.js file from the "./services" folder (including subfolders)
    broker.loadServices("./");    // Load every *.service.js file from the current folder (including subfolders)
    broker.loadServices("./services", "user*.service.js"); // Load every user*.service.js file from the "./services"
    broker.loadServices("./services", "*"); // Load all the services in the /service folder. Typically what moleculer-runner does
    broker.start();

    nodemon main.broker.js // start the broker task

### [moleculer-runner](https://moleculer.services/docs/0.13/runner.html)

  ./services

      user.service.js
      tasks.service.js
      projects.service.js

  moleculer.config.json

    module.exports = {
      BrokerOptions
    }

 package.json // Use RunnerOptions

    {
      "scripts": {
        "dev": "moleculer-runner --repl --hot --config moleculer.config.js services", //--configuration file =moleculer.config.json, services folder = services
        "start": "moleculer-runner --instances=4 services"
      }
    }

    // start moleculer-runner
    npm dev

## [Services](https://moleculer.services/docs/0.13/services.html):  microservices, define actions, subscribe to events

### Skeleton

    module.exports = {
      name, // The name of the service
      settings,
      mixins,
      actions,
      event,
      created(){}, 
      created(){}, 
      stopped(){}
    }

### Settings (Stores props)

    broker = new ServiceBroker({

      settings: {
          $secureSettings: ["transport.auth.user", "transport.auth.pass", {apiKey}], // visiblility: this.settings.apiKey
          from: "sender@moleculer.services",
          transport: {
            service: 'gmail',
            auth: {
              user: 'gmail.user@gmail.com',
              pass: 'yourpass'
            }
          }
        }  

    });

### Mixins (Mixins will be appended to the schema and underwrite schema props)

    broker = new ServiceBroker({

      // the properties of SomeOtherService and ApiGwService will be appended to this schema    
      mixins: [SomeOtherService, ApiGwService], 
      settings: {
        // properties set here will overwrite the properties inherited from mixins
      }

    });   

### Actions (callable/public methids of the service called : broker.call, ctx.call)

    module.exports = {

      actions: {
        add(ctx) { return Number(ctx.params.a) + Number(ctx.params.b) },
        sub(ctx) { return Number(ctx.params.a) - Number(ctx.params.b) },
        
        mult: {
          cache: false,
          params: {
            a: "number",
            b: "number"
          },
          handler(ctx) { //handler must be implemented because mult is an object
            // The action properties are accessible as `ctx.action.*`
            if (!ctx.action.cache){
                return Number(ctx.params.a) * Number(ctx.params.b);
            } else {
              return ...
            }
          }
        },

        async getPostbyId(ctx) {
          // call other services using ctx,call
          const post = await ctx.call("posts.get", { id: ctx.params.id});
          const user = await ctx.call("users.get", { id: post.author });
          if (user) {
              post.author = user;
          }
          return post;
        }
      }

    }

### Events (Here you subscribe to events emitted by other services)

    module.exports = {

      events: {

        // Subscribe to "user.created" event
        "user.created"(payload) {
            this.logger.info("User created:", payload);
        },

        // Subscribe to all "user.*" events
        "user.*"(payload, sender, eventName) {
            // Do something with payload. 
            // The `eventName` contains
            // the original event name. E.g. `user.modified`.
            // The `sender` is the nodeID of sender.
        }

        // Subscribe to a local event
        "$node.connected"({ node }) {
            this.logger.info(`Node '${node.id}' is connected!`);
        }
      }

    }

#### Grouping (Register the handler to belong to a given group)

    module.exports = {
      name: "payment",
      events: {
        "users.created": {
          group: "VIP", // the broker will emit events to
          handler(payload) {
          }
        }
      }
    }

### Methods (Contains private methods: visible to this.)

    nodule.exports = {

        methods: {
          sendMail(recipients, subject, body) {
            return new Promise((resolve, reject) => {});
          },
          async calclate() {
            return await this.sendMail("","","")
          }
        }

    }

### Lifecycle events (These are triggered by the broker)

    module.exports = {

      created() {
        // Fired when the service instance created (with `broker.loadService` or `broker.createService`)
      },

      async started() {
        // Fired when broker starts this service (in `broker.start()`)
      }

      async stopped() {
        // Fired when broker stops this service (in `broker.stop()`)
      }
    }

### Dependencies (Declares dependency servces)

  module.exports = {

    dependencies: [
      "likes", // shorthand w/o version
      { name: "users", version: 2 }, // with numeric version
      { name: "comments", version: "staging" } // with string version
    ],

    async started() {
      this.logger.info("It will be called after all dependent services are available.");
      const users = await this.broker.call("users.list");
    }

  }

### Metadata (Store service metadata here, not used by moleculer, visibility: published)

    module.exports = {

      metadata: {
        scalable: true,
        priority: 5
      },

    }

### Local variables( Create local vars only in the created lifecycle method)

    module.exports = {

      created() {
        this.server = http.createServer(this.httpHandler); // server local variable
      },

      started() {
        this.server.listen(this.settings.port);
      },

      stopped() {
        this.server.close();
      },

      methods() {
        httpHandler(req, res) {
          res.end("Hello Moleculer!");
        }
      }

    }


### ES6 classes(Write services using ES6 class and use constructor.parseServiceSchema to manually functions)

    const {Service} = require("moleculer");

    class GreeterService extends Service {

        constructor(broker) {
          super(broker);

          this.parseServiceSchema({
            name: "greeter",
            version: "v2",

            meta: {
              scalable: true
            },

            dependencies: [
              "auth",
              "users"
            ],

            settings: {
              upperCase: true
            },

            actions: {
              hello: this.hello,
              welcome: {
                cache: {
                  keys: ["name"]
                },
                params: {
                  name: "string"
                },
                handler: this.welcome
              }
            },

            events: {
              "user.created": this.userCreated
            },

            created: this.serviceCreated,
            started: this.serviceStarted,
            stopped: this.serviceStopped,
          });
        }

        // Action handlers
        hello() {return "Hello Moleculer" }
        welcome(ctx) {return this.sayWelcome(ctx.params.name) }

        // Private method
        sayWelcome(name) {return `Welcome, ${this.settings.upperCase ? name.toUpperCase() : name}` }

        // Event handler
        userCreated(user) {this.broker.call("mail.send", { user }) }
        serviceCreated() {this.logger.info("ES6 Service created.") }      
        serviceStarted() {this.logger.info("ES6 Service started.") }
        serviceStopped() {this.logger.info("ES6 Service stopped.") }
    }

    module.exports = GreeterService;

### ES7 Decorators(Use decorators to create a sevice class)

    const { ServiceBroker } = require('moleculer');
    const { Service, Action, Event, Method } = require('moleculer-decorators');
    const web = require('moleculer-web');
    const broker = new ServiceBroker();

    @Service({
        mixins: [web],
        settings: {
          port: 3000,
          routes: []
        }
    })
    class MyService {

        @Action()
        Login(ctx) {}

        @Action()
        anotherActiopn(ctx) {}

        // With options
        @Action({
            cache: false,
            params: {
              a: "number",
              b: "number"
            }
        })
        someOtherAction(ctx) {}

        @Event
        'users.created'(payload, sender, eventName) {}

        @Event
        'users.stopped'(payload, sender, eventName) {}

        @Method
        authorize(ctx, route, req, res) {}

        hello() {} // Private

        started() {} // Reserved for moleculer, fired when started
        created() {} // Reserved for moleculer, fired when created
        stopped() {} // Reserved for moleculer, fired when stopped
    }

    broker.createService(MyService);
    broker.start();

## Actions(The public methods of a service)

    // calling an action
    const Users = await broker.call(
      'users.getAllUsers', // The name of the service dot the name of the action
      {parametersObject},  // An object containing the params. The service acesses this using ctx.params
      options              // An object to set/reset request params (See API)
    );

### Usage

    const broker = new ServiceBroker();

    broker.loadServices("/path/to/services/"); 

    broker.call("user.list")
      .then(res => console.log("User list: ", res));
    
    //Call with params
    broker.call("user.get", { id: 3 })
      .then(res => console.log("User: ", res));

    //Call with async/await
    const users = await broker.call("user.get", { id: 3 });
    
    //Call with options
    broker.call("user.recommendation", { limit: 5 }, {
      timeout: 500,
      retries: 3,
      fallbackResponse: () => {}
    }).then(res => console.log("Result: ", res));
    
    //Call with error handling
    broker.call("posts.update", { id: 2, title: "Modified post title" })
      .then(res => console.log("Post updated!"))
      .catch(err => console.error("Unable to update Post!", err));
    
    //Direct call: get health info from the “node-21” node
    broker.call('$node.health', {}, { nodeID: "node-21" })
      .then(res => console.log("Result: ", res));
  
### Meta (used by to communicate action.metadata between caller and callee )

    broker.createService({
      name: "test",
      
      actions: {
          first(ctx) {
              return ctx.call("test.second", null, { meta: {
                  b: 5
              }});
          },

          second(ctx) {
            console.log(ctx.meta); //  { a: "John", b: 5 }
          }
      }

    });

    broker.call("test.first", null, { meta: {
      a: "John"
    }});

### Internal calls (use {parentCtx: ctx} to forward context when making internal calls)

  broker.createService({
    name: "mod",

    actions: {
      hello(ctx) {
        console.log(ctx.meta); // { user: 'John' }
        ctx.meta.age = 123
        return this.actions.subHello(ctx.params, { parentCtx: ctx });
      },

      subHello(ctx) {
        console.log("meta from subHello:", ctx.meta); // { user: 'John', age: 123 }
        return "hi!";
      }
    }
  });

  broker.call("mod.hello", { param: 1 }, { meta: { user: "John" } });

### Multiple calls

    await broker.mcall([
      { action: "posts.find", params: { limit: 5, offset: 0 } },
      { action: "users.find", params: { limit: 5, sort: "username" }, opts: { timeout: 500 } }
    ]);

    await broker.mcall({
      posts: { action: "posts.find", params: { limit: 5, offset: 0 } },
      users: { action: "users.find", params: { limit: 5, sort: "username" }, opts: { timeout: 500 } }
    });

### Streaming(files)

    //Send a file to a service as a stream
    broker.call("storage.saveFile", 
      fs.createReadStream(fileName), // params must be a stream (file)
      { meta: { filename: "avatar-123.jpg" }}
    );

    // Process received stream on the caller side
    const filename = "avatar-123.jpg";
    broker.call("storage.getFile", { filename })
    .then(stream => {
      const s = fs.createWriteStream(`./${filename}`);
      stream.pipe(s);
      s.on("close", () => broker.logger.info("File has been received"));
    })


    module.exports = {
      name: "storage",
      actions: {

        // Receiving a stream in a service
        saveFile(ctx) {
          const stream = fs.createWriteStream(`/tmp/${ctx.meta.filename}`);
          ctx.params.pipe(stream);
        },


        // Return a stream as response in a service
        getFile: { 
          params: {
            filename: "string"
          },
          handler(ctx) {
            return fs.createReadStream(`/tmp/${ctx.params.filename}`);
          }
        }
      }
    };


#### AES encode/decode example service

    const crypto = require("crypto");
    const password = "moleculer";

    module.exports = {
      name: "aes",
      actions: {
        encrypt(ctx) {
          const encrypt = crypto.createCipher("aes-256-ctr", password);
          return ctx.params.pipe(encrypt);
        },

        decrypt(ctx) {
          const decrypt = crypto.createDecipher("aes-256-ctr", password);
          return ctx.params.pipe(decrypt);
        }
      }
    };

### Action visibility

    published || null || default:can be published via API Gateway
    public: locally + remotely but not published via API GW
    protected: can be called only locally (from local services)
    private: can be called only internally (via this.actions.xy() inside service)

    actions: {
      find(ctx) {}, // published by default
      clean: {
          visibility: "private", // Callable only via `this.actions.clean`
          handler(ctx) {}
      }
    },

## Events

### Broadcast event

    broker.broadcast("config.changed", config); // broadcast that a config service has been created
    broker.broadcast("user.created", { user }, "mail"); // Send to all "mail" service instances
    broker.broadcast("user.created", { user }, ["user", "purchase"]); // Send to all "user" & "purchase" service instance
    broker.broadcastLocal("config.changed", config); //broadcast to local services only

    # Subscribing to events/broadcasts
    module.exports = {
      events: {
        // Subscribe to `user.created` event
        "user.created"(user) {
            console.log("User created:", user);
        },

        // Subscribe to all `user` events
        "user.*"(user) {
            console.log("User event:", user);
        }

        // Subscribe to all internal events
        "$**"(payload, sender, event) {
            console.log(`Event '${event}' received from ${sender} node:`, payload);
        }
      }
    }

### Balanced events (sent to specific group of services)

    // The `user` will be serialized to transportation.
    broker.emit("user.created", user);

    // Only the `mail` & `payments` services receives it
    broker.emit("user.created", user, ["mail", "payments"]);

    # Subscribing to balanced events
    module.exports = {
      name: "payment",
      events: {
        "order.created": {
          // Register handler to the "other" group instead of "payment" group.
          group: "other",
          handler(payload) {
          }
        }
      }
    }

### [Internal events (prefixed $)](https://moleculer.services/docs/0.13/events.html#Internal-events)

    $node.connected // broker sends this when: a node is (re)connected
    $node.updated
    $node.disconnected
    $broker.started // broker sends this when: broker.stat() is called && all services started
    $broker.stopped
    $transporter.connected // transporter sends this once it is connected
    $transporter.disconnected

## [Networking - Transporter](https://moleculer.services/docs/0.13/networking.html)

### Custom Transporter

#### my-transporter.js

    const BaseTransporter = require("moleculer").Transporters.Base;

    module.exports class MyTransporter extends BaseTransporter {
      connect() { /*...*/ }
      disconnect() { /*...*/ }
      subscribe() { /*...*/ }
      publish() { /*...*/ }
    }

#### app.js

    const { ServiceBroker } = require("moleculer");
    const MyTransporter = require("./my-transporter");

    const broker = new ServiceBroker({
      transporter: new MyTransporter()
    });

## Load Balancing (Strategy to select a node for services that run multiple instances on different nodes)

  const broker = new ServiceBroker({
    registry: {
      strategy: "Random" //
      strategy: "RoundRobin" // Based on Round Robin
    }

    registry: {
      strategy: "CpuUsage", // select the node with the lowest cpu usage
      strategyOptions: {
        sampleCount: 3,
        lowCpuUsage: 10 // select the node wih cpu usage < 10%>
      }
    }

    registry: {
      strategy: "CpuUsage", // select the node with the lowest cpu usage
        strategyOptions: {
          sampleCount: 15,
          lowLatency: 20,
          collectCount: 10,
          pingInterval: 15
        }
    }
  });

## Fault tolerenc - Circuit breaker(stop trying an operation that already failed several times)

    const broker = new ServiceBroker({

      circuitBreaker: { // set circuit breaker for the whole service
        enabled: true,
        threshold: 0.5,
        minRequestCount: 20,
        windowTime: 60, // in seconds
        halfOpenTime: 5 * 1000, // in milliseconds
        check: err => err && err.code >= 500,
      },
      
      retryPolicy: { // can be overwritten by the caller: broker.call("posts.find", {}, { retries: 3 });
        enabled: true,
        retries: 5,
        delay: 100,
        maxDelay: 2000,
        factor: 2,
        check: err => err && !!err.retryable
      },

      requestTimeout: 5 * 1000,// setTimeout for service calling // overwrite: broker.call("posts.find", {}, { timeout: 3000 });


      // control the concurrent request handling of actions
      bulkhead: {
        enabled: true,
        concurrency: 3, // # of concurrent tasks
        maxQueueSize: 10, // max# of queued tasks, QueueIsFullException will be thrown if the rquests exceed
      }

      actions: {
        create: {
          circuitBreaker: { /*Overwrite the global cb*/},
          retryPolicy: {/*overwrite*/},
          bulkhead: {/*overwrite*/}
          handler(ctx){}
        }
      },

    });

### Fallback (function to be called in case the action fails)

    // Fallback response setting in calling options
    const result = await broker.call("users.recommendation", { userID: 5 }, {
      timeout: 500,
      fallbackResponse(ctx, err) {
        return broker.cacher.get("users.fallbackRecommendation:" + ctx.params.userID);
      }
    });

    module.exports = {
      name: "recommends",
      actions: {
        add: {
            fallback: (ctx, err) => "Some cached result", // Fallback in action definition
            fallback: "getCachedResult", // Fallback as method name string
            handler(ctx) {}
        }
      },
      methods: {
        getCachedResult(ctx, err) {
          return "Some cached result";
        }
      }
    };

## Caching (cache responses of service.actions)

    const { ServiceBroker } = require("moleculer");

    const broker = new ServiceBroker({
      cacher: "Memory" // enable caching
    });

    broker.createService({
      name: "users",
      actions: {
        list: {
            cache: true, // Enable caching to this action
            handler(ctx) {
              this.logger.info("Handler called!");
              return [
                { id: 1, name: "John" },
                { id: 2, name: "Jane" }
              ]
            }
        }
      }
    });

    broker.start()
      .then(() => {
          // Will be called the handler, because the cache is empty
          return broker.call("users.list").then(res => broker.logger.info("Users count:", res.length));
      })
      .then(() => {
          // Return from cache, handler won't be called
          return broker.call("users.list").then(res => broker.logger.info("Users count from cache:", res.length));
      });

## Validating(validators for ctx.params)

    const { ServiceBroker } = require("moleculer");

    const broker = new ServiceBroker({
      validation: true // enable params validation. Default is true
    });

    broker.createService({
      name: "say",
      actions: {
          hello: {
              // Validator schema for params
              params: {
                name: { type: "string", min: 2 }
              },
              handler(ctx) {
                return "Hello " + ctx.params.name;
              }
          }
      }
    });

    broker.call("say.hello")                      // [throw ValidationError]: "The 'name' field is required!"
    broker.call("say.hello", { name: 123 })       // [throw ValidationError]: "The 'name' field must be a string!"
    broker.call("say.hello", { name: "Walter" })  // [No Error] -> "Hello Walter"

### [Validator schema](https://github.com/icebob/fastest-validator#readme)

    {
      id: { type: "number", positive: true, integer: true },
      name: { type: "string", min: 3, max: 255 },
      status: "boolean" // short-hand def
    }

## [API Gateway (Use this to publish services as RESTfil API, access each action as a url/path)](https://moleculer.services/docs/0.13/moleculer-web.html#Full-service-settings)

    npm i moleculer-web

    const { ServiceBroker } = require("moleculer");
    const ApiService = require("moleculer-web");

    const broker = new ServiceBroker();

    broker.createService(ApiService);
    broker.start();

    Call test.hello action: http://localhost:3000/test/hello
    Call math.add action with params: http://localhost:3000/math/add?a=25&b=13
    Get health info of node: http://localhost:3000/~node/health
    List all actions: http://localhost:3000/~node/actions

### Whitelist (Use whitelist to publish specific actions)

    broker.createService({
      mixins: [ApiService],
      settings: {
        routes: [{
          path: "/api",
          whitelist: [
            "posts.*",      // Access any actions in 'posts' service
            "users.list",   // Access call only the `users.list` action
            /^math\.\w+$/   // Access any actions in 'math' service
          ]
        }]
      }
    });

### Aliases

    broker.createService({
      mixins: [ApiService],

      settings: {
        routes: [{
          aliases: {
            "login": "auth.login", // Call 'auth.login' action with 'GET /login or 'POST /login
            "POST users": "users.create", // Restrict the request method
            "GET greeter/:name": "test.greeter", // access the  'name' in the action: `ctx.params.name` in action

            // define manually
            "GET users":        "users.list",
            "GET users/:id":    "users.get",
            "POST users":       "users.create",
            "PUT users/:id":    "users.update",
            "DELETE users/:id": "users.remove",

            // Use shorthand rest route
            "REST cart": "cart"
          }
        }]
      }
    });

### Middlewares

    broker.createService({
      mixins: [ApiService],

      settings: {
        // Global middlewares. Applied to all routes.
        use: [
            cookieParser(),
            helmet()
        ],

        routes: [
            {
                path: "/",

                // Route-level middlewares.
                use: [
                    compression(),
                    
                    passport.initialize(),
                    passport.session(),

                    serveStatic(path.join(__dirname, "public"))
                ],
                
                aliases: {
                    "GET /secret": [
                        // Alias-level middlewares.
                        auth.isAuthenticated(),
                        auth.hasRole("admin"),
                        "top.secret" // Call the `top.secret` action
                    ]
                }
            }
        ]
      }
    });

### Serve static files

    broker.createService({
      mixins: [ApiService],

      settings: {
        assets: {
          folder: "./assets",
          options: {}
        },

        routes: [{
          callOptions: {
            timeout: 500,
            retries: 3,
            fallbackResponse(ctx, err) {  }
          }
        }]
      }
    });

### Authorization

    const Error = require("moleculer-web").Errors;

    broker.createService({
      mixins: [ApiService],

      settings: {
        routes: [
          {
            authorization: true // First thing
          } 
        ]
      },

      methods: {

        authorize(ctx, route, req, res) { // second thing

          let auth = req.headers["authorization"];

          if (auth && auth.startsWith("Bearer")) {
            let token = auth.slice(7);

            if (token == "123456") {
              ctx.meta.user = { id: 1, name: "John Doe" };
              return Promise.resolve(ctx);
            } else {
              return Promise.reject(new Error.UnAuthorizedError(Error.ERR_INVALID_TOKEN));
            }

          } else {
            return Promise.reject(new Error.UnAuthorizedError(Error.ERR_NO_TOKEN));
          }
        }
      }
    }

### Authentication

    broker.createService({
      mixins: ApiGatewayService,

      settings: {
        routes: [
          {
            authentication: true //1. Enable authentication
          }
        ]
      },

      methods: {

        // 2. implement the authenticate method
        authenticate(ctx, route, req, res) { 
          let accessToken = req.query["access_token"];
          if (accessToken) {
              if (accessToken === "12345") {
                  // valid credentials
                  return Promise.resolve({ id: 1, username: "john.doe", name: "John Doe" });
              } else {
                  // invalid credentials
                  return Promise.reject();
              }
          } else {
              // anonymous user
              return Promise.resolve(null);
          }
        }
      }
    });

### Route hooks (to set ctx.meta, req.headers, ctx.data)

    broker.createService({
      mixins: [ApiService],

      settings: {
        routes: [
          {
            path: "/",

            onBeforeCall(ctx, route, req, res) {
                ctx.meta.userAgent = req.headers["user-agent"]; // Set request headers to context meta
            },

            onAfterCall(ctx, route, req, res, data) {
                return doSomething(ctx, res, data); // Async function which return with Promise
            }
          }
        ]
      }
    });

### Error handlers (call res.end in handlers)

    broker.createService({
      mixins: [ApiService],
      settings: {

        routes: [{
            path: "/api",

            // Route error handler
            onError(req, res, err) {
              res.setHeader("Content-Type", "application/json; charset=utf-8");
              res.writeHead(500);
              res.end(JSON.stringify(err));
            }
        }],

        // Global error handler
        onError(req, res, err) {
          res.setHeader("Content-Type", "text/plain");
          res.writeHead(501);
          res.end("Global error: " + err.message);
        }
      }
    }

### CORS headers

    const svc = broker.createService({
      mixins: [ApiService],

      settings: {

        // Global CORS settings for all routes
        cors: {
            
            methods: [
              "GET", 
              "OPTIONS", 
              "POST", 
              "PUT",
              "DELETE"
            ],                    // Configures the Access-Control-Allow-Methods CORS header. 
            origin: "*",          // Configures the Access-Control-Allow-Origin CORS header.
            allowedHeaders: [],   // Configures the Access-Control-Allow-Headers CORS header.
            exposedHeaders: [],   // Configures the Access-Control-Expose-Headers CORS header.
            credentials: false,   // Configures the Access-Control-Allow-Credentials CORS header.
            maxAge: 3600          // Configures the Access-Control-Max-Age CORS header.
        },

        routes: [{
            path: "/api",

            // Route CORS settings (overwrite global settings)
            cors: {
                origin: ["http://localhost:3000", "https://localhost:4000"],
                methods: ["GET", "OPTIONS", "POST"],
                credentials: true
            },
        }]
      }
    });

### ExpressJS middleware usage(Using moleculer as a middleware in expressJs)

    const broker = broker.createService({
      mixins: [ApiService],

      settings: {
          middleware: true
      }
    });

    const app = express();
    app.use("/api", broker.express());
    app.listen(3000);
    broker.start();

## [Database Adapters](https://moleculer.services/docs/0.13/moleculer-db.html) (Moleculer uses 1-db-per-service pattern, adaptrer is based on NeDB)

    npm install moleculer-db --save

    "use strict";

    const { ServiceBroker } = require("moleculer");
    const DbService = require("moleculer-db");

    const broker = new ServiceBroker();

    broker.createService({
      name: "users",
      mixins: [DbService], // inherit methods from the dbADapter

      settings: {
        idField: "POSTS", // id of the database

### Populating

        populates: {
          // Shorthand populate rule. Resolve the `voters` values with `users.get` action.
          "voters": "users.get",

          // Define the params of action call. It will receive only with username & full name of author.
          "author": {
            action: "users.get",
            params: {
              fields: "username fullName"
            }
          },

          // Custom populator handler function
          "rate"(ids, rule, ctx) {
              return Promise.resolve(...);
          }
        }

        pageSize: required_default_pageSize_in_list_action,
        maxPageSize: Number_required_Maximum_pageSize_list_action,
        maxLimit: Number_required_Maximum_value_of_limit_in_find_action,// Default: -1 (no limit)
        entityValidator: {}, validator schema or a function to validate the incoming entity in create & ‘insert’ actions,
        fields: ["_id", "username", "name", filed filtering list], // Declare filterable fields

        }, // end of settings

### Extend the adapter action with custom actions

    actions: {

    // findByIdAndUpdate
    vote(ctx) {
      return this.adapter.updateById(ctx.params.id, { $inc: { votes: 1 } });
    },

    // findByIdAndUpdate
    unvote(ctx) {
      return this.adapter.updateById(ctx.params.id, { $inc: { votes: -1 } });
    },

    // List posts of an author
    byAuthors(ctx) {
      return this.find({
        query: {
          author: ctx.params.authorID
        },
        limit: ctx.params.limit || 10,
        sort: "-createdAt"
      });
    }

### lifecycle entity events

      entityCreated(json, ctx) {
          this.logger.info("New entity created!");
      },

      entityUpdated(json, ctx) {
          // You can also access to Context
          this.logger.info(`Entity updated by '${ctx.meta.user.name}' user!`);
      },

      entityRemoved(json, ctx) {
          this.logger.info("Entity removed", json);
      },  

      // This is not an Adapter's lifecycle method
      afterConnected() {
        // Seed the DB with ˙this.create`
        this.logger.info("Connected successfully");
      }

### Data Manipulation(modify entities before or after CRUD)

        hooks: {
          before: {
            create: [
              function addTimestamp(ctx) {
                // Add timestamp
                ctx.params.createdAt = new Date();
                return ctx;
              }
            ]
          },
          after: {
            get: [
              // Arrow function as a Hook
              (ctx, res) => {
                // Remove sensitive data
                delete res.mail;
                delete res.phoneNumber;

                return res;
              }
            ]
          }
        }

      }

    });

    broker.start()

    // Create a new user
    .then(() => broker.call("users.create", {
        username: "john",
        name: "John Doe",
        status: 1
    }))

    // Get all users
    .then(() => broker.call("users.find").then(console.log));

    // List users with pagination
    .then(() => broker.call("users.list", { page: 2, pageSize: 10 }).then(console.log));

    // Get a user
    .then(() => broker.call("users.get", { id: 2 }).then(console.log));

    // Update a user
    .then(() => broker.call("users.update", { id: 2, name: "Jane Doe" }).then(console.log));

    // Delete a user
    .then(() => broker.call("users.remove", { id: 2 }).then(console.log));

### [Adapter API]

Mongoose adapter

    npm install moleculer-db moleculer-db-adapter-mongoose mongoose --save

    "use strict";

    const { ServiceBroker } = require("moleculer");
    const DbService = require("moleculer-db");
    const MongooseAdapter = require("moleculer-db-adapter-mongoose");
    const mongoose = require("mongoose");

    const broker = new ServiceBroker();

    // Create a Mongoose service for `post` entities
    broker.createService({
      name: "posts",
      mixins: [DbService],
      adapter: new MongooseAdapter("mongodb://localhost/moleculer-demo"),
      model: mongoose.model("Post", mongoose.Schema({
        title: { type: String },
        content: { type: String },
        votes: { type: Number, default: 0}
      }))
    });

    broker.start()
    // Create a new post
    .then(() => broker.call("posts.create", {
      title: "My first post",
      content: "Lorem ipsum...",
      votes: 0
    }))

    // Get all posts
    .then(() => broker.call("posts.find").then(console.log));

The Adapter already implements a set of:

- CRUD operations as [Actions](https://moleculer.services/docs/0.13/moleculer-db.html#Actions)
- Helpers [Methods](https://moleculer.services/docs/0.13/moleculer-db.html#Methods)

### Data Manipulation(modify entities before or after CRUD)

    const { ServiceBroker } = require("moleculer");
    const DbService = require("moleculer-db");

    const broker = new ServiceBroker();

    broker.createService({
      name: "db-with-hooks",

      // Load DB actions
      mixins: [DbService],

      // Add Hooks to DB actions
      hooks: {
        before: {
          create: [
            function addTimestamp(ctx) {
              // Add timestamp
              ctx.params.createdAt = new Date();
              return ctx;
            }
          ]
        },
        after: {
          get: [
            // Arrow function as a Hook
            (ctx, res) => {
              // Remove sensitive data
              delete res.mail;
              delete res.phoneNumber;

              return res;
            }
          ]
        }
      }
    });

    const user = {
      name: "John Doe",
      mail: "john.doe@example.com",
      phoneNumber: 123456789
    };

    broker.start()
    // Insert user into DB
    // Call "create" action. Before hook will be triggered
    .then(() => broker.call("db-with-hooks.create", user))
    // Get user from DB
    // Call "get" action. After hook will be triggered
    .then(entry => broker.call("db-with-hooks.get", { id: entry._id }))
    .then(res => console.log(res))
    .catch(err => console.error(err));

## Uncovered

- [Action hooks (To be continued)](https://moleculer.services/docs/0.13/actions.html#Action-hooks)
- [Contexts (To be continued)](https://moleculer.services/docs/0.13/actions.html#Contexts)
- [Lifecycle](https://moleculer.services/docs/0.13/lifecycle.html)
- [Logging](https://moleculer.services/docs/0.13/logging.html)
- [Middlewares](https://moleculer.services/docs/0.13/middlewares.html)
- [Discovery & Registry](https://moleculer.services/docs/0.13/registry.html)
- [Metrics](https://moleculer.services/docs/0.13/metrics.html)
- [Error](https://moleculer.services/docs/0.13/errors.html)
- [REPL Console](https://moleculer.services/docs/0.13/moleculer-repl.html)
- [All Modules](https://moleculer.services/modules.html)

## Introduction

    const { ServiceBroker } = require("moleculer");

    const broker = new ServiceBroker();

    broker.createService({
      name: "math",
      actions: {
        add(ctx) {
          return Number(ctx.params.a) + Number(ctx.params.b);
        },
        subtract(ctx) {
          return Number(ctx.params.a) - Number(ctx.params.b);
        },
      }
    });

### Call the services

    broker.start()
      .then(() => broker.call("math.add", { a: 5, b: 3 }))
      .then(res => console.log("5 + 3 =", res))
      .catch(err => console.error(`Error occured! ${err.message}`));

    broker.start()
      .then(() => broker.call("math.multiply", { a: 5, b: 3 }))
      .then(res => console.log("5 - 3 =", res))
      .catch(err => console.error(`Error occured! ${err.message}`));

### Core Concepts

Service

    A JavaScript module containing some part of a complex application. It is isolated and self-contained, meaning that even if it goes offline or crashes the remaining services would be unaffected.

Node

    An OS process running on a local or external network and can host service(s).

Local Services

    Service(s) running on a node, Share hardware resources and use local bus to communicate with each other, no network latency (transporter) is not used.

Remote Services

    Service(s) distributed across multiple nodes. Communication is done via transporter.

Service Broker

    The heart of Moleculer. Responsible for management and communication between services (local and remote). Each node must have an instance of Service Broker.

Transporter

    Communication bus that services use to communicate (transfers events, requests and responses).

Gateway

    API Gateway exposes Moleculer services to end-users. A regular Moleculer service running a (HTTP, WebSockets, etc.) server. It handles the incoming requests, maps them into service calls, and then returns appropriate responses.

Overall View

### Example

    const
    { ServiceBroker }   = require("moleculer"),
    HTTPServer          = require("moleculer-web"),
    transporter         = "NATS" // must be installed and started

    /********************************** Create the services *******************************/
    const 
    gatewayBroker = new ServiceBroker({
      nodeID: "node-1",
      transporter
    }),

    databaseBroker = new ServiceBroker({
      nodeID: "node-2",
      transporter
    });

    /********************************** Confugure the services *******************************/
    gatewayBroker.createService({
      name: "gateway",
      mixins: [HTTPServer],
      settings: {
        routes: [
          {
            aliases: {
              "GET /products": "dbBroker.listProducts"
            }
          }
        ]
      }
    });

    databaseBroker.createService({
      name: "dbBroker",
      actions: {
        listProducts(ctx) {
          return [
            { name: "Apples", price: 5 },
            { name: "Oranges", price: 3 },
            { name: "Bananas", price: 2 }
          ];
        }
      }
    });

    /********************************** start the services *******************************/
    Promise.all([gatewayBroker.start(), databaseBroker.start()]);
