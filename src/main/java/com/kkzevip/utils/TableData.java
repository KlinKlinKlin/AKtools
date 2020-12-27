package com.kkzevip.utils;

import com.aliyuncs.ecs.model.v20140526.DescribeInstancesResponse;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class TableData {

    private final Object[] initColumns = {"序号", "实例ID", "服务器地区", "地区编号", "主机名称", "系统版本", "运行状态", "私有IP", "公网IP", "安全组ID", "主机配置", "创建时间", "过期时间"};
    private final Object[][] initData = {};
    private Map<String, String> map;

    public TableData(Map<String, String> m) {
        this.map = m;
    }

    public void updateTable(List<DescribeInstancesResponse.Instance> list, JTable jTable) {

         Vector<Vector<String>> cRows = new Vector<Vector<String>>();
         int n = 1;
         for (DescribeInstancesResponse.Instance ins: list) {
             StringBuilder privateIP = new StringBuilder();
             StringBuilder publicIP = new StringBuilder();
             StringBuilder secs = new StringBuilder();
             String configs = ins.getCpu() + "核" + ins.getMemory() + "MB";
             for (String ip: ins.getVpcAttributes().getPrivateIpAddress()) {
                 privateIP.append(ip).append(",");
             }
             for (String ip: ins.getPublicIpAddress()) {
                 publicIP.append(ip).append(",");
             }
             for (String sec: ins.getSecurityGroupIds()) {
                 secs.append(sec).append(",");
             }
             Vector<String> cRow = new Vector<String>();
             cRow.add(String.valueOf(n));
             cRow.add(ins.getInstanceId());
             cRow.add(this.map.get(ins.getBizRegionId()));
             cRow.add(ins.getBizRegionId());
             cRow.add(ins.getInstanceName());
             cRow.add(ins.getOSName());
             cRow.add(ins.getStatus());
             cRow.add(privateIP.substring(0, privateIP.toString().lastIndexOf(",")));
             cRow.add(publicIP.substring(0, publicIP.toString().lastIndexOf(",")));
             cRow.add(secs.substring(0, secs.toString().lastIndexOf(",")));
             cRow.add(configs);
             cRow.add(ins.getCreationTime());
             cRow.add(ins.getExpiredTime());

             cRows.add(cRow);
             n++;
         }
         Vector<String> title = new Vector<String>();
         for (Object initColumn : initColumns) {
            title.add(initColumn.toString());
         }
         TableModel tableModel = new DefaultTableModel(cRows, title);
         jTable.setModel(tableModel);
    }

    public Object[] getInitColumns() {
        return initColumns;
    }

    public Object[][] getInitData() {
        return initData;
    }
}
