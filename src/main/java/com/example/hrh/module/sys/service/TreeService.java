package com.example.hrh.module.sys.service;/**
 * Created by Administrator on 2018/11/4 0004.
 */

import com.example.hrh.module.common.exceptions.FormValidException;
import com.example.hrh.module.sys.dao.entities.Menu;
import com.example.hrh.module.sys.dao.entities.TreeNode;
import com.example.hrh.module.sys.dao.jpas.TreeNodeMapper;
import com.example.hrh.module.sys.dto.json.menu.BootstrapTreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

/**
 * @Description:
 * @Author: ren
 * @CreateTime: 2018-11-2018/11/4 0004 16:49
 */
@Service
public class TreeService {

    /**
     * 默认根目录节点
     */
    public static final long DEFAULT_PARENT_ID = 0L;

    /**
     * 类别集合
     */
    public final static Map<String, String> entityMap = new HashMap<>();

    static {
        entityMap.put(Menu.class.getSimpleName(), "系统目录树");
    }

    @Autowired
    private TreeNodeMapper treeNodeMapper;


    /**
     * 更新树节点
     *
     * @param entityId       实体ID
     * @param parentEntityId 实体父级节点ID
     * @param afterEntityId  排序节点ID
     * @param entityKey      实体Key
     */
    @Transactional(rollbackOn = Exception.class)
    public void modifyTreeNode(Long entityId, Long parentEntityId, Long afterEntityId, String entityName, String entityKey) {

        if (parentEntityId.equals(entityId)) {
            throw new FormValidException("父级ID不能为当前ID");
        }

        TreeNode currentNode = null;

        if (null == parentEntityId || parentEntityId == 0L) {

            List<TreeNode> treeNodes = treeNodeMapper.findByParentEntityId(parentEntityId, entityKey);
            // 更新树节点排序
            currentNode = updateNode(entityId, afterEntityId, entityKey, treeNodes);
            // 更新节点信息
            currentNode.setParentId(DEFAULT_PARENT_ID);
            currentNode.setParentEntityId(DEFAULT_PARENT_ID);
        } else {
            TreeNode parentNode = treeNodeMapper.findParentNode(parentEntityId, entityKey);
            if (null != parentNode) {

                // 更新树节点排序
                currentNode = updateNode(entityId, afterEntityId, entityKey, parentNode.getSubTreeNodes());

                // 更新节点信息
                currentNode.setParentId(parentNode.getId());
                currentNode.setParentEntityId(parentNode.getEntityId());
            }
        }

        // 更新当前树节点
        currentNode.setEntityName(entityName);
        treeNodeMapper.update(currentNode);

    }

    /**
     * 更新节点排序
     *
     * @param entityId
     * @param afterEntityId
     * @param entityKey
     * @param treeNodes
     * @return
     */
    @Transactional(rollbackOn = Exception.class)
    private TreeNode updateNode(Long entityId, Long afterEntityId, String entityKey, List<TreeNode> treeNodes) {
        Collections.sort(treeNodes);
        // 当前节点
        TreeNode currentNode = null;

        for (TreeNode node : treeNodes) {
            if (node.getEntityId().equals(entityId)) {
                currentNode = node;
            }
        }

        if (null == currentNode) {
            currentNode = treeNodeMapper.findByEntityId(entityId, entityKey);
        } else {
            treeNodes.remove(currentNode);
        }

        int index = -1;
        // 待更新列表
        List<TreeNode> sortList = new ArrayList<>(treeNodes.size());

        if (null == afterEntityId || afterEntityId == -1L) {

            TreeNode lastNode = treeNodes.get(treeNodes.size() - 1);
            currentNode.setSort(lastNode.getSort() + 1);
            sortList.add(currentNode);

        } else if (afterEntityId == 0L) {
            // 列首
            index = 1;
            currentNode.setSort(index);
            sortList.add(currentNode);
            index++;
        }

        // 列中
        for (TreeNode node : treeNodes) {

            if (index != -1) {
                node.setSort(index);
                sortList.add(node);
                index++;
                continue;
            }
            if (node.getEntityId().equals(afterEntityId)) {
                index = node.getSort() + 2;
                currentNode.setSort(node.getSort() + 1);
                sortList.add(currentNode);
            }
        }

        // 更新排序列表
        if (!sortList.isEmpty()) {
            treeNodeMapper.updateSortList(sortList);
        }

        return currentNode;
    }

    /**
     * 保存树节点
     *
     * @param node
     * @param afterNodeId
     */
    public void saveTreeNode(TreeNode node, Long afterNodeId) {

        Long parentId = node.getParentId();
        String entityKey = node.getEntityKey();

        // 顶级树
        if (parentId == 0L) {

            node.setParentEntityId(parentId);
            node.setParentId(parentId);

            List<TreeNode> treeNodes = treeNodeMapper.findByParentEntityId(parentId, entityKey);
            // 插入节点
            insertNode(node, afterNodeId, treeNodes);
        }

        // 子树
        TreeNode parentNode = treeNodeMapper.findParentNode(parentId, entityKey);

        if (null != parentNode) {
            node.setParentEntityId(parentNode.getEntityId());
            node.setParentId(parentNode.getId());
            // 插入节点
            insertNode(node, afterNodeId, parentNode.getSubTreeNodes());
        }
    }

    /**
     * 插入节点
     *
     * @param node
     * @param afterNodeId
     * @param treeNodes
     */
    @Transactional(rollbackOn = Exception.class)
    private void insertNode(TreeNode node, Long afterNodeId, List<TreeNode> treeNodes) {

        if (node.getParentEntityId().equals(node.getEntityId())) {
            throw new FormValidException("父级ID不能为当前ID");
        }

        Collections.sort(treeNodes);
        // 列首
        if (treeNodes.isEmpty()) {
            node.setSort(1);
            treeNodeMapper.save(node);
            return;
        }

        // 列尾
        if (null == afterNodeId || afterNodeId == -1L) {
            TreeNode lastOne = treeNodes.get(treeNodes.size() - 1);
            node.setSort(lastOne.getSort() + 1);
            treeNodeMapper.save(node);
            return;
        }

        List<TreeNode> sortUpdateList = new ArrayList<>(treeNodes.size());
        // 列首
        if (afterNodeId == 0L) {
            node.setSort(1);
            treeNodeMapper.save(node);
            int index = 2;
            for (int i = 0; i < treeNodes.size(); i++) {
                TreeNode obj = treeNodes.get(i);
                obj.setSort(index + i);
                sortUpdateList.add(obj);
            }
        } else {

            // 列中
            Integer index = -1;
            for (TreeNode obj : treeNodes) {
                if (index != -1) {
                    index++;
                    obj.setSort(index);
                    sortUpdateList.add(obj);
                }
                if (obj.getEntityId().equals(afterNodeId)) {
                    index = obj.getSort() + 1;
                    node.setSort(index);
                    treeNodeMapper.save(node);
                }
            }
        }

        // 批量更新树节点排序
        if (!sortUpdateList.isEmpty()) {
            treeNodeMapper.updateSortList(sortUpdateList);
        }
        return;
    }

    /**
     * 获取 BootstrapTree
     *
     * @param source
     * @param checkList
     * @return
     */
    public List<BootstrapTreeNode> getBootstrapTree(List<TreeNode> source, Set<Long> checkList) {

        List<BootstrapTreeNode> bootstrapTree = new ArrayList<>(source.size());
        // 拼接树
        appendBootstrapTreeNode(source, checkList, bootstrapTree);

        return bootstrapTree;
    }

    /**
     * 拼接 BootstrapTreeNode 子节点
     *
     * @param source
     * @param checkList
     * @param bootstrapTree
     */
    private void appendBootstrapTreeNode(List<TreeNode> source, Set<Long> checkList, List<BootstrapTreeNode> bootstrapTree) {
        source.forEach(obj -> {
            BootstrapTreeNode node = new BootstrapTreeNode();
            node.setId(obj.getEntityId());
            node.setText(obj.getEntityId() + " - " + obj.getEntityName());

            // 配置选择
            BootstrapTreeNode.State state = null;
            if (null != checkList && checkList.contains(obj.getEntityId())) {
                state = new BootstrapTreeNode.State(true);
            } else {
                state = new BootstrapTreeNode.State(false);
            }
            node.setState(state);
            bootstrapTree.add(node);

            if (obj.getSubTreeNodes().isEmpty()) {
                return;
            }

            // 递归注入子节点
            List<BootstrapTreeNode> subBootstrapTree = new ArrayList<>(obj.getSubTreeNodes().size());
            node.setNodes(subBootstrapTree);
            appendBootstrapTreeNode(obj.getSubTreeNodes(), checkList, subBootstrapTree);
        });
    }

    /**
     * 获取实体树
     *
     * @param entityKey
     * @param status
     * @return
     */
    public List<TreeNode> getTree(String entityKey, Integer status) {

        // TODO 不具备该类别
        if (!entityMap.containsKey(entityKey)) {
            return null;
        }

        List<TreeNode> treeNodes = treeNodeMapper.findByTypeAndStatus(entityKey, status);

        // 索引目录集合
        Map<Long, List<TreeNode>> sourceTreeNodeMap = new HashMap<>(16);
        List<TreeNode> topTreeNodeList = new ArrayList<>();

        treeNodes.forEach(node -> {
            if (node.getParentId().equals(DEFAULT_PARENT_ID)) {
                topTreeNodeList.add(node);
                return;
            }

            List<TreeNode> list = sourceTreeNodeMap.get(node.getParentId());
            if (null == list) {
                list = new ArrayList<>();
                sourceTreeNodeMap.put(node.getParentId(), list);
            }

            list.add(node);
        });

        Collections.sort(topTreeNodeList);
        // 拼接子节点
        appendTreeNode(topTreeNodeList, sourceTreeNodeMap);

        return topTreeNodeList;
    }

    /**
     * 递归拼接子节点
     *
     * @param parentNodes
     * @param treeNodeMap
     */
    private void appendTreeNode(List<TreeNode> parentNodes, Map<Long, List<TreeNode>> treeNodeMap) {

        if (treeNodeMap.isEmpty()) {
            return;
        }

        Map<Long, List<TreeNode>> removeMap = new HashMap<>(treeNodeMap.size());

        parentNodes.forEach(parentNode -> {
            List<TreeNode> list = treeNodeMap.get(parentNode.getId());
            if (null != list) {
                Collections.sort(list);
                parentNode.setSubTreeNodes(list);
                removeMap.put(parentNode.getId(), list);
                // 递归注入
                appendTreeNode(list, treeNodeMap);
            }
        });

        // 清除已使用 key
        treeNodeMap.remove(removeMap);
    }


    public static void setIcon(List<BootstrapTreeNode> list, String icon) {

        list.forEach(obj -> {
            if (!obj.getNodes().isEmpty()) {
                setIcon(obj.getNodes(), icon);
            } else {
                if (obj.getId() != 0L) {
                    obj.setIcon(icon);
                    obj.setText(" " + obj.getText());
                } else {
                    obj.setText("  " + obj.getText());
                }
            }
        });
    }
}
