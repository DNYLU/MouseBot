import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class MouseBot {

    // The file path of the button image
    private static final String BUTTON_IMAGE_FILE_PATH = "images/button.png";

    public static void main(String[] args) {
        System.out.println("MouseBot.main() is called");
        try {
            // Create a Robot instance
            Robot robot = new Robot();

            // Get the graphics devices for the screens on the computer
            GraphicsDevice[] devices = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();

            // Print the ID string of each monitor in the devices array
            for (int i = 0; i < devices.length; i++) {
                System.out.println("Monitor at position " + i + ": " + devices[i].getIDstring());
            }

            // Capture a screenshot of each screen and save it to a file
            for (int i = 0; i < devices.length; i++) {
                BufferedImage screen = robot.createScreenCapture(devices[i].getDefaultConfiguration().getBounds());
                ImageIO.write(screen, "png", new File("screenshots/screen" + i + ".png"));
            }

            // Search for the button image on each screen
            for (GraphicsDevice device : devices) {
                // Capture the screen and save it to a file
                BufferedImage screen = robot.createScreenCapture(device.getDefaultConfiguration().getBounds());
                ImageIO.write(screen, "png", new File("images/screen.png"));
                // Load the button image
                BufferedImage buttonImage = ImageIO.read(new File(BUTTON_IMAGE_FILE_PATH));
                System.out.println("Button image dimensions: " + buttonImage.getWidth() + "x" + buttonImage.getHeight());

                // Get the coordinates of the button from the image map file
                Scanner scanner = new Scanner(new File("image-map.txt"));
                String line = scanner.nextLine();
                String[] coords = line.split(",");
                int x = Integer.parseInt(coords[0]);
                int y = Integer.parseInt(coords[1]);

                // Move the mouse to the button's location and click it
                robot.mouseMove(x, y);
                robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

            }


            // Listen for the "ø" key and exit when it is pressed
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.contains("ø")) {
                    robot.keyPress(KeyEvent.VK_ESCAPE);
                    break;
                }
            }
            scanner.close();
        } catch (AWTException | IOException e) {
            e.printStackTrace();
        }
    }
}
