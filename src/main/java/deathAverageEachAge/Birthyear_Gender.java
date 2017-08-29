package deathAverageEachAge;

public class Birthyear_Gender {
	private boolean male;
	private int birthyear;

	public Birthyear_Gender(boolean male, int birthyear) throws UnavailableYearException {
		setBirthyear(birthyear);
		setMale(male);
	}

	public boolean isMale() {
		return male;
	}

	public void setMale(boolean male) {
		this.male = male;
	}

	public int getBirthyear() {
		return birthyear;
	}

	public void setBirthyear(int birthyear) throws UnavailableYearException {
		if (birthyear >= 1896 && birthyear <= 2009) {
			this.birthyear = birthyear;
		} else {
			throw new UnavailableYearException();
		}
	}

}
