package com.bingo.springbatch.restartreader;

import com.bingo.springbatch.itemreaderfromfile.Customer;
import org.springframework.batch.item.*;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

/**
 * @author: jiangjiabin
 * @description:
 */
@Component("restartReader")
public class RestartReader implements ItemStreamReader<Customer> {

    private FlatFileItemReader<Customer> customerFlatFileItemReader = new FlatFileItemReader<>();
    private int currentLocation;
    private boolean reStart = false;
    private ExecutionContext executionContext;

    private static final String CURRENT_LOCATIO = "current.location";

    public RestartReader() {
        customerFlatFileItemReader.setResource(new ClassPathResource("customer.txt"));
        customerFlatFileItemReader.setLinesToSkip(1);//跳过第一行

        //解析数据
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setNames(new String[]{"id", "firstName", "lastName", "birthday"});
        //将解析出的数据映射为对象
        DefaultLineMapper<Customer> mapper = new DefaultLineMapper<>();
        mapper.setLineTokenizer(tokenizer);
        mapper.setFieldSetMapper(fieldSet -> {
            Customer customer = new Customer();
            customer.setId(fieldSet.readLong("id"));
            customer.setFirstName(fieldSet.readString("firstName"));
            customer.setLastName(fieldSet.readString("lastName"));
            customer.setBirthday(fieldSet.readString("birthday"));
            return customer;
        });

        mapper.afterPropertiesSet();
        customerFlatFileItemReader.setLineMapper(mapper);
    }

    //读数据
    @Override
    public Customer read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        Customer customer;
        currentLocation++;
        if (reStart) {
            customerFlatFileItemReader.setLinesToSkip(this.currentLocation);
            reStart = false;
            System.out.println("currentLocation:  " + currentLocation);
        }

        customerFlatFileItemReader.open(this.executionContext);
        customer = customerFlatFileItemReader.read();

//        if (customer != null && customer.getId() == 7) {
//            throw new RuntimeException("wrong! custom id is: " + customer.getId());
//        }
        return customer;
    }

    /**
     * 根据executionContext打开需要读取资源的stream，
     * 可以根据持久化在执行上下文中的数据重新定位需要读取记录的位置。
     * step之前执行
     *
     * @param executionContext
     * @throws ItemStreamException
     */
    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        System.out.println("open--------");
        this.executionContext = executionContext;
        if (executionContext.containsKey(CURRENT_LOCATIO)) {
            currentLocation = executionContext.getInt(CURRENT_LOCATIO);
            reStart = true;
        } else {
            currentLocation = 0;
            executionContext.put(CURRENT_LOCATIO, currentLocation);
            System.out.println("start read line: " + (currentLocation + 1));
        }
    }

    /**
     * 需要持久化的数据存放在执行上下文executionContext中
     * 在commit时会通过JobRepository持久化到数据库中。
     * 一批任务成功执行之后才执行update
     *
     * @param executionContext
     * @throws ItemStreamException
     */
    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {
        System.out.println("update--------");
        executionContext.put(CURRENT_LOCATIO, currentLocation);
    }

    /**
     * 关闭读取资源
     *
     * @throws ItemStreamException
     */
    @Override
    public void close() throws ItemStreamException {
        System.out.println("close----------");

    }
}
