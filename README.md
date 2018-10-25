# Business Applications by jBPM - Websocket Terminal

This is a demo business application built with https://start.jbpm.org and expanded
to create a process-driven websocket terminal. 

![Sample of demo](img/demoscreenshot.png?raw=true)

In this demo we create a process driven websocket web terminal. Every command entered in the terminal
starts our business process which then executes the command. In the demo the business process
uses the jBPM Exec Workitem, and the jBPM OpenWeatherMap Workitem. The Exec workitem executes
all commands this do not start with "show temp for", where as the OpenWeatherMap workitem is responsible 
to execute commands such as "show temp for Atlanta,UNITED_STATES". 
We use websockets so that the results of the business process are dynamically updates on the terminal 
web page, without having to refresh the page at all.

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

3. Setup account for OpenWeatherMap (https://openweathermap.org/price) to get an access key for the free
offer they have (Click on the "Get Api key an Start" button)

![OpenWeatherMapApiKey](img/getapikey.png?raw=true)

4. Update the Workitem Handler with your OpenWeatherMap api key:
in sample-websocket-terminal-kjar/src/main/resources/META-INF/kie-deployment-descriptor.xml

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

## Accessing your application

Once the app has started you cann access the landing page at 

```
localhost:8090/demo
```

Depending on the system you are running on you can start entering commands into the Terminal
and results will be dynamically posted. 
For example if running on Unix based systems some someple commands could be 

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

In addition our business process an get us the current weather information. For this 
you can enter into the terminal for example:

```
show temp for Atlanta,UNITED_STATES
show temp for Londin,UNITED_KINGDOM
show temp for Warshaw,POLAND
etc etc
```

Special thanks to Andrew Barfield (https://gist.github.com/AndrewBarfield) as 
this demo uses a (heavily) modified version of his HTML web terminal implementation 
that you can find here: https://gist.github.com/AndrewBarfield/3ee59402501422cdec47
