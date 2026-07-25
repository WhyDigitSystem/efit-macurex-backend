
package com.efitops.basesetup.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class SecurityConfig {

	@Bean
	TokenAuthenticationFilter tokenAuthenticationFilter() {
		return new TokenAuthenticationFilter();
	}

	@Bean
	RestAuthenticationEntryPoint restAuthenticationEntryPoint() {
		return new RestAuthenticationEntryPoint();
	}

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.cors().and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().csrf()
				.disable().formLogin().disable().httpBasic().disable().exceptionHandling()
				.authenticationEntryPoint(restAuthenticationEntryPoint()).and().authorizeHttpRequests()
				.antMatchers("/", "/error", "/favicon.ico", "/**/*.png", "/**/*.gif", "/**/*.svg", "/**/*.jpg",
						"/**/*.html", "/**/*.css", "/**/*.js")
				.permitAll()
				.antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**", "/configuration/security",
						"/swagger-ui.html", "/swagger-ui/*", "/api/auth/signup", "/api/auth/login", "/api/auth/logout",
						"/api/auth/getRefreshToken", "/api/auth/**", "/api/commonmaster/**", "/api/master/**",
						"/api/user/**", "/images/**", "/api/transaction/**", "/api/GlobalParam/**","/api/documentType/**",
						"/api/costdebitnote/**", "/api/costInvoice/**", "/api/irnCreditNote/**", "/api/efitmaster/**",
						"/api/machinemaster/**", "/api/inwardoutward/**", "/api/customerenquiry/**","/api/customerenquiry",
						"/api/inventory/**", "/api/grn/**", "/api/issuetosubcontractor/**", 
						"/api/quality/**", "/api/purchase/**", "/api/dispatchcontroller/**", "/api/qualityapproval/**",
						"/api/sales/**", "/api/productionPlan/**", "/api/toolmanagement/**","/api/salesVController/**","/api/processDone/**","/api/packingList/**","/api/assembly/**","/api/dailypatrolinspectioncontroller/**"
						,"/api/jobOrder/**","/api/deletestock/**","/api/detailsSubmissionToBank/**","/api/exportpackinglist/**","/api/stockreconcilation/**","/api/NotificationDesignationController/**","/api/ticketcontroller/**","/api/toolmanagement/files/**","/api/screen/**","/api/notificationcontroller/**","/investmentfiles/**","/api/develop/**","/api/develop/**","/api/dev/**")
				.permitAll().antMatchers("/api/**").hasAnyRole("USER", "GUEST_USER").anyRequest().authenticated();
		http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
}
