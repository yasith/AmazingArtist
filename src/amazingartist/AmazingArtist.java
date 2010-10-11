/*	Copyright (C) 2010  Yasith Vidanaarachchi (yasith.vidanaarachchi@gmail.com)

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package amazingartist;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

class JImagePanel extends JPanel {

	private BufferedImage img;
	int x, y;
	
	int trans = 80;
	int maskx = 0;
	int masky = 0;
	int parts = 5;

	public JImagePanel(BufferedImage image, int x, int y) {
		super();
		this.img = image;
		this.x = x;
		this.y = y;
	}

	private AlphaComposite makeComposite(float alpha) {
		int type = AlphaComposite.SRC_OVER;
		return (AlphaComposite.getInstance(type, alpha));
	}

	protected void drawGrid(Graphics g) {
		super.paintComponents(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(img, 0, 0, null);

		int w = img.getWidth();
		int h = img.getHeight();

		double size = w / parts;

		g2.setColor(Color.BLACK);

		// Drawing vertical lines
		
		for(int i = 0; size*i < w; i++){
			g2.draw(new Line2D.Double(size * i, 0.00, size * i, (double) h));	
		}

		// Drawing horizontal lines
		for(int i = 0; size * i < h; i++){
			g2.draw(new Line2D.Double(0.00, size * i, (double) w, size * i));	
		}
		
		// Drawing the mask
		Composite original = g2.getComposite();
		g2.setComposite(makeComposite(trans * 0.01f));
		
		for(int i = 0; size * i < w; i++){
			for(int j = 0; size * j < h; j++){
				double sx = size * i;
				double sy = size * j;
				double ex = Math.min((double)w, size*(i+1));
				double ey = Math.min((double)h, size*(j+1));
				
				Rectangle2D rect = new Rectangle2D.Double(sx, sy, size, size);
				
				g2.setPaint(Color.BLACK);
				g2.fill(rect);		
			}
		}
		
		g2.setComposite(original);
		
	}

	/**
	 * Sets the number of parts the image should be split into
	 * 
	 * @param val
	 *            +1 or -1 to increase or to decrease the number of parts
	 */
	protected void setParts(int val) {
		this.parts += val;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponents(g);
		this.drawGrid(g);
	}
}

public class AmazingArtist implements ActionListener {

	JImagePanel panel;

	JButton load = new JButton("Load image");
	JButton incr = new JButton("More squares");
	JButton decr = new JButton("Lesser squares");
	JButton tran = new JButton("Transparent");
	JButton opaq = new JButton("Opaque");


	public void displayImage(JFrame frame) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File("images/test.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		panel = new JImagePanel(img, 0, 0);

		frame.add(panel, BorderLayout.CENTER);
		frame.setBounds(0, 0, img.getWidth(), img.getHeight());
		frame.setVisible(true);

	}

	public void addButtons(JFrame container) {

		container.setLayout(new BorderLayout());

		JPanel toolbar = new JPanel();
		toolbar.setLayout(new GridLayout(1, 5));
		toolbar.add(load);
		toolbar.add(incr);
		toolbar.add(decr);
		toolbar.add(tran);
		toolbar.add(opaq);

		load.addActionListener(this);
		incr.addActionListener(this);
		decr.addActionListener(this);
		tran.addActionListener(this);
		opaq.addActionListener(this);
		
		container.add(toolbar, BorderLayout.NORTH);

	}

	void drawGrid(Graphics g) {
		panel.drawGrid(g);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		AmazingArtist artist = new AmazingArtist();
		JFrame container = new JFrame("AmazingArtist");
		
		container.addWindowListener(new WindowAdapter() {
			@SuppressWarnings("unused")
			public void WindowClosing(WindowEvent e){
				System.exit(0);
			}
		});
		
		artist.addButtons(container);
		artist.displayImage(container);
		artist.drawGrid(container.getRootPane().getGraphics());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(incr)){
			panel.setParts(1);
			panel.repaint();
		} else if(e.getSource().equals(decr)){
			panel.setParts(-1);
			panel.repaint();
		}
	}

}
