package com.syhd.math.chameleon;
 
/**
 * Chameleon(变色龙)两阶段聚类算法
 * @author lyq
 * http://www.2cto.com/database/201503/384704.html
 *
 */
public class Client {
    public static void main(String[] args){
        String filePath = "/home/socket/workspace_guoan/DanverDafa/src/com/syhd/math/chameleon/graphData.txt";
        //k-近邻的k设置
        int k = 1;
        //度量函数阈值
        double minMetric = 0.1;
         
        ChameleonTool tool = new ChameleonTool(filePath, k, minMetric);
        tool.buildCluster();
    }
}