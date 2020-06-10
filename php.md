# PHP Tutorials 

- [Content](http://www.zend.com/en/services/certification/php-certification)

## Connect to the server and database 

config.php

	<?php

	$hostname = "localhost";
	$username = "fabrigeas";
	$password = "98919032";
	$database ="serverseitigeanw";

	$conn = new mysqli($hostname, $username, $password);
	
	if ( $conn->connect_error ) 
		die("Connection failed: " . $conn->connect_error);
	else
		echo "Connected successfully";

	$db = mysqli_connect($hostname,$username,$password,$database);
	if ($db->connect_error)
		echo "unable to connect to server";

	?>
  
index.php

    <? include("config.php"); ?>
    
## [MySQL queries](http://php.net/manual/de/mysqli.query.php)
  
### 1. Procedural queries

Select several

	$query = mysqli_query($db,"SELECT * FROM movies WHERE media_type LIKE '%$_GET[media_type]%' ") 
	or die("");

	while ( $row = mysqli_fetch_array($query,MYSQLI_ASSOC) ){

		echo " $row[title]";
	} 

Select One

	$result=mysqli_query($db,"SELECT * FROM tasks WHERE taskId=$taskId AND  groupId=$groupId");
	$head = mysqli_fetch_array($result,MYSQLI_ASSOC);
	$taskName = $head['taskName'];
	$taskId   = $head['taskName'];

Insert

	mysqli_query( $db,"INSERT INTO cart (title, description, item_id, user_id, inserted_when, type, other) 
	VALUES ('$_GET[title]','$_GET[desc]','$_GET[id]','$_SESSION[user_id]', '$today', '$_GET[type]', null)")
	or die("unable  to insert into cart");

Insert and get id of inserted item
 
	$timestamp=Date("D d.m.Y-H.i.s.U");
	mysqli_query($db,"INSERT INTO `ordered_templates` (`id`, `title`, `detail`, `user`, `timestamp`) VALUES (NULL, 'sadasdasd', NULL, '-1', '$timestamp');")
	
	$q=mysqli_query($db,"SELECT id FROM `ordered_templates` WHERE timestamp='$timestamp'");
	$head=mysqli_fetch_array($q,MYSQLI_ASSOC);
	$id=$head['id'];      
   
Update
 
	mysqli_query($db,"UPDATE tasks SET taskName='$taskName',indexInGroup='$indexInGroup' WHERE taskId=$taskId")
	or die("unable to update task $taskName");
 
 Count
  
	$q = mysqli_query($db,"SELECT * FROM resources ") or die("unable to select resources");
	$total = mysqli_num_rows($q);
    
Empty tables
 
	TRUNCATE TABLE ordered_templates;
	TRUNCATE TABLE queue;
	TRUNCATE TABLE subtasks;
    
Retrive object

	$query=mysqli_query($db,"SELECT * FROM tasks WHERE id='?'")or die("");
	$object=mysqli_fetch_object($query);
	print_r($next->taskName);    

### 2. ObjectOriented queries

    $mysqli = new mysqli($hostname,$username,$password, "what is this?");
    
    if ($mysqli->connect_errno) {
      printf("Connect failed: %s\n", $mysqli->connect_error);
      exit();
    }
    
    if ($mysqli->query("CREATE TEMPORARY TABLE myCity LIKE City") === TRUE) {
      printf("Table myCity successfully created.\n");
    }
    
Select queries return a resultset 

    if ($result = $mysqli->query("SELECT Name FROM City LIMIT 10")) {
      printf("Select returned %d rows.\n", $result->num_rows);
      
      //result must be closed to free access to database
      $result->close();
    }

## AJAX
 
.js

	<input onkeyup='onLiveSearch(this.value)'>

	function onLiveSearch(key){
		var xmlhttp;

		if (window.XMLHttpRequest) 
			xmlhttp=new XMLHttpRequest();//IE7+, Firefox, Chrome, Opera, Safari
		else 
			xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");// code for IE6 <7

		xmlhttp.onreadystatechange=function() {
			if (xmlhttp.readyState==4 && xmlhttp.status==200) {
				xmlhttp.responseText;//the string echoed from the server
			}
		}
		xmlhttp.open("GET","php.php?parameter="+str,true);
		xmlhttp.send();
	}

php.php

	echo "the result as string"
  
## [File System](https://www.tutorialrepublic.com/php-tutorial/php-file-system.php)

	$file = "data.txt";
	
	if( file_exists($file) ){
		//operations here
		
	} 
	else{
		echo "ERROR: File does not exist.";
	}

### Read file

- open, read and close the file 

		$handle = fopen($file, "r") or die("ERROR: Cannot open the file.");
		$content = fread($handle, "20");// read 20 bytes
		$content = fread($handle, filesize($file)); // read entire file
		fclose($handle);
		echo $content;

read the whole file without opening it

		$content = readfile($file) or die("ERROR: Cannot open the file.");

- Reading the entire file into a string

	$content = file_get_contents($file) or die("ERROR: Cannot open the file.");

- Reading the entire file into an array

		$arr = file($file) or die("ERROR: Cannot open the file.");
		foreach($arr as $line){
			echo $line;
		}

### Write to file
	
	$handle = fopen($file, "w") or die("ERROR: Cannot open the file.");
	fwrite($handle, $data) or die ("ERROR: Cannot write the file.");
	file_put_contents($file, $data) or die("ERROR: Cannot write the file.");
	file_put_contents($file, $data, FILE_APPEND) or die("ERROR: Cannot write the file.");
	fclose($handle);

### rename file

	//if file exists
    if(rename($file, "newfile.txt")){
        echo "File renamed successfully.";
    } else{
        echo "ERROR: File cannot be renamed.";
    }

### Remove file

	//if file exists
	if(unlink($file)){
        echo "File removed successfully.";
    } else{
        echo "ERROR: File cannot be removed.";
    }

### File Upload

    <form action="upload-manager.php" method="post" enctype="multipart/form-data">
        <h2>Upload File</h2>
        <label for="fileSelect">Filename:</label>
        <input type="file" name="photo" id="fileSelect">
        <input type="submit" name="submit" value="Upload">
        <p><strong>Note:</strong> Only .jpg, .jpeg, .gif, .png formats allowed to a max size of 5 MB.</p>
    </form>

	if($_SERVER["REQUEST_METHOD"] == "POST"){
		if( isset( $_FILES["photo"]) && $_FILES["photo"]["error"] == 0 ){
			$allowed = array("jpg" => "image/jpg", "jpeg" => "image/jpeg", "gif" => "image/gif", "png" => "image/png");
			$filename = $_FILES["photo"]["name"];
			$filetype = $_FILES["photo"]["type"];
			$filesize = $_FILES["photo"]["size"];
		
			$ext = pathinfo($filename, PATHINFO_EXTENSION);
			if(!array_key_exists($ext, $allowed)) die("Error: Please select a valid file format.");
		
			$maxsize = 5 * 1024 * 1024;
			if($filesize > $maxsize) die("Error: File size is larger than the allowed limit.");

			if(in_array($filetype, $allowed)){
				if( file_exists("upload/" . $_FILES["photo"]["name"]) ){
					echo $_FILES["photo"]["name"] . " already exists.";
				} else{
					move_uploaded_file($_FILES["photo"]["tmp_name"], "upload/" . $_FILES["photo"]["name"]);
					echo "Your file was uploaded successfully.";
				} 
			} else echo "Error: There was a problem uploading your file. Please try again."; 
		} else echo "Error: " . $_FILES["photo"]["error"];
	}

## [PHP Parsing Directories](https://www.tutorialrepublic.com/php-tutorial/php-parsing-directories.php)

Create

	if( !file_exists("di_name") ){
		if( mkdir($dir) ){ // create success
			echo "Directory created successfully.";
		} 
	} 

copy files between dirs

	//if exists file to be copied
	if( copy("file/tobe/copied.ext", "dest/name.ext") ){ //copy success
        echo "File copied successfully.";
    }

ls - files in $path

	if( file_exists($path) && is_dir($path) ){
		$result = scandir($path);
		$files = array_diff( $result, array('.', '..') );
		
		if(count($files) > 0){
			foreach($files as $file){
				if(is_file("$path/$file")){
					echo $file . "<br>";
				} else if( is_dir("$path/$file") ){
					outputFiles("$path/$file");
				}
			}
		} else echo "ERROR: No files found in the directory.";
	} else echo "ERROR: The directory does not exist.";

## Cookies and Sessions

### cookies

	setcookie("username", "John Carter", time()+30*24*60*60); //set
	echo $_COOKIE["username"];
	setcookie("username", "", time()-3600); //delet

	if( isset($_COOKIE["username"]) ){
		echo "Hi " . $_COOKIE["username"]; // access
	} else{
		echo "Welcome Guest!";
	}

### sessions

	if( !_successfull_login() )
		_redirect_to_login_page

	session_start();

	$_SESSION["firstname"] = "Peter";
	$_SESSION["lastname"] = "Parker";

other-pages.php

	session_start(); //first line of the file

	if( !isset($_SESSION["user"]) )
		//redirect to login

	echo 'Hi, ' . $_SESSION["firstname"] . ' ' . $_SESSION["lastname"];

logout.php

	session_start();

	session_destroy();

	if( isset( $_SESSION["firstname"] ) ){
		unset( $_SESSION["firstname"] );
	}

## SendMail

1. Text message

	$to = 'maryjane@email.com';
	$subject = 'Marriage Proposal';
	$from = 'peterparker@email.com';
	$message = 'Hi Jane, will you marry me?'; 

2. HTML Formatted Message

	$headers  = 'MIME-Version: 1.0' . "\r\n";
	$headers .= 'Content-type: text/html; charset=iso-8859-1' . "\r\n";

	$headers .= 'From: '.$from."\r\n".
	'Reply-To: '.$from."\r\n" .
	'X-Mailer: PHP/' . phpversion();

	$message = '<html><body>';
	$message .= '<h1 style="color:#f40;">Hi Jane!</h1>';
	$message .= '<p style="color:#080;font-size:18px;">Will you marry me?</p>';
	$message .= '</body></html>';

	if( mail($to, $subject, $message) ){ // add header parameter for html emails
		echo 'Your mail has been sent successfully.';
	} else echo 'Unable to send email. Please try again.';

## [Filters, Sanitize user form](https://www.w3schools.com/pHP/php_filter.asp)

	function filterName($field){
		$field = filter_var(trim($field), FILTER_SANITIZE_STRING);
		
		if( filter_var( $field, FILTER_VALIDATE_REGEXP, array("options"=>array("regexp"=>"/^[a-zA-Z\s]+/"))) ){
			return $field;
		}
		else return FALSE;
	}    

	function filterEmail($field){

		$field = filter_var(trim($field), FILTER_SANITIZE_EMAIL);
		if(filter_var($field, FILTER_VALIDATE_EMAIL)){
			return $field;
		}
		else return FALSE;
	}
	function filterString($field){
		$field = filter_var( trim($field), FILTER_SANITIZE_STRING );
		if(!empty($field)){
			return $field;
		}else return FALSE;
	}
		
	$nameErr = $emailErr = $messageErr = "";
	$name = $email = $subject = $message = "";
		
	if($_SERVER["REQUEST_METHOD"] == "POST"){
	
		if( empty($_POST["name"]) ){
			$nameErr = 'Please enter your name.';
		}
		else{
			$name = filterName($_POST["name"]);
			if($name == FALSE){
				$nameErr = 'Please enter a valid name.';
			}
		}
			
		if(empty($_POST["email"])){
			$emailErr = 'Please enter your email address.';     
		}
		else{
			$email = filterEmail($_POST["email"]);
			if($email == FALSE){
				$emailErr = 'Please enter a valid email address.';
			}
		}
		
		if(empty($_POST["subject"])){
			$subject = "";
		}else{
			$subject = filterString($_POST["subject"]);
		}
			
		if(empty($_POST["message"])){
			$messageErr = 'Please enter your comment.';     
		}else{
			$message = filterString($_POST["message"]);
			if($message == FALSE){
				$messageErr = 'Please enter a valid comment.';
			}
		}
			
		// Check input errors before sending email
		if(empty($nameErr) && empty($emailErr) && empty($messageErr)){
			$to = 'webmaster@example.com';
			$headers = 'From: '. $email . "\r\n" .
			'Reply-To: '. $email . "\r\n" .
			'X-Mailer: PHP/' . phpversion();
			if(mail($to, $subject, $message, $headers)){
				echo '<p class="success">Your message has been sent successfully!</p>';
			}else echo '<p class="error">Unable to send email. Please try again!</p>';
		}
	}

	<form action="contact.php" method="post">
		<input type="text" name="name" id="inputName" value="<?php echo $name; ?>">
		<span class="error"><?php echo $nameErr; ?></span>
		<input type="text" name="email" id="inputEmail" value="<?php echo $email; ?>">
		<span class="error"><?php echo $emailErr; ?></span>
		<input type="text" name="subject" id="inputSubject" value="<?php echo $subject; ?>">
		<textarea name="message" id="inputComment" rows="5" cols="30"><?php echo $message; ?></textarea>
		<span class="error"><?php echo $messageErr; ?></span>
		<input type="submit" value="Send">
		<input type="reset" value="Reset">
	</form>

## [PHP Error Handling](https://www.tutorialrepublic.com/php-tutorial/php-error-handling.php)

## Pagination

	$size=3;
	if( isset($_GET['page']) ){
		$page=(int) $_GET['page'];
		$prev=$page-1;
		$next=$page+1;
	}
	else{
		$page=$_GET['page']=1;
		$prev=$next=1;
	}

	$all_movies = mysqli_query($db,"SELECT * FROM movies  LIMIT {$page},{$size} ") 
	or die("unable to retrive movies from database");

	echo"<a href='?page=$prev'> prev </a> ";

	for($i=1;$i<$size;$i++)
		echo" <a href='?page=$i'> $i </a> ";  

	echo"<a href='?page=$next'> Next </a>"
## Misc

Variables and Constants

	define("MY_CONATANT", "https://www.tutorialrepublic.com/");
	console.log(MY_CONATANT)


include/require file
	
		include("path/to/header.php"); 
		require("path/to/footer.php"); // will crash if footer is not found

		include_once // 
		require_once //

### Classes and Objects

myClass.php

	class Human{
		public $name = "unnamed";
		public $gender;

		// Constructor
		public function __construct( $name="unnamed", $gender="hermaphrodite"){
			$this->name= $name;
			$this->gender = $gender;
			echo 'The class "' . __CLASS__ . '" was initiated!<br>';
		}
		
		// Destructor
		public function __destruct(){
			echo 'The class "' . __CLASS__ . '" was destroyed.<br>';
		}
		
		function sayHello(){
			return "hello world from: ".$this->name;
		}
	}

file.php

	require "myClass.php"

	$fab = new Human("fabrice", "male");
	$hermaphrodite = new Human;

	var_dump($fab);
	var_dump($hermaphrodite);

	$fab->name = "Fabrigeas;

	var_dump($fab); // log all infos about

	// Destroy the object
	unset($fab);
	unset($hermaphrodite);

#### Inheritance

	require "myClass.php"

	class Male extends Human{

		public function isMale(){
			return $this->name == "Male"
		}
	}

#### Interface

	require "myClass.php"

	class Report implements Human {
	}

#### Abstract

	Cannot be instantiated, can only be extended
	abstract class MyAbstractClass {
		abstract function myAbstractFunction() { // all abstract methods must be implementef
		}
	}

#### Static

	static vars can be accessed withput instatiating the class
	static members cannon be accessed by instantiated methods

#### php 5

Parent

	class Name {
		var $_firstName;
		var $_lastName;
		
		function Name($first_name, $last_name) {
			$this->_firstName = $first_name;
			$this->_lastName = $last_name;
		}
		
		function toString() {
			return($this->_lastName .", " .$this->_firstName);
		}
	}

Child

	class NameSub1 extends Name {
		var $_middleInitial;
		
		function NameSub1($first_name, $middle_initial, $last_name) {
			Name::Name($first_name, $last_name);
			$this->_middleInitial = $middle_initial;
		}
		
		function toString() {
			return(Name::toString() . " " . $this->_middleInitial);
		}
	}

### JSON

JSonArray

	$list=array();

	for($i=0;$i<5;$i++){

		$list[]=array(
			"taskId"=>$i,
			"taskName"=>"task: $i",
			"taskDuration"=>$i*1,
			"res"=>$i,
			"day"=>$i+1
		);
	}
  
Decode JSON

	$task=$_GET['task'];
	$obj = json_decode($task);
	echo $obj->{'taskName'};


### ARRAYS

associative
   
    //declare
    $ages = array("Peter"=>"35", "Ben"=>"37", "Joe"=>"43");
    
    //append
    $ages['Peter'] = "35";
    $ages['Ben'] = "37";
    $ages['Joe'] = "43";
    
    //index
    echo "Peter is ". $ages['Peter'] ." years old.";
    
    
    //print
    foreach ($ages as $key => $value)
      echo "$key=>$value\n";

### login

config.php

	$host     = "localhost";
	$username = "";
	$password = "";
	$database = "";

	$conn = new mysqli($host, $username, $password);
	if ($conn->connect_error) 
	die("Connection failed: " . $conn->connect_error);

	$db = mysqli_connect($host,$username,$password,$database);
	if ($db->connect_error) 
	echo "unable to connect to server";
  
login.php
   
    include("config.php");
	session_start();
   
    if( isset($_GET['Username']) && isset($_GET['Password']) )
    {
      $myusername = mysqli_real_escape_string($db,$_GET['Username']);
      $mypassword = mysqli_real_escape_string($db,$_GET['Password']); 

      $result = mysqli_query($db,"SELECT user_id FROM users WHERE username = '$myusername' and password = '$mypassword'")
	  or die("unable to access users table");
      
	  $row   = mysqli_fetch_array($result,MYSQLI_ASSOC);
      $count = mysqli_num_rows($result);

      if($count == 1) {
        $_SESSION['username'] = $myusername;
        $_SESSION['password'] = $password;
        header("location: home.php");
      }
      else{
        header("location: index.php");
      }
    }

html form
   
    <form name='loginForm' class='popup-form' name='loginForm' id='loginForm' onsubmit='event.preventDefault(); sendLoginInfo();' >
    <input type='text'      placeholder='User name' id='UserName' name='Username' title='enter your user name or E-mail' required >
    <input type='password' placeholder='password'  id='Password' name='Password' title='enter your user password' required>
    <div >
      <input type='checkbox' name='KeepMeLoggedIn' value='KeepMeLoggedIn' title='check this to avoid filling this form next time'>
      keep me logged in
      <a href='' id='recoverPassword'>password forgotten?</a>
    </div>
    <input type='submit' value='login' >
    <p><a href=''>don't have an account? click to create an account'</a></p>
    </form>

### magic form | html form -> JSonkp,l
 
  //collect data from form and insert in database dynamically, the client side with html, jquery and ajax

.html
   
    <form action='none' method='get' name='form' id='form' onsubmit='event.preventDefault();submitForm()'>
      <input type='text' name='fname' placeholder='fname' value='Fabrice' required>
      <input type='text' name='lname' placeholder='lname' value='Feugang' required>
      <input type='text' name='other' placeholder='other' value='hello world' required>
      <input type='submit' value='submit'>
    </form>
  
.js
   
	function submitForm(){

		var person = $("form").serializeArray();

		person.push({name:'insert',value:'person'});
		person=JSON.stringify(person);

		$.ajax({ 
			type : 'POST', 
			url  : 'form2-handler.php', 
			data :person,
			success: function (data) { 

			}
		});
	}
  
.php

    $data = json_decode(file_get_contents("php://input"),true);

    $insert= "INSERT INTO person(NULL";
    $option=$data[count($data)-1]['name'];
    $table=$data[count($data)-1]['value'];
    $insert= "INSERT INTO $table(NULL";
    
	for($i=0;$i<count($data);$i++)
      	$insert=$insert."'".$data[$i]['value']."',";

	$insert[strlen($insert)-1]=")";
    echo $insert;

### JSon encode/decode
 
encode

    $list=array();
    $q="SELECT * FROM Queue";
    $query=mysqli_query($db,$q)or die("error: $q");

    while( $item= mysqli_fetch_array($query,MYSQLI_ASSOC) )
      $list[]=array($item);

    echo json_encode($list);

decode
   
    $.ajax({
		type: "GET",
		url: "databaseCommunications.php",
		data:{"option":"load"},
		success: function(data)
		{
		var jSonArray = JSON.parse(data);
		all=[];
		
		$.each( jSonArray, function(idx, obj){
			loadTaskOnBoard(obj);
		});
		}
    });

### receive any instance of class as JSON Array and insert it into the database
   
.js
   
	$.ajax({
	type:"POST",
	url:"php-crud.php",
	data:JSON.stringify({option:'update-object',id:object.taskId,count:1,table:'objects',data:JSON.stringify(object)}),
	success:function(result){}
	});

.php
	$data = json_decode(file_get_contents("php://input"),true);
	$option=$data['option'];
	
	$query="UPDATE $data[table] SET ";
	foreach (json_decode($data['data'],true) as $key=>$value)
		$query=$query."$key='$value',";

	$query[strlen($query)-1]=" ";
	$query=$query." WHERE taskId='$data[id]'";
	mysqli_query($db,$query)or die("error: $query");
	echo "ok";

