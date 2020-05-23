package co.com.domicilio.corrientazo.models;

import co.com.domicilio.corrientazo.enums.CardinalPoint;
import co.com.domicilio.corrientazo.enums.InstructionSet;

/**
 * Geographic locations in a 2 dimension plane, plus a facing orientation {@link CardinalPoint}}
 * @author JoseAlejandro
 *
 */
public class Location {
	private int coordenateX;
	private int coordenateY;
	private CardinalPoint cardinalPoint;
	private boolean outOfCoverage;

	public Location() {
		coordenateX = 0;
		coordenateY = 0;
		cardinalPoint = CardinalPoint.NORTH;
	}

	public Location(int coordenateX, int coordenateY, CardinalPoint cardinalPoint) {
		this.coordenateX = coordenateX;
		this.coordenateY = coordenateY;
		this.cardinalPoint = cardinalPoint;
	}

	public int getCoordenateX() {
		return coordenateX;
	}

	public void setCoordenateX(int coordenateX) {
		this.coordenateX = coordenateX;
	}

	public int getCoordenateY() {
		return coordenateY;
	}

	public void setCoordenateY(int coordenateY) {
		this.coordenateY = coordenateY;
	}

	public CardinalPoint getCardinalPoint() {
		return cardinalPoint;
	}

	public void setCardinalPoint(CardinalPoint cardinalPoint) {
		this.cardinalPoint = cardinalPoint;
	}

	public boolean isOutOfCoverage() {
		return outOfCoverage;
	}

	public void setOutOfCoverage(boolean outOfCoverage) {
		this.outOfCoverage = outOfCoverage;
	}

	/**
	 * Advance 1 unit of distance in the corresponding direction
	 */
	public void advanceOneBlock() {
		switch (cardinalPoint) {
		case NORTH:
			coordenateY++;
			break;
		case SOUTH:
			coordenateY--;
			break;
		case EAST:
			coordenateX++;
			break;
		case WEST:
			coordenateX--;
			break;
		}
	}

	/**
	 * Turn location according direction specified
	 * @param instruction instruction to turn in that location
	 */
	public void turnDirection(InstructionSet instruction) {
		if (instruction == InstructionSet.D) {

			switch (cardinalPoint) {
			case NORTH:
				cardinalPoint = CardinalPoint.EAST;
				break;
			case SOUTH:
				cardinalPoint = CardinalPoint.WEST;
				break;
			case EAST:
				cardinalPoint = CardinalPoint.SOUTH;
				break;
			case WEST:
				cardinalPoint = CardinalPoint.NORTH;
				break;
			}

		}

		else if (instruction == InstructionSet.I) {
			switch (cardinalPoint) {
			case NORTH:
				cardinalPoint = CardinalPoint.WEST;
				break;
			case SOUTH:
				cardinalPoint = CardinalPoint.EAST;
				break;
			case EAST:
				cardinalPoint = CardinalPoint.NORTH;
				break;
			case WEST:
				cardinalPoint = CardinalPoint.SOUTH;
				break;
			}
		}
	}

	@Override
	public String toString() {
		String text = null;
		if (outOfCoverage) {
			text = String.format("(%d,%d) %s direction (OUT OF COVERAGE)", coordenateX, coordenateY, cardinalPoint);
		} else {
			text = String.format("(%d,%d) %s direction", coordenateX, coordenateY, cardinalPoint);
		}
		return text;
	}
}
