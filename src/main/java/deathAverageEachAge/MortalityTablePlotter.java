package deathAverageEachAge;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class MortalityTablePlotter extends JFrame {
	JPanel mainPanel = new JPanel();
	List<Birthyear_Gender> gb;

	public MortalityTablePlotter(List<Birthyear_Gender> bgList, int graphIndex) {
		gb = new ArrayList<Birthyear_Gender>();

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainPanel.setLayout(new BorderLayout());

		final PlotterPanel plot = new PlotterPanel(bgList_to_MiList(bgList), graphIndex);
		this.setSize(plot.getPreferredSize());
		mainPanel.add(plot, BorderLayout.CENTER);

		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new GridLayout(3,1));
		JButton livingProbability = new JButton("Living Probability");
		JButton dyingProbability = new JButton("Dying Probability");
		JButton deadPeople = new JButton("Death People");
		controlPanel.add(livingProbability);
		controlPanel.add(dyingProbability);
		controlPanel.add(deadPeople);
		controlPanel.add(new JLabel(" "));
//		final JPanel delitePanel = new JPanel();
//		controlPanel.add(delitePanel);
		mainPanel.add(controlPanel, BorderLayout.EAST);

		JPanel input = new JPanel();
		// BirthYear Input
		JPanel birthYearPanel = new JPanel();
		birthYearPanel.setLayout(new GridLayout(1, 2, 0, (int) (ImageObserver.HEIGHT / 50)));
		JLabel birthYearLabel = new JLabel("Birth Year:");
		final JTextField birthYearInput = new JTextField();
		birthYearInput.setSize(50, 20);
		birthYearPanel.add(birthYearLabel);
		birthYearPanel.add(birthYearInput);
		// Gender RadioButton
		JPanel genderPanel = new JPanel();
		genderPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		final JRadioButton male = new JRadioButton("Male", true);
		JRadioButton female = new JRadioButton("Female");
		ButtonGroup gender = new ButtonGroup();
		gender.add(male);
		gender.add(female);
		genderPanel.add(male);
		genderPanel.add(female);
		input.add(birthYearPanel);
		input.add(genderPanel);
		JButton apply = new JButton("Apply");
		apply.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int birthYear = Integer.parseInt(birthYearInput.getText());
					boolean isMale = male.isSelected();
					Birthyear_Gender birthyearGender = new Birthyear_Gender(isMale, birthYear);
					if (!containsElement(gb, birthyearGender)) {
						gb.add(birthyearGender);
						plot.setInfos(bgList_to_MiList(gb));
//						delitePanel.add(new JLabel("Blaaaa"));
//						delitePanel.add(new JButton("x" + ""));
						repaint();
					}
				} catch (Exception ex) {
					System.out.println(ex.getMessage());
				}
			}

			private boolean containsElement(List<Birthyear_Gender> gb, Birthyear_Gender birthyearGender) {
				for (Iterator iterator = gb.iterator(); iterator.hasNext();) {
					Birthyear_Gender birthyear_Gender = (Birthyear_Gender) iterator.next();
					if (birthyear_Gender.getBirthyear() == birthyearGender.getBirthyear()
							&& birthyear_Gender.isMale() == birthyearGender.isMale()) {
						return true;
					}
				}
				return false;
			}
		});
		input.add(apply);
		mainPanel.add(input, BorderLayout.NORTH);

		this.add(mainPanel);
		this.pack();
		this.setVisible(true);
	}

	private List<MortalityInformations> bgList_to_MiList(List<Birthyear_Gender> bgList) {
		List<MortalityInformations> miList = new ArrayList<MortalityInformations>();

		for (Iterator iterator = bgList.iterator(); iterator.hasNext();) {
			Birthyear_Gender birthyear_Gender = (Birthyear_Gender) iterator.next();
			MortalityInformations mortalityInfo;
			try {
				mortalityInfo = new MortalityInformations(birthyear_Gender.getBirthyear(), birthyear_Gender.isMale());
				miList.add(mortalityInfo);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return miList;
	}

	class PlotterPanel extends JPanel {
		private List<MortalityInformations> infos;

		int width;
		int height;
		Koordinate xTop;
		Koordinate yTop;
		Koordinate cross;

		public List<GraphName> graphNames = new ArrayList<GraphName>();
		private static final int AGE_DEATHPROBABILITY = 0;
		private static final int AGE_LIVINGPROBABILITY = 1;
		private static final int AGE_DEATHPEOPLE = 2;
		private int graphIndex = 0;

		Font koordinatsFont = new Font("Verdana", Font.PLAIN, 10);
		Font axisFont = new Font("Verdana", Font.PLAIN, 12);
		Font headlineFont = new Font("Verdana", Font.PLAIN, 25);

		private Color[] graphColors = new Color[] { Color.RED, Color.BLUE, Color.GREEN, Color.CYAN, Color.GRAY,
				Color.YELLOW };

		public PlotterPanel(List<MortalityInformations> info, int graphIndex) {
			graphNames.add(
					new GraphName("Todeswahrscheinlichkeitsrate", "Todeswahrscheinlichkeit in %", "Alter in Jahren"));
			graphNames.add(
					new GraphName("Lebenswahrscheinlichkeitsrate", "Lebenswharscheinlichkeit in %", "Alter in Jahren"));
			graphNames.add(new GraphName("Gestorbene Personen", "Todeswahrscheinlichkeit in %", "Alter in Namen"));

			this.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
			this.infos = info;
			this.graphIndex = graphIndex;
		}

		public Dimension getPreferredSize() {
			return new Dimension(900, 900);
		}

		public void paintComponent(Graphics g) {
			width = this.getWidth();
			height = this.getHeight();
			g.setColor(Color.BLACK);

			// Headline
			g.setFont(headlineFont);
			g.drawString(graphNames.get(graphIndex).getGraphName(),
					width / 2
							- g.getFontMetrics(g.getFont()).stringWidth(graphNames.get(graphIndex).getGraphName()) / 2,
					height / 15);

			// Koordinatensystem
			g.setFont(koordinatsFont);
			xTop = new Koordinate(width - Math.round(width / 10) - 50, height - Math.round(height / 10));
			cross = new Koordinate(width - Math.round(width / 10) * 9, height - Math.round(height / 10));
			yTop = new Koordinate(width - Math.round(width / 10) * 9, height - Math.round(height / 10) * 9 + 50);
			// Horizontal
			g.drawLine(cross.getX(), cross.getY(), yTop.getX(), yTop.getY() - (height / 35));
			// Arrow
			g.drawLine(yTop.getX(), yTop.getY() - Math.round(height / 35), yTop.getX() - Math.round(width / 100),
					yTop.getY() - Math.round(height / 60));
			g.drawLine(yTop.getX(), yTop.getY() - Math.round(height / 35), yTop.getX() + Math.round(width / 100),
					yTop.getY() - Math.round(height / 60));
			for (int i = 0; i <= 10; i++) {
				Koordinate point = new Koordinate(yTop.getX(),
						cross.getY() - Math.round((cross.getY() - yTop.getY()) / 10 * i));
				if (i == 10) {
					point = new Koordinate(yTop.getX(), yTop.getY());
				}
				// Marks
				g.drawLine(point.getX() - Math.round(width / 200), point.getY(), point.getX() + Math.round(width / 200),
						point.getY());
				// Lines
				g.setColor(Color.LIGHT_GRAY);
				g.drawLine(point.getX(), point.getY(), xTop.getX(), point.getY());
				g.setColor(Color.BLACK);
				g.drawString(i * 10 + "",
						point.getX() - (g.getFontMetrics(g.getFont()).stringWidth(i * 10 + "")) - width / 100,
						point.getY() + (g.getFontMetrics(g.getFont()).getHeight()
								- g.getFontMetrics(g.getFont()).getDescent()) / 2);
			}

			// Vertical
			g.drawLine(cross.getX(), cross.getY(), xTop.getX() + (width / 35), xTop.getY());
			// Arrow
			g.drawLine(xTop.getX() + Math.round(width / 35), xTop.getY(), xTop.getX() + Math.round(width / 60),
					xTop.getY() + Math.round(height / 100));
			g.drawLine(xTop.getX() + Math.round(width / 35), xTop.getY(), xTop.getX() + Math.round(width / 60),
					xTop.getY() - Math.round(height / 100));
			for (int i = 0; i <= 10; i++) {
				Koordinate point = new Koordinate(xTop.getX() - Math.round((xTop.getX() - cross.getX()) / 10 * i),
						xTop.getY());
				if (i == 10) {
					point = new Koordinate(cross.getX(), cross.getY());
				}
				g.drawLine(point.getX(), point.getY() - Math.round(height / 200), point.getX(),
						point.getY() + Math.round(width / 200));
				g.drawString((10 - i) * 10 + "",
						point.getX() - (g.getFontMetrics(g.getFont()).stringWidth((10 - i) * 10 + "")) / 2,
						point.getY() + height / 50);
			}

			addAxisNames(g, graphNames.get(graphIndex).getxAxis(), graphNames.get(graphIndex).getyAxis());
			for (Iterator iterator = infos.iterator(); iterator.hasNext();) {
				MortalityInformations mortalityInformations = (MortalityInformations) iterator.next();
				g.setColor(graphColors[infos.indexOf(mortalityInformations)]);
				drawDeathProbabilityGraph(g, mortalityInformations.getRowDatas());

				// Legend
				g.fillRect((width / 6) * infos.indexOf(mortalityInformations) + width / 20, (int) (height * 0.95), 10,
						10);
				g.setColor(Color.BLACK);
				g.drawString(
						mortalityInformations.getBirthyear() + ", "
								+ (mortalityInformations.isMale() ? "männlich" : "weiblich"),
						(width / 6) * infos.indexOf(mortalityInformations) + width / 20 + 12,
						(int) (height * 0.95) + g.getFontMetrics(g.getFont()).getHeight()
								- g.getFontMetrics(g.getFont()).getDescent() * 2);
			}
		}

		public void drawDeathProbabilityGraph(Graphics g, List<RowData> rowDatas) {
			Koordinate previous = null;
			for (Iterator<RowData> row = rowDatas.iterator(); row.hasNext();) {
				RowData rowData = (RowData) row.next();
				Koordinate point = new Koordinate(
						Math.round(cross.getX() + (xTop.getX() - cross.getX()) * rowData.getAge() / 100),
						(int) Math.round(
								cross.getY() - (cross.getY() - yTop.getY()) * rowData.getDeathPercentage() / 100));
				if (previous != null) {
					g.drawLine(previous.getX(), previous.getY(), point.getX(), point.getY());
				}
				previous = point;
			}
		}

		public void drawLivingProbabilityGraph(Graphics g, List<RowData> rowDatas) {
			Koordinate previous = null;
			for (Iterator<RowData> row = rowDatas.iterator(); row.hasNext();) {
				RowData rowData = (RowData) row.next();
				Koordinate point = new Koordinate(
						Math.round(cross.getX() + (xTop.getX() - cross.getX()) * rowData.getAge() / 100),
						(int) Math.round(
								cross.getY() - (cross.getY() - yTop.getY()) * rowData.getLivingPercentage() / 100));
				if (previous != null) {
					g.drawLine(previous.getX(), previous.getY(), point.getX(), point.getY());
				}
				previous = point;
			}
		}

		public void drawDeadPeopleGraph(Graphics g, List<RowData> rowDatas) {
			Koordinate previous = null;
			for (Iterator<RowData> row = rowDatas.iterator(); row.hasNext();) {
				RowData rowData = (RowData) row.next();
				Koordinate point = new Koordinate(
						Math.round(cross.getX() + (xTop.getX() - cross.getX()) * rowData.getAge() / 100), (int) Math
								.round(cross.getY() - (cross.getY() - yTop.getY()) * rowData.getDeadPeople() / 10000));
				if (previous != null) {
					g.drawLine(previous.getX(), previous.getY(), point.getX(), point.getY());
				}
				previous = point;
			}
		}

		public void addAxisNames(Graphics g, String xAxis, String yAxis) {
			g.setFont(axisFont);
			g.drawString(xAxis, xTop.getX() + width / 30, xTop.getY()
					+ (g.getFontMetrics(g.getFont()).getHeight() - g.getFontMetrics(g.getFont()).getDescent()) / 2);
			g.drawString(yAxis, yTop.getX() - (g.getFontMetrics(g.getFont()).stringWidth(yAxis)) / 3,
					yTop.getY() - height / 25);
		}

		public List<MortalityInformations> getInfos() {
			return infos;
		}

		public void setInfos(List<MortalityInformations> infos) throws ToManyElementsException {
			if (infos.size() < graphColors.length) {
				this.infos = infos;
			} else {
				List<MortalityInformations> shortedInfos = new ArrayList<MortalityInformations>();
				for (int i = 0; i < graphColors.length; i++) {
					shortedInfos.add(infos.get(i));
				}
				throw new ToManyElementsException();
			}
		}

		public void setGraphIndex(int graphIndex) {
			if (graphIndex >= 0 && graphIndex <= 2) {
				this.graphIndex = graphIndex;
			} else {
				System.out.println(graphIndex + " ist kein verfügbarer Graph");
			}
		}

	}

	public static void main(String[] args) {
		@SuppressWarnings("unused")
		MortalityTablePlotter plotter;

		MortalityInformations info;
		try {
			Birthyear_Gender bg1 = new Birthyear_Gender(false, 1997);
			// Birthyear_Gender bg2 = new Birthyear_Gender(true, 1997);
			// Birthyear_Gender bg3 = new Birthyear_Gender(true, 1950);
			// Birthyear_Gender bg4 = new Birthyear_Gender(true, 1975);
			// Birthyear_Gender bg5 = new Birthyear_Gender(true, 2000);
			// List<Birthyear_Gender> bgList = new
			// List<Birthyear_Gender> bgList = new
			// ArrayList<Birthyear_Gender>();
			// bgList.add(bg1);
			// bgList.add(bg2);
			// bgList.add(bg3);
			// bgList.add(bg4);
			// bgList.add(bg5);
			List<Birthyear_Gender> bgList = Collections.emptyList();
			plotter = new MortalityTablePlotter(bgList, PlotterPanel.AGE_DEATHPROBABILITY);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
