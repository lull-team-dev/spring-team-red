package com.example.demo.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import org.springframework.beans.BeanWrapperImpl;

public class FieldsValueMatchValidator
		implements ConstraintValidator<FieldsValueMatch, Object> {

	private String field;
	private String fieldMatch;

	@Override
	public void initialize(FieldsValueMatch constraint) {
		field = constraint.field();
		fieldMatch = constraint.fieldMatch();
	}

	@Override
	public boolean isValid(Object obj, ConstraintValidatorContext context) {
		Object v1 = new BeanWrapperImpl(obj).getPropertyValue(field);
		Object v2 = new BeanWrapperImpl(obj).getPropertyValue(fieldMatch);
		if ((v1 == null && v2 == null) || (v1 != null && v1.equals(v2))) {
			return true;
		}
		// 不一致時にエラー位置を fieldMatch に設定
		context.disableDefaultConstraintViolation();
		context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
				.addPropertyNode(fieldMatch)
				.addConstraintViolation();
		return false;
	}
}
