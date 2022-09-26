package com.randered.imdb.security;

import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import static com.randered.imdb.security.SecurityConfigurationProperties.SECURITY_CONFIG_PREFIX;

@AllArgsConstructor
@ConstructorBinding
@ConfigurationProperties(prefix = SECURITY_CONFIG_PREFIX)
public class SecurityConfigurationProperties {

    static final String SECURITY_CONFIG_PREFIX = "security";

    public final Jwt jwt;

    @AllArgsConstructor
    @ConstructorBinding
    public static final class Jwt {

        public final String secret;
        public final Login login;
        public final Refresh refresh;

        @AllArgsConstructor
        @ConstructorBinding
        public static final class Login {

            public final Expiration expiration;

            @AllArgsConstructor
            @ConstructorBinding
            public static final class Expiration {

                public final Long ms;
            }
        }

        @AllArgsConstructor
        @ConstructorBinding
        public static final class Refresh {

            public final Expiration expiration;

            @AllArgsConstructor
            @ConstructorBinding
            public static final class Expiration {

                public final Long ms;
            }
        }
    }
}
