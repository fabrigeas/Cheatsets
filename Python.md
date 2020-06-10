# Welcome Humans,

## Links

- [Python Tutorial – Learn Programming Step By Step](http://www.techbeamers.com/python-tutorial-step-by-step/#web-app-development)

- [Web Programming in Python](https://wiki.python.org/moin/WebProgramming)

- [Download python](https://www.python.org/ftp/python/3.6.5/python-3.6.5.exe)

## What I nee to deepen

- Variables
    - Lists
    - Tuples
- Object Oriented
    - Classes and Objects
- Error handling
- CGI
    - Web development
- Maybe also GUI

Test python: in shell type

    python
# --------------- Here we go ----------
Hello world
    
    #this is a comment
    #python.py
    print("This line will be printed.")
    #shell
    python python.py

Indentation and semicolons

    #tab or 4 spaces are used for blocks
    if True:
        print "True"
    else:
        print "False"

    expression; anotherExpression; Another; andSoOn

Quotation

    word = 'word'
    sentence = "This is a sentence."
    paragraph = """This is a paragraph. It is
    made up of multiple lines and sentences."""

[Variables](https://www.tutorialspoint.com/python/python_variable_types.htm)


    myInt = 1
    myInt = int(1)

    myFloat = 7.0
    myFloat = float(7)

    myString = 'hello'
    myString = str("hello")

Strings

    str = 'Hello World!'

    print str          # Prints complete string
    print str[0]       # Prints first character of the string
    print str[2:5]     # Prints characters starting from 3rd to 5th
    print str[2:]      # Prints string starting from 3rd character
    print str * 2      # Prints string two times
    print str + "TEST" # Prints concatenated string

    # List
    list = [ 'abcd', 786 , 2.23, 'john', 70.2 ]
    tinylist = [123, 'john']
    theList = [iter for iter in range(5)] # [0, 1, 2, 3, 4]
    
    print ([x+y for x in 'get' for y in 'set']) # ['gs', 'ge', 'gt', 'es', 'ee', 'et', 'ts', 'te', 'tt']
    
    print ([x+y for x in 'get' for y in 'set' if x != 't' and y != 'e' ]) #
    
    # Tuples
    tuple = ( 'abcd', 786 , 2.23, 'john', 70.2  )
    tinytuple = (123, 'john')

    # Dictionary

    dict = {}
    dict['one'] = "This is one"
    dict[2]     = "This is two"

    tinydict = {'name': 'john','code':6734, 'dept': 'sales'}


    print dict['one']       # Prints value for 'one' key
    print dict[2]           # Prints value for 2 key
    print tinydict          # Prints complete dictionary
    print tinydict.keys()   # Prints all the keys
    print tinydict.values() # Prints all the values

[Basic operations](https://www.learnpython.org/en/Basic_Operators)

    number = 1 + 2 * 3 / 4.0 #2.5
    remainder = 11 % 3 #2

    squared = 7 ** 5 # 7^5
    cubed = 2 ** 3   # 2^3

    helloworld = "hello" + " " + "world" # "hello world"
    lotsofhellos = "hello" * 3 # "hellohellohello

    even_numbers = [2,4,6,8]
    odd_numbers = [1,3,5,7]
    all_numbers = odd_numbers + even_numbers #[1, 3, 5, 7, 2, 4, 6, 8]

    print([1,2,3] * 3) # [1, 2, 3, 1, 2, 3, 1, 2, 3]

[String Formatting](https://www.learnpython.org/en/String_Formatting)

    name = "John"
    print("Hello, %s!" % name) # Hello, John!

    print("%s is %d years old." % (name, age)) # John is 23 years old.

    mylist = [1,2,3,'anything']
    name = 'fabrice'
    print("all: %s %s" % (name,mylist) )  # all: fabrice [1, 2, 3, 'anything']

    # Conversion specifiers

    %s - String (or any object with a string representation, like numbers)

    %d - Integers
    %f - Floating point numbers
    %.3f : 3 digits as float
    %.3d : 3 digits (converted to int)
    %x/%X - Integers in hex representation (lowercase/uppercase)

[Conditions](https://www.learnpython.org/en/Conditions)

    x = 2
    print(x == 2) # True
    print(x == 3) # False
    print(x < 3) # True

    # and or
    if name == "John" and age == 23 or something != someOther:
        someThingHere

    # in
    if 1 in [1,2,3,4,5,6,]:
        print("yep")
    
    if expression : 
    suite
    elif expression : 
    suite 
    else : 
    suite

[Loops](https://www.learnpython.org/en/Loops)

    collection = [1,2,3,4,5]
                 # range(5) 
                 # range(3, 6)
                 # range(3, 8, 2)
    for i in [2, 3, 5, 7]:
        print(i)

    count = 0
    while True:
        print(count)
        count += 1
        if count >= 5:
            break

    for x in range(10):
        if x % 2 == 0:
            continue
        print(x)
    
    # loops with else
    for i in range(1, 10): # while(count<5):
        if(i%5==0):
            break
        code
    else:
        code
    else code is executed once the conition becomes false, event if continue is use. Else coe will be skippe if 'break' is use

[Functions](https://www.learnpython.org/en/Functions)

    def maxOfBoth(a, b):
        if a > b:
            return a
        else:
            return b
    print( maxOfBoth(2,4) )

[Classes and Objects](https://www.learnpython.org/en/Classes_and_Objects)

    class Employee:
        'Common base class for all employees'
        empCount = 0

        def __init__(self, name, salary):
            self.name = name
            self.salary = salary
            Employee.empCount += 1
        
        def displayCount(self):
            print ("Total Employee %d" % Employee.empCount)

        def displayEmployee(self):
            print ("Name : ", self.name,  ", Salary: ", self.salary)
    
    # Cleaner extends Emplyee
    class Cleaner(Employee):
        super().__init(sel, name, salary)

    emp1 = Employee("Zara", 2000)

Files I/O [tutorials point](https://www.tutorialspoint.com/python/python_files_io.htm)
,[tech beamer](http://www.techbeamers.com/python-file-handling-tutorial-beginners/)


    name = input("Enter your input: ");
    print ("Name is : ", name)

[Serializing | DeSerializing](http://www.techbeamers.com/python-tutorial-using-pickle-for-serializing-python-objects/)

[Multithreading](http://www.techbeamers.com/python-multithreading-concepts/
)
[CGI](https://www.tutorialspoint.com/python/python_cgi_programming.htm)

