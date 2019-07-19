package kr.or.connect.guestbook.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import kr.or.connect.guestbook.config.DBConfig;

@Configuration	//Config라는 것을 알려주는 어노테이션 : 그래야지 실행이 되면서 여기에 있는 설정들에 대한 정보를 읽어들임.
@ComponentScan(basePackages = {"kr.or.connect.guestbook.dao", "kr.or.connect.guestbook.service"})	// dao나 service에 구현되어 있는 컴포넌트들을 읽어온다.
@Import({DBConfig.class})	//설정파일을 여러개로 나눠서 작성할 수 있다. DBconfig는 따로 보관하겠다는 뜻
public class ApplicationConfig {

}
