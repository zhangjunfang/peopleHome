package com.hipac.service;

import com.hipac.service.common.Constant;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by huqiang on 15/11/31.
 */
public class ServiceRegistry {

    private static final Logger logger= LoggerFactory.getLogger(ServiceRegistry.class);

    private CountDownLatch countDownLatch=new CountDownLatch(1);

    private String registryAddress;

    public ServiceRegistry(String registryAddress){
        this.registryAddress=registryAddress;
    }

    public void register(String data){
        if (data!=null){
            ZooKeeper zk=connectServer();
            if (zk!=null){
                createNode(zk,data);
            }
        }
    }

    private ZooKeeper connectServer(){
        ZooKeeper zk=null;
        try {
            zk=new ZooKeeper(registryAddress, Constant.ZK_SESSION_TIMEOUT, new Watcher() {
                public void process(WatchedEvent event) {
                    if (event.getState()==Event.KeeperState.SyncConnected){
                        countDownLatch.countDown();
                    }
                }
            });
            countDownLatch.await();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return zk;
    }

    private void createNode(ZooKeeper zk,String data){
        byte[]  bytes=data.getBytes();
        try {
            String path=zk.create(Constant.ZK_DATA_PATH,bytes, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode
                    .EPHEMERAL_SEQUENTIAL);
            logger.info("[ServiceRegistry - createNode ] {} {} ",path,data);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
