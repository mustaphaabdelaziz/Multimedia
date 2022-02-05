package com.example.imageprocessing;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

public class ImageProcessing implements Initializable {

    static Image image;
    static File fileSelected;
    static File outputfile;
    static FileChooser chooser;
    static BufferedImage source_Image;
    static BufferedImage distination_Image;
    static BufferedImage temporary_Image;
    static String imagepath;
    static int right = 0;
    static int left = 0;
    static int i = 1;
    static int zoomINLevel = 1;
    static int zoomOutLevel = 1;
    static int newImageWidth = 1;
    static int newImageHeight = 1;
    static int w;
    static int h;
    static boolean isGray = false;
    AffineTransform tx;
    AffineTransformOp op;
    JPanel panel;
    @FXML
    private StackPane stackPane;
    @FXML
    private AnchorPane root;
    @FXML
    private JFXButton close;
    @FXML
    private ImageView imageviewer;
    @FXML
    private JFXButton browse;
    @FXML
    private Label ImageProcessing;
    @FXML
    private JFXButton save;
    @FXML
    private JFXButton Zoom_out;
    @FXML
    private JFXButton Zoom_In;
    @FXML
    private JFXRadioButton gray;
    @FXML
    private JFXRadioButton color;
    @FXML
    private JFXRadioButton setdefault;
    @FXML
    private JFXRadioButton NoirEtBlanc;
    @FXML
    private JFXRadioButton flou;
    @FXML
    private JFXRadioButton Lighten;
    @FXML
    private JFXRadioButton Darken;
    @FXML
    private JFXButton Rotation_Left;
    @FXML
    private JFXButton Rotation_Right;
    @FXML
    private JFXButton histogramme;

    //=======================================================================================================================================
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ToggleGroup tg = new ToggleGroup();
        gray.setToggleGroup(tg);
        color.setToggleGroup(tg);
        setdefault.setToggleGroup(tg);
        NoirEtBlanc.setToggleGroup(tg);
        Darken.setToggleGroup(tg);
        Lighten.setToggleGroup(tg);
        flou.setToggleGroup(tg);
        tg.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {

            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                RadioButton rb = (RadioButton) tg.getSelectedToggle();
                try {
                    if (rb != null && rb.equals(color)) {
                        System.out.println("Color");
                        TypeColor(BufferedImage.TYPE_INT_RGB);
                    }
                    if (rb != null && rb.equals(gray)) {
                        System.out.println("gray");
                        isGray = true;
                        TypeColor(BufferedImage.TYPE_BYTE_GRAY);

                    }
                    if (rb != null && rb.equals(NoirEtBlanc)) {
                        System.out.println("Noir Et Blanc");
                        TypeColor(BufferedImage.TYPE_BYTE_BINARY);
                        isGray = false;
                    }
                    if (rb != null && rb.equals(flou)) {
                        System.out.println("Flou");
                        flou();
                        isGray = false;
                    }
                    if (rb != null && rb.equals(Lighten)) {
                        System.out.println("Lighten");
                        imageEclaircie();
                        isGray = false;
                    }
                    if (rb != null && rb.equals(Darken)) {
                        System.out.println("Darken");
                        imageSombre();
                        isGray = false;
                    }

                    ImageIO.write(distination_Image, "jpg", outputfile);
                    temporary_Image = ImageIO.read(outputfile);
                    imageviewer.setImage(new Image(outputfile.toURI().toURL().toString()));
                    if (rb != null && rb.equals(setdefault)) {
                        System.out.println("setdefault");
                        imageviewer.setImage(new Image(imagepath));
                        temporary_Image = source_Image;
                        w = source_Image.getWidth();
                        h = source_Image.getHeight();
                        isGray = false;
                        right = 0;
                        left = 0;
                        i = 1;
                        zoomINLevel = 1;
                        zoomOutLevel = 1;
                        newImageWidth = 1;
                        newImageHeight = 1;

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });


    }


    //=======================================================================================================================================
    public void clsoe() {
        System.exit(0);
        this.clsoe();
    }

    //=======================================================================================================================================
    @FXML
    public void chooseFile(ActionEvent actionEvent) {
        i++;
        chooser = new FileChooser();
        chooser.setTitle("Open File");
        fileSelected = chooser.showOpenDialog(new Stage());
        // limit chooser options to image files
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.bmp", "*.png", "*.jpg", "*.gif"));
        if (fileSelected != null) {
            gray.setDisable(false);
            color.setDisable(false);
            setdefault.setDisable(false);
            NoirEtBlanc.setDisable(false);
            Lighten.setDisable(false);
            Darken.setDisable(false);
            flou.setDisable(false);
            Rotation_Left.setDisable(false);
            Rotation_Right.setDisable(false);
            save.setDisable(false);
            histogramme.setDisable(false);
            Zoom_In.setDisable(false);
            Zoom_out.setDisable(false);


            try {
                source_Image = ImageIO.read(fileSelected);
                temporary_Image = source_Image;
                imagepath = fileSelected.toURI().toURL().toString();
                System.out.println("imagepath = " + imagepath);
                outputfile = new File("Image " + i + ".jpg");
                w = source_Image.getWidth();
                h = source_Image.getHeight();
                System.out.println("file:" + imagepath);
                image = new Image(imagepath);
                imageviewer.setImage(image);
                distination_Image = new BufferedImage(w, h, BufferedImage.SCALE_DEFAULT);
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

    //=======================================================================================================================================
    @FXML
    public void rotate_To_Right() {
        try {
            right += 90;
            rotation(right);
            setdefault.setSelected(false);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //=======================================================================================================================================
    @FXML
    public void rotate_To_Left() {
        try {
            left += -90;
            rotation(left);
            setdefault.setSelected(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //=======================================================================================================================================
    public void rotation(int rotation) throws IOException {
        //convert the Angle of rotation to double rotation value
        TypeColor(BufferedImage.SCALE_DEFAULT);
        double rotationRequired = Math.toRadians(rotation);
        tx = new AffineTransform();
        tx.rotate(rotationRequired, distination_Image.getWidth() / 2, distination_Image.getHeight() / 2);
        op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        distination_Image = op.filter(distination_Image, null);
        ImageIO.write(distination_Image, "jpg", outputfile);
        imageviewer.setImage(new Image(outputfile.toURI().toURL().toString()));

    }

    //=======================================================================================================================================
    @FXML
    public void save() {
        try {
            ImageIO.write(distination_Image, "jpg", outputfile);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //=======================================================================================================================================
    public void TypeColor(int TYPE) {
        distination_Image = new BufferedImage(w, h, TYPE);
        for (int j = 0; j < h; j++) {
            for (int i = 0; i < w; i++) {
                Color c = new Color(source_Image.getRGB(i, j));
                distination_Image.setRGB(i, j, (c.getRed() + 15) * 65536 + c.getGreen() * 256 + c.getBlue());
            }

        } // fin des boucles for
    }

    //=======================================================================================================================================
    @FXML
    public void zoomIn() throws IOException {
        zoomINLevel++;
        newImageWidth = w * zoomINLevel;
        newImageHeight = h * zoomINLevel;
        distination_Image = new BufferedImage(newImageWidth, newImageHeight, BufferedImage.TYPE_INT_RGB);
        for (int j = 0; j < h; j++) {
            for (int i = 0; i < w; i++) {
                Color c = new Color(source_Image.getRGB(i, j));
                distination_Image.setRGB(i * zoomINLevel, j * zoomINLevel, (c.getRed() + 15) * 65536 + c.getGreen() * 256 + c.getBlue());
            }
        }
        ImageIO.write(distination_Image, "jpg", outputfile);
        imageviewer.setImage(new Image(outputfile.toURI().toURL().toString()));
        h = newImageHeight;
        w = newImageWidth;
        setdefault.setSelected(false);

    }

    //=======================================================================================================================================
    @FXML
    public void zoomOut() throws IOException {
        zoomOutLevel++;
        newImageWidth = w / zoomOutLevel;
        newImageHeight = h / zoomOutLevel;
        distination_Image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        for (int j = 0; j < h; j++) {
            for (int i = 0; i < w; i++) {
                Color c = new Color(source_Image.getRGB(i, j));
                distination_Image.setRGB(i / zoomOutLevel, j / zoomOutLevel, (c.getRed() + 15) * 65536 + c.getGreen() * 256 + c.getBlue());
                setdefault.setSelected(false);
            }
        }

        ImageIO.write(distination_Image, "jpg", outputfile);
        imageviewer.setImage(new Image(outputfile.toURI().toURL().toString()));
        h = newImageHeight;
        w = newImageWidth;
    }

    //=======================================================================================================================================
    @FXML
    public void flou() {
        try {
            distination_Image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
            for (int j1 = 1; j1 < h - 1; j1++)
                for (int i1 = 1; i1 < w - 1; i1++) {
                    int r = 0;
                    int g = 0;
                    int b = 0;
                    for (int m1 = j1 - 1; m1 <= j1 + 1; m1++)
                        for (int k1 = i1 - 1; k1 <= i1 + 1; k1++) {
                            Color c = new Color(source_Image.getRGB(k1, m1));
                            r += c.getRed();
                            g += c.getGreen();
                            b += c.getBlue();
                        }
                    distination_Image.setRGB(i1, j1, new Color(r / 9, g / 9, b / 9).getRGB());
                    setdefault.setSelected(false);
                }
        } catch (Exception e) {
        }

    }

    //=======================================================================================================================================
    @FXML
    public void imageEclaircie() throws IOException {
        distination_Image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        RescaleOp brillance = new RescaleOp(2, 6, null);
        brillance.filter(source_Image, distination_Image);

    }

    //=======================================================================================================================================
    @FXML
    public void imageSombre() throws IOException {
		/* RescaleOp assombrir = new RescaleOp(A,K, null);
		* 1. A < 1, l�image devient plus sombre.
		2. A > 1, l�image devient plus brillante.
		3. K est compris entre 0 et	256 et ajoute un �clairement . * */
        distination_Image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        RescaleOp assombrir = new RescaleOp(0.4f, 10, null);
        assombrir.filter(source_Image, distination_Image);
        System.out.println("assombrir effectue");
    }

    //=======================================================================================================================================
    @FXML
    public void histogram() {
        //Histogram class takes a BufferedImage object as an input
        //so we're passing our image to that class
        // that class do somme operations to that image
        Histogramme histogramme = new Histogramme(temporary_Image);
        JFrame frame = new JFrame("Histogram");
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.setSize(400, 400);
        frame.add(panel.add(histogramme));
        frame.setSize(500, 500);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


    }


}
