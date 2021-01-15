package com.bank.app.domain.mapper;

import com.bank.app.domain.dto.CustomerView;
import com.bank.app.domain.model.Customer;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;

import java.util.List;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

/**
 * @author Mathan Raj O
 * @version 1.0
 * @since 13/01/2021
 */
@Mapper(componentModel = "spring")
public interface CustomerViewMapper {

    public CustomerView toCustView(Customer customer);

    @BeanMapping(nullValueCheckStrategy = ALWAYS, nullValuePropertyMappingStrategy = IGNORE)
    public List<CustomerView> toCustView(List<Customer> customer);
}
