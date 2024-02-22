FROM tomcat:8.0.51-jre8-alpine
MAINTAINER shubhamveer111@gmail.com
# copy war file on to container
COPY ./target/flipkart*.jar /usr/local/tomcat/webapps
EXPOSE  8080 80
USER flipkart
WORKDIR /usr/local/tomcat/webapps
CMD ["catalina.sh","run"]
