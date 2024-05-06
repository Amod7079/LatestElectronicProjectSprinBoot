// ImageNameValid.java
package com.CoderXAmod.Electronic.validate;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ImageNameValidator.class)
public @interface ImageNameValid {

    String message() default "Invalid Image Name!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
