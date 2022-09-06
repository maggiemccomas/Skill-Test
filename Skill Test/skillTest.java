
// Maggie McComas 9/6/22

import java.io.*;
import java.nio.file.Paths;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.*;

public class skillTest{


    private static void rectangle (ArrayList<Integer> coordinates, File png){
        try{
            int numCoords = coordinates.size();
            String name = png.getName().replace("png", "");
            BufferedImage inputImage = ImageIO.read(png);
            int imgWidth = inputImage.getWidth();
            int imgHeight = inputImage.getHeight();
            BufferedImage editedImage = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_ARGB);

            Graphics2D editedGraphics = editedImage.createGraphics();
            editedGraphics.setColor(Color.YELLOW);
            editedGraphics.setStroke(new BasicStroke(8));
            editedGraphics.drawImage(inputImage, 0, 0, null);

            int rectwidth;
            int rectheight;
            int y;
            int x2;
            int y2;

            for (int x = 0; x < numCoords; x+=4){
                y = x + 1;
                x2 = x + 2;
                y2 = x + 3;
                rectwidth = coordinates.get(x2) - coordinates.get(x);
                rectheight = coordinates.get(y2) - coordinates.get(y);

                editedGraphics.drawRect(coordinates.get(x), coordinates.get(y), rectwidth, rectheight);
                File outputImage = new File(name + "editedcopy.png");
                ImageIO.write(editedImage, "png", outputImage);
            }

        } catch (IOException error){
            System.out.println("An I/O exception has occured while trying to create boxes on a png");
        }
    }


    private static void parser(File xml, File png){
        try{
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xml);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getElementsByTagName("node");

            ArrayList<Integer> coordinates = new ArrayList<Integer>();
            Node curNode;
            Element elem;
            String boundary;
            String[] coordPoints;

            for (int iter = 0; iter < nodeList.getLength(); iter++){
                curNode = nodeList.item(iter);
                elem = (Element)curNode;


                if (!curNode.hasChildNodes()){
                    boundary = elem.getAttribute("bounds");
                    boundary = boundary.replace("][", ",");
                    boundary = boundary.replace("]", "");
                    boundary = boundary.replace("[", "");
                    coordPoints = boundary.split(",");

                    for(int i = 0; i < 4; i++){
                        coordinates.add(Integer.parseInt(coordPoints[i]));
                    }
                }
            }

            rectangle(coordinates, png);

        } catch (Exception exception) {
            System.out.println("Error occurred when trying to parse through documents.");
        }
    }


    private static void cycleFiles(File[] content){
        String name;
        String extension;

        for (File file : content){
            if(!file.isDirectory()){
                extension = file.getName().substring(file.getName().length() - 3);
                name = file.getName().substring(0, file.getName().length() - 4);

                for (File filePair : content){
                    if (!filePair.getName().equals(file.getName()) && name.equals(filePair.getName().substring(0, filePair.getName().length() - 4)) && extension.equals("xml")){
                        parser(file, filePair);
                    }
                }
            }
        }
    }


    public static void main(String[] args){
        if (args[0] == null){
            System.out.println("Please specify a path");
            return;
        }
        else{
            String path = args[0];
            File[] contents = new File(path).listFiles();

            cycleFiles(contents);

            String curPath = Paths.get(".").toAbsolutePath().normalize().toString();
            File folder = new File(curPath + "/Edited-Pngs");
            folder.mkdirs();
            File[] update = new File(curPath).listFiles();
            for(File file : update){
                if(file.getName().contains("editedcopy")){
                    file.renameTo(new File(folder + "/" + file.getName()));
            }
        }
        }

    }

}