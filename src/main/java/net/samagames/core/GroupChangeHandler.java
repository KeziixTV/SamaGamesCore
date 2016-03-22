package net.samagames.core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import net.samagames.api.permissions.permissions.PermissionGroup;
import net.samagames.api.permissions.permissions.PermissionUser;
import net.samagames.api.pubsub.IPacketsReceiver;
import net.samagames.core.api.permissions.PermissionEntity;
import net.samagames.core.api.permissions.PermissionManager;

import java.util.UUID;

/**
 * This file is a part of the SamaGames Project CodeBase
 * This code is absolutely confidential.
 * Created by Thog
 * (C) Copyright Elydra Network 2014 & 2015
 * All rights reserved.
 */
public class GroupChangeHandler implements IPacketsReceiver
{
    private final PermissionManager permissionsManager;
    private final Gson gson;

    public GroupChangeHandler(PermissionManager manager)
    {
        this.permissionsManager = manager;
        this.gson = new GsonBuilder().create();
    }

    @Override
    public void receive(String channel, String packet)
    {
        GroupChangePacket packetObj = null;
        try
        {
            packetObj = gson.fromJson(packet, GroupChangePacket.class);
        } catch (JsonSyntaxException ignored)
        {
        }
        if (packetObj == null || packetObj.target == null || packetObj.type == null || packetObj.playerUUID == null )
            return;
        PermissionEntity user = permissionsManager.getPlayer(packetObj.playerUUID);
        user.refresh();
    }

    private class GroupChangePacket
    {
        private String type;
        private UUID playerUUID;
        private String target;
    }
}
