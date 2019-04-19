package com.example.AutoservicePortal.Repository;

import com.example.AutoservicePortal.domain.Offer;
import com.example.AutoservicePortal.domain.Request;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RequestRepo extends CrudRepository<Request, Integer> {
    List<Request> findByOffer(Offer offer);
}
