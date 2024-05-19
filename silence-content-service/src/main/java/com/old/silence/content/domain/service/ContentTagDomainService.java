package com.old.silence.content.domain.service;

import org.springframework.stereotype.Service;
import com.old.silence.content.api.vo.ContentTagBasicView;
import com.old.silence.content.api.vo.ContentTagTreeVo;
import com.old.silence.content.domain.enums.ContentTagType;
import com.old.silence.content.domain.repository.ContentTagRepository;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author MurrayZhang
 */
@Service
public class ContentTagDomainService {

    private final ContentTagRepository contentTagRepository;

    public ContentTagDomainService(ContentTagRepository contentTagRepository) {
        this.contentTagRepository = contentTagRepository;
    }


    public List<ContentTagTreeVo> findTags(BigInteger id, ContentTagType type, Boolean enabled) {
        var contentTags = contentTagRepository.findByTypeAndEnabled(type, enabled, ContentTagBasicView.class);
        return listToTree(contentTags, id);
    }

    private List<ContentTagTreeVo> listToTree(List<ContentTagBasicView> list, BigInteger parentId) {
        // 创建一个映射表，用于快速查找节点
        Map<BigInteger, ContentTagTreeVo> map = new HashMap<>();
        List<ContentTagTreeVo> tree = new ArrayList<>();

        // 初始化映射表和树结构
        for (ContentTagBasicView item : list) {
            ContentTagTreeVo node = new ContentTagTreeVo();
            node.setId(item.getId());
            node.setName(item.getName());
            node.setCode(item.getCode());
            node.setParentId(item.getParentId());
            node.setSort(item.getSort());
            map.put(node.getId(), node);
        }

        // 构建树结构
        for (ContentTagBasicView item : list) {
            ContentTagTreeVo node = map.get(item.getId());
            if (node.getParentId() == null || parentId.equals(node.getParentId())) {
                tree.add(node);
            } else {
                ContentTagTreeVo parent = map.get(item.getParentId());
                if (parent != null) {
                    if (parent.getChildren() == null) {
                        parent.setChildren(new ArrayList<>());
                    }
                    parent.getChildren().add(node);
                }
            }
        }

        // 按sort字段排序
        tree.sort(Comparator.comparingLong(ContentTagTreeVo::getSort));
        for (ContentTagTreeVo node : tree) {
            sortTree(node);
        }

        return tree;
    }

    private void sortTree(ContentTagTreeVo node) {
        if (node.getChildren() != null) {
            node.getChildren().sort(Comparator.comparingLong(ContentTagTreeVo::getSort));
            for (ContentTagTreeVo child : node.getChildren()) {
                sortTree(child);
            }
        }
    }

}
