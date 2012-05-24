import processing.core.PVector;

public class Form {

	public PVector[] vektoren;
	private Main p;
	private boolean ones = true;

	public Form(Main p, PVector[] vektoren) {
		this.vektoren = vektoren;
		this.p = p;
	}

	/**
	 * Zeichnet eine Form an die x, y Position.
	 * 
	 * @param x Linker Rand der Box
	 * @param y Hälfte der Höhe der Box
	 * @param maxWidth
	 */
	public void draw(int x, int y, int maxWidth) {
		int scale = getScaleFactor(maxWidth);
		
		/* Wo müssen wir anfangen zu zeichnen? */
		x += (-1*getMaxNegWidth(maxWidth) * scale);
		y += (maxWidth / 2) - (-1*getMaxNegHeight(maxWidth) * scale);
		
		p.pushMatrix();
		p.translate(x, y, 0);

		float vorigesX = 0;
		float vorigesY = 0;

		//p.ellipse(0, 0, 30, 30);

		for (int i = 0; i < vektoren.length; i++) {
			float curX = vorigesX + (scale * vektoren[i].x);
			float curY = vorigesY + (-scale * vektoren[i].y);

			p.line(vorigesX, vorigesY, curX, curY);

			if (ones) {
				//System.out.println(vorigesX + ", " + vorigesY + ", " + curX
					//	+ ", " + curY);
			}

			vorigesX = curX;
			vorigesY = curY;
		}
		if (ones)
				ones = false;

		p.popMatrix();
	}

	

	private int getMaxNegHeight(int maxWidth) {
		int maximalerNegWertDenWidthAnnahm = 0;
		int width = 0;
		
		for (int i = 0; i < vektoren.length; i++) {
			width += vektoren[i].y;
			
			if (width < maximalerNegWertDenWidthAnnahm && width < 0)
				maximalerNegWertDenWidthAnnahm = width;
		}
		
		return maximalerNegWertDenWidthAnnahm;
	}

	private int getMaxPosHeight(int maxWidth) {
		int maximalerPosWertDenWidthAnnahm = 0;
		int width = 0;
		
		for (int i = 0; i < vektoren.length; i++) {
			width += vektoren[i].y;
			if (width > maximalerPosWertDenWidthAnnahm && width > 0)
				maximalerPosWertDenWidthAnnahm = width;
		}	
		
		return maximalerPosWertDenWidthAnnahm;
	}


	private int getScaleFactor(int maxWidth) {
		int width = 0;
		int maximalerPosWertDenWidthAnnahm = getMaxPosWidth(maxWidth);
		int maximalerNegWertDenWidthAnnahm = getMaxNegWidth(maxWidth);
		
		int maximalerPosWertDenHeightAnnahm = getMaxPosHeight(maxWidth);
		int maximalerNegWertDenHeightAnnahm = getMaxNegHeight(maxWidth);
		
		/* Skalierung gerade passend w�hlen */
		int div = (-1*maximalerNegWertDenWidthAnnahm + maximalerPosWertDenWidthAnnahm);
		int div2 = (-1*maximalerNegWertDenHeightAnnahm + maximalerPosWertDenHeightAnnahm);
		
		if (div == 0)
			div = 1;
		
		if (div2 == 0)
			div2 = 1;
		
		int scale = maxWidth / div;
		int scale2 = maxWidth / div2;
		
		if (scale < scale2)
			return scale;
		else 
			return scale2;
	}

	private int getMaxNegWidth(int maxWidth) {
		int maximalerNegWertDenWidthAnnahm = 0;
		int width = 0;
		
		for (int i = 0; i < vektoren.length; i++) {
			width += vektoren[i].x;
			
			if (width < maximalerNegWertDenWidthAnnahm && width < 0)
				maximalerNegWertDenWidthAnnahm = width;
		}
		
		return maximalerNegWertDenWidthAnnahm;
	}

	private int getMaxPosWidth(int maxWidth) {
		int maximalerPosWertDenWidthAnnahm = 0;
		int width = 0;
		
		//if (vektoren[i] != null)
		
		for (int i = 0; i < vektoren.length; i++) {
			width += vektoren[i].x;
			if (width > maximalerPosWertDenWidthAnnahm && width > 0)
				maximalerPosWertDenWidthAnnahm = width;
		}	
		
		return maximalerPosWertDenWidthAnnahm;
	}

	public void printIt() {
		ones = true;
	}

}
