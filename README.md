# moneyxchange.io
Belatrix moneyxchange.io Project

The following project was made using SpringBoot (backend, port 8080) and ReactJS (frontend, port 3000). They have been separated so that changes can be made to the webpage without having to redeploy the Java services. The DB used for development was a MySQL instance running on a Docker Container (generic mysql image). The DB schema can be found in the resources folder of the Java project.

## Disclaimer
Classes used for JWT Authentication in the SprintBoot project were taken from the implementation made by Stephan Zerhusen https://github.com/szerhusenBC/jwt-spring-security-demo
