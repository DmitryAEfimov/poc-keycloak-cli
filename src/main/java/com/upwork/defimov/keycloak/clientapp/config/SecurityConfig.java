package com.upwork.defimov.keycloak.clientapp.config;

import java.util.stream.Stream;

import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

import com.upwork.defimov.keycloak.clientapp.model.AccountType;

@Configuration
@EnableWebSecurity
class SecurityConfig extends KeycloakWebSecurityConfigurerAdapter {

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		KeycloakAuthenticationProvider keycloakAuthenticationProvider = keycloakAuthenticationProvider();

		keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(new SimpleAuthorityMapper());
		auth.authenticationProvider(keycloakAuthenticationProvider);
	}

	@Bean
	public KeycloakSpringBootConfigResolver KeycloakConfigResolver() {
		return new KeycloakSpringBootConfigResolver();
	}

	@Bean
	@Override
	protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
		return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
	}

	@Bean
	public AccessDeniedHandler getAccessDeniedHundler() {
		return (request, response, accessDeniedException) -> response.sendRedirect(request.getContextPath() + "/403");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		super.configure(http);
		ExpressionUrlAuthorizationConfigurer<?>.ExpressionInterceptUrlRegistry authConfig = http.authorizeRequests()
				.antMatchers("/", "/poc").hasRole("regular");
		Stream.of(AccountType.values()).forEach(
				accountType -> authConfig.antMatchers("/poc/access/" + accountType).hasRole(accountType.name()));
		authConfig.anyRequest().permitAll();

		http.cors().and().csrf().disable();
		http.exceptionHandling().accessDeniedHandler(getAccessDeniedHundler());
	}

}
