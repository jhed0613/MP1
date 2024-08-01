package com.example.mp.configuration;

//import com.example.mp.security.CustomAuthenticationSuccessHandler;
import com.example.mp.security.CustomAuthenticationSuccessHandler;
import com.example.mp.security.JwtRequestFilter;
import com.example.mp.service.CustomUserDetailService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Autowired
    private CustomAuthenticationSuccessHandler successHandler;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;
    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((auth) -> auth
                                .requestMatchers( "/","/home","/login", "/join", "/joinProc")
                                .permitAll()
                        // ,"/jpa/board/**", "/kospi/list","/kosdak/list","/stocks/**")
                        // 앞의 주소 5개는 권한을 허용하겠다 ...
                                .requestMatchers("/admin").hasRole("ADMIN")  // ADMIN 이라는 역할 (role)이 있는 애만 허용 ...
                                .requestMatchers("/jpa/**").hasAnyRole("ADMIN", "USER")
                                .requestMatchers("/api/**").hasAnyRole("ADMIN", "USER")
                                .requestMatchers("/stocks/**").hasAnyRole("ADMIN", "USER")
                                .anyRequest().authenticated()  // 그 외 나머지는 인증 되어야 한다.
//                        .requestMatchers("/stocks/**").hasAnyRole("ADMIN", "USER")
//                        .requestMatchers("/stocks/kospi/**","/stocks/kosdak/**").hasAnyRole("ADMIN", "USER")
                        // board , api 밑의 주소들은 ADMIN or USER 의 권한이 있는 사람만 허용하겠다 ...
                );
        http
                .formLogin((auth) -> auth
                                .loginPage("/login")      // login 페이지로 /login 을 쓸거다.
                                .loginProcessingUrl("/loginProc")   // ProcessingUrl 은 loginProc 을 쓸 것이다.
                                .permitAll()  // 접근을 허용할 것이다.
        // TODO. 얘는 로그인 성공시 토큰 반환 Proc을 컨트롤러로 바꿨기 때문에 주석처리하고 컨트롤러에서 다시 불러와서 토큰을 생성했음 ( 다시 변경 )
                                .successHandler(successHandler)
////                        .defaultSuccessUrl("/home")
                );
        http
                .csrf((auth) -> auth.disable());	// 기본적으로 CSRF 토큰을 이용해서 요청절차를 검증
        // 토큰 기반 인증을 위해서는 CSRF 설정을 해제
        // 개발 단계에서 일시적으로 disable 처리

        http
                .sessionManagement((auth) -> auth
                        .sessionFixation((sessionFixation) -> sessionFixation
                                .newSession()
                                .maximumSessions(1)
                                .maxSessionsPreventsLogin(true))
                );

        http
                .logout((auth) -> auth
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
//                        .permitAll()
                );

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(customUserDetailService).passwordEncoder(bCryptPasswordEncoder());
        return authenticationManagerBuilder.build();
    }
}


