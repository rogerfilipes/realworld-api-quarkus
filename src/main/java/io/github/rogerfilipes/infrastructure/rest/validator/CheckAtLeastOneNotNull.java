package io.github.rogerfilipes.infrastructure.rest.validator;

import org.apache.commons.beanutils.PropertyUtils;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = CheckAtLeastOneNotNull.CheckAtLeastOneNotNullValidator.class)
@Documented
public @interface CheckAtLeastOneNotNull {

    String message() default "At least one field must be not null";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String[] fieldNames();

    class CheckAtLeastOneNotNullValidator implements ConstraintValidator<CheckAtLeastOneNotNull, Object> {

        private String[] fieldNames;

        @Override
        public void initialize(CheckAtLeastOneNotNull constraintAnnotation) {
            this.fieldNames = constraintAnnotation.fieldNames();
        }

        public boolean isValid(Object object, ConstraintValidatorContext constraintContext) {

            if (object == null) {
                return true;
            }
            try {
                for (String fieldName : fieldNames) {
                    Object property = PropertyUtils.getProperty(object, fieldName);

                    if (property != null) {
                        return true;
                    }
                }
                return false;
            } catch (Exception e) {
                return false;
            }
        }

    }

}