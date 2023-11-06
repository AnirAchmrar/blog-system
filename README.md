# blog-system
RESTful API for a Blog System

The application main components are a Spring Boot backend, Angular frontend and a PostgreSQL database.

Next are the steps to run the application locally:

# Run the postgres DB
Change directory to the postgres DB deployment corresponding directory from the project root directory:

`cd deployment/db`

Build a Docker image from the current directory using a Dockerfile named Dockerfile.txt. 
The "-t" flag assigns a tag (name) to the image, which is "postgres":

`docker build -t postgres . -f Dockerfile.txt`

Run a Docker container from the "postgres" image.
The "-d" flag runs the container in detached mode.
The "-p" flag maps port 5432 on the host to port 5432 in the container.
The "--name" flag assigns the container a name, which is "postgres":

`docker run -d -p 5432:5432 --name postgres postgres`

More commands to stop and start the docker container are located in `deployment/db/commands-postgres-image.txt`.

# Run the Spring Boot Backend

Change directory to the api deployment corresponding directory from the project root directory:

`cd deployment/api`

Build a Docker image from the current directory using a Dockerfile named Dockerfile.txt.
The "-t" flag assigns a tag (name) to the image, which is "blog-system-api":

`docker build -t blog-system-api . -f Dockerfile.txt`

Run a Docker container from the "blog-system-api" image.
The "-d" flag runs the container in detached mode.
The "-p" flag maps port 8090 on the host to port 8090 in the container.
The "--name" flag assigns the container a name, which is "blog-system-api":

`docker run -d -p 8090:8090 --name blog-system-api blog-system-api`

More commands to stop and start the docker container are located in `deployment/db/commands-blog-system-api-image.txt`.

# Run the Angular Frontend

Change directory to the app deployment corresponding directory from the project root directory:

`cd deployment/app`

Build a Docker image from the current directory using a Dockerfile named Dockerfile.txt.
The "-t" flag assigns a tag (name) to the image, which is "blog-system-app":

`docker build -t blog-system-app . -f Dockerfile.txt`

Run a Docker container from the "blog-system-app" image.
The "-d" flag runs the container in detached mode.
The "-p" flag maps port 4200 on the host to port 4200 in the container.
The "--name" flag assigns the container a name, which is "blog-system-app":

`docker run -d -p 4200:4200 --name blog-system-app blog-system-app`

More commands to stop and start the docker container are located in `deployment/db/commands-blog-system-app-image.txt`.

# Access to the application

After a successful running of the three docker containers, you can access the application through front end container using the following url

`http://localhost:4200/`

To log into the application, use one of the following user accounts from database bootstrapping data:

`username: patrick, password: patrick`

`username: joseph, password: joseph`

`username: charles, password: charles`

Each user can see all the blog posts from `All posts` or see his own from `My posts`. A user can only update or delete his own posts. And globally each user can create new posts or read a specific blog post.