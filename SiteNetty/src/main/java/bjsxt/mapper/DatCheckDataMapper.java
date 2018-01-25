package bjsxt.mapper;

import java.util.List;

import bjsxt.entity.DatCheckData;

public interface DatCheckDataMapper {
    int deleteByPrimaryKey(String checkNo);

    int insert(DatCheckData record);

    int insertSelective(DatCheckData record);

    DatCheckData selectByPrimaryKey(String checkNo);

    int updateByPrimaryKeySelective(DatCheckData record);

    int updateByPrimaryKey(DatCheckData record);
    
    //String generateKeyPreFix();
    
    List<DatCheckData> findNeedSync();
    
    void updateSync(String checkNo);
}