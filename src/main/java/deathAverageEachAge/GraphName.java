package deathAverageEachAge;

public class GraphName {
	private String graphName;
	private String yAxis;
	private String xAxis;

	GraphName(String graphName, String yAxis, String xAxis) {
		this.graphName = graphName;
		this.yAxis = yAxis;
		this.xAxis = xAxis;
	}

	public String getGraphName() {
		return graphName;
	}

	public void setGraphName(String graphName) {
		this.graphName = graphName;
	}

	public String getyAxis() {
		return yAxis;
	}

	public void setyAxis(String yAxis) {
		this.yAxis = yAxis;
	}

	public String getxAxis() {
		return xAxis;
	}

	public void setxAxis(String xAxis) {
		this.xAxis = xAxis;
	}

}