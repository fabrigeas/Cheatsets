# Javascript

## Object Oriented

### [Classes](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Classes)

    let Rectangle = class Rectangle2 {

      publicProperty = null;
      #privateProperty = null;
      constructor(height, width) {
        // instance properties: must be defined inside of class methods
        this.height = height;
        this.width = width;
      }
     
      //getter
      get area() {
        return this.calcArea();
      }

      calcArea() {
        return this.height * this.width;
      }

      // static methods: takes 2 Rectangles and 
      static eat() {
        return this;
      }

      static distance(rectangleA, rectangleB) {
        const dx = rectangleA.height - rectangleB.height;
        const dy = rectangleA.width - rectangleB.width;
        return Math.hypot(dx, dy);
      }

      // method
      speak() {
        return this;
      }
    };

Static class-side properties and prototype data properties must be defined outside of the ClassBody declaration:

    Rectangle.staticWidth = 20;
    Rectangle.prototype.prototypeWidth = 25;


    const square = new Rectangle(10, 10);
    let a = new Rectangle(10, 10);
    let b = new Rectangle(5, 5);

    console.log(Rectangle.name);// //Rectangle
    console.log(square.area); // 100
    console.log(Rectangle.distance(a, b)); // 7.0710678118654755
    // console.log(square.speak())
    console.log(Rectangle.eat())

    class Dog extends rectangle{
      constructor(name){
        super(name)
      }
    }

## Typescript

    npm install -g typescript
    # store your script in a .ts file

### Types

    String
    number,
    boolean
    Array
    any

Let

    let names: [String];
    let names: Array<String>;

Tuple

    let mTuple: [String, number]

Enum

    # An array where indexes are named instead
    
    enum Colors {Red, Green, Yellow}
    Colors.Red; //0
    Colors.Yellow; //2
    
    # Indexes chaged
    enum Colors {Green = 1, Red, Yellow}
    Colors.Red; // 1 instead of the default 0
    
    enum Colors {Green = 2, Red = 4, Yellow = 0]
    Colors.Yellow; // 0
    Colors[0]; // 'Yellow'
Void

    # Has no type, and can only be undefined or nulll
    let unusable: void = undefined; 
[Never: Function returning never must have unreachable end point eg infinit loop, throw](https://www.typescriptlang.org/docs/handbook/basic-types.html)

Type assertions(cast): <type>, as 

    let notsure: any = "lkajdlksaj";
    let length: number = (<string>notsure).length;
    let length: number = (notsure as string).length

[Interfaces](https://www.typescriptlang.org/docs/handbook/interfaces.html)

    An interface defines the structure of an object. What attr the object must have.

The problem

    function calculateBMI( paerson: {height: number, weight: number} ){}
    calculateBMI( {height, weight, ..otherAttributes);

The solutions

    interface Person{
        weight: number,
        height?: number, // ? optional property
        readonly height: number, // readonly is the same as const but is used for attributes rather than variables. (can be init but not reassigned(
        ...
    function calculateBMI( paerson: Person){}
    calculateBMI({weight: 1, height: 2}); // ok
    
ReadonlyArray

    let constArray ReadonlyArray<number> = [01, 2, 3]; // the array, elements can be init but not reassigned;
    
Excess Property Checks
    
    calculateBMI({weight: 1, height: 2, age: 23}); // error: 'age' not expected in type 'Person'
    calculateBMI( {weight: 1, age: 23} as Person ); // would do the trick
    
    # index signature # The better solution 
    interface Person {
        weight?: number,
        height?: number,
        [propName: string]: any
    }
    // This means, a person can have weight, height, then any number of other properties

Function types (using interfaces as function signature)

    interface SearchFunction { 
        (source: string, substring: string): boolean; 
    }
    
    //The parameter names do not need to match. Only their types need to.
    let mySearch: SearchFunction = function ( src: string, sub: string) {
        return srs === sub;
    }
    
    //Because the function implements the interface, the types need tot be given.
    let mySearch: SearchFunction = function ( src, sub) {
        return srs === sub;
    }
Indexable Types

    interface StringArray {
        [index: number]: string;
    }
    
    let myArray: StringArray = ["A", "B"];
    myArray[0]; //

### Class types

    interface ClockInterface {
      currentTime: Date;
      setTime(d: Date);
    }

    class Clock implements ClockInterface {
      currentTime: Date;
      setTime(d: Date) {
        this.currentTime = d;
      }
      constructor(h: number, m: number) { }
    }

Extending Interfaces (allwos the child class to onherit/copy properties of the parent)

    interface Person {name: string, gender: boolean}
    interface Man extends Person {height: number}
    interface Mixed extends A, B, C;
    
    let fab = <Mixed> {};
    fab.height: 180;
    fab.name: "fabrice"; //Man inerits gender and name from its parent Person.

Interfaces Extending Classes

    class Control {private state: any; }

    interface SelectableControl extends Control { select(): void; }
    class Button extends Control implements SelectableControl {select() { } }
    class TextBox extends Control {select() { } }

    // Error: Property 'state' is missing in type 'Image'. because Image must implement all members of the interface
    class Image implements SelectableControl {
    select() { }
    }

    class Location {

    }

### Classes

    class Animal {
      name: string;
      protected gender: boolean;
      onstructor(name:string) { this.name = name; }
      getInformation() { return this.name; }
    }
    
    let man = new Animal("lkjasdlkja");

Inheritance

    class Human extends Animal {
      walk() {console.log("lkölkö");}
      constructor(name: string) {super.name(name);}
      getInformation() {super.getInformation();}
      dance() {return this.gender;} //protected parent members can be accessed in the child class
    }
    
    const man = new Human();
    man.walk();
    man.getInformation();
    man.name = "ölkajda";
    man.gender = false; //error: protected cannot be accessed by objects
    
    //protected superconstructor can only be accessed by child but not by Instance (grand child) 

Parameter property (enables to create and initialize class members in one place)

    class Octopus {
      readonly numberOfLegs: number = 8;
      constructor(readonly name: string) { 
        // The class now has a new member 'name'
      }

      let passcode = "secret passcode";

      class Employee {
        private _fullName: string;

        get fullName(): string {return this._fullName; }

        set fullName(newName: string) {
          if (passcode && passcode == "secret passcode") {
            this._fullName = newName; 
          }
          else { 
            console.log("Error: Unauthorized update of employee!"); 
          }
        }
      }

      let employee = new Employee();

      employee.fullName = "Bob Smith";

      if (employee.fullName) {
        console.log(employee.fullName);
      }
    }

Here you find my summarized javascript notes

### [Object Destruct](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Operators/Destructuring_assignment)

#### With arrays

    [a, b] = [10, 20]; // a=10, b=20
    [a, b, ...rest] = [10, 20, 30, 40, 50];// a=10, b=20, rest = [20, 40, 50]
    
    var x = [1, 2, 3, 4, 5];
    var [y, z] = x; // y=1, z=2
    
    var foo = ['one', 'two', 'three'];
    var [one, two, three] = foo;

    var a, b;
    [a, b] = [1, 2];
    
    var a, b;
    [a=5, b=7] = [1]; // 5, 7 are default values
    console.log(a); // 1
    console.log(b); // 7

swap

    var a = 1;
    var b = 3;
    [a, b] = [b, a];
    
    function f() {
      return [1, 2];
    }
    var a, b; 
    [a, b] = f();

ignore some returned values

    function f() {
      return [1, 2, 3];
    }
    var [a, , b] = f();

assign the rest of an array to a var

    var [a, ...b] = [1, 2, 3];
    console.log(b); // [2, 3]

#### With objects

    var {a, b} = {a:10, b:20}
    
    #paranthesis required when assigning undefinned values
    ({a, b, ...rest} = {a: 10, b: 20, c: 30, d: 40});  // rest = {c: 30, d: 40}

Assigning to new variable names

    var o = {p: 42, q: true};
    var {p: foo, q: bar} = o;
    console.log(foo); // 42 
    console.log(bar); // true
    
    const metadata ={
        name: "asdlkas",
        address : {
            street: "asdas",
            number: 100
        }
        otherAttributes: {}
    }
    
    let {
        address{
            street: "renamed"
        }
    } = metadata;

for iteration and destructuring

    let people = [
        {
            name: "asda",
            address: {
                street: "Bessemerstr",
            }
        },
    ]
    
    for( var {name:n, address: {street: s}} of people ){
        console.log(n, s) //asda, Bessemerstr
    }

unpackaging object acttributes

    function userId({id}) { //extract only the id of the given object
      return id;
    }

    function whois({displayName, fullName: {firstName: name}}) { // extract only the displayName and fullName.firstName and ignore the others
      console.log(displayName + ' is ' + name);
    }

    console.log('userId: ' + userId(user)); // "userId: 42"
        whois(user); // "jdoe is John"
    };
     const user = {an object with deep structure}
    console.log('userId: ' + userId(user)); // "userId: 42"
    whois(user); // "jdoe is John"

## [Inheritance and the prototype chain](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Inheritance_and_the_prototype_chain)

Prperty is the this of js

    let parent = function () {
      this.a = 1;
      this.b = 2;
    }
    let child = new parent();

    // add properties to parent using prototype
    parent.prototype.b = 3;
    parent.prototype.c = 4;
    parent.d = 5;

    console.log(child.a); // 1
    console.log(child.b); // 2
    console.log(child.c); // 4: 
    console.log(child.d); // undefined because was added to parent without prototype
    console.log(child.e); // undefined none of the ancestors has this property

### [Event loop](https://developer.mozilla.org/en-US/docs/Web/JavaScript/EventLoop#Event_loop)

    Event queue is a queue of messages, each executed in disorder but according to the message received.
    Every event in js 
    Each message is completed before an other can be processed, ef a click event will be handled till the end before location.reload.
    
    (function() {

      console.log('executes immediately becuase it is the only message in the queue');

      setTimeout(function cb() { 
        console.log('Run immediately asa message queue is empty');
      });

      console.log('before asap');

      setTimeout(function cb1() {
        console.log('wait atleast 0 ms after message queue is empty then log');
      }, 0);

      setTimeout(function cb1() {
        console.log('wait atleast 2 ms after message queue is empty then log');
      }, 2);

      setTimeout(function cb1() {
        console.log('wait atleast 1 ms after message queue is empty then log');
      }, 1);

      console.log('this is the end but before timeouts');

    })();


References:

[https://javascript.info/](https://javascript.info/)

### [fetch](https://developer.mozilla.org/en-US/docs/Web/API/Fetch_API/Using_Fetch)

[fetch Params](https://developers.google.com/web/updates/2015/03/introduction-to-fetch)

    postData('http://example.com/answer', { answer: 42 })
      .then(data => console.log(data)) // JSON from `response.json()` call
      .catch(error => console.error(error));

    function postData(url, data) {
      return fetch(url, {
        body: JSON.stringify(data), // must match 'Content-Type' header
        cache: 'no-cache', // *default, no-cache, reload, force-cache, only-if-cached
        credentials: 'same-origin', // include, same-origin, *omit
        headers: {
          'user-agent': 'Mozilla/4.0 MDN Example',
          'content-type': 'application/json',
        },
        method: 'POST', // *GET, POST, PUT, DELETE, etc.
        mode: 'cors', // no-cors, cors, *same-origin
        redirect: 'follow', // manual, *follow, error
        referrer: 'no-referrer', // *client, no-referrer
      }).then(response => response.json()); // parses response to JSON
    }
    

### Select image files and preview them
    
Single file

    /** 
	 * Event handler for image selected
	 * This function takes the selected image and places it on an image preview
	 * @param {event} event the input.file
	 * 
	 * This function requires a file input and a img, add the id of your image to the input data.preview attribute
	 *  * Example
	 *  * input(type='file' accept="image/*" data-preview="book-detail-preview" onchange='onImageSelectionComplete(event)')
	 *  * img(id="book-detail-preview.preview")
	 */
	function onImageSelectionComplete (event) {

		let fileReader = new FileReader();

		fileReader.onload = function (e) {
			document.getElementById(event.target.dataset.preview).src = e.target.result;
		};

		fileReader.readAsDataURL(event.target.files[0]);

	}
    
Multiple files

    input#item-preview-input.form-control(type='file' value='click here to select images' accept="image/*" multiple=true onchange='onImageSelectionComplete(event)')

      function onImageSelectionComplete(event){

        let img           = document.getElementById("item-preview-input"),
            selectedFiles = event.target.files,
            total         = $('.btn.btn-link.p-0.preview').length;

            
        for (let i = 0, file; file = selectedFiles[i]; i++) {

            let fileReader = new FileReader();

            fileReader.onload = function(event) {

                if(++total > 10){
                    return;
                }

                let item =
                `<a class='btn btn-link p-0 preview' data-src='${event.target.result}' data-id='item-preview-photo-${total}' onclick='showImagePopup(this)' href='#'> 
                    <img class='preview rounded' height='200' src='${event.target.result}'>
                </a>`;
                img.insertAdjacentHTML('afterend', item);
            }

            fileReader.readAsDataURL(file);
        }

    }  

## [Arrow functions](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Functions/Arrow_functions)

--> n goes

    let countdown = (n) => {
      while (n --> 0)  // "n goes to zero"
        console.log(n)
    }
    countdown(5)

## New JS

var     

    ## var is a scopeless variable
    if (true) {
      var x = 5;
    }
    console.log(x);  // x is 5
const 

    is a block scoped variable that can be assigned only once
    
let
    
    # a block scoped local variable that overwrites the previously created variable.
    if (true) {
      let y = 5;
    }
    console.log(y);  // ReferenceError: y is not defined  
    
    
### Hoisting| Lifting | floating (only for vars. Does not work for const and let)

    #A var used before being declared.
    # This doesnot cause an exception, however even thaugh the hoisted value is post initialized,
    # it's value cannot be pre used.
    
    
    var myvar = 'my value';
    (function() {
      console.log(myvar); // undefined because during lifting, only the declaration is lifted; the initialization remains in its position.
      var myvar = 'local value';
    })();
    
function hoisting

    foo(); // "bar"

    function foo() {
      console.log('bar');
    }


    baz(); // TypeError: baz is not a function. (it is a variable)

    var baz = function() {
      console.log('bar2');
    };
    
string interpolation

    let result = `${variable} / ${variable} / ${variable}`
    let result = variable + ' / ' + variable + ' / ' + variable

literal notation

    var myBooks = {};
    var mango = {
        color: "yellow",
        howSweetAmI: function () {
            console.log("Hmm Hmm Good");
        }
    }

Object Notation
    
    var mango =  new Object ();
    mango.color = "yellow";
    mango.howSweetAmI = function () {
        console.log("Hmm Hmm Good");
    }

class declaration
    
    function Fruit (theColor) {
        this.color = theColor;
        this.showColor = function () {
            console.log("This is a " + this.color);
        }
    }

instantiate the class Fruit
    
    var mangoFruit = new Fruit ("Yellow", 8, "Mango", ["South America", "Central America", "West Africa"]);
    mangoFruit.showName(); // This is a Mango.
    mangoFruit.nativeTo();
    var pineappleFruit = new Fruit ("Brown", 5, "Pineapple", ["United States"]);
    pineappleFruit.showName(); // This is a Pineapple.


Accessing Objects attributes

    var book = {
        title: "Ways to Go",
        pages: 280,
        bookMark1:"Page 20"
    };
    console.log ( book.title); // Ways to Go
    console.log ( book["title"]); //Ways to Go

    var bookTitle = "title";
    
    console.log ( book[bookTitle]); // Ways to Go
    console.log ( book["bookMark" + 1]); // Page 20

    var school = {
        schoolName: "MIT"
    };
    
    console.log("schoolName" in school);  // true because schoolName is an own property on the school object
    console.log("schoolType" in school);  // false false because we did not define a schoolType property on the school object, and neither did the object inherit a schoolType property from its prototype object Object.prototype.​
    console.log("toString" in school);  // true because the school object inherited the toString method from Object.prototype. 

loop through object's properties can also be done using Object.entries

    var school = {
        schoolName:"MIT", 
        schoolAccredited: true, 
        schoolLocation:"Massachusetts"
    };

    for (var key in school) {
        console.log(key); // Prints schoolName, schoolAccredited, schoolLocation

    }

## [DOM](https://javascript.info/dom-navigation)

[w3s](https://www.w3schools.com/jsref/dom_obj_document.asp)

    document.documentElement // html
    document.head
    document.body

dom navigaion

    # these are collections, not arrays,
    Array.from(document.body.childNodes)//convert collection to array
    
    for (let node of document.body.children) {
        console.log(node); // shows all nodes from the collection
    }
    
    for (let node of document.body.childNodes) {
        console.log(node); // shows all nodes from the collection including texts
    }

Searching: getElement* and querySelector*

    <div id='alpha'>
    <div id='alpha-beta'>
    
    console.log( alpha )
    console.log( window.alpha )
    console.log( window['alpha-beta'] )
    
selectors ! they return collections, not variables, thus uses index [0] to acces ur element

    document.getElementById('alpha')
    elem.getElementsByTagName(tag)  
    elem.getElementsByClassName(className)
    document.querySelector('CSS selectors') // returns the first node
    elem.querySelectorAll('css selector') // returns all nodes as collection
    elem.matches('a[href$="zip"]') // returns true or false if the element matches the parameter
    elem.closest(css) // find the ancestors that match the css query
    
    window.alpha //where id is alpha
    window['alpha-beta'] // when id contains a slash

Node properties: type, tag and contents

    
    console.log( document.body) // print the html element
    console.dir( document.body) // print body as json
    
    // for comment
    alert( document.body.firstChild.tagName ); // undefined (no element)
    alert( document.body.firstChild.nodeName ); // #comment

    // for document
    alert( document.tagName ); // undefined (not 
    alert( document.nodeName ); // #document

innerHTML: outerHTML

    alert( document.body.innerHTML ); // read body.toString()
    document.body.innerHTML = '<new content/>'; // replace it
    elem.outerHTML // includes the elem too

Attributes and properties

    elem.hasAttribute(name) //checks for existence.
    elem.getAttribute(name) //gets the value.
    elem.setAttribute(name, value) // sets the value.
    elem.removeAttribute(name)// removes the attribute.
    
    #custom attributes        
    <div show-info="name"></div>
    <div show-info="age"></div>
    let user = {name: "Pete", age: 25 };
    for(let div of document.querySelectorAll('[show-info]')) {
        let field = div.getAttribute('show-info');
        div.innerHTML = user[field]; // Pete, then age
    }
    
    #custom atrributes and selectors
    // use custom attributes istead of normal classes beacause the earlier are a bit simpler than removing old/adding a new class
    div.setAttribute('order-state', 'canceled');
    
    <style>
      .order[order-state="new"] {color: green; }
      .order[order-state="pending"] {color: blue; }
      .order[order-state="canceled"] {color: red;}
    </style>
    <div class="order" order-state="new">A new order.</div>
    <div class="order" order-state="pending">A pending order.</div>
    <div class="order" order-state="canceled">A canceled order.</div>
    
    #data- attribute reserved 
    <body data-about="Elephants"> // document.body.dataset.about: Elephantes
    <div data-order-state/> //document.body.dataset.dataset.orderState : elephants
    
Modifying the document

    node.append(...nodes or strings) – append nodes or strings at the end of node,
    node.prepend(...nodes or strings) – insert nodes or strings into the beginning of node,
    node.before(...nodes or strings) –- insert nodes or strings before the node,
    node.after(...nodes or strings) –- insert nodes or strings after the node,
    node.replaceWith(...nodes or strings
    
    div.insertAdjacentHTML('beforebegin', '<p>Hello</p>');
    div.insertAdjacentHTML('afterend', '<p>Bye</p>');
    
Styles and classes
    
    elem.classList.add/remove("class") // adds/removes the class.
    elem.classList.toggle("class") //if the class exists, then removes it, otherwise adds it.
    elem.classList.contains("class") // returns true/false, checks for the given class.
    
    elem.style.left = left; // e.g '123px'
    elem.style.top = top; // e.g '456px'
    document.body.classList.add('article');
    document.body.className  // main page article

ElementStyle

    background-color  => elem.style.backgroundColor
    z-index           => elem.style.zIndex
    border-left-width => elem.style.borderLeftWidth
    document.body.style.backgroundColor = prompt('background color?', 'green');
    elem.style.display = "" //reset display to it's default value
    
    div.style="color: red; width: 100px" // set multiple properties
        div.style.cssText='color: red !important;
        background-color: yellow;
        width: 100px;
        text-align: center;
      ';
      
    #getComputedStyle(node).rule: rule must be detailed eg marginTop instead of margin
    document.body.style.color  //empty
    document.body.style.marginTop // empty
    
    getComputedStyle(document.body).colr
    getComputedStyle(document.body).marginTop
    
[Element size and scrolling](https://javascript.info/size-and-scroll)

## [Events](https://javascript.info/event-details)

Browser Events

    mouse events: click, contextmenu, mouseover, mouseout, mousedown, museup, mousemove   
    form events: submit, focus, 
    kyebpard:keydown, keyup
    css: transitioned
    
html Event Handlers: add an attribute prepend on: onclick, ocontextmenu, onsubmit ...

Dom Property Event Handlre

    selecor.onclick = function {}
    
    elem.addEventListener( "click" , (event) => itemClicked);
    elem.removeEventListener( "click", () => alert('Thanks!'));
    
    input.addEventListener("click", handler);
    input.removeEventListener("click", handler);

    #object handler
    elem.addEventListener('click', {
      handleEvent(event) {
        alert(event.type + " at " + event.currentTarget);
      }
    });
    
        class Menu {
      handleEvent(event) {
        switch(event.type) {
          case 'mousedown':
            elem.innerHTML = "Mouse button pressed";
            break;
          case 'mouseup':
            elem.innerHTML += "...and released.";
            break;
        }
      }
    }
    let menu = new Menu();
    elem.addEventListener('mousedown', menu);
    elem.addEventListener('mouseup', menu);

## [Closure | inner function accessing outer function's variables](https://javascript.info/closure)

    let name = "John";
    function sayHi(){
      alert("Hi, " + name);
    }
    name = "Pete";
    sayHi(); //hi pete
    
A function gets outer variables as they are now; it uses the most recent values.
 
     function makeWorker() {
      let name = "Pete";
      return function() {
        alert(name);
      };
    }

    let name = "John";
    let work = makeWorker();
    work();//pete

Nested functions

    function User(name) {
      this.sayHi = function() {
        alert(name);
      };
    }
    let user = new User("John");
    user.sayHi(); //john

    function makeCounter() {
      let count = 0;
      return function() {
        return count++; // has access to the outer counter
      };
    }
    
    let counter = makeCounter();
    alert( counter() ); // 0
    alert( counter() ); // 1
    alert( counter() ); // 2
 
immediately-invoked function expressions (IIFE)| functions that run immediately, without being called

    (function(){
      let message = "Hello";
      alert(message);
    })();
    

    (function() {
      alert("Brackets around the function");
    })();

    (function() {
      alert("Brackets around the whole thing");
    }());

    !function() {
      alert("Bitwise NOT operator starts the expression");
    }();

    +function() {
      alert("Unary plus starts the expression");
    }();
    
    ## errors
    // Error: Unexpected token (
    function() { // <-- JavaScript cannot find function name, meets ( and gives error
      let message = "Hello";
      alert(message); // Hello
    }();
    
    // syntax error because of brackets below
    function go() {
    }(); // <-- can't call Function Declaration immediately

## [Global object | Window](https://javascript.info/global-object)
    alert("Hello");
    // the same as
    window.alert("Hello");
    
    var phrase = "Hello";
    function sayHi() {
      alert(phrase);
    }
    alert( window.phrase ); // Hello (global var)
    alert( window.sayHi ); // function (global function declaration)
    window.test = 5;
    alert(test); // 5
    
But the global object does not have variables declared with let/const!
    
    let user = "John";
    alert(user); // John
    alert(window.user); // undefined, don't have let
    alert("user" in window); // false

## [Function object, NFE](https://javascript.info/function-object)

The 'name' property

    function sayHi() {
      alert("Hi");
    }
    alert(sayHi.name); // sayHi
    
    ## this differs from the top function 'assignment'
    let sayHi = function() {
      alert("Hi");
    }
    alert(sayHi.name); // sayHi (works!)

bind 

    var module = {
      x: 42,
      getX: function() { return this.x; }
    }

    var unboundGetX = ;
    alert(unboundGetX()); // 'undefined': The function gets invoked at the global scope
    var boundGetX = unboundGetX.bind(module);
    alert(boundGetX()); // 42
    
    #
    this.x = 9;    // this refers to global "window" object here in the browser
    var module = {
      x: 81,
      getX: function() { return this.x; }
    };
    module.getX(); // 81
    var retrieveX = module.getX;
    retrieveX();  // 9: The function gets invoked at the global scope
    // Create a new function with 'this' bound to module New programmers might confuse the global var x with module's property x
    var boundGetX = retrieveX.bind(module);
    boundGetX(); // 81

## [Scheduling: setTimeout and setInterval](https://javascript.info/settimeout-setinterval)

calls sayHi() after one second: 

    function sayHi() {
      alert('Hello');
    }
    setTimeout(sayHi, 1000);
    
    ## with parameters
    function sayHi(phrase, who) {
      alert( phrase + ', ' + who );
    }
    setTimeout(sayHi, 1000, "Hello", "John"); // Hello, John
    
    //If the first argument is a string, JavaScript creates a function from it.So, this will also work:
    setTimeout("alert('Hello')", 1000);
    
    // use this instead
    setTimeout(() => alert('Hello'), 1000);
    
    // wrong!, don't add (), cuz setTimeout awats a function reference 
    setTimeout(sayHi(), 1000);
    
Canceling with clearTimeout

    //schedule the function and then cancel it (changed our mind). As a result, nothing happens:
    let timerId = setTimeout(() => alert("never happens"), 1000);
    alert(timerId); // timer identifier
    clearTimeout(timerId);
    alert(timerId); // same identifier (doesn't become null after canceling)
    
setInterval: runs the function not only once, but regularly after the given interval of time.

    // repeat with the interval of 2 seconds
    let timerId = setInterval(() => alert('tick'), 2000);

    // after 5 seconds stop
    setTimeout(() => { clearInterval(timerId); alert('stop'); }, 5000);
    
Recursive setTimeout

    // instead of:
    let timerId = setInterval(() => alert('tick'), 2000);
    
    // u may also use
    let timerId = setTimeout(function tick() {
      alert('tick');
      timerId = setTimeout(tick, 2000); // (*)
    }, 2000);
    
run the code as soon as everything is complete

    setTimeout(() => alert("World"), 0); // will be executed as soon as alert has been executed
    alert("Hello");

## [Decorators and forwarding, call/apply](https://javascript.info/call-apply-decorators)

Transparent caching: this enables the results a function to be cached so that the function is not called several times with the same variable

    function slow(x) {
      // there can be a heavy CPU-intensive job here
      alert(`Called with ${x}`);
      return x;
    }

    function cachingDecorator(func) {
      let cache = new Map();

      return function(x) {
        if (cache.has(x)) { // if the result is in the map
          return cache.get(x); // return it
        }

        let result = func(x); // otherwise call func

        cache.set(x, result); // and cache (remember) the result
        return result;
      };
    }

    slow = cachingDecorator(slow);

    alert( slow(1) ); // slow(1) is cached
    alert( "Again: " + slow(1) ); // the same

    alert( slow(2) ); // slow(2) is cached
    alert( "Again: " + slow(2) ); // the same as the previous line
    
Using 'func.call' for the context

    let worker = {
      someMethod() { return 1;},
      slow(x) {return x * this.someMethod(); // (*) }
    };
    
    function cachingDecorator(func) {
      let cache = new Map();
      return function(x) {
        if (cache.has(x)) { return cache.get(x); }
        let result = func(x); # this will fail based on the output below, th context is wrong
     // let result = func.call(this, x); # "this" is passed correctly now
        cache.set(x, result);
        return result;
      };
    }
    
    alert( worker.slow(1) ); // the original method works
    worker.slow = cachingDecorator(worker.slow); // now make it caching
    alert( worker.slow(2) ); // Whoops! Error: Cannot read property 'someMethod' of undefined (only .slow method was passed. no context)
    
func.call

    # example 1
    function sayHi() {
      alert(this.name); // 'this' will be provided by caller
    }
    
    let user = { name: "John" };
    let admin = { name: "Admin" };
    sayHi.call( user ); // this = John
    sayHi.call( admin ); // this = Admin
    
    # example 2
    function say(phrase) {
      alert(this.name + ': ' + phrase);
    }
    let user = { name: "John" };
    say.call( user, "Hello" ); // John: Hello
    
func.apply works like func.call but takes a context and a list of parameters

    #example
    func(1, 2, 3);
    func.apply(context, [1, 2, 3])
    
    #
    function say(time, phrase) {
      alert(`[${time}] ${this.name}: ${phrase}`);
    }
    let user = { name: "John" };
    say.apply(user, ['10:00', 'Hello']); // [10:00] John: Hello (this=user)
  
The spread operator ... allows to pass iterable args as the list to call. The apply accepts only array-like args.

    let args = [1, 2, 3];
    func.call(context, ...args); // pass an array as list with spread operator
    func.apply(context, args);   // is same as using apply

## [callbacks](https://javascript.info/callbacks) for asynchronous tasks

the problem

    //loads a script and appends it to the head of the doc
    function loadScript(src) {
      let script = document.createElement('script');
      script.src = src;
      document.head.append(script);
    }
    loadScript('/my/script.js');
    newFunction(); // no such function! because this function is in the loaded script, but load script is not complete yet

the solution

    function loadScript(src, callback) {
      let script = document.createElement('script');
      script.src = src;
      script.onload = () => callback(script);
      document.head.append(script);
    }
    loadScript('/my/script.js', function() {
      newFunction(); // so now it works
    });
    loadScript('https://cdnjs.cloudflare.com/ajax/libs/lodash.js/3.2.0/lodash.js', script => {
       alert(`Cool, the ${script.src} is loaded`);
       alert( _ ); // function declared in the loaded script
     });
     
Callback in callback

    loadScript('/my/script.js', function(script) {
      loadScript('/my/script2.js', function(script) {
        loadScript('/my/script3.js', function(script) {
        });
      })
    });
    
Handling errors

    function loadScript(src, callback) {
      let script = document.createElement('script');
      script.src = src;
      script.onload = () => callback(null, script);
      script.onerror = () => callback(new Error(`Script load error for ${src}`));
      document.head.append(script);
    }
    loadScript('/my/script.js', function(error, script) {
      if (error) {
        // handle error
      } else {
        // script loaded successfully
      }
    });
    
Pyramid of doom | callback hell is solved by replacing anonymous functions with real ones 

## [Promise](https://javascript.info/promise-basics)
    
    see utils below

## [Promises, async/await](https://javascript.info/async-await)

'Async' ensures that a function returs a promise

    async function f() {
      return 1;
    }
    f().then(alert); // 1
    
    async function f() {
      return Promise.resolve(1);
    }

    f().then(alert); // 1
    
'Await' runs inside an async function, forces the executor to wait the promise resolution before proceeding#
If we try to use await in non-async function, that would be a syntax error:

    async function f() {
      let promise = new Promise((resolve, reject) => {
        setTimeout(() => resolve("done!"), 2000)
      });
    
      let result = await promise; // wait till the promise resolves (*)
      alert(result); // "done!" will be displayed after the 2 secs
    }
    
    f();
    
example

    async function showAvatar() {
      // read our JSON
      let response = await fetch('/article/promise-chaining/user.json');
      let user = await response.json();
    
      // read github user
      let githubResponse = await fetch(`https://api.github.com/users/${user.name}`);
      let githubUser = await githubResponse.json();
    
      // show the avatar
      let img = document.createElement('img');
      img.src = githubUser.avatar_url;
      img.className = "promise-avatar-example";
      document.body.append(img);
    
      // wait 3 seconds
      await new Promise((resolve, reject) => setTimeout(resolve, 3000));
      img.remove();
      return githubUser;
    }
    
    showAvatar();
    
await accepts thenables (objects that have then methods)
      
      constructor(num) {
          this.num = num;
        }
        then(resolve, reject) {
          setTimeout(() => resolve(this.num * 2), 1000); // (*)
        }
      };
      
      async function f() {
        let result = await new Thenable(3);// waits for 1 second, then result becomes 6
      }
      
      f();

## [functions](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Functions/)

rest pparameters

    function f(...[a, b, c]) {
      return a + b + c;
    }
    f(1)          // NaN (b and c are undefined)
    f(1, 2, 3)    // 6
    f(1, 2, 3, 4) // 6 (the fourth parameter is not destructured)
    
    function fun1(...theArgs) {
      console.log(theArgs.length);
    }
    fun1();  // 0
    fun1(5); // 1
    fun1(5, 6, 7); // 3
    
    function multiply(multiplier, ...theArgs) {
      return theArgs.map(function(element) {
        return multiplier * element;
      });
    }
    var arr = multiply(2, 1, 2, 3); 
    console.log(arr); // [2, 4, 6]

## [Automated testing with mocha](https://javascript.info/testing-mocha)

[chai assertions](http://www.chaijs.com/api/assert/)
[chai:expect/should](http://www.chaijs.com/api/bdd/)


	<head>
	  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/mocha/3.2.0/mocha.css">
	  <script src="https://cdnjs.cloudflare.com/ajax/libs/mocha/3.2.0/mocha.js"></script>
	  <script src="https://cdnjs.cloudflare.com/ajax/libs/chai/3.5.0/chai.js"></script>
	  <script>
          //setup
		mocha.setup('bdd');
		let assert = chai.assert;
		
        //function to be tested
		function pow(x, n) {
		  return x**n;
		}
	  </script>
	</head>

	<body>
	  <div id="mocha"></div>
      <!-- here are the tests-->
	  <script>
		 describe("pow", function() {
			it("raises to n-th power", function() {
				assert.equal(pow(2, 3), 8);
			});
		});
		
		mocha.run();
	  </script>
	</body>
    
automated tests
    
  describe("test", function() {
    
      // before(() => console.log("Testing started – before all tests"));
      // after(() => console.log("Testing finished – after all tests"));
    
      // beforeEach(() => console.log("Before a test – (after every  it)"));
      // afterEach(() => console.log("After a test – ( after every it)"));
      
        function makeTest(x) {
          let expected = x * x * x;
          it(`${x} in the power 3 is ${expected}`, function() {
            assert.equal(pow(x, 3), expected);
          });
         }
  
        for (let x = 1; x <= 5; x++) {
          makeTest(x);
        }
      
      it("for negative n the result is NaN", function() {
        assert.isNaN(pow(2, -1));
      });
    
      it("for non-integer n the result is NaN", function() {
        assert.isNaN(pow(2, 1.5));
      });
    
    });

## karma jasmine testing

init
    
    npm init
    npm install --save-dev jasmine
    npm install --save-dev jasmine-core
    npm install --save-dev karma
    npm install --save-dev karma-jasmine 
    npm install --save-dev karma-chrome-launcher
    
    npm install -g karma-cli
    npm install -g karma
    npm install -g jasmine
    
    karma init
    
touch karma.conf.js [content here](https://raw.githubusercontent.com/Microsoft/cordova-samples/master/unit-testing/karma-jasmine-cli/karma.conf.js)

[karma.config.js.parameters](http://karma-runner.github.io/0.12/config/configuration-file.html)

js/main.js
    
    

tests/main.test.js

    karma start

Variable Destructuring

    const wolverine = {
      firstName: 'James',
      lastName: 'Howlett',
      powers: [
        'healing factor',
        'adamantium skeleton',
      ],
    };
    
    // declare and init variables using values from the object
    const { firstName, lastName, powers} = wolverine;
    console.log(firstName, lastName, powers) 
    
    const { powers: [power1, power2] } = wolverine;
    console.log(power1, power2) // power1 : healing ...
    
    // desctructure array
    const xMen = ['Storm', 'Cyclops', 'Beast', 'Phoenix', 'Wolverine', 'Mystique', 'Quicksilver'];
    const [first, second, third] = xMen;
    console.log(first, second, third); //Storm Cyclops Beast
    
Iterators
    anArray.entries() returns an array of [index,value]
    
    for ([index, value] of anArray.entries()) {
      console.log(`index: ${index}, value: ${value}`);
      
    }
    
    # for of loops through the array genting each value
    for (const entry of ['Starlord', 'Gamora', 'Rocket', 'Groot', 'Drax']) {
      console.log(entry)
    }
    
    # let in loops through the indexes
    for (const index in ['Starlord', 'Gamora', 'Rocket', 'Groot', 'Drax']) {
      console.log(index)
    }
map

    let temp = [1,2,3,4,5];
    temp = temp.map( item => item *=2); // [2,4,6,8]
    
Rest Operator

    function logNames(name1, name2) {
      console.log(name1); //Bob
      console.log(name2); //Jen
      
      for (var i = 0; i < arguments.length; i++) {
        console.log(arguments[i]);
      }
    }
    logNames('Bob', 'Jen', 'Cam', 'Pratik') // Bob Jen Bob Jen Cam Pratik
    
    
    # you specify the first 2 parameters but still handle the remaining ones
    const logNames = (name1, name2, ...names) => {
      console.log(name1); //Bob
      console.log(name2); //Jen
      
       for (let name of names) {
        console.log(name); // Cam, Patrick
      }
    }
    logNames('Bob', 'Jen', 'Cam', 'Pratik'); // Bob Jen Cam Pratik
    
Template Literals

    const name = 'T\'Challa';
    const country = 'Wakanda';
    const numSiblings = 1;
    const occupation = 'king';
    
    
    console.log(`Black Panther has ${numSiblings} ${ numSiblings === 1 ? 'sibling' : 'siblings' }.`);
    console.log(`Black Panther has ${ numSiblings === 1 ? `${numSiblings} sibling` : `${numSiblings} siblings` }.`);
    
Spread Operator ...myArray

    const characters = ['Bojack', 'Todd', 'Princess Carolyn'];
    const newCharacters = ['Diane', 'Mr. Peanutbutter'];
    
    let catList;
    castList = [characters, newCharacters]; // [ ["Bojack", "Todd", "Princess Carolyn"], ["Diane", "Mr. Peanutbutter"] ] # an array of the 2 arrays
    castList = [...characters, ...newCharacters]; // ['Bojack', 'Todd', 'Princess Carolyn', 'Diane', 'Mr. Peanutbutter'];
    castList = characters.push(...newCharacters);
    characters.push(...newCharacters) //["Bojack", "Todd", "Princess Carolyn", "Diane", "Mr. Peanutbutter", "Diane", "Mr. Peanutbutter"]
    
Tenary operator replaces if...else

    size = 10;
    size === 10 ? doSomeThing(); : doSomeThingElse();
    
    height===2 && width===2  ? doSomeThing(); : null; // doe something or nothing
    
Object Shorthand
    
    // create an object by mapping the function's parameter name and value
    const createPerson = (Name, age, rank) => {
      const person = {
        Name,
        age,
        rank,
        enteredShimmer: true,
      }
      return person;
    }
    
    const kane = createPerson('Kane', 34, 'Sergeant'); // { name: 'Kane', age: 34, rank: 'Sergeant', enteredShimmer: true }

    shimmerParty = [
      { name: 'Ventress', speciality: 'psychology' },
      { name: 'Lena', specialty: 'biology' },
    ].forEach(({ name, speciality }) => {
      
    });

Array.prototype.reduce()
    
    numbers.reduce( (sum,i) => sum +i); //sum = number[0], i = number[1] in first loop, sum, number[i=1++] afterwards


[Modules: import and export](https://developers.google.com/web/fundamentals/primers/modules)

    // 📁 lib.mjs
    export const repeat = (string) => `${string} ${string}`;
    export shout = (string) => `${string.toUpperCase()}!`;
    
    // 📁 main.mjs
    import {repeat, shout} from './lib.mjs';
    repeat('hello'); //'hello hello'
    shout('Modules in action'); //'MODULES IN ACTION!'

## AJAX

    var xMLHttpRequest = new XMLHttpRequest();
    xMLHttpRequest.open("POST", "/talks", true);
    xMLHttpRequest.setRequestHeader('Content-type','application/json; charset=utf-8');
    xMLHttpRequest.onload = function () {
        if (xMLHttpRequest.readyState == 4) {
            xMLHttpRequest.responseText;
        }
         else{
             connsole.log(xMLHttpRequest.response)
        }
    }
    xMLHttpRequest.send( 
        JSON.stringify( {Object} )
    );

### Select read and Parse file

        input.btn.btn-primary.btn-sm.mr-2(type="file" onchange='onLoadTalksFromFile(event)' value='load data from file'

        function onLoadTalksFromFile(event) {
            let file  = event.target.files[0];

            if (file) {

                let fileReader = new FileReader();

                fileReader.onload = function(e) { 

                    const lines = this.result.split('\n');

                    for(var i = 0; i < lines.length; i++){

                        let line     = lines[i],
                            arrayed  = line.split(" "),
                            duration =  parseInt( arrayed.pop() ) || 15;

                        talks.push({title:arrayed.join(' '), duration:duration})
                    }
                }
                fileReader.readAsText(file);
            } 
            else { 
                alert("Failed to load file");
            }
        }
        
### Read File

        function readTextFile(fileName, callback)
        {
            const xMLHttpRequest  = new XMLHttpRequest();
            xMLHttpRequest.open("GET", fileName, true);
            xMLHttpRequest.onload = function ()
            {
                if(xMLHttpRequest .readyState === 4)
                {
                    if(xMLHttpRequest .status === 200 || xMLHttpRequest .status == 0)
                    {
                        callback(xMLHttpRequest.responseText)
                    }
                    else {
                        // open file failed
                    }
                }
            }
            xMLHttpRequest .send();
        }

## reduce sum an array of objects

    const calculateTotal = cartItems => cartItems.reduce( (accumulator, {unitPrice, quantity}) => accumulator + (unitPrice * quantity), 0);

## utils

	/** This function makes a HTTP request and returns the resolve, error
	 * @param {String} url the backend url
	 * @param {String} method GET, POST ...
	 * @param {Object} data The Object
	 */
	function httpRequest (url, method, data) {

		return fetch(url, {
			body: JSON.stringify(data),
			cache: "no-cache",
			credentials: "same-origin",
			headers: {
				// 'user-agent': 'Mozilla/4.0 MDN Example',
				"content-type": "application/json",
			},
			method: method,
			mode: "cors",
			redirect: "follow",
			referrer: "no-referrer",
		}).then(response => response.json() );
		
	}

	/** Make a get request
	 * Returns a promise
	 * @param {String} url
	 */
	function get (url) {
		return fetch(url).then( response => response.json());
	}
	/** Event handler for image selected
	 * This function takes the selected image and places it on an image preview
	 * @param {event} event the input.file
	 *
	 * This function requires a file input and a img, add the id of your image to the input data.preview attribute
	 *  * Example
	 *  * input(type='file' accept="image/*" data-preview="book-detail-preview" onchange='onImageSelectionComplete(event)')
	 *  * img(id="book-detail-preview.preview")
	 */
	function onImageSelectionComplete (event) {

		let fileReader = new FileReader();

		fileReader.onload = function (e) {
			document.getElementById(event.target.dataset.preview).src = e.target.result;
		};

		fileReader.readAsDataURL(event.target.files[0]);
	}

	/** This function takes a file input, and uploads the input's files to the server
	 *
	 * @param {HTMLElement} input the Input containing the files: o ..* files
	 * @param {String} path The destination folder, where the files should be saved. The backend will create one if it doesn't exist
	 */
	function uploadFiles (input, path) {
		
		var formData = new FormData();

		for (let file of input.files ) {
			formData.append("file", file);
		}

		formData.append("path", path);

		return new Promise(function (resolve, reject) {
			$.ajax({
				type: "POST",
				url: "/files",
				processData : false,
				contentType : false,
				data : formData,
				succes : resolve,
				error  : reject
			});
		});
	}

	/** This function returns and object containing all the attributes of the form
	 * @param {HTMLElement} form The html form:
	 */
	function formToObject (form) {
		
		let item = {
		};

		for(let element of form.querySelectorAll("[name]")) {
			if(element.type == "checkbox") {
				item[element.name] = element.checked;
			} else {
				item[element.name] = element.value;
			}
		}

		return item;
	}

	/** This function fills the form's inputs with the values of the object
	 * @param {HTMLElement} form The form to be filled: documen.querySelector("selector")
	 * @param {Object} object The Object containing the values: {,,}
	 */
	function populateForm (form, object) {

		for(let attribute in object) {
			let element =  form.querySelector(`[name=${attribute}]`);
			if( element) {
				if(element.type == "select-one") {
					let e = element.querySelector(`[value='${object[attribute]}']`);
					if(e) {
						e.selected = true;
					}
				} else  if(element.type == "checkbox") {
					element.checked = object[attribute];
				} else {
					element.value = object[attribute];
				}
			}
		}
	}

	/** This function clears all the values of the form by setting the to null
	 * @param {HTMLElement} form The form to be cleard
	 */
	function resetForm (form) {
		for(let element of form.querySelectorAll("[name]")) {
			element.value = null;
		}
	}

## ES7

### Array.prototype.includes

    assert([1, 2, 3].includes(2) === true);
    assert([1, 2, 3].includes(4) === false);

    assert([1, 2, NaN].includes(NaN) === true);

    assert([1, 2, -0].includes(+0) === true);
    assert([1, 2, +0].includes(-0) === true);

    assert(["a", "b", "c"].includes("a") === true);
    assert(["a", "b", "c"].includes("a", 1) === false);

## ES8

### Classes

### String.prototype.padStart/ String.prototype.padStart

    "fab".padStart(5,"?"); // "??fab"
    "f".padStart(3,"?") // "??f"


    "fab".padEnd(5,"?"); // "fab??"
    "f".padEnd(3,"?") // "f??"

### Exponentiation Operator

    // x ** y

    let squared = 2 ** 2;
    // same as: 2 * 2

    let cubed = 2 ** 3;
    // same as: 2 * 2 * 2
    // x **= y

    let a = 2;
    a **= 2;
    // same as: a = a * a;

    let b = 3;
    b **= 3;
    // same as: b = b * b * b;

### Object.values / Object.entries

    var fruits = {
      
        apple: 10,
        orange: 20,
        grapes: 30,
        pineapple: 40
      
    }

    Object.values(fruits) // (4) [10, 20, 30, 40]
    Object.keys(fruits) // (4) ["apple", "orange", "grapes", "pineapple"]
    Object.entries(fruits)
        0: (2) ["apple", 10]
        1: (2) ["orange", 20]
        2: (2) ["grapes", 30]
        3: (2) ["pineapple", 40]


### Trailing commas in functions is now allowed

## ES9

    Template literals: `Hello ${name}`

### Asynchronous iterator

    async function process(array) {
      for await (let i of array) {
        doSomething(i);
      }
    }

### Template string of non escape sequence
    function foo(str) {
        return str[0].toUpperCase();
    }

    foo`justjavac`; // Output JUSTJAVAC
    foo`Xyz`; // Output XYZ
  
### Object expansion operator
    let a = [1,2,3];
    let b = [0, ...a, 4]; // [0,1,2,3,4]
    
    let obj = { a: 1, b: 2 };
    let obj2 = { ...obj, c: 3 }; // { a:1, b:2, c:3 }
    let obj3 = { ...obj, a: 3 }; // { a:3, b:2 }

    let object = { a: '01', b: '02' };
    let newObject = { c: '03', ...object }; //{c: "03", a: "01", b: "02"}
    
## ES10 

### flat()
    var arr1 = [1, 2, [3, 4]];
    arr1.flat(); // [1, 2, 3, 4]
    
    var arr2 = [1, 2, [3, 4, [5, 6]]];
    arr2.flat(); // [1, 2, 3, 4, [5, 6]]
    
    var arr3 = [1, 2, [3, 4, [5, 6]]];
    arr3.flat(2); // [1, 2, 3, 4, 5, 6]
    
    //Using Infinity as the depth, expand the nested array of any depth
    arr3.flat(Infinity); // [1, 2, 3, 4, 5, 6]
    
    //Remove null items
    var arr4 = [1, 2, , 4, 5];
    arr4.flat(); // [1, 2, 4, 5]

### flatMap()
    var arr1 = [1, 2, 3, 4];

    arr1.map(x => [x * 2]); // [[2], [4], [6], [8]]

    arr1.flatMap(x => [x * 2]); // [2, 4, 6, 8]

    // Only "flatten" the array returned by the function in flatMap
    arr1.flatMap(x => [[x * 2]]); // [[2], [4], [6], [8]]

### String.trimStart() String.trimEnd() 
    "    Hello World".trimStart(); // "Hello World"
    "Hello World    ".trimStart(); // "Hello World"

### Object.fromEntries()
    const map = new Map([ 
      ['foo', 'bar'], 
      ['baz', 42] 
    ]);
    const obj = Object.fromEntries(map); // { foo: "bar", baz: 42 }

    const arr = [ 
      ['0', 'a'], 
      ['1', 'b'], 
      ['2', 'c'] 
    ];
    const obj = Object.fromEntries(arr); // { 0: "a", 1: "b", 2: "c" }
    
## generators (Serve values on demand rather )

	function * iterableObj() {
	  yield 1;
	  yield 2;
	  yield 3;
	  yield 4;
	  // return 10,
	  yield 5;
	  yield 6;
	}
	
	for (const val of iterableObj() ) {
	  console.log(val);
	}
	
	// 1
	// 2
	// 3
	// 4
	// no more values to yield
	// 5
	// 6
	
	console.log( iterableObj() );
	// {value: undefined, done: true}
