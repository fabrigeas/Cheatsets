 <?php
      $host = "localhost";
      $username = "fabrigeas";
      $password = "98919032";
      $database ="serverseitigeanw";

      // Create connection
      $conn = new mysqli($host, $username, $password);

      // Check connection
      if ($conn->connect_error) 
        die("Connection failed: " . $conn->connect_error);
      // else
        // echo "Connected successfully";

      $db = mysqli_connect($host,$username,$password,$database);
      if ($db->connect_error) 
        echo "unable to connect to server";

      mysqli_query($db,
      "CREATE TABLE  IF NOT EXISTS users(
        user_id int(11) NOT NULL AUTO_INCREMENT,
        firstname text NOT NULL,
        lastname varchar(30) NOT NULL,
        username varchar(30) NOT NULL,
        password text NOT NULL,
        email varchar(20) NOT NULL,
        image text NOT NULL,
        other varchar(30) DEFAULT NULL,
        PRIMARY KEY (user_id)
      ) ENGINE=InnoDB DEFAULT CHARSET=latin1")or die("unable to create users table");

      mysqli_query($db,
      "CREATE TABLE IF NOT EXISTS movies (
        id int(20) NOT NULL AUTO_INCREMENT,
        title varchar(50) DEFAULT NULL,
        description text NOT NULL,
        link text,
        actors text,
        authors text,
        release_date varchar(20) NOT NULL,
        type text NOT NULL,
        media_type text,
        other text,
        isavailable tinyint(1) NOT NULL DEFAULT '1',
        lendperiod int(11) DEFAULT NULL,
        returnOn date DEFAULT NULL,
        PRIMARY KEY (id)
      ) ENGINE=InnoDB DEFAULT CHARSET=latin1")or die("unable to create items table");

      mysqli_query($db,
      "
      CREATE TABLE IF NOT EXISTS `cart` (
        `cart_item_id` int(20) NOT NULL AUTO_INCREMENT,
        `title` varchar(100) DEFAULT NULL,
        `description` text,
        `item_id` int(11) NOT NULL,
        `user_id` int(11) NOT NULL,
        `inserted_when` varchar(20) DEFAULT NULL,
        `type` text,
        `other` text,
        PRIMARY KEY ( cart_item_id ),
        FOREIGN KEY (item_id) REFERENCES movies(id) ON DELETE CASCADE ON UPDATE CASCADE,
        FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE ON UPDATE CASCADE
        
      ) ENGINE=InnoDB DEFAULT CHARSET=latin1;
      "
      )or die("unable to create table for carts (Warenkorb)");
      mysqli_query($db,
      "
      CREATE TABLE IF NOT EXISTS bookings (
        booking_id int(11) NOT NULL AUTO_INCREMENT,
        user_id int(11) NOT NULL,
        item_id int(11) NOT NULL,
        booking_date varchar(20) NOT NULL,
        to_return_date varchar(20) NOT NULL,
        returned_date varchar(20) NOT NULL,
        booking_fee int(11) DEFAULT NULL,
        extra_fee_payed int(11) DEFAULT NULL,
        PRIMARY KEY ( booking_id ),
        FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE ON UPDATE CASCADE
      ) ENGINE=InnoDB DEFAULT CHARSET=latin1
      "
      )or die("unable to create table for carts (bookings)");

      //dumping values
      mysqli_query($db,
      "
      INSERT INTO `movies` (`id`, `title`, `description`, `link`, `actors`, `authors`, `release_date`, `type`, `media_type`, `other`, `isavailable`, `lendperiod`, `returnOn`) VALUES
      (1, 'Doom (2016)', 'The Union Aerospace Corporation''s massive research facility on Mars is overwhelmed by demons, and only one person stands between their world and ours. As the lone DOOM Marine, you''ve been activated to do one thing: kill them all.', 'http://www.imdb.com/video/imdb/vi3958813721/imdb/embed?autoplay=false&width=480', NULL, 'John Carmack, Tim Willits, Mark Lambert Bristol', '0', 'movie', 'Action | Horror | Sci-Fi', NULL, 1, 15, NULL),
      (2, 'Captain America: Civil War (2016)', 'Political interference in the Avengers'' activities causes a rift between former allies Captain America and Iron Man.', 'http://www.imdb.com/video/imdb/vi174044441/imdb/embed?autoplay=false&width=480', 'Chris Evans, Robert Downey Jr., Scarlett Johansson', ' Anthony Russo, Joe Russo, Christopher Markus (screenplay), Stephen McFeely (screenplay)', '0', 'movie', ' Action, Adventure, Sci-Fi', NULL, 1, 10, NULL),
      (3, 'The Mask of Troy: A Novel (Jack Howard)', 'Here is the most explosive adventure yet from the New York Times bestselling author of Atlantis and The Lost Tomb—a whiplash-inducing novel that sends marine archaeologist Jack Howard and his team on a treasure hunt . . . and a race against time to stop a terrifying threat.', 'http://ecx.images-amazon.com/images/I/61fEyZGpHsL._SX303_BO1,204,203,200_.jpg', NULL, 'David Gibbins ', 'May 24, 2011', 'book', 'Fiction, Crime and Mystery, Archaeology', NULL, 1, 30, NULL),
      (4, 'Pirates of the Caribbean: On Stranger Tides (2011)', 'Captain Barbossa, Will Turner and Elizabeth Swann must sail off the edge of the map, navigate treachery and betrayal, find Jack Sparrow, and make their final alliances for one last decisive battle.', 'http://www.imdb.com/video/imdb/vi3747192089/imdb/embed?autoplay=false&width=480', ' Johnny Depp, Penélope Cruz, Ian McShane', 'Gore Verbinski', '20 May 2011', 'movie', 'Action, Adventure, Fantasy', 'IDMB: 8.7/10', 1, 20, NULL),
      (5, 'Titanic (1997)', 'A seventeen-year-old aristocrat falls in love with a kind, but poor artist aboard the luxurious, ill-fated R.M.S. Titanic.', 'http://www.imdb.com/video/imdb/vi907189785/imdb/embed?autoplay=false&width=480', ' Leonardo Dicaprio, Kate Winslet, Billy Zane, Kathy Bates', 'James Cameron', '', 'movie', 'Drama, Romance', 'IDMB: 7.7/10', 1, NULL, NULL),
      (6, 'Finding Nemo (2003) TRAILER', 'A clown fish named Marlin lives in the Great Barrier Reef loses his son, Nemo. After he ventures into the open sea, despite his father''s constant warnings about many of the ocean''s dangers. Nemo is abducted by a boat and netted up and sent to a dentist''s office in Sydney. So, while Marlin ventures off to try to retrieve Nemo, Marlin meets a fish named D', 'http://www.imdb.com/video/imdb/vi2687214105/imdb/embed?autoplay=false&width=480', 'Albert Brooks, Ellen Degeneres, Alexander Gould, Willem Dafoe', 'Andrew Stanton, Lee Unkrich', '', 'movie', ' Animation, Adventure, Comedy', 'IDMB: 8.1/10', 1, 15, NULL);
      "
      );
?>