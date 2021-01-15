package com.bank.app.domain.mapper;

import com.bank.app.domain.dto.CustomerRequest;
import com.bank.app.domain.model.Customer;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

/**
 * @author Mathan Raj O
 * @version 1.0
 * @since 13/01/2021
 */
@Mapper(componentModel = "spring")
public interface CustomerMapper {
    @BeanMapping(nullValueCheckStrategy = ALWAYS, nullValuePropertyMappingStrategy = IGNORE)
    public Customer addCustomer(CustomerRequest customerRequest);

    @BeanMapping(nullValueCheckStrategy = ALWAYS, nullValuePropertyMappingStrategy = IGNORE)
    public void updateCustomer(CustomerRequest customerRequest, @MappingTarget Customer customer);
}
