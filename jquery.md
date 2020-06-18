// INCLUDE JQUERY LIBRARIES EXTERNAL
{
<script type = "text/javascript" src = "http://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<script type = "text/javascript" src = "resources/jquery.min.js"></script>
}

//INCLUDE INTERNAL SCRIPTS
{		
	//internal s
      <script type = "text/javascript">
         $(document).ready(function(){document.write("Hello, World from the other side !");});
      </script> 
	 
	 //external script
	<script type="text/javascript" src="resources/myJs.js" ></script>
}

// DOCUMENT READY
{
	$( document ).ready( function() {...} );
	$( window ).load( function() { ... })
	$( function() {} );
	$( document ).ready( functionsName );
	$( window ).load( functionsName);
	$( functionsName );
}	

//SELECTORS
{
$("SELECTOR HERE") 	eg *=> $("*") //select *
$(".intro , .demo"),$("h1,div,p") //OR
$("x:Y") // all x that is Y
$("div p") 	//ps that follow div, 
$(":contains('Hello')")  //* that contains "hello"

//"[attribute=value]" ("[]")what stay within the box is attribute value pair
$("[href]") 		//* with attribute href
$("[href='default.htm']") //* with attribute href = "default.htm"
$("[href!='default.htm']")//* with attribute href != "default.htm"
$("[href$='.jpg']") //href ending with .jpeg ($)
$("[title^='Tom']") //href starting (^)
$("[title~='hello']")//title containing "hello"
$("[title*='hello']")//

//INPUTS '$(":input")'
$(":text"), //password,email...

}
	
//GET SET (HTML) ATTRIBUTES
{
	var select=$("select");
	var id=select.attr("id");
	select.attr("newAttribute","newValue");
	select.removeAttr( "title" )



	//SET CSS VALUES
	{
		$("select")
		.css( "background-color", "red" );
		.css(
			{"color":"red",
			 "background-color":"green"
			});
	}

}

//DRAGGABLE FOR DRAG AND DROP
{
	<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.5.0/jquery.min.js"></script>
	<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.9/jquery-ui.min.js"></script>
	$(
		$('selct item to be dragged').draggable
		(
			containment: '#content',//boundaries{parent,document,window,A selector,Array of 4 values ([x1,y1,x2,y2])
			stop:  function handleDragStop( event, ui ) {
			  var offsetXPos = parseInt( ui.offset.left );
			  var offsetYPos = parseInt( ui.offset.top );
			  alert( "Drag stopped!\n\nOffset: (" + offsetXPos + ", " + offsetYPos + ")\n");
			},
			start: functionNameWithoutBraces,
			drag:  callFunctionThatDrawsOrUpdatesUi
				
		);
	 );
}


//TRANSFORM A DIV INTO A DIALOG
{
<body>
	<div id = "helloWorldDiv"><h1>Hello world</h1></div>
	<div id="taskForm" title="Task form">
	  <form>
      	Item Name:<br> 
        <input id="taskName" type="text" value="" size="30"/><br> 
        Duration(Days):<br> 
        <input type="text" id="duration"><br> 
      </form>
      </div>	
</body>
$( function(){
		<!-- document.write("one of the functions called onload!"); -->
		var taskForm = $('#taskForm').dialog();
		taskForm = $('#taskForm').dialog();					   
			   taskForm.dialog({
				  autoOpen: false,
				  height: 300,
				  width: 350,
				  show: {effect: "blind",duration: 1000},
				  hide: {effect: "explode",duration: 1000},
				  modal: true,
				  buttons: {"Add task": function(){},Cancel: function() {taskForm.dialog( "close" );}},
				  close: function() {form[ 0 ].reset();}
				});
	});

 //Attributes 
 var title = $("em").attr("id");  //title = id of selected
$("#divid").text(title);			//tag value =

//set attributes
$("#myimg").attr("src", "/jquery/images/jquery.jpg");



//APPLY CSS PROPERTIES
//.eq(n) nth child
$("li").eq(2).css({"color":"red", "background-color":"green"});
$("div:first").width(100);

//scope
$("div").click(function () {
   $(this).remove( );=> $("div")
});


//insert before
$("div").click(function () {
	$(this).before('<div class="div"></div>' );
});

//Binding event handlers
$('div').bind('click', function( event ){
   alert('Hi there!');
});


// read file and create dom
{
	$.get("files/vegetables.json", function(data) {
		console.log("Click!");
		console.log(data);
		for (var p in data) {
			var vegetable = data[p];
			$("#list").append("<li>" + vegetable.name + " is " + vegetable.color + "</li>");
		}
	}, "json");
}

// assign object by value
{
	var myArray  = jQuery.extend(true, [], anotherArray);
	var myObject = jQuery.extend(true, {}, anotherObject);
}

// assing object (array) by value
{
	aDestination = jQuery.extend(true, [], aSource);
}
