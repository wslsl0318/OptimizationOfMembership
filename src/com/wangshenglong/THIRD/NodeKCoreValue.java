package com.wangshenglong.THIRD;

import java.util.Comparator;

public class NodeKCoreValue implements Comparator<NodeKCoreValue>
{
    public int id;
    public int core_value;

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

    public NodeKCoreValue()
    {

    }

    public NodeKCoreValue(int id, int value)
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
        NodeKCoreValue other = (NodeKCoreValue) obj;
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
    public int compare(NodeKCoreValue o1, NodeKCoreValue o2)
    {
        // TODO Auto-generated method stub
        NodeKCoreValue node_1 = o1;
        NodeKCoreValue node_2 = o2;
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
