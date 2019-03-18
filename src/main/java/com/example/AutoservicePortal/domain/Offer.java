package com.example.AutoservicePortal.domain;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Offer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    private String text;

    private Integer cost;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "service_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Service service;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "autoservice_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Autoservice autoservice;

    public Offer() {
    }

    public Offer(String text, Integer cost, Service service, Autoservice autoservice) {
        this.text = text;
        this.cost = cost;
        this.service = service;
        this.autoservice = autoservice;
    }

    public String getServiceName() {
        return service.getName();
    }

    public String getAutoserviceName() {
        return autoservice.getName();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Autoservice getAutoservice() {
        return autoservice;
    }

    public void setAutoservice(Autoservice autoservice) {
        this.autoservice = autoservice;
    }
}
