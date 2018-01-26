package cc.chinagps.seat.service;

import java.util.List;

import cc.chinagps.seat.bean.table.CommandTable;

public interface CmdService {

    /**
     * 获取指令列表
     * @param param
     * @param type
     * @return
     */
    public List<CommandTable> getCmdList(String param, int type);
}
