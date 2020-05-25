package co.com.domicilio.corrientazo.parsers;

import co.com.domicilio.corrientazo.enums.InstructionSet;
import co.com.domicilio.corrientazo.exceptions.InvalidRouteDescription;

public class ParseTextInstruction {

	private final String VALID_TOKENS_SET = "^[AID]+$";
	private final String ROUTE_PARSER_ERROR_MSG = "Invalid set of intructions found. Valid set is [A,I,D]";

	/**
	 * From an String, parse it and generate a valid instruction set
	 * {@link InstructionSet}
	 * 
	 * @param route String which represent a instruction set to parse
	 * @return {@link InstructionSet[]}
	 * @throws InvalidRouteDescription
	 */
	public InstructionSet[] parse(String route) throws InvalidRouteDescription {

		InstructionSet[] instructionSet = null;

		if (!route.matches(VALID_TOKENS_SET)) {
			throw new InvalidRouteDescription(ROUTE_PARSER_ERROR_MSG);
		}

		instructionSet = new InstructionSet[route.length()];
		String upperRoute = route.toUpperCase();

		for (int index = 0; index < upperRoute.length(); index++) {

			switch (upperRoute.charAt(index)) {
			case 'A':
				instructionSet[index] = InstructionSet.A;
				break;
			case 'I':
				instructionSet[index] = InstructionSet.I;
				break;
			case 'D':
				instructionSet[index] = InstructionSet.D;
				break;
			}
		}

		return instructionSet;
	}
}
