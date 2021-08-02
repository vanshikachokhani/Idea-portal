FROM openjdk:8
MAINTAINER RahulAgrawal NishantMeena

COPY Idea-portal/idea-portal/target/idea-portal-0.0.1-SNAPSHOT.war idea_portal.war
ENTRYPOINT ["java","-jar","idea_portal.war"]