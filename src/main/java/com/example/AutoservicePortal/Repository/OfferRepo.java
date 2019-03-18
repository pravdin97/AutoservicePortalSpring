package com.example.AutoservicePortal.Repository;

import com.example.AutoservicePortal.domain.Autoservice;
import com.example.AutoservicePortal.domain.Offer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OfferRepo extends CrudRepository<Offer, Integer> {

    List<Offer> findByAutoservice(Autoservice autoservice);
}
