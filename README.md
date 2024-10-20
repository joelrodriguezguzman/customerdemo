# Demo App Technical Challenge
## General Objective
Create a RESTful API that provides basic CRUD and other relevant operations on the following domain entity object. You can use an embedded database or external database. Be sure you explain how to create and/or connect to the database and any other relevant information. Note that your thoughts on SQL (or database specific language) is relevant. Make the attribute names relevant and accessible as well, do not simply copy the names we have provided below. Don’t forget unit testing. This repo is a technical exercise demo that shows:
- The creation of a public GitHub Repository for this exercise (this customer repo).
- Ensures all code is on main.
- Provide proper instructions in this README.md of how to setup prerequisites, build the code, and ultimately run and test the code.

#### Entity: Customer
| Attribute     | Type   | Constraints | Notes             |
|---------------|--------|-------------|-------------------|
| Id            | UUID   | PK          |                   |
| First Name    | String |             |                   |
| Middle Name   | String |             | Null is acceptable|
| Last Name     | String |             |                   |
| Email Address | String | Unique      |                   |
| Phone Number  | String |             | Can be composite  |

## Instructions to setup prerequisites
Ensure to visit https://start.spring.io/ and fill out the following prereqs before generating the project files

Language: Java
Spring Boot Version: 3.3.4
Package: Jar
Java Version: 17
Dependencies: JPA, Spring Web, Spring Boot DevTools, H2 Database

## Prerequisites
Add the following additional dependencies to the pom.xml
org.springdoc - springdoc-openapi-starter-webmvc-ui
io.swagger.core.v3 - swagger-annotations
org.xerial - sqlite-jdbc
org.hibernate.orm - hibernate-community-dialects

Ensure to have java sdk 17 and latest version of Maven installed in your local development environment

## Build the code
mvn clean install

## Test the code
mvn test

### Integration and/or Acceptance Testing
Create a pattern and implement it for integration and or acceptance testing of your REST API. Be sure to provide clear instructions on how to run it and possibly expand on how you might automate this for CI/CD.

### Provide Observability
To your API from the previous step, provide common and well-constructed observability into the functioning of your application. Assumptions on collectors of this telemetry data is fine, just document them.

### Containerization Prerequisites
Containerize your API. Be sure to document how you need to run the container and what inputs you need to provide to run the application.
Ensure that a private registry has firewall open ports 5000, 8080, 2375 for containerization in a local dev environment
Ensure the registry daemon is running and configure for remote access to dev containers in vs code.
Ensure to install dev containers and docker extension in vs code and setup the local dev environment variables to connect to the remote registry.

### Containerization
#### use maven to do a clean install of the jar file for the app to ensure it will run properly in docker
mvn clean package 
#### Test the jar files locally before pushing container
java -jar target/customer-0.0.1-SNAPSHOT.jar
#### Then build the docker image
docker build -t customer .
#### Test the docker image from the docker instance 
docker run --rm -it -v /mnt/hgfs/Documents/1cJava/web:/workspace -p 8080:8080 customer 
#### Use the docker instance URL to test the app running from the docker instance
http://192.168.xxx.xxx:8080/hello http://192.168.xxx.xxx:8080/swagger-ui/index.html

#### Tag image to ready it for pushing it to the private registry
docker tag customer:latest 192.168.231.128:5000/customer:latest    
#### Push the image when ready
docker push 192.168.231.128:5000/customer:latest

### Kubernetes
Prepare your application for deployment to Kubernetes. You can leverage technologies like Kind or MiniKube to test this as you see fit, but be sure to create the pathway for deployment that is extensible. Include instructions needed for deploying your application to a Kubernetes instance.

This app uses the terraform templates to configure permissions, availability groups, nodes, clusters, subnets, ips and resources needed to deploy the eks cluster at AWS. All resources are created with the templates but logic added to rerun the deployment as needed reusing resources that exists with the same names already. The deployment.yaml has been turn off with .yaml.bak for this exercise and the real secrets have not been added to this public repo and thus this templates are for demo purpose only.

### CI/CD
Design and document a CI/CD pipeline for your application. Put this documentation in your repository in a location appropriate for you. Be sure to call out any manual or automated gates in your process.

CI/CD Pipeline Steps
Checkout Code:
Retrieves the latest code from the repository.
Set Up JDK:
Configures the Java Development Kit (JDK) for the build environment.
Run Linting: (NOTE left out and shown for demo purposes for this app due to time constraint)
Executes Checkstyle to ensure the code adheres to coding standards.
Build Application:
Uses Maven to compile the code and package the application.
Run Tests:
Executes unit tests to verify the functionality of the application.
Initialize CodeQL:
Sets up CodeQL for static code analysis.
Perform CodeQL Analysis:
Analyzes the code for security vulnerabilities and code quality issues.
Run Secret Detection:
Scans the code for any hardcoded secrets or sensitive information.
Build Docker Image:
Creates a Docker image of the application.
Scan Docker Image:
Uses Trivy to scan the Docker image for vulnerabilities.
