package cn.edu.nju.software.obdii.data.location;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 */
public class LocationDataManager {
    private OnLocationListener mOnLocationListener;
    private List<Point2D> mLocations;
    private String mUserDirectory;

    public LocationDataManager(String userDirectory) {
        mLocations = new ArrayList<Point2D>();
        mUserDirectory = userDirectory;
        readData();
    }

    private void readData() {
        String filename = mUserDirectory + "locations";
        File file = new File(filename);
        if (file.exists()) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(filename));
                String line = null;
                while ((line = reader.readLine()) != null) {
                    Point2D point2D = toGeoPoint(line);
                    if (point2D != null) {
                        mLocations.add(point2D);
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void writeData() {
        String filename = mUserDirectory + "locations";
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(filename));
            for (Point2D point : mLocations) {
                writer.println(point);
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Point2D toGeoPoint(String line) {
        String[] coordinates = line.split(",");
        try {
            double latitude = Double.parseDouble(coordinates[0]);
            double longitude = Double.parseDouble(coordinates[1]);
            String timestamp = coordinates[2];
            return new Point2D(latitude, longitude, timestamp);
        } catch (NumberFormatException exception) {
            return null;
        }
    }

    public void onLocationReceived(double latitude, double longitude, String timestamp) {
        Point2D point2D = new Point2D(latitude, longitude, timestamp);
        mLocations.add(point2D);
        writeData();

        if (mOnLocationListener != null) {
            mOnLocationListener.onLocationUpdate();
        }
    }

    public void setOnLocationListener(OnLocationListener onLocationListener) {
        mOnLocationListener = onLocationListener;
    }

    public List<Point2D> getLocationData() {
        return mLocations;
    }

    public interface OnLocationListener {
        public void onLocationUpdate();
    }
}
