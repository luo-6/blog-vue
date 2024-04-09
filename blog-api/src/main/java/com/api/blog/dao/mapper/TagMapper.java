package com.api.blog.dao.mapper;

import com.api.blog.dao.pojo.Tag;
import com.api.blog.vo.TagVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface TagMapper extends BaseMapper<Tag> {
    /**
     * 根据文章id查询标签列表
     * @return
     */
    List<Tag> findTagsByArticleId(Long articleId);

    /**
     * 查询前n条最热标签
     * @param limit
     * @return
     */
    List<Long> findTagsHotsIds(int limit);

    List<Tag> findTagsByTagsIds(List<Long> tagIds);
}
