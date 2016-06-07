package nl.vaneijndhoven.opencv.lanedetection;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.awt.font.ImageGraphicAttribute;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.Path;

import static java.lang.Math.PI;

public class ImageLaneDetection {

    public static void main(String[] args) throws URISyntaxException {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        ImageLaneDetection detector = new ImageLaneDetection();
        URL image1 = detector.getClass().getClassLoader().getResource("images/road1.jpg");
        URL image2 = detector.getClass().getClassLoader().getResource("images/road2.jpg");
        detector.detectLane(image1);
        detector.detectLane(image2);
    }

    private void detectLane(URL url) throws URISyntaxException {
        System.out.println("reading image " + url);
        File file = new File(url.toURI());
        String filename = file.getName();
        String edgesname = filename.replace(".", "-edges.");
        String linesname = filename.replace(".", "-lines.");

        Mat image = Imgcodecs.imread(url.getPath());
        Mat imgEdges = new Mat();
        Mat lines = new Mat(); //MatOfInt4 ?
        Mat imgLines = new Mat();

        Imgproc.cvtColor(image, imgLines, Imgproc.COLOR_RGBA2GRAY);
        Imgproc.cvtColor(imgLines, imgLines, Imgproc.COLOR_GRAY2RGBA);

        // step1 edge detection
        Imgproc.Canny(image, imgEdges, 10, 700);

        // step 2 line detection
        Imgproc.HoughLinesP(imgEdges, lines, 1, Math.PI/180, 70, 30, 15);

        // step 3 draw detected lines
        for (int x = 0; x < lines.rows(); x++) {
            double[] vec = lines.get(x, 0);
            double x1 = vec[0];
            double y1 = vec[1];
            double x2 = vec[2];
            double y2 = vec[3];
            Point start = new Point(x1, y1);
            Point end = new Point(x2, y2);

            Imgproc.line(imgLines, start, end, new Scalar(255,0,0), 2);
        }

        Imgcodecs.imwrite("/Users/jpoint/Repositories/opencv-playground/src/main/resources/images/" + edgesname, imgEdges);
        Imgcodecs.imwrite("/Users/jpoint/Repositories/opencv-playground/src/main/resources/images/" + linesname, imgLines);
    }

}
