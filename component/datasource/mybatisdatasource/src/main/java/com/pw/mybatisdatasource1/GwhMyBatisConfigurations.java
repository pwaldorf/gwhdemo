package com.pw.mybatisdatasource1;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.pw.mybatisroutetemplatesloader1.mybatis.mapper")
@ConditionalOnProperty(prefix = "gwh.mybatis.db.datasource", name = "className", matchIfMissing = false)
public class GwhMyBatisConfigurations {

    private GwhMyBatisDataSourceProperties gwhMyBatisDataSourceProperties;

    private GwhMyBatisDataSourceConfiguration gwhMyBatisDataSourceConfiguration;

    public GwhMyBatisConfigurations(GwhMyBatisDataSourceProperties gwhJpaDataSourceProperties,
                                    GwhMyBatisDataSourceConfiguration gwhMyBatisDataSourceConfiguration) {
        this.gwhMyBatisDataSourceProperties = gwhJpaDataSourceProperties;
        this.gwhMyBatisDataSourceConfiguration = gwhMyBatisDataSourceConfiguration;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(gwhMyBatisDataSourceConfiguration.getDataSource());
        return factoryBean.getObject();
    }

//    @Bean(name = "gwhTransactionManager")
//    public PlatformTransactionManager transactionManager() {
//        JpaTransactionManager transactionManager = new JpaTransactionManager();
//        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
//        return transactionManager;
//    }

}
