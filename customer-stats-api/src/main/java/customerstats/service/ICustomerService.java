package customerstats.service;

import org.springframework.data.jpa.repository.JpaRepository;

import customerstats.model.Customer;

public interface ICustomerService extends JpaRepository<Customer, Integer>{

}
