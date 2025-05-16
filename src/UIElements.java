import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class UIElements {
    public static Color priCol = new Color(106, 105, 37);
    public static Color secCol = new Color(198, 166, 132);
    public static Color borderCol = new Color(185, 146, 104);
    public static Color bgCol = new Color(253, 239, 210);

    public static int defaultSize = 14;
    public static String defaultFont = "Arial";

    // BUTTON
    public static class customButton extends JButton {
        public customButton(String label, String fontFace, int fontType, int fontSize) {
            setText(label); // equivalent to new JButton(text);
            labelStyle(fontFace, fontType, fontSize);
            buttonStyle();
        }
        public customButton(String label, int fontType, int fontSize) {
            setText(label); // equivalent to new JButton(text);
            labelStyle(defaultFont, fontType, fontSize);
            buttonStyle();
        }
        public customButton(String label, int fontSize) {
            setText(label); // equivalent to new JButton(text);
            labelStyle(defaultFont, Font.PLAIN, fontSize);
            buttonStyle();
        }
        public customButton(String label) {
            setText(label);
            labelStyle(defaultFont, Font.PLAIN, defaultSize);
            buttonStyle();
        }

        // Button Icons
        public customButton(ImageIcon image, int width, int height) {
            imgStyle(image, width, height);
            buttonStyle();
        }
        public customButton(ImageIcon image) {
            imgStyle(image, defaultSize, defaultSize);
            buttonStyle();
        }

        private void labelStyle(String fontFace, int fontType, int fontSize) {
            // Font to be changed (want Poppins font)
            setFont(new Font(fontFace, fontType, fontSize));
            setForeground(UIElements.bgCol);
        }
        
        private void imgStyle(ImageIcon image, int width, int height) {
            Image img = image.getImage();
            Image resizeImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            setIcon(new ImageIcon(resizeImg));
        }
        
        private void buttonStyle() {
            // All of these colors are temporary. Will find a proper palette soon.
            setBackground(UIElements.secCol);
            //Remove button highlights when navigating with Tab
            setFocusPainted(false);
            setBorder(new LineBorder(UIElements.borderCol, 2));
        }
    }

    public static class customLabel extends JLabel {
        public customLabel(String label, String fontFace, int fontType, int fontSize) {
            labelStyle(label, fontFace, fontType, fontSize);
        }
        public customLabel(String label, int fontType, int fontSize) {
            labelStyle(label, defaultFont, fontType, fontSize);
        }
        public customLabel(String label, int fontSize) {
            labelStyle(label ,defaultFont, Font.PLAIN, fontSize);
        }
        public customLabel(String label) {
            labelStyle(label, defaultFont, Font.PLAIN, defaultSize);
        }

        public customLabel(ImageIcon image, int width, int height) {
            imgStyle(image, width, height);
        }
        public customLabel(ImageIcon image) {
            imgStyle(image,defaultSize, defaultSize);
        }

        private void labelStyle(String label, String fontFace, int fontType, int fontSize) {
            // Font to be changed (want Poppins font)
            setFont(new Font(fontFace, fontType, fontSize));
            setForeground(UIElements.borderCol);
            setText(label);
        }

        private void imgStyle(ImageIcon image, int width, int height) {
            Image img = image.getImage();
            Image resizeImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            setIcon(new ImageIcon(resizeImg));
        }
    }


    /*public static class customImage {
        public customImage(String image, int width, int height) {
            imgStyle(image, width, height);
        }

        public customImage(String image) {
            imgStyle(image, defaultSize, defaultSize);
        }

        private Image imgStyle(String image, int width, int height) {
            ImageIcon icon = new ImageIcon(image);
            Image img = icon.getImage();
            return img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        }
    }*/
}
