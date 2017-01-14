/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mandelbrotset;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.PixelWriter;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author Ashley
 */
public class MandelbrotSet extends Application {

    int width = 1980;
    int height = 1200;
    int res = 32;
    
    @Override
    public void start(final Stage primaryStage) {
        GridPane root = new GridPane();
        root.setPadding(new Insets(10));
        root.setHgap(5);
        root.setVgap(5);
        
        final Label widthLbl = new Label("Width:");
        GridPane.setConstraints(widthLbl, 0, 0);
        
        final TextField widthTxtFld = new TextField(String.valueOf(width));
        GridPane.setConstraints(widthTxtFld, 1, 0);
        
        final Label heightLbl =  new Label("Height:");
        GridPane.setConstraints(heightLbl, 0, 1);
        
        final TextField heightTxtFld = new TextField(String.valueOf(height));
        GridPane.setConstraints(heightTxtFld, 1, 1);
        
        final Label resLbl = new Label("Resolution");
        GridPane.setConstraints(resLbl, 0, 2);
        
        final TextField resTxtFld = new TextField(String.valueOf(res));
        GridPane.setConstraints(resTxtFld, 1, 2);
        
        final Button confirmBtn = new Button("Confirm");
        GridPane.setConstraints(confirmBtn, 0, 3, 1, 2);
        
        confirmBtn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent e) {
                primaryStage.hide();                
                width = Integer.parseInt(widthTxtFld.getText());
                height = Integer.parseInt(heightTxtFld.getText());
                res = Integer.parseInt(resTxtFld.getText());
                
                Canvas displayCanvas = new Canvas(width, height);
                StackPane displayRoot = new StackPane(displayCanvas);
                displayRoot.setAlignment(Pos.CENTER);
                Scene displayScene = new Scene(displayRoot, Color.BLACK);
                Stage displayStage = new Stage();
                displayStage.setScene(displayScene);
               
                drawImage(width, height, res, displayCanvas.getGraphicsContext2D().getPixelWriter());
                
                displayStage.setMaximized(true);
                displayStage.show();
            }
        });
        
        root.getChildren().addAll(widthLbl, widthTxtFld, heightLbl, heightTxtFld, resLbl, resTxtFld, confirmBtn);
        
        Scene scene = new Scene(root);
        
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private static void drawImage(int width, int height, int resolution, PixelWriter pixelWriter) {
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
                pixelWriter.setColor(Px, Py, getColor(iteration, maxIteration));
            }
        }
    }
    
    private static Color getColor(int iteration, int maxIteration) {
        Color c = Color.WHITE;
        double oneThird = maxIteration / 3.0;
        if (iteration < oneThird) {
            double calc = iteration / oneThird;
            c = Color.color(0, 0, calc);
        } else if (iteration < oneThird * 2.0) {
            double calc = (iteration - oneThird) / oneThird;
            c = Color.color(0, calc, 1);
        } else if (iteration < maxIteration) {
            double calc = (iteration - oneThird * 2) / oneThird;
            c = Color.color(calc, 1, 1);
        } else if (iteration == maxIteration) {
            c = Color.BLACK;
        } else {
            System.err.println("ERROR: Invalid value for iteration");
        }
        return c;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
