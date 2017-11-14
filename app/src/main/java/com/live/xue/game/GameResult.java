package com.live.xue.game;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

import java.util.List;

/**
 * Created by Ofx on 2017/10/31.
 * {"code":200,"data":{"record":[["x","x","x","z","z","z","x","x","z"],["h","xxd","xzd","x","zzd","zxd","z","xzd","x"],["z","h","x","x","x","x","z","x","h"],["zzdxd","xzdxd","z","z","xzd","z","z","z","x"],["x","h","z","x","x","z","z","x"],["z","z","x","xzd","zxd","h","z","zd"]],"chart":[["x1","z","x","z","x","z","x","z","x","z","x","z","x","z","x","z","x","z","x","z","x","z","x","z","x1"],[0,"z",0,0,"x3",0,"x","z",0,0,"x",0,"x","z","x","z",0,"z1",0,"z","x",0,0,"z","x"],[0,0,0,0,0,0,"x",0]]}}
 */

public class GameResult {


    /**
     * code : 200
     * data : {"record":[["x","x","x","z","z","z","x","x","z"],["h","xxd","xzd","x","zzd","zxd","z","xzd","x"],["z","h","x","x","x","x","z","x","h"],["zzdxd","xzdxd","z","z","xzd","z","z","z","x"],["x","h","z","x","x","z","z","x"],["z","z","x","xzd","zxd","h","z","zd"]],"chart":[["x1","z","x","z","x","z","x","z","x","z","x","z","x","z","x","z","x","z","x","z","x","z","x","z","x1"],[0,"z",0,0,"x3",0,"x","z",0,0,"x",0,"x","z","x","z",0,"z1",0,"z","x",0,0,"z","x"],[0,0,0,0,0,0,"x",0]]}
     */

    private int code;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<List<String>> record;
        private List<List<String>> chart;

        public List<List<String>> getRecord() {
            return record;
        }

        public void setRecord(List<List<String>> record) {
            this.record = record;
        }

        public List<List<String>> getChart() {
            return chart;
        }

        public void setChart(List<List<String>> chart) {
            this.chart = chart;
        }
    }

    public static GameResult mokeData(){
        String json = "{\"code\":200,\"data\":{\"record\":[[\"x\",\"x\",\"x\",\"z\",\"z\",\"z\",\"x\",\"x\",\"z\"],[\"h\",\"xxd\",\"xzd\",\"x\",\"zzd\",\"zxd\",\"z\",\"xzd\",\"x\"],[\"z\",\"h\",\"x\",\"x\",\"x\",\"x\",\"z\",\"x\",\"h\"],[\"zzdxd\",\"xzdxd\",\"z\",\"z\",\"xzd\",\"z\",\"z\",\"z\",\"x\"],[\"x\",\"h\",\"z\",\"x\",\"x\",\"z\",\"z\",\"x\"],[\"z\",\"z\",\"x\",\"xzd\",\"zxd\",\"h\",\"z\",\"zd\"]],\"chart\":[[\"x1\",\"z\",\"x\",\"z\",\"x\",\"z\",\"x\",\"z\",\"x\",\"z\",\"x\",\"z\",\"x\",\"z\",\"x\",\"z\",\"x\",\"z\",\"x\",\"z\",\"x\",\"z\",\"x\",\"z\",\"x1\"],[0,\"z\",0,0,\"x3\",0,\"x\",\"z\",0,0,\"x\",0,\"x\",\"z\",\"x\",\"z\",0,\"z1\",0,\"z\",\"x\",0,0,\"z\",\"x\"],[0,0,0,0,0,0,\"x\",0]]}}";
        GameResult result = JSON.parseObject(json, new TypeReference<GameResult>() {});
        return result;
    }
}
