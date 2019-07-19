package kr.or.connect.guestbook.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

//DispatcherServlet이 실행될 때 읽어들이는 설정파일

@Configuration	//Config라는 것을 알려줌
@EnableWebMvc
@ComponentScan(basePackages = { "kr.or.connect.guestbook.controller" })	//controller 알아서 읽어옴
public class WebMvcContextConfiguration extends WebMvcConfigurerAdapter {	//내가 원하는 설정들을 하기 위해서 상속받음
	// 실제로 url요청이  /css/ 이렇게 들어오면 /css/ 파일에서 찾으라는 코드
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/css/**").addResourceLocations("/css/").setCachePeriod(31556926);
		registry.addResourceHandler("/img/**").addResourceLocations("/img/").setCachePeriod(31556926);
		registry.addResourceHandler("/js/**").addResourceLocations("/js/").setCachePeriod(31556926);
	}

	// default servlet handler를 사용하게 합니다.
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}	// 매핑정보가 없는 url요청이 들어왔을 때 default servlet 사용

	// 특정 url에 대한 처리를 컨트롤러 클래스를 작성하지 않고 매핑 할 수 있도록 해줌
	@Override
	public void addViewControllers(final ViewControllerRegistry registry) {
		System.out.println("addViewControllers가 호출됩니다. ");
		registry.addViewController("/").setViewName("index");
	}

	@Bean
	public InternalResourceViewResolver getInternalResourceViewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		return resolver;
	}
}
