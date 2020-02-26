package com.sample.board.mysql;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * Description : Board DB Configuration
 * Version : V1.0
 * Author : Demian.khj
 * Create Date : 2020-02-24
 * Update : None
 */
@Configuration
@MapperScan(value="com.sample.board.mysql.mapper.board", sqlSessionFactoryRef = "boardSqlSessionFactory")
public class BoardDBConfiguration {
    public static final String BOARD_DATASOURCE = "boardDS";

    @Bean(name = BOARD_DATASOURCE, destroyMethod = "")
    @ConfigurationProperties(prefix = "board.datasource")
    @Primary
    public DataSource boardDataSource() {
        return new HikariDataSource();
    }

    @Bean(name = "boardSqlSessionFactory")
    @Primary
    public SqlSessionFactory dbSqlSessionFactory(@Qualifier(BOARD_DATASOURCE) DataSource boardDataSource, ApplicationContext applicationContext) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(boardDataSource);
        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:mybatis/mapper/board/*.xml"));
        sqlSessionFactoryBean.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);
        return sqlSessionFactoryBean.getObject();
    }
}
