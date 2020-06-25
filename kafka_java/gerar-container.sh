#!/bin/bash
docker image ls
mvn package -s settings.xml
docker image build -t fernando107/magpi:latest .
docker image push fernando107/magpi:latest
docker image ls
