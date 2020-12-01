package com.bingo.springbatch.itemreaderfromdb;

import lombok.Data;
import lombok.ToString;

/**
 * @author: jiangjiabin
 * @description:
 */
@Data
@ToString
public class User {

    private Integer id;
    private String username;
    private String password;
    private Integer age;
}
