package cn.ryanliu.jycz.common.constant;

public class Constant {
    public static class PDAType {
        //扫码卸车
        public final static int SAOMAXIECHE = 1;
        //入场交接
        public final static int RUCHANGJIAOJIE = 2;
        //扫码装车
        public final static int SAOMAZHUANGCHE = 3;
        //出场交接
        public final static int CHUCHANGJIAOJIE = 4;
        //分拣码放
        public final static int FENJIANMAFANG = 5;
        //库区调整
        public final static int KUQUTIAOZHENG = 6;
        //库存盘点
        public final static int KUCUNPANDIAN = 7;
    }

    public static class CARType {
        //卸车明细
        public final static int XIECHEMINGXI = 1;
        //装车明细
        public final static int ZHUANGCHEMINGXI = 2;
        //装卸车汇总
        public final static int ZHUANGXIECHEHUZONG = 3;
        //盘点查询
        public final static int PANDIANCHAXUN = 4;
        //库区调整明细
        public final static int KUQUTIAOZHENGMINGXI = 5;
    }


    public static class MmKv_KEY {
        public final static String ROLES = "roles";
        public final static String ALL_ROLES = "allRoles";
        public final static String TOKEN = "token";
    }

}
