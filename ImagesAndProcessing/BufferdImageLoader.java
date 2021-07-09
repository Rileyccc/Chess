package ImagesAndProcessing;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class BufferdImageLoader {
	
	private BufferedImage image;
	
	public void setImage(String path) throws IOException{
		 this.image = ImageIO.read(getClass().getResource(path));
		
	}
	public BufferedImage getImage() {
		return image;
	}
 	

}
