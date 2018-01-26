package cc.chinagps.gateway.unit.useat.util;

import org.seg.lib.net.encoder.SegPackageEncoder;

import cc.chinagps.gateway.unit.useat.upload.SegPkgHandler;

public class USeatUtil {
	public static final int CMD_ID_ALL_CMD = -1;
	
	public static final int CMD_ID_ALL_DATA = -2;
	
	public static final SegPackageEncoder segSeatPackageEncoder = new SegPackageEncoder();
	
	public static final SegPkgHandler segSeatPackageHandler = new SegPkgHandler();
}