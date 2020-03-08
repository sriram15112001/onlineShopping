package com.niit.DB;
import java.util.Properties;
import javax.sql.DataSource;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.niit.model.Category;
import com.niit.model.Product;
import com.niit.model.Supplier;
import com.niit.model.User;

@ComponentScan("com.niit")
@Configuration
@EnableTransactionManagement
public class DBConfig {

@Bean(name="DataSource")
public DataSource getH2DataSource()
{
DriverManagerDataSource dataSource=new DriverManagerDataSource();
dataSource.setDriverClassName("org.h2.Driver");
dataSource.setUrl("jdbc:h2:tcp://localhost/~/test");
dataSource.setUsername("sa");
dataSource.setPassword("");
System.out.println("Data Source");
return dataSource;
}

@Bean(name="sessionFactory")
public SessionFactory getSessionFactory()
{
Properties hibernateProperties=new Properties();
hibernateProperties.put("hibernate.dialect","org.hibernate.dialect.H2Dialect");
hibernateProperties.setProperty("hibernate.hbm2ddl.auto","update");
LocalSessionFactoryBuilder localSessionFactory=new LocalSessionFactoryBuilder(getH2DataSource());
localSessionFactory.addProperties(hibernateProperties);
localSessionFactory.addAnnotatedClass(Category.class);
localSessionFactory.addAnnotatedClass(Product.class);
localSessionFactory.addAnnotatedClass(Supplier.class);
localSessionFactory.addAnnotatedClass(User.class);

SessionFactory sessionFactory=localSessionFactory.buildSessionFactory();
System.out.println("Session Factory");
return sessionFactory;



}
@Bean(name="txManager")
public HibernateTransactionManager getHibernateTransactionManager(SessionFactory sessionFactory)
{
System.out.println("Transaction Manager");
return new HibernateTransactionManager(sessionFactory);
}
}
