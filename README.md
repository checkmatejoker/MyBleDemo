# MyBleDemo
##一.项目介绍：
本项目是使用原有互诺蓝牙模块开发的简介模块支持基本的基于蓝牙4.0的Android发送和接受功能

##二.支持版本
android4.3以上，项目配置如下
compileSdkVersion 27
minSdkVersion 18
argetSdkVersion 22

##三.接入方式
在需要使用的模块中引入
implementation 'com.mybledemo:tzybletool:1.0.+'

##四.使用方法
###步骤一 配置：
```
在希望初始化蓝牙的界面的activity包含中配置一下三个字段，例子中使用的是蓝牙列表界面，也是示例的第一个界面
            <meta-data
                android:name="serviceid"
                android:value="你的serverid"></meta-data>
            <meta-data
                android:name="characteristicid"
                android:value="你的特征值id"></meta-data>
            <meta-data
                android:name="notifyid"
                android:value="你的notifyid"></meta-data>
                ```

###步骤二 搜索&连接：让希望使用蓝牙功能的界面继承库的BaseActivity，然后调用其中的方法下面做对应的说明，具体可以参考例子。
```
1.搜索 public void stratScan(boolean scan);//蓝牙搜索方法
 有新设备加入会调用 
 public void scanCallback();//蓝牙搜索到新设备后回掉
 同时会刷新存储蓝牙设备的列表AppManger.getAppManger().getmDevicelist();获取该列表后可以自行进行相关操作
```
2.连接 public void connecdevice(int postion);//蓝牙连接方法
 连接成功后会调用 
 public void connectCallback();
 连接成功后可进行相关操作。
```
 PS:在蓝牙搜索的界面要设置该界面的界面属性为start界面，具体方法就是调用 setmStartBleActivity(true);
 该方法只能在初始化蓝牙服务的第一个界面调用一次，具体参考例子，并且要在supreme方法之前
```
###步骤三 发送&接受数据
```
 1.发送数据
 调用单例对象
 CommondManger.getCommondManger().sendCommond(mbyte)；//mbyte为发送数组，一次最多20byte；
```
 2.接受数据
 public void dataGetCallback(byte[] getdata);
 在该回调中会返回硬件发送过来的数据 getdata，最多也为20个byte，可以在该回调中处理数据。
```
 ##五，类以及方法说明
 AppManger 
 功能：单例数据存储，以及通用属性值对象，后续会拓展。
 主要方法： 
 ###public  List<BluetoothDevice> getmDevicelist();//获取蓝牙列表
```
 CommondManger 
 功能：单例数据发送对象，后续会拓展。
 主要方法：
 ###public void sendCommond(byte[] msenddata);//发送蓝牙数据
```
 BaseActivity 
 功能：蓝牙服务集成基础activity对象，后续会拓展。
 主要方法： 
 ```
 1.public void stratScan(boolean scan);//搜索蓝牙
```
 2.public void setmStartBleActivity(boolean mStartBleActivity);//设置启动界面
```
 3.public void connecdevice(int postion);//连接设备
```
 4.public void  disConnectCallback(){};//蓝牙连接断开时的回调
```
 5.public void  connectCallback(){};//蓝牙连接成功的回调
```
 6.public void  initServerCallback(){};//蓝牙服务初始化成功的回调
```
 7.public void  scanCallback(){};//搜索到新设备的蓝牙回调
```
 8.public void  dataGetCallback(byte [] getdata){};//接受到数据后的蓝牙回调
```
 ##六，项目计划
 目前该项目只是实现了基本的搜索连接发送功能，后续功能会持续更新，欢迎提出宝贵的意见。我的邮箱215475174@qq.com，欢迎大家互相交流学习









