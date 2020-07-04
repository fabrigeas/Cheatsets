# [Simple Object Access Protocol](https://www.w3.org/2001/12/soap-envelope)

## Structure

	<?xml version = "1.0"?>
	<SOAP-ENV:Envelope xmlns:SOAP-ENV = "http://www.w3.org/2001/12/soap-envelope" 
	   SOAP-ENV:encodingStyle = "http://www.w3.org/2001/12/soap-encoding">

	   <SOAP-ENV:Header>
           <t:Transaction xmlns:t = "http://www.tutorialspoint.com/transaction/" 
				 SOAP-ENV:mustUnderstand = "true">5
            </t:Transaction>
	   </SOAP-ENV:Header>
	   <SOAP-ENV:Body>
		  ...
		  ...
		  <SOAP-ENV:Fault>
			 ...
			 ...
		  </SOAP-ENV:Fault>
		  ...
	   </SOAP-ENV:Body>
	</SOAP_ENV:Envelope>
    
SOAP with HTTP POST

	POST /OrderEntry HTTP/1.1
	Host: www.tutorialspoint.com
	Content-Type: application/soap; charset = "utf-8"
	Content-Length: nnnn

    # The soap is sent in the request body as a String 
	<?xml version = "1.0"?>
	<SOAP-ENV:Envelope 
	   ...
	</SOAP-ENV:Envelope>