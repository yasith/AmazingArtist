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

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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

class LoadedImage extends Component {
	
	BufferedImage image;
	
	public void paint(Graphics g) {
		g.drawImage(image, 0, 0, null);
	}
	
	public LoadedImage(String file) {
		try{
			image = ImageIO.read(new File(file));
		}catch (IOException e){
			// TODO 
		}
	}
	
	public Dimension getPreferredSize() {
		if(image == null) {
			return new Dimension(100, 100);
		} else {
			return new Dimension(image.getWidth(), image.getHeight());
		}
	}
}

public class AmazingArtist{
	
	Canvas canvas = new Canvas();
	BufferedImage img;
	String file = "images/test.jpg";
	private BufferStrategy strategy;
	
	
	public AmazingArtist(){
		JFrame container = new JFrame("AmazingArtist");
		
		container.setLayout(new BorderLayout());
		
		JButton load = new JButton("Load image");
		JButton incr = new JButton("More squares");
		JButton decr = new JButton("Lesser squares");
		JButton tran = new JButton("Transparent");
		JButton opaq = new JButton("Opaque");
		
		JPanel toolbar = new JPanel();
		toolbar.setLayout(new GridLayout(1, 5));
		toolbar.add(load);
		toolbar.add(incr);
		toolbar.add(decr);
		toolbar.add(tran);
		toolbar.add(opaq);
		
		container.add(toolbar, BorderLayout.NORTH);
		
		//TODO decent loading of the image
		
		try {
			img = ImageIO.read(new File(file));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Dimension PrefDim = new Dimension(img.getWidth(), img.getHeight());
		
		System.out.println(PrefDim.width + "x" + PrefDim.height);
		
		JPanel panel = new JPanel();
		panel.setPreferredSize(PrefDim);
		panel.setLayout(null);
		//panel.add(canvas);
		
		canvas.setPreferredSize(PrefDim);
		canvas.setIgnoreRepaint(true);
		
		container.add(canvas, BorderLayout.CENTER);
		container.pack();
		container.setResizable(false);
		container.setVisible(true);
		
		container.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		});
		
		
		// TODO add a key listener
		
		canvas.requestFocus();
		
		canvas.createBufferStrategy(2);
		strategy = canvas.getBufferStrategy();
		
		// Display the default image
		Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
		g.drawImage(img, 0, 0, null);
		g.dispose();
		strategy.show();
	}
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		AmazingArtist artist = new AmazingArtist();
	}

}
