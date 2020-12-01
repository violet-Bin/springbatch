package com.bingo.springbatch.itemwriterdb;

import com.bingo.springbatch.itemreaderfromfile.Customer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

/**
 * @author: jiangjiabin
 * @date: 2020/12/1 22:36
 * @description:
 */
@Component("customerUpperProcessor")
public class CustomerUpperProcessor implements ItemProcessor<Customer, Customer> {

    @Override
    public Customer process(Customer item) throws Exception {
        Customer customer = new Customer();
        customer.setId(item.getId());
        customer.setFirstName(item.getFirstName().toUpperCase());
        customer.setLastName(item.getLastName().toUpperCase());
        customer.setBirthday(item.getBirthday());
        return customer;
    }
}
