### step1:消费者数量和分区数量分区吞吐量调优
org.example.consumer.Partition3ToConsumer1 \
org.example.consumer.Partition3ToConsumer3 \
org.example.consumer.Partition3ToConsumer6 \
对比上面类，验证分区和消费数量并行度的关系 \
**可以得出一个绝对结论**

### step2:优化消费者代码逻辑提升吞吐量
org.example.consumer.biz.BizLogic1 \
org.example.consumer.biz.BizLogic2 \
org.example.consumer.biz.BizLogic3 \
对比上面类，循环10000次查看行代实际运码时长，择出性能最优 \
**可以得出一个绝对结论**

### step3:优化 kafka 拉取配置
org.example.consumer.config.PullConfig1 \
org.example.consumer.config.PullConfig2 \
org.example.consumer.config.PullConfig3 \
对比上面类，消费5000w条数据，查看实际运行时长，择出性能最优 \
**可以得出一个实际场景下的结论**，配置最优解应该会和消费者代码逻辑性能、数据量、网络情况等有关

