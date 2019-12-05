package com.housingsociety.apartmentservice.model;

import java.io.Serializable;
import java.util.Objects;

public class Member implements Serializable {
    private String memberId;
    private char gender;
    private String memberName;
    private int age;
    private String profession;

    public Member(){

    }

    public Member(String memberId, char gender, String memberName, int age, String profession) {
        this.memberId = memberId;
        this.gender = gender;
        this.memberName = memberName;
        this.age = age;
        this.profession = profession;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Member)) return false;
        Member member = (Member) o;
        return gender == member.gender &&
                age == member.age &&
                memberId.equals(member.memberId) &&
                Objects.equals(memberName, member.memberName) &&
                Objects.equals(profession, member.profession);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberId, gender, memberName, age, profession);
    }
}
