package com.PhysicalTrack.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.PhysicalTrack.interceptor.AuthenticationInterceptor;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

	private final AuthenticationInterceptor authenticationInterceptor;
	
	// Add Interceptor
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		// + Authentication Interceptor
		registry.addInterceptor(authenticationInterceptor)
				.addPathPatterns("/api/**")
	            .excludePathPatterns("/api/user/**");
	}
	
    // Add CORS Configuration
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
        		.allowedOrigins(
        				"http://localhost:3000",
        				"https://physical-t-7jce.vercel.app"
        		)  // 허용 도메인
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
        		.allowCredentials(true);
    }
}
