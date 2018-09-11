
import org.apache.poi.util.IOUtils;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFPictureData;
import org.apache.poi.xslf.usermodel.XSLFSlide;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import java.io.*;


public class app {

    public static void main(String[] args) {
        XMLSlideShow ppt = new XMLSlideShow();
        CriarPpt();

        File fotoRuim = new File("Evidencia.png");
        String dir = System.getProperty("user.dir");

        for (int i = 0; i < 5; i++) {
            TirarFoto(dir, i);
            File resized = new File(dir + "\\Evidencias\\EvidenciaResized"+i+".png");
            createSlide(resized, ppt);

        }




    }

    public static void CreateDirectory() {
        File file = new File("Evidencias");

        if (!file.exists()) {
            file.mkdir();
            System.out.println("Pasta criada!");


        } else {
            System.out.println("A pasta já existe!");

        }
    }

    public static void TirarFoto(String dir, int num) {
        try {

            Robot robot = new Robot();
            String format = "png";
            String fileName = "Evidencia." + format;


            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            Rectangle captureRect = new Rectangle(0, 0, screenSize.width, screenSize.height - 40);
            BufferedImage screenFullImage = robot.createScreenCapture(captureRect);
            ImageIO.write(screenFullImage, format, new File(fileName));

            resize(dir + "\\Evidencia.png", dir + "\\Evidencias\\EvidenciaResized" + num + ".png", 1280, 720);
            System.out.println("Evidencia criada com sucesso");


        } catch (AWTException | IOException ex) {
            System.err.println(ex);
        }
    }
        public static void createSlide (File nomeFoto, XMLSlideShow ppt){
            XSLFSlide slide = ppt.createSlide();
            //converting it into a byte array
            byte[] picture = new byte[0];
            try {
                picture = IOUtils.toByteArray(new FileInputStream(nomeFoto));
            } catch (IOException e) {
                e.printStackTrace();
            }

            //adding the image to the presentation
            XSLFPictureData idx = ppt.addPicture(picture, XSLFPictureData.PictureType.PNG);

            //creating a slide with given picture on it
            slide.createPicture(idx);
            File file = new File("Evicendias.pptx");
            FileOutputStream out = null;
            try {
                out = new FileOutputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            //saving the changes to a file
            try {
                ppt.write(out);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("image added successfully");

        }

    public static void CriarPpt() {
        //creating a file object
        XMLSlideShow ppt = new XMLSlideShow();

    }

    public static void resize(String inputImagePath,
                              String outputImagePath, int scaledWidth, int scaledHeight)
            throws IOException {
        // reads input image
        File inputFile = new File(inputImagePath);
        BufferedImage inputImage = ImageIO.read(inputFile);

        // creates output image
        BufferedImage outputImage = new BufferedImage(scaledWidth,
                scaledHeight, inputImage.getType());

        // scales the input image to the output image
        Graphics2D g2d = outputImage.createGraphics();
        g2d.drawImage(inputImage, 0, 0, scaledWidth, scaledHeight, null);
        g2d.dispose();

        // extracts extension of output file
        String formatName = outputImagePath.substring(outputImagePath
                .lastIndexOf(".") + 1);

        // writes to output file
        ImageIO.write(outputImage, formatName, new File(outputImagePath));


    }
}








