package com.housingsociety.apartmentservice.controller;

import com.housingsociety.apartmentservice.model.Apartment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@Mapping("/apartments")
public class ApartmentController {
    @Autowired
    private RestTemplate restTemplate;

    public Apartment getApartmentDetails()
    {

    }

    @RequestMapping("/{apartmentId}")
    public Apartment getApartmentDetailsById(@PathVariable("apartmentId") String apartmentId)
    {

    }
}
