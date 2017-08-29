package deathAverageEachAge;

public class RowData {
	private int age;
	private int livingPeople; // Out of 100000
	private int deadPeople; // Out of 100000
	private double livingPercentage;
	private double deadPercentage;

	public RowData(int age, int livingPeople, int deadPeople, double livingPercentage, double deadPercentage) {
		super();
		this.age = age;
		this.livingPeople = livingPeople;
		this.deadPeople = deadPeople;
		this.livingPercentage = livingPercentage;
		this.deadPercentage = deadPercentage;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getLivingPeople() {
		return livingPeople;
	}

	public void setLivingPeople(int livingPeople) {
		this.livingPeople = livingPeople;
	}

	public int getDeadPeople() {
		return deadPeople;
	}

	public void setDeadPeople(int deadPeople) {
		this.deadPeople = deadPeople;
	}

	public double getLivingPercentage() {
		return livingPercentage;
	}

	public void setLivingPercentage(double livingPercentage) {
		this.livingPercentage = livingPercentage;
	}

	public double getDeathPercentage() {
		return deadPercentage;
	}

	public void setDeadPercentage(double deadPercentage) {
		this.deadPercentage = deadPercentage;
	}

}
