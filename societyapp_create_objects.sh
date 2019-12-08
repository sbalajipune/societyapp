#!/bin/sh
echo "Creating society app objects and pipelines"
oc new-project societyapp
oc new-app -f societyapp.yaml