FROM openjdk:8
MAINTAINER RahulAgrawal NishantMeena

COPY ./idea-portal/target/idea-portal-0.0.1-SNAPSHOT.war idea_portal.war
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=docker","idea_portal.war"]
