/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.softech.InterfazJavaFX.controlador;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

/**
 *
 * @author Esau
 */
public class ImgController
{
    public static String encode(Image img) throws IOException {
        String b64 = "";

        BufferedImage bimg = SwingFXUtils.fromFXImage(img, null);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        javax.imageio.ImageIO.write(bimg, "jpg", baos);

        byte bytes[] = baos.toByteArray();

        baos.close();

        b64 = Base64.getEncoder().encodeToString(bytes);

        return b64;
    }

    public static Image decode(String base64) throws IOException {
        byte bytes[] = Base64.getDecoder().decode(base64);

        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);

        BufferedImage bimg = javax.imageio.ImageIO.read(bais);

        return SwingFXUtils.toFXImage(bimg, null);

    }
}
