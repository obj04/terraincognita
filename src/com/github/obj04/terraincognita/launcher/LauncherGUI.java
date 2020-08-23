package com.github.obj04.terraincognita.launcher;

import javax.swing.*;
import java.io.File;

public class LauncherGUI extends JFrame {
    Launcher launcher;

    public LauncherGUI(Launcher launcher) {
        super("Terra Incognita Launcher");
        this.launcher = launcher;
        String addr = JOptionPane.showInputDialog(null, "Server address", "localhost");
        launcher.startServer(addr);
        launcher.joinServer(addr, 38642);
    }
}
