# Installation Instructions for CityRankApp

---
## Setup
---

### Java Setup
1. Open a command line and
2. Enter "java - version"
3. Should be 1.8, if not, please update
4. Enter "echo $JAVA_HOME", should point to the 1.8 jdk
5. If it doesn't, enter "export JAVA_HOME=$(/usr/libexec/java_home)"
6. Try "echo $JAVA_HOME" again

---

### Maven Setup
1. Download Apache Maven from https://maven.apache.org/download.cgi
2. Installation instructions can be found here https://maven.apache.org/install.html
3. Once complete, run "mvn -version" to ensure you have 3.5.0 installed like below

Apache Maven 3.5.0 (ff8f5e7444045639af65f6095c62210b5713f426; 2017-04-03T13:39:06-06:00)  
Maven home: /Applications/apache-maven-3.5.0  
Java version: 1.8.0_151, vendor: Oracle Corporation  
Java home: /Library/Java/JavaVirtualMachines/jdk1.8.0_151.jdk/Contents/Home/jre  
Default locale: en_US, platform encoding: UTF-8  
OS name: "mac os x", version: "10.12.6", arch: "x86_64", family: "mac"  

---

### Tomcat Setup
1. Download Apache Tomcat v8.5 from https://tomcat.apache.org/download-80.cgi
2. Extract to a convenient location
3. Navigate to "apache-tomcat-8.5.23/bin" in command line
4. Give execute permission to catalina.sh, startup.sh, and shutdown.sh (ex. chmod 777 startup.sh)

---

## Build and Run
1. Clone project to a directory https://github.com/officialjallen/emsiapp
2. Enter the "emsiapp" directory
3. Run "mvn clean install"
4. Project should Build
5. Enter the "target" directory
6. Copy the "CityRankApp.war" file to the "apache-tomcat-8.5.23/webapps" folder
7. In the command line in "apache-tomcat-8.5.23/bin" run "./startup.sh"
8. Tomcat should startup, and the service should be running on http://localhost:8080

Note: I have added the compiled war file in the "Compiled War" folder

## Sample Requests
I used Postman to send these

### Get City by ID - GET Request
http://localhost:8080/CityRankApp/city/4

### Get Ranked List of Cities - POST Request
http://localhost:8080/CityRankApp/rank  
Requires a JSON request like below:  
```
{  
	"weights": {  
		"walkability": 4,  
		"job_growth": 1.0,  
		"green_space": 2.5,  
		"taxes": 0.5  
	}  
}  
```
as well as a header with Key: "Content-Type" and Value: "application/json"
