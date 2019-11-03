FROM adoptopenjdk/openjdk11:alpine

LABEL maintainer="Bastien Marsaud <bastien@mouseover.fr>"

ARG FILE_NAME
ENV FILE_NAME=$FILE_NAME

RUN mkdir -p /opt/calendar-shaper
COPY $FILE_NAME /opt/calendar-shaper

CMD java -jar /opt/calendar-shaper/$FILE_NAME
