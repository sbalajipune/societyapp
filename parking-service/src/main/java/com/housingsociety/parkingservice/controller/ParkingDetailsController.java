package com.housingsociety.parkingservice.controller;

import com.housingsociety.parkingservice.dao.ParkingDAO;
import com.housingsociety.parkingservice.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/parkingdetails")
public class ParkingDetailsController {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Environment env;

    private String memberServiceURL;
    private String vehicleServiceURL;
    private ParkingDAO parkingDAO;

    private static final Logger LOG = LoggerFactory.getLogger(ParkingDetailsController.class);

    @PostConstruct
    public void init() {
        memberServiceURL = "http://" + env.getProperty("member-service", "member-service") + ":8080/";
        vehicleServiceURL = "http://" + env.getProperty("vehicles-service", "vehicles-service") + ":8080/";
        parkingDAO = new ParkingDAO();
    }

    @RequestMapping("/parkingId/{parkingId}")
    public ParkingDetails getParkingDetailsByParkingId(@PathVariable("parkingId") String parkingId)
    {
        Parking parking = parkingDAO.getParkingDetailsByParkingId(parkingId);
        LOG.info("Invoking member-service");
        Member member = restTemplate.getForObject(memberServiceURL + "members/member/" + parking.getOwnerId(),  Member.class);
        LOG.info("Invoking vehicle-service");
        List<Vehicle> vehicleList = new ArrayList<Vehicle>();
        for (String registrationId : parking.getVehicles().split(",")) {
            Vehicle vehicle = restTemplate.getForObject(vehicleServiceURL + "vehicledetails/registrationId/" + registrationId, Vehicle.class);
            vehicleList.add(vehicle);
        }
        ParkingDetails parkingDetails = new ParkingDetails(parkingId, parking.getApartmentId(), member, parking.getLevel(), vehicleList);
        return parkingDetails;
    }

    @RequestMapping("/ownerId/{ownerId}")
    public List<ParkingDetails> getParkingDetailsByOwnerId(@PathVariable("ownerId") String ownerId)
    {
        List<Parking> parkings = parkingDAO.getParkingDetailsByOwnerId(ownerId);
        return constructParkingDetailsList(parkings);
    }

    @RequestMapping("/apartmentId/{apartmentId}")
    public List<ParkingDetails> getParkingDetailsByApartmentId(@PathVariable("apartmentId") String apartmentId)
    {
        List<Parking> parkings = parkingDAO.getParkingDetailsByApartmentId(apartmentId);
        return constructParkingDetailsList(parkings);
    }

    private List<ParkingDetails> constructParkingDetailsList(List<Parking> parkings)
    {
        List<ParkingDetails> parkingsDetailsList = new ArrayList<ParkingDetails>();
        for (Parking parking: parkings)
        {
            LOG.info("Invoking member-service");
            Member member = restTemplate.getForObject(memberServiceURL + "members/member/" + parking.getOwnerId(),  Member.class);
            LOG.info("Invoking vehicle-service");
            List<Vehicle> vehicleList = new ArrayList<Vehicle>();
            for (String registrationId : parking.getVehicles().split(",")) {
                Vehicle vehicle = restTemplate.getForObject(vehicleServiceURL + "vehicledetails/registrationId/" + registrationId, Vehicle.class);
                vehicleList.add(vehicle);
            }
            ParkingDetails parkingDetails = new ParkingDetails(parking.getParkingId(), parking.getApartmentId(), member, parking.getLevel(), vehicleList);
            parkingsDetailsList.add(parkingDetails);
        }
        return parkingsDetailsList;
    }
}
