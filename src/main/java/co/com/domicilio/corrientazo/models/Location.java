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
		cardinalPoint = CardinalPoint.NORTE;
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
		case NORTE:
			coordenateY++;
			break;
		case SUR:
			coordenateY--;
			break;
		case ORIENTE:
			coordenateX++;
			break;
		case OCCIDENTE:
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
			case NORTE:
				cardinalPoint = CardinalPoint.ORIENTE;
				break;
			case SUR:
				cardinalPoint = CardinalPoint.OCCIDENTE;
				break;
			case ORIENTE:
				cardinalPoint = CardinalPoint.SUR;
				break;
			case OCCIDENTE:
				cardinalPoint = CardinalPoint.NORTE;
				break;
			}

		}

		else if (instruction == InstructionSet.I) {
			switch (cardinalPoint) {
			case NORTE:
				cardinalPoint = CardinalPoint.OCCIDENTE;
				break;
			case SUR:
				cardinalPoint = CardinalPoint.ORIENTE;
				break;
			case ORIENTE:
				cardinalPoint = CardinalPoint.NORTE;
				break;
			case OCCIDENTE:
				cardinalPoint = CardinalPoint.SUR;
				break;
			}
		}
	}

	@Override
	public String toString() {
		String text = null;
		if (outOfCoverage) {
			text = String.format("(%d,%d) %s dirección (FUERA DE CONVERTURA)", coordenateX, coordenateY, cardinalPoint);
		} else {
			text = String.format("(%d,%d) %s dirección", coordenateX, coordenateY, cardinalPoint);
		}
		return text;
	}
}
