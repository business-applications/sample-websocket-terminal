# Business Applications by jBPM - Websocket Terminal

This is a demo business application built with https://start.jbpm.org and expanded
to create a process-driven websocket terminal. 

![Sample of demo](img/demoscreenshot.png?raw=true)

In this demo we create a process driven websocket web terminal. Every command entered in the terminal
starts our business process which then executes the command. 
We use websockets so that the results of the business process are dynamically updated on the terminal 
web page, without having to refresh the page at all.

Each command is evaluated by business rules which determine if the command entered is "dangerous" by comparing it
to set of determined dangerous commands such as "delete", "reboot" etc, if the command is a weather command
which are commands that request weather info such as "show temp for Atlanta,UNITED_STATES", or if this is a 
safe command to execut such as "ls -ltr", "pwd", or similar.

![Demmo process](img/demoprocess.png?raw=true)

## Getting Started 
1. Clone and build the needed kie server thymeleaf dialect project:
```
git clone https://github.com/tsurdilo/thymeleaf-kie-server-dialect.git
cd thymeleaf-kie-server-dialect
mvn clean install
```

2. Clone this repository locally and start the demo app:

```
git clone https://github.com/business-applications/sample-websocket-terminal.git
cd sample-websocket-terminal
cd sample-websocket-terminal-service
chmod 755 launch.sh (only needed for unix environments , use launch.bat for windows)
./launch.sh (or launch.bat for windows)
```

3. Setup an account for OpenWeatherMap (https://openweathermap.org/price) to get an access key for the free
offer they have (Click on the "Get Api key an Start" button)

![OpenWeatherMapApiKey](img/getapikey.png?raw=true)

4. Update the Workitem Handler with your OpenWeatherMap api key.
In in sample-websocket-terminal-kjar/src/main/resources/META-INF/kie-deployment-descriptor.xml 
replace the YOUR_API_KEY_HERE:

```xml
    <work-item-handler>
      <resolver>mvel</resolver>
      <identifier>new org.jbpm.process.workitem.exec.ExecWorkItemHandler()</identifier>
      <parameters/>
      <name>Exec</name>
    </work-item-handler>
    <work-item-handler>
      <resolver>mvel</resolver>
      <identifier>new org.jbpm.process.workitem.owm.CurrentWeatherWorkitemHandler("YOUR_API_KEY_HERE")</identifier>
      <parameters/>
      <name>CurrentWeather</name>
    </work-item-handler>
    <work-item-handler>
      <resolver>mvel</resolver>
      <identifier>new org.jbpm.process.workitem.owm.DailyForecastWorkitemHandler("YOUR_API_KEY_HERE")</identifier>
      <parameters/>
      <name>DailyForecast</name>
    </work-item-handler>
```


with the real api key you get after registering on the OpenWeatherMap.

5. go to sample-websocket-terminal-service and start the appliaction:

for Windows users:

```
launch.bat clean install
```

or for Unix/Linux users

```
chmod 755 launch.bat (only has to be done once)
./launch.sh clean install
```

Note that step 3 here is optional and if you would rather not go through the process of setting up an api key
on OpenWeatherMap it is ok. In this case skip step 3 and keep "YOUR_API_KEY_HERE" as is in step 4. Your app will run
fine, you just will not be able to get results for commands that start with "show temp for". 

## Accessing your application

Once the app has started you can access the landing page at 

```
localhost:8090/demo
```

Depending on the system you are running on you can start entering commands into the Terminal
and results will be dynamically posted. 
For example if running on Unix based systems some sample commands could be 

```
ls -ltr
whoami
pwd
```
etc. For DOS users you can use the dos based commands and try them out.
If running on OSX you can enter OSX commands as well, for example:

```
open -t
open -a firefox someFile.someFileExtension
```

If you have dont step 3 of the "Getting Started" section, your business app can get us the current weather information as well.
For this you can enter into the terminal for example:

```
show temp for Atlanta,UNITED_STATES
show temp for Londin,UNITED_KINGDOM
show temp for Warshaw,POLAND
etc etc
```

Special thanks to Andrew Barfield (https://gist.github.com/AndrewBarfield) as 
this demo uses a (heavily) modified version of his HTML web terminal implementation 
that you can find here: https://gist.github.com/AndrewBarfield/3ee59402501422cdec47
