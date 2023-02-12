package com.luizfbs.experiments.generic.conditions;

import com.luizfbs.experiments.generic.conditions.models.Sample;
import com.luizfbs.experiments.generic.conditions.operators.Operator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ApplicationTests {
	final private Sample mockSample = new Sample(
			"RETAIL",
			List.of(new String[]{ "GROCERIES_A", "GROCERIES_B", "GROCERIES_C", "GROCERIES_D" })
	);

	@Test
	void getChannelConditionName() {
		String conditionName = "CHANNEL";
		String name = Condition.getConditionName(conditionName);
		assert(name).equals(conditionName);
	}

	@Test
	void getNullWhenTryingToGetUnknownCondition() {
		String conditionName = "OTHER_CHANNEL";
		String name = Condition.getConditionName(conditionName);
		Assertions.assertNull(name);
	}

	@Test
	void getChannelAllowedConditionOperators() {
		String conditionName = "CHANNEL";
		List<Operator> operators = Condition.getOperators(conditionName);

		assert(operators).equals(List.of(new Operator[]{
				Operator.EQUAL_TO, Operator.NOT_EQUAL_TO
		}));
	}

	@Test
	void evaluateChannelEqualToExpectedValue() {
		String conditionName = "CHANNEL";
		String operatorName = "EQUAL_TO";
		String expectedValue = "RETAIL";

		String conditionReference = String.valueOf(Condition.getConditionReference(conditionName));
		Operator operator = Condition.getOperator(conditionName, operatorName);

		Object result = operator.evaluate.apply(mockSample, conditionReference, expectedValue);
		assert(result).equals(true);
	}

	@Test
	void evaluateTagInExpectedValue() {
		String conditionName = "TAGS";
		String operatorName = "IN";
		String expectedValue = "GROCERIES_B";

		String conditionReference = String.valueOf(Condition.getConditionReference(conditionName));
		Operator operator = Condition.getOperator(conditionName, operatorName);

		Object result = operator.evaluate.apply(mockSample, conditionReference, expectedValue);
		assert(result).equals(true);
	}
}
