# Less

## Content

  + [Variables](#Variables)
  + [Extend](#Extend)
  + [Merge](#Merge)
  + [Mixins](#Mixins)
  + [Variables](#Variables)
  + [Variables](#Variables)

* [http://lesscss.org/](http://lesscss.org/)
* [Features](http://lesscss.org/features/)

## Set up

    npm install -g less
    npm install grunt grunt-contrib-less grunt-contrib-watch jit-grunt --save-dev

  

    touch /path/input.less 
    touch /path/output.css

    //Compile manually
    write code in the .less file and compile with the following command
    $lessc /path/input.less /path/output.css 

## compile automatically using grunt

    // gruntfile.js
    module.exports = function(grunt) {
        require('jit-grunt')(grunt);
        grunt.initConfig({
            pkg: grunt.file.readJSON('package.json'),
            less: {
                development: {
                    options: {
                        paths: ['public/stylesheets/'],
                        compress: true,
                        yuicompress: true,
                        optimization: 2
                    },
                    files: {
                        'public/stylesheets/chat.css': 'public/stylesheets/style.less'
                    }
                }
            },
            watch: {
                styles: {
                    files: ['public/**/*.less'], // which files to watch
                    tasks: ['less'],
                    options: {
                        nospawn: true
                    }
                }
            }
    
        });
        grunt.registerTask('default', ['less', 'watch']);
    };

    //execute
    $grunt

## Variables

    @link-color:        #428bca; // sea blue
    @link-color-hover:  darken(@link-color, 10%); 
    @my-selector: banner;
    @images: "../img";
    @property: color;

    // Usage
    a, 
    .link {
      color: @link-color; 
      background: url("@{images}/white-sand.png");
      @{property}: #0ee;
      background-@{property}: #999;
    }

    a:hover {
      color: @link-color-hover; 
    }

    .@{my-selector} {  #.banner
      font-weight: bold;
      line-height: 40px;
      margin: 0 auto;
    }

Variable variable

    @primary:  green;
    @secondary: blue;

    .section {
      @color: primary;

    .element {
        color: @@color; # color: green
    }
  }

Lazy evaluation

    .lazy-eval {
      width: @var;
    }

    @var: @a;
    @a: 9%;

properties as variables

    .widget {
      color: #efefef;
      background-color: $color; #background-color: #efefef;
    }

    .block {
      color: red; 
      .inner {
        background-color: $color;  //blue
      }
      color: blue;  
    } 

### Parent Selectors

    a {
      color: blue;
      &:hover { // a:hover{}
        color: green;
      }
    }

    .button {
      &-ok { //.button-ok{
        background-image: url("ok.png");
      }
      &-cancel { //.button-cancel{}
        background-image: url("cancel.png");
      }

      &-custom { //.button-custom{}
        background-image: url("custom.png");
      }
    } 


Multiple &

    .link {
      & + & { //.link + .link {
        color: red;
      }

      & & { //.link .link {
        color: green;
      }

      && { //.link.link {
        color: blue;
      }

      &, &ish { //.link, .linkish {
        color: cyan;
      }
    }


    .grand {
      .parent {
        &, &ish { //.grand .parent, .grand .parentish
          color: cyan;
        }

        & > & { //.grand .parent > .grand .parent
          color: red;
        }

        & & { // .grand .parent .grand .parent
          color: green;
        }

        && { //.grand .parent.grand .parent
          color: blue;
        }

      }
    }

Changing Selector Order

    .header {
      .menu { // .header .menz
        border-radius: 5px;
        .no-borderradius & { //.no-borderradius .header .menu
          background-image: url('images/button-background.png');
        }
      }
    }

Combinatorial Explosion

    p, a, ul, li {
      border-top: 2px dotted #366;
      & + & {
        border-top: 0;
      }
    }

    //output
    p,
    a,
    ul,
    li {
      border-top: 2px dotted #366;
    }
    p + p,
    p + a,
    p + ul,
    p + li,
    a + p,
    a + a,
    a + ul,
    a + li,
    ul + p,
    ul + a,
    ul + ul,
    ul + li,
    li + p,
    li + a,
    li + ul,
    li + li {
      border-top: 0;
    }

## Extend

    nav ul {
      &:extend(.inline);
      background: blue;
    }
    .inline {
      color: red;
    }

    <!-- Outputs -->
    nav ul {
      background: blue;
    }
    .inline, nav ul {
      color: red;
    }

## Merge

Comma

    .mixin() {
      box-shadow+: inset 0 0 10px #555;
    }

    .myclass {
      .mixin();
      box-shadow+: 0 0 20px black;
    }
    
    <!-- Outputs -->
    .myclass {
      box-shadow: inset 0 0 10px #555, 0 0 20px black;
    }

Space

    .mixin() {
      transform+_: scale(2);
    }
    .myclass {
      .mixin();
      transform+_: rotate(15deg);
    }

    <!-- Outputs -->
    .myclass {
      transform: scale(2) rotate(15deg);
    }

## Mixins

    .a, #b {
      color: red;
    }
    .mixin-class {
      .a();
    }
    .mixin-id {
      #b();
    }

    <!-- which results in: -->
    .a, #b {
      color: red;
    }
    .mixin-class {
      color: red;
    }
    .mixin-id {
      color: red;
    }

Not Outputting the Mixin

    .my-mixin {
      color: black;
    }
    .my-other-mixin() {
      background: white;
    }
    .class {
      .my-mixin();
      .my-other-mixin();
    }

    <!-- outputs -->
    .my-mixin {
      color: black;
    }
    .class {
      color: black;
      background: white;
    }

Selectors in Mixins

    .my-hover-mixin() {
      &:hover {
        border: 1px solid red;
      }
    }
    button {
      .my-hover-mixin();
    }

    <!-- Outputs -->
    button:hover {
      border: 1px solid red;
    }

### Namespaces

    #outer() {
      .inner {
        color: red;
      }
    }

    .c {
      #outer > .inner();
    }

    // all do the same thing
    #outer > .inner();
    #outer .inner();
    #outer.inner();

Example:

    #my-library {
      .my-mixin() {
        color: black;
      }
    }

    // which can be used like this
    .class {
      #my-library.my-mixin();
    }
    
Guarded Namespaces

    #namespace when (@mode = huge) {
      .mixin() { /* */ }
    }

    #namespace {
      .mixin() when (@mode = huge) { /* */ }
    }

    #sp_1 when (default()) {
      #sp_2 when (default()) {
        .mixin() when not(default()) { /* */ }
      }
    }
    
!important:

    .foo (@bg: #f5f5f5, @color: #900) {
      background: @bg;
      color: @color;
    }
    .unimportant {
      .foo();
    }
    .important {
      .foo() !important;
    }

    <!-- Results in: -->
    .unimportant {
      background: #f5f5f5;
      color: #900;
    }
    .important {
      background: #f5f5f5 !important;
      color: #900 !important;
    }

Parametric Mixins

    .border-radius(@radius) {
      -webkit-border-radius: @radius;
        -moz-border-radius: @radius;
              border-radius: @radius;
    }

    <!-- And here's how we can mix it into various rulesets: -->
    #header {
      .border-radius(4px);
    }
    .button {
      .border-radius(6px);
    }

    Parametric mixins can also have default values for their parameters:

    .border-radius(@radius: 5px) {
      -webkit-border-radius: @radius;
        -moz-border-radius: @radius;
              border-radius: @radius;
    }
    
    We can invoke it like this now:

    #header {
      .border-radius();
    }
    
    And it will include a 5px border-radius.

    You can also use parametric mixins which don't take parameters. This is useful if you want to hide the ruleset from the CSS output, but want to include its properties in other rulesets:

    .wrap() {
      text-wrap: wrap;
      white-space: -moz-pre-wrap;
      white-space: pre-wrap;
      word-wrap: break-word;
    }

    pre { .wrap() }
    Which would output:

    pre {
      text-wrap: wrap;
      white-space: -moz-pre-wrap;
      white-space: pre-wrap;
      word-wrap: break-word;
    }
    Mixins with Multiple Parameters
    Parameters are either semicolon or comma separated. It is recommended to use semicolon. The symbol comma has double meaning: it can be interpreted either as a mixin parameters separator or css list separator.

    Using comma as mixin separator makes it impossible to create comma separated lists as an argument. On the other hand, if the compiler sees at least one semicolon inside mixin call or declaration, it assumes that arguments are separated by semicolons and all commas belong to css lists:

    two arguments and each contains comma separated list: .name(1, 2, 3; something, else),
    three arguments and each contains one number: .name(1, 2, 3),
    use dummy semicolon to create mixin call with one argument containing comma separated css list: .name(1, 2, 3;),
    comma separated default value: .name(@param1: red, blue;).
    It is legal to define multiple mixins with the same name and number of parameters. Less will use properties of all that can apply. If you used the mixin with one parameter e.g. .mixin(green);, then properties of all mixins with exactly one mandatory parameter will be used:

    .mixin(@color) {
      color-1: @color;
    }
    .mixin(@color; @padding: 2) {
      color-2: @color;
      padding-2: @padding;
    }
    .mixin(@color; @padding; @margin: 2) {
      color-3: @color;
      padding-3: @padding;
      margin: @margin @margin @margin @margin;
    }
    .some .selector div {
      .mixin(#008000);
    }
    compiles into:

    .some .selector div {
      color-1: #008000;
      color-2: #008000;
      padding-2: 2;
    }
    Named Parameters
    A mixin reference can supply parameters values by their names instead of just positions. Any parameter can be referenced by its name and they do not have to be in any special order:

    .mixin(@color: black; @margin: 10px; @padding: 20px) {
      color: @color;
      margin: @margin;
      padding: @padding;
    }
    .class1 {
      .mixin(@margin: 20px; @color: #33acfe);
    }
    .class2 {
      .mixin(#efca44; @padding: 40px);
    }
    compiles into:

    .class1 {
      color: #33acfe;
      margin: 20px;
      padding: 20px;
    }
    .class2 {
      color: #efca44;
      margin: 10px;
      padding: 40px;
    }
    The @arguments Variable
    @arguments has a special meaning inside mixins, it contains all the arguments passed, when the mixin was called. This is useful if you don't want to deal with individual parameters:

    .box-shadow(@x: 0; @y: 0; @blur: 1px; @color: #000) {
      -webkit-box-shadow: @arguments;
        -moz-box-shadow: @arguments;
              box-shadow: @arguments;
    }
    .big-block {
      .box-shadow(2px; 5px);
    }
    Which results in:

    .big-block {
      -webkit-box-shadow: 2px 5px 1px #000;
        -moz-box-shadow: 2px 5px 1px #000;
              box-shadow: 2px 5px 1px #000;
    }
    Advanced Arguments and the @rest Variable
    You can use ... if you want your mixin to take a variable number of arguments. Using this after a variable name will assign those arguments to the variable.

    .mixin(...) {        // matches 0-N arguments
    .mixin() {           // matches exactly 0 arguments
    .mixin(@a: 1) {      // matches 0-1 arguments
    .mixin(@a: 1; ...) { // matches 0-N arguments
    .mixin(@a; ...) {    // matches 1-N arguments
    Furthermore:

    .mixin(@a; @rest...) {
      // @rest is bound to arguments after @a
      // @arguments is bound to all arguments
    }
    Pattern-matching
    Sometimes, you may want to change the behavior of a mixin, based on the parameters you pass to it. Let's start with something basic:

    .mixin(@s; @color) { ... }

    .class {
      .mixin(@switch; #888);
    }
    Now let's say we want .mixin to behave differently, based on the value of @switch, we could define .mixin as such:

    .mixin(dark; @color) {
      color: darken(@color, 10%);
    }
    .mixin(light; @color) {
      color: lighten(@color, 10%);
    }
    .mixin(@_; @color) {
      display: block;
    }
    Now, if we run:

    @switch: light;

    .class {
      .mixin(@switch; #888);
    }
    We will get the following CSS:

    .class {
      color: #a2a2a2;
      display: block;
    }
    Where the color passed to .mixin was lightened. If the value of @switch was dark, the result would be a darker color.

    Here's what happened:

    The first mixin definition didn't match because it expected dark as the first argument.
    The second mixin definition matched, because it expected light.
    The third mixin definition matched because it expected any value.
    Only mixin definitions which matched were used. Variables match and bind to any value. Anything other than a variable matches only with a value equal to itself.

    We can also match on arity, here's an example:

    .mixin(@a) {
      color: @a;
    }
    .mixin(@a; @b) {
      color: fade(@a; @b);
    }
    Now if we call .mixin with a single argument, we will get the output of the first definition, but if we call it with two arguments, we will get the second definition, namely @a faded to @b.


    Using Mixins as Functions
    Selecting properties and variables from mixin calls

    Property / value accessors
    Released v3.5.0

    Starting in Less 3.5, you can use property/variable accessors to select a value from an evaluated mixin's rules. This can allow you to use mixins similar to functions.

    Example:

    .average(@x, @y) {
      @result: ((@x + @y) / 2);
    }

    div {
      // call a mixin and look up its "@result" value
      padding: .average(16px, 50px)[@result];
    }
    Results in:

    div {
      padding: 33px;
    }
    Overriding mixin values
    If you have multiple matching mixins, all rules are evaluated and merged, and the last matching value with that identifier is returned. This is similar to the cascade in CSS, and it allows you to "override" mixin values.

    // library.less
    #library() {
      .mixin() {
        prop: foo;
      }
    }

    // customize.less
    @import "library";
    #library() {
      .mixin() {
        prop: bar;
      }
    }

    .box {
      my-value: #library.mixin[prop];
    }
    Outputs:

    .box {
      my-value: bar;
    }
    Unnamed lookups
    If you don't specify a lookup value in [@lookup] and instead write [] after a mixin or ruleset call, all values will cascade and the last declared value will be selected.

    Meaning: the averaging mixin from the above example could be written as:

    .average(@x, @y) {
      @result: ((@x + @y) / 2);
    }

    div {
      // call a mixin and look up its final value
      padding: .average(16px, 50px)[];
    }
    The output is the same:

    div {
      padding: 33px;
    }
    The same cascading behavior is true for rulesets or variables aliased to mixin calls.

    @dr: {
      value: foo;
    }
    .box {
      my-value: @dr[];
    }
    This outputs:

    .box {
      my-value: foo;
    }
    Unlocking mixins & variables into caller scope
    DEPRECATED - Use Property / Value Accessors

    Variables and mixins defined in a mixin are visible and can be used in caller's scope. There is only one exception: a variable is not copied if the caller contains a variable with the same name (that includes variables defined by another mixin call). Only variables present in callers local scope are protected. Variables inherited from parent scopes are overridden.

    Note: this behavior is deprecated, and in the future, variables and mixins will not be merged into the caller scope in this way.

    Example:

    .mixin() {
      @width:  100%;
      @height: 200px;
    }

    .caller {
      .mixin();
      width:  @width;
      height: @height;
    }

    Results in:

    .caller {
      width:  100%;
      height: 200px;
    }
    Variables defined directly in callers scope cannot be overridden. However, variables defined in callers parent scope is not protected and will be overridden:

    .mixin() {
      @size: in-mixin;
      @definedOnlyInMixin: in-mixin;
    }

    .class {
      margin: @size @definedOnlyInMixin;
      .mixin();
    }

    @size: globaly-defined-value; // callers parent scope - no protection
    Results in:

    .class {
      margin: in-mixin in-mixin;
    }
    Finally, mixin defined in mixin acts as return value too:

    .unlock(@value) { // outer mixin
      .doSomething() { // nested mixin
        declaration: @value;
      }
    }

    #namespace {
      .unlock(5); // unlock doSomething mixin
      .doSomething(); //nested mixin was copied here and is usable
    }
    Results in:

    #namespace {
      declaration: 5;
    }

    Recursive Mixins
    Creating loops

    In Less a mixin can call itself. Such recursive mixins, when combined with Guard Expressions and Pattern Matching, can be used to create various iterative/loop structures.

    Example:

    .loop(@counter) when (@counter > 0) {
      .loop((@counter - 1));    // next iteration
      width: (10px * @counter); // code for each iteration
    }

    div {
      .loop(5); // launch the loop
    }
    Output:

    div {
      width: 10px;
      width: 20px;
      width: 30px;
      width: 40px;
      width: 50px;
    }
    A generic example of using a recursive loop to generate CSS grid classes:

    .generate-columns(4);

    .generate-columns(@n, @i: 1) when (@i =< @n) {
      .column-@{i} {
        width: (@i * 100% / @n);
      }
      .generate-columns(@n, (@i + 1));
    }
    Output:

    .column-1 {
      width: 25%;
    }
    .column-2 {
      width: 50%;
    }
    .column-3 {
      width: 75%;
    }
    .column-4 {
      width: 100%;
    }

    Mixin Guards
    Guards are useful when you want to match on expressions, as opposed to simple values or arity. If you are familiar with functional programming, you have probably encountered them already.

    In trying to stay as close as possible to the declarative nature of CSS, Less has opted to implement conditional execution via guarded mixins instead of if/else statements, in the vein of @media query feature specifications.

    Let's start with an example:

    .mixin(@a) when (lightness(@a) >= 50%) {
      background-color: black;
    }
    .mixin(@a) when (lightness(@a) < 50%) {
      background-color: white;
    }
    .mixin(@a) {
      color: @a;
    }
    The key is the when keyword, which introduces a guard sequence (here with only one guard). Now if we run the following code:

    .class1 { .mixin(#ddd) }
    .class2 { .mixin(#555) }
    Here's what we'll get:

    .class1 {
      background-color: black;
      color: #ddd;
    }
    .class2 {
      background-color: white;
      color: #555;
    }
    Guard Comparison Operators
    The full list of comparison operators usable in guards are: >, >=, =, =<, <. Additionally, the keyword true is the only truthy value, making these two mixins equivalent:

    .truth(@a) when (@a) { ... }
    .truth(@a) when (@a = true) { ... }
    Any value other than the keyword true is falsy:

    .class {
      .truth(40); // Will not match any of the above definitions.
    }
    Note that you can also compare arguments with each other, or with non-arguments:

    @media: mobile;

    .mixin(@a) when (@media = mobile) { ... }
    .mixin(@a) when (@media = desktop) { ... }

    .max(@a; @b) when (@a > @b) { width: @a }
    .max(@a; @b) when (@a < @b) { width: @b }
    Guard Logical Operators
    You can use logical operators with guards. The syntax is based on CSS media queries.

    Use the and keyword to combine guards:

    .mixin(@a) when (isnumber(@a)) and (@a > 0) { ... }
    You can emulate the or operator by separating guards with a comma ,. If any of the guards evaluate to true, it's considered a match:

    .mixin(@a) when (@a > 10), (@a < -10) { ... }
    Use the not keyword to negate conditions:

    .mixin(@b) when not (@b > 0) { ... }
    Type Checking Functions
    Lastly, if you want to match mixins based on value type, you can use the is functions:

    .mixin(@a; @b: 0) when (isnumber(@b)) { ... }
    .mixin(@a; @b: black) when (iscolor(@b)) { ... }
    Here are the basic type checking functions:

    iscolor
    isnumber
    isstring
    iskeyword
    isurl
    If you want to check if a value is in a specific unit in addition to being a number, you may use one of:

    ispixel
    ispercentage
    isem
    isunit

    Aliasing Mixins
    Released v3.5.0

    Assigning mixin calls to a variable

    Mixins can be assigned to a variable to be called as a variable call, or can be used for map lookup.

    #theme.dark.navbar {
      .colors(light) {
        primary: purple;
      }
      .colors(dark) {
        primary: black;
        secondary: grey;
      }
    }

    .navbar {
      @colors: #theme.dark.navbar.colors(dark);
      background: @colors[primary];
      border: 1px solid @colors[secondary];
    }
    This would output:

    .navbar {
      background: black;
      border: 1px solid grey;
    }
    Variable calls
    Entire mixin calls can be aliased and called as variable calls. As in:

    #library() {
      .rules() {
        background: green;
      }
    }
    .box {
      @alias: #library.rules();
      @alias();
    }
    Outputs:

    .box {
      background: green;
    }
    Note, unlike mixins used in root, mixin calls assigned to variables and called with no arguments always require parentheses. The following is not valid.

    #library() {
      .rules() {
        background: green;
      }
    }
    .box {
      @alias: #library.colors;
      @alias();   // ERROR: Could not evaluate variable call @alias
    }
    This is because it's ambiguous if variable is assigned a list of selectors or a mixin call. For example, in Less 3.5+, this variable could be used this way.

    .box {
      @alias: #library.colors;
      @{alias} {
        a: b;
      }
    }
    The above would output:

    .box #library.colors {
      a: b;
    }
