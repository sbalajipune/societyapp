#!/bin/sh
echo "Creating society app objects and pipelines"
oc start-build member-app-pipeline
#wait for this pipeline to finish so that other builds will be successful as nexus would have cached the maven artifacts by then
sleep 60000
oc start-build vehicle-app-pipeline
sleep 6000
oc start-build parking-app-pipeline
sleep 6000
oc start-build apartment-app-pipeline