package com.bingo.springbatch.itemreaderfromdb;

import com.google.common.collect.Maps;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Map;

/**
 * @author: jiangjiabin
 * @description: 从数据库中读取数据  使用 JdbcPagingItemReader
 */
//@Configuration
public class ItemReaderDB {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    @Qualifier("dbwriter")
    private ItemWriter<User> dbwriter;

    @Autowired
    private DataSource dataSource;

    @Bean
    public Job itemReaderDBJob() {
        return jobBuilderFactory.get("itemReaderDBJob")
                .start(itemReaderDBStep())//执行step.
                .build();
    }

    @Bean
    public Step itemReaderDBStep() {
        return stepBuilderFactory.get("itemReaderDBStep")
                .<User, User>chunk(2)
                .reader(dbReader())
                .writer(dbwriter)
                .build();
    }

    @Bean
    public JdbcPagingItemReader<? extends User> dbReader() {
        JdbcPagingItemReader<User> reader = new JdbcPagingItemReader<>();
        reader.setDataSource(dataSource);
        reader.setFetchSize(2);
        //把读取到的数据转换成User对象
        reader.setRowMapper((resultSet, rowNum) -> {
            User user = new User();
            user.setId(resultSet.getInt(1));
            user.setUsername(resultSet.getString(2));
            user.setPassword(resultSet.getString(3));
            user.setAge(resultSet.getInt(4));
            return user;
        });
        //指定sql语句
        MySqlPagingQueryProvider provider = new MySqlPagingQueryProvider();
        provider.setSelectClause("id, username, password, age");
        provider.setFromClause("from user");

        //指定根据哪个字段排序
        Map<String, Order> sort = Maps.newHashMap();
        sort.put("id", Order.ASCENDING);
        provider.setSortKeys(sort);

        reader.setQueryProvider(provider);
        return reader;
    }
}
