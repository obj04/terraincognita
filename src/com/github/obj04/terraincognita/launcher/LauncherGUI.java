package com.github.obj04.terraincognita.launcher;

import javax.swing.*;
import java.io.File;

public class LauncherGUI extends JFrame {
    Launcher launcher;

    public LauncherGUI(Launcher launcher) {
        super("Terra Incognita Launcher");
        this.launcher = launcher;
        launcher.startServer(new File("."));
    }
}
