package com.housingsociety.apartmentservice.controller;

import com.housingsociety.apartmentservice.dao.ApartmentDAO;
import com.housingsociety.apartmentservice.model.Apartment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
@RequestMapping("/apartments")
public class ApartmentController {
    @Autowired
    private RestTemplate restTemplate;

    private ApartmentDAO apartmentDAO;

    @PostConstruct
    public void init() {
        apartmentDAO = new ApartmentDAO();
    }

    @RequestMapping("/")
    public List<Apartment> getApartmentDetails()
    {
        return apartmentDAO.getApartmentDetails();
    }

    @RequestMapping("/apartmentId/{apartmentId}")
    public Apartment getApartmentDetailsById(@PathVariable("apartmentId") String apartmentId)
    {
        return apartmentDAO.getApartmentById(apartmentId);
    }

    @RequestMapping("/ownerId/{ownerId}")
    public List<Apartment> getApartmentDetails(@PathVariable("ownerId") String ownerId)
    {
        return apartmentDAO.getApartmentsByOwnerId(ownerId);
    }

}
