package org.fynx.fynxhub.listener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.UUID;

public class LobbyVisibility {

    private static ArrayList<UUID> noplayervisible;

    public static ArrayList<UUID> getNoplayervisible() {
        return noplayervisible;
    }
}
