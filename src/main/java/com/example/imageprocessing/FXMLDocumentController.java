package com.example.imageprocessing;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Fatiha
 */
public class FXMLDocumentController implements Initializable {
    static final int largeur = 256;
    static final int hauteur = 200;
    static final int[] rouge = new int[256];
    static final int[] vert = new int[256];
    static final int[] bleu = new int[256];
    static int right = 0;
    static int left = 0;
    static BufferedImage nouvelleImage;
    static BufferedImage monimage;
    static BufferedImage image, histogramme;
    static Graphics2D dessin;
    static FileChooser chooser;
    static String imagepath;
    static int i = 1;
    static Image img;
    /*
File fileSelected = new File ("C:/Users/Fatiha/Documents/NetBeansProjects/image/src/image/img2.jpg");
File outputfile =new File ("C:/Users/Fatiha/Documents/NetBeansProjects/image/src/image/image2.jpg");
*/
    //static File fileSelected = new File("E:/women.JPG");
    static File fileSelected ;
    static File outputfile ;
    @FXML
    private TextField b;
    @FXML
    private javafx.scene.control.Button blanc;
    @FXML
    private javafx.scene.control.Button gris;
    @FXML
    private ImageView imageViewSource;
    @FXML
    private javafx.scene.control.Button ajouterImage;
    @FXML
    private javafx.scene.control.Button enColeur;
    @FXML
    private javafx.scene.control.Button zoomAvant;
    @FXML
    private javafx.scene.control.Button rotationD;
    @FXML
    private javafx.scene.control.Button next;
    @FXML
    private javafx.scene.control.Button retationG;
    @FXML
    private javafx.scene.control.Button zoomArire;
    @FXML
    private ImageView i1;
    @FXML
    private TextField l;
    @FXML
    private Pane p;

    //====================================================================================================
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
//====================================================================================================	    
    @FXML
    public void ajouterImage(){
        i++;
        chooser = new FileChooser();
        chooser.setTitle("Open File");
        fileSelected = chooser.showOpenDialog(new Stage());
        // limit chooser options to image files
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.bmp", "*.png", "*.jpg", "*.gif"));
        if (fileSelected != null) {
            try {
                monimage = ImageIO.read(fileSelected);
                imagepath = fileSelected.toURI().toURL().toString();
                System.out.println("imagepath = " + imagepath);
                outputfile = new File("Image " + i + ".jpg");
                System.out.println("file:" + imagepath);
                img = new Image(imagepath);
                imageViewSource.setImage(img);
                nouvelleImage = new BufferedImage(monimage.getWidth(), monimage.getHeight(), BufferedImage.SCALE_DEFAULT);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {

                e.printStackTrace();
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Please Select a File");
            /*alert.setContentText("You didn't select a file!");*/
            alert.showAndWait();
        }
        right = 0;
        left = 0;
    }
    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {
        try {

            BufferedImage imag1 = ImageIO.read(fileSelected);
            BufferedImage imag2 = new BufferedImage(imag1.getWidth(), imag1.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
            int w = imag1.getWidth();
            int h = imag1.getHeight();
            for (int j = 0; j < h; j++) {
                for (int i = 0; i < w; i++) {
                    Color c = new Color(imag1.getRGB(i, j));
                    imag2.setRGB(i, j, (c.getRed() + 15) * 65536 + c.getGreen() * 256 + c.getBlue());
                }
            }
            ImageIO.write(imag2, "jpg", outputfile);
            i1.setImage(new Image(outputfile.toURI().toURL().toString()));

        } catch (IOException ex) {
            System.out.printf("erreeuurr");
        }

    }

    //====================================================================================================
    @FXML
    private void handleButtonAction2(ActionEvent event) throws IOException {
        try {
            BufferedImage imag1 = ImageIO.read(fileSelected);
            BufferedImage imag2 = new BufferedImage(imag1.getWidth(), imag1.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
            int w = imag1.getWidth();
            int h = imag1.getHeight();
            for (int j = 0; j < h; j++) {
                for (int i = 0; i < w; i++) {
                    Color c = new Color(imag1.getRGB(i, j));
                    imag2.setRGB(i, j, (c.getRed() + 15) * 65536 + c.getGreen() * 256 + c.getBlue());
                }
            }
            ImageIO.write(imag2, "jpg", outputfile);
            i1.setImage(new Image(outputfile.toURI().toURL().toString()));

        } catch (IOException ex) {
            System.out.printf("erreeuurr");
        }

    }

    //====================================================================================================
    @FXML
    private void handleButtonAction3(ActionEvent event) throws IOException {

        try {
            BufferedImage imag1 = ImageIO.read(fileSelected);
            BufferedImage imag2 = new BufferedImage(imag1.getWidth(), imag1.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
            int w = imag1.getWidth();
            int h = imag1.getHeight();
            for (int j = 0; j < h; j++) {
                for (int i = 0; i < w; i++) {
                    Color c = new Color(imag1.getRGB(i, j));
                    imag2.setRGB(i, j, (c.getRed() + 15) * 65536 + c.getGreen() * 256 + c.getBlue());
                }
            }
            ImageIO.write(imag2, "jpg", outputfile);
            i1.setImage(new Image(outputfile.toURI().toURL().toString()));

        } catch (IOException ex) {
            System.out.printf("erreeuurr");
        }

    }

    //====================================================================================================
    @FXML
    private void handleButtonAction4(ActionEvent event) throws IOException {
        try {

            BufferedImage imag1 = ImageIO.read(fileSelected);
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

            int ll = Integer.valueOf(l.getText());
            int a = 100 / ll;

            BufferedImage imag2 = new BufferedImage(imag1.getWidth() * a, imag1.getHeight() * a, BufferedImage.TYPE_3BYTE_BGR);
            int w = imag1.getWidth();
            int h = imag1.getHeight();
            for (int j = 0; j < h; j++) {
                for (int i = 0; i < w; i++) {
                    Color c = new Color(imag1.getRGB(i, j));
                    imag2.setRGB(i * a, j * a, (c.getRed() + 15) * 65536 + c.getGreen() * 256 + c.getBlue());
                }
            }
            ImageIO.write(imag2, "png", outputfile);
            i1.setImage(new Image(outputfile.toURI().toURL().toString()));

        } catch (IOException ex) {
            System.out.printf("erreeuurr");
        }

    }

    //******************************************************************************
    @FXML
    private void handleButtonAction5(ActionEvent event) throws IOException {
        try {

            BufferedImage imag1 = ImageIO.read(fileSelected);
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

            int ll = Integer.valueOf(l.getText());
            int a = 100 / ll;

            BufferedImage imag2 = new BufferedImage(imag1.getWidth(), imag1.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
            int w = imag1.getWidth();
            int h = imag1.getHeight();
            for (int j = 0; j < h; j++) {
                for (int i = 0; i < w; i++) {
                    Color c = new Color(imag1.getRGB(i, j));
                    imag2.setRGB(i / a, j / a, (c.getRed() + 15) * 65536 + c.getGreen() * 256 + c.getBlue());
                }
            }
            ImageIO.write(imag2, "png", outputfile);
            i1.setImage(new Image(outputfile.toURI().toURL().toString()));

        } catch (IOException ex) {
            System.out.printf("erreeuurr");
        }

    }

    //********************************************************************************

    @FXML
    private void handleButtonAction6(ActionEvent event) throws IOException {
        try {

            monimage = ImageIO.read(fileSelected);
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));


            BufferedImage imag2 = new BufferedImage(monimage.getWidth(), monimage.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
            int w = monimage.getWidth();
            int h = monimage.getHeight();
            for (int j = 0; j < h; j++) {
                for (int i = 0; i < w; i++) {
                    Color c = new Color(monimage.getRGB(i, j));
                    imag2.setRGB(i, j, (c.getRed()) * 65536 + c.getGreen() * 256 + c.getBlue());
                }
                if (rotationD.isArmed())
                    rotate_To_Right();
                else
                    rotate_To_Left();
            }


            ImageIO.write(imag2, "png", outputfile);
            i1.setImage(new Image(outputfile.toURI().toURL().toString()));


        } catch (IOException ex) {
            System.out.printf("erreeuurr");
        }
    }

    //=======================================================================================================================================
    @FXML
    private void rotate_To_Right() {
        right += +45;
        i1.setRotate(right);

    }

    //=======================================================================================================================================
    @FXML
    public void rotate_To_Left() {
        left += -45;
        i1.setRotate(left);
    }

    //********************************************************************************************************
    @FXML
    private void histogramme(ActionEvent event) throws IOException {
        monimage = ImageIO.read(fileSelected);
        Histogramme histogramme = new Histogramme(monimage);
        //histogramme.setBorder(BorderFactory.createLineBorder(Color.black));
        JFrame frame = new JFrame("Histogramme");
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.setSize(400, 400);
        frame.add(panel.add(histogramme));
        frame.setSize(500, 500);

        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    //*****************************************************************************************************
    @FXML

    private void histogramme2(ActionEvent event) throws IOException {
        monimage = ImageIO.read(fileSelected);
        Histogramme2 histogramme = new Histogramme2(monimage);
        //histogramme.setBorder(BorderFactory.createLineBorder(Color.black));
        JFrame frame = new JFrame("Histogramme");
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.setSize(400, 400);
        frame.add(panel.add(histogramme));
        frame.setSize(500, 500);

        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }


    //***************************************************************************************************

    @FXML
    private void flou(ActionEvent event) throws IOException {
        int l = 0, h = 0;

        BufferedImage im = null, imfl = null;
        try {
            im = ImageIO.read(fileSelected);

            l = im.getWidth();
            h = im.getHeight();
            imfl = new BufferedImage(l, h, BufferedImage.TYPE_INT_RGB);
            for (int j = 1; j < h - 1; j++)
                for (int i = 1; i < l - 1; i++) {
                    int r = 0;
                    int g = 0;
                    int b = 0;
                    for (int m = j - 1; m <= j + 1; m++)
                        for (int k = i - 1; k <= i + 1; k++) {
                            Color c = new
                                    Color(im.getRGB(k, m));
                            r += c.getRed();
                            g += c.getGreen();
                            b += c.getBlue();
                        }
                    imfl.setRGB(i, j, new Color(r / 9, g / 9, b / 9).getRGB());
                }

            ImageIO.write(imfl, "jpg", outputfile);
            i1.setImage(new Image(outputfile.toURI().toURL().toString()));
        } catch (Exception e) {
            System.out.println("faux !!!");
        }
    }
    //*************************************************************************************

    @FXML
    private void sombre(ActionEvent event) throws IOException {
        try {

            BufferedImage imgBrillant = new BufferedImage(monimage.getWidth(), monimage.getHeight(), BufferedImage.TYPE_INT_RGB);
            RescaleOp brillance = new RescaleOp(-1.2f, 100, null);
            brillance.filter(monimage, imgBrillant);
         
            ImageIO.write(monimage, "jpg", outputfile);
            i1.setImage(new Image(outputfile.toURI().toURL().toString()));
        } catch (Exception e) {
            System.out.println("faux !!!");
        }
    }

    @FXML
    private void claire(ActionEvent event) throws IOException {
        try {
            monimage = ImageIO.read(fileSelected);
            BufferedImage imgBrillant = new BufferedImage(monimage.getWidth(), monimage.getHeight(), BufferedImage.TYPE_INT_RGB);
            RescaleOp brillance = new RescaleOp(1.2f, 0, null);
            brillance.filter(monimage, imgBrillant);
            monimage = imgBrillant;
            ImageIO.write(imgBrillant, "jpg", outputfile);
            i1.setImage(new Image(outputfile.toURI().toURL().toString()));
        } catch (Exception e) {
            System.out.println("faux !!!");
        }
    }

    @FXML
    private void Tresclaire(ActionEvent event) throws IOException {
        try {
            BufferedImage tempo = monimage;
            BufferedImage imgBrillant = new BufferedImage(tempo.getWidth(), tempo.getHeight(), BufferedImage.TYPE_INT_RGB);
            RescaleOp brillance = new RescaleOp(1.2f, 100, null);
            brillance.filter(tempo, imgBrillant);
            tempo = imgBrillant;
            ImageIO.write(imgBrillant, "jpg", outputfile);
            i1.setImage(new Image(outputfile.toURI().toURL().toString()));
        } catch (Exception e) {
            System.out.println("faux !!!");
        }
    }


}
    
    
    


