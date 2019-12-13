# App Functionality
Society app that returns the details of apartments in a society
   e.g given the apartment id, this will return 
   1. the details of members in apartment
   2. the details of parking(s)
   3. the details of vehicles owned by members of this apartment

This repo contains 4 springboot apps as below
1. member-service - returns the details of members in society e.g. member id, name, age, profession
2. vehicles-service - returns the details of vehicles registered in society e.g. registration id, owner member id, owner details (gets these details by calling member-setvice)
3. parking-service - returns the details of parkings in society e.g. parking id, apartment id, vehicle(s) details (gets these details by callling vehicles-service), owner details  (gets these details by calling member-setvice)
4. apartment-service - returns the details of apartments in society e.g. apartment id, owner details, members details (gets these details by calling member-setvice), parking details ((gets these details by calling parking-setvice)

# Steps to run this app
1. clone this repo
   git clone <repo clone URL>

2. create the objects for this app in minishift/openshift cluster
   sh societyapp_create_objects.sh
   
   This will have created following
   1. A project in minishift/openshift with name 'societyapp'
   2. postgresql - running service
   3. Jenkins - running service
   4. Nexus - running service
   5. BuildConfig, DeploymentConfig, Route and Service objects for following apps
		1. member-services
		2. vehicles-service
		3. parking-service
		4. apartment-service
   6. Jenkins pipelines for building and deploying above apps
        1. member-app-pipeline
		2. vehicles-app-pipeline
		3. parking-app-pipeline
		4. apartment-app-pipeline

3.  Start the pipelines to build and deploy apps
	sh societyapp_run_pipelines.sh
	
	This script will trigger all the above 4 pipelines in that sequence. The member-app-pipeline will take little longer since it download all jars during maven build first time. So there is 10 mins delay given after triggering this pipeline.
	For rest of the pipelines, 3 mins delay is given. This delay may not be required when it's run in actual openshift environment. Minishift has the limitation of resources so triggering all the pipelines at the same time may have impact on the minishift cluster.
	
	Each of this pipelines does below
	1. Create S2I build and push the image on local image registry. Nexus is used as MAVEN_MIRROR so the subsequent builds are faster as the jar download will happen from Nexus instead of downloading them from the actual sources.
	2. Deploy this latest image in minishift
	3. Triiger the postman collection to perform the integration testing
		
