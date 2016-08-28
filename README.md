Score game application.

This application is REST service that will store the results of the passage of the fictional game.
Each player  have the opportunity to see his place for period of time,
also each player can see his result for last day and last week


Installation

Get the project , if you want to run it via terminal make sure that you have installed JDK and maven.
Go to application folder in terminal and run  mvn spring-boot:run, 
after launch the application you can find it running on http://localhost:8080/                                                   
Also you can import project as maven project to IDE and use it from there.<br />
You need MySQL Server, you can set options for you in application.properties file in this lines:<br />
spring.datasource.url =YOUR DATABASE CONNECTION URL <br />
spring.datasource.username = USERNAME <br />
spring.datasource.password = PASSWORD <br />
 
Usage

If you want see user score by day sent GET request to:<br />
user/score/day?id=x&date=y <br />
where x=id of exist user and y=date in format yyyy-MM-dd HH:mm:ss 


If you want to see user score by last week sent GET request to: <br />
http://localhost:8080/user/score/lastweek?id=x <br />
where x=id of exist user

If you want add score to exist user sent POST request to: <br />
http://localhost:8080/user/addscore with parameters id,date,timezone,score 

If you want list of score(from Max to Min) for some period sent GET request to:<br />
http://localhost:8080/user/getscore?start=x&end=y <br />
where x start of period and y end of period 

If you want get position of user for some period sent GET request to: <br />
http://localhost:8080/user/getpositionbyid?start=x&end=y&id=z <br />
where x start of period and y end of period and z id of user
