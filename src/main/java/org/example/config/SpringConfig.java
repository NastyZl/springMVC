package org.example.config;

import org.example.models.Director;
import org.example.models.Employee;
import org.example.models.enums.Department;
import org.example.models.enums.Post;
import org.example.repository.DirectorRepositoryImpl;
import org.example.repository.EmployeeRepositoryImpl;
import org.example.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@ComponentScan("org.example")
@EnableWebMvc
public class SpringConfig implements WebMvcConfigurer {
    private final ApplicationContext applicationContext;

    @Autowired
    public SpringConfig(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(applicationContext);
        templateResolver.setPrefix("/WEB-INF/views/");
        templateResolver.setSuffix(".html");
        return templateResolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        templateEngine.setEnableSpringELCompiler(true);
        return templateEngine;
    }

    @Bean
    public List<Employee> defaultEmployeeList() {
        List<Employee> employees= new ArrayList<>();
        employees.add(new Employee(1, 1,"IVAN", Post.ANALYST));
        employees.add(new Employee(2, 1,"DASHA",Post.TESTER));
        employees.add(new Employee(3, 1,"SASHA",Post.DEVELOPER));
        employees.add(new Employee(4, "IVAN", Post.ANALYST));
        employees.add(new Employee(5, "DASHA",Post.TESTER));
        employees.add(new Employee(6, "SASHA",Post.DEVELOPER));
        return employees;
    }

    @Bean
    public Repository<Director> directorRepository(List<Employee> defaultEmployeeList) {
        List<Director> directors= new ArrayList<>();
        directors.add(new Director(1, "IVAN", Department.DEVELOPMENT, getEmployeesByDirectorId(defaultEmployeeList, 1)));

        return new DirectorRepositoryImpl(directors);
    }

    private static List<Employee> getEmployeesByDirectorId(List<Employee> defaultEmployeeList, int id) {
        return defaultEmployeeList
                .stream()
                .filter((employee -> employee.getIdDirector() == id)).collect(Collectors.toList());
    }
//    @Bean
//    public EmployeeRepository employeeRepositoryIVAN() {
//        return getEmployeeRepository();
//    }
//    @Bean
//    public EmployeeRepository employeeRepositorySASHA() {
//        List<Employee> employees= new ArrayList<>();
//        employees.add(new Employee(1, "IVAN", Post.ANALYST));
//        employees.add(new Employee(2, "DASHA",Post.TESTER));
//        employees.add(new Employee(3, "SASHA",Post.DEVELOPER));
//        employees.add(new Employee(4, "IVAN", Post.ANALYST));
//        employees.add(new Employee(5, "DASHA",Post.TESTER));
//        employees.add(new Employee(6, "SASHA",Post.DEVELOPER));
//        return new EmployeeRepositoryImpl(employees);
//    }


    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(templateEngine());
        registry.viewResolver(resolver);
    }


}
