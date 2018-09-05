[![forthebadge](https://forthebadge.com/images/badges/made-with-java.svg)](https://forthebadge.com)&nbsp;[![forthebadge](https://forthebadge.com/images/badges/powered-by-electricity.svg)](https://forthebadge.com)
<br>
## Tailer - An application monitor 
Tailer monitors a log file. For each new row entered in the log file the application will be picked up and sent to a socket server (see separate git repo). The socket server will then distribute the message to all its subscribers (plugins).
<br>
<br>
Tailer can ask for a pulse (heartbeat) from the application. If this feature is activated, the application will send a pulse every 30 second, to the socket server. This can be used to see if the application that is producing the log file is up and running, good if no logs are being produced and you still want confirmation the application is up and running.

**Server:<br>**
Socket Server&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[Git repo](https:)&nbsp;(Coming soon)<br>

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
`1. Open src/resources/application.properties.`<br>
`2. Fill out logfile.url for the application to monitor.`<br>
`3. Fill out pulse.url (recommended).`<br>
`4. Optional communication port.`<br>
`5. Compile using Maven`<br>
`6. Run in terminal using: java -jar tailer-0.0.1-SNAPSHOT.jar `

## Contribute

Want to help? send me a message. <br> **`Harald.billstein@gmail.com`**

