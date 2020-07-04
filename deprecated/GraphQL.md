# GraphQL

<!-- markdownlint-disable MD024  -->

## Fields & Arguments(Fields are the object properties that can be queried, arguments: paramented-values that you send as query)

### makeQuery(query) convert your query into a fetch request

    // request for the fields id, content, author
    var query = `

      query {
        getAllmessages {
          id
          content
          author
        }
      }

    `;

    var body = JSON.stringify({query})

    fetch('/graphql', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json',
      },
      body
    })
      .then(r => r.json())
      .then(({data}) => console.log(data));

### Fields

    // request for the fields id, content, author
    query {
      getAllmessages {
        id 
        content
        author
      }
    }

### Aliases (rename the fields id => _id, content => inhalt)

    query {
      getAllmessages {
        _id     : id 
        inhalt  : content
        author
      }
    }

### Fragments (define a group of fields to avoid repeating for each requested field)

    query {
      getAllmessages{
        ...messageFields
      }
      
      messageFormFabrice      : getMessage(author: "fabrice"){
        ...messageFields
      }
      
      messageFormSomeOneElse  : getMessage(author: "SomeOneElse"){
        ...messageFields
      }
    }

    // This fragment defines the fields that should be queried
    fragment messageFields on MessageType {
      _id: id // rename id
      content
    }

## Express-GraphQL

### [Basic types](https://graphql.org/graphql-js/basic-types/)

    const expressGraphQL = require('express-graphql');
    const { buildSchema } = require('graphql');

    // Construct a schema, using GraphQL schema language
    var schema = buildSchema(`
      type Query {
        name: String
        age: Int
        weight: Float
      }
    `);

    // The rootValue provides a resolver function for each API endpoint
    var rootValue = {
      name:   () => "fabrigeas", // return a String as defined
      age:    () => 30,          // an Int as defined
      weight: () => 86.1,        // Float as defined
    };

#### Route

    app.use('/graphql', expressGraphQL({
      schema,
      rootValue,
      graphiql: true,
    }));

#### Client

    const body = {query: "{ name }"} || {query: "{ name, weigh }"} || {query: "{ name, weigh, age }"};

    fetch('/graphql', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json',
      },
      body: JSON.stringify(body)
    })
  
### Passing Arguments

    const { buildSchema } = require('graphql');

    var schema = buildSchema(`
      type Query {
        getArea(length: Int, width: Int): Int,
        getSquare(number: Int): Int,
        getMax(x: Int, y: Int): Int
      }
    `);

    var rootValue = {
      getArea   : ({length, width}) => (length * width),
      getSquare : ({number}) => (number * number),
      getMax    : ({x,y})  => x > y ? x : y
    };

#### Client

    var query = `query {
      getArea(length: 10, width: 13)
      getSquare(number: 2)
      getMax(x: 14, y: 4)
    }`;

    fetch('/graphql', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json',
      },
      body: JSON.stringify({
        query,
      })
    })

### Objects

    var schema = buildSchema(`

      type Person {  // Fields. i.e everything that can be queried
        id     : Int!
        name   : String!
        height : Float
        weight : Float
        getBMI: Float!
      }

      type Query {
        getPerson(id: Int!, name: String!, weight: Float, height: Float): Person // The parameters
      }
    `);

    var rootValue = {
      getPerson: ({id = -1, name = "No name", weight, height}) => ({ // LL the fields must be returned (Use a class instead of manually)
        id, 
        name,
        weight,
        height, 
        getBMI: weight/ (height*height)
      })
    }

#### Client

    <!-- query {
      getPerson(id: 12, name : "fabrigeas", weight: 98, height: 180) {
        id
        name
        getBMI
        height
        weight
        w: weight
      }
    } -->


    var query = `query {
      getPerson(id: 12, name : "fabrigeas", weight: 98, height: 180) {
        id
        name
        getBMI
        height
        weight
        w: weight
      }
    }`;

    fetch('/graphql', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json',
      },
      body: JSON.stringify({
        query,
      })
    })

### Mutations and Input Types (Used to insert|update)

    var schema = buildSchema(`

        input MessageInput {
          content: String
          author: String
        }

        type MessageType {
          id: ID!
          content: String
          author: String
        }

        type Query {
          getMessage(id: ID!): MessageType,
          getAllmessages: [MessageType]
        }

        type Mutation {
          createMessage(input: MessageInput): MessageType
          updateMessage(id: ID!, input: MessageInput): MessageType
        }
    `);


    class Message {
      constructor(id, {content, author}) {
        this.id = id;
        this.content = content;
        this.author = author;
      }
    }

    var messages = [];

    var rootValue = {
      getMessage      : ({id}) => messages.find( ({id: _id}) => id === _id),
      getAllmessages  : () => messages,
      createMessage   :  ({input}) => {
        const id      = require('crypto').randomBytes(10).toString('hex'),
              message = new Message(id, input)

        messages.push(message);
        return message;
      },
      updateMessage   : ({id, input}) => {
        for( let i =0; i < messages.length; i++) {
          let temp = messages[i];

          if(id === temp.id) {
            temp = input;
            return temp
          }
        }
        throw new Error('no message exists with id ' + id);
      },
    }

#### Cliemt

    <!-- mutation{
      createMessage(input: {content: "hello", author: "fabrigeas"}){
        id
        content
        author
      }
    }

    query{
      getAllmessages{
        id
        content
        author
      }
    }

    {
      getMessage(id: "39ce9215da610c799a06"){
        content
        author
      }
    } -->

    mutation{
      createMessage(input: {content: "hello", author: "fabrigeas"}){
        id
        content
        author
      }
    }

    query{
      getAllmessages{
        id
        content
        author
      }
    }

    {
      getMessage(id: "39ce9215da610c799a06"){
        content
        author
      }
    }

    var author = 'andy';
    var content = 'hope is a good thing';

    <!-- var query = `mutation CreateMessage($input: MessageInput) {
      createMessage(input: $input) {
        id
      }
    }`; -->

    var query = `mutation{
      createMessage(input: {content: "hahaha", author: "Feugang"}){
        id
        content
        author
      }
    }`;

    fetch('/graphql', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json',
      },
      body: JSON.stringify({
        query,
        variables: {
          input: {
            author,
            content,
          }
        }
      })
    })
      .then(r => r.json())
      .then(data => console.log('data returned:', data));

### Authentication and Express Middleware

    var schema = buildSchema(`
      type Query {
        ip: String
      }
    `);

    function authMiddleware(req, res, next) {
      console.log('ip:', req.ip);
      next();
    }

    var rootValue = {
      ip: function (args, request) {
        return request.ip;
      }
    };

    app.use(authMiddleware);

    app.use('/graphql', expressGraphQL({
      schema: schema,
      rootValue,
      graphiql: true,
    }));

### Constructing Types Using GraphQLSchema instead of buildSchema

    // Maps id to User object
    var fakeDatabase = {
      'a': {
        id: 'a',
        name: 'alice',
      },
      'b': {
        id: 'b',
        name: 'bob',
      },
    };

    // Define the User type
    var userType = new graphql.GraphQLObjectType({
      name: 'User',
      fields: {
        id: { type: graphql.GraphQLString },
        name: { type: graphql.GraphQLString },
      }
    });

    // Define the Query type
    var queryType = new graphql.GraphQLObjectType({
      name: 'Query',
      fields: {
        user: {
          type: userType,
          // `args` describes the arguments that the `user` query accepts
          args: {
            id: { type: graphql.GraphQLString }
          },
          resolve: function (_, {id}) {
            return fakeDatabase[id];
          }
        }
      }
    });

    var schema = new graphql.GraphQLSchema({query: queryType});

    app.use('/graphql', expressGraphQL({
      schema: schema,
      graphiql: true,
    }));

### Playground