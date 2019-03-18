package com.example.AutoservicePortal.Repository;

import com.example.AutoservicePortal.domain.Service;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ServiceRepo extends CrudRepository<Service, Integer> {
    List<Service> findByIdNot(Integer id);
}
