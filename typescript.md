# Typescript

[Basic Types](https://www.typescriptlang.org/docs/handbook/basic-types.html)\
[Interfaces](https://www.typescriptlang.org/docs/handbook/interfaces.html)

An interface is like a signature for objects.

## Types

```ts
// numbers
let decimal: number = 6;
let hex: number = 0xf00d;
let binary: number = 0b1010;
let octal: number = 0o744;
let big: bigint = 100n;

// string
let color: string = "blue";
color = "red";

// array
let list: number[] = [1, 2, 3];
let list: Array<number> = [1, 2, 3];

// type
let x: [string, number];

x = ["hello", 10]; // OK
x = [10, "hello"]; // Error

// enum
enum Color {
  Red, // 0
  Green, // 1
  Blue, // 2
}

enum Color {
  Red = 1, //1
  Green, // 2
  Blue, // 3
}
let c: Color = Color.Green;

enum Color {
  Red = 1,
  Green = 2,
  Blue = 4,
}

let colorName: string = Color[2]; // 'Green'

// any
let notSure: any = 4;
notSure = "maybe a string instead";
notSure = false; // okay, definitely a boolean
notSure.ifItExists(); // okay, ifItExists might exist at runtime
notSure.toFixed(); // okay, toFixed exists (but the compiler doesn't check)

let prettySure: Object = 4;
prettySure.toFixed(); // Error: Property 'toFixed' doesn't exist on type 'Object'.

// Null and Undefined
let u: undefined = undefined;
let n: null = null;

// object

declare function create(o: object | null): void;

create({ prop: 0 }); // OK
create(null); // OK
create(42); // Error Argument of type '42' is not assignable to parameter of type 'object | null'.
create("string"); // Error Argument of type '"string"' is ... 'object | null'.
create(false); // Error Argument of type 'false' ... 'object | null'.
create(undefined); // Error  Argument of type 'undefined' is ... type 'object | null'.
```

## Interfaces

```ts
// The problem
function calculateBMI( person: {height: number, weight: number} ){ }
calculateBMI( {height, weight, ..otherAttributes);

// The solutions
interface Person {
    weight: number,
    height?: number, // ? optional property
    readonly height: number, // for properties and attributes VS const => variables
}

function calculateBMI( person: Person){} //You Use the Interface as the fx signature
calculateBMI({weight: 1, height: 2}); // ok

// ReadonlyArray:the array, elements can be init but not reassigned
let constArray ReadonlyArray<number> = [01, 2, 3];

// Excess Property Checks
calculateBMI( {weight: 1, height: 2, age: 23}); // error: 'age' not expected in type 'Person'
calculateBMI( {weight: 1, height: 2, age: 23} as Person ); // would do the trick

// index signature # The better solution
// a person can have weight, height, then any number of other properties
interface Person {
  weight?: number,
  height?: number,
  [propName: string]: any
}

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

// Indexable Types
interface StringArray {
    [index: number]: string;
}

let myArray: StringArray = ["A", "B"];
myArray[0]; //
```

## Classes

```ts
// Classes with Protected constructor can only be extended not instantiated
class Animal {
  name: string; // is equivalent to public name: string
  private myPrivate: string; // private
  protected myProtected: string;

  constructor(theName: string) {
    this.name = theName;
  }
  // = public constructor
  move(distanceInMeters: number = 0) {
    // public move
    console.log(`${this.name} moved ${distanceInMeters}m.`);
  }
}

class Snake extends Animal {
  constructor(name: string) {
    super(name); // Call super here to trigger the constructor from base class
  }
  move(distanceInMeters = 5) {
    super.move(distanceInMeters);
  }
}

class Horse extends Animal {
  constructor(name: string) {
    super(name);
  }
  move(distanceInMeters = 45) {
    console.log("Galloping...");
    super.move(distanceInMeters);
  }
}

let sam = new Snake("Sammy the Python");
let tom: Animal = new Horse("Tommy the Palomino");

sam.move();
tom.move(34);
```

### Parameter properties: Declare class parameters directly in the constructor using readonly or accessibility modifiers

```ts
class Octopus {
  readonly numberOfLegs: number = 8;
  constructor(
    myProperty: number,
    readonly myreadonly: string,
    private myPrivate: string,
    protected myProtected: string,
    public myPublic: string
  ) {}
}

let fab = new Octopus(1, "readonly", "private", "protecte", "public");

fab.myProperty = "faugang"; // Cannot assign to 'name' because it is a read-on1ly property
fab.myreadonly = "faugang"; // Cannot assign to 'myreadonly' because it is a read-only property
fab.myPrivate = "faugang"; // Property 'myPrivate' is private and only accessible within class
fab.myPublic = "faugang";

console.log(fab.myProperty); // Property 'myProperty' does not exist on type
console.log(fab.myreadonly); // "myReadonly"
console.log(fab.myPrivate); // Property 'myPrivate' is private and only accessible within class 'Octopus'
console.log(fab.myPublic); // fabrifauganggeas
```

### Accessors {get, set}

```ts
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

employee.fullName = "Bob Smith"; // you don't need to call set
if (employee.fullName) {
  console.log(employee.fullName); // you don't need to call get
}
```

### Static properties

```ts
class Grid {
  static origin = {x: 0, y: 0}; <-- Declare static property
  calculateDistanceFromOrigin(point: {x: number; y: number;}) {
      let xDist = (point.x - Grid.origin.x);  // Access static properties using classname instead of this
      let yDist = (point.y - Grid.origin.y);
      return Math.sqrt(xDist * xDist + yDist * yDist) / this.scale;
  }
  constructor (public scale: number) { }
}

console.log(new Grid(1.0).calculateDistanceFromOrigin({x: 10, y: 10})); // 14.142135623730951
console.log(new Grid(5.0).calculateDistanceFromOrigin({x: 10, y: 10})); // 2.8284271247461903
```

### Abstract Classes (cannot be instantiated, should be implemented by subclasses)

```ts
abstract class Department {
  constructor(public name: string) {}

  printName(): void {
    console.log("Department name: " + this.name);
  }

  abstract printMeeting(): void; // must be implemented in derived classes
}

class AccountingDepartment extends Department {
  constructor() {
    super("Accounting and Auditing"); // constructors in derived classes must call super()
  }

  printMeeting(): void {
    console.log("The Accounting Department meets each Monday at 10am.");
  }

  // Must be implemented
  generateReports(): void {
    console.log("Generating accounting reports...");
  }
}

let department: Department; // ok to create a reference to an abstract type

department = new Department(); // error: cannot create an instance of an abstract class
department = new AccountingDepartment(); // ok to create and assign a non-abstract subclass
department.printName();
department.printMeeting();
department.generateReports(); // error: method doesn't exist on declared abstract type
```

## [Functions](https://www.typescriptlang.org/docs/handbook/functions.html#optional-and-default-parameters)

```ts
let myAdd: (baseValue: number, increment: number) => number =
function(x: number, y: number): number { return x + y; };

let myAdd: (baseValue: number, increment: number) => number = \
function(x, y) { return x + y; };

// Optional Parameters
function buildName(firstName: string, lastName?: string) {
  if (lastName)
    return firstName + " " + lastName;
  else
    return firstName;
}

let result1 = buildName("Bob");                  // works correctly now
let result3 = buildName("Bob", "Adams");         // ah, just right
let result2 = buildName("Bob", "Adams", "Sr.");  // error, too many params

// Optional and Default Parameters
function buildName(firstName: string, lastName = "Smith") {
  return firstName + " " + lastName;
}

let result1 = buildName("Bob");                  // works correctly now, returns "Bob Smith"
let result2 = buildName("Bob", undefined);       // still works, also returns "Bob Smith"
let result3 = buildName("Bob", "Adams", "Sr.");  // error, too many params
let result4 = buildName("Bob", "Adams");         // ah, just right

// Optional and Default-Initialized Parameters

function buildName(firstName = "Will", lastName: string) {
  return firstName + " " + lastName;
}

let result1 = buildName("Bob");                  // error, too few params
let result2 = buildName("Bob", "Adams", "Sr.");  // error, too many params
let result3 = buildName("Bob", "Adams");         // okay and returns "Bob Adams"
let result4 = buildName(undefined, "Adams");     // okay and returns "Will Adams" default-initialized params that come before required params need to explicitly pass undefinned

// Rest parameters
function buildName(firstName: string, ...anyThingFollowing3Ellipses: string[]) {
  return firstName + " " + anyThingFollowing3Ellipses.join(" ");
}

// "Joseph Samuel Lucas MacKinzie"
let employeeName = buildName("Joseph", "Samuel", "Lucas", "MacKinzie");
```

## Literal Types

```ts
function padLeft(value: string, padding: string | number) {}

// Error Argument of type 'boolean' is not assignable to parameter of type 'string | number'.
padLeft("Hello world", true);



interface Bird {
  fly(): void;
  layEggs(): void;
}

interface Fish {
  swim(): void;
  layEggs(): void;
}

declare function getSmallPet(): Fish | Bird;

let pet = getSmallPet();
pet.layEggs();

// Only available in one of the two possible types
pet.swim();
Property 'swim' does not exist on type 'Bird | Fish'.
  Property 'swim' does not exist on type 'Bird'.
```

## Generics

```ts
function identity<T>(arg: T): T {
  return arg;
}
let output = identity<string>("myString"); // type of output will be 'string'
let output = identity("myString"); // type of output will be 'string'(cleaner)
```

### Generic Type Variables

```ts
function loggingIdentity<T>(arg: T): T {
  console.log(arg.length); // Error: T doesn't have .length
  return arg;
}

function loggingIdentity<T>(arg: T[]): T[] {
  console.log(arg.length); // Array has a .length, so no more error
  return arg;
}

function loggingIdentity<T>(arg: Array<T>): Array<T> {
  console.log(arg.length); // Array has a .length, so no more error
  return arg;
}
```

### Generic Types

```ts
function identity<T>(arg: T): T {
  return arg;
}
let myIdentity: <T>(arg: T) => T = identity;
let myIdentity: <U>(arg: U) => U = identity; // can use any upperCase letter

// We can also write the generic type as a call signature of an object literal type:
interface GenericIdentityFn {
  <T>(arg: T): T;
}

function identity<T>(arg: T): T {
  return arg;
}

let myIdentity: GenericIdentityFn = identity;

interface GenericIdentityFn<T> {
  (arg: T): T;
}

function identity<T>(arg: T): T {
  return arg;
}

let myIdentity: GenericIdentityFn<number> = identity;
```

### Generic Classes

```ts
// Generic classes
class GenericCls<T> {
  zeroValue: T;
  add: (x: T, y: T) => T;
}

let genericNum = new GenericCls<number>();
genericNum.zeroValue = 0;
genericNum.add = function (x, y) {
  return x + y;
};

let genericStr = new GenericCls<string>();
genericStr.zeroValue = "";
genericStr.add = function (x, y) {
  return x + y;
};

console.log(genericStr.add(genericStr.zeroValue, "test"));

// Generic Constraints
interface Lengthwise {
  length: number;
}

function loggingIdentity<T extends Lengthwise>(arg: T): T {
  console.log(arg.length); // Now we know it has a .length property, so no more error
  return arg;
}
loggingIdentity(3); // Error, number doesn't have a .length property
loggingIdentity({ length: 10, value: 3 }); // ok
```

### Using Type Parameters in Generic Constraints

```ts
function getProperty<T, K extends keyof T>(obj: T, key: K) {
  return obj[key];
}

let x = { a: 1, b: 2, c: 3, d: 4 };

getProperty(x, "a"); // okay
getProperty(x, "m"); // error: Argument of type 'm' isn't assignable to 'a' | 'b' | 'c' | 'd'.
```

### Using Class Types in Generics

```ts
function create<T>(c: { new (): T }): T {
  return new c();
}

class BeeKeeper {
  hasMask: boolean;
}

class ZooKeeper {
  nameTag: string;
}

class Animal {
  numLegs: number;
}

class Bee extends Animal {
  keeper: BeeKeeper;
}

class Lion extends Animal {
  keeper: ZooKeeper;
}

function createInstance<A extends Animal>(c: new () => A): A {
  return new c();
}

createInstance(Lion).keeper.nameTag; // typechecks!
createInstance(Bee).keeper.hasMask; // typechecks!
```

## Decorators

```json
{
  "compilerOptions": {
    "target": "ES5",
    "experimentalDecorators": true
  }
}
```

### Decorator Factories

```ts
// a function that returns the expression that will be called by the decorator at runtime.
function color(value: string) {
  // this is the decorator factory
  return function (target) {
    // this is the decorator
    // do something with 'target' and 'value'...
  };
}

// Example
function configurable(value: boolean) {
  return function (
    target: any,
    propertyKey: string,
    descriptor: PropertyDescriptor
  ) {
    descriptor.configurable = value;
  };
}
```

### Decorator Composition

```ts
// On single line:
@f @g x

// On multiple lines:
@f
@g
x

function f() {
  console.log("f(): evaluated");
  return function(target, propertyKey: string, descriptor: PropertyDescriptor) {
    console.log("f(): called");
  };
}

function g() {
  console.log("g(): evaluated");
  return function(target, propertyKey: string, descriptor: PropertyDescriptor) {
    console.log("g(): called");
  };
}

class C {
  @f()
  @g()
  method() {}
}

// output
f(): evaluated
g(): evaluated
g(): called
f(): called

// Parameter Decorators, followed by Method, Accessor, or Property Decorators are applied for each instance member.
// P arameter Decorators, followed by Method, Accessor, or Property Decorators are applied for each static member.
// Parameter Decorators are applied for the constructor.
// Class Decorators are applied for the class.
```

### Decorator Evaluation

There is a well defined order to how decorators applied to various declarations inside of a class are applied:

Parameter Decorators, followed by Method, Accessor, or Property Decorators are applied for each instance member.
Parameter Decorators, followed by Method, Accessor, or Property Decorators are applied for each static member.
Parameter Decorators are applied for the constructor.
Class Decorators are applied for the class.

### Class Decorators

```ts
function sealed(constructor: Function) {
  Object.seal(constructor);
  Object.seal(constructor.prototype);
}

@sealed
class Greeter {
  greeting: string;
  constructor(message: string) {
    this.greeting = message;
  }
  greet() {
    return "Hello, " + this.greeting;
  }
}

// Override the constructor
function classDecorator<T extends { new (...args: any[]): {} }>(
  constructor: T
) {
  return class extends constructor {
    newProperty = "new property";
    hello = "override";
  };
}

@classDecorator
class Greeter {
  property = "property";
  hello: string;
  constructor(m: string) {
    this.hello = m;
  }
}

console.log(new Greeter("world"));
```

### Method Decorators

```ts
function enumerable(value: boolean) {
  return function (
    target: any,
    propertyKey: string,
    descriptor: PropertyDescriptor
  ) {
    descriptor.enumerable = value;
  };
}

class Greeter {
  greeting: string;
  constructor(message: string) {
    this.greeting = message;
  }

  @enumerable(false)
  greet() {
    return "Hello, " + this.greeting;
  }
}
```

### Accessor Decorators

```ts
class Point {
  private _x: number;
  private _y: number;
  constructor(x: number, y: number) {
    this._x = x;
    this._y = y;
  }

  @configurable(false)
  get x() {
    return this._x;
  }

  @configurable(false)
  get y() {
    return this._y;
  }
}

function configurable(value: boolean) {
  return function (
    target: any,
    propertyKey: string,
    descriptor: PropertyDescriptor
  ) {
    descriptor.configurable = value;
  };
}
```

### Parameter Decorators

```ts
class Greeter {
  greeting: string;

  constructor(message: string) {
    this.greeting = message;
  }

  @validate
  greet(@required name: string) {
    return "Hello " + name + ", " + this.greeting;
  }
}

import "reflect-metadata";

const requiredMetadataKey = Symbol("required");

function required(
  target: Object,
  propertyKey: string | symbol,
  parameterIndex: number
) {
  let existingRequiredParameters: number[] =
    Reflect.getOwnMetadata(requiredMetadataKey, target, propertyKey) || [];
  existingRequiredParameters.push(parameterIndex);
  Reflect.defineMetadata(
    requiredMetadataKey,
    existingRequiredParameters,
    target,
    propertyKey
  );
}

function validate(
  target: any,
  propertyName: string,
  descriptor: TypedPropertyDescriptor<Function>
) {
  let method = descriptor.value;
  descriptor.value = function () {
    let requiredParameters: number[] = Reflect.getOwnMetadata(
      requiredMetadataKey,
      target,
      propertyName
    );
    if (requiredParameters) {
      for (let parameterIndex of requiredParameters) {
        if (
          parameterIndex >= arguments.length ||
          arguments[parameterIndex] === undefined
        ) {
          throw new Error("Missing required argument.");
        }
      }
    }

    return method.apply(this, arguments);
  };
}
```
