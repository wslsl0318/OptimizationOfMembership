package com.wangshenglong.THIRD;

import java.util.ArrayList;
import java.util.Comparator;

public class NodeKCoreTemp implements Comparator<NodeKCoreTemp>
{
    public int id;
    public int core_value;
    public int degree_temp;

    public ArrayList<Integer> list;

    public int getDegree_temp() {
        return degree_temp;
    }

    public void setDegree_temp(int degree_temp) {
        this.degree_temp = degree_temp;
    }

    public ArrayList<Integer> getList() {
        return list;
    }

    public void setList(ArrayList<Integer> list) {
        this.list = list;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCore_value() {
        return core_value;
    }

    public void setCore_value(int core_value) {
        this.core_value = core_value;
    }

    public NodeKCoreTemp()
    {

    }

    public NodeKCoreTemp(int id, int value)
    {
        this.id = id;
        this.core_value = value;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + core_value;
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        NodeKCoreTemp other = (NodeKCoreTemp) obj;
        if (id != other.id)
            return false;
        if (core_value != other.core_value)
            return false;
        return true;
    }

    @Override
    public String toString()
    {
        return "NodeImportance [id=" + id + ", core_value=" + core_value
                + "]";
    }

    @Override
    public int compare(NodeKCoreTemp o1, NodeKCoreTemp o2)
    {
        // TODO Auto-generated method stub
        NodeKCoreTemp node_1 = o1;
        NodeKCoreTemp node_2 = o2;
        if (node_1.core_value < node_2.core_value)
        {
            return 1;

        } else if (node_1.core_value == node_2.core_value)
        {
            if (node_1.id > node_2.id)
            {
                return 1;

            } else if (node_1.id == node_2.id)
            {
                return 0;
            } else if (node_1.id < node_2.id)
            {
                return -1;
            }
        } else if (node_1.core_value > node_2.core_value)
        {
            return -1;
        }
        return 0;
    }
}
