package com.example.myapplication;

public class Tutor {
    private String name,institution,classes,subject,salary,contact;

    public Tutor()
    {

    }

    public Tutor(String name, String institution, String classes, String subject, String salary, String contact) {
        this.name = name;
        this.institution = institution;
        this.classes = classes;
        this.subject = subject;
        this.salary = salary;
        this.contact = contact;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
