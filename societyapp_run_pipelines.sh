#!/bin/sh
echo "Running the build and deployment pipelines"
oc start-build member-app-pipeline
echo "Wait for 10 mins for this pipeline to finish so that other builds will be successful as nexus would have cached the maven artifacts by then"
#wait for this pipeline to finish so that other builds will be successful as nexus would have cached the maven artifacts by then
sleep 600
oc start-build vehicle-app-pipeline
sleep 60
echo "Wait for 1 min to finish the build"
oc start-build parking-app-pipeline
echo "Wait for 1 min to finish the build"
sleep 60
oc start-build apartment-app-pipeline
echo "Wait for 1 min to finish the build"
sleep 60