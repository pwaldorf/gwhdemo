package com.pw.gwhdatasource1.configuration;

import com.pw.gwhdatasource1.GwhDataSourceConfiguration;
import com.pw.gwhdatasource1.GwhDataSourceProperties;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(value = "gwh.framework.load.routetemplates.mybatis1.enabled", havingValue = "true", matchIfMissing = false)
public class GwhMyBatisConfigurations {

    private final GwhDataSourceProperties gwhDataSourceProperties;

    private final GwhDataSourceConfiguration gwhDataSourceConfiguration;

    public GwhMyBatisConfigurations(GwhDataSourceProperties gwhDataSourceProperties,
                                    GwhDataSourceConfiguration gwhDataSourceConfiguration) {
        this.gwhDataSourceProperties = gwhDataSourceProperties;
        this.gwhDataSourceConfiguration = gwhDataSourceConfiguration;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(gwhDataSourceConfiguration.getDataSource());
        return factoryBean.getObject();
    }

//    @Bean(name = "gwhTransactionManager")
//    public PlatformTransactionManager transactionManager() {
//        JpaTransactionManager transactionManager = new JpaTransactionManager();
//        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
//        return transactionManager;
//    }

}
