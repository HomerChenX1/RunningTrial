package com.example.runningtrial.base;

import java.io.Serializable;

class DesignBase {
}

// only for data storage
class PoJo {
    private int idx;
    private long data;

    public int getIdx() { return idx; }
    public void setIdx(int idx) { this.idx = idx; }
    public long getData() { return data; }
    public void setData(long data) { this.data = data; }
    public PoJo() { }
}
// only for data storage
class PoJo2 {
    private int idx2;
    private long data2;

    public PoJo2() { }

    public int getIdx2() { return idx2; }
    public void setIdx2(int idx2) { this.idx2 = idx2; }
    public long getData2() { return data2; }
    public void setData2(long data2) { this.data2 = data2; }
}

// if Asynchronous, implements Chain
class JavaBean extends PoJo implements Serializable {
    // 所有屬性都必須是private
    private int idx3;
    private long data3;
    // 提供一個默認的無參構造函數。
    public JavaBean() { super(); }
    // getters/setters are optional by design
    public int getIdx3() { return idx3; }
    public void setIdx3(int idx3) { this.idx3 = idx3; }
    public long getData3() { return data3; }
    public void setData3(long data3) { this.data3 = data3; }
    // OverRide toString for debug
}

// may extends PoJo or JavaBean dependent on Networking or save/retrive from files
// or use gson for serialize
// if Asynchronous, implements Chain
class SpringBean extends PoJo {
    // 所有屬性都必須是private or protected
    private int idx4;
    protected long data4;
    // 提供一個默認的無參構造函數。
    public SpringBean() { super(); }
    // getters are not necessary
    public int getIdx4() { return idx4; }
    public long getData4() { return data4; }
    // setters are necessary
    public void setIdx4(int idx4) { this.idx4 = idx4; }
    public void setData4(long data4) { this.data4 = data4; }
    // controller methods
    public boolean chkIdx4() { return idx4==0? false: true;}
    // strategy methods
    public void strategy() {
        // Ex : volley
        // volleyRequestQueue.add(this);
    }
    // OverRide toString for debug
}

class Composition extends SpringBean{
    private PoJo pojo3;
    private PoJo2 pojo4;

    public Composition() {
        pojo3 = new PoJo();
        pojo4 = new PoJo2();
    }

    // redirect methods
    public int getIdx() { return pojo3.getIdx(); }

    // demo destructor. in Java , GC automatically do this
    public void close() {
        // destructor
        pojo3 = null;
        pojo4 = null;
        try {
            this.finalize();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}

class Aggregation extends SpringBean{
    private PoJo pojo1;
    private PoJo2 pojo2;

    public Aggregation(PoJo pojo1, PoJo2 pojo2) {
        this.pojo1 = pojo1;
        this.pojo2 = pojo2;
    }

    // redirect methods
    public int getIdx() { return pojo1.getIdx(); }
}
