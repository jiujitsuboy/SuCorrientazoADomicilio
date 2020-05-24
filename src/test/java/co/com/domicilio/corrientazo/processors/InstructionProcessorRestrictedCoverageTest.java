package co.com.domicilio.corrientazo.processors;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import co.com.domicilio.corrientazo.enums.InstructionSet;
import co.com.domicilio.corrientazo.models.Location;

public class InstructionProcessorRestrictedCoverageTest extends InstructionProcessorTest {

	private InstructionProcessorRestrictedCoverage instructionProcessorRestrictedCoverage;
	

	@BeforeEach
	public void init() {
		instructionProcessorRestrictedCoverage = new InstructionProcessorRestrictedCoverage();
	}

	@Override
	protected InstructionProcessor createIntructionProcessor() {
		return instructionProcessorRestrictedCoverage;
	}

	@Test
	public void processInstructionsOutOfRange() {
		List<InstructionSet[]> instructionSets = new ArrayList<InstructionSet[]>();
		
		InstructionSet[] instructionSet = new InstructionSet[] { InstructionSet.A, InstructionSet.A, InstructionSet.A,
				InstructionSet.A, InstructionSet.A, InstructionSet.A, InstructionSet.A, InstructionSet.A,
				InstructionSet.A, InstructionSet.A, InstructionSet.A };
		
		instructionSets.add(instructionSet);
		
		List<Location> locations = instructionProcessorRestrictedCoverage.processOrdersIntructions(instructionSets);
		
		assertTrue(locations.get(0).isOutOfCoverage());
		
	}
	
	@Test
	public void processInstructionsInsideRange() {
		List<InstructionSet[]> instructionSets = new ArrayList<InstructionSet[]>();
		
		InstructionSet[] instructionSet = new InstructionSet[] { InstructionSet.A, InstructionSet.I, InstructionSet.A,
				InstructionSet.A, InstructionSet.D, InstructionSet.A, InstructionSet.A, InstructionSet.D,
				InstructionSet.A, InstructionSet.A, InstructionSet.A };
		
		instructionSets.add(instructionSet);
		
		List<Location> locations = instructionProcessorRestrictedCoverage.processOrdersIntructions(instructionSets);
		
		assertFalse(locations.get(0).isOutOfCoverage());
		
	}

}
