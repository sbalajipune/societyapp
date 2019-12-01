package com.housingsociety.memberservice.controller;

import com.housingsociety.memberservice.model.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/members")
public class MemberController {
    @Autowired
    private RestTemplate restTemplate;

    public List<Member> getMemberDetails()
    {
        List<Member> members = new ArrayList<Member>();
        members.add(getTestMember());
        return members;
    }

    @RequestMapping("/member/{memberId}")
    public Member getMemberDetailsById(@PathVariable("memberId") String apartmentId)
    {
        return getTestMember();
    }

    @RequestMapping("/{memberFirstName}/{memberLastName}")
    public Member getMemberDetailsByName(@PathVariable("memberFirstName") String memberFirstName, @PathVariable("memberLastName") String memberLastName)
    {
        return getTestMember();
    }

    @RequestMapping("/apartment/{apartmentId}")
    public Member getMemberDetailsByName(@PathVariable("apartmentId") String apartmentId)
    {
        return getTestMember();
    }
    private Member getTestMember()
    {
        Member member = new Member("B-1002-1", 'M', "Balaji", "Londhe",35, "Engineer");
        return member;
    }
}
