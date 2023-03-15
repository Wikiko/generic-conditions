package com.luizfbs.experiments.generic.conditions;

import com.luizfbs.experiments.generic.conditions.helpers.ReflectionHelper;
import com.luizfbs.experiments.generic.conditions.models.Sample;
import com.luizfbs.experiments.generic.conditions.operators.Operator;
import com.luizfbs.experiments.generic.conditions.operators.Operator2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.List;

@SpringBootTest
class ApplicationTests {
	final private Sample mockSample = new Sample(
			"RETAIL",
			List.of(new String[] { "GROCERIES_A", "GROCERIES_B", "GROCERIES_C", "GROCERIES_D" }),
			BigDecimal.valueOf(10)
			);

	@Test
	void getChannelConditionName() {
		String conditionName = "CHANNEL";
		String name = Condition.getConditionName(conditionName);
		assert (name).equals(conditionName);
	}

	@Test
	void getNullWhenTryingToGetUnknownCondition() {
		String conditionName = "OTHER_CHANNEL";
		String name = Condition.getConditionName(conditionName);
		Assertions.assertNull(name);
	}

	@Test
	void getChannelAllowedConditionOperators() throws NoSuchFieldException, SecurityException {
		String fieldName = "channel";

		var operators = ReflectionHelper.getOperatorsOfSample(fieldName);

		assertEquals(operators, List.of(new Operator[] {
				Operator.EQUAL_TO, Operator.NOT_EQUAL_TO
		}));
	}

	@Test
	void evaluateChannelEqualToExpectedValue() {
		String conditionName = "channel";
		String operatorName = "EQUAL_TO";
		String expectedValue = "RETAIL";

		Object conditionReference = ReflectionHelper.getValue(mockSample, conditionName);;
		//String conditionReference = String.valueOf(Condition.getConditionReference(conditionName));
		
		Operator operator = ReflectionHelper.getOperatorOfSample(conditionName, operatorName).get();

		Boolean result = operator.evaluate.apply(mockSample, conditionName, expectedValue);
		assertTrue(result);
	}

	@Test
	void evaluateChannelEqualToExpectedValue2UsingAnotherWays(){
		String fieldName = "channel";
		String operatorName = "EQUAL_TO";
		String expectedValue = "RETAIL";

		Object value = ReflectionHelper.getValue(mockSample, fieldName);;
		
		Operator2 operator = ReflectionHelper.getOperator2OfSample(fieldName, operatorName).get();

		Boolean result = operator.apply(expectedValue, value);
		assertTrue(result);
	}

	@Test
	void evaluatePriceEqualToExpectedValue(){
		String fieldName = "price";
		String operatorName = "EQUAL_TO";
		BigDecimal expectedValue = BigDecimal.valueOf(10);

		Object value = ReflectionHelper.getValue(mockSample, fieldName);
		
		Operator2 operator = ReflectionHelper.getOperator2OfSample(fieldName, operatorName).get();

		assertTrue(operator.apply(expectedValue, value));
	}

	@Test
	void evaluateTagInExpectedValue2() {
		String conditionName = "merchant_tags";
		String operatorName = "IN";
		String expectedValue = "GROCERIES_B";

		Object value = ReflectionHelper.getValue(mockSample, conditionName);

		Operator2 operator = ReflectionHelper.getOperator2OfSample(conditionName, operatorName).get();

		assertTrue(operator.apply(expectedValue, value));
	}

	@Test
	void evaluateMerchantTagToTestNameOnAnnotation(){
		String conditionName = "merchant_tags";
		String operatorName = "IN";
		String expectedValue = "GROCERIES_B";

		Object value = ReflectionHelper.getValue(mockSample, conditionName);

		assertEquals(mockSample.getTags(), value);
	}
}
