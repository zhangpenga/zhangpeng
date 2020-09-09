import java.awt.event.*;

import javax.swing.*;

import java.sql.*;
public class sale2 extends JFrame implements ActionListener
{
private JPanel sM=new JPanel();
JLabel label1,label2,label3,label4,label5;
JTextField field1,field2;
JButton button1,button2,button3,button4;
String num=null,name=null,time=null,add=null,t1=null,t2=null,t3=null;
float price=0;int counts;int n1;
private JTextArea goodsShow=new JTextArea();
private JScrollPane jsp=new JScrollPane(goodsShow);
private JLabel[]CD={
new JLabel("编号"),
new JLabel("名称"),
new JLabel("价格"),
new JLabel("生产日期"),
new JLabel("数量")};
public sale2()
{
label1=new JLabel("查询商品");
label2=new JLabel("删除商品");
label3=new JLabel("增加商品");
label4=new JLabel("更改属性");
label5=new JLabel("商品信息管理系统");
field1=new JTextField(20);
field2=new JTextField(20);
JTextField[] jtf=new JTextField[3];
for(int i=0;i<3;i++){
    jtf[i]=new JTextField(20);
    jtf[i].setBounds(150+150*i,160,100,22);
    sM.add(jtf[i]);
}
JTextField[] jtf2=new JTextField[5];
for(int i=0;i<5;i++){
    jtf2[i]=new JTextField(20);
    jtf2[i].setBounds(150+150*i,190,100,22);
    sM.add(jtf2[i]);
}
button1=new JButton("查询");
button2=new JButton("删除");
button3=new JButton("增加");
button4=new JButton("更改");
sM.setLayout(null);
field1.setBounds(150,100,100,22);
field2.setBounds(150,130,100,22);
label1.setBounds(90,90,150,30);
label2.setBounds(90,120,150,30);
label3.setBounds(90,190,150,30);
label4.setBounds(90,155,150,30);
label5.setBounds(400,30,150,30);
button1.setBounds(280,100,80,22);
button2.setBounds(280,130,80,22);
button3.setBounds(910,190,80,22);
button4.setBounds(610,160,80,22);
button1.addActionListener(new ActionListener() {
	   public void actionPerformed(ActionEvent e) {
		   String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	        String dbURL = "jdbc:sqlserver://localhost:1433;DatabaseName=market";
	        String userName = "sa";
	        String userPwd = "12345";
	        Connection dbConn = null;
	        try
	        {
	            Class.forName(driverName);
	            dbConn = DriverManager.getConnection(dbURL, userName, userPwd);
	            String str=field1.getText();
	            String sql = ("SELECT * FROM [goods] where num='"+str+"'");
	            int bs=0;
	            PreparedStatement statement = null;
	            statement = dbConn.prepareStatement(sql);
	            ResultSet rs = statement.executeQuery();
	            while(rs.next()){ 
	            	num=rs.getString("num");
	            	name=rs.getString("name");
	            	price=rs.getFloat("price");
	            	time=rs.getString("time");
	            	counts=rs.getInt("counts");
	            bs=1;	
	            }
	            if(bs==1) {goodsShow.append("   "+num+" "+name+" "+price
	            		+ "                                             "+time+
	            		"                  "+counts+"\n");
	            bs=0;}
	            else  goodsShow.append("没有此商品"+"\n");
 }
	        catch (Exception e1)
	        {
	            e1.printStackTrace();
}
		}
});

button2.addActionListener(new ActionListener() {
	   public void actionPerformed(ActionEvent e) {
		   String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	        String dbURL = "jdbc:sqlserver://localhost:1433;DatabaseName=market";
	        String userName = "sa";
	        String userPwd = "12345";
	        Connection dbConn = null;
	        try
	        {
	            Class.forName(driverName);
	            dbConn = DriverManager.getConnection(dbURL, userName, userPwd);
	            String str=field1.getText();
	            String sql = ("delete FROM [goods] where num='"+str+"'");
	            Statement stmt=dbConn.createStatement();
	            stmt.executeUpdate(sql);
	             goodsShow.append("删除成功"+"\n");
}
	        catch (Exception e1)
	        {
	            e1.printStackTrace();
}
		}
});
button3.addActionListener(new ActionListener() {
	   public void actionPerformed(ActionEvent e) {
		   String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	        String dbURL = "jdbc:sqlserver://localhost:1433;DatabaseName=market";
	        String userName = "sa";
	        String userPwd = "12345";
	        Connection dbConn = null;
	        String str[]=new String[5];
	        try
	        {
	            Class.forName(driverName);
	            dbConn = DriverManager.getConnection(dbURL, userName, userPwd);
	            for(int i=0;i<5;i++)
	            { 
	            	str[i]=jtf2[i].getText();
	            }
	            String sql = ("insert [goods] values('"+str[0]+"','"+str[1]+"','"+str[2]+"','"+str[3]+"','"+str[4]+"')");
	            Statement stmt=dbConn.createStatement();
	            stmt.executeUpdate(sql);
	             goodsShow.append("添加成功"+"\n");
}
	        catch (Exception e1)
	        {
	            e1.printStackTrace();
}
		}
});
button4.addActionListener(new ActionListener() {
	   public void actionPerformed(ActionEvent e) {
		   String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	        String dbURL = "jdbc:sqlserver://localhost:1433;DatabaseName=market";
	        String userName = "sa";
	        String userPwd = "12345";
	        Connection dbConn = null;
	        String str[]=new String[3];
	        try
	        {
	            Class.forName(driverName);
	            dbConn = DriverManager.getConnection(dbURL, userName, userPwd);
	            for(int i=0;i<3;i++)
	            { 
	            	str[i]=jtf[i].getText();
	            }
	            String sql = ("update [goods] set "+str[1]+"='"+str[2]+"' where num= '"+str[0]+"'");
	            Statement stmt=dbConn.createStatement();
	            stmt.executeUpdate(sql);
	             goodsShow.append("更改成功"+"\n");
}
	        catch (Exception e1)
	        {
	            e1.printStackTrace();
}
		}
});
sM.add(label1);
sM.add(label2);
sM.add(label3);
sM.add(label4);
sM.add(label5);
sM.add(field1);
sM.add(field2);
sM.add(button1);
sM.add(button2);
sM.add(button3);
sM.add(button4);
CD[0].setBounds(120,260,115,20);
CD[1].setBounds(300,260,115,20);
CD[2].setBounds(460,260,115,20);
CD[3].setBounds(620,260,115,20);
CD[4].setBounds(780,260,115,20);
for(int i=0;i<5;i++)
{ 
sM.add(CD[i]);
}
jsp.setBounds(100,280,800,500);
sM.add(jsp);
this.add(sM);
this.setTitle("商品信息");
this.setResizable(true);
this.setBounds(0,0,1024,668);
this.setVisible(true);
}
public static void main(String[] args) {
	new sale2();
}
@Override
public void actionPerformed(ActionEvent arg0) {
	// TODO Auto-generated method stub
	
}
}

