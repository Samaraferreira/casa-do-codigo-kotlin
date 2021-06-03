package br.com.zup.utils.annotations

import io.micronaut.core.annotation.AnnotationValue
import io.micronaut.transaction.annotation.TransactionalAdvice
import io.micronaut.validation.validator.constraints.ConstraintValidator
import io.micronaut.validation.validator.constraints.ConstraintValidatorContext
import javax.inject.Singleton
import javax.persistence.EntityManager

@Singleton
@TransactionalAdvice
class UniqueValueValidator(val entityManager: EntityManager) : ConstraintValidator<UniqueValue, String> {

    override fun isValid(
        value: String?,
        annotationMetadata: AnnotationValue<UniqueValue>,
        context: ConstraintValidatorContext
    ): Boolean {

        if(value == null){
            return true
        }

        val clazz = annotationMetadata.stringValue("clazz").get()
        val fieldName = annotationMetadata.stringValue("fieldName").get()

        return entityManager
            .createQuery("select 1 from $clazz where $fieldName =:pValue")
            .setParameter("pValue", value)
            .resultList
            .isEmpty()
    }

}
