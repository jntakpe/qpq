package com.github.jntakpe.qpq.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Informations sur l'application
 *
 * @author jntakpe
 */
@Component
@ConfigurationProperties("app")
public class AppInfosProperties {

    private String name;

    private String description;

    private String version;

    private String contact;

    private String license;

    private String licenseUrl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getLicenseUrl() {
        return licenseUrl;
    }

    public void setLicenseUrl(String licenseUrl) {
        this.licenseUrl = licenseUrl;
    }
}
