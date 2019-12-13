#!/bin/sh
echo "Creating objects, resources and pipelines for society-dec project"
oc new-project societyapp
oc new-app sonatype/nexus
oc expose svc/nexus
sleep 10
oc new-app -f societyapp.yaml
