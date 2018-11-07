package com.example.hrh.dao;

import com.example.hrh.module.sys.dao.entities.Menu;
import com.example.hrh.module.sys.dao.entities.TreeNode;
import com.example.hrh.module.sys.dao.jpas.MenuMapper;
import com.example.hrh.module.sys.dao.jpas.TreeNodeMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TreeNodeMapperTests {

    @Autowired
    private TreeNodeMapper treeNodeMapper;

    @Test
    public void testFindParentNode() {
        TreeNode parentNode = treeNodeMapper.findParentNode(2L, Menu.class.getSimpleName());
        System.out.println(parentNode);
        System.out.println();
        parentNode.getSubTreeNodes().forEach(System.out::println);
    }

    @Test
    public void testFindByTypeAndStatus() {
        List<TreeNode> treeNodes = treeNodeMapper.findByTypeAndStatus(Menu.class.getSimpleName(), null);
        treeNodes.forEach(obj -> {
            System.out.println("obj:" + obj);
        });
    }

    @Test
    public void testSaveBatch() {

        List<TreeNode> treeNodes = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            TreeNode node = new TreeNode();
            node.setEntityName("测试" + i);
            node.setEntityId(i + 0L);
            node.setParentId(0L);
            node.setEntityKey("Menu");
            node.setParentEntityId(2L);
            node.setSort(i + 1);
            node.setCreateTime(new Date());
            node.setStatus(1);
            treeNodes.add(node);
        }

        treeNodeMapper.saveBatch(treeNodes);
    }
}
