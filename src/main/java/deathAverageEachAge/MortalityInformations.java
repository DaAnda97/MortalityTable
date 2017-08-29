package deathAverageEachAge;

import java.util.ArrayList;
import java.util.List;

public class MortalityInformations {
	public int birthyear;
	public boolean male;

	public List<RowData> rowDatas = new ArrayList<RowData>();

	public MortalityInformations(int birthyear, boolean male) throws Exception {
		super();
		setBirthyear(birthyear);
		this.male = male;

		MortalityTableExcel mortalityTable = new MortalityTableExcel();
		rowDatas = mortalityTable.getRowDataList("" + birthyear + "_" + (male ? "M" : "W") + "_V2");
	}

	public void getDeathpercentageEachYear() {
		System.out.println("Alter : Wahrscheinlickeit Tod zu sein");
		for (RowData rowData : rowDatas) {
			System.out.printf("%d : %.2f%% Leben noch:%d sterben in diesem Jahr:%d \n", rowData.getAge(),
					rowData.getDeathPercentage(), rowData.getLivingPeople(), rowData.getDeadPeople());
		}
	}

	public void getLivingpercentageEachYear() {
		System.out.println("Alter : Wahrscheinlickeit noch zu Leben");
		for (RowData rowData : rowDatas) {
			System.out.printf("%d : %.2f%% \n", rowData.getAge(), rowData.getLivingPercentage());
		}
	}

	public List<RowData> getRowDatas() {
		return rowDatas;
	}

	private void setBirthyear(int birthyear) throws UnavailableYearException {
		if (birthyear >= 1896 && birthyear <= 2009) {
			this.birthyear = birthyear;
		} else {
			throw new UnavailableYearException();
		}
	}

	public int getBirthyear() {
		return birthyear;
	}

	public boolean isMale() {
		return male;
	}

	public static void main(String[] args) {
		MortalityInformations info;
		try {
			info = new MortalityInformations(1960, false);
			info.getDeathpercentageEachYear();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

}
