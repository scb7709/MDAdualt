package photopicker.widget;


/**
 * Created by abc on 2017/12/15.
 */
public class ImageCheck {
    public static boolean isImag(String filepath) {
        //String[] imagType = {".jpg", ".png", ".jpeg"};
        return filepath.endsWith(".jpg") || filepath.endsWith(".png") || filepath.endsWith(".jpeg") || filepath.endsWith(".bmp");
    }
}
