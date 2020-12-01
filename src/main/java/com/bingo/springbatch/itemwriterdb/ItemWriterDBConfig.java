package com.bingo.springbatch.itemwriterdb;

import com.bingo.springbatch.itemreaderfromfile.Customer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author: jiangjiabin
 * @description: write data to db
 */
@Configuration
public class ItemWriterDBConfig {

    @Autowired
    private DataSource dataSource;

    @Bean
    public JdbcBatchItemWriter<Customer> itemWriterDB() {
        JdbcBatchItemWriter<Customer> writer = new JdbcBatchItemWriter<>();
        writer.setDataSource(dataSource);
        writer.setSql("insert into customer (id, first_name, last_name, birthday) " +
                "values (:id, :firstName, :lastName, :birthday)");
        //将对象属性的值替换到占位符
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        return writer;
    }


}
