//package org.example.bookmyshow.configs;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.domain.AuditorAware;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.Optional;
//
//@Configuration
//public class AuditorConfig {
//
//        @Bean
//        public AuditorAware<String> auditorProvider() {
//            return () -> Optional.ofNullable(getCurrentAuditor());
//        }
//
//        private String getCurrentAuditor() {
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            if (authentication == null || !authentication.isAuthenticated()) {
//                return null;
//            }
//            Object principal = authentication.getPrincipal();
//            if (principal instanceof UserDetails) {
//                return ((UserDetails) principal).getUsername();
//            } else {
//                return principal.toString();
//            }
//        }
//    }
