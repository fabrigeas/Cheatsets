# Docker

Platform to build, run and share apps with containers.\
`containerization` using containers to deploy applications.

## Examples

### 1 bulletin-board-app

```bash

# Download a containerized repo
git clone https://github.com/dockersamples/node-bulletin-board
cd node-bulletin-board/bulletin-board-app

# build the image
# (Windows users) Docker logo in your system tray, Switch to Linux containers
docker build --tag bulletin:1.0 .

# run image as container (create container)
docker run --publish 8000:8080 --detach --name bb bulletin:1.0

# browser
http://localhost:8000/

# delete the container
docker rm --force bb

```

## 2 Balancesheet backend

## Share images on Docker Hub

```bash

```

## busybox example

```docker
# download and run busybox
docker pull busybox

# starting/running containers
docker run busybox
docker run busybox echo "hello from busybox"

# run container and exec command
docker run -it busybox echo 'hello world'

# Run container interactively
docker run -it busybox sh

# to exit the interactive container
exit

# list containers
docker ps -a

# kill/stop containers
docker rm dfd262f18a0a # container name or id
docker rm dfd262f18a0a ab0e283f906c 55533c7b1ce6 681d4d964c9f eaab8358d2c9 d709aa103cfd
docker rm $(docker ps -a -q -f status=exited) # delete all exited containers
docker container prune # clean all exited containers
docker run --rm busybox echo "hello from busybox"  # --rm to clean the container once exited
```

## Terminology

### Container

A running process running in isolation with it's own private filesystem provided by a Docker Image.

### Docker Image

provides the code bin, runtimes, dependencies, file systems etc everything required to run a container.\
Runs natively on Linux and shares the kernel of the host machine with other containers.\
Virtual machines run a guest OS with virtual access to host resources through hypervisors

- `base image`: have no parent eg ubuntu, debian, busybox, node
- `child image`: are built on parent images + add additional functionality
- `user image`: maintained by users eg fabrigeas/golfpace

### Dockerfile

file containing commands to create an image\
Describes how to assemble a private file system for a container

### Docker Daemon

Background service running on the host OS and manages building, running and distributing Docker containers.

Docker Client
\_The command line tool that allows the user to interact with the Docker daemon\*\
e.g of clients `Kitematic` which provide a GUI to the users.

### Docker Hub

A registry of Docker images.

## Cheat sheet

```docker

### Images

# list images
docker images
docker images -a


# list dangling images
docker images -f dangling=true

# remove
docker rmi Image Image
docker rmi $(docker images -a -q)
# remove dangling images
docker images purge
docker images -a | grep "pattern" | awk '{print $3}' | xargs docker rmi

#### Containers

# list containers
docker ps -a
docker ps -a -f status=exited

# run and remove a container upon exit
docker run --rm image_name


# remove containers
docker ps -a |  grep "pattern"
docker ps -a | grep "pattern" | awk '{print $3}' | xargs docker rmi
docker rm $(docker ps -a -f status=exited -q)
docker rm $(docker ps -a -f status=exited -f status=created -q)

# stop and remove all containers
docker stop $(docker ps -a -q)
docker rm $(docker ps -a -q)
docker rm --force $(docker ps -a | awk '{print $1}')

# stop amd remove a container
docker rm --force

# Volumes
docker volume ls
docker volume rm volume_name volume_name

#remove all unused images, containers, volumes
docker system prune -a
docker system prune

# push fabrigeas/catnip to the docker hub
docker push fabrigeas/catnip

### Misc
# Anyone can access fabrigeas/catnip form anywhere
docker run -p 8888:5000 fabrigeas/catnip

# stop and remove a container
docker rm --force bb

# Print app output
$ docker logs <container id>

# Enter the container
$ docker exec -it <container id> /bin/bash

```
