package bg.paysafe.springboot.api.config;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;

import static bg.paysafe.springboot.api.constant.ConfigurationConstants.CONFIG_VERSION;
import static bg.paysafe.springboot.api.constant.ConfigurationConstants.UTF;
import static bg.paysafe.springboot.api.constant.URLMappings.TEMPLATE_BASE;

@Configuration
public class BeanConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public freemarker.template.Configuration freemarkerConfiguration() {
        var cfg = new freemarker.template.Configuration(new Version(CONFIG_VERSION));

        cfg.setTemplateLoader(new ClassTemplateLoader(this.getClass(), TEMPLATE_BASE));
        cfg.setDefaultEncoding(UTF);
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        return cfg;
    }

}
