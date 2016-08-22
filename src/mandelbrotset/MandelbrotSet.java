/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mandelbrotset;

import java.io.File;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
 *
 * @author Ashley
 */
public class MandelbrotSet extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        int width = 1000;
        int height = 1000;
        
        for (int i = 1; i < 1000; i *= 2) {
            WritableImage wim = drawSet(width, height, i);

            File file = new File("Mandelbrot Set " + width + "x" + height + " " + i + ".png");
            try {
                System.out.println("Writing file: " + file.getName());
                ImageIO.write(SwingFXUtils.fromFXImage(wim, null), "png", file);
            } catch (Exception s) {
                System.out.println(s);
            }
        }
    }

    private static WritableImage drawSet(int width, int height, int resolution) {
        WritableImage wim = new WritableImage(width, height);
        PixelWriter pw = wim.getPixelWriter();
        double xScale = width / 3.5;
        double yScale = height / 2;
        for (int Px = 0; Px < width; Px++) {
            for (int Py = 0; Py < height; Py++) {
                double x0 = Px / xScale - 2.5;
                double y0 = Py / yScale - 1;
                double x = 0.0;
                double y = 0.0;
                int iteration = 0;
                int maxIteration = resolution;
                while (x*x + y*y < 2*2 && iteration < maxIteration) {
                    double xTemp = x*x - y*y + x0;
                    y = 2*x*y + y0;
                    x = xTemp;
                    iteration++;
                }
                Color c = Color.WHITE;
                double oneThird = maxIteration / 3.0;
                if (iteration < oneThird) {
                    c = Color.color(iteration / oneThird, 0, 0);
                } else if (iteration < oneThird * 2.0) {
                    c = Color.color(0, (iteration - oneThird) / oneThird, 0);
                } else if (iteration < maxIteration) {
                    c = Color.color(0, 0, (iteration - oneThird * 2) / oneThird);
                } else if (iteration == maxIteration) {
                    c = Color.BLACK;
                } else {
                    System.err.println("ERROR: Invalid value for iteration");
                }
                pw.setColor(Px, Py, c);
            }
        }
        return wim;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
