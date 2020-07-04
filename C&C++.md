# C AND C++

## Hello world

```C
include <stdio.h>

int main( void )
{
  printf( "Welcome to C!\n" );
  return 0;
}

```

## Arrays

```c
int a[5],
    b[5] = {1,2,3,4,5},
    c[ ] = {1,2,3,4,5};

```

## Pointers

```C
int y =5,
    *yPtr;

yPtr = &y; // assign the address of y
printf( "%d", *yPtr ); // 5

printf( "%p", &y);    // print the address of y
printf( "%p", yPtr ); // print the address of y

printf( "%p", *&yPtr ); // print the address because *& complement each other
printf( "%p", &*yPtr ); // print the address
```

## Passing parameters by reference

```C
void cubeByReference( int *nPtr ) {
  *nPtr = _nPtr _ _nPtr _ *nPtr;
}

int number = 5;
//main
cubeByReference( &number ); //the value of 5 after this call will change
```

### pointer as array

```C
//convertToUppercase( "hello world" );

#include <ctype.h>

void convertToUppercase( cahr *sPtr)
{
  while(*sPtr != '\0'){
    *sPtr = toupper( *sPtr );
    ++sPtr;
  }
}

//printCharacters("Fabrice Feugang")
void printCharacters( const char *sPtr )
{
  for ( ; *sPtr != '\0'; sPtr++ ) {
    printf( "%c", *sPtr );
  }
}

```

### Pointer Expressions and Arithmetic

```C
int v[5] = {1,2,3,4,5},
*vPtr;

vPtr = v;
vPtr = &v[0];

vPtr++; // next index

int a = {1,2,3,4,5}
int *b;

b = a;
b = &a[0];

a[3];
*(b +3);
&b[3];
b+3;
b[3]

```

## Structures

```C
strcut employee {
  char *face; // string face or char face[];
  char *suite;
  char firstName[20];
  int age;
};//do not forget semicolon

struct employee a, b[10], *c;

struct test{} a, b, c;

typedef

typedef struct myStruct a;
typedef struct {} myStruct; myStruct a;
```

## Enum

```C
enum days {ONE, TWO, ..., THREE}; // days.ONE = 0 and days.NINETY = 89
enum days {ONE=2, Tue, }; // start counting from 2
```

## File

```C
FILE *cfPtr ;
int account;
char name[10];
double balance;

if( ( cfPtr = fopen("finame.ext", "W") ) != NULL){
scanf("%d %s %lf", &account, name, &balance)

    while ( !feof( stdin ) ){
        fprintf( cfPtr, "%d %s %.2f\n", account, name, balance );
        printf( "? " );
        scanf( "%d %s %lf", &account, name, &balance );
    }
    fclose( cfPtr );
}

```
