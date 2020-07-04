## hello world

code

    #!/usr/bin/perl

    print "Hello, world\n";

execute 

    chmod 0755 hello.pl
    ./hello.pl

comments

    #!/usr/bin/perl

    # This is a single line comment

    print "Hello, world\n";

    =begin comment
    This is all part of multiline comment.
    You can use as many lines as you like
    These comments will be ignored by the 
    compiler until the next =cut is encountered.
    =cut

Single and Double Quotes in Perl

    print "Hello, world\n";

    print 'Hello, world\n'; 
    -------------------------------------------
    Hello, world
    Hello, world\n$

Here document

    #!/usr/bin/perl

    $a = 10;
    $var = <<"EOF";
    This is the syntax for here document and it will continue
    until it encounters a EOF in the first line.
    This is case of double quote so variable value will be 
    interpolated. For example value of a = $a
    EOF
    print "$var\n";

    

    $var = <<'EOF';
    This is case of single quote so variable value will not be 
    interpolated. For example value of a = $a
    EOF
    print "$var\n";
    --------------------------------------------
    This is the syntax for here document and it will continue
    until it encounters a EOF in the first line.
    This is case of double quote so variable value will be
    interpolated. For example value of a = 10

    

    This is case of single quote so variable value will be
    interpolated. For example value of a = $a

variables

    #!/usr/bin/perl

    $age = 25;             # An integer assignment
    $name = "John Paul";   # A string 
    $salary = 1445.50;     # A floating point

    print "Age = $age\n";
    print "Name = $name\n";
    print "Salary = $salary\n";

arrays

    @ages = (25, 30, 40);             
    @names = ("John Paul", "Lisa", "Kumar");

    print "\$ages[0] = $ages[0]\n";
    print "\$ages[1] = $ages[1]\n";
    print "\$ages[2] = $ages[2]\n";
    print "\$names[0] = $names[0]\n";
    print "\$names[1] = $names[1]\n";
    print "\$names[2] = $names[2]\n";

hash variables

    #!/usr/bin/perl

    %data = ('John Paul', 45, 'Lisa', 30, 'Kumar', 40);

    

    print "\$data{'John Paul'} = $data{'John Paul'}\n";
    print "\$data{'Lisa'} = $data{'Lisa'}\n";
    print "\$data{'Kumar'} = $data{'Kumar'}\n";
    --------------------------------------------
    $data{'John Paul'} = 45
    $data{'Lisa'} = 30
    $data{'Kumar'} = 40

context

    @names = ('John Paul', 'Lisa', 'Kumar');
    @copy = @names;
    $size = @names; # $size is defined as scalar thus

    

    print "Given names are : @copy\n";
    print "Number of names are : $size\n";
    --------------------------------------
    Given names are : John Paul Lisa Kumar
    Number of names are : 3

scalars

    

    $age = 25;             # An integer assignment
    $name = "John Paul";   # A string 
    $salary = 1445.50;     # A floating point

    print "Age = $age\n";
    print "Name = $name\n";
    print "Salary = $salary\n";

    --------------------------
    Age = 25
    Name = John Paul
    Salary = 1445.5

numeric scalars

    $integer = 200;
    $negative = -300;
    $floating = 200.340;
    $bigfloat = -1.2E-23;

    # 377 octal, same as 255 decimal
    $octal = 0377;

    # FF hex, also 255 decimal
    $hexa = 0xff;

    print "integer = $integer\n";
    print "negative = $negative\n";
    print "floating = $floating\n";
    print "bigfloat = $bigfloat\n";
    print "octal = $octal\n";
    print "hexa = $hexa\n";
    ----------------------------
    integer = 200
    negative = -300
    floating = 200.34
    bigfloat = -1.2e-23
    octal = 255
    hexa = 255

string scalars

    $var = "This is string scalar!";
    $quote = 'I m inside single quote - $var';
    $double = "This is inside single quote - $var";

    $escape = "This example of escape -\tHello, World!";

    print "var = $var\n";
    print "quote = $quote\n";
    print "double = $double\n";
    print "escape = $escape\n";
    ---------------------------------   
    var = This is string scalar!
    quote = I m inside single quote - $var                                                       
    double = This is inside single quote - This is string scalar!                                
    escape = This example of escape -       Hello, World

    

scalar operations

    $str = "hello" . "world";       # Concatenates strings.
    $num = 5 + 10;                  # adds two numbers.
    $mul = 4 * 5;                   # multiplies two numbers.
    $mix = $str . $num;             # concatenates string and number.

    

    print "str = $str\n";
    print "num = $num\n";
    print "mix = $mix\n";
    -------------------------------
    str = helloworld
    num = 15
    mix = helloworld15

multiline string

    $string = 'This is
    a multiline
    string';

    

    print "$string\n";
    ---------------------
    This is
    a multiline
    string

v-string

    

    $smile  = v9786; 
    $foo    = v102.111.111; 
    $martin = v77.97.114.116.105.110; 

    

    print "smile = $smile\n"; 
    print "foo = $foo\n"; 
    print "martin = $martin\n"; 
    -------------------------------
    smile = ☺
    foo = foo
    martin = Martin
    Wide character in print at main.pl line 7.

    

special literals

    print "File name ". __FILE__ . "\n";
    print "Line Number " . __LINE__ ."\n";
    print "Package " . __PACKAGE__ ."\n";

    

    # they can not be interpolated
    print "__FILE__ __LINE__ __PACKAGE__\n";
    ---------------------------------------------
    File name hello.pl
    Line Number 4
    Package main
    __FILE__ __LINE__ __PACKAGE__

arrays

    #!/usr/bin/perl

    @days = qw/Mon Tue Wed Thu Fri Sat Sun/; #qw// tokens separated by // white space

    

    print "$days[0]\n";
    print "$days[1]\n";
    print "$days[2]\n";
    print "$days[6]\n";
    print "$days[-1]\n";
    print "$days[-7]\n";

    

sequential arrays

    

    @var_10 = (1..10); 
    @var_20 = (10..20); 
    @var_abc = (a..z); 

    

    print "@var_10\n"; # Prints number from 1 to 10
    print "@var_20\n"; # Prints number from 10 to 20
    print "@var_abc\n"; # Prints number from a to z
    ------------------------------------------------
    1 2 3 4 5 6 7 8 9 10
    10 11 12 13 14 15 16 17 18 19 20
    a b c d e f g h i j k l m n o p q r s t u v w x y z

array size

    

    @array = (1, 2, 3); 
    $array[50] = 4; 

    

    $size = @array; 
    $max_index = $#array; 

    

    print "Size:  $size\n"; 
    print "Max Index: $max_index\n"; 
    ------------------------------
    Size: 51
    Max Index: 50

###########

    

    # create a simple array
    @coins = ("Quarter", "Dime", "Nickel"); 
    print "1. \@coins  = @coins\n"; 

    

    # add one element at the end of the array
    push(@coins, "Penny"); 
    print "2. \@coins  = @coins\n"; 

    

    # add one element at the beginning of the array
    unshift(@coins, "Dollar"); 
    print "3. \@coins  = @coins\n"; 

    

    # remove one element from the last of the array.
    pop(@coins); 
    print "4. \@coins  = @coins\n"; 

    

    # remove one element from the beginning of the array.
    shift(@coins); 
    print "5. \@coins  = @coins\n"; 
    -------------------------------------

    1. @coins = Quarter Dime Nickel
    2. @coins = Quarter Dime Nickel Penny
    3. @coins = Dollar Quarter Dime Nickel Penny
    4. @coins = Dollar Quarter Dime Nickel
    5. @coins = Quarter Dime Nickel

array slicing

    @days = qw/Mon Tue Wed Thu Fri Sat Sun/;

    

    @weekdays = @days[3,4,5];

    

    print "@weekdays\n";
    --------------------------
    Thu Fri Sat

    

    

    @weekdays = @days[3..5];

    

    print "@weekdays\n";
    --------------------
    Thu Fri Sat

    

array splicing

    

    @nums = (1..20); 
    print "Before - @nums\n"; 

    

    splice(@nums, 5, 5, 21..25); 
    print "After - @nums\n"; 
    --------------------------------
    Before - 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20
    After  - 1 2 3 4 5 21 22 23 24 25 11 12 13 14 15 16 17 18 19 20

string to array

    $var_string = "Rain-Drops-On-Roses-And-Whiskers-On-Kittens";
    $var_names = "Larry,David,Roger,Ken,Michael,Tom";

    

    # transform above strings into arrays.
    @string = split('-', $var_string);
    @names  = split(',', $var_names);

    

    print "$string[3]\n";  # This will print Roses
    print "$names[4]\n";   # This will print Michael
    -------
    Roses
    Michael

    

array to string

    # define Strings
    $var_string = "Rain-Drops-On-Roses-And-Whiskers-On-Kittens";
    $var_names = "Larry,David,Roger,Ken,Michael,Tom";

    

    # transform above strings into arrays.
    @string = split('-', $var_string);
    @names  = split(',', $var_names);

    

    $string1 = join( '-', @string );
    $string2 = join( ',', @names );

    

    print "$string1\n";
    print "$string2\n";
    --------------------------
    Rain-Drops-On-Roses-And-Whiskers-On-Kittens
    Larry,David,Roger,Ken,Michael,Tom

    

array sorting

    # define an array
    @foods = qw(pizza steak chicken burgers);
    print "Before: @foods\n";

    

    # sort this array
    @foods = sort(@foods);
    print "After: @foods\n";
    --------------------------------------
    Before: pizza steak chicken burgers
    After: burgers chicken pizza steak

Merging Arrays

    

    @numbers = (1, 3, (4, 5, 6)); 

    

    print "numbers = @numbers\n"; 
    ------------------------------------
    numbers = 1 3 4 5 6
    +++++++++++++++++++++++++++

    

    @odd = (1, 3, 5); 
    @even = (2, 4, 6); 

    

    @numbers = (@odd, @even); 

    

    print "numbers = @numbers\n"; 
    This will produce the following result −

    

    numbers = 1 3 5 2 4 6

array indexing

    

    $var = (5, 4, 3, 2, 1)[4]; 

    

    print "value of var = $var\n"
    --------------------------------
    value of var = 1

    +

    

    @list = (5, 4, 3, 2, 1)[1..3]; 

    

    print "Value of list = @list\n"; 
    This will produce the following result −

    

    Value of list = 4 3 2

hashes

    

    %data = ('John Paul', 45, 'Lisa', 30, 'Kumar', 40); 

    

    print "\$data{'John Paul'} = $data{'John Paul'}\n"; 
    print "\$data{'Lisa'} = $data{'Lisa'}\n"; 
    print "\$data{'Kumar'} = $data{'Kumar'}\n"; 
    -----------------------------------------------------
    $data{'John Paul'} = 45
    $data{'Lisa'} = 30
    $data{'Kumar'} = 40

creating hashes

    

    $data{'John Paul'} = 45; 
    $data{'Lisa'} = 30; 
    $data{'Kumar'} = 40; 

    +

    %data = ('John Paul', 45, 'Lisa', 30, 'Kumar', 40); 

    +

    %data = ('John Paul' => 45, 'Lisa' => 30, 'Kumar' => 40); 

    +

    %data = (-JohnPaul => 45, -Lisa => 30, -Kumar => 40); 

    

## Accessing Hash Elements

    #!/usr/bin/perl

    %data = ('John Paul' => 45, 'Lisa' => 30, 'Kumar' => 40);

    

    print "$data{'John Paul'}\n";
    print "$data{'Lisa'}\n";
    print "$data{'Kumar'}\n";
    -------------------------------
    45
    30
    40

## Extracting Slices

    #!/uer/bin/perl

    

    %data = (-JohnPaul => 45, -Lisa => 30, -Kumar => 40);

    

    @array = @data{-JohnPaul, -Lisa};

    

    print "Array : @array\n";
    ------------------------------
    Array : 45 30

    

## Extracting Keys and Values

#!/usr/bin/perl 

    %data = ('John Paul' => 45, 'Lisa' => 30, 'Kumar' => 40);

    

    @names = keys %data;

    

    print "$names[0]\n"; 
    print "$names[1]\n"; 
    print "$names[2]\n"; 
    --------------------------
    Lisa
    John Paul
    Kumar

    +

    %data = ('John Paul' => 45, 'Lisa' => 30, 'Kumar' => 40);

    

    @ages = values %data;

    

    print "$ages[0]\n";
    print "$ages[1]\n";
    print "$ages[2]\n";
    -----------------------------------------
    30
    45
    40

Checking for Existence
#!/usr/bin/perl

    

    %data = ('John Paul' => 45, 'Lisa' => 30, 'Kumar' => 40); 

    

    if( exists($data{'Lisa'} ) ) {
       print "Lisa is $data{'Lisa'} years old\n"; 
    } else {
       print "I don't know age of Lisa\n"; 
    }
    -------------------------------------------------------
    Lisa is 30 years old

    

## Getting Hash Size

    #!/usr/bin/perl

    %data = ('John Paul' => 45, 'Lisa' => 30, 'Kumar' => 40);

    

    @keys = keys %data;
    $size = @keys;
    print "1 - Hash size:  is $size\n";

    

    @values = values %data;
    $size = @values;
    print "2 - Hash size:  is $size\n";
    -----------------------------------------------
    1 - Hash size: is 3
    2 - Hash size: is 3

    

## Add and Remove Elements in Hashes

    #!/usr/bin/perl

    

    %data = ('John Paul' => 45, 'Lisa' => 30, 'Kumar' => 40); 

    @keys = keys %data; 
    $size = @keys; 
    print "1 - Hash size:  is $size\n"; 

    

    # adding an element to the hash; 
    $data{'Ali'} = 55; 
    @keys = keys %data; 
    $size = @keys; 
    print "2 - Hash size:  is $size\n"; 

    

    # delete the same element from the hash; 
    delete $data{'Ali'}; 
    @keys = keys %data; 
    $size = @keys; 
    print "3 - Hash size:  is $size\n"; 
    --------------------------------------------------
    1 - Hash size: is 3
    2 - Hash size: is 4
    3 - Hash size: is 3

    

# tertiary operator

    #!/usr/local/bin/perl
     
    $name = "Ali"; 
    $age = 10; 

    

    $status = ($age > 60 )? "A senior citizen" : "Not a senior citizen"; 

    

    print "$name is  - $status\n"; 
    This will produce the following result −

    

    Ali is - Not a senior citizen

# for

    #!/usr/local/bin/perl
     
    for( ; ; ) {
       printf "This loop will run forever.\n";
    }

# functions

    #!/usr/bin/perl

    

    # Function definition
    sub Hello {
       print "Hello, World!\n"; 
    }

    

    # Function call
    Hello(); 

## arguments @_

    #!/usr/bin/perl

    

    sub Average {
     # get total number of arguments passed.
       $n = scalar(@_); #size of parameters
       $sum = 0; 
       # @_[0] first parameter

    

       foreach $item (@_) {
          $sum += $item; 
       }
       $average = $sum / $n; 

    

       print "Average for the given numbers : $average\n"; 
    }

    

    # Function call
    Average(10, 20, 30); 
    -----------------------------------------------
    Average for the given numbers : 20

## passing a list to a function

    #!/usr/bin/perl

    sub PrintList {
       my @list = @_; 
       print "Given list is @list\n"; 
    }
    $a = 10; 
    @b = (1, 2, 3, 4); 
    PrintList($a, @b); 
    ------------------------------
    Given list is 10 1 2 3 4

## passing hashes to functions

    #!/usr/bin/perl

    

    sub PrintHash {
       my (%hash) = @_; 

    

       foreach my $key ( keys %hash ) {
          my $value = $hash{$key}; 
          print "$key : $value\n"; 
       }
    }
    %hash = ('name' => 'Tom', 'age' => 19); 

    

    # Function call with hash parameter
    PrintHash(%hash); 
    --------------------------------------
    name : Tom
    age : 19

    

## returning values from subroutines

    #!/usr/bin/perl

    

    # Function definition
    sub Average {
       # get total number of arguments passed.
       $n = scalar(@_); 
       $sum = 0; 

    

       foreach $item (@_) {
          $sum += $item; 
       }
       $average = $sum / $n; 

    

       return $average; 
    }

    

    # Function call
    $num = Average(10, 20, 30); 
    print "Average for the given numbers : $num\n"; 
    -------------------------------------------------
    Average for the given numbers : 20

    

## private variables

    #!/usr/bin/perl

    

    # Global variable
    $string = "Hello, World!"; 

    

    # Function definition
    sub PrintHello {
       # Private variable for PrintHello function
       my $string; 
       $string = "Hello, Perl!"; 
       print "Inside the function $string\n"; 
    }
    # Function call
    PrintHello(); 
    print "Outside the function $string\n"; 
    ------------------------------------------
    Inside the function Hello, Perl!
    Outside the function Hello, World!

## temporary variables

    #!/usr/bin/perl

    

    # Global variable
    $string = "Hello, World!"; 

    

    sub PrintHello {
       # Private variable for PrintHello function
       local $string; 
       $string = "Hello, Perl!"; 
       PrintMe(); 
       print "Inside the function PrintHello $string\n"; 
    }
    sub PrintMe {
       print "Inside the function PrintMe $string\n"; 
    }

    

    # Function call
    PrintHello(); 
    print "Outside the function $string\n"; 
    ----------------------------------------------
    Inside the function PrintMe Hello, Perl!
    Inside the function PrintHello Hello, Perl!
    Outside the function Hello, World!

## state variables do net get reinitialized after several calls

    #!/usr/bin/perl

    use feature 'state';

    sub PrintCount {
       state $count = 0; # initial value

       print "Value of counter is $count\n";
       $count++;
    }

    for (1..5) {
       PrintCount();
    }
    -------------------------------------
    Value of counter is 0
    Value of counter is 1
    Value of counter is 2
    Value of counter is 3
    Value of counter is 4
