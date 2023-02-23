#!/bin/bash

cd ./petshop-admin-service && mvn clean install && docker build -t petshop-admin-srvc .
cd ../petshop-attendant-service && mvn clean install && docker build -t petshop-attendant-srvc .
cd ../petshop-auth-service && mvn clean install && docker build -t petshop-auth-srvc .
cd ../petshop-config-server && mvn clean install && docker build -t petshop-config-srvc .
cd ../petshop-customer-service && mvn clean install && docker build -t petshop-customer-srvc .
cd ../petshop-discovery-server && mvn clean install && docker build -t petshop-discovery-srvc .
cd ../petshop-gateway && mvn clean install && docker build -t petshop-gateway .
cd ../petshop-veterinarian-service && mvn clean install && docker build -t petshop-veterinarian-srvc .
cd ../petshop-visit-service && mvn clean install && docker build -t petshop-visit-srvc .
