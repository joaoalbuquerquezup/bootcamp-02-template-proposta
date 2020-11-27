package br.com.zup.proposta.validation;

import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;

@Component
public class UniqueValidator implements ConstraintValidator<Unique, Object> {

    private final EntityManager entityManager;
    private Class<?> entityClass;
    private String entityField;

    public UniqueValidator (EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void initialize(Unique constraintAnnotation) {
        this.entityClass = constraintAnnotation.clazz();
        this.entityField = constraintAnnotation.field();
    }

    @Override
    @Transactional
    public boolean isValid (Object propValueThatMustBeUnique, ConstraintValidatorContext context) {

        if (this.entityField.isEmpty()) {
            this.entityField = this.getFieldValueByReflection(context);
        }

        String sql = "select 1 from " + this.entityClass.getSimpleName() + " where " + this.entityField + "= :entityField";

        return this.entityManager
                    .createQuery(sql)
                    .setParameter("entityField", propValueThatMustBeUnique)
                    .getResultList()
                    .isEmpty();
    }

    private String getFieldValueByReflection(ConstraintValidatorContext context) {
        try {
            Field basePath = ConstraintValidatorContextImpl.class.getDeclaredField("basePath");
            basePath.setAccessible(true);
            PathImpl pathImpl = (PathImpl) basePath.get(context);
            return pathImpl.getLeafNode().asString();
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException();
        }
    }
}

