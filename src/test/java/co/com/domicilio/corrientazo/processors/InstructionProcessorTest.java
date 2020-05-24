package co.com.domicilio.corrientazo.processors;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import co.com.domicilio.corrientazo.config.AbstractConfigTest;
import co.com.domicilio.corrientazo.enums.CardinalPoint;
import co.com.domicilio.corrientazo.enums.InstructionSet;
import co.com.domicilio.corrientazo.models.Location;
import co.com.domicilio.corrientazo.processors.InstructionProcessor;

public abstract class InstructionProcessorTest extends AbstractConfigTest {

	private List<InstructionSet[]> instructionSets;

	@BeforeEach
	public void setUp() {
		instructionSets = new ArrayList<InstructionSet[]>();
		InstructionSet[] instructionSet = new InstructionSet[] { InstructionSet.A, InstructionSet.A, InstructionSet.D,
				InstructionSet.A, InstructionSet.I };

		InstructionSet[] instructionSet2 = new InstructionSet[] { InstructionSet.I, InstructionSet.A, InstructionSet.D,
				InstructionSet.A, InstructionSet.A };

		instructionSets.add(instructionSet);
		instructionSets.add(instructionSet2);
	}

	protected abstract InstructionProcessor createIntructionProcessor();

	@Test
	public void processOrdersAndEndFacingNorth() {
		InstructionProcessor instructionProcessor = createIntructionProcessor();
		List<Location> locations = instructionProcessor.processOrdersIntructions(instructionSets);
		for (Location location : locations) {
			assertEquals(location.getCardinalPoint(), CardinalPoint.NORTE);
		}
	}

}
