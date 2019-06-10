#!/bin/bash

FILE=$1
URL=$2

curl -k -s -F "fix=@$1" "$URL/fixman/send"
