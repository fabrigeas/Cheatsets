# [Home](http://nightwatchjs.org/)

Write Webapp automated tests.

## Requirements and Installations

    npm install -g nightwatch
    npm install geckodriver --save-dev
    npm install chromedriver --save-dev
    
[Download Selenium](http://selenium-release.storage.googleapis.com/index.html)

    Download the latest version of the selenium-server-standalone-{VERSION}.jar, into webapp/bin.
    
Running Selenium Manually

    java -jar selenium-server-standalone-{VERSION}.jar
    
Using Selenium Standalone Server

    java -jar selenium-server-standalone-{VERSION}.jar -help
    
Configuration

    nightwatch.json //next to node_modules
