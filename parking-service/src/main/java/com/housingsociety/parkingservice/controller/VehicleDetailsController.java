package com.housingsociety.parkingservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

@RestController
@RequestMapping("/vehicledetails")
public class VehicleDetailsController {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Environment env;

    /*private String remoteURL;
    private VehicleDAO vehicleDAO;

    private static final Logger LOG = LoggerFactory.getLogger(VehicleDetailsController.class);

    @PostConstruct
    public void init() {
        remoteURL = "http://" + env.getProperty("member-service", "member-service") + ":8080/";
        vehicleDAO = new VehicleDAO();
    }

    @RequestMapping("/registrationId/{registrationId}")
    public VehicleDetails getVehicleDetailsByRegistrationId(@PathVariable("registrationId") String registrationId)
    {
        Vehicle vehicle = vehicleDAO.getVehicleByRegistrationId(registrationId);
        LOG.info("Invoking member-service");
        Member member = restTemplate.getForObject(remoteURL + "members/member/" + vehicle.getOwnerId(),  Member.class);
        VehicleDetails vehicleDetails = new VehicleDetails(vehicle.getRegistrationId(), member, vehicle.getParkingId(), vehicle.getModel(), vehicle.getWheelsType());
        return vehicleDetails;
    }*/
}
