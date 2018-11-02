package com.example.hrh.module.sys.dto.json.menu;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Collections;
import java.util.List;

public class BootstrapTreeNode {

    private Long id;

    private String text;

    private State state;

    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    private List<BootstrapTreeNode> nodes = Collections.emptyList();

    public BootstrapTreeNode() {
    }

    public BootstrapTreeNode(MenuNode node) {

        this.id = node.getId();
        this.text = node.getName();
        this.state = new State(false);
        if (node.isCheck()) {
            state.setChecked(true);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public List<BootstrapTreeNode> getNodes() {
        return nodes;
    }

    public void setNodes(List<BootstrapTreeNode> nodes) {
        this.nodes = nodes;
    }

    public static class State {

        private Boolean checked = false;

        public State(Boolean checked) {
            this.checked = checked;
        }

        public Boolean getChecked() {
            return checked;
        }

        public void setChecked(Boolean checked) {
            this.checked = checked;
        }

    }
}
