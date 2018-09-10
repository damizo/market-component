
**Market-Component**    

Technologies:
- Java 8
- Spring Boot
- Spock
- H2


 ****Setting up environment:****    
 Application has three different profiles (their correspond your purposes). Set up has to be convenient and easy. You can set up application on your local machine without any external services.
    
 **DEV**     
 This profile is default. Tests are turned off and application running on that profile uses **H2** database.    
 It is the simplest way to run application.    
        
	 mvn clean install   
	 mvn spring-boot:run   
  
 That's it, you don't have to do anything else.    
    
**TEST** Profile created with unit & integration & acceptance tests in mind. It uses **H2** database, as well. It's pretty     
similar to profile above, but main purpose of this profile is to run all tests.  
           
	 mvn clean install -P test  
 
**LOCAL**  
When I've added him, I had in my mind setting up **MariaDB** in Docker container. Unfortunately I haven't end it, because there was other important stuff to do. Anyway, you can check what has left from this idea in **docker-conf** folder ;)   
  
**PROD**  
Just like above.   
  
---------------------  
**Swagger REST API Documentation:**  
http://localhost:8082/swagger-ui.html  
  
**Postman**  
In folder **examples** you can find postman collection with endpoints.  
  
In order to get through business process, just invoke endpoints in order:
***GET ALL PRODUCTS*** -> ***COME UP TO CHECKOUT*** -> ***SCAN PRODUCT*** -> ***ASSIGN DISCOUNT*** -> ***MAKE PAYMENT***
