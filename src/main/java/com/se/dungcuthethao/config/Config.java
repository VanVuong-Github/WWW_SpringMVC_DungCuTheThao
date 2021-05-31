package com.se.dungcuthethao.config;

import java.util.Properties;

import org.hibernate.SessionFactory;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "com.se.dungcuthethao" })
@PropertySource("classpath:application.properties")
@EnableTransactionManagement
@EnableAutoConfiguration(exclude = { HibernateJpaAutoConfiguration.class })
public class Config implements WebMvcConfigurer {
	private static final String DATABASE_URL = "spring.datasource.url";
	private static final String DATABASE_DRIVER = "spring.datasource.driver-class-name";
	private static final String DATABASE_PASSWORD = "spring.datasource.password";
	private static final String DATABASE_USERNAME = "spring.datasource.username";

	private static final String HIBERNATE_DIALECT = "spring.jpa.database-platform";
	private static final String HIBERNATE_SHOW_SQL = "spring.jpa.show-sql";
	private static final String HIBERNATE_FORMAT_SQL = "spring.jpa.properties.hibernate.format_sql";
	private static final String HIBERNATE_HBM2DDL_AUTO = "spring.jpa.hibernate.ddl-auto";
//	private static final String HIBERNATE_NAMING_PHYSICAL_STRATEGY = "spring.jpa.hibernate.naming.physical-strategy";

	@Autowired
	private Environment environment;
	
	@Autowired
	private ApplicationContext applicationContext;

	@Bean
	public InternalResourceViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}

	@Bean
	public DriverManagerDataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setUrl(environment.getRequiredProperty(DATABASE_URL));
		dataSource.setUsername(environment.getRequiredProperty(DATABASE_USERNAME));
		dataSource.setPassword(environment.getRequiredProperty(DATABASE_PASSWORD));
		dataSource.setDriverClassName(environment.getRequiredProperty(DATABASE_DRIVER));
		return dataSource;
	}

	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource());
		sessionFactory.setPackagesToScan("com.se.dungcuthethao");
		sessionFactory.setHibernateProperties(hibernateProperties());
		return sessionFactory;
	}

	@Bean
	@Autowired
	public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(sessionFactory);
		return transactionManager;
	}

	@Bean
	public Properties hibernateProperties() {
		Properties properties = new Properties();
		properties.setProperty("hibernate.dialect", environment.getProperty(HIBERNATE_DIALECT));
		properties.setProperty("hibernate.format_sql", environment.getProperty(HIBERNATE_FORMAT_SQL));
		properties.setProperty("hibernate.show_sql", environment.getProperty(HIBERNATE_SHOW_SQL));
		properties.setProperty("hibernate.hbm2ddl.auto", environment.getProperty(HIBERNATE_HBM2DDL_AUTO));
		return properties;
	}

	@Bean
	public JpaTransactionManager transactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(sessionFactory().getObject());

		return transactionManager;
	}

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);

		return modelMapper;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/src/**").addResourceLocations("/src/");
	}
	
	@Bean
	public ObjectMapper objectMapper() {
		return Jackson2ObjectMapperBuilder.json().applicationContext(applicationContext).build();
	}

	@Bean
	public JavaMailSender getMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

		mailSender.setHost(environment.getProperty("spring.mail.host"));
		mailSender.setPort(Integer.valueOf(environment.getProperty("spring.mail.port")));
		mailSender.setUsername(environment.getProperty("spring.mail.username"));
		mailSender.setPassword(environment.getProperty("spring.mail.password"));

		Properties javaMailProperties = new Properties();
		javaMailProperties.put("mail.smtp.starttls.enable",
				environment.getProperty("spring.mail.properties.mail.smtp.starttls.enable"));
		javaMailProperties.put("mail.smtp.auth", environment.getProperty("spring.mail.properties.mail.smtp.auth"));
		javaMailProperties.put("mail.transport.protocol",
				environment.getProperty("spring.mail.properties.mail.transport.protocol"));
		javaMailProperties.put("mail.debug", environment.getProperty("spring.mail.properties.mail.debug"));

		mailSender.setJavaMailProperties(javaMailProperties);
		return mailSender;
	}
}
