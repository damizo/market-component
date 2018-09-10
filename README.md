**Market-Component**  
  
****Setting up environment:****  
  
Application has three different profiles (their correspond your purposes). Set up has to be convenient and easy. You can set up application on your local machine without any external services if you want to, but it depends on your profile.  
  
 **DEV**   
 This profile is default. Tests are turned off and application running on that profile uses **H2** database.  
 It is the simplest way to run application.  
      
	 mvn clean install 
	 mvn spring-boot:run  
	 
That's it, you don't have to do anything else.  
  
**TEST**  
Profile created with unit & integration & acceptance tests in mind. It uses **H2** database as well. It's pretty   
similar to profile above, but main purpose of this profile is to run all tests.
			
	mvn clean install -P test

**LOCAL**
When I've added him, I had in my mind setting up **MariaDB** in Docker container. Unfortunately Spring has perfect support only for Embedded databases, for external databases also, but. Finally I haven't end it, because there other important stuff to do. Anyway, you can check what has left from this idea in **docker-conf** folder ;) 

**PROD**
Just like above. 

---------------------
**Swagger REST API Documentation:**
http://localhost:8082/swagger-ui.html

