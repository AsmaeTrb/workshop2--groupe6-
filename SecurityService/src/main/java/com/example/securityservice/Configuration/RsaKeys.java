package com.example.securityservice.Configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;

@ConfigurationProperties(prefix = "rsa")
public record RsaKeys(Resource publicKey, Resource privateKey) { }
