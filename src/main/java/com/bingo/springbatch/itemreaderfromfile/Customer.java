package com.bingo.springbatch.itemreaderfromfile;

import lombok.Data;
import lombok.ToString;

/**
 * @author: jiangjiabin
 * @description:
 */
@Data
@ToString
public class Customer {

    private Long id;
    private String firstName;
    private String lastName;
    private String birthday;
}
