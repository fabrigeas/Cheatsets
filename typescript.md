# Typescript

## [Basic Types](https://www.typescriptlang.org/docs/handbook/basic-types.html)

## [Interfaces](https://www.typescriptlang.org/docs/handbook/interfaces.html)

    An interface is like a signature for objects.

The problem

    function calculateBMI( person: {height: number, weight: number} ){}
    calculateBMI( {height, weight, ..otherAttributes);

The solutions

    interface Person {
        weight: number,
        height?: number, // ? optional property
        readonly height: number, // for properties and attributes VS const => variables
    }

    function calculateBMI( person: Person){} <-- You Use the Interface as the functions's signature
    calculateBMI({weight: 1, height: 2}); // ok

ReadonlyArray

    let constArray ReadonlyArray<number> = [01, 2, 3]; // the array, elements can be init but not reassigned;

Excess Property Checks

    calculateBMI( {weight: 1, height: 2, age: 23}); // error: 'age' not expected in type 'Person'
    calculateBMI( {weight: 1, height: 2, age: 23} as Person ); // would do the trick
    
    # index signature # The better solution 
    interface Person {
        weight?: number,
        height?: number,
        [propName: string]: any
    }
    // This means, a person can have weight, height, then any number of other properties

Function types (using interfaces as function signature)

    interface FunctionSignature { 
        (source: string, substring: string): boolean; 
    }
    
    let anImplementation: FunctionSignature = function ( src: string, sub: string) {
        return src === sub; // must return a boolean
    }
    
    let anotherImplementation: FunctionSignature = function ( src, sub) {
        return src ; // [Error] Type 'string' is not assignable to type 'boolean'
    }

Indexable Types

    interface StringArray {
        [index: number]: string;
    }
    
    let myArray: StringArray = ["A", "B"];
    myArray[0]; //

## Classes

    Clases with Protected constructor can only be extended not instantiated
      class Animal {
          name: string; // is equivalent to public name: string
          private myPrivate: string; // private
          protected myProtected: string
          constructor(theName: string) { this.name = theName; } // = public constructor
          move(distanceInMeters: number = 0) { // public move
              console.log(`${this.name} moved ${distanceInMeters}m.`);
          }
      }

      class Snake extends Animal {
          constructor(name: string) { super(name); }  // Call super here to trigger the constructor from base class
          move(distanceInMeters = 5) {
              super.move(distanceInMeters);
          }
      }

      class Horse extends Animal {
          constructor(name: string) { super(name); }
          move(distanceInMeters = 45) {
              console.log("Galloping...");
              super.move(distanceInMeters);
          }
      }

      let sam = new Snake("Sammy the Python");
      let tom: Animal = new Horse("Tommy the Palomino");

      sam.move();
      tom.move(34);

### Parameter properties: Declare class parameters directly in the constructor using readonly or accessibility modifiers

    class Octopus {
        readonly numberOfLegs: number = 8;
        constructor(
            myProperty: number,
            readonly myreadonly: string,
            private myPrivate: string,
            protected myProtected: string,
            public myPublic: string) {
        }
    }

    let fab = new Octopus(1, "readonly", "private", "protecte", "public");

    fab.myProperty  = "faugang"; // Cannot assign to 'name' because it is a read-on1ly property
    fab.myreadonly  = "faugang"; // Cannot assign to 'myreadonly' because it is a read-only property
    fab.myPrivate   = "faugang"; // Property 'myPrivate' is private and only accessible within class
    fab.myPublic    = "faugang";

    console.log(fab.myProperty) // Property 'myProperty' does not exist on type
    console.log(fab.myreadonly) // "myReadonly"
    console.log(fab.myPrivate) // Property 'myPrivate' is private and only accessible within class 'Octopus'
    console.log(fab.myPublic) // fabrifauganggeas

### Accessors {get, set}

    class Employee {
        private _fullName: string;

        get fullName(): string {
            return this._fullName;
        }

        set fullName(newName: string) {
            if (newName && newName.length > fullNameMaxLength) {
                throw new Error("fullName has a max length of " + fullNameMaxLength);
            }
            
            this._fullName = newName;
        }
    }

    let employee = new Employee();

    employee.fullName = "Bob Smith";  // you don't need to call set
    if (employee.fullName) { 
        console.log(employee.fullName); // you don't need to call get
    }

### Static properties

    class Grid {
        static origin = {x: 0, y: 0}; <-- Declare static property
        calculateDistanceFromOrigin(point: {x: number; y: number;}) {
            let xDist = (point.x - Grid.origin.x);  <-- Access static properties using classname instead of this
            let yDist = (point.y - Grid.origin.y);  
            return Math.sqrt(xDist * xDist + yDist * yDist) / this.scale;
        }
        constructor (public scale: number) { }
    }

    console.log(new Grid(1.0).calculateDistanceFromOrigin({x: 10, y: 10})); // 14.142135623730951
    console.log(new Grid(5.0).calculateDistanceFromOrigin({x: 10, y: 10})); // 2.8284271247461903

### Abstract Classes (cannot be instantiated, should be implemented by subclasses)

    abstract class Department {

        constructor(public name: string) {}

        printName(): void {
            console.log("Department name: " + this.name);
        }

        abstract printMeeting(): void; <-- must be implemented in derived classes
    }

    class AccountingDepartment extends Department {

        constructor() {
            super("Accounting and Auditing"); <-- constructors in derived classes must call super()
        }

        printMeeting(): void {
            console.log("The Accounting Department meets each Monday at 10am.");
        }

        generateReports(): void { <-- Must be implemented
            console.log("Generating accounting reports...");
        }
    }

    let department: Department; <-- ok to create a reference to an abstract type

    department = new Department(); <-- error: cannot create an instance of an abstract class
    department = new AccountingDepartment(); <-- ok to create and assign a non-abstract subclass
    department.printName();
    department.printMeeting();
    department.generateReports(); <-- error: method doesn't exist on declared abstract type

## [Functions](https://www.typescriptlang.org/docs/handbook/functions.html#optional-and-default-parameters)

    let myAdd: (baseValue: number, increment: number) => number =
      function(x: number, y: number): number { return x + y; };

    let myAdd: (baseValue: number, increment: number) => number =
      function(x, y) { return x + y; };

### Optional Parameters

    function buildName(firstName: string, lastName?: string) {
        if (lastName)
            return firstName + " " + lastName;
        else
            return firstName;
    }

    let result1 = buildName("Bob");                  // works correctly now
    let result3 = buildName("Bob", "Adams");         // ah, just right
    let result2 = buildName("Bob", "Adams", "Sr.");  // error, too many params

### Optional and Default Parameters

    function buildName(firstName: string, lastName = "Smith") {
        return firstName + " " + lastName;
    }

    let result1 = buildName("Bob");                  // works correctly now, returns "Bob Smith"
    let result2 = buildName("Bob", undefined);       // still works, also returns "Bob Smith"
    let result3 = buildName("Bob", "Adams", "Sr.");  // error, too many params
    let result4 = buildName("Bob", "Adams");         // ah, just right

### Optional and Default-Initialized Parameters

    function buildName(firstName = "Will", lastName: string) {
        return firstName + " " + lastName;
    }

    let result1 = buildName("Bob");                  // error, too few params
    let result2 = buildName("Bob", "Adams", "Sr.");  // error, too many params
    let result3 = buildName("Bob", "Adams");         // okay and returns "Bob Adams"
    let result4 = buildName(undefined, "Adams");     // okay and returns "Will Adams" default-initialized params that come before required params need to explicitly pass undefinned

### Rest parameters

    function buildName(firstName: string, ...anyThingFollowing3Ellipses: string[]) {
        return firstName + " " + anyThingFollowing3Ellipses.join(" ");
    }
    
    let employeeName = buildName("Joseph", "Samuel", "Lucas", "MacKinzie"); <-- "Joseph Samuel Lucas MacKinzie"