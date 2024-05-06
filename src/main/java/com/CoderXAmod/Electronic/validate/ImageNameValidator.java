// ImageNameValidator.java
package com.CoderXAmod.Electronic.validate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ImageNameValidator implements ConstraintValidator<ImageNameValid, String> {
    private static final Logger logger = LoggerFactory.getLogger(ImageNameValidator.class);

    @Override
    public void initialize(ImageNameValid constraintAnnotation) {
        // Initialize any validation resources if needed
        
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        logger.info("Message from isValid: {}", value);
        return value != null && !value.isBlank();
    }
}
