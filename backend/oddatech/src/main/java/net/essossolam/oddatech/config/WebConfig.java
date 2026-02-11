package net.essossolam.oddatech.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

        @org.springframework.beans.factory.annotation.Value("${app.file.upload-dir:./uploads}")
        private String uploadDir;

        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
                // Configuration pour Swagger UI uniquement
                registry.addResourceHandler("/swagger-ui/**")
                                .addResourceLocations("classpath:/META-INF/resources/webjars/springdoc-openapi-ui/")
                                .resourceChain(false);

                registry.addResourceHandler("/webjars/**")
                                .addResourceLocations("classpath:/META-INF/resources/webjars/");

                // Exposer le répertoire d'uploads pour les fichiers statiques (CV, lettres,
                // annonces)
                java.nio.file.Path uploadPath = java.nio.file.Paths.get(uploadDir).toAbsolutePath().normalize();
                registry.addResourceHandler("/uploads/**")
                                .addResourceLocations("file:" + uploadPath.toString() + "/");
        }

        @Override
        public void addViewControllers(ViewControllerRegistry registry) {
                // Redirection pour Swagger UI uniquement
                registry.addViewController("/swagger-ui/")
                                .setViewName("forward:/swagger-ui/index.html");
        }
}
