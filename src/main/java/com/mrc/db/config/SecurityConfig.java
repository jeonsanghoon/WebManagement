/**
 * 
 */
package com.mrc.db.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.mrc.db.config.hander.AuthenticationFailureHandlerImpl;
import com.mrc.db.config.hander.AuthenticationSuccessHandlerImpl;

import lombok.AllArgsConstructor;

/**
 * @author jsh
 *
 */
@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private MemberService memberService;
	@Autowired
	AuthProvider authProvider;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		// static 디렉터리의 하위 파일 목록은 인증 무시 ( = 항상통과 )
		web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/lib/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable(); // 개발 시 에만

		http.authorizeRequests().antMatchers("/user/**").permitAll() // 사용자 페이지
				.antMatchers("/admin/**").access("ROLE_ADMIN") // 관리자 페이지
				.antMatchers("/login", "/upload", "/download/**" , "/templeate/**").permitAll()
				.antMatchers("/temp/**").permitAll()
				.antMatchers("/member/**").permitAll()
				.antMatchers("/files/**").permitAll()
				.antMatchers("/register/**").permitAll()
				.antMatchers("/banner/**").permitAll()
				.antMatchers("/keyword/**").permitAll()
				.antMatchers(HttpMethod.POST, "/temp/**").permitAll()
				.antMatchers(HttpMethod.POST, "/register/**").permitAll()
				.antMatchers(HttpMethod.POST, "/banner/**").permitAll()
				.antMatchers(HttpMethod.POST, "/kakaoapi/**").permitAll()
				.antMatchers(HttpMethod.POST, "/userbanner/**").permitAll()
			
				.antMatchers(HttpMethod.POST, "/**").permitAll()
				.antMatchers(HttpMethod.POST, "/member/**").permitAll()
				.antMatchers(HttpMethod.POST, "/keyword/**").permitAll()
				.antMatchers(HttpMethod.POST, "/menu/**").permitAll()
				.antMatchers(HttpMethod.POST, "/file/**").permitAll()
				.antMatchers(HttpMethod.POST, "/common/**").permitAll()
				.antMatchers(HttpMethod.POST, "/logout").permitAll()
				.antMatchers(HttpMethod.POST, "/mongodb/**").permitAll()
				// .antMatchers("/**").permitAll();
				// .antMatchers("/**").authenticated()
				.antMatchers("/error").permitAll().antMatchers("/").permitAll();

		http.formLogin().loginPage("/member/login").permitAll()

				.defaultSuccessUrl("/").failureUrl("/member/login?loginfail=true")
				.successHandler(new AuthenticationSuccessHandlerImpl())
				.failureHandler(new AuthenticationFailureHandlerImpl()).usernameParameter("member_id")
				.passwordParameter("member_pw").and()

				.rememberMe()
				//.key("uniqueAndSecret").tokenValiditySeconds(60 * 60 * 24 * 14)
				//.rememberMeParameter("remember-me").rememberMeCookieName("remember-me")
				//.tokenRepository(persistentTokenRepository());
		         .rememberMeServices(persistentTokenBasedRememberMeServices()); // 기본
		// 토큰
		// 유효시간
		http.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.deleteCookies("JSESSIONID", "remember-me").logoutSuccessUrl("/member/login?rtn=logout")

				.invalidateHttpSession(true).permitAll().and();

		http.authenticationProvider(authProvider);
	
	}

	@Bean
	public PersistentTokenBasedRememberMeServices persistentTokenBasedRememberMeServices() {
		PersistentTokenBasedRememberMeServices persistenceTokenBasedservice = new PersistentTokenBasedRememberMeServices(
				"uniqueAndSecret", memberService, persistentTokenRepository());
		persistenceTokenBasedservice.setParameter("remember-me");
		persistenceTokenBasedservice.setAlwaysRemember(true);
		persistenceTokenBasedservice.setCookieName("remember-me");
		persistenceTokenBasedservice.setTokenValiditySeconds(60 * 60 * 24 * 14); // 토큰 유효시간 2주일 설정
		return persistenceTokenBasedservice;
	}

	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		RememberMeTokenRepository tokenRepositoryImpl = new RememberMeTokenRepository();
		return tokenRepositoryImpl;
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(memberService).passwordEncoder(passwordEncoder());

	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("http://localhost:8080","http://localhost:3000"));
		configuration.setAllowedMethods(Arrays.asList("GET","POST","HEAD","OPTIONS","PUT","PATCH","DELETE"));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
}
