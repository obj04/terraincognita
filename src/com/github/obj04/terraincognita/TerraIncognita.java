package com.github.obj04.terraincognita;

import com.github.obj04.terraincognita.launcher.*;

import java.awt.*;

public class TerraIncognita {
    public static void main(String[] args) {
        Launcher launcher = new Launcher();
        if(args.length == 0) {
            try {
                new LauncherGUI(launcher);
            } catch(HeadlessException e) {
                new LauncherCLI(launcher).run();
            }
        } else {
            System.out.println("Non-interactive mode");
        }
    }
}
