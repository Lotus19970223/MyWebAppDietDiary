package com.dietdiary.controller;

import org.springframework.data.repository.CrudRepository;

import com.dietdiary.entity.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
}
