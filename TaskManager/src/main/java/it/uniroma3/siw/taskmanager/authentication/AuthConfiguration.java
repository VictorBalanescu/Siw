package it.uniroma3.siw.taskmanager.authentication;

import static it.uniroma3.siw.taskmanager.model.Credentials.ADMIN_ROLE;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * The AuthConfiguration is a Spring Security Configuration.
 * It extends WebSecurityConfigurerAdapter, meaning that it provides the settings for Web security.
 */
@Configuration
@EnableWebSecurity
public class AuthConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	 DataSource dataSource;
	
	public void configure(HttpSecurity http) throws Exception {
		http
			//authorise pharagraf
			.authorizeRequests()
			//anyone
			.antMatchers(HttpMethod.GET,"/","/index","/register","/registrazioneSuccessful","/home",
					"/myProjects","/userProfile","/login","/condivisiConME","/newProject",
					"/modificaDati","/modificaSuccessful","/project","/newTag","/css/**").permitAll()
			.antMatchers(HttpMethod.POST,"/login","/register","/newProject","/modificaDati","/delete/project/",
					"/newTag").permitAll()
			// only authenticated users with ADMIN authority can access the admin pag
            .antMatchers(HttpMethod.GET, "/admin").hasAnyAuthority(ADMIN_ROLE)
			.anyRequest().authenticated()
			//.anyRequest().permitAll()
			.and().formLogin()
			.loginPage("/login")
			.defaultSuccessUrl("/home")
			.and().logout()
			.logoutUrl("/logout")
			.logoutSuccessUrl("/index")
			.invalidateHttpSession(true)
			.clearAuthentication(true).permitAll();
	}
	public void configure(AuthenticationManagerBuilder Auth)throws Exception{
		Auth.jdbcAuthentication()
		.dataSource(this.dataSource)
		.authoritiesByUsernameQuery("SELECT username, role FROM credentials WHERE username = ?")
		.usersByUsernameQuery("SELECT username, password, 1 as enabled FROM credentials WHERE username=?");
	}
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}