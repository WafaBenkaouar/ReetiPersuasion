package moca.output.reeti_controler;

public class ReetiLedColor {

	public enum Modes {
		mode0("both"), mode1("right"), mode2("left"), mode4("none");

		private final String name;

		private Modes(String s) {
			name = s;
		}

		public boolean equalsName(String otherName) {
			return (otherName == null) ? false : name.equals(otherName);
		}

		@Override
		public String toString() {
			return name;
		}

		public int toInt() {
			return name.charAt(name.length() - 1) - 97;
		}

	}

	public enum Colors {
		rouge("red"), vert("green"), vert_clair("light green"), bleu("blue"), bleu_fonce(
						"dark blue"), turquoise("turquoise"), jaune("yellow"), violet("violet"), blanc(
						"white"), stop("stop");

		private final String name;

		private Colors(String s) {
			name = s;
		}

		public boolean equalsName(String otherName) {
			return (otherName == null) ? false : name.equals(otherName);
		}

		@Override
		public String toString() {
			return name;
		}

		public static Colors[] getColors() {
			Colors[] colors = { rouge, vert, vert_clair, bleu, bleu_fonce, turquoise, jaune,
							violet, blanc, stop };
			return colors;

		}

	}

	private Modes mode;
	private int r;
	private int g;
	private int b;
	private int intensity;
	private Colors name;

	public ReetiLedColor(Modes mode, int r, int g, int b, int intensity) {
		this.mode = mode;
		this.r = r;
		this.g = g;
		this.b = b;
		this.intensity = intensity;
		this.name = Colors.stop;
	}

	public ReetiLedColor(String modestr, int r, int g, int b, int intensity) {
		this.mode = Modes.valueOf(modestr);
		this.r = r;
		this.g = g;
		this.b = b;
		this.intensity = intensity;
		this.name = Colors.stop;
	}

	public ReetiLedColor(Colors name) {
		this.name = name;
		this.mode = Modes.mode4;
		this.r = -1;
		this.g = -1;
		this.b = -1;
		this.intensity = -1;
	}

	public ReetiLedColor(String name) {
		this.name = Colors.valueOf(name);
		this.mode = Modes.mode4;
		this.r = -1;
		this.g = -1;
		this.b = -1;
		this.intensity = -1;
	}

	/**
	 * @return the mode
	 */
	public Modes getMode() {
		return mode;
	}

	/**
	 * @param mode
	 *            the mode to set
	 */
	public void setMode(Modes mode) {
		this.mode = mode;
	}

	/**
	 * @return the r
	 */
	public int getR() {
		return r;
	}

	/**
	 * @param r
	 *            the r to set
	 */
	public void setR(int r) {
		this.r = r;
	}

	/**
	 * @return the g
	 */
	public int getG() {
		return g;
	}

	/**
	 * @param g
	 *            the g to set
	 */
	public void setG(int g) {
		this.g = g;
	}

	/**
	 * @return the b
	 */
	public int getB() {
		return b;
	}

	/**
	 * @param b
	 *            the b to set
	 */
	public void setB(int b) {
		this.b = b;
	}

	/**
	 * @return the intensity
	 */
	public int getIntensity() {
		return intensity;
	}

	/**
	 * @param intensity
	 *            the intensity to set
	 */
	public void setIntensity(int intensity) {
		this.intensity = intensity;
	}

	/**
	 * @return the name
	 */
	public Colors getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(Colors name) {
		this.name = name;
	}

	public String getShellCommand() {
		if (mode != Modes.mode4)
			return "servo.change.LedColorRGB(" + mode.toInt() + "," + r + "," + g + "," + b + ","
							+ intensity + ")";
		else
			return "servo.change.LedColor( \" " + name.toString() + " \")";
	}

	// TODO
	/*
	 * public String getRGBfromName(String name) {
	 * 
	 * }
	 */
}
