package org.example.consumer.biz;

public class BizLogicPerformanceStatistic {

    public static void main(String[] args) {

        long sum4 = 0;
        int tryTime = 100;
        for (int i = 0; i < tryTime; i++) {
            long costTime = BizLogic4.getCostTime();
            if (i > 0) {
                // 去掉第一次运行，比较慢，不准
                sum4 += costTime;
            }
        }
        int sum3 = 0;
        for (int i = 0; i < tryTime; i++) {
            long costTime = BizLogic3.getCostTime();
            if (i > 0) {
                // 去掉第一次运行，比较慢，不准
                sum3 += costTime;
            }
        }
        int sum2 = 0;
        for (int i = 0; i < tryTime; i++) {
            long costTime = BizLogic2.getCostTime();
            if (i > 0) {
                // 去掉第一次运行，比较慢，不准
                sum2 += costTime;
            }
        }
        int sum1 = 0;
        for (int i = 0; i < tryTime; i++) {
            long costTime = BizLogic1.getCostTime();
            if (i > 0) {
                // 去掉第一次运行，比较慢，不准
                sum1 += costTime;
            }
        }

        System.out.printf("sum4:%d, sum3:%d, sum2:%d, sum1:%d", sum4/(tryTime-1), sum3/(tryTime-1), sum2/(tryTime-1), sum1/(tryTime-1));
    }
}
