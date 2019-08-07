import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class FileRead {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		File imageFile = new File("D:\\training\\AccessibilityAssignment\\lg_logo.png");
		BufferedImage image = ImageIO.read(imageFile);
		BufferedImage newImage = rotate(image);
		
		File outputFile = new File("D:\\training\\AccessibilityAssignment\\hello.bmp");
		ImageIO.write(newImage, "BMP", outputFile);
		System.out.println("hello");

		
	}
	
	
	public static BufferedImage rotate(BufferedImage img) {  
	    int w = img.getWidth();  
	    int h = img.getHeight();
	    BufferedImage newImage = new BufferedImage(w, h, img.getType());
	      Graphics2D g2 = newImage.createGraphics();
	      g2.rotate(Math.toRadians(90), w/2, h/2);  
	      g2.drawImage(img,null,0,0);
	      return newImage;  
	  }

}
