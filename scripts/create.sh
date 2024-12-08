#!/bin/bash

sebastien_token=$(curl http://127.0.0.1:8000/api/v1/auth/sign-up \
  -H "Content-Type: application/json" \
  -d '{ "email": "sebastien@mail.com", "password": "changeme" }')

bashar_token=$(curl http://127.0.0.1:8000/api/v1/auth/sign-up \
  -H "Content-Type: application/json" \
  -d '{ "email": "bashar@mail.com", "password": "changeme" }')

pablo_token=$(curl http://127.0.0.1:8000/api/v1/auth/sign-up \
  -H "Content-Type: application/json" \
  -d '{ "email": "pablo@mail.com", "password": "changeme" }')

seriai_token=$(curl http://127.0.0.1:8000/api/v1/auth/sign-up \
  -H "Content-Type: application/json" \
  -d '{ "email": "pablo@mail.com", "password": "changeme" }')

product1=$(curl http://127.0.0.1:8000/api/v1/products \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $sebastien_token" \
  -d '{ "name": "Hello", "price": 100, "expirationDate": "2024-12-07T13:28:26Z" }')

product2=$(curl http://127.0.0.1:8000/api/v1/products \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $sebastien_token" \
  -d '{ "name": "Hello 2", "price": 100, "expirationDate": "2024-12-07T13:28:26Z" }')

product3=$(curl http://127.0.0.1:8000/api/v1/products \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $pablo_token" \
  -d '{ "name": "Hello 3", "price": 300, "expirationDate": "2024-12-07T13:28:26Z" }')

product4=$(curl http://127.0.0.1:8000/api/v1/products \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $bashar_token" \
  -d '{ "name": "Hello 4", "price": 400, "expirationDate": "2024-12-07T13:28:26Z" }')

product1_id=$(echo "$product1" | jq -r '.id')
product2_id=$(echo "$product2" | jq -r '.id')
product3_id=$(echo "$product3" | jq -r '.id')
product4_id=$(echo "$product4" | jq -r '.id')

product4_modified=$(curl http://127.0.0.1:8000/api/v1/products/${product4_id} \
  -X PUT\
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $seriai_token" \
  -d '{ "name": "EditedProduct", "price": 400, "expirationDate": "2024-12-08T13:28:26Z" }')

echo $(curl http://127.0.0.1:8000/api/v1/products/$product2_id \
  -X DELETE \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $seriai_token" \
  -d "{ \"id\": $product2_id }")

echo $(curl http://127.0.0.1:8000/api/v1/products/$product3_id \
  -X DELETE \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $seriai_token" \
  -d "{ \"id\": $product3_id }")

echo $(curl http://127.0.0.1:8000/api/v1/products/$product4_id \
  -X DELETE \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $seriai_token" \
  -d "{ \"id\": $product4_id }")
