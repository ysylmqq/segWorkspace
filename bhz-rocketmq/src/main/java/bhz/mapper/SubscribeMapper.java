package bhz.mapper;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

import bhz.entity.Subscribe;

public interface SubscribeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Subscribe record);

    int insertSelective(Subscribe record);

    Subscribe selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Subscribe record);

    int updateByPrimaryKey(Subscribe record);
    
    List<Subscribe> findAllSubscribe();
    
    List<Subscribe> findByPage(Map<String, String> map, PageBounds pageBounds);
}