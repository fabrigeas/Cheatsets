# Docker

Platform to build run and share apps with containers.\
`containerization` using containers to deploy applications.

## Container

A running process + that runs in isolation with it's own private filesystem provided by a Docker Image.

`images` provides everything required to run the app. is \
the code bin, runtimes, dependencies, file systems etc

Runs natively on Linux and shares the kernel of the host machine with other containers.\
Virtual machines run a guest OS with virtual access to host resources through hypervisors

## Tutorials

```docker
# download and run busybox
docker pull busybox

# starting/running containers
docker run busybox
docker run busybox echo "hello from busybox"

# Run container iteratively
docker run -it busybox sh # exit to exit the container

# list containers
docker ps -a

# kill/stop containers

docker ps -a # list containers
docker rm dfd262f18a0a ab0e283f906c 55533c7b1ce6 681d4d964c9f eaab8358d2c9 d709aa103cfd
docker rm $(docker ps -a -q -f status=exited) # delete all exited containers
docker container prune # clean all exited contzainers
docker run --rm busybox echo "hello from busybox"  # --rm to clean the container once exited
```

## Terminology

**Image**

**Containers**
_Created from Docker images_\
`docker run` creates a container
`docker p` list containers

**Docker Daemon**\
Background service running on the host OS and manages building, running and distributing Docker containers.

**Docker Client**: \_The command line tool that allows the user to interact with the Docker daemon\*\
e.g of clients `Kitematic` which provide a GUI to the users.

**Docker Hub:**\_A registry of Docker images.\_

## WEBAPPS WITH DOCKER

```docker
docker -d fetch the terminal -p publish all ports to random ports --name rename the image to
docker run -d -P --name static-site prakhar1989/static-site
docker stop static-site # stop the detached container using the pseudo given
```

## Docker Images

```docker
docker images # list images
```

- `base image`: have no parent eg ubuntu, debian, busybox, node
- `child image`: are built on parent images + add additional functionality
- `user image`: maintained by users eg fabrigeas/golfpace

- Create your webapp
- create Dockerfile

### Dockerfile(Text file containing commands to create an image)

Dockerfile

```docker
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
```

run

```docker
# build the image
docker build -t fabrigeas/catnip .

# list images
docker images

# start the image
docker run -p 8888:5000 fabrigeas/catnip

#browse
http://localhost:8888/

```

## Docker on AWS

```docker
# push fabrigeas/catnip to the docker hub
docker push fabrigeas/catnip

# Anyone can access fabrigeas/catnip form anywhere
docker run -p 8888:5000 fabrigeas/catnip
```

## MULTI-CONTAINER ENVIRONMENTS

## Dockerizing a Node.js web app

```sh
cd your-nodejs-app
```

Dockerfile

```docker
  FROM node:10

# push fabrigeas/catnip to the docker hub
docker push fabrigeas/catnip

# Anyone can access fabrigeas/catnip form anywhere
docker run -p 8888:5000 fabrigeas/catnip
```

.dockerignore

```docker
node_modules
npm-debug.log
```

build and run the image

```docker
docker build -t <your username>/node-web-app .
docker run -p 49160:8080 -d <your username>/node-web-app
```

Print the output of your app:

```docker
# Get container ID
$ docker ps

# Print app output
$ docker logs <container id>

# Example
Running on http://localhost:8080

# Enter the container
$ docker exec -it <container id> /bin/bash
```
