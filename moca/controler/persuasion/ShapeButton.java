package moca.controler.persuasion;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;

import javax.swing.BorderFactory;
import javax.swing.DefaultButtonModel;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

class ShapeButton extends JButton {
	public Color fc = new Color(100, 150, 255, 200);
	public Color ac = new Color(230, 230, 230);
	public Color rc = Color.ORANGE;
	public double score = 75;
	public int x, y;
	public String tex;
	private int startAngle;

	public void setFc(Color fc) {
		this.fc = fc;
	}

	public void setAc(Color ac) {
		this.ac = ac;
	}

	public void setRc(Color rc) {
		this.rc = rc;
	}

	protected Shape shape;

	private Path2D.Double makeArc(int x, int y, int startAngle, int angle,
			int radius) {
		Path2D.Double p = new Path2D.Double();
		p.moveTo(0, 0);
		double x2, y2;
		x2 = radius;
		y2 = 0;
		p.lineTo(x2, y2);
		p.moveTo(x2, y2);
		double x3, y3;
		x3 = radius * Math.cos(angle * Math.PI / 180 / 2);
		y3 = radius * Math.sin(angle * Math.PI / 180 / 2);
		double x4, y4;
		x4 = radius * Math.cos(angle * Math.PI / 180);
		y4 = radius * Math.sin(angle * Math.PI / 180);
		p.quadTo(x3, y3, x4, y4);
		p.lineTo(0, 0);
		p.closePath();
		AffineTransform at = new AffineTransform();
		at.translate(x, y);
		at.rotate(startAngle * Math.PI / 180);
		return new Path2D.Double(p, at);
	}

	public ShapeButton(int x, int y, int startAngle, int angle, int radius) {
		this.x = x;
		this.y = y;
		shape = makeArc(x, y, startAngle, angle, radius);
		setModel(new DefaultButtonModel());
		setVerticalAlignment(SwingConstants.CENTER);
		setVerticalTextPosition(SwingConstants.CENTER);
		setHorizontalAlignment(SwingConstants.CENTER);
		setHorizontalTextPosition(SwingConstants.CENTER);
		setBorder(BorderFactory.createEmptyBorder());
		setContentAreaFilled(false);
		setFocusPainted(false);
		this.setRolloverEnabled(true);
		setBackground(new Color(250, 250, 250));
	}

	public ShapeButton(int x, int y, int startAngle, int angle, int radius,
			double score) {
		this.x = x;
		this.score = score;
		this.y = y;
		shape = makeArc(x, y, startAngle, angle, radius);
		setModel(new DefaultButtonModel());
		setVerticalAlignment(SwingConstants.CENTER);
		setVerticalTextPosition(SwingConstants.CENTER);
		setHorizontalAlignment(SwingConstants.CENTER);
		setHorizontalTextPosition(SwingConstants.CENTER);
		setBorder(BorderFactory.createEmptyBorder());
		setContentAreaFilled(false);
		setFocusPainted(false);
		this.setRolloverEnabled(true);
		setBackground(new Color(250, 250, 250));

	}
	
	public ShapeButton(String text, int x, int y, int startAngle, int angle, int radius,
			double score) {
		this.tex = text;
		this.x = x;
		this.score = score;
		this.y = y;
		this.startAngle=startAngle;
		shape = makeArc(x, y, startAngle, angle, radius);
		setModel(new DefaultButtonModel());
		setVerticalAlignment(SwingConstants.CENTER);
		setVerticalTextPosition(SwingConstants.CENTER);
		setHorizontalAlignment(SwingConstants.CENTER);
		setHorizontalTextPosition(SwingConstants.CENTER);
		setBorder(BorderFactory.createEmptyBorder());
		setContentAreaFilled(false);
		setFocusPainted(false);
		this.setRolloverEnabled(true);
		setBackground(new Color(250, 250, 250));

	}


	public String getTex() {
		return tex;
	}

	public void setTex(String tex) {
		this.tex = tex;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
		this.updateUI();

	}

	public ShapeButton(Shape s) {
		shape = s;
		setModel(new DefaultButtonModel());
		setVerticalAlignment(SwingConstants.CENTER);
		setVerticalTextPosition(SwingConstants.CENTER);
		setHorizontalAlignment(SwingConstants.CENTER);
		setHorizontalTextPosition(SwingConstants.CENTER);
		setBorder(BorderFactory.createEmptyBorder());
		setContentAreaFilled(false);
		setFocusPainted(false);
		setBackground(new Color(250, 250, 250));
		this.setRolloverEnabled(true);
	}

	private void paintFocusAndRollover(Graphics2D g2, Color color) {
		g2.setPaint(new GradientPaint(0, 0, color, getWidth() - 1,
				getHeight() - 1, color.brighter(), true));
		g2.fill(shape);
	}

	private void paintGradient(Graphics2D g2, Color color) {
		int x1, x2, y1, y2;
		x2 = getWidth() - 1;
		y2 = getHeight() - 1;
		AffineTransform at = new AffineTransform();
		if (x != this.getHorizontalAlignment()) {
			at.translate(x * (1 - score / 100), 0);
			x2 = (int) (x * (1 - score / 100));
		}
		if (y != this.getVerticalAlignment()) {
			at.translate(0, y * (1 - score / 100));
			y2 = (int) (x * (1 - score / 100));
		}
		at.scale(score / 100, score / 100);
		Path2D.Double p = new Path2D.Double(shape, at);
		g2.setPaint(new GradientPaint(x, y, color, x2, y2, fc, true));
		g2.fill(p);
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		getScore();
		paintGradient(g2, ac);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_OFF);
		Font big = new Font( "SanSerif", Font.BOLD , 18 );
		g2.setColor(Color.WHITE);
		g2.setFont( big );
		// Get the current transform
		 AffineTransform saveAT = g2.getTransform();
		 // Perform transformation
		 AffineTransform at = new AffineTransform();
		 at.translate(getWidth()/2-20, getHeight()/2);
		// at.rotate( startAngle*Math.PI / 180 + 3*Math.PI / 4 );
		 g2.transform(at);
		 // Render
		 g2.drawString (tex, 0, 0 );
		 // Restore original transform
		 g2.setTransform(saveAT);
		 
		
	
		
		g2.setColor(getBackground());
		super.paintComponent(g2);
	}

	@Override
	protected void paintBorder(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		if (getModel().isArmed()) {
			g2.setColor(rc);
		} else if (getModel().isRollover()) {
			g2.setColor(fc);
		} else
			g2.setColor(getForeground());
		g2.draw(shape);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_OFF);
	}

	public Color getFc() {
		return fc;
	}

	public Color getAc() {
		return ac;
	}

	public Color getRc() {
		return rc;
	}

	@Override
	public boolean contains(int x, int y) {
		return shape.contains(x, y);
	}
	/*
	 * / Test
	 * 
	 * @Override public Dimension getPreferredSize() { Rectangle r =
	 * shape.getBounds(); return new Dimension(r.width, r.height); } /
	 */

	// */
}
