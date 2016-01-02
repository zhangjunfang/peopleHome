package com.hipac.service.rpc;

import com.hipac.service.common.Constant;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import java.util.concurrent.ThreadLocalRandom;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by huqiang on 15/11/31.
 */
public class ServiceDiscovery {

    private static final Logger   logger   = LoggerFactory.getLogger(ServiceDiscovery.class);

    private CountDownLatch        latch    = new CountDownLatch(1);

    private volatile List<String> dataList = new ArrayList<String>();

    private String                registryAddress;

    public ServiceDiscovery(String registryAddress){
        this.registryAddress=registryAddress;
        ZooKeeper zk=connectServer();
        if (zk!=null){
            watchNode(zk);
        }
    }

    public String discover(){
        String data=null;
        logger.info("[ServiceDiscovery - discover ] ");
        int size=dataList.size();
        if(size>0){
            if (size==1){
                data=dataList.get(0);
                logger.info("[ServiceDiscovery - discover ] data:{} ",data);
            }else {
                data=dataList.get(ThreadLocalRandom.current().nextInt(size));
                logger.info("[ServiceDiscovery - discover ] data:{}",data);
            }
        }
        return data;
    }

    private ZooKeeper connectServer() {
        ZooKeeper zk = null;
        try {
            zk = new ZooKeeper(registryAddress, Constant.ZK_SESSION_TIMEOUT, new Watcher() {

                @Override
                public void process(WatchedEvent watchedEvent) {
                    if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
                        latch.countDown();
                    }
                }
            });
            latch.await();
        } catch (IOException |InterruptedException e) {
            logger.error("[ServiceDiscovery - connectServer fail ]", e);
        }
        return zk;
    }

    private void watchNode(final ZooKeeper zk) {
        try {
            List<String> nodeList = zk.getChildren(Constant.ZK_REGISTRY_PATH, new Watcher() {

                @Override
                public void process(WatchedEvent watchedEvent) {
                    if (watchedEvent.getType() == Event.EventType.NodeChildrenChanged) {
                        watchNode(zk);
                    }
                }
            });
            List<String> dataList=new ArrayList<>();
            for (String node : nodeList) {
                byte[] bytes=zk.getData(Constant.ZK_REGISTRY_PATH+"/"+node,false,null);
                dataList.add(new String(bytes));
            }
            logger.info("[ServiceDiscovery - watchNode ] nodeData: {} ",dataList);
            this.dataList=dataList;
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
