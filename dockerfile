FROM java:8
MAINTAINER cshouu <cshouu@gmail.com>
ADD ./springboot.jar /app.jar
CMD java -jar /app.jar --spring.profiles.active=prod
