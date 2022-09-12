package me.hi12167pies.BedClutch.Utils;

import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class DirectionVector {
    private static String checkDiag(Player p) {
        double rotation = (p.getLocation().getYaw() - 180) % 360;
        if (rotation < 0) {
            rotation += 360.0;
        }
        if (0 <= rotation && rotation < 90) {
            return "NE";
        } else if (90 <= rotation && rotation < 180) {
            return "SE";
        } else if (180 <= rotation && rotation < 270) {
            return "SW";
        } else if (270 <= rotation && rotation < 360) {
            return "NW";
        } else {
            return null;
        }
    }
    private static String checkDirection(Player p) {
        double rotation = (p.getLocation().getYaw() - 180) % 360;
        if (rotation < 0) {
            rotation += 360.0;
        }
        if (0 <= rotation && rotation < 45) {
            return "N";
        } else if (45 <= rotation && rotation < 135) {
            return "E";
        } else if (135 <= rotation && rotation < 225) {
            return "S";
        } else if (225 <= rotation && rotation < 315) {
            return "W";
        } else if (315 <= rotation && rotation < 360.0) {
            return "N";
        } else {
            return null;
        }
    }
    public static Vector getDiag(Player p, Double X, Double Y, Double Z) {
        String PlayerDirection = checkDiag(p);
        switch (PlayerDirection) {
            case "NW":
                return new Vector(X, Y, Z);
            case "NE":
                return new Vector(-X, Y, Z);
            case "SE":
                return new Vector(-X, Y, -Z);
            case "SW":
                return new Vector(X, Y, -Z);
        }
        return new Vector(0,0,0);
    }
    public static Vector getStraight(Player p, Double X, Double Y) {
        String PlayerDirection = checkDirection(p);
        switch (PlayerDirection) {
            case "N":
                return new Vector(0.0, Y, X);
            case "S":
                return new Vector(0.0, Y, -X);
            case "E":
                return new Vector(-X, Y, 0.0);
            case "W":
                return new Vector(X, Y, 0.0);
        }
        return new Vector(0,0,0);
    }
}
