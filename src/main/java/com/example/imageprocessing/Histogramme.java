package com.example.imageprocessing;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;

public class Histogramme extends JComponent {
    private static final long
            serialVersionUID = 1L;
    private final int largeur = 256;
    private final int hauteur = 200;
    private final int[] rouge = new int[256];
    private final int[] vert = new int[256];
    private final int[] bleu = new int[256];
    private BufferedImage image, histogramme;
    private Graphics2D dessin;

    public Histogramme(BufferedImage monImage) {
        // get the source image as a BufferedImage
        image = monImage;
        //Get the image's RGB colors
        recupererRVB();
        tracerHistogrammes();
    }

    protected void paintComponent(Graphics surface) {
        // surface.drawImage(image, 0, 0,null);
        surface.drawImage(histogramme, 100, 100, null);
    }

    private void recupererRVB() {
        //Raster Images are based on pixels
        Raster trame = image.getRaster();
        int[] rgb = new int[3];
        int maximum = 0;
        for (int y = 0; y < image.getHeight(); y++)
            for (int x = 0; x < image.getWidth(); x++) {
                trame.getPixel(x, y, rgb);
                rouge[rgb[0]]++;
                vert[rgb[1]]++;
                bleu[rgb[2]]++;
            }
    }

    private void tracerHistogrammes() {
        histogramme = new BufferedImage(largeur, hauteur, BufferedImage.TYPE_INT_ARGB);
        dessin = histogramme.createGraphics();
        Rectangle2D rectangle = new Rectangle2D.Double(0, 0, largeur - 1, hauteur - 1);
        dessin.draw(rectangle);
        dessin.setPaint(new Color(1F, 1F, 1F, 0.2F));
        dessin.fill(rectangle);
        changerAxes();
        dessin.setPaint(new Color(1F, 0F, 0F, 0.4F));
        tracerHistogramme(rouge);
        dessin.setPaint(new Color(0F, 1F, 0F, 0.4F));
        tracerHistogramme(vert);
        dessin.setPaint(new Color(0F, 0F, 1F, 0.4F));
        tracerHistogramme(bleu);
    }

    private void changerAxes() {
        dessin.translate(0, hauteur);
        double surfaceImage = image.getWidth() * image.getHeight();
        double surfaceHistogramme = histogramme.getWidth() * histogramme.getHeight();
        dessin.scale(1, -surfaceHistogramme / surfaceImage / 3.7);
    }

    private void tracerHistogramme(int[] couleur) {
        for (int i = 0; i < 255; i++)
            dessin.drawLine(i, 0, i, couleur[i]);
    }
}
