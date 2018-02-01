# moneyxchange.io
Belatrix moneyxchange.io Project

The following project was made using SpringBoot (backend, port 8080) and ReactJS (frontend, port 3000). They have been separated so that changes can be made to the webpage without having to redeploy the Java services.

##Issues
Having enabled CORS (cross-origin-resource-sharing) in the SpringBoot project to permit the requests coming from port 3000, an issue was encountered while trying to perform the JWT Auth on the client side (the response, while having status OK 200, does not contain the Token in the jsonResponse). So, for the time being, the REST endpoints do not require a JWToken to be accessed.

##Disclaimer
Classes used for JWT Authentication in the SprintBoot project were taken from the implementation made by Stephan Zerhusen https://github.com/szerhusenBC/jwt-spring-security-demo
