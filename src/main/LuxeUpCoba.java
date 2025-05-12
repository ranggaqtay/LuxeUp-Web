package main;

import View.SignInView;
import javax.swing.SwingUtilities;

public class LuxeUpCoba {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SignInView().setVisible(true);
   });

    }
}
