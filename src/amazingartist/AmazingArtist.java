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

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class AmazingArtist {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame container = new JFrame("AmazingArtist");
		JPanel panel = (JPanel) container.getContentPane();
		
		panel.setPreferredSize(new Dimension(800, 600));
		panel.setLayout( new GridLayout(2, 0));
		
	}

}