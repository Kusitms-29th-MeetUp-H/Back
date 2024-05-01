//package Backend.socket.infra.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@EnableWebMvc
//@Configuration
//public class WebConfig implements WebMvcConfigurer {
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOrigins("*")
//                .allowedMethods("*")
//                .exposedHeaders("Access-Control-Allow-Origin",
//                        "Access-Control-Allow-Methods",
//                        "Access-Control-Allow-Headers",
//                        "Access-Control-Max-Age",
//                        "Access-Control-Request-Headers",
//                        "Access-Control-Request-Method")
//                .allowCredentials(false)
//                .maxAge(30000000);
//    }
//}
