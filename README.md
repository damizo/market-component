**Market-Component**

**1.** Setting up environment:

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