generate key pair
	
	gpg --gen-key

revocation key to be stored in a remote computer, and will be used  to recover the lost key pair

	gpg --gen-revoke your_email@address.com

search a key from the mit keyserver
	
	gpg --keyserver pgp.mit.edu  --search-keys someones@gmail.com

import the public key of someone else, this key is saved in a file

	gpg --import name_of_pub_key_file

fingerprint a key to check it. use fingerprint to generate a number that will be checked by both parties as checkup
	
	gpg --fingerprint your_email@address.com

once the fingerprint is checked and ok, sign the key so as to make it trusted
	
	gpg --sign-key email@example.com
	armor= display

	gpg --export --armor x@example.com
	gpg --export --armor x@example.com > output.txt

import someone's key that is saved in a file
	
	gpg --import file_name

export a key to a file
	
	gpg --armor --export the_key@address.com >filename.txt

export key to key server list keys displays a list of keys SIZE/key_id

	gpg --list-keys
	gpg --send-keys --keyserver pgp.mit.edu key_id


send ecrypted email
	
	gpg --encrypt --sign --armor -r receipients@email.com message_file.ext
	gpg encripted_file_name.ext

update key

	gpg --refresh-keys
	gpg --keyserver key_server --refresh-keys



delete key
	
	gpg --delete-secret-key fabrigeas@gmail.com

Sample

	gpg --gen-key
	gpg --export --armor fabrigeas@gmail.com > fabrigeas@gmail.com
	gpg --export-secret-key -a fabrigeas@hotmail.com > fabrigeas-key.asc

	gpg --gen-revoke your_email@address.com
	gpg --send-keys --keyserver pgp.mit.edu key_id

	gpg --keyserver pgp.mit.edu  --search-keys someones@gmail.com
	gpg --list-keys
	gpg --import name_of_pub_key_file
	gpg --fingerprint your_email@address.com
	gpg --sign-key email@example.com

	gpg --encrypt --sign --armor -r receipients@email.com message_file.ext
	gpg encripted_file_name.ext
