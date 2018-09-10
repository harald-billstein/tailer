[![forthebadge](https://forthebadge.com/images/badges/made-with-java.svg)](https://forthebadge.com)&nbsp;[![forthebadge](https://forthebadge.com/images/badges/powered-by-electricity.svg)](https://forthebadge.com)
<br>
## Tailer - An application monitor 
Tailer monitors a log file. For each new row entered in the log file the line will be picked up and sent to a socket server (see separate git repo). The socket server will then distribute the message to all its subscribers (plugins).
<br>
<br>
Tailer can ask for extra information (recommend, Spring Boot Actuator) from the application. If this feature is activated, the application will send this information every 30 second to the socket server. This can be used to see if the application, that is producing the log file, is up and running. If no logs are being produced and you want the current status.

**Server:<br>**
Socket Server&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[Git repo](https://github.com/harald-billstein/socketServer.git)&nbsp;<br>

**Available plugins:<br>**
Email Alerter &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-&nbsp;&nbsp;&nbsp;&nbsp;[Git repo](https:)&nbsp;(Coming soon)<br>
Log Saver &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-&nbsp;&nbsp;&nbsp;&nbsp;[Git repo](https:)&nbsp;(Coming soon)<br>
GUI Interface &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-&nbsp;&nbsp;&nbsp;&nbsp;[Git repo](https:)&nbsp;(Coming soon)<br>
Cellphone notifications  &nbsp;&nbsp;&nbsp;-&nbsp;&nbsp;&nbsp;&nbsp;[Git repo](https:)&nbsp;(Coming soon)<br>

## Motivation
I was looking for a way to update a web view instantly, to push updates. The solution i found was web sockets. I wanted to learn more and decided to build the application. I choose Springboot, a framework i wanted to learn more about. The result will be an easy to use application that can be used to build more plugins, for your needs.


## Tech/framework used

<b>Built with</b>
- [SpringBoot](https://spring.io/projects/spring-boot)
- [Maven](https://maven.apache.org/)
- [Java8](https://www.oracle.com/technetwork/java/javase/overview/java8-2100321.html)
- [Intellij](https://www.jetbrains.com/idea/)

## Installation

**Make sure Maven is installed!**

`1. Open src/resources/application.properties.`<br>
`2. Fill out logfile.url for the application to monitor.`<br>
`3. Fill out pulse.url (recommended).`<br>
`4. Optional communication port.`<br>
`5. got to file build.sh and type: sh build.sh`

## API functions

**Start tailing:**<br>
`Method: POST`<br>
`url: /v1/application/tailer/starter`

**Stop tailing:**<br>
`Method: POST`<br>
`url /v1/application/tailer/stop`

**Start pulse:**<br>
`Method: POST`<br>
`url /v1/application/pulse/starter`

**Stop pulse:**<br>
`Method: POST`<br>
`url /v1/application/pulse/stop`

## Contribute

Want to help? send me a message. <br> **`Harald.billstein@gmail.com`**

