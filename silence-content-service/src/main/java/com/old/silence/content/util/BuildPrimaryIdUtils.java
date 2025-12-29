package com.old.silence.content.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Clock;

/**
 * @author moryzang
 */
public class BuildPrimaryIdUtils {

    private static final Logger logger = LoggerFactory.getLogger(BuildPrimaryIdUtils.class);

    private static final FlakeIdUtils flakeIdUtils = new FlakeIdUtils(initMachineId(), 1, Clock.systemDefaultZone());

    private BuildPrimaryIdUtils() {

    }

    public static long getPrimaryId() {
        return flakeIdUtils.nextId();
    }

    private static int initMachineId() {
        String localIp = "";
        InetAddress address = null;
        try {
            address = InetAddress.getLocalHost();
            localIp = address.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        logger.info("localIp:{}", localIp);
        if (StringUtils.isNotEmpty(localIp)) {
            String[] split = localIp.split("\\.");
            if (split.length == 4) {
                int machineId = Integer.parseInt(split[0]);
                logger.info("localIp:{}, machineId:{}", localIp, machineId);
                return machineId;
            }
        }
        return 64;
    }
}
