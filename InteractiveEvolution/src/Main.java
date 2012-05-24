import processing.core.*;
import processing.xml.*;

import java.applet.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.text.*;
import java.util.*;
import java.util.zip.*;
import java.util.regex.*;

public class Main extends PApplet {
	private int BLACK = color(0, 0, 0);
	private int WHITE = color(255, 255, 255);
	private int RED = color(255, 0, 0);
	private int LIGHT_BLUE = color(5, 52, 98);
	private int LIGHT_RED = color(100, 0, 0, 5);
	
	private final int BUFFER = 20;
	private int currGeneration = 0;
	private int fitOnOnePage = 5;
	private int startDrawing = 0;

	Form forms[][] = new Form[BUFFER][8];
	int selecteds[][] = new int[BUFFER][2];
	
	int selected0 = -1, selected1 = -1;

	public void setup() {
		size(1440, 900, P3D);

		PVector[] labyrinth = {
				new PVector(5, 0), new PVector(0, -5), 
				new PVector(-5, 0), new PVector(0, 4),
				
				new PVector(4, 0), new PVector(0, -4), 
				new PVector(-4, 0), new PVector(0, 3)
		};
		
		
		PVector[] zigzag = {
				new PVector(1, -1), new PVector(-1, -1),
				new PVector(1, -1), new PVector(-1, -1),
				new PVector(1, -1), new PVector(-1, -1)
		};
		
		/* create initial random forms */
		for (int i = 0; i < 8; i++) {
			forms[currGeneration][i] = new Form(this, permutiere(labyrinth, zigzag));
		}
	}

	public void draw() {
		background(color(2, 29, 56));
		
		strokeWeight(1);
		stroke(WHITE);
		
		x = xOriginal;
		y = yOriginal;
		
		
		//for (int gen = 0; gen < BUFFER; gen++) {
		for (int gen = startDrawing; gen < BUFFER; gen++) {
			if (forms[gen][0] != null) {
				for (int i = 0; i < 8; i++) {
					drawForm(forms[gen][i]);
					if (currGeneration > gen) drawRedBorders(gen - startDrawing);
				}
				x = xOriginal;
				y += yMargin + xWidth;
			}
		}
		
		
		if (selected0 >= 0) drawRedBorder(selected0);
		if (selected1 >= 0) drawRedBorder(selected1);
	
		/*

		for (int j = 0; j <= 4; j++) {
			if (j > 0) 
				y += xWidth + yMargin;
			x = xOriginal;
			
			if (allPermutations.length > j) {
				for (int i = 0; i < allPermutations[j].length; i++) {
					if (allPermutations[j][i] != null)
						drawForm(allPermutations[j][i]);
				}
			}
		}
		*/

		// allPermutations[0][1].draw( 200, 200);
		// allPermutations[0][0].draw( 400, 200);
		// allPermutations[0][2].draw( 300, 500);
	}
	
	private void drawRedBorders(int gen) {
		noSmooth();
		fill(LIGHT_RED);
		stroke(RED);
		int xStart0 = (xWidth + xMargin) * selecteds[gen][0];
		int xStart1 = (xWidth + xMargin) * selecteds[gen][1];
		int yDraw = yOriginal + (gen * (yMargin + xWidth));
		
		rect((xOriginal + xStart0) - 5, yDraw - 5, xWidth + 10, xWidth + 10);
		rect((xOriginal + xStart1) - 5, yDraw - 5, xWidth + 10, xWidth + 10);
	}

	public void mouseClicked() {
		// wo fällt der Click rein?
		int foo = (mouseX - xOriginal);
		int selected = -1;
		while (foo > 0) {
			foo -= (xMargin + xWidth);
			selected++;
		}
		
		if (selected < 0) selected  = 0;
		
		if (selected0 >= 0) selected1 = selected;
		if (selected0 == -1) selected0 = selected;
		
		if (selected0 >= 0 && selected1 >= 0) {
			selecteds[currGeneration][0] = selected0;
			selecteds[currGeneration][1] = selected1;
			newPermutation();
		}
	}

	private void newPermutation() {
		PVector[] f1 = forms[currGeneration][selected0].vektoren;
		PVector[] f2 = forms[currGeneration][selected1].vektoren;
		
		// permutiere die 2 ausgewählten acht mal
		int newG = currGeneration + 1;
		for (int i = 0; i < 8; i++) {
			forms[newG][i] = new Form(this, permutiere(f1, f2));
		}
		
		selected0 = -1;
		selected1 = -1;
		currGeneration++;
		
		// does it still fit to one page?
		if (currGeneration > fitOnOnePage)
			startDrawing++;
	}

	private PVector[] permutiere(PVector[] form1, PVector[] form2) {
		int longer = (form1.length > form2.length) ? form1.length
				: form2.length;
		PVector[] res = new PVector[form1.length];

		/* erste Form erstmal komplett übernhmen */
		for (int i = 0; i < form1.length; i++)
			res[i] = new PVector(form1[i].x, form1[i].y);

		/* anschließend Permutation mit zweiter erzeugen */
		for (int i = 0; i < form2.length; i++) {
			if (i > form1.length - 1)
				break;
			
			double r = Math.random();
			System.out.println(r);
			if (r <= 0.5d)
				res[i] = form2[i];
		}
		
		/* und jetzt noch randommäßig mutieren */
		double r = Math.random();
		int high = 4;
		int low = 1;
		for (int i = 0; i < res.length; i++) {
			if (r <= 0.2d)
				res[i] = new PVector((int) Math.random() * (high - low) + low,  
									 (int) Math.random() * (high - low) + low);
		}

		return res;
	}

	private void drawRedBorder(int selected) {
		noSmooth();
		noFill();
		stroke(RED);
		int xStart = (xWidth + xMargin) * selected;
		int yDraw = yOriginal + ((currGeneration - startDrawing) * (yMargin + xWidth));
		
		rect((xOriginal + xStart) - 5, yDraw - 5, xWidth + 10, xWidth + 10);
	}


	private int xOriginal = 25;
	private int x = xOriginal;
	private int xWidth = 100;
	private int xMargin = 25;
	private int yMargin = 25;
	private int yOriginal = 25;
	private int y = yOriginal;

	private void drawForm(Form form) {
		/*
		noSmooth();
		fill(LIGHT_BLUE);
		rect(x, y, xWidth, xWidth);
		*/
		
		smooth();
		stroke(WHITE);
		form.draw(x, y + (xWidth / 2), xWidth);
		x += xWidth + xMargin;
	}

}
