package com.lzy.demo.jpa.auditing;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * 只有一个AuditorAware,spring会自动找到,否则需要在@EnableJpaAuditing的auditorAwareRef指定
 *
 * @author LZY
 * @version v1.0
 */
@Component
public class SecurityAuditorAware implements AuditorAware<String> {
    /**
     * Returns the current auditor of the application.
     *
     * @return the current auditor
     */
    @Override
    public Optional<String> getCurrentAuditor() {
        // 为@CreatedBy,@LastModifiedBy提供值
        // 这边通过SecurityContextHolder获取真实用户
        return Optional.ofNullable("lzy");
    }
}
