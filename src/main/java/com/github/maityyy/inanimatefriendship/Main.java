package com.github.maityyy.inanimatefriendship;

public final class Main {
    private Main() {}

    static void main() {
        var fabricUrl = "https://fabricmc.net/use/installer";
        var fabricLink = "\033]8;;" + fabricUrl + "\033\\" + "\033[4m" + fabricUrl + "\033[0m" + "\033]8;;\033\\";

        System.out.println(
                "\nI don't know why I wrote the code for this at all,\nbut you do understand that this is a mod for Minecraft, not a regular Java application, right?" +
                "\nInstall the Fabric mod loader and put this file into the mods folder.\n" + fabricLink + "\n"
        );
    }
}
