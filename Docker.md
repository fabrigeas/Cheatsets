# Docker

## Container

 A running process + encapsulation features to keep it autonomous (isolated from host and containers).
 Each container interacts with it's own filesystem provided by a Docker Image.
 Runs natively on Linux and shares the kernel of the host machine with other containers.

 Virtual macgines run a guest OS with virtual access to host resources through hypervisors

 ## Tutorials

 download and run busybox

    docker pull busybox

starting/running containers 

    docker run busybox
    docker run busybox echo "hello from busybox"

Run container iteratively

    docker run -it busybox sh # exit to exit the container

list containers

    docker ps -a

kill/stop containers

    docker ps -a # list containers
    docker rm dfd262f18a0a ab0e283f906c 55533c7b1ce6 681d4d964c9f eaab8358d2c9 d709aa103cfd
    docker rm $(docker ps -a -q -f status=exited) // delete all exited containers
    docker container prune // clean all exited contzainers
    docker run --rm busybox echo "hello from busybox"  // --rm to clean the container once exited

## Terminology

Images
Containers - Created from Docker images and run the actual application. We create a container using docker run which we did using the busybox image that we downloaded. A list of running containers can be seen using the docker ps command.
Docker Daemon - The background service running on the host that manages building, running and distributing Docker containers. The daemon is the process that runs in the operating system which clients talk to.
Docker Client - The command line tool that allows the user to interact with the daemon. More generally, there can be other forms of clients too - such as Kitematic which provide a GUI to the users.
Docker Hub - A registry of Docker images. You can think of the registry as a directory of all available Docker images. If required, one can host their own Docker registries and can use them for pulling images

## WEBAPPS WITH DOCKER

  -d detch the terminal -p publish all ports to random ports --name rename the image to

    docker run -d -P --name static-site prakhar1989/static-site
    docker stop static-site # stop the detached container using the pseudo given

## Docker Images

    docker images # list images

  base image: have no parent eg ubuntu, debian, busybox
  child image: are built on parent images + add additional functionality
  oficial images: officially maintained by docker eg python, ubuntum busybox hello-world ...
  user image: mainained by users eg fabrigeas/golfpace

* Create your webapp
* create Dockerfile

### Dockerfile

  Textfile containing commands to create an image

Dockerfile

    # define the base image
    FROM python:3

    # set a directory for the app
    WORKDIR /usr/src/app

    # copy all the files to the container
    COPY . .

    # install dependencies
    RUN pip install --no-cache-dir -r requirements.txt

    # tell the port number the container should expose
    EXPOSE 5000

    # run the command
    CMD ["python", "./app.py"]

  
ruin 
  

    # build the image
    docker build -t fabrigeas/catnip .

    # list images
    docker images

    # start the image
    docker run -p 8888:5000 fabrigeas/catnip

    #browse
    http://localhost:8888/

## Docker on AWS

    # push fabrigeas/catnip to the docker hub
    docker push fabrigeas/catnip

    # Anyone can access fabrigeas/catnip form anywhere
    docker run -p 8888:5000 fabrigeas/catnip

## MULTI-CONTAINER ENVIRONMENTS

## Dockerizing a Node.js web app

    cd your-nodejs-app

Dockerfile

    FROM node:10

    # Create app directory
    WORKDIR /usr/src/app

    # Install app dependencies
    # A wildcard is used to ensure both package.json AND package-lock.json are copied
    # where available (npm@5+)
    COPY package*.json ./

    RUN npm install
    # If you are building your code for production
    # RUN npm ci --only=production

    # Bundle app source
    COPY . .

    EXPOSE 8080
    CMD [ "node", "server.js" ]

.dockerignore file

    node_modules
    npm-debug.log

build and run the image

    docker build -t <your username>/node-web-app .
    docker run -p 49160:8080 -d <your username>/node-web-app

Print the output of your app:

    # Get container ID
    $ docker ps

    # Print app output
    $ docker logs <container id>

    # Example
    Running on http://localhost:8080

    # Enter the container
    $ docker exec -it <container id> /bin/bash
